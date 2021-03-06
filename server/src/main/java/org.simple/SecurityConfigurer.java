package org.simple;


import org.simple.Helpers.JwtRequestFilter;
import org.simple.Helpers.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String STATIC_PATH = "/static/**";

    @Value("${application.authorization.enabled}")
    private Boolean authorizationEnabled;

    @Value("${application.authorization.redirect}")
    private String authorizationRedirect;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Boolean.FALSE.equals(authorizationEnabled)) {
            http.csrf()
                    .ignoringAntMatchers("/**")
                    .ignoringAntMatchers("/h2/**");
            http.headers().frameOptions().disable();

        } else {
             http.csrf().disable().authorizeRequests()
                     .antMatchers(
                             Controller.API_PREFIX + AuthController.LOGIN,
                             Controller.API_PREFIX + AuthController.SIGNUP,
                             AuthController.LOGIN,
                             AuthController.SIGNUP,
                             STATIC_PATH)
                     .permitAll()
                     .anyRequest().authenticated()
                     .and()
                     .formLogin().loginPage(authorizationRedirect);
             http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
         }
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
