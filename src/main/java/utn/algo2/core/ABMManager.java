package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.exception.CasteoInvalidoException;
import utn.algo2.exception.ValorNoCumpleCondicionException;
import utn.algo2.visualizacion.Visualizador;

import java.lang.reflect.Field;
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
		ArrayList<Atributo<T>> atributos = convertirFieldsAAtributos();
		this.visualizador.setAtributos(atributos);
	}
	
	/* Interfaz */

	public void ejecutar() {
		Runnable crearCreacion = () -> callbackCrearCreacion();
		visualizador.onCrear(crearCreacion);

		Runnable modificarModificacion = () -> callbackModificarModificacion();
		visualizador.onModificar(modificarModificacion);

		Runnable crearFiltrado = () -> callbackCrearFiltrado();
		visualizador.onCrearFiltrado(crearFiltrado);

		Runnable modificarFiltrado = () -> callbackModificarFiltrado();
		visualizador.onModificarFiltrado(modificarFiltrado);
		
		Runnable borrarFiltrado = () -> callbackBorrarFiltrado();
		visualizador.onBorrarFiltrado(borrarFiltrado);

		Runnable volverCreacion = () -> callbackVolverCreacion();
		visualizador.onVolverCrear(volverCreacion);
		
		Runnable volverModificacion = () -> callbackVolverModificacion();
		visualizador.onVolverModificar(volverModificacion);
		
		Runnable volverFiltracion = () -> callbackVolverFiltracion();
		visualizador.onVolverFiltrar(volverFiltracion);

		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.abrirPantallaFiltrado(entidades);
	}

	/* Complementarios */

	private ArrayList<Atributo<T>> convertirFieldsAAtributos() {
		Field[] fields = clase.getDeclaredFields();
		ArrayList<Atributo<T>> atributos = new ArrayList<Atributo<T>>();
		for (Field field : fields) {
			Atributo<T> atributo = new Atributo<T>(field);
			atributos.add(atributo);
		}
		return atributos;
	}
	
	/* Callbacks */

	private void callbackCrearCreacion() {
		Entidad<T> entidadCreada = visualizador.getCreado();
		try {
			guardarEntidad(entidadCreada);
		} catch (CasteoInvalidoException | ValorNoCumpleCondicionException e) {
			visualizador.mostrarErrorEnCrear(e.getMessage());
			return;
		}
		visualizador.cerrarPantallaCrear();
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackModificarModificacion() {
		Entidad<T> entidadModificada = visualizador.getModificado();
		try {
			actualizar(entidadModificada);
		} catch (CasteoInvalidoException | ValorNoCumpleCondicionException e) {
			visualizador.mostrarErrorEnModificar(e.getMessage());
			return;
		}
		visualizador.cerrarPantallaModificar();
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackCrearFiltrado() {
		visualizador.abrirPantallaCrear();
	}

	private void callbackModificarFiltrado() {
		Entidad<T> entidadFiltrada = visualizador.getFiltrado();
		visualizador.abrirPantallaModificar(entidadFiltrada);
	}
	
	private void callbackBorrarFiltrado() {
		Entidad<T> entidadFiltrada = visualizador.getFiltrado();
		try {
			eliminarEntidad(entidadFiltrada);
		} catch (CasteoInvalidoException | ValorNoCumpleCondicionException e) {
			visualizador.mostrarErrorEnModificar(e.getMessage());
		}
		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.actualizarFiltro(entidades);
	}

	private void callbackVolverCreacion() {
		visualizador.cerrarPantallaCrear();
	}

	private void callbackVolverModificacion() {
		visualizador.cerrarPantallaModificar();
	}
	
	private void callbackVolverFiltracion() {
		System.exit(0);
	}
	
	/* Base de datos */

	private void guardarEntidad(Entidad<T> entidad) throws CasteoInvalidoException, ValorNoCumpleCondicionException {
		entidad.setClase(clase);
		T objeto = entidad.crearObjeto();
		persistidor.guardar(objeto);
	}

	private void eliminarEntidad(Entidad<T> entidad) throws CasteoInvalidoException, ValorNoCumpleCondicionException {
		entidad.setClase(clase);
		T objeto = entidad.crearObjeto();
		persistidor.remover(objeto);
	}

	private void actualizar(Entidad<T> entidad) throws CasteoInvalidoException, ValorNoCumpleCondicionException {
		entidad.actualizarAtributos();
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
