package org.eduardomango.clasespringsecurity.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eduardomango.clasespringsecurity.security.services.JwtService;
import org.eduardomango.clasespringsecurity.security.services.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtención del encabezado de autenticación
        // Se espera que el token JWT venga en el encabezado 'Authorization' con el prefijo 'Bearer '.
        // (Alternativamente, en una aplicación web tradicional, podría venir de una cookie).
        final String authHeader = request.getHeader("Authorization");

        // 2. Validación inicial del token
        // Si el encabezado 'Authorization' está ausente o no comienza con "Bearer ",
        // no hay un token JWT válido. En este caso, se permite que la solicitud continúe
        // a los siguientes filtros (por ejemplo, para recursos públicos o autenticación alternativa).
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            //Si al llegar al filtro de autorizacion, no se tiene una autenticacion valida
            //Y el endpoint no es publico, se rechaza automaticamente.
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extracción del token JWT
        // Se extrae la cadena del token JWT eliminando el prefijo "Bearer ".
        final String jwt = authHeader.substring(7);
        // 4. Extracción del nombre de usuario del token
        // Se utiliza el servicio JWT para obtener el nombre de usuario (el 'subject' del token).
        final String username = jwtService.extractUsername(jwt);

        // 5. Verificación del contexto de seguridad
        // Se obtiene la autenticación actual del SecurityContextHolder.
        // Esto es importante para no sobrescribir una autenticación ya existente
        // (por ejemplo, si otro filtro ya autenticó al usuario o si ya existe una sesión activa).
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // 6. Si el usuario existe en el token y NO hay una autenticación previa
        // Se procede a autenticar al usuario solo si se encontró un nombre de usuario en el token
        // Y si el usuario no ha sido autenticado previamente en el contexto de seguridad actual.
        if (username != null && authentication == null){

            // 7. Carga de los detalles del usuario
            // Se carga el UserDetails del usuario desde el servicio de usuario,
            // lo que incluye sus roles y el estado de su cuenta (habilitado, no bloqueado, etc.).
            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(username);

            // 8. Verificación de la validez del token
            // Se utiliza el servicio JWT para validar el token contra los detalles del usuario,
            // comprobando que el usuario en el token coincide y que el token no ha expirado.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 9. Creación del objeto de autenticación
                // Si el token es válido, se crea un objeto UsernamePasswordAuthenticationToken.
                // Las credenciales (el segundo parámetro) son nulas porque ya autenticamos
                // con el token; no necesitamos la contraseña original aquí.
                // Se incluyen las autoridades (roles) del usuario.
                UsernamePasswordAuthenticationToken authToken = new
                        UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 10. Añadir detalles de la solicitud a la autenticación
                // Se añaden detalles adicionales de la solicitud (como la dirección IP remota)
                // al objeto de autenticación para un mejor registro o auditoría.
                authToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));

                // 11. Almacenar la autenticación en el contexto de seguridad
                // Se establece el objeto de autenticación en el SecurityContextHolder.
                // Esto significa que, para el resto de la vida de esta solicitud,
                // Spring Security considerará a este usuario como autenticado.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 12. Continuar con la cadena de filtros
        // Se pasa la solicitud y la respuesta al siguiente filtro en la cadena de Spring Security.
        // Si la autenticación fue exitosa, el SecurityContext ya contiene la información del usuario.
        filterChain.doFilter(request, response);
    }
}
