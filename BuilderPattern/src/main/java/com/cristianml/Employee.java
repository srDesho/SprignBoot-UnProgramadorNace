package com.cristianml;

import com.cristianml.domain.Address;
import com.cristianml.domain.Contact;
import com.cristianml.domain.Phone;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private int age;
    private String name;
    private String gender;
    private Address address;

    // Instanciamos las listas para poder agregar elementos desde nuestro builder.
    private List<Phone> phoneList = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();

    public Employee() {
    }

    public Employee(int age, String name, String gender, Address address, List<Phone> phoneList, List<Contact> contactList) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phoneList = phoneList;
        this.contactList = contactList;
    }

    // Creamos una clase interna estática, una clase con el método build
    // la cuál implementamos a través de nuestra Interfaz IBuilder.

    public static class EmployeeBuilder implements IBuilder {

        //  Ponemos los mismos atributos definidos para el objeto
        private int age;
        private String name;
        private String gender;
        private Address address;
        private List<Phone> phoneList;
        private List<Contact> contactList;

        // Creamos un constructor vacío
        public EmployeeBuilder() {
        }

        // Creamos los métodos que nos permitirán agregar los atributos a esta clase EmployeeBuilder.
        // Esta clase nos permite crear objetos con los atributos que nosotros queramos, o sea no es obligatorio
        // que a la hora de crear un objeto le pasemos todos sus atributos.

        public EmployeeBuilder setAge(int age) {
            this.age = age;
            return this; // retornamos el objeto actual.
        }

        public EmployeeBuilder setName(String name) {
            this.name = name;
            return this; // retornamos el objeto actual.
        }

        public EmployeeBuilder setGender(String gender) {
            this.gender = gender;
            return this; // retornamos el objeto actual.
        }

        // Para setear objetos lo podemos hacer de 2 formas:
        // Pasándole el objeto como parámetro.
        public EmployeeBuilder setAddress(Address address) {
            this.address = address;
            return this; // retornamos el mismo objeto
        }

        // Pasándole como parámetros todos los atributos del objeto que necesitamos setear.
        public EmployeeBuilder setAddress(String address, String city, String country, String cp) {
            this.address = new Address(address, city, country, cp);
            return this;
        }

        // Pasando el objeto como parámetro
        public EmployeeBuilder setPhone(Phone phone) {
            this.phoneList.add(phone);
            return this; // retornamos el mismo objeto
        }

        // Pasando como parámetros todos los atributos del objeto que necesitamos setear.
        public EmployeeBuilder setPhone(String phoneNumber, String ext, String phoneType) {
            this.phoneList.add(new Phone(phoneNumber, ext, phoneType));
            return this; // retornamos el mismo objeto
        }

        // Pasando como parámetro el objeto que necesitamos setear.
        public EmployeeBuilder setContact(Contact contact) {
            this.contactList.add(contact);
            return this; // retornamos el mismo objeto
        }

        // Pasando como parámetros todos los atributos del objeto que necesitamos setear.
        public EmployeeBuilder setContact(String name, Phone phone, Address address) {
            this.contactList.add(new Contact(name, phone, address));
            return this; // retornamos el mismo objeto.
        }

        // Este método que se implementa desde nuestra interfaz debe retornar un objeto instanciado, en este caso un Employee
        @Override
        public Employee build() {
            return new Employee(age, name, gender, address, phoneList, contactList);
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "\n, age=" + age +
                "\n, name='" + name + '\'' +
                "\n, gender='" + gender + '\'' +
                "\n, address=" + address +
                "\n, phoneList=" + phoneList +
                "\n, contactList=" + contactList +
                '}';
    }
}
