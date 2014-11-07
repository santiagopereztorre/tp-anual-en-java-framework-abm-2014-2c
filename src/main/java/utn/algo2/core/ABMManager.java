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
		Runnable creacion = () -> callbackCreacion();
		visualizador.onCrear(creacion);

		Runnable modificacion = () -> callbackModificacion();
		visualizador.onModificar(modificacion);

		Runnable creacionFiltrado = () -> callbackCreacionFiltrado();
		visualizador.onCrearFiltrado(creacionFiltrado);

		Runnable modificacionFiltrado = () -> callbackModificacionFiltrado();
		visualizador.onModificarFiltrado(modificacionFiltrado);

		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.abrirPantallaFiltrado(entidades);
	}

	private void callbackCreacion() {
		Entidad<T> entidadCreada = visualizador.getCreado();
		visualizador.cerrarPantallaCrear();
		guardarEntidad(entidadCreada);
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackModificacion() {
		Entidad<T> entidadModificada = visualizador.getModificado();
		Entidad<T> entidadVieja = visualizador.getEntidadModificada();
		visualizador.cerrarPantallaModificar();
		reemplazarEntidad(entidadVieja, entidadModificada);
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackCreacionFiltrado() {
		visualizador.abrirPantallaCrear();
	}

	private void callbackModificacionFiltrado() {
		Entidad<T> entidadFiltrada = visualizador.getFiltrado();
		visualizador.abrirPantallaModificar(entidadFiltrada);
	}

	private void guardarEntidad(Entidad<T> entidad) {
		entidad.setClase(clase);
		T objeto = entidad.crearObjeto();
		persistidor.guardar(objeto);
	}

	private void reemplazarEntidad(Entidad<T> entidadVieja,
			Entidad<T> entidadModificada) {
		entidadVieja.setClase(clase);
		T objetoViejo = entidadVieja.crearObjeto();
		entidadModificada.setClase(clase);
		T objetoNuevo = entidadModificada.crearObjeto();
		persistidor.modificar(objetoViejo, objetoNuevo);
	}

	private List<Entidad<T>> recuperarTodasEntidades() {
		List<T> objetos = persistidor.obtenerTodo();
		List<Entidad<T>> entidades = new ArrayList<Entidad<T>>();
		for (T objeto : objetos) {
			Entidad<T> entidad = new Entidad<T>();
			entidad.setClase(clase);
			entidad.actualizarAtributosFrom(objeto);
			entidades.add(entidad);
		}
		return entidades;
	}
}
