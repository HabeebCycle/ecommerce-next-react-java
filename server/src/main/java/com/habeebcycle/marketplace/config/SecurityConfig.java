package com.habeebcycle.marketplace.config;

import com.habeebcycle.marketplace.security.CustomUserDetailsService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import com.habeebcycle.marketplace.security.jwt.JwtAuthenticationEntryPoint;
import com.habeebcycle.marketplace.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(ApplicationConstants.GRAPHQL_ENDPOINTS).permitAll()
                    .antMatchers(ApplicationConstants.PUBLIC_ENDPOINTS).permitAll()
                    .antMatchers(ApplicationConstants.AUTH_ENDPOINTS).permitAll()
                    .antMatchers(ApplicationConstants.USER_AVAILABILITY_ENDPOINT,
                            ApplicationConstants.EMAIL_AVAILABILITY_ENDPOINT).permitAll()
                    .antMatchers(HttpMethod.GET, ApplicationConstants.PRODUCT_ENDPOINTS,
                            ApplicationConstants.PRODUCT_CATEGORY_ENDPOINTS, ApplicationConstants.BRAND_ENDPOINTS,
                            ApplicationConstants.BANNER_ENDPOINTS, ApplicationConstants.PROMOTION_ENDPOINTS,
                            ApplicationConstants.STORE_ENDPOINTS, ApplicationConstants.COLLECTION_ENDPOINTS,
                            ApplicationConstants.POST_ENDPOINTS, ApplicationConstants.GRAPHIQL_ENDPOINTS,
                            ApplicationConstants.GRAPHIQL_ENDPOINT, ApplicationConstants.SUBSCRIPTION_ENDPOINT)
                        .permitAll()
                    .anyRequest()
                        .authenticated();
        // Add the custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
