package com.gladunalexander.config.security;

import com.gladunalexander.config.security.entrypoint.RequestAwareAuthenticationSuccessHandler;
import com.gladunalexander.config.security.entrypoint.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;
import java.security.SecureRandom;

/**
 * @author Alexander Gladun
 */
@Configuration
@EnableWebSecurity
public class MultiSecurityConfig {

    private static final String SALT = "fdalkjalk;3jlwf00sfaof";

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Configuration
    @Order(1)
    public static class RestSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{

        @Autowired
        private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/rest/**")
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/rest/users/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/rest/products/**" ).permitAll()
                    .antMatchers(HttpMethod.POST, "/rest/products/**" ).hasAnyRole("ADMIN","MANAGER")
                    .antMatchers(HttpMethod.DELETE, "/rest/products/**" ).hasAnyRole("ADMIN","MANAGER")
                    .antMatchers("/rest/order/add").authenticated()
                    .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                    .httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

        @Autowired
        @Qualifier("dataSource")
        private DataSource dataSource;

        @Bean
        public PersistentTokenRepository tokenRepository(){
            final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
            tokenRepository.setDataSource(dataSource);
            return tokenRepository;
        }

        @Bean
        public RequestAwareAuthenticationSuccessHandler mySuccessHandler(){
            return new RequestAwareAuthenticationSuccessHandler();
        }
        @Bean
        public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
            return new SimpleUrlAuthenticationFailureHandler();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/users/**").hasRole("ADMIN")
                    .antMatchers("/checkout/**", "/charge/**").authenticated()
                    .and()
                    .formLogin().loginPage("/login").successHandler(mySuccessHandler()).defaultSuccessUrl("/")
                    .failureHandler(myFailureHandler()).failureUrl("/login?error")
                    .and()
                    .logout()
                    .and()
                    .rememberMe().tokenRepository(tokenRepository()).tokenValiditySeconds(604800) //1 week
                    .and()
                    .exceptionHandling().accessDeniedPage("/login?denied");
        }
    }
}
