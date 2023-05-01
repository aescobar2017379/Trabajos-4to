drop database if exist DBEjemploIN5CV;
create database DBEjemploIN5CV;

use DBEjemploIN5CV

create table datos (
codigoDato int not null auto_increment,
num1 int not null,
num2 int not null,
suma int,
resta int,
multiplicacion int,
division int,
Primary key PK_codigoDato (codigoDato)
);



Delimiter $$
	create procedure Sp_ListarDatos()
		Begin
			select 
				D.codigoDato,
                D.num1,
                D.num2,
                D.suma,
                D.resta,
                D.Multiplicacion,
                D.division
                from Datos D;
        end $$
Delimiter ;

drop procedure Sp_AgregarDato

Delimiter $$ 

create procedure Sp_AgregarDato(in num1 int, in num2 int)
	Begin
		insert into Datos(num1, num2) values (num1, num2);
    end $$
Delimiter ;


Delimiter $$
	create function fn_suma (valo1 int, valor2 int) returns int
		reads sql data deterministic 
        begin
			declare result int;
            set result = valor1 + valor2;
			return result;	
        end $$
Delimiter ;

select fn_suma(D.num1, D.num2)as resultado from  Datos D;

UPDATE Datos D 
SET 
    D.suma = FN_SUMA(valor1, valor2)
WHERE
    codigoDato = codDa;
    
	

call Sp_AgregarDato (3, 5);
select fn_suma (5, 9) as Resultado; 
