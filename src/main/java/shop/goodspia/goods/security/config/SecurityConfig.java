package shop.goodspia.goods.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.security.exception.JwtAccessDeniedHandler;
import shop.goodspia.goods.security.exception.JwtAuthenticationEntryPoint;
import shop.goodspia.goods.security.filter.JwtAuthenticationFilter;
import shop.goodspia.goods.common.util.JwtUtil;
import shop.goodspia.goods.security.filter.JwtLoginFilter;
import shop.goodspia.goods.security.handler.JwtLoginFailureHandler;
import shop.goodspia.goods.security.handler.JwtLoginSuccessHandler;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public SecurityConfig(JwtUtil jwtUtil,
                          MemberRepository memberRepository,
                          @Value("${access.expiration}") long accessTokenExpiration,
                          @Value("${refresh.expiration}") long refreshTokenExpiration) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/goods/list", "/goods/detail/*").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtLoginFilter(http.getSharedObject(AuthenticationConfiguration.class)), JwtAuthenticationFilter.class);
        return http.build();
    }

    //비밀번호 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, accessTokenExpiration);
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        jwtLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jwtLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return jwtLoginFilter;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new JwtLoginFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JwtLoginSuccessHandler(
                jwtUtil,
                memberRepository,
                accessTokenExpiration,
                refreshTokenExpiration
        );
    }
}
