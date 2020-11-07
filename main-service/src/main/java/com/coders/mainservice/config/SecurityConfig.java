package com.coders.mainservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        User.UserBuilder users = User.builder();

        auth.inMemoryAuthentication()
                .withUser(users.username("John").password(passwordEncoder.encode("test123")).roles("API_CLIENT"))
                .withUser(users.username("Mary").password(passwordEncoder.encode("test123")).roles("API_CLIENT"))
                .withUser(users.username("Tom").password(passwordEncoder.encode("admin123")).roles("API_CLIENT", "ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()

                .authorizeRequests()
                .antMatchers("/api").hasAnyRole("API_CLIENT", "ADMIN")
                .antMatchers("/all-records").hasAnyRole("API_CLIENT", "ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/api", true)
                .passwordParameter("password")
                .usernameParameter("username")
                .and()

                .logout()
                .logoutUrl("logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");

    }
}
