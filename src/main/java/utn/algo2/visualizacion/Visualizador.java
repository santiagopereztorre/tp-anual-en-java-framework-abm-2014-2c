package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.core.Entidad;

public interface Visualizador<T> {

	public void setFields(Field[] fields);
	public void abrirPantallaCrear();
	public void cerrarPantallaCrear();
	public Entidad<T> getCreado();
	public void abrirPantallaModificar(Entidad<T> entidadAModificar);
	public void cerrarPantallaModificar();
	public Entidad<T> getModificado();
}
