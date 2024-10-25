package com.eazybytes.eazyschool.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cglib.core.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.ignoringRequestMatchers("/saveMsg"))
        .authorizeHttpRequests(requests -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/","/home").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/contact").authenticated()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/about").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/assets/**").permitAll())
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(withDefaults());
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user= User.withDefaultPasswordEncoder().username("thiru").password("12345").roles("user").build();
        UserDetails admin= User.withDefaultPasswordEncoder().username("uma").password("12345").roles("user","admin").build();

        return new InMemoryUserDetailsManager(user,admin);


    }

}
