package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;


import com.mysite.sbb.user.UserSecurityService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration //스프링의 환경설정 파일이라는 것을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@EnableGlobalMethodSecurity(prePostEnabled = true) // 질문|답변컨트롤러에서 로그인 여부 판별을 위한 @PreAuthorize 를 사용하기 위해 반드시 필요
public class SecurityConfig {
    
    /* @Bean
     * 1.개발자가 직접 제어가 불가능한 라이브러리를 활용할 때
       2.애플리케이션 전범위적으로 사용되는 클래스를 등록할 때
       3.다형성을 활용하여 여러 구현체를 등록해주어야 할 때
    */
    // @Component : 개발자가 직접 작성한 클래스를 Bean으로 등록할 때 사용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
	        .authorizeRequests()
	        .antMatchers("/**").permitAll()
            .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            ;
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
