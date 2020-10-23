import java.io.Serializable;
import java.time.LocalDate;
/**
 * 
 * @autor Alberto Sánchez de la Nieta Pérez
 *	Esta clase almacena toda la informacion acerca del objeto prestamo que haya creado
 */
public class Prestamos implements Comparable<Prestamos>, Serializable{
	//Atributos de la clase 
	private static final long serialVersionUID = 1L;
	private Lector lector;
	private Libro libroPrestado;
	private LocalDate fechaPrestamo;
	
	//Constructor vacio
	public Prestamos() {}
	
	//Constructor con parametros
	public Prestamos(Lector lector, Libro libroPrestado) {
		this.lector = lector;
		this.libroPrestado = libroPrestado;
		this.fechaPrestamo = LocalDate.now();
	}
	
	//Metodos set y get de la clase Prestamos
	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	public Libro getLibroPrestado() {
		return libroPrestado;
	}

	public void setLibroPrestado(Libro libroPrestado) {
		this.libroPrestado = libroPrestado;
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	//Metodo que devuelve toda la informacion del prestamo
	@Override
	public String toString() {
		return "\n********* Prestamo: ********* " 
				+"\nDni: " + lector.getDni()
				+ "\nNombre: " + lector.getNombre() 
				+ "\nApellidos: " + lector.getApellidos() 
				+ "\nLibro prestado: " + libroPrestado.getTitulo() + " con ID: "+ libroPrestado.getId() 
				+ "\nFecha de prestamo: " + fechaPrestamo;
	}
	//Establezco el criterio de ordenacion de arraylist de prestamos por orden de fecha de creacion
	@Override
	public int compareTo(Prestamos o) {
		return fechaPrestamo.compareTo(o.fechaPrestamo);
	}
	
	
}
