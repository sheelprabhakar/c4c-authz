package com.c4c.auth.configs;

import com.c4c.auth.core.services.UserServiceImpl;
import com.c4c.auth.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

  /**
   * The constant AUTH_WHITELIST.
   */
  private static final String[] AUTH_WHITELIST =
      {"/swagger-resources", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
          "/v3/api-docs/**", "/actuator/**", "/swagger-ui/**", "/auth/*", "/token/*",
          "/api/v1/auth/authenticate", "/api/v1/auth/refreshToken", "/error", "/", "/uploads/**",
          "favicon.ico"};
  private final JwtTokenUtil jwtTokenUtil;

  private final AuthEntryPoint unauthorizedHandler;
  @Autowired
  private UserServiceImpl userServiceImpl;
  private AuthenticationManager authManager;
  /**
   * The Security debug.
   */
  @Value("${spring.security.debug:false}")
  private boolean securityDebug;

  public WebSecurityConfig(AuthEntryPoint unauthorizedHandler, JwtTokenUtil jwtTokenUtil) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    var provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(provider);
  }

  @Bean
  public AuthenticationFilter authenticationTokenFilterBean() throws Exception {
    return new AuthenticationFilter(userServiceImpl, jwtTokenUtil);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable()).authorizeRequests().requestMatchers(AUTH_WHITELIST)
        .permitAll().anyRequest().authenticated().and()
        .httpBasic(hbc -> hbc.authenticationEntryPoint(unauthorizedHandler)).sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(authenticationTokenFilterBean(),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * Web security customizer web security customizer.
   *
   * @return the web security customizer
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.debug(securityDebug).ignoring().requestMatchers(AUTH_WHITELIST)
        .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
  }

  /**
   * Cors configurer web mvc configurer.
   *
   * @return the web mvc configurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }
}