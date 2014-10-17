package utn.algo2.core;

import utn.algo2.Persona;
import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

public class ABMManager<T> {

	Persistidor<T> persistidor;
	Visualizador visualizador;
	
	public ABMManager(Persistidor<T> persistidor, Visualizador visualizador) {
		this.persistidor = persistidor;
		this.visualizador = visualizador;
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar()
	{
		
	}
}
