package com.example.backend;

import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override

            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.example.backend.entity.User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + username));

                return User.builder().username(username)
                        .password(user.getPassword()).
                        roles(String.valueOf(user.getRole())).build();
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .cors().and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/v1/auth/*").permitAll()
                .antMatchers(HttpMethod.GET, "/uploads/**","/api/v1/product/**").permitAll()
                .antMatchers("/h2-console/*").permitAll()
//                .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","SUPPLIER")
                .antMatchers(HttpMethod.POST,"/api/v1/orders/").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/orders/bySupplier/*").hasRole("SUPPLIER")
                .antMatchers(HttpMethod.GET,"/api/v1/orders/byCustomer/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET,"/api/v1/product/").hasAnyRole("ADMIN","SUPPLIER","USER")
                .anyRequest().authenticated();
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
//        return web -> web.ignoring().antMatchers("/h2-console","/api/v1/user/login");
//    }
}
