package shop.goodspia.goods.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import shop.goodspia.goods.security.filter.JwtAuthenticationFilter;
import shop.goodspia.goods.security.filter.JwtLoginFilter;
import shop.goodspia.goods.security.handler.JwtAccessDeniedHandler;
import shop.goodspia.goods.security.handler.JwtAuthenticationEntryPoint;
import shop.goodspia.goods.security.handler.JwtLoginFailureHandler;
import shop.goodspia.goods.security.handler.JwtLoginSuccessHandler;
import shop.goodspia.goods.security.service.JwtUtil;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "/application-dev.yml")
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "/swagger-ui",
                "/swagger-ui/**",
                "/api-docs",
                "/api-docs/**",
                "/goods/list",
                "/goods/detail/*");
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
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .addFilterBefore(jwtLoginFilter(http.getSharedObject(AuthenticationConfiguration.class)),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), JwtLoginFilter.class);
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
        return new JwtLoginSuccessHandler(jwtUtil, memberRepository);
    }
}
