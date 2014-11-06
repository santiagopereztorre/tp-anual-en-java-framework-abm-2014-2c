package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.util.ArrayList;
import java.util.List;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private Class<?> clase;

	public ABMManager(Class<T> clase, Persistidor<T> persistidor,
			Visualizador<T> visualizador) {
		this.clase = clase;
		this.persistidor = persistidor;
		this.visualizador = visualizador;
		this.visualizador.setFields(this.clase.getFields());
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar() {
		
		visualizador.abrirPantallaCrear();
		Entidad<T> entidadCreada = visualizador.getCreado();
		visualizador.cerrarPantallaCrear();
		guardarEntidad(entidadCreada);

		Runnable modificacionFiltrado = () -> callbackModificacionFiltrado();
		Runnable modificacion = () -> callbackModificacion();
		
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.onModificarFiltrado(modificacionFiltrado);
		visualizador.onModificar(modificacion);
		visualizador.abrirPantallaFiltrado(entidades);
		
		
		
		
//		visualizador.abrirPantallaCrear();
//		Entidad<T> entidadCreada = visualizador.getCreado();
//		visualizador.cerrarPantallaCrear();
//		guardarEntidad(entidadCreada);
//		
//		Entidad<T> entidadAModificar = recuperarEntidad();
//		visualizador.abrirPantallaModificar(entidadAModificar);
//		Entidad<T> entidadModificada = visualizador.getModificado();
//		visualizador.cerrarPantallaModificar();
//		guardarEntidad(entidadModificada);
//		
//		List<Entidad<T>> entidades = recuperarTodasEntidades();
//		visualizador.abrirPantallaFiltrado(entidades);
//		Entidad<T> entidadFiltrada = visualizador.getFiltrado();
//		visualizador.cerrarPantallaFiltrado();
//		visualizador.abrirPantallaModificar(entidadFiltrada);
//		entidadModificada = visualizador.getModificado();
//		visualizador.cerrarPantallaModificar();
//		guardarEntidad(entidadModificada);
	}

	private void callbackModificacion() {
		Entidad<T> entidadModificada = visualizador.getModificado();
		guardarEntidad(entidadModificada);
		visualizador.cerrarPantallaModificar();
	}

	private void callbackModificacionFiltrado() {
		Entidad<T> entidadFiltrada = visualizador.getFiltrado();
		visualizador.abrirPantallaModificar(entidadFiltrada);
	}

	private void guardarEntidad(Entidad<T> entidad) {
		entidad.setClase(this.clase);
		T objeto = entidad.crearObjeto();
		this.persistidor.guardar(objeto);
	}
	
	private Entidad<T> recuperarEntidad() {
		T objeto = persistidor.obtener();
		Entidad<T> entidad = new Entidad<T>();
		entidad.setClase(this.clase);
		entidad.setObjeto(objeto);
		return entidad;
	}

	private List<Entidad<T>>recuperarTodasEntidades() {
		List<T> objetos = persistidor.obtenerTodo();
		List<Entidad<T>> entidades = new ArrayList<Entidad<T>>();
		for (T objeto : objetos) {
			Entidad<T> entidad = new Entidad<T>();
			entidad.setClase(this.clase);
			entidad.setObjeto(objeto);
			entidades.add(entidad);
		}
		return entidades;
	}
}
