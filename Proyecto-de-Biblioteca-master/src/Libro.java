import java.io.Serializable;
/**
 * 
 * @autor Alberto Sánchez de la Nieta Pérez
 * Esta es la clase libro, donde se almacenará toda la informacion del objeto creado.
 */
public class Libro implements Comparable<Libro>, Serializable{
	//Atributos de la clase Libro
	private static final long serialVersionUID = 1L;
	private int id;
	private String titulo;
	private String autor;
	private boolean disponible;
	
	//Constructor vacio
	public Libro() {}
	
	//Constructor con parametros
	public Libro(int id, String titulo, String autor) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.disponible = true;
	}

	//Metodos set y get de la clase libro
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	//metodo sobrescrito que devuelve la informacion del libro
	@Override
	public String toString() {
		return  "\nTitulo: " + titulo
				+ "\nID: " + id 
				+ "\nAutor: " + autor 
				+ "\nDisponible: " + disponible+ "\n";
	}
	//Establezco el criterio de ordenacion de arraylist de libros por orden alfabetico del libro. (Mostrará todos los ejemplares con el mismo nombre jusntos). 
	@Override
	public int compareTo(Libro libro) {
		return titulo.compareTo(libro.titulo);
	}
/** @deprecated 
 * Codigo que comparaba id para evitar el duplicado de libros, ahora el ID es automatico
 * 
	@Override
	public boolean equals(Object obj) {
		
		return id.equals(((Libro)obj).id);
	}
	*/
}
