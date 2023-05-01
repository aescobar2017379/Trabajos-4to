drop database if exists DBTonysKinal_2017379;

create database DBTonysKinal_2017379;
use DBTonysKinal_2017379;

create table Productos(
	codigoProducto int auto_increment not null,
    nombreProducto varchar(150) not null,
    cantidad int not null,
    primary key PK_codigoProducto(codigoProducto)	
);

create table TipoEmpleado(
	codigoTipoEmpleado int auto_increment not null,
    descripcion varchar(100),
    primary key PK_codigoTipoEmpleado(codigoTipoEmpleado)
);

create table Empleados(
	codigoEmpleado int auto_increment not null,
    numeroEmpleado int not null,
    apellidosEmpleado varchar(150) not null,
    nombresEmpleado varchar(150) not null,
    direccionEmpleado varchar(150) not null,
    telefonoContacto varchar(10) not null,
    gradoCocinero varchar(50) not null,
    codigoTipoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_Empleados_TipoEmpleado foreign key (codigoTipoEmpleado) 
    references tipoEmpleado(codigoTipoEmpleado)
);

create table TipoPlato(
	codigoTipoPlato int auto_increment not null,
    descripcionTipo varchar(100) not null,
    primary key PK_codigoTipoPlato(codigoTipoPlato)
);

create table Platos(
	codigoPlato int auto_increment not null,
    cantidad int not null,
    nombrePlato varchar(50),
    descripcionPlato varchar(150) not null,
    precioPlato decimal(10,2),
    codigoTipoPlato int not null,
    primary key PK_codigoPlato(codigoPlato),
    constraint FK_Platos_TipoPlato foreign key (codigoTipoPlato)
    references TipoPlato(codigoTipoPlato)
);

create table Empresas(
	codigoEmpresa int auto_increment not null,
    nombreEmpresa varchar(150) not null,
    direccion varchar(150) not null,
    telefono varchar(10) not null,
    primary key PK_codigoEmpresa(codigoEmpresa)
);

create table Presupuesto(
	codigoPresupuesto int auto_increment not null,
    fechaSolicitud date,
    cantidadPresupuesto decimal(10,2),
    codigoEmpresa int not null,
    primary key PK_codigoPresupuesto(codigoPresupuesto),
    constraint FK_Presupuesto_Empresas foreign key (codigoEmpresa)
    references Empresas(codigoEmpresa)
);

create table Servicios(
	codigoServicio int auto_increment not null,
    fechaServicio date,
    tipoServicio varchar(100),
    horaServicio time,
    lugarServicio varchar(100),
    telefonoContacto varchar(10),
    codigoEmpresa int not null,
    primary key PK_codigoServicio(codigoServicio),
    constraint FK_Servicios_Empresas foreign key (codigoEmpresa)
    references Empresas(codigoEmpresa)
);

CREATE TABLE Servicios_has_Empleados(
	codigoServicioEmpleado INT auto_increment NOT NULL,
	codigoServicio INT NOT NULL,
    codigoEmpleado INT NOT NULL,
	CONSTRAINT FK_Servicios_Servicios_has_Empleados
		FOREIGN KEY (codigoServicio) REFERENCES Servicios(codigoServicio),
	CONSTRAINT FK_Empleados_Servicios_has_Empleados
		FOREIGN KEY (codigoEmpleado) REFERENCES Empleados(codigoEmpleado),
	fechaEvento DATE NOT NULL,
    horaEvento TIME NOT NULL,
    lugarEvento VARCHAR(150) NOT NULL,
    PRIMARY KEY PK_codigoServicioEmpleado(codigoServicioEmpleado)
);

CREATE TABLE Servicios_has_Platos(
	codigoServicio INT NOT NULL,
    codigoPlato INT NOT NULL,
	CONSTRAINT FK_Servicios_Servicios_has_Platos
		FOREIGN KEY (codigoServicio) REFERENCES Servicios(codigoServicio),
	CONSTRAINT FK_Platos_Servicios_has_Platos
		FOREIGN KEY (codigoPlato) REFERENCES Platos(codigoPlato)
);

CREATE TABLE Productos_has_Platos(
	codigoProducto INT NOT NULL,
    codigoPlato INT NOT NULL,
	CONSTRAINT FK_Productos_Productos_has_Platos
		FOREIGN KEY (codigoProducto) REFERENCES Productos(codigoProducto),
	CONSTRAINT FK_Platos_Productos_has_Platos
		FOREIGN KEY (codigoPlato) REFERENCES Platos(codigoPlato)
);
DELIMITER $$
	create procedure sp_ListarProductos()
    begin
		select codigoProducto,nombreProducto,cantidad from Productos;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarProducto(in _nombreProducto varchar(150),in _cantidad int)
    begin
		insert into Productos(nombreProducto,cantidad) 
        values(_nombreProducto,_cantidad);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarProducto(in _codigoProducto int,in _nombreProducto varchar(150),in _cantidad int)
    begin
		update Productos set 
        nombreProducto = _nombreProducto,
        cantidad= _cantidad
        where codigoProducto = _codigoProducto;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarProducto(in _codigoProducto int)
    begin
		delete from Productos where codigoProducto = _codigoProducto;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarProducto(in _codigoProducto int)
    begin
		select * from Productos where codigoProducto = _codigoProducto;
    end $$
DELIMITER ;

/**********************************************************************Tipo de Empleado********************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarTipoEmpleado()
    begin
		select codigoTipoEmpleado,descripcion from TipoEmpleado;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarTipoEmpleado(in _descripcion varchar(100))
    begin
		insert into TipoEmpleado(descripcion) 
        values(_descripcion);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarTipoEmpleado(in _codigoTipoEmpleado int,in _descripcion varchar(100))
    begin
		update TipoEmpleado set 
        descripcion = _descripcion 
        where codigoTipoEmpleado = _codigoTipoEmpleado;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarTipoEmpleado(in _codigoTipoEmpleado int)
    begin
		delete from TipoEmpleado where codigoTipoEmpleado = _codigoTipoEmpleado;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarTipoEmpleado(in _codigoTipoEmpleado int)
    begin
		select * from TipoEmpleado where codigoTipoEmpleado = _codigoTipoEmpleado;
    end $$
DELIMITER ;

/****************************************************************************Empleados********************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarEmpleados()
    begin
		select codigoEmpleado,numeroEmpleado,apellidosEmpleado,nombresEmpleado,direccionEmpleado,telefonoContacto,gradoCocinero,codigoTipoEmpleado
        from Empleados;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarEmpleado(in _numeroEmpleado int,in _apellidosEmpleado varchar(150),in _nombresEmpleado varchar(150),in _direccionEmpleado varchar(150),
    in _telefonoContacto varchar(10),in _gradoCocinero varchar(50),in _codigoTipoEmpleado int)
    begin
		insert into Empleados(numeroEmpleado,apellidosEmpleado,nombresEmpleado,direccionEmpleado,telefonoContacto,gradoCocinero,codigoTipoEmpleado)
        values(_numeroEmpleado,_apellidosEmpleado,_nombresEmpleado,_direccionEmpleado,_telefonoContacto,_gradoCocinero,_codigoTipoEmpleado);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarEmpleado(in _codigoEmpleado int,in _numeroEmpleado int,in _apellidosEmpleado varchar(150),in _nombresEmpleado varchar(150),in _direccionEmpleado varchar(150),
    in _telefonoContacto varchar(10),in _gradoCocinero varchar(50),in _codigoTipoEmpleado int)
    begin
		update Empleados set 
        numeroEmpleado = _numeroEmpleado,
        apellidosEmpleado = _apellidosEmpleado,
        nombresEmpleado = _nombresEmpleado,
        direccionEmpleado = _direccionEmpleado,
        telefonoContacto = _telefonoContacto,
        gradoCocinero = _gradoCocinero,
        codigoTipoEmpleado = _codigoTipoEmpleado
        where codigoEmpleado = _codigoEmpleado;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarEmpleado(in _codigoEmpleado int)
    begin
		delete from Empleados where codigoEmpleado = _codigoEmpleado;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarEmpleado(in _codigoEmpleado int)
    begin
		select * from Empleados where codigoEmpleado = _codigoEmpleado;
    end $$
DELIMITER ;

/***********************************************************************Tipo de Plato*********************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarTipoPlatos()
    begin
		select codigoTipoPlato,descripcionTipo from TipoPlato;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarTipoPlato(in _descripcionTipo varchar(100))
    begin
		insert into TipoPlato(descripcionTipo) values(_descripcionTipo);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarTipoPlato(in _codigoTipoPlato int,in _descripcionTipo varchar(100))
    begin
		update TipoPlato set 
        descripcionTipo = _descripcionTipo 
        where codigoTipoPlato = _codigoTipoPlato;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarTipoPlato(in _codigoTipoPlato int)
    begin
		delete from TipoPlato where codigoTipoPlato = _codigoTipoPlato;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarTipoPlato(in _codigoTipoPlato int)
    begin
		select * from TipoPlato where codigoTipoPlato = _codigoTipoPlato;
    end $$
DELIMITER ;

/********************************************************************Platos********************************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarPlatos()
    begin
		select codigoPlato,cantidad,nombrePlato,descripcionPlato,precioPlato,codigoTipoPlato from Platos;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarPlato(in _cantidad int,in _nombrePlato varchar(50),in _descripcionPlato varchar(150),in _precioPlato decimal(10,2), in _codigoTipoPlato int)
    begin
		insert into Platos(cantidad,nombrePlato,descripcionPlato,precioPlato,codigoTipoPlato)
        values(_cantidad,_nombrePlato,_descripcionPlato,_precioPlato,_codigoTipoPlato);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarPlato(in _codigoPlato int,in _cantidad int,in _nombrePlato varchar(50),in _descripcionPlato varchar(150),
    in _precioPlato decimal(10,2), in _codigoTipoPlato int)
    begin
		update Platos set 
        cantidad = _cantidad,
        nombrePlato = _nombrePlato,
        descripcionPlato = _descripcionPlato,
        precioPlato = _precioPlato,
        codigoTipoPlato = _codigoTipoPlato
        where codigoPlato = _codigoPlato;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarPlato(in _codigoPlato int)
    begin
		delete from Platos where codigoPlato = _codigoPlato;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarPlato(in _codigoPlato int)
    begin
		select * from Platos where codigoPlato = _codigoPlato;
    end $$
DELIMITER ;

/*************************************************************Empresas*************************************************************************************************/


DELIMITER $$
	create procedure sp_ListarEmpresa()
    begin
		select codigoEmpresa,nombreEmpresa,direccion,telefono from Empresas;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarEmpresa(in _nombreEmpresa varchar(150),in _direccion varchar(150),in _telefono varchar(10))
    begin
		insert into Empresas(nombreEmpresa,direccion,telefono) 
        values(_nombreEmpresa,_direccion,_telefono);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarEmpresa(in _codigoEmpresa int,in _nombreEmpresa varchar(150),in _direccion varchar(150),in _telefono varchar(10))
    begin
		update Empresas set 
        nombreEmpresa = _nombreEmpresa,
        direccion = _direccion,
        telefono = _telefono
        where codigoEmpresa = _codigoEmpresa;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarEmpresa(in _codigoEmpresa int)
    begin
		delete from Empresas where codigoEmpresa = _codigoEmpresa;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarEmpresa(in _codigoEmpresa int)
    begin
		select * from Empresas where codigoEmpresa = _codigoEmpresa;
    end $$
DELIMITER ;

/****************************************************************Presupuesto*******************************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarPresupuesto()
    begin
		select codigoPresupuesto,fechaSolicitud,cantidadPresupuesto,codigoEmpresa from Presupuesto;
    end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarPresupuesto(in _fechaSolicitud date,in _cantidadPresupuesto decimal(10,2),in _codigoEmpresa int)
    begin
		insert into Presupuesto(fechaSolicitud,cantidadPresupuesto,codigoEmpresa) 
        values(_fechaSolicitud,_cantidadPresupuesto,_codigoEmpresa);
    end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarPresupuesto(in _codigoPresupuesto int,in _fechaSolicitud date,in _cantidadPresupuesto decimal(10,2),in _codigoEmpresa int)
    begin
		update Presupuesto set 
        fechaSolicitud = _fechaSolicitud,
        cantidadPresupuesto = _cantidadPresupuesto,
        codigoEmpresa = _codigoEmpresa
        where codigoPresupuesto = _codigoPresupuesto;
    end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarPresupuesto(in _codigoPresupuesto int)
    begin
		delete from Presupuesto where codigoPresupuesto = _codigoPresupuesto;
    end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarPresupuesto(in _codigoPresupuesto int)
    begin
		select * from Presupuesto where codigoPresupuesto = _codigoPresupuesto;
    end $$
DELIMITER ;

/********************************************************************Servicios*****************************************************************************************/

/*Listar*/
DELIMITER $$
	create procedure sp_ListarServicios()
    begin
		select codigoServicio,fechaServicio,tipoServicio,horaServicio,lugarServicio,telefonoContacto,codigoEmpresa from Servicios;
	end $$
DELIMITER ;
/*Agregar*/
DELIMITER $$
	create procedure sp_AgregarServicio(in _fechaServicio date, in _tipoServicio varchar(100), in _horaServicio time, 
    in _lugarServicio varchar(100), in _telefonoContacto varchar(10), in _codigoEmpresa int)
    begin
		insert into Servicios(fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa)
        values (_fechaServicio, _tipoServicio, _horaServicio, _lugarServicio, _telefonoContacto, _codigoEmpresa);
	end $$
DELIMITER ;
/*Actualizar*/
DELIMITER $$
	create procedure sp_ActualizarServicio(in _codigoServicio int, in _fechaServicio date, in _tipoServicio varchar(100), in _horaServicio time, in _lugarServicio varchar(100), 
    in _telefonoContacto varchar(10), in _codigoEmpresa int)
    begin
		update Servicios set
        fechaServicio = _fechaServicio,
        tipoServicio = _tipoServicio,
        horaServicio = _horaServicio,
        lugarServicio = _lugarServicio,
        telefonoContacto =_telefonoContacto
        where codigoServicio = _codigoServicio;
	end $$
DELIMITER ;
/*Eliminar*/
DELIMITER $$
	create procedure sp_EliminarServicio(in _codigoServicio int)
		begin 
        delete from Servicios where codigoServicio = _codigoServicio;
        end $$
DELIMITER ;
/*Buscar*/
DELIMITER $$
	create procedure sp_BuscarServicio(in _codigoServicio int)
    begin
    select * from Servicios where codigoServicio = _codigoServicio;
    end $$
DELIMITER ;
 
 /**********************************************************Servicios_has_Empleado************************************************************************************/
 
 /*Agregar*/
DROP PROCEDURE IF EXISTS sp_agregarServicio_has_Empleado;
DELIMITER $$
CREATE PROCEDURE sp_agregarServicio_has_Empleado(
	IN codigoServicio INT,
    IN codigoEmpleado INT,
    IN fechaEvento DATE,
    IN horaEvento TIME,
    IN lugarEvento VARCHAR(100)
)
BEGIN
	INSERT INTO Servicios_has_Empleados(codigoServicio,codigoEmpleado,fechaEvento,horaEvento,lugarEvento) VALUES (codigoServicio,codigoEmpleado,fechaEvento,horaEvento,lugarEvento);
END $$
DELIMITER ;

/*Listar*/
DROP PROCEDURE IF EXISTS sp_listarServicios_has_Empleados;
DELIMITER $$
CREATE PROCEDURE sp_listarServicios_has_Empleados()
BEGIN
	select Servicios_has_Empleados.codigoServicioEmpleado,Servicios_has_Empleados.codigoServicio,Servicios_has_Empleados.codigoEmpleado,Servicios_has_Empleados.fechaEvento,Servicios_has_Empleados.horaEvento,
		Servicios_has_Empleados.lugarEvento FROM Servicios_has_Empleados;
END $$
DELIMITER ;



/*Buscar*/
DROP PROCEDURE IF EXISTS sp_buscarServicio_has_Empleado;
DELIMITER $$
CREATE PROCEDURE sp_buscarServicio_has_Empleado(
	IN codServEmpleado INT
)
BEGIN
	SELECT Servicio_has_Empleado.codigoServicio,Servicios_has_Empleados.codigoEmpleado,Servicios_has_Empleados.fechaEvento,Servicios_has_Empleados.horaEvento,
		Servicios_has_Empleados.lugarEvento FROM Servicios_has_Empleados WHERE codigoServicioEmpleado = codServEmpleado;
END $$
DELIMITER ;


/*Actualizar*/
DROP PROCEDURE IF EXISTS sp_actualizarServicios_has_Empleados;
DELIMITER $$
CREATE PROCEDURE sp_actualizarServicios_has_Empleados(
	IN codServEmpleado INT,
	IN codServicio INT,
    IN codEmpleado INT,
    IN feEvento DATE,
    IN hrEvento TIME,
    IN lugEvento VARCHAR(100)
)
BEGIN
	UPDATE Servicios_has_Empleados SET
		codigoServicio = codServicio,
		codigoEmpleado = codEmpleado,
        fechaEvento = feEvento,
        horaEvento = hrEvento,
        lugarEvento = lugEvento
    WHERE codigoServicioEmpleado = codServEmpleado; 
END $$
DELIMITER ;

/*Elimnar*/
DROP PROCEDURE IF EXISTS sp_eliminarServicio_has_Empleado;
DELIMITER $$
CREATE PROCEDURE sp_eliminarServicio_has_Empleado(
	IN codServEmpleado INT
)
BEGIN
	DELETE FROM Servicios_has_Empleados  WHERE codigoServicioEmpleado = codServEmpleado;
END $$
DELIMITER ;

/*****************************************************************Servicios_has_Platos*********************************************************************************/

/*Listar*/
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_listarServicios_has_Platos;
DELIMITER $$
	CREATE PROCEDURE sp_listarServicios_has_Platos()
		BEGIN
			select codigoServicio,codigoPlato FROM Servicios INNER JOIN Platos ON codigoServicio = CodigoPlato;
		END $$
        
DELIMITER ;

/*Agregar*/
DELIMITER $$
	CREATE PROCEDURE sp_agregarServicios_has_Plato(
		IN codigoServicio INT,
		IN codigoPlato INT
	)
	BEGIN
		INSERT INTO Servicios_has_Platos(codigoServicio,codigoPlato)
			VALUES (codigoServicio,codigoPlato);
	END $$
DELIMITER ;

/*******************************************************************Productos_has_Platos*******************************************************************************/

/*Listar*/
DELIMITER $$
CREATE PROCEDURE sp_listarProductos_has_Platos()
BEGIN
	SELECT codigoProducto,codigoPlato FROM Productos INNER JOIN Platos ON codigoProducto = codigoPlato;
END $$
DELIMITER ;
call sp_listarProductos_has_Platos();

/*Agregar*/
DELIMITER $$
CREATE PROCEDURE sp_agregarProductos_has_Platos(
	in codigoPlato INT,
	in codigoProducto INT
)
BEGIN
	INSERT INTO Productos_has_Platos (codigoPlato,codigoProducto) VALUES (codigoPlato,codigoProducto);
END $$
DELIMITER ;