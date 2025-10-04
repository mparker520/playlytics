package com.mparker.playlytics.security;

// Imports
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    //<editor-fold desc="Security Config Constructor">

        private final AuthenticationConfiguration authenticationConfiguration;


        public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
            this.authenticationConfiguration = authenticationConfiguration;
        }


    //</editor-fold>

    //<editor-fold desc="CSRF Repository">

        @Bean
        public CsrfTokenRepository csrfTokenRepository() {
            return CookieCsrfTokenRepository.withHttpOnlyFalse();
        }


    //</editor-fold>

    //<editor-fold desc="Password Encoder">

        @Bean
        PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }


    //</editor-fold>

    //<editor-fold desc="Authentication Manager">

        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }


    //</editor-fold>

    //<editor-fold desc="Security Filter Chain">

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/login", "/create-account").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(f -> f.disable())
                .httpBasic(b -> b.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        })
                );


        return http.build();


    }
    //</editor-fold>

    //<editor-fold desc="CORS Configuration">

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200"));
            configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("Authorization","Content-Type","X-XSRF-TOKEN"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }


    //</editor-fold>

}
