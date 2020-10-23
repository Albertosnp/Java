import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Alberto Sánchez de la Nieta Pérez
 * Esta clase Biblioteca es donde se recogen casi todos los metodos que realiza el programa.
 * Almacena el nombre de la Biblioteca en cuestion, asi como las listas de Prestamos,lectores y libros, y sus ficheros correspondientes.
 * He puesto como privados todos los metodos que son utilizados internamente en esta clase
 */
public class Biblioteca implements Serializable{
	//Atributos de la clase
	private ArrayList<Lector> listadoLectores;
	private ArrayList<Libro> listadoLibros;
	private ArrayList<Prestamos> listadoPrestamos;
	private File ficheroLectores;
	private File ficheroLibros; 
	private File ficheroPrestamos;
	private String nombreBiblioteca;
	private static final long serialVersionUID = 1L;
	
	//Constructor que se le pasa el nombre de la biblioteca a crear
	public Biblioteca(String nombreBiblioteca) {
		this.nombreBiblioteca = nombreBiblioteca;
		this.nombreBiblioteca = nombreBiblioteca.toUpperCase(); //Almacena el nombre en mayusculas
		this.listadoLectores = new ArrayList<>();
		this.listadoLibros = new ArrayList<>();
		this.listadoPrestamos = new ArrayList<>();
		this.ficheroLectores =  new File(nombreBiblioteca+"_Listado de lectores.dat"); //Se crea el nombre del fichero con el prefijo de la biblioteca
		this.ficheroLibros = new File(nombreBiblioteca+"_Listado de libros.dat"); //Se crea el nombre del fichero con el prefijo de la biblioteca
		this.ficheroPrestamos = new File(nombreBiblioteca+"_Listado de prestamos.dat"); //Se crea el nombre del fichero con el prefijo de la biblioteca
		crearLeerFicheros(); //Llamada al metodo que comprueba-crea los ficheros de la biblioteca
	}
	//Metodo que ejecutará el menu principal de la biblioteca.
	//Este metodo se encarga de recoger las decisiones que vaya tomando el usuario, y realizar las llmadas pertinentes a los demas metodos
	public void menu() {
		Scanner sc = new Scanner(System.in);
		boolean salir1=false; // Variable utilizada para salir-continuar del menu de la biblioteca
		System.out.println("\n--------------------- BIENVENIDO A LA BIBLIOTECA: "+nombreBiblioteca+" ---------------------\n"); 
		do {
			int decision = 0;
			//Salida con el menu especifico de la biblioteca
			System.out.println("\n********** MENÚ DE LA BIBLIOTECA: "+nombreBiblioteca+" **********\n"
						+ "\n1. Crear usuarios."
						+ "\n2. Ver lista de usuarios."
						+ "\n3. Crear libros."
						+ "\n4. Ver lista de libros."
						+ "\n5. Crear Préstamos."
						+ "\n6. Ver lista de Préstamos."
						+ "\n7. Informe de biblioteca."
						+ "\n8. Salir al menú principal.");	
			//Bloque que intentara almacenar la decision del usuario. Si la entrada por teclado son letras, lanzara un mensaje de error informando.	
			try {
				decision = Integer.parseInt(sc.nextLine()); //Otra manera de recoger informacion del usuario. El string entrante lo intenta convertir a integer, para despues asignarlo a la variable "decision"  
			} catch (NumberFormatException e) {
				System.err.println("\nHas introducido un valor no valido.\n");
			}	
			//Si el valor numerico introducido no esta entre los valores indicados, mostrara el siguiente mensaje.	
			if (decision<=0 || 8<decision) System.out.println("****** Numeros válidos: [1-7] ******\n");
			//Estructura switch, que en funcion del valor de "decision" entrará en el correspondiente case.
			switch (decision) {
				case 1:
					try { //Bloque que intenta crear usuarios, en caso de que el metodo le propague una excepcion, mostrará el mensaje que tiene el catch
						crearUsuarios(); //llamada que crea usuarios
					} catch (InputMismatchException e) {
						System.err.println("\nERROR: No has introducido un numero. \n");
					}
					break;
				case 2:
					mostrarListadoLectores();//Llamada a metodo que mostrara todos los lectores de la biblioteca
					break;
				case 3:
					try {//Bloque que intenta llamar a crearlibros, en caso de que el metodo le propague una excepcion, mostrará el mensaje que tiene el catch
						crearLibros(); //Llamada al metodo que permite crear libros 
					} catch (InputMismatchException e) {
						System.err.println("\nERROR: No has introducido un numero. \n");
					}
					break;
				case 4:
						mostrarListadoLibros(); //metodo que mostrara todos los libros almacenados en la biblioteca
						break;
				case 5:
						crearPrestamo(); //Llmada a metodo que intentara crear un prestamo
						break;
				case 6:
						mostrarListadoPrestamos(); //metodo que mostrara todos los prestamos creados y almacenados en la biblioteca
						break;
				case 7: 
						System.out.println(toString()); // Método añadido que muestra las estadisticas de la biblioteca.
						break;
				case 8:
						guardarFicheros(); //LLamada a metodo que guarda todos los cambios realizdos en los arraylist de la biblioteca, guardandolos en los ficheros correspondientes
						salir1=true; // Cambio de valor a la variable que permite salir-continuar del menu de la biblioteca
						System.out.println("\n-------------------- HAS CERRADO LA BIBLIOTECA: "+nombreBiblioteca+" --------------------\n"); //Mensaje personalizado para avisar que se ha cerrado la biblioteca, (Saldrá al menu principal)
						break;
				}
		} while (!salir1);
	}
	
	// Metodo que comprueba si existen los ficheros de la biblioteca, de ser asi los lee y los almacena en los listados. Si no es asi creará los ficheros y mostrará un mensaje por cada fichero creado
	private void crearLeerFicheros() {

		if (!ficheroLectores.exists()) {//Si no existe el fichero entrará y se creará
			try {
				ficheroLectores.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Fichero creado con exito!");
		} else { //Si el fichero ya existía, procedera a leerlo y almacenarlo (cargarlo en memoria) en el arraylist correspondiente
			try {
				listadoLectores = (ArrayList<Lector>) lecturaFichero(ficheroLectores); //llamada a metodo que lee el fichero y devuelve el listado relleno
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
		if (!ficheroLibros.exists()) {//Si no existe el fichero entrará y se creará
			try {
				ficheroLibros.createNewFile();
				System.out.println("Fichero creado con exito!");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {//Si el fichero ya existía, procedera a leerlo y almacenarlo (cargarlo en memoria) en el arraylist correspondiente
			try {
				listadoLibros = (ArrayList<Libro>) lecturaFichero(ficheroLibros);//llamada a metodo que lee el fichero y devuelve el listado relleno
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
		if (!ficheroPrestamos.exists()) {//Si no existe el fichero entrará y se creará
			try {
				ficheroPrestamos.createNewFile();
				System.out.println("Fichero creado con exito!");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {//Si el fichero ya existía, procedera a leerlo y almacenarlo (cargarlo en memoria) en el arraylist correspondiente
			try {
				listadoPrestamos = (ArrayList<Prestamos>) lecturaFichero(ficheroPrestamos);//llamada a metodo que lee el fichero y devuelve el listado relleno
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	//Metodo "comodin" que lee un fichero cualquiera y lo devuelve en forma de listado.
	private static ArrayList<?> lecturaFichero(File fichero) throws ClassNotFoundException {
		ArrayList<?> listado = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) { //utilizo esta manera de trabjar con el flujo para que lo cierre automaticamente.
			listado = (ArrayList<?>) ois.readObject(); //Lee el objeto completo y lo asigna al listado
		} catch (EOFException eof) { //Esta bloque catch ya no haria falta porque solo lee el fichero en caso de que esté creado, aun así lo dejo para futuras consultas.

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return listado; // se devuelve el listado relleno
	}

	//Metodo que que guardará (escribirá) los cambios de todos los arraylist de la biblioteca en sus ficheros correspondientes.
	private void guardarFicheros() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroLectores))) { //Utilizo este metodo para manejar el flujo para que no se me olvide nunca cerrarlo.
			oos.writeObject(listadoLectores); //EScritura del listado en el fichero
		} catch (IOException e) { //En caso de error capturará el error y mostrará mensaje
			System.err.println(e.getMessage());
		}
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroLibros))) {
			oos.writeObject(listadoLibros);//Escritura del listado en el fichero
		} catch (IOException e) {//En caso de error capturará el error y mostrará mensaje
			System.err.println(e.getMessage());
		}
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroPrestamos))) {
			oos.writeObject(listadoPrestamos);//Escritura del listado en el fichero
		} catch (IOException e) {//En caso de error capturará el error y mostrará mensaje
			System.err.println(e.getMessage());
		}
	}
	
	//Metodo para crea prestamos en la biblioteca, verifica si el lector y el libro estan registrados, por medio de su dni(lector) y titulo(libro).
	private void crearPrestamo() {
		Scanner sc = new Scanner(System.in);
		//Se piden los datos que se han de comprobar
		System.out.println("Para realizar un prestamo introduce un DNI ya registrado:");
		String dni = sc.nextLine();
		System.out.println("Además debes introducir un titulo ya registrado:");
		String titulo = sc.nextLine();
		// Las llamadas que comprueban el dni y el titulo, se pueden refactorizar
		// metiendolas directamente en el IF
		// pero lo he dejado asi para que sea mas legible
		Lector lector = comprobarDni(dni); // LLmada a metodo que comprueba si el dni esta registrado.
		Libro libro = comprobarTitulo(titulo); //LLamada a metodo que comprueba que el titulo existe.

		if (lector != null && libro != null) { //Si el libro y el lector ambos son diferentes de null, pasaran la condicion. (significa que ambos existen)
											
			if (lector.getLibroPrestado() == null) { //Condicion que evalua si el lector tiene algun libro prestado, de ser asi no entrara en el bloque
				Prestamos prestamo = new Prestamos(lector, libro); // se crea el prestamo
				listadoPrestamos.add(prestamo); //Se añade el prestamo a la lista de prestamos
				lector.setLibroPrestado(libro); //Se cambia el atributo "libroPrestado" del lector y se le asigna el libro correspondiente
				libro.setDisponible(false); //Se cambia la disponibilidad del libro a NO DISPONIBLE
				System.out.println("\n------------------ Prestamo creado con éxito! ------------------\n"); //Salida para avisar de que el prestamo se ha realizado
			} else {
				//Salida (aviso) de que el lector ya tenia un libro prestado (no se puede hacer otro prestamo).
				System.out.println("El lector con DNI: " + lector.getDni() + " no puede tener mas de un libro prestado."); 
			}
		}
	}
	//MEtodo que comprueba si el titulo pasado por parametro existe ya en la biblioteca, 
	//devolvera el libro esté libre, si no hay ninguno disponible devolvera null, al igual que si no existe ningun libro con ese titulo. 
	private Libro comprobarTitulo(String titulo) {
		boolean encontrado=false; //Variable que controla en caso de que el libro exista pero no este disponible.
		Iterator<Libro> it = listadoLibros.iterator(); //Se crea el iterador para recorrer el arraylist
		
		while (it.hasNext()) {
			Libro libro = it.next();
			if (libro.isDisponible() && libro.getTitulo().equalsIgnoreCase(titulo)) { // Condicion que evalua que le libro este disponible y los titulos sean iguales
				System.out.println("\nEl libro: " + libro.getTitulo() + " está DISPONIBLE. Ejemplar: " + libro.getId());
				return libro;
			} else if (libro.getTitulo().equalsIgnoreCase(titulo)) { //Condicion que evalua si el titulo es igual, una vez comprobado que el libro no esta disponible
				System.out.println("\nEl libro: " + libro.getTitulo() + " está NO DISPONIBLE. Ejemplar: " + libro.getId());
				encontrado = true; // VAriable controla que el libro existe, por lo que NO se mostrara el mensaje de que no existe 
			}
		}
		if (!encontrado) {//Si el libro no existe en la biblioteca se avisara con el siguiente mensaje
			System.out.println("\nEl titulo: " + titulo + " no está registrado. No se puede realizar el prestamo.");
		}
		return null; //Si llega hasta aqui, significa que no hay ningun libro con ese titulo que este disponible, o directamente que no existe.
	}
	
	//Metodo que comprueba si el DNI pasado por parametro existe (esta registrado) en la biblioteca. 
	//He utilizado el metodo completo para recorrer el array a modo de practica, pero seria mas eficiente hacerlo con for-each 
	private Lector comprobarDni(String dni) {
		Iterator<Lector> it = listadoLectores.iterator(); //SE crea el iterador para recorrer el arraylist
		
		while (it.hasNext()) { //Se repetira el bucle mientras haya un siguiuente objeto
			Lector lector = it.next();
			if (lector.getDni().equalsIgnoreCase(dni)) {//Condicion que comprueba si el dni del lector es igual al pasdo por parametro
				System.out.println("\nEl DNI: " + lector.getDni() + " existe y corresponde a: " + lector.getNombre()+ " " + lector.getApellidos());
				return lector; //se retorna el lector que coincide con el dni pasado por parametro
			}
		}
		System.out.println("El DNI introducido no está registrado");
		return null; //Devolverá null ne caso de que el dni no este registrado
	}

	// Funcion que crea tantos libros como quiera el usuario. No puede haber dos
	// libros con id igual pues es asignado automaticamente. Pueden haber varios ejemplares con mismo titulo, pero no seran el mismo objeto.
	private void crearLibros() throws InputMismatchException {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------- ¿Cuántos libros quieres crear? --------------------");
		int numlibros = 0;
		//Bucle que se repetirá mientras que el numero introducido no sea mayor que 0
		do {
			numlibros = sc.nextInt();
			if (numlibros <= 0) {
				System.out.println("Introduce un numero mayor a 0"); //Aviso al usuario de los valores permitidos
			}
		} while (numlibros <= 0);
		sc.nextLine(); // Limpieza de buffer
		//Bucle que se repetirá segun el numero de libros que quieren dar de alta
		for (int i = 0; i < numlibros; i++) {
			//Se piden los datos del libro que se quiere dar de alta
			System.out.println("\nIntroduce el Titulo:"); String titulo = sc.nextLine();
			System.out.println("\nIntroduce los Autor:"); String autor = sc.nextLine();
			//Se crea el objeto libro que ha de ser comprobado
			//El ID adquirirá el valor de la longitud del arraylist + 1 (Por lo tanto nunca podra haber 2 libros con id igual, pero si dos ejemplares con el mismo nombre).
			Libro libro = new Libro(listadoLibros.size() + 1, titulo, autor); 
			listadoLibros.add(libro);//Se añade el libro al arraylist
			System.out.println("\n-------------- Libro creado con éxito con ID: " + libro.getId() + ". ------------------\n");	
		}          
	}
	// Funcion que crea tantos lectores como el usuario quiera. No puede haber dos
	// lectores con dni igual, ademas deberá introducirse DNI reales. En caso de introducir caracteres no numericos propagará una excepcion
	private void crearUsuarios() throws InputMismatchException {
		Scanner sc = new Scanner(System.in);
		System.out.println("-------------- ¿Cuántos usuarios quieres crear? --------------------");
		int numUsers = 0;
		//Bucle que se repetirá mientras que el numero introducido no sea mayor que 0 
		do { 
			numUsers = sc.nextInt();
			if (numUsers <= 0) {
				System.out.println("Introduce un numero mayor a 0"); //Aviso al usuario de los valores permitidos
			}
		} while (numUsers <= 0);
		sc.nextLine(); //Limpieza de buffer despues de haber guardado numeros
		//Bucle que se repetirá segun el numero de lectores que quieren dar de alta
		for (int i = 0; i < numUsers; i++) {
			//Se piden los datos del lector que se quiere dar de alta
			System.out.println("\nIntroduce el DNI correcto:"); 
			Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]"); //SE define el patron que debe cumplir el dni
			String dni = sc.nextLine();
			Matcher real = pat.matcher(dni); //Comprobacion que guardará la variable 
			while(!real.matches()){ //Si el DNI no es real realizara el bucle
		       System.out.println("El DNI introducido es incorrecto, debes introducir un DNI real a continuación:");
		        dni = sc.nextLine(); //Lectura 
		        real = pat.matcher(dni); //Comprobacion
		    }
			System.out.println("\nIntroduce el Nombre:"); String nombre = sc.nextLine();
			System.out.println("\nIntroduce los Apellidos:"); String apellidos = sc.nextLine();
			
			Lector lector = new Lector(dni, nombre, apellidos);//Se crea el objeto lector que ha de ser comprobado
			if (!listadoLectores.contains(lector)) { //Condicion que comprueba que el lector no exista en el arraylist
				listadoLectores.add(lector); //Se añade el lector al arraylist
				System.out.println("\n-------------- Lector creado con éxito. ------------------\n");
			} else { //Si el lector ya esta registrado, mostrara el siguiente mensaje
				System.out.println("\nEl usuario con DNI: " + dni + " ya está dado del alta, no se puede duplicar.\n");
			}
		}
	}
	
	//Metodo que recorre y muestra de forma eficiente la lista de Prestamos en caso de que no este vacia
	private void mostrarListadoPrestamos() {
		Collections.sort(listadoPrestamos);// El listado se ordena por orden de creacion de prestamos
		if (listadoPrestamos.isEmpty()) { //Si la lista esta vacia mostrara el mensaje de que no hay registros 
			System.out.println("\nNo hay registros.");
		} else {
			for (Prestamos elemento : listadoPrestamos) {
				System.out.println(elemento);
			}
		}
	}
	//Metodo que recorre y muestra de forma eficiente la lista de lectores en caso de que no este vacia
	private void mostrarListadoLectores() {
		Collections.sort(listadoLectores); // El listado se ordena y se muestra por orden alfabetico de nombre.
		if (listadoLectores.isEmpty()) {//Si la lista esta vacia mostrara el mensaje de que no hay registros
			System.out.println("\nNo hay registros.");
		} else {
			for (Lector elemento : listadoLectores) {
				System.out.println(elemento);
			}
		}
	}
	//MEtodo que recorre y muestra de forma eficiente la lista de libros en caso de que no este vacia
	private void mostrarListadoLibros() {
		Collections.sort(listadoLibros);// El listado se ordena y se muestra por orden alfabetico de titulos.
		if (listadoLibros.isEmpty()) {//Si la lista esta vacia mostrara el mensaje de que no hay registros
			System.out.println("\nNo hay registros.");
		} else {
			System.out.println("\n************* Listado de Libros: *************");
			for (Libro elemento : listadoLibros) { 
				System.out.println(elemento);
			}
		}
	}
	//Devuelve el nombre de la biblioteca
	public String getNombreBiblioteca() {
		return nombreBiblioteca;
	}
	//Metodo que devuelve en forma de string las "estadisticas" de la biblioteca
	@Override
	public String toString() {
		return "\n--------------- Registro de la Biblioteca: "+ nombreBiblioteca +" ---------------\n"
				+"\nNumero de Lectores: " + listadoLectores.size() 
				+ "\nNumero de Libros: " + listadoLibros.size()
				+ "\nNumero de Prestamos: " + listadoPrestamos.size()+"\n";
	}
	
	
}
