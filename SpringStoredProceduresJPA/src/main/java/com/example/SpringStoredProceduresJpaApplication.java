package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStoredProceduresJpaApplication {

	// Procedimientos almacenados.

	// Los procedimientos almacenados (o "stored procedures" en inglés) son conjuntos de instrucciones SQL precompiladas
	// que se almacenan en la base de datos y se ejecutan en el servidor. Sirven para encapsular operaciones complejas
	// en una única llamada, mejorando la eficiencia y la seguridad. Se utilizan para realizar tareas repetitivas,
	// manejar transacciones, y aplicar lógica empresarial en la base de datos, reduciendo el tráfico de red y
	// centralizando la lógica de negocio, lo que facilita el mantenimiento y la consistencia de los datos.

	public static void main(String[] args) {
		SpringApplication.run(SpringStoredProceduresJpaApplication.class, args);
	}

}
