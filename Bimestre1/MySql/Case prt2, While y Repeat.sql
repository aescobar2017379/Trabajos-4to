/*
Nombre: Angel Gabriel Escobar Arevalo
Materia: Taller, Tics, Caldulo, Dibujo 
Grado: 5to informática
seccion: IN5CV
fecha de ceración: 22/02/2022
fecha de modificación: 24/02/2022
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
				return concat('El mayor es', num1);
			when false then 
				return concat('El mayor es', num2);
			end case;
        end $$
Delimiter ;

SELECT FN_MAYOR(45, 10) AS Mayor;


Delimiter $$
	create function fn_CasePosiNeg (num1 int) returns varchar (25)
		reads sql data deterministic 
			begin
				case (num1 > 0)
                when true then 
					return concat('Positivo');
				when false then
					return concat('Negativo');
			end case;
		end $$
Delimiter ;

SELECT FN_CASEPOSINEG(5);

Delimiter $$
	create function fn_Cero (num1 int) returns varchar (30)
		reads sql data deterministic
			begin
				case (num1 = 0)
                when true then
					return concat('El valor es', num1);
				when false then 
					return concat('se ingreso otro digito');
				end case;
			end $$
Delimiter ;
SELECT FN_CERO(0);


-- -----------------------------------------------Ejemplos de ciclo While-------------------------------------------------------

Delimiter $$
	create function fn_WhileExample (vueltas int) returns int
		reads sql data deterministic
        begin
			declare cont integer default 0;
            while (cont < 10) do
				set cont = cont + 1;
			end while;
            return cont;
		end $$
Delimiter ;

SELECT FN_WHILEEXAMPLE(20);

Delimiter $$
	create function fn_WhileExample2 (vueltas int) returns int
		reads sql data deterministic
        begin
			declare cont integer default 0;
            while (cont < vueltas) do
				set cont = cont + 1;
			end while;
            return cont;
		end $$
Delimiter ;

SELECT FN_WHILEEXAMPLE2(30);

-- -----------------------------------------------Sumatoria de los primeros 10 Nùmeros---------------------------------------------
drop function fn_Sumatoria10;
Delimiter $$
	Create function fn_Sumatoria10 (vueltas int) returns int
		reads sql data deterministic
			begin
				declare cont int default 0;
                declare suma int default 0;
                while (cont < vueltas) do
					set cont = cont + 1;
                    set suma = suma + cont;
				end while;
					return suma;
				end$$
Delimiter ;

SELECT FN_SUMATORIA10(100000) AS Resultado;

-- -----------------------------------------------Factorial de cuaquier Nùmero-----------------------------------------------------
drop function fn_Factorial;
Delimiter $$
	create function fn_Factorial (Valor int) returns int
		reads sql data deterministic
			begin
				declare cont int default 0;
                declare factorial int default 1;
					while (cont < Valor) do 
						set cont = cont + 1;
                        set factorial = factorial * cont;
					end while;
						return factorial;
					end $$
Delimiter ;

select fn_Factorial (5) as Resultados;

-- ------------------------------------------------Ejemplo Repeat--------------------------------------------------------------------------
drop function fn_Rep;
Delimiter $$
	create function fn_Rep (cant int) returns varchar (150)
		reads sql data deterministic 
		begin 
			declare cont int default 0;
            declare num int default 0;
            declare resultado Varchar (150);
            set resultado = '';
            Repeat
				set cont = cont + 1;
                set num = floor (rand()*10);
                set resultado = concat(resultado, num, ',');
			until (cont = cant)
            end repeat;
            return resultado;
		end $$
Delimiter ;

select fn_Rep (10) as resultados;

-- ----------------------------------------------Ejercicio----------------------------------------------------------------------------------
drop function fn_CodeAspi;
Delimiter $$
	create function fn_CodeAspi (Valor varchar (20)) returns varchar (20)
		reads sql data deterministic 
        begin
			case (ord(Valor) >= 48 ) and (ord(Valor) <= 57) 
				when true then 
					return concat ('Es un Digito');
				when false then 
					return concat ('No Es Digito');
				end case;
			end$$
Delimiter  ;

select fn_CodeAspi ('A');