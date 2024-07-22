package com.cristianml;

// Esta interfaz es para crear nuestro patrón builder, va a ser un genérico para que pueda trabajar con cualquier tipo de objeto.
public interface IBuilder<T> {
    // Creamos el método build que será el que se encarga crear cada objeto y de unir cada objeto asociado.
    public T build();

}
