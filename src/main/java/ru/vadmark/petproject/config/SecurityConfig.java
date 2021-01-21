package ru.vadmark.petproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vadmark.petproject.config.jwt.JWTAuthenticationFilter;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RequiredArgsConstructor
@EnableWebSecurity/*(debug = true)*/
// @EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {
            "/",
            "/favicon.ico",
            "/images/**",
            "/css/**",
            "/svelte/**",
            "/ws",
            "/login/failure"
    };
    private static final String[] SWAGGER_WHITELIST = {
            "/documentation/swagger-ui/",
            "/documentation/swagger-ui/*",
            "/documentation/swagger-resources/**",
            "/v3/api-docs"
    };
    private static final String[] ADMIN_WHITELIST = {"/admin", "/admin/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/accessDenied").and()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers(ADMIN_WHITELIST).hasRole("ADMIN")
                .anyRequest().authenticated().and()

                .formLogin(customize -> {
                    customize.loginPage("/login");
                    customize.permitAll();
                })

                .logout(customize -> {
                    customize.logoutSuccessUrl("/");
                    customize.permitAll();
                })

                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}