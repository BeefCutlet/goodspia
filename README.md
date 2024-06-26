# GoodsPia
굿즈 판매 플랫폼 API



## 목차
1. [개요](#개요)
2. [기술스택](#기술스택)
3. [아키텍처](#아키텍처)
4. [ERD](#ERD)
5. [세부구현](#세부구현)
   - [회원가입/로그인 프로세스](#회원가입/로그인-프로세스)
   - [인증 프로세스](#인증-프로세스)
   - [배포 자동화 프로세스](#배포-자동화-프로세스)
   - [전략 패턴으로 쿠폰 할인 정책 적용](#전략-패턴으로-쿠폰-할인-정책-적용)
   - [API 성능테스트](#API-성능테스트)



## 개요

- 일반 사용자가 굿즈판매자로 등록하면 굿즈를 판매할 수 있는 굿즈 판매 플랫폼의 API 입니다.
- RESTful한 API를 만들기 위하여 URL을 설정할 때, 행위는 GET / POST / PUT / DELETE와 같은 HTTP 메서드를 이용하여 표현하고, 행위의 대상이 되는 리소스는 명사형으로 표현하였습니다.
- 성능 테스트 툴을 사용하여 API의 성능테스트를 진행하고, 캐싱으로 성능을 개선하였습니다.
- 로그인을 하면 JWT를 생성하여 전달하고, 인증이 필요한 요청 시에 JWT에 기록된 정보를 바탕으로 인증을 처리합니다.
- GithubActions를 이용하여 Github에 Push 이벤트가 발생하면 배포 작업을 자동으로 수행합니다.



## 기술스택

- Backend
    - Java 11
    - Spring Boot 2.7.14.
    - Spring MVC
    - Spring Security
    - Spring Data JPA
    - AWS S3
    - AWS RDS
- Frontend
   - Javascript
   - Vue3
   - Pinia
- Deploy
    - Amazon EC2
    - AWS Code Deploy
    - Github Actions
    - Amazon S3
    - Vercel


## 아키텍처

![architecture](https://github.com/BeefCutlet/goodspia/assets/77325024/7d8e5e8a-e592-4d81-b20f-9e6391a607ea)


## ERD

![erd](https://github.com/BeefCutlet/goodspia/assets/77325024/00d3b22c-d368-467b-9b29-c0dd7c15c5a5)


## 세부구현

### 회원가입/로그인 프로세스

![GoodsPia_Login_Process](https://github.com/BeefCutlet/goodspia/assets/77325024/6661a03a-40a9-465a-b795-a664c940d856)

- 회원가입은 입력받은 이메일을 바탕으로 중복 회원이 없다면 비밀번호를 암호화한 뒤 DB에 저장하도록 하였습니다.
- 로그인은 입력받은 이메일을 바탕으로 DB에서 회원 정보를 조회하여 이메일과 비밀번호가 일치하면 성공하도록 하였습니다.
- 인증이 완료된 후 사용자의 인증 상태를 확인하는 방법은 세션과 JWT 2가지를 고려하였습니다.
  - 세션은 클라이언트의 상태를 서버의 메모리에 저장하여 관리하기 때문에 로그인 중인 사용자가 늘어날수록 서버 메모리에 부담이 된다는 단점이 있습니다.
  - JWT는 클라이언트의 상태를 서버에서 관리하지 않고 클라이언트과 관리하기 때문에 로그인 중인 사용자가 늘어나더라도 서버에 부담이 줄어든다는 장점이 있습니다.
- 트래픽이 늘어났을 때 Scale out 방식으로 대처할 것으로 가정하였기 때문에 서버가 분산되더라도 클라이언트의 상태를 쉽게 파악할 수 있는 수단을 사용해야 했습니다.
  - JWT는 스케일 아웃 방식으로 서버의 수가 늘어나도 Stateless한 특성 덕분에 클라이언트의 상태를 파악하기 적합하였습니다. 
  - 세션 인증을 할 경우, 서버의 수가 늘어나면 Stateful한 성격 때문에 세션 불일치 문제가 발생할 가능성이 있습니다. 그래서 세션 인증 방식을 채택한다면, 추가적인 설계를 고려해야 했습니다.
- 다만, JWT 인증 방식은 상태 관리를 서버에서 할 수가 없기 때문에 토큰을 탈취당할 경우, 다른 사람이 인증을 하는 것을 막을 방법이 없습니다.
  - 이런 보안 상의 문제에 대처하기 위하여 액세스 토큰의 만료기한을 5분으로 짧게 설정하고, 리프레시 토큰의 만료기한을 30분으로 비교적 길게 설정하여 토큰이 탈취되더라도 금방 사용이 불가능해지도록 설정하였습니다.
  - 리프레시 토큰은 HTTP Header의 Set-Cookie에 담아서 전달하였습니다. 그러나 쿠키는 브라우저 또는 HTTP 통신 감청을 통해 탈취될 가능성이 있습니다.
  - 혹시라도 SSL 암호화를 피해서 토큰을 탈취하는 일이 없도록 secure 옵션을 설정하였습니다. 또한, XSS 공격으로 쿠키를 탈취당하지 않도록 HttpOnly 옵션을 설정하였습니다.




### 인증 프로세스

![GoodsPia_Authentication_Process](https://github.com/BeefCutlet/goodspia/assets/77325024/4e524808-5f82-47f7-9049-6da3e5dde417)

- 로그인하지 않은 사용자는 다른 회원의 정보에 접근할 수 없습니다.
- 또한, 일반 사용자는 아티스트가 사용할 수 있는 기능(굿즈 등록, 수정 등)을 이용할 수 없습니다.
- 접근할 수 없는 정보에 접근하지 못하게 하기 위해 특정 요청 시에 인증을 요청하게 하고 있으며, 인증은 JWT 인증 방식을 사용합니다.
- 인증은 다음과 같은 방식으로 진행됩니다.
  - 사용자가 특정 요청을 하면, 요청의 Authorization 헤더에서 액세스 토큰을 추출합니다. 토큰의 인증 타입이 Bearer이 아니라면, 인증이 실패합니다.
  - Secret Key를 이용하여 토큰의 유효성 검사를 실시합니다.
  - 토큰에 설정된 만료일을 현재 시각과 비교하여 만료 여부를 판단합니다.
- 3단계의 인증을 통과하면 요청을 정상적으로 처리하고, 인증에 실패할 경우 리프레시 토큰을 이용하여 액세스 토큰을 재발급하도록 합니다. 리프레시 토큰은 다음과 같은 방식으로 검증하여 액세스 토큰을 재발급합니다.
  - Secret Key를 이용하여 토큰의 유효성 검사를 실시합니다.
  - DB에 저장된 리프레시 토큰과 전달된 리프레시 토큰을 비교하여 일치 여부를 확인합니다.
  - 토큰에 설정된 만료일을 현재 시각과 비교하여 만료 여부를 판단합니다.
- 리프레시 토큰 인증에 성공하면 액세스 토큰을 재발급하고, 실패하면 다시 로그인하도록 처리합니다.



### 배포 자동화 프로세스

![GoodsPia_Deploy_Process](https://github.com/BeefCutlet/goodspia/assets/77325024/7c30e351-bb6d-4ace-965d-512ab8135d72)

- 프로젝트를 배포한 뒤, 디버깅을 하고 성능 테스트를 진행한 뒤 재배포를 하려면, 다음과 같은 과정을 거쳐야 했습니다.
  - 깃허브에 수정된 코드를 Push 한다.
  - 기존 프로세스를 종료한다.
  - 깃허브에서 pull 명령어로 프로젝트를 업데이트한다.
  - 프로세스를 재실행한다.
- 해당 과정을 직접 서버에 접속해서 수동으로 하는 것은 시간적 비용이 많이 들어가는 작업이었습니다. 그래서 이 과정을 단축하기 위해서는 배포를 자동화할 수 있는 프로세스가 필요했습니다.
- 배포 자동화를 할 수 있는 대표적인 수단으로 Travis CI, Jenkins, Github Actions 등의 툴이 있습니다.
- 이 중 Github Actions는 추가적인 프로그램을 설치할 필요없이 Github에서 스크립트를 작성해서 간단하게 배포가 가능하였기에, 배포 자동화만을 위해서는 Github Actions이 관리하기 편하다고 생각하여 선택하였습니다.
- Github Actions으로 배포 자동화를 구현하는 과정은 다음과 같이 구현하였습니다.
  - Github에 Push 이벤트가 동작하면 application.yml에 Github Secrets로부터 파라미터를 넣은 다음, zip 파일로 압축하여 Amazon S3에 업로드합니다.
  - Amazon CodeDeploy에 요청을 하여 S3에서 업로드한 zip 파일을 EC2 인스턴스에 다운로드합니다.
  - EC2 인스턴스에 zip 파일을 압축 해제하고, 프로그램을 빌드 후 배포합니다.


### 전략 패턴으로 쿠폰 할인 정책 적용

주문 정보를 저장할 때, 주문 가격에 대해 적용된 할인 쿠폰이 있다면 할인된 주문 가격을 저장하도록 하였습니다.\
처음에는 주문 정보 저장 메소드에 쿠폰 할인에 대한 로직을 그대로 넣었는데, 쿠폰 할인을 위한 검증 및 계산 로직이 비대하여 private 메소드를 만들어서 분리하더라도 OrderService 클래스와 메소드의 크기가 비대해졌습니다.\
또한, 쿠폰은 정액 할인인지 정률 할인인지에 따라 다른 검증과 계산을 해야했기에 쿠폰 종류에 따라 switch(혹은 if)문을 통해 다른 로직을 작성해야 했습니다.\
카테고리 쿠폰, 브랜드 쿠폰 등 다른 쿠폰이 추가된다면 조건문은 더 커질 것이 자명했습니다.\
이를 해결하기 위해 쿠폰 할인을 계산하는 로직을 분리하고, 쿠폰 정책을 전략 패턴으로 구현한다면 추후에 새로운 쿠폰 정책이 추가되더라도 유연하게 추가할 수 있을 거라고 생각했습니다.

![CouponPolicy](https://github.com/BeefCutlet/goodspia/assets/77325024/c40b3170-090c-4991-ba84-752a8685cbc1)

 
위와 같은 구조로 구현한 결과, 새로운 쿠폰을 적용할 때에는 CouponPolicy 인터페이스를 상속받은 클래스를 구현하기만 하고, CouponDiscountCalculator에 쿠폰 타입에 따라 해당 정책을 선택하는 로직을 추가 작성하면 다른 로직을 건드리지 않고 새로운 쿠폰 정책을 추가할 수 있게 되었습니다.


### API 성능테스트

- 성능테스트는 1분 동안 여러 명의 사용자가 API에 동시에 요청을 하는 상황을 가정하고, 요청에 대한 응답을 받을 때까지 걸리는 시간을 기준으로 측정하였습니다.
- 성능테스트를 위한 툴은 간단한 자바스크립트 코드로 테스트를 작성하여 결과를 숫자로 받아볼 수 있는 k6를 선택하였습니다.
- 테스트는 트래픽이 몰릴 가능성이 있는 API 중 2가지를 대상으로 진행하였습니다.
   - 선택한 상품을 장바구니에 등록하는 API(INSERT) => 인기 굿즈로 알려져서 여러 사용자가 동시에 특정 상품을 장바구니에 담는 상황에 트래픽이 몰릴 수 있습니다.
   - 메인페이지에서 최신 굿즈를 조회하는 API(SELECT) => 메인페이지는 사이트에 접속하는 모든 사용자가 가장 처음 보게 되는 페이지이기 때문에 가장 많이 요청될 수 있습니다.


**(1) 장바구니 등록 API**


**1) 200 VUS(Virtual Users) + 1분 + AmazonRDS for MySQL**

RDS만 사용했을 때에는
- 1분 동안 1304개의 요청을 처리
- 평균 응답 시간 약 853ms
- 최대 응답 시간은 2.63초
- 상위 10%의 요청이 1.08초를 초과

하는 결과가 나왔습니다.
![cart-200vus-1m-mysql-v2-loop](https://github.com/BeefCutlet/goodspia/assets/77325024/8a9935d4-e46a-43cc-9da1-ccf5fff5c11f)




**2) 200 VUS + 1분 + Amazon ElastiCache (Redis)**

Redis를 이용하여 데이터를 저장하면 In-Memory DB 특유의 빠른 속도로 처리되어 응답 시간이 단축될 것이라 기대하였습니다.

결과적으로
- 1분 동안 1493개의 요청을 처리
- 평균 응답 시간 약 748ms
- 최대 응답 시간은 1.75초
- 상위 10%의 요청이 905ms를 초과

하는 결과가 나왔습니다.

평균적으로 약 10% 정도 속도가 빨라졌고, 응답 시간의 편차도 줄어들었지만 생각보다 큰 개선이 이루어지지 않았습니다.
테스트하는 과정에서 EC2 서버의 CPU 사용률을 확인한 결과, 꾸준히 100%를 유지하고 있었는데 Redis 사용으로 인해 DB 처리속도는 빨라졌지만 서버에서 부하가 걸려서 크게 개선되지 않았다는 가설을 내릴 수 있었습니다.
![cart-200vus-1m-redis-v2-loop](https://github.com/BeefCutlet/goodspia/assets/77325024/cb2bab7f-81bd-4c27-aa21-6d85cfe744e3)


**(2) 굿즈 조회 API**


**1) 600 VUS + 1분 + AmazonRDS for MySQL**

최신 굿즈를 조회하는 요청은 페이징 처리가 되어있었고, 테스트에서 요청한 데이터는 총 15개만 가져오도록 요청하였습니다.
그래서 INSERT 요청에 비해 상대적으로 빠른 응답 시간을 받을 수 있었고, Virtual Users를 600까지 늘려서 테스트 해볼 수 있었습니다.

테스트 결과,
- 1분 동안 12146개의 요청을 처리
- 평균 응답 시간 약 671ms
- 최대 응답 시간은 4.94초
- 상위 10%의 요청이 1.26초를 초과

하는 결과가 나왔습니다.
![goodslist-600vus-1m-noncache](https://github.com/BeefCutlet/goodspia/assets/77325024/2af4ef5d-0b26-4713-b61e-5f21fd08b0fc)



**2) 600 VUS + 1분 + Amazon ElastiCache (Redis)**

굿즈 조회 성능 개선을 위하여 Redis를 사용하여 캐싱 처리를 하였습니다.
캐싱 전략은 조회 요청이 들어오면 우선적으로 Redis에서 데이터를 찾아본 뒤, 데이터가 없으면 RDS에서 가져와서 처리하고 이후에 Redis에 캐싱하는 Cache-Aside 전략을 사용하였습니다.
맨 처음 요청이 들어왔을 때에는 Redis에 데이터가 들어있지 않아서 다시 RDS에서 조회하기 때문에 상대적으로 더 느려집니다.
그래서 캐싱이 이루어졌을 때의 평균 응답 시간을 정확하게 측정하기 위해서 1회의 요청을 먼저 넣은 다음에 캐싱이 완료되고나서 테스트를 수행하였습니다.

테스트 결과,
- 1분 동안 30720개의 요청을 처리
- 평균 응답 시간 약 58ms
- 최대 응답 시간은 약 458ms
- 상위 10%의 요청이 약 114ms를 초과

하는 결과가 나왔습니다.

캐싱을 하지 않았을 때보다 약 91%라는 상당한 속도 개선이 있었습니다.
![goodslist-600vus-1m-cache](https://github.com/BeefCutlet/goodspia/assets/77325024/f76f6a33-ae38-40e0-8c60-26e4464b63dc)
