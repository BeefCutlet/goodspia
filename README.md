# GoodsPia
굿즈 구매 웹 서비스


## 목차
1. 프로젝트 목적
2. 핵심기능
3. 기술스택
4. 시스템 아키텍처
5. ERD
6. 세부구현
   - 회원가입/로그인 프로세스
   - 인증 프로세스
   - 배포 자동화 프로세스


## 핵심기능

- 플랫폼의 사용자는 아티스트로 등록할 수 있습니다.
- 아티스트로 등록한 사용자는 자신만의 굿즈를 등록, 판매할 수 있습니다.
- 일반 사용자는 굿즈를 장바구니에 담고 구매할 수 있습니다.
- 로그인 이후에는 JWT를 발급받아서 인증이 필요한 요청 시에 헤더에 함께 담아 전달하여 인증을 실시합니다.
- GithubActions를 이용하여 Github에 Push 이벤트가 발생하면 배포 작업을 자동으로 수행합니다.


## 기술스택

- Development
    - Spring Boot
    - Spring MVC
    - Spring Security
    - Spring Data JPA

- DB
    - AWS S3
    - AWS RDS

- Deploy
    - Amazon EC2
    - AWS Code Deploy
    - Github Actions


## 시스템 아키텍처

![GoodsPia Architecture](https://github.com/BeefCutlet/goodspia/assets/77325024/e1f4f44b-048e-4c1e-96f3-a5eb0eb69b40)



## ERD

![GoodsPia_ERD](https://github.com/BeefCutlet/goodspia/assets/77325024/dfdc2142-ee0d-4877-859d-1f81341892c3)


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
