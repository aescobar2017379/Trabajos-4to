/*
Nombre: Angel Gabriel Escobar Arevalo
Materia: Taller, Tics, Caldulo, Dibujo 
Grado: 5to informática
seccion: IN5CV
*/
create database CaseEjemplo_2017379;
use CaseEjemplo_2017379;

Delimiter $$
	create function fn_CaseEjemplo (valor int) returns int
		reads sql data deterministic
		begin
			case (valor)
				when (1) then
                return 1;
				when (2) then 
                return 2;
                when (3) then 
                return 3;
                when (4) then 
                return 4;
                when (5) then 
                return 5;
                else 
					return 0;
            end case;
        end $$
Delimiter ;

SELECT FN_CASEEJEMPLO(1) AS resultado;

Delimiter $$
	create function fn_Mayor (num1 int, num2 int) returns varchar (25)
		reads sql data deterministic 
        begin
			case (num1 > num2)
            when true then 
				return concat ('El mayor es', num1);
			when false then 
				return concat ('El mayor es', num2);
			end case;
        end $$
Delimiter ;

SELECT FN_MAYOR(45, 10) AS Mayor;