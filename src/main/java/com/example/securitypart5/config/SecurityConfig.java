package com.example.securitypart5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()


                .and()
                .authorizeRequests()
                //.anyRequest().authenticated()//endpoint level authorization
               // .anyRequest().permitAll()
               // .anyRequest().denyAll()
              //  .anyRequest().hasAuthority("read")//just user with authority 'read' has access
               // .anyRequest().hasAnyAuthority("read","write")// both users have access
               // .anyRequest().hasRole("ADMIN")
              //  .anyRequest().hasAnyRole("ROLE_ADMIN","ROLE_MANAGER")
                .anyRequest().access("isAuthenticated() and hasAuthority('read')")// return true or false
                //.mvcMatchers("/demo").hasAuthority("read")??
               // .anyRequest().authenticated()

                .and()

                .build();

        //matcher method + authorization rule
        //1 which matcher methods should you use and how (anyRequest(),mvcMatchers(),antMatchers(),regexMatchers())
        //2 how to apply different authorization
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var usd = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                //.roles("ADMIN") two options records
               // .authorities("ROLE_ADMIN")//equivalent  .roles("ADMIN")
                .build();


        var u2 = User.withUsername("john")
                .password(passwordEncoder().encode("12345"))
                //.authorities("ROLE_MANAGER")
                .authorities("write")
                .build();

        usd.createUser(u1);
        usd.createUser(u2);


        return usd;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
