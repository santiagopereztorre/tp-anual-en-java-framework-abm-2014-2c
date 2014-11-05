package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.util.ArrayList;
import java.util.List;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private Class<?> aClass;

	public ABMManager(Class<T> class1, Persistidor<T> persistidor,
			Visualizador<T> visualizador) {
		this.aClass = class1;
		this.persistidor = persistidor;
		this.visualizador = visualizador;
		this.visualizador.setFields(this.aClass.getFields());
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar() {
		visualizador.abrirPantallaCrear();
		Entidad<T> entidadCreada = visualizador.getCreado();
		visualizador.cerrarPantallaCrear();
		guardarEntidad(entidadCreada);

		
		Entidad<T> entidadAModificar = recuperarEntidad();
		visualizador.abrirPantallaModificar(entidadAModificar);
		Entidad<T> entidadModificada = visualizador.getModificado();
		visualizador.cerrarPantallaModificar();
		guardarEntidad(entidadModificada);
		
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.abrirPantallaFiltrado(entidades);
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

	private List<Entidad<T>>recuperarTodasEntidades() {
		List<T> objetos = persistidor.obtenerTodo();
		List<Entidad<T>> entidades = new ArrayList<Entidad<T>>();
		for (T objeto : objetos) {
			Entidad<T> entidad = new Entidad<T>();
			entidad.setClase(this.aClass);
			entidad.setObjeto(objeto);
			entidades.add(entidad);
		}
		return entidades;
	}
}
