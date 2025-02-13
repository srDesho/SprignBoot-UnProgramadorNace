package com.cristianml.config;

import com.cristianml.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

// Creamos primero que todo esta clase para la configuración de Spring security

@Configuration // Decimos a spring que esta clase es de configuración.
@EnableWebSecurity // Habilitamos la seguridad web
@EnableMethodSecurity // Con esta hacemos configuraciones con anotaciones.
public class SecurityConfig {

     // Lo segundo es configurar el SecurityFilterChain como un Bean
    @Bean
    // El HttpSecurity es el que nos permite pasar todos los filtros y gracias a él podemos personalizar nuestra config.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Protección contra CSRF (Cross-Site Request Forgery)

                // .csrf en Spring Security habilita la protección contra ataques CSRF.
                // CSRF es un tipo de ataque en el que un atacante intenta
                // que un usuario autenticado realice acciones no deseadas en una
                // aplicación web, aprovechando la sesión activa del usuario.

                // En APIs REST, donde la autenticación suele ser stateless
                // (mediante tokens), la protección CSRF no es necesaria y puede
                // incluso interferir con el funcionamiento normal de la API.
                // Por lo tanto, se desactiva la protección CSRF.
                .csrf(csrf -> csrf.disable())
                // Configuramos para que haga una autenticación básica(o sea sin token u otra) y que sea por defecto.
                .httpBasic(Customizer.withDefaults())
                // Configuramos el sessionManagement para que trabajemos con sesión sin estado(STATELESS).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Ahora configuramos los endpoints públicos
                .authorizeHttpRequests(http -> {
                    // Primero identificamos el tipo de método de solicitud(GET, POST, ETC...)
                    // Lo segundo es poner las rutas de los endpoints.
                    // permitAll() es para permitir el acceso a usuarios sin que estén logeados
                    http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();
                    // Configuramos endpoints privados
                    // .hasAuthority() es para definir qué usuarios pueden acceder depende de la autorización(permiso) que tienen.
                    http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyAuthority("CREATE", "READ");
                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyRole("DEVELOPER");

                    // Configura seguridad para peticiones no especificadas.

                    // Bloquea todo acceso a endpoints no definidos explícitamente (lista blanca).
                    http.anyRequest().denyAll();

                    // Requiere autenticación para endpoints no definidos explícitamente (lista negra).
                    // http.anyRequest().authenticated();
                })
                .build();
    }

    /*// Trabajaremos con anotaciones en el controlador y la configuración simplemente debe ir de la siguiente manera:
    @Bean
    // El HttpSecurity es el que nos permite pasar todos los filtros y gracias a él podemos personalizar nuestra config.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // Configuramos para que haga una autenticación básica(o sea sin token u otra) y que sea por defecto.
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/

    // Lo tercero es crear el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Lo cuarto es crear nuestro authentication provider(proveedor de autenticación)
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        // En este caso como trabajamos con credenciales de una base de datos pues haremos uso de DaoAuthenticatioProvider.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Seteamos las credenciales
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // Vamos a crear nuestros usuarios en memoria(luego lo hacemos con la db real).
    // Spring security sólo reconoce a los usuarios con el objeto UserDetails, o sea que cuando obtendremos registros
    // de la db pues debemos convertir al usuario normal en un userDetails.
    /*@Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("desho")
                .password("123456")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());
        userDetailsList.add(User.withUsername("damont")
                .password("123456")
                .roles("USER")
                .authorities("READ")
                .build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }*/

    // Creamos nuestro passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    // De esta manera podemos encriptar nuestras contraseñas.
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
    */

}
