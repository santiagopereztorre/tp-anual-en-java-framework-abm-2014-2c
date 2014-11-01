package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.core.Entidad;

public interface Visualizador<T> {

	public void setFields(Field[] fields);
	public Entidad<T> pantallaCrear();
	public Entidad<T> pantallaModificar(Entidad<T> entidadAModificar);
}
