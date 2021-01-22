package com.yl.quartz.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig配置
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:34 上午
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/webjars/**",
                        "/resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui.html#!/**",
                        "/swagger-resources/**",
                        "/v2/api-docs")
                .permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().formLogin();

        http.csrf().disable();
    }
}
