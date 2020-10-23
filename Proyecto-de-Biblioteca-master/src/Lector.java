import java.io.Serializable;
/**
 * 
 * @autor Alberto Sánchez de la Nieta Pérez
 *	Esta es la clase Lector donde se guardará la informacion del objeto lector que se haya creado 
 */
public class Lector implements Comparable<Lector>, Serializable{
	//Atributos de la clase
	private static final long serialVersionUID = 1L;
	private String dni;
	private String nombre;
	private String apellidos;
	private Libro libroPrestado;
	
	//Constructor sin parametros
	public Lector() {}
	//Constructor con parametros
	public Lector(String dni, String nombre, String apellidos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.libroPrestado = null;
	}
	
	//Metodos set y get de la clase Lector
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Libro getLibroPrestado() {
		return libroPrestado;
	}

	public void setLibroPrestado(Libro libroPrestado) {
		this.libroPrestado = libroPrestado;
	}
	//metodo que devuelve la informacion del lector 
	@Override
	public String toString() {
		return "\n********* Lector: *********"
				+ "\nDni: " + dni 
				+ "\nNombre: " + nombre 
				+ "\nApellidos: " + apellidos;
	}
	//Sobrescribo metodo equals para establecer criterio al introducir lectores y que no haya duplicados 
	@Override
	public boolean equals(Object obj) {
		
		return dni.equals(((Lector)obj).dni);
	}
	//Establezco el criterio de ordenacion de arraylist de lectores por orden alfabetico de nombres
	@Override
	public int compareTo(Lector o) {
		
		return nombre.compareTo(o.nombre);
	}
	
	
}
