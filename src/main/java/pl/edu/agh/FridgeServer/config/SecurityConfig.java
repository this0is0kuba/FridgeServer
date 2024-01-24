package pl.edu.agh.FridgeServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.edu.agh.FridgeServer.config.customeAuthentication.*;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(FridgeService fridgeService) {

        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();

        dap.setUserDetailsService(fridgeService);
        dap.setPasswordEncoder(passwordEncoder());

        return dap;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests( configurer ->
                configurer
                        .requestMatchers(HttpMethod.POST, "/processRegistrationForm").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin( form ->
                        form
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler(authenticationSuccessHandler())
                                .failureHandler(authenticationFailureHandler())
                                .permitAll()
                )
                .logout( logout ->
                        logout
                                .permitAll()
                                .logoutSuccessHandler(logoutSuccessHandler())
                )
                .exceptionHandling( exception ->
                        exception
                                .accessDeniedHandler(accessDeniedHandler())
                                .authenticationEntryPoint(authenticationEntryPoint())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomeAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
