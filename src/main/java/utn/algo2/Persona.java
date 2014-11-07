package utn.algo2;

import utn.algo2.annotations.NotNull;
import utn.algo2.annotations.ValidacionPersonalizada;
import utn.algo2.validaciones.Validacion;

public class Persona {
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String apellido;
	
	@ValidacionPersonalizada(metodo=Validacion.MENOR_A_100)
	private Integer edad;
	
	@ValidacionPersonalizada(metodo=Validacion.MAYOR_A_0)
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
