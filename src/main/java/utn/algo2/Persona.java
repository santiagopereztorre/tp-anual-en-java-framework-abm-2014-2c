package utn.algo2;

import java.time.LocalDate;

import utn.algo2.annotations.EnteroMayorA0;
import utn.algo2.annotations.Etiqueta;
import utn.algo2.annotations.Filtrable;
import utn.algo2.annotations.NotNull;
import utn.algo2.annotations.Personalizada;
import utn.algo2.annotations.SoloLectura;

public class Persona {
	
	@NotNull
	@Filtrable
	private String nombre;

	@SoloLectura
	private String apellido;
	
	@NotNull
	@EnteroMayorA0
	@Personalizada(metodo="menorA100")
	private Integer edad;
	
	@Filtrable
	private Double sueldo;
	
	@Etiqueta(nombre="santi")
	private LocalDate fecha;

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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}
	
	/* Override */

	@Override
	public String toString() {
		return this.nombre;
	}
	
	/* Validadores */
	
	public boolean menorA100(Integer valor) {
		return valor < 100;
	}

}
