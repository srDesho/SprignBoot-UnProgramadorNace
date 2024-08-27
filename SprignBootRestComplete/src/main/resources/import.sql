-- Este archivo siempre se debe llamar import, porque se importará automáticamente nuestros datos.

INSERT INTO fabricantes(id, nombre) VALUES(1, 'Acer');
INSERT INTO fabricantes(id, nombre) VALUES(2, 'Dell');
INSERT INTO fabricantes(id, nombre) VALUES(3, 'Canon');
INSERT INTO fabricantes(id, nombre) VALUES(4, 'LG');
INSERT INTO fabricantes(id, nombre) VALUES(5, 'Western Digital');
INSERT INTO fabricantes(id, nombre) VALUES(6, 'Kingston');
INSERT INTO fabricantes(id, nombre) VALUES(7, 'MSI');
INSERT INTO fabricantes(id, nombre) VALUES(8, 'Sony');
INSERT INTO fabricantes(id, nombre) VALUES(9, 'OnePlus');

INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(1, 'Disco duro SATA3 2TB', 95.99, 5);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(2, 'Memoria RAM DDR4 16GB', 130, 6);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(3, 'Disco SSD 2 TB', 170.99, 4);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(4, 'GeForce RTX 2060', 295, 7);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(5, 'GeForce RTX 3080 Ti', 865, 6);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(6, 'Monitor 24 LED 4K UHD', 250, 1);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(7, 'Monitor 32 LED 4K UHD', 355.99, 1);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(8, 'Portátil XPS 15', 799, 2);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(9, 'Portátil Inspiron 14', 499, 2);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(10, 'Impresora Canon PIXMA G5020', 109.99, 3);
INSERT INTO productos(id, nombre, precio, id_fabricante) VALUES(11, 'Impresora Canon MAXIFY MB5420', 215, 3);