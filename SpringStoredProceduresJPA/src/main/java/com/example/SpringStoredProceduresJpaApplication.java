package com.example;

import com.example.entity.Person;
import com.example.respository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Procedimientos almacenados.

// Los procedimientos almacenados (o "stored procedures" en inglés) son conjuntos de instrucciones SQL precompiladas
// que se almacenan en la base de datos y se ejecutan en el servidor. Sirven para encapsular operaciones complejas
// en una única llamada, mejorando la eficiencia y la seguridad. Se utilizan para realizar tareas repetitivas,
// manejar transacciones, y aplicar lógica empresarial en la base de datos, reduciendo el tráfico de red y
// centralizando la lógica de negocio, lo que facilita el mantenimiento y la consistencia de los datos.

@Slf4j // Esto nos sirve para que se muestren los logs.
// Inyectamos nuestro repositorio con Lombok con el método @RequiredArgsConstructor
@RequiredArgsConstructor
@SpringBootApplication
// Implementamos el CommandLineRunner que nos brindará un método para que se ejecute inicialmente.
public class SpringStoredProceduresJpaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringStoredProceduresJpaApplication.class, args);
	}

	// Inyectamos nuestro repositorio con lombok
	private final PersonRepository personRepository;

	@Override
	// Importamos el transactional de Springframework
	// Siempre que trabajemos con invocación de sp debe estar envuelto en un @Transactional
	@Transactional
	public void run(String... args) throws Exception {
		// Creamos una lista para hacer llamado de nuestra SP (Stored Procedure)
		List<Person> persons = this.personRepository.verPersonas();

		// Imprimimos de la siguiente manera.
		log.info("\n");
		persons.forEach(person -> log.info(person.toString()));
	}
}
