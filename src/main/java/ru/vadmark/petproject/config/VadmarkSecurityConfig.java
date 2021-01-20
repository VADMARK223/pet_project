package ru.vadmark.petproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vadmark.petproject.config.jwt.JwtFilter;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RequiredArgsConstructor
@EnableWebSecurity/*(debug = true)*/
// @EnableGlobalMethodSecurity(securedEnabled = true)
public class VadmarkSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/favicon.ico",
            "/images/**",
            "/css/**",
            "/svelte/**",
            "/ws"
    };
    private static final String[] SWAGGER_WHITELIST = {
            "/documentation/swagger-ui/",
            "/documentation/swagger-ui/*",
            "/documentation/swagger-resources/**",
            "/v3/api-docs"
    };
    private static final String[] ADMIN_WHITELIST = {"/admin", "/admin/**"};
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
//                exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and().
        csrf().disable().
                exceptionHandling().accessDeniedPage("/accessDenied").and().
                authorizeRequests().
                antMatchers(AUTH_WHITELIST).permitAll().
                antMatchers(SWAGGER_WHITELIST).permitAll().
                antMatchers("/registration").not().fullyAuthenticated().
                antMatchers(ADMIN_WHITELIST).hasRole("ADMIN").
                anyRequest().authenticated().and().

                formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll().and().

                logout().logoutSuccessUrl("/").permitAll().and().

                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(...);
        return authenticationProvider;
    }*/
}
