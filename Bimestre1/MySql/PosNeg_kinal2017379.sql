Create database PosNeg_kinal2017379;
use PosNeg_kinal2017379;


delimiter $$
	create function fn_PosiNeg (Valor int) returns Varchar (10)
    reads sql data deterministic 
		begin 
			if (Valor > 0) then 
				return 'Positivo';
			end if;
            if (Valor < 0) then 
				return 'Negativo';
			end if; 
            end $$
delimiter ;

SELECT FN_POSINEG(5) AS Resultados;

Drop function fn_PosiNeg2;

Delimiter $$
	create function fn_PosiNeg2 (Valor int) returns Varchar (15)
		reads sql data deterministic 
        begin
			if (Valor > 0) then 
				return 'Positivo';
			else 
				return 'Negativo' ;
			end if;
		end $$
Delimiter ;

select fn_PosiNeg2 (0) as Resultado

Delimiter $$
	create function fn_PosiNeg3 (Valor int) returns varchar (15)
		reads sql data deterministic
        begin
			if (Valor > 0) then
				return 'Positivo';
			elseif (Valor < 0) then 
				return 'Negativo';
			else 
				return 'Es el 0';
			end if;
		end $$
Delimiter ;

SELECT FN_PsiNeg3(0) AS Resultados;

Delimiter $$
	create function fn_Digito (Valor Varchar(1)) returns Varchar (15)
		reads sql data deterministic
		begin
			if (ord(Valor) >= 48) and ord(Valor) <=57 then 
				return 'Es un Digito';
			else 
				return 'No es un Digito';
			end if;
		end $$
Delimiter ;

select fn_Digito ('1');

Drop function fn_DigitosLargos;

Delimiter $$
	create function fn_DigitosLargos (Valor Varchar(15)) returns Varchar (15)
		reads sql data deterministic 
		begin
			if (Valor <='0') then 
				return 'Es un Digito';
			elseif (Valor = '1') then 
				return 'Es un Digito';
			else 
				return 'No es un Digito';
			end if;
		end $$
Delimiter ;

select fn_DigitosLargos ('a');

Drop function fn_Binary;

Delimiter $$
	Create function fn_Binary (Valor int) returns varchar (17)
reads sql data deterministic
	begin
		if (Valor = 10) then 
			return 'Binario 1010';
		else 
			return 'Error';
		end if;
	end $$
Delimiter ;

Select fn_Binary ('10');

Drop function fn_Caracter;

Delimiter $$
	create function fn_Caracter (Valor Varchar (15)) returns Varchar (30)
    reads sql data deterministic
		begin 
			return concat('El caracter ingresado fue', Valor);
		end $$
Delimiter ;

SELECT fn_Caracter('f');

Drop function fn_NumeroMayor;

Delimiter $$
	create function fn_NumeroMayor (num1 int, num2 int) returns Varchar (50)
		reads sql data deterministic
			begin
				if (num1 > num2) then 
					return concat ('El mayor es', num1);
				else
					return concat ('El menor es', num2);
				end if;
			end$$
Delimiter ;

select fn_NumeroMayor (40, 20);

Drop function fn_SumaMayor;

delimiter $$
	create function fn_Sumayor(Valor1 int, Valor2 int) returns varchar (35)
		reads sql data deterministic 
			begin 
				declare result int;
					set result = Valor1 + Valor2;
				if (Valor1 > Valor2) then 
					return concat (Result 'El mayor es', Valor1);
				end if;
		end $$
delimiter ;

select  fn_Sumayor (5, 4);