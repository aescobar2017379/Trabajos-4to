DROP DATABASE DBSportStore2017379;
CREATE DATABASE DBSportStore2017379;
USE DBSportStore2017379;

create table categorias(
CodigoCategoria int not null auto_increment,
descripcion varchar (150) not null,
Primary Key PK_CodigoCategoria (CodigoCategoria)
);

create table marcas(
CodigoMarca int not null auto_increment,
descripcion varchar (150) not null,
Primary Key PK_CodigoMarca (CodigoMarca)
);

create table tallas(
CodigoTalla int not null auto_increment,
descripcion varchar (150) not null,
Primary Key PK_CodigoTalla (CodigoTalla)
);

create table productos(
codigoProducto int not null auto_increment,
descripcion varchar (150) not null,
existencia int not null,
precioUnitario decimal (10,2) not null default 0.00,
precioPorDocena decimal (10,2) not null default 0.00,
precioPorMayor decimal (10,2) not null default 0.00,
CodigoCategoria int not null,
CodigoMarca int not null,
CodigoTalla int not null,
primary key PK_codigoProducto (codigoProducto),
constraint FK_productos_categorias foreign key (CodigoCategoria) references categorias(CodigoCategoria),
constraint FK_productos_marcas foreign key (CodigoMarca) references marcas (CodigoMarca),
constraint Fk_productos_tallas foreign key (CodigoTalla) references tallas (CodigoTalla)
);

Delimiter $$
	CREATE PROCEDURE sp_AgregarCategoria(in despripcion varchar (150))
		begin 
			insert into categorias (descripcion) values (descripcion);
		End $$
Delimiter ;

call sp_AgregarCategoria('Ropa de caballero');

select * from categorias;
Delimiter $$ 
	CREATE PROCEDURE sp_Listarcategorias()
		begin
			select 
				C.CodigoCategoria,
                C.descripcion
                From categorias C;
		End $$
Delimiter ;

call sp_Listarcategorias();


Delimiter $$
	CREATE PROCEDURE sp_BuscarCategoria(in CodCat int)
		begin
			select 
				C.CodigoCategoria,
                C.descripcion
				From Categorias C where CodigoCategoria = CodCat;
		End $$
Delimiter ;
call sp_BuscarCategoria(1);

Delimiter $$ 
	CREATE PROCEDURE sp_EliminarCategoria(in CodCat int)
		begin
			Delete From Categorias
				where CodigoCategoria = CodCat;
		End $$
Delimiter ;
call sp_EliminarCategoria(2);


Delimiter $$
	CREATE PROCEDURE sp_EdiarCategoria(in CodCat int, descr varchar (150))
		begin 
			update Categorias C
				Set descripcion = descr
                where CodigoCategoria = CodCat;
		End $$
Delimiter ;

call sp_EditarCategoria(3,'Ropa de dama');