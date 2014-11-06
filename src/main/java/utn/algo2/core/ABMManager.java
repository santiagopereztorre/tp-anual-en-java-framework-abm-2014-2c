package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.util.ArrayList;
import java.util.List;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private Class<?> clase;
	private Runnable actualizarFiltro;

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
		Runnable modificacionFiltrado = () -> callbackModificacionFiltrado();
		Runnable modificacion = () -> callbackModificacion();
		
		Runnable creacionFiltrado = () -> callbackCreacionFiltrado();
		Runnable creacion = () -> callbackCreacion();
		
		actualizarFiltro = () -> callbackActualizarFiltro();
		
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.onModificarFiltrado(modificacionFiltrado);
		visualizador.onModificar(modificacion);
		visualizador.onCrearFiltrado(creacionFiltrado);
		visualizador.onCrear(creacion);
		visualizador.abrirPantallaFiltrado(entidades);
	}

	private void callbackActualizarFiltro() {
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackCreacion() {
		Entidad<T> entidadCreada = visualizador.getCreado();
		visualizador.cerrarPantallaCrear();
		guardarEntidad(entidadCreada);
		actualizarFiltro.run();
	}

	private void callbackCreacionFiltrado() {
		visualizador.abrirPantallaCrear();
	}

	private void callbackModificacion() {
		Entidad<T> entidadModificada = visualizador.getModificado();
		guardarEntidad(entidadModificada);
		visualizador.cerrarPantallaModificar();
		actualizarFiltro.run();
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
