package com.cristianml.DAOvsDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaOvsDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaOvsDtoApplication.class, args);

		// Diferencias entre DTO y DAO

		// DTO (Data Transfer object)
		// Este patrón se utiliza principalmente para transferir datos entre software, componentes o capas.

		// 1. Simplicidad: Sirven únicamente para el transporte de datos, sin contener lógica de negocio
		// o comportamiento complejo.

		// 2. Planos: Poseen propiedades básicas y accesibles a través de métodos simples como getters y setters.

		// 3. Serializable: Pueden ser transformados a formatos como JSON o XML para su transmisión entre
		// procesos o servicios.

		// DAO (Data Access Object)
		// Este patrón es un componente de software que proporciona una interfaz abstracta para algún tipo de base
		// de datos o mecanismo de persistencia.

		// 1. Abstracción: Separan las operaciones de la base de datos de la lógica de negocio.

		// 2. Encapsulamiento: Centralizan el acceso de datos, ocultando los detalles de la base de datos.

		// 3. Reusabilidad y Mantenibilidad: Promueven la eficiencia al gestionar datos, facilitando actualizaciones
		// y mantenimiento.
	}

}
