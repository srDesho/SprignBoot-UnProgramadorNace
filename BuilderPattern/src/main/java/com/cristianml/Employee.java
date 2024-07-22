package com.cristianml;

import com.cristianml.domain.Address;
import com.cristianml.domain.Contact;
import com.cristianml.domain.Phone;

import java.util.List;

public class Employee {

    private int age;
    private String name;
    private String gender;
    private Address address;
    private List<Phone> phoneList;
    private List<Contact> contactList;

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
