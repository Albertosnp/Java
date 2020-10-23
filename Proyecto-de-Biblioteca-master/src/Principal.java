import java.util.*;
/**
 * @autor Alberto Sánchez de la Nieta Pérez
 * Clase Principal donde se ejecuta el main del Proyecto.
 */
public class Principal {

	public static void main(String[] args){
		// Variable que recoge la decision del usuario para salir o continuar en el programa. Solo cambiara en caso de que elija cerrar el programa
		boolean decision = false; 
		int eleccion; // Variable que  almacena la decision que quiera realizar el usuario
		do  {
			Scanner sc = new Scanner(System.in);
			eleccion=0; //Inicializacion de la variable por cada iteracion del bucle
			System.out.println("\n********** MENÚ PRINCIPAL **********\n" 
					+ "\n1. Abrir Biblioteca.\n" 
					+ "\n2. Cerrar Programa.\n"
					+ "\n************************************");
			//Bloque que intentará recoger la eleccion del usuario, si el valor son letras, lanzará un mensaje de error 
			try {
				eleccion = sc.nextInt(); //REcojo la eleccion del usuario
			} catch (InputMismatchException e) {
				System.err.println("\nHas introducido un valor no valido.\n");
			}
			switch (eleccion) {//Se evalua el contenido de "eleccion",
				case 1://si es 1 iniciará el proceso para abrir bibilioteca
					sc.nextLine();
					System.out.println("\nIntroduce el nombre de la biblioteca que quieras manejar.");
					String nombreBiblioteca = sc.nextLine();
					Biblioteca biblioteca = new Biblioteca(nombreBiblioteca); //Se crea la biblioteca
					biblioteca.menu(); //Llamada al menu de la biblioteca
					break;
				case 2:// En caso de que "eleccion" valga 2, se cambia la variable "decision" para salir del bucle (cierra el programa)
					decision = true;
					break;
				default:
					System.out.println("Introduce [1,2]"); //Si no es ninguna de las anteriores, mostrará mensaje de valor no valido
					break;
			}
 
		}while(!decision);
		System.out.println("\n-------------------- HAS CERRADO EL PROGRAMA --------------------\n"); //Salida para avisar al usuario que ha llegado al final del programa         
	}
}
