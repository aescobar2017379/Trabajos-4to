drop database if exists DBKinalCare2017379;

Create database DBKinalCare2017379;

use DBKinalCare2017379;

create table Pacientes(
	codigoPaciente int not null,
    nombresPaciente varchar (50) not null,
    apellidosPaciente varchar (50)not null,
    sexo char not null,
    fechaNacimiento date not null,
    direccionPaciente varchar (100) not null,
    TelefonoPersonal varchar (8) not null,
    FechaPrimeraVisita date,
    primary key PK_codigoPaciente (codigoPaciente)
);

create table Especialidades(
	codigoEspecialidad int not null auto_increment,
    descripcion varchar (100) not null,
    primary key PK_codigoEspecialidad (codigoEspecialidad)
);

create table Medicamentos (
	codigoMedicamento int not null auto_increment,
    nombreMedicamento varchar (100) not null,
    primary key PK_codigoMedicamento (codigoMedicamento)
);

create table Doctores(
	numeroColegiado int,
    nombresDoctor varchar (50) not null,
    apellidosDoctor varchar (50) not null,
    telefonoContacto varchar (8) not null,
    codigoEspecialidad int not null,
    primary key PK_numeroColegiado (numeroColegiado),
    constraint FK_Doctores_Especialidades foreign key (codigoEspecialidad)
		references Especialidades (codigoEspecialidad)
    );
    
    create table Recetas (
		codigoReceta int not null auto_increment,
        fechaReceta date not null,
        numeroColegiado int not null,
        primary key PK_codigoReceta (codigoReceta),
        constraint FK_Recetas_Doctores foreign key (numeroColegiado)
			references Doctores (numeroColegiado)
    );
    
    create table DetalleReceta(
		codigoDetalleReceta int not null auto_increment,
        dosis varchar (100) not null,
        codigoReceta int not null,
        codigoMedicamento int not null,
        primary key PK_codigoDetalleReceta (codigoDetalleReceta),
        constraint FK_DetalleReceta_Receta foreign key (codigoReceta)
			references Recetas(codigoReceta),
		constraint FK_DetalleReceta_Medicamentos foreign key(CodigoMedicamento)
			references Medicamentos (codigoMedicamento)
    );
    
    CREATE TABLE Citas (
    codigoCita INT NOT NULL AUTO_INCREMENT,
    fechaCita DATE NOT NULL,
    horaCita TIME NOT NULL,
    tratamiento VARCHAR(150),
    descripCondActual VARCHAR(255) NOT NULL,
    codigoPaciente INT NOT NULL,
    numeroColegiado INT NOT NULL,
    PRIMARY KEY PK_codigoCita (codigoCita),
    CONSTRAINT FK_Citas_Pacientes FOREIGN KEY (codigoPaciente)
        REFERENCES Pacientes (codigoPaciente),
    CONSTRAINT FK_Citas_Doctores FOREIGN KEY (numeroColegiado)
        REFERENCES Doctores (numeroColegiado)
);
    
     -- -------------------------------------------------------------------
    -- ----------------------------Procedimientos Almacenados-------------
    -- ----------------------------Pacientes------------------------------
    -- ----------------------------Agregar CLiente------------------------
   
    Delimiter $$
		create procedure sp_AgregarPaciente(in codigoPaciente int, in nombresPaciente varchar (50),
        apellidosPaciente varchar (50), sexo char, fechaNacimiento date, direccionPaciente varchar (100), TelefonoPersonal varchar (8),
        FechaPrimeraVisita date)
        begin
			insert into Pacientes (codigoPaciente, nombresPaciente, apellidosPaciente, sexo, fechaNacimiento,
			direccionPaciente, TelefonoPersonal, fechaPrimeraVisita)
            values (codigoPaciente, nombresPaciente, apellidosPaciente, upper (sexo), fechaNacimiento,
			direccionPaciente, TelefonoPersonal, fechaPrimeraVisita);
      end $$      
	Delimiter ;
    call sp_AgregarPaciente(1001, 'Angel Gabriel', 'Escobar Arevalo', 'M', '2004-04-29', 'zona 5 proyecto 4/4', '42536871', '2008-06-20');
    
    
    
    -- ----------------------------Listar Pacientes----------------------------------
    Delimiter $$
		create procedure sp_ListarPacientes()
			begin
				select
				P.codigoPaciente,
                P.nombresPaciente,
                P.apellidosPaciente,
                p.sexo,
                P.fechaNacimiento,
                P.direccionPaciente,
                P.telefonoPersonal,
                P.fechaPrimeraVisita
                from Pacientes P;
			end$$
	Delimiter $$
    
    -- ---------------------------Buscar Paciente--------------------------------------
    
    Delimiter $$
		create procedure sp_BuscarPaciente(in CodPaciente int)
			begin
				select
                p.codigoPaciente,
                p.nombresPaciente,
                p.apellidosPaciente,
                p.sexo,
                p.fechaNacimiento,
                p.direccionPaciente,
                p.telefonoPersonal,
                p.fechaPrimeraVisita
			from Pacientes p
				where codigoPAciente = CodPaciente;
                
    end $$
    Delimiter ;
    
    call sp_BuscarPaciente (1001);
    
    -- ---------------------------Eliminar Paciente--------------------------------------
    Delimiter $$
		create procedure sp_EliminarPaciente (in codPaciente int)
				delete from Pacientes 
				where codigoPaciente = codPaciente;
		end $$
    Delimiter ;
    call sp_EliminarPaciente (1001);
    call sp_ListarPacientes();
 -- -------------------------------Editar Paciente---------------------------------------   
 Delimiter $$
 CREATE PROCEDURE sp_EditarPaciente (in codPaciente int, in nomPaciente varchar (50),
	in apPaciente Varchar (50), in sx char, in fechaNac date, in dirPaciente varchar(100),
    in telPersonal varchar (8), in fechaPV date)
    begin
		update Pacientes P
			set
				p.nombresPaciente = nomPaciente,
                p.apellidosPaciente = apPaciente,
                p.sexo = sx,
                p.fechaNacimiento = fechaNac,
                p.direccionPaciente = dirPaciente,
                p.telefonoPersonal = telPersonal,
                p.fechaPrimeraVisita = fechaPV
                where codigoPaciente = codPaciente;
		end $$
 Delimiter ;


-- ---------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------
-- --------------------------------- Agregar Especialidades--------------------------
Delimiter $$
	create procedure sp_AgregarEspecialidades(in codigoEspecialidad int, descripcion varchar (100))
    Begin
		insert into Especialidades (codigoEspecialidad, descripcion)
        values (codigoEspecialidad, descripcion);
        end $$
Delimiter ;

call sp_AgregarEspecialidades ();

-- -------------------------------- Listar especialidades-----------------------------
Delimiter $$
	create procedure sp_ListarEspecialidades()
    Begin 
		select
        E.codigoEspecialidad,
        E.descripccion
        from Especialidades E;
	end $$
Delimiter ;

-- ------------------------------- Buscar Especialidades------------------------------
Delimiter $$
	create procedure sp_BuscarEspecialidades (in codEspecialidad int)
    Begin 
		select 
        E.codigoEspecialidad,
        E.descripccion
        from Especialidades E
			where codigoEspecialidad = codEspecialidad;
	end $$
Delimiter ;
-- ------------------------------- Eliminar Especialidades -----------------------------

Delimiter $$
	create procedure sp_EliminarEspecialidades (in codEspecialidad int)
		delete from Especialidades 
			where codigoEspecialidade = codEspecialidad;
	end $$
Delimiter ;
-- --------------------------------- Editar Especialidades -----------------------------
drop procedure sp_editarEspecialidades;
Delimiter $$
	create procedure sp_EditarEspecialidades (in codEspecialidad int, descr varchar (100))
    begin 	
		update Especialidades E
			set 
			E.descripcion = descr
			where codigoEspecialidad = codEspecialidad;
	end $$
Delimiter ;


-- --------------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------------
-- ----------------------------------- Agregar Medicamentos------------------------------

Delimiter $$
	create procedure sp_AgregarMedicamentos (in codigoMedicamento int, in nombreMedicamento varchar (100))
    begin
		insert into Medicamentos (codigoMedicamento, nombreMedicamento)
        values (codigoMedicamento, nombreMedicamento);
	end $$
Delimiter ;

-- ----------------------------------- Listar Medicamentos ------------------------------

Delimiter $$
	create procedure sp_ListarMedicamentos ()
    begin 
		select 
        M.codigoMedicamento,
        M.nombreMedicamento
        from Medicamentos M;
	end $$
Delimiter ;
-- ------------------------------------ Buscar Medicamentos-------------------------------
drop procedure sp_BuscarMedicamentos;
Delimiter $$
	create procedure sp_BuscarMedicamentos(in codMedicamento int)
		select 
        M.codigoMedicamento,
        M.nombreMedicamento
		from Medicamentos M
			where codigoMedicamento = codMedicamento;
	End $$
Delimiter ;
-- ---------------------------------- Eliminar Medicamentos -------------------------------
Delimiter $$
	create procedure sp_EliminarMedicamentos(in codMedicamento int)
		delete from Medicamentos
			where codigoMedicamento = codMedicamento;
	End $$
Delimiter ;
-- -------------------------------- Editar Medicamentos ------------------------------------
Delimiter $$
	create procedure sp_EditarMedicamento (in codMedicamento int, nomMedicamento varchar (100))
		begin 
			update Medicamentos M
				set 
					M.nomMedicamento = nombreMedicamento
						where M.codigoMedicamento = codMedicamento;
	end $$
Delimiter ;
-- -----------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------
-- ---------------------------------- Agregar Doctores--------------------------------------
drop procedure sp_AgregarDoctores;
Delimiter $$
	create procedure sp_AgregarDoctores (in numeroColegiado int, nombresDoctor varchar (50), apellidosDoctor varchar (50), telefonoContacto varchar (8),
    codigoEspecialidad int)
    begin
		insert into Doctores (numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto)
			values (numeroColegiado, nombresDoctor, apellidosDoctor, telefonoContacto);
	end $$
Delimiter ;

-- -------------------------------- Listar Doctores------------------------------------------
Delimiter $$
	create procedure sp_ListarDoctores ()
		begin
			select
			D.numeroColegiado,
			D.nombresDoctor,
			D.apellidosDoctor,
			D.telefonoContacto,
			D.codigoEspecialidad
			from Doctores D;
	end $$
Delimiter ;
-- ------------------------------- Buscar Doctores--------------------------------------------
Delimiter $$
	create procedure sp_BuscarDoctores (in numColegiado int)
		begin 
			select 
			D.numeroColegiado,
			D.nombresDoctor,
			D.apellidosDoctor,
			D.telefonoContacto,
			D.codigoEspecialidad
			from Doctores D
				where numeroColegiado = numColegiado;
	end $$
Delimiter ;
-- ------------------------------- Eliminar Doctores------------------------------------------
Delimiter $$
	create procedure sp_EliminarDoctores (in numColegiado int)
		delete from Doctores
			where numeroColegiado = numColegiado;
	end $$
Delimiter ;
-- --------------------------------- Editar Doctores------------------------------------------

Delimiter $$
	create procedure sp_EditarDoctores (in numColegiado int, nomDoctor varchar (50), apeDoctor varchar (50), telContacto varchar (8), codiEspecialidad int)
		begin 
			update Doctores 
				set 
					nomDoctor = nombresDoctor,
                    apeDoctor = apellidosDoctor,
                    telContacto = telefonoContacto,
                    codiEspecialidad = codigoEspecialidad
						where numColegiado = numeroColegiado;
	end $$
Delimiter ;
-- -------------------------------------------------------------------------------------------
-- --------------------------------- Agregar Recetas ----------------------------------------

Delimiter $$
create procedure sp_AgregarRecetas (in codigoReceta int, fechaReceta date, numeroColegiado int)
	begin
		insert into Recetas (codigoReceta, fechaReceta, numeroColegiado)
			values (codigoReceta, fechaReceta, numeroColegiado);
	end $$
Delimiter ;
-- --------------------------------- Listar Recetas ----------------------------------------
Delimiter $$
	create procedure sp_ListarRecetas ()
		begin 
			select 
            R.codigoReceta,
            R.fechaReceta,
            R.numeroColegiado
            from Recetas R;
	end $$
Delimiter 
-- -------------------------------- Buscar Recetas -----------------------------------------
Delimiter $$
	create procedure sp_BuscarRecetas (in codReceta int)
		begin 
			select 
            R.codigoReceta,
            R.fechaReceta,
            R.numeroColegiado
            from Recetas R
				where R.codigoReceta = codReceta;
	end $$
Delimiter ;
-- ------------------------------- ELiminar Recetas -------------------------------------------
Delimiter $$
	create procedure sp_EliminarRecetas ()
	end $$
Delimiter ;