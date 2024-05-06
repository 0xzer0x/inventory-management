package io.fursan.inventorymanagement.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private HttpSecurity useCustomAuthentication(HttpSecurity http) throws Exception {
    return http.formLogin(form -> form.loginPage("/login").permitAll());
  }

  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Order(1)
  public SecurityFilterChain unauthenticatedFilterChain(HttpSecurity http) throws Exception {
    http.securityMatchers(
            matcher -> matcher.requestMatchers("/login", "/webjars/**", "/static/**", "/error"))
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
    return useCustomAuthentication(http).build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain logoutFilterChain(HttpSecurity http) throws Exception {
    http.securityMatchers(matcher -> matcher.requestMatchers("/logout"))
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .csrf(AbstractHttpConfigurer::disable);
    return useCustomAuthentication(http).build();
  }

  @Bean
  @Order(3)
  public SecurityFilterChain h2ConsoleFilterChain(HttpSecurity http) throws Exception {
    http.securityMatchers(matcher -> matcher.requestMatchers("/h2-console/**"))
        .authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .csrf(AbstractHttpConfigurer::disable);
    return useCustomAuthentication(http).build();
  }

  @Bean
  @Order(4)
  public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authorize ->
            authorize
                .requestMatchers(HttpMethod.GET, "/", "/items", "/suppliers")
                .authenticated()
                .requestMatchers("/items/save", "/suppliers/save")
                .hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers("/items/delete", "/suppliers/delete")
                .hasRole("ADMIN"));
    return useCustomAuthentication(http).build();
  }
}
