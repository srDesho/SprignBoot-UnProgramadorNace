package com.cristianml;

import com.cristianml.domain.Address;
import com.cristianml.domain.Contact;
import com.cristianml.domain.Phone;

public class Main {
    public static void main(String[] args) {
        // Concepto
        // Builder es un patrón de diseño creacional que nos permite construir objetos complejos paso a paso.
        // El patrón nos permite producir distintos tipos y representaciones de un objeto empleando el mismo
        // código de construcción.

        // Este patrón es esencialmente útil cuando tenemos un objeto que se relaciona con diferentes objetos,
        // nos permite crear cada uno de esos objetos utilizando un solo código de construcción.

        // Hacemos ejemplo para probar nuestro builder
        // Agregamos los parámetros que deseemos ya que son opcionales porque de esa manera creamos nuestro
        // patrón builder perfectamente.
        Employee employee = new Employee
                .EmployeeBuilder()
                .setAge(26)
                .setName("Cristian")
                .setGender("Male")

                // .setAddress(new Address("Calle 9", "Medellín", "Colombia", "05030"))
                // Podemos setearle directamente con los atributos porque así lo definimos.
                .setAddress("Calle 9", "Medellín", "Colombia", "05030")

                // .setPhone(new Phone("1231", "1002", "Fijo"))
                .setPhone("1231", "1002", "Fijo")

                .setContact(new Contact("Daniel",
                        new Phone("8888", null, "Celular"),
                        new Address("Calle 45", "Bogota", "Country", "050934")))
                .build();

        System.out.println(employee);

    }
}