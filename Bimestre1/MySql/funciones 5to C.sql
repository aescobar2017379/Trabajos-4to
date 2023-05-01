create database DB_Ejemplo2017379;
use DB_Ejemplo2017379;

CREATE TABLE Datos (
    codigoDato INT NOT NULL AUTO_INCREMENT,
    num1 INT NOT NULL,
    num2 INT NOT NULL,
    suma INT,
    resta INT,
    multiplicacion INT,
    division INT,
    PRIMARY KEY PK_codigoDato (codigoDato)
);

delimiter $$
	create procedure sp_AgregarDato(in num1 int, in num2 int)
		begin 
			insert into Datos (num1, num2)
				values (num1, num2);
		end $$
delimiter ;

call sp_AgregarDato (3, 5);

delimiter $$
	create procedure sp_ListarDatos ()
		begin
			select 
				D.num1,
                D.num2,
                D.suma,
                D.resta,
                D.multipmicacion,
                D.division
              
				From Datos D;
		end$$
delimiter ;

delimiter $$
	create function fn_Sumatoria(valor1 int, valor2 int)
		returns int
        reads sql data deterministic 
		begin
			declare result int;
            set result = valor1 + valor2;
            return result;
        end$$
delimiter $$

select fn_sumatoria(10, 5) as resultado;
select fn_Sumatoria(D.num1, D.num2) from Datos D;

update Datos D
	set suma = fn_Sumaroria(D.num1, D.num2)
		where codigoDato = 1;
                
delimiter $$
	create function fn_Restar(valor1 int, valor2 int)
		returns int
        reads sql data deterministic
        begin	
			declare result int;
            set result = valor1 - valor2;
            return result;
		end$$
delimiter ;

SELECT FN_RESTAR(9, 3) AS resultado;


delimiter $$ 
	create function fn_Multiplicar(valor1 int, valor2 int)
		returns int 
        reads sql data deterministic 
        begin
			declare result int;
            set result = valor1 * valor2;
            return result;
		end $$
delimiter ;

SELECT FN_MULTIPLICAR(2, 10);

drop function fn_Dividir;
delimiter $$
	create function fn_Dividir(valor1 int, valor2 int)
		returns int
        reads sql data deterministic 
        begin
			declare result int;
            set result = valor1 DIV valor2;
            return result;
		end$$
delimiter ;

SELECT FN_DIVIDIR(100, 2);

drop function fn_Reloj;

delimiter $$
	create function fn_Reloj(Digito int)
		returns varchar (100)
        reads sql data deterministic 
        begin
			declare Horas int;
            declare Minutos int;
            declare Segundos int;
            declare Mensaje varchar (100);
            set Mensaje = '';
			set Horas = Digito div 3600;
            set Minutos = (Digito % 60)DIV 60;
            set Segundos = (Digito % 60)% 60;
            set Mensaje = concat(Horas,':',Minutos,':',Segundos);
            return Mensaje;
            
        end$$
delimiter ;

select fn_Reloj (3750);