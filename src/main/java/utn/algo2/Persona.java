package utn.algo2;

import utn.algo2.annotations.EnteroMayorA0;
import utn.algo2.annotations.NotNull;
import utn.algo2.annotations.Personalizada;

public class Persona {
	
	@NotNull
	private String nombre;
	
	@NotNull
	@EnteroMayorA0
	@Personalizada(metodo="menorA100")
	private Integer edad;

	/* Getters and Setters */
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/* Override */
	
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
	
	/* Validadores */
	
	public boolean menorA100(Integer valor) {
		return valor < 100;
	}

}
