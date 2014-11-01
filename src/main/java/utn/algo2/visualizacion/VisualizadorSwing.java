package utn.algo2.visualizacion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map.Entry;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.swing.ui.PantallaCrear;

public class VisualizadorSwing<T> implements Visualizador<T> {

	private Field[] fields;
	private T anObject;
	private Class<T> aClass;
	private Persistidor<T> persistidor;

	public void run() {
		PantallaCrear crear = new PantallaCrear(fields);
		Hashtable<String, String> datos = crear.getDatos();
		Method[] methods = aClass.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String nombre = method.getName().substring("set".length());
				String valor = datos.get(nombre.toLowerCase());
				try {
					method.invoke(anObject, valor);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		persistidor.add(anObject);
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public void setObjeto(Object objeto) {
		aClass = (Class<T>) objeto.getClass();
		T elemento = null;
		try {
			elemento = (T) aClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.anObject = elemento;
	}

	public void setPersistidor(Persistidor<T> persistidor) {
		this.persistidor = persistidor;
	}

}
