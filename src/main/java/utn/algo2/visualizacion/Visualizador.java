package utn.algo2.visualizacion;

import java.lang.reflect.Field;
import java.util.List;

import utn.algo2.core.Entidad;

public interface Visualizador<T> {

	/* Necesarios */
	
	public void setFields(Field[] fields);
	public void actualizarFiltro(List<Entidad<T>> entidades);

	/* Navegacion */
	
	public void abrirPantallaCrear();
	public void cerrarPantallaCrear();
	public void abrirPantallaModificar(Entidad<T> entidadAModificar);
	public void cerrarPantallaModificar();
	public void abrirPantallaFiltrado(List<Entidad<T>> entidades);
	public void cerrarPantallaFiltrado();

	/* Obtencion de datos */
	
	public Entidad<T> getCreado();
	public Entidad<T> getModificado();
	public Entidad<T> getFiltrado();
	
	/* Callbacks */
	
	public void onCrear(Runnable callback);
	public void onModificar(Runnable callback);
	public void onCrearFiltrado(Runnable callback);
	public void onModificarFiltrado(Runnable callback);
}
