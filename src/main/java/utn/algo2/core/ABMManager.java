package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.lang.reflect.*;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador visualizador;
	private T objeto;

	public ABMManager(T objeto, Persistidor<T> persistidor,
			Visualizador visualizador) {
		this.objeto = objeto;
		this.persistidor = persistidor;
		this.visualizador = visualizador;
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar() {
		Class<?> aClass = objeto.getClass();
		Field[] fields = aClass.getFields();
		
		visualizador.setFields(fields);

		visualizador.run();
	}
}
