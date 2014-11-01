package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.lang.reflect.*;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private Class<?> aClass;
	private Field[] fields;

	public ABMManager(T objeto, Persistidor<T> persistidor,
			Visualizador<T> visualizador) {
		this.aClass = objeto.getClass();
		this.fields = this.aClass.getFields();
		this.persistidor = persistidor;
		this.visualizador = visualizador;
		this.visualizador.setFields(fields);
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar() {
		Entidad<T> entidadCreada = visualizador.pantallaCrear();
		guardarEntidad(entidadCreada);
		
		Entidad<T> entidadAModificar = recuperarEntidad();
		
		Entidad<T> entidadModificada = visualizador.pantallaModificar(entidadAModificar);
		guardarEntidad(entidadModificada);
	}

	private void guardarEntidad(Entidad<T> entidad) {
		entidad.setClase(this.aClass);
		T objeto = entidad.crearObjeto();
		this.persistidor.guardar(objeto);
	}
	
	private Entidad<T> recuperarEntidad() {
		Entidad<T> entidad = new Entidad<T>();
		entidad.setClase(this.aClass);
		T objeto = persistidor.obtener();
		entidad.setObjeto(objeto);
		return entidad;
	}
}
