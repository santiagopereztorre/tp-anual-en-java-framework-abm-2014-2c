package utn.algo2.visualizacion;

import java.util.ArrayList;
import java.util.List;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

public interface Visualizador<T> {

	/* Necesarios */
	
	public void setFields(ArrayList<Atributo<T>> atributos);
	public void actualizarFiltro(List<Entidad<T>> entidades);
	public Entidad<T> getEntidadModificada();
	public void setEntidadModificada(Entidad<T> entidadModificada);

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
	public void onVolverCrear(Runnable volverCrear);
	public void onVolverModificar(Runnable volverModificar);
	public void onVolverFiltrar(Runnable volverFiltrar);
	
	/* Errores */
	
	public void mostrarErrorEnCrear(String message);
	public void mostrarErrorEnModificar(String message);
}
