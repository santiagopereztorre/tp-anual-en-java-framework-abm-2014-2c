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
		this.visualizador.setFields(atributos);
	}
	
	/* Interfaz */

	public void ejecutar() {
		Runnable creacion = () -> callbackCreacion();
		visualizador.onCrear(creacion);

		Runnable modificacion = () -> callbackModificacion();
		visualizador.onModificar(modificacion);

		Runnable creacionFiltrado = () -> callbackCreacionFiltrado();
		visualizador.onCrearFiltrado(creacionFiltrado);

		Runnable modificacionFiltrado = () -> callbackModificacionFiltrado();
		visualizador.onModificarFiltrado(modificacionFiltrado);
		
		Runnable borrarFiltrado = () -> callbackBorrarFiltrado();
		visualizador.onBorrarFiltrado(borrarFiltrado);

		Runnable volverCrear = () -> callbackVolverCrear();
		visualizador.onVolverCrear(volverCrear);
		
		Runnable volverModificar = () -> callbackVolverModificar();
		visualizador.onVolverModificar(volverModificar);
		
		Runnable volverFiltrar = () -> callbackVolverFiltrar();
		visualizador.onVolverFiltrar(volverFiltrar);

		List<Entidad<T>> entidades = recuperarTodasEntidades();
		visualizador.abrirPantallaFiltrado(entidades);
	}

	/* Complementarios */

	private ArrayList<Atributo<T>> convertirFieldsAAtributos() {
		Field[] fields = this.clase.getDeclaredFields();
		ArrayList<Atributo<T>> atributos = new ArrayList<Atributo<T>>();
		for (Field field : fields) {
			Atributo<T> atributo = new Atributo<T>(field);
			atributos.add(atributo);
		}
		return atributos;
	}
	
	/* Callbacks */

	private void callbackCreacion() {
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

	private void callbackModificacion() {
		Entidad<T> entidadModificada = visualizador.getModificado();
		Entidad<T> entidadVieja = visualizador.getFiltrado();
		try {
			actualizar(entidadVieja, entidadModificada);
		} catch (CasteoInvalidoException | ValorNoCumpleCondicionException e) {
			System.out.println(e.getMessage());
			visualizador.mostrarErrorEnModificar(e.getMessage());
			return;
		}
		visualizador.cerrarPantallaModificar();
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

	private void callbackVolverCrear() {
		visualizador.cerrarPantallaCrear();
	}

	private void callbackVolverModificar() {
		visualizador.cerrarPantallaModificar();
	}
	
	private void callbackVolverFiltrar() {
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

	private void actualizar(Entidad<T> entidadVieja,
			Entidad<T> entidadModificada) throws CasteoInvalidoException, ValorNoCumpleCondicionException {
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
