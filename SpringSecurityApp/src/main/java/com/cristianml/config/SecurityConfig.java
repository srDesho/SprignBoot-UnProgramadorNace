package com.cristianml.config;

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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration // Le decimos a spring que será una clase de configuración.
@EnableWebSecurity // Habilitamos la seguridad web
@EnableMethodSecurity // Permite hacer configuraciones con antoción, aunque se puede omitir.
public class SecurityConfig {

    // Spring security nos dice que el primer componente que tenemos que configurar es el Security Filter Chain
    // Anotamos un bean para que Spring sepa que es un método que debe ser configurado y Manejado por él.
    /*@Bean
    // HttpSecurity es el objeto que se pasa por todos los filtros, o sea es la solicitud http request
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        // Configuramos las condiciones de seguridad
        return httpSecurity
                // .csrf en Spring Security se refiere a la protección contra los ataques de Cross-Site Request Forgery (CSRF).
                // CSRF es un tipo de ataque en el que un atacante engaña a los usuarios para que realicen acciones no deseadas
                // en una aplicación web en la que están autenticados.
                // Si estamos trabajando con ApisRest no necesitamos activarlo, así que lo desactivaremos.
                .csrf(csrf -> csrf.disable())
                // Definimos que se va a trabajar con una autorización básica de sólo usuario y contraseña.
                .httpBasic(Customizer.withDefaults())
                // Si trabajamos con aplicaciones web, debemos configurarlo con sesión sin estado para que no se cree un
                // objeto de sesión pesado, haremos que sea como en las páginas que cuando estás un tiempo sin dar señales
                // éste cierre sesión, o sea el stateless no nos va a guardar sesión en memoria, sino que va a depender
                // de la expiración del token.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Damos los permisos a nuestros endpoints
                .authorizeHttpRequests( http -> {
                    // Configurar endpoints públicos.
                    // Definimos que éste endpoint puede ser accesado por todos
                    http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();

                    // Configurar endpoints privados
                    // .hasAuthority indica que el endpoint tiene que tener una autorización.
                    http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAuthority("CREATE");

                    // Configurar el resto de endpoints no especificados
                    // Por ultimo hacemos que cualquier otro request diferente a los que no especificamos anteriormente
                    // denegamos el acceso con .anyRequest.denyAll o .anyRequest.authenticated
                    http.anyRequest().denyAll(); // Denega todo que no esté especificado, este es recomendable usar este.
                    // http.anyRequest().authenticated(); // Deja acceder a cualquiera que tenga registro, no es muy usado

                })
                .build();
    }*/

    // Hacemos lo siguiente para uso de anotaciones desde nuestro controlador
    @Bean
    // HttpSecurity es el objeto que se pasa por todos los filtros, o sea es la solicitud http request
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        // Configuramos las condiciones de seguridad
        return httpSecurity
                // .csrf en Spring Security se refiere a la protección contra los ataques de Cross-Site Request Forgery (CSRF).
                // CSRF es un tipo de ataque en el que un atacante engaña a los usuarios para que realicen acciones no deseadas
                // en una aplicación web en la que están autenticados.
                // Si estamos trabajando con ApisRest no necesitamos activarlo, así que lo desactivaremos.
                .csrf(csrf -> csrf.disable())
                // Definimos que se va a trabajar con una autorización básica de sólo usuario y contraseña.
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // Creamos nuestro componente AuthenticationManager que es el que se encargar de administrar la autenticación.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Definimos nuestro proveedor con AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // En este caso usaremos este proveedor Dao porque es el que se encarga de traer los datos de la la DB
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Seteamos los 2 componentes que requiere nuestro proveedor
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    // Definimos nuestro UserDetailsService y Creamos una lista de usuarios simulando que vienen de una base de datos
    // Éste método lo que hace es guardar el usuario en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("cristian")
                .password("123456")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());

        userDetailsList.add(User.withUsername("daniel")
                .password("123456")
                .roles("User")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    // Defiminos nuestro PaswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        // Retornamos NoOpPasswordEncoder que es el que no encripta y está deprecado pero en éste caso sólo será de prueba
        return NoOpPasswordEncoder.getInstance();
    }

}
