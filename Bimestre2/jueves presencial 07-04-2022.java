/*
Nombre: Angrl gabriel escobar arevalo 
codigo tecnico: IN5CV
carné: 2017379
fecha de creación: 07/04/2022
fecha de modificación:
*/


public class Principal {
	
	
		public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int cont;
		int [] valores = new int [5];
		float prom=0;
		
		for (cont=0; cont<5; cont++){
			System.out.println("ingrese un numero en posicion"+ String.valueOf(cont))
			valores [cont]= sc.nextInt();
		}
		prom = (valores [0]+ valores [1]+ valores [3]+ valores [4])/5;
		Systwm.out.println("El promedio es; "+ String.valueOf(prom));
			
	}
}