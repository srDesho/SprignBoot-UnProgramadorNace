package com.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Esta será nuestra clase de utilería para JWT
@Component
public class JwtUtils {

    // Necesitaremos 2 cosas:
    // 1. Clave Privada.
    // 2. UserGenerator: que será el usuario generador del token.

    // Para la clave primaria, nos dirigimos a una página que genere hash a nuestra clave, ejemplo keycdn generato SHA256
    // generamos el hash copiamos y la asignamos en una variable en nuestro aplication.properties con cualquier nombre.

    @Value("${security.jwt.key.private}")
    private String privateKey;

    // Estos atributos son para garantizar algo nuestra autenticidad de nuestro token.

    @Value("${security.jwt.user.generator}")
    private String userGenerator;


}
