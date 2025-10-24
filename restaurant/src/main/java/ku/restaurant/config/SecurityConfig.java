package ku.restaurant.config;


import ku.restaurant.security.JwtAuthFilter;
import ku.restaurant.security.UnauthorizedEntryPointJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UnauthorizedEntryPointJwt unauthorizedHandler;


    @Autowired
    public SecurityConfig(UnauthorizedEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }


    @Bean
    public JwtAuthFilter authenticationJwtTokenFilter() {
        return new JwtAuthFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (not needed for stateless JWT)
                .csrf(csrf -> csrf.disable())


                // Set session management to stateless
                .sessionManagement(sessionMnt -> sessionMnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Set unauthorized requests exception handler
                .exceptionHandling(exceptionHandling ->             exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )



                // Set permissions on endpoints
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Our public endpoints
                                .requestMatchers("/api/auth/**").permitAll()


                                // All other endpoints require authentication
                                .anyRequest().authenticated()
                );

        // Add the JWT Token filter
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // You can customize parameters for memory, iterations, parallelism
        // based on your security requirements and system resources.
        // Recommended values are at least 19 MiB memory, 2 iterations,
        // and 1 parallelism.
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        // Or specify custom parameters:
        // return new Argon2PasswordEncoder(16, 32, 1, 19 * 1024, 2);
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/h2-console/**");
    }
}
