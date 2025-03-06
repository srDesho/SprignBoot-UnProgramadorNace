package com.cristianml.config;

import com.cristianml.config.filter.JwtTokenValidator;
import com.cristianml.service.UserDetailsServiceImpl;
import com.cristianml.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

// Creamos primero que todo esta clase para la configuración de Spring security

@Configuration // Decimos a spring que esta clase es de configuración.
@EnableWebSecurity // Habilitamos la seguridad web
@EnableMethodSecurity // Con esta hacemos configuraciones con anotaciones.
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    // Lo segundo es configurar el SecurityFilterChain como un Bean
    @Bean
// El HttpSecurity es el que nos permite pasar todos los filtros y gracias a él podemos personalizar nuestra config.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Deshabilita CSRF para APIs REST stateless.
                .csrf(csrf -> csrf.disable())
                // Configura autenticación básica por defecto.
                .httpBasic(Customizer.withDefaults())
                // Configura sesión stateless.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura endpoints públicos/privados.
                .authorizeHttpRequests(http -> {
                    // Permite acceso a /auth/get sin login.
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    // Requiere permisos CREATE/READ para /auth/post.
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAnyAuthority("CREATE", "READ");
                    // Requiere rol DEVELOPER para /auth/patch.
                    http.requestMatchers(HttpMethod.PATCH, "/method/patch").hasAnyRole("DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/method/get").hasRole("INVITED");
                    // Bloquea endpoints no definidos.
                    http.anyRequest().denyAll();
                    // Requiere autenticación para endpoints no definidos (alternativa).
                    // http.anyRequest().authenticated();
                })
                // Aquí es donde debemos agregar nuestro filtro token que configuramos.
                // .addFilterBefore para que este se ejecute antes del filtro BasicAuthenticationFilter
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
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
