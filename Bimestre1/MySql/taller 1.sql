create database Proyecto_2017379;
use Proyecto_2017379


create table Presupuesto (
codigoPresupuesto int not null auto_increment,
fechaSolicitud date,
cantidadPresupuesto decimal (10,2) not null,
codigoEmpresa int not null auto_increment,
primary key (codigoPresupuesto)
);


CREATE TABLE Empresas (
    codigoEmpresa INT NOT NULL AUTO_INCREMENT,
    nombreEmpresa VARCHAR(150) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    PRIMARY KEY (codigoEmpresa)
);


create table Servicios (
codigoServicio int not null auto_increment,
fechaServicio date,
tipoServicio varchar (100) not null,
horaServicio time,
lugarServicio varchar (100) not null,
telefonoContacto varchar (10),
codigoEmpresa int not null,
foreign key;
);


CREATE TABLE Servicios_has_Empleados (
    servicios_codigoServicio INT NOT NULL AUTO_INCREMENT,
    empleados_codigoEmpleado INT NOT NULL AUTO_INCREMENT,
    fechaEvento DATE,
    horaevento TIME,
    lugarEvento VARCHAR(150) NOT NULL
);


CREATE TABLE Empleados (
    codigoEmpleado INT NOT NULL AUTO_INCREMENT,
    numeroEmpleado INT NOT NULL AUTO_INCREMENT,
    apellidosEmpleado VARCHAR(150) NOT NULL,
    nombresEmpleados VARCHAR(150) NOT NULL,
    direccionEmpleado VARCHAR(150) NOT NULL,
    telefonoContacto VARCHAR(10),
    gradoCocinero VARCHAR(50) NOT NULL,
    codigoTipoEmpleado INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (codigoEmpleado)
);


CREATE TABLE tipoEmpleado (
    codigoTipoEmpleado INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    PRIMARY KEY (codigoTipoEmpleado)
);


CREATE TABLE Servicios_has_platos (
    Servicios_CodigoServicio INT NOT NULL AUTO_INCREMENT,
    platos_CodigoPlatos INT NOT NULL AUTO_INCREMENT
);


CREATE TABLE Platos (
    codigoPlato INT NOT NULL AUTO_INCREMENT,
    cantidad INT NOT NULL AUTO_INCREMENT,
    nombrePlato VARCHAR(50),
    descripcionplato VARCHAR(150),
    precioPlato DECIMAL(10 , 2 ),
    codigoTipoPlato INT NOT NULL AUTO_INCREMENT
	foreign key
);


CREATE TABLE TipoPlato (
    codigoTipoPlato INT NOT NULL AUTO_INCREMENT,
    descripcionTipo VARCHAR(100),
    primary key (codigoTipoPlato)
);


CREATE TABLE Productos_has_Platos (
    Producto_codigoProducto INT NOT NULL AUTO_INCREMENT,
    Platos_codigoPlato INT NOT NULL AUTO_INCREMENT
);


CREATE TABLE Productos (
    codigoProducto INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(150),
    cantidad INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (codigoProducto)
);