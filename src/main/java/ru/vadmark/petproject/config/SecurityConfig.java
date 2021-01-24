package ru.vadmark.petproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vadmark.petproject.config.jwt.JWTAuthenticationFilter;
import ru.vadmark.petproject.config.jwt.JWTSecurityContextRepository;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RequiredArgsConstructor
@EnableWebSecurity/*(debug = true)*/
// @EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTSecurityContextRepository securityContextRepository;
    private static final String[] WHITELIST = {
            "/",
            "/favicon.ico",
            "/images/**",
            "/css/**",
            "/js/**",
            "/svelte/**",
            "/ws",
            "/login/failure",
            "/auth"
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .securityContext().securityContextRepository(securityContextRepository).and()
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/accessDenied").and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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

                .addFilterBefore(new JWTAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
