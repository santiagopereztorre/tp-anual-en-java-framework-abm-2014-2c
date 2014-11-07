package utn.algo2;

public class Persona {
	
	private String nombre;
	
	private String apellido;
	
	private Integer edad;
	
	private Double sueldo;
	
	/* Getters and Setters */
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}
	
	/* Override */
	
	@Override
	public String toString() {
		return this.nombre;
	}

}
