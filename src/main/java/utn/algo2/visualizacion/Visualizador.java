package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.baseDeDatos.Persistidor;

public interface Visualizador<T> {

	public void setFields(Field[] fields);
	public void setObjeto(T anObject);
	public void setClase(Class<?> aClass);
	public void setPersistidor(Persistidor<T> persistidor);
	public void run();
}
