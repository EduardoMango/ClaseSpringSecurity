package org.eduardomango.clasespringsecurity.security.config;

import org.eduardomango.clasespringsecurity.security.filters.JwtAuthenticationFilter;
import org.eduardomango.clasespringsecurity.security.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // Configura las reglas de autorización para las solicitudes HTTP.
        http.authorizeHttpRequests(auth -> auth
                        // Permite el acceso sin autenticación a cualquier ruta que comience con "/auth/**".
                        // Esto es ideal para endpoints de login, registro, etc.
                        .requestMatchers("/auth/**").permitAll()
                        // Cualquier otra solicitud HTTP debe estar autenticada.
                        .anyRequest().authenticated())
                // Habilita la configuración por defecto de CORS (Cross-Origin Resource Sharing).
                // Esto es crucial para permitir que aplicaciones frontend en un dominio diferente
                // puedan comunicarse con esta API.
                .cors(Customizer.withDefaults())
                // Deshabilita la protección CSRF (Cross-Site Request Forgery).
                // Esto es común y seguro para APIs RESTful que son stateless (sin estado)
                // y utilizan tokens (como JWT) para la autenticación, ya que no dependen de sesiones.
                .csrf(AbstractHttpConfigurer::disable)
                // Configura las cabeceras HTTP, específicamente la cabecera X-Frame-Options.
                // Establece X-Frame-Options a 'sameOrigin', lo que evita que la página sea
                // incrustada en un iframe desde un dominio diferente, previniendo ataques de clickjacking.
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                // Configura la gestión de sesiones.
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                // Establece la política de creación de sesiones como STATELESS (sin estado).
                // Esto significa que Spring Security no creará ni usará sesiones HTTP
                // para almacenar el estado del usuario, lo cual es fundamental para APIs REST con JWT.
                // Agrega un filtro personalizado (jwtAuthenticationFilter) antes del
                // UsernamePasswordAuthenticationFilter. Esto asegura que nuestro filtro de JWT
                // se ejecute primero para validar el token y autenticar al usuario
                // antes de que Spring Security intente usar la autenticación basada en usuario/contraseña.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //Establece la clase encargada de manejar las excepciones que sean lanzadas.
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(restAuthenticationEntryPoint));

        // Construye y devuelve la cadena de filtros de seguridad configurada.
        return http.build();
    }
}
