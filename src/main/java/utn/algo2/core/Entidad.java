package utn.algo2.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map.Entry;

public class Entidad<T> {

	private Class<?> unaClase;
	private T unObjeto;
	private Hashtable<Field, String> valoresFields = new Hashtable<Field, String>();

	public T crearObjeto() {
		this.unObjeto = crearInstancia(this.unaClase);
		
		for (Entry<Field, String> entry : this.valoresFields.entrySet()) {
			Method metodo = null;
			try {
				Class<?>[] args = new Class[1];
				args[0] = String.class;
				metodo = this.unaClase.getMethod("set" + capitalize(entry.getKey().getName()), args);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			try {
				metodo.invoke(this.unObjeto, entry.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return this.unObjeto;
	}

	private String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	@SuppressWarnings("unchecked")
	private T crearInstancia(Class<?> unaClase) {
		T objeto = null;
		try {
			objeto = (T) unaClase.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return objeto;
	}

	public T getObjeto() {
		return this.unObjeto;
	}

	public void setObjeto(T unObjeto) {
		this.unObjeto = unObjeto;
		actualizarHashConFields();
	}

	private void actualizarHashConFields() {
		for (Field field : this.unaClase.getFields()) {
			String valor = null;
			Class<?>[] args = new Class[0];
			Method metodo = null;
			try {
				metodo = this.unaClase.getMethod("get" + capitalize(field.getName()), args);
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				valor = (String) metodo.invoke(this.unObjeto);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			this.valoresFields.put(field, valor);
		}
	}

	public Class<?> getClase() {
		return unaClase;
	}

	public void setClase(Class<?> unaClase) {
		this.unaClase = unaClase;
	}

	public void setValor(Field aKey, String aValue) {
		this.valoresFields.put(aKey, aValue);
	}

	public String getValor(Field aKey) {
		return this.valoresFields.get(aKey);
	}

	public boolean isEmpty() {
		return valoresFields.isEmpty();
	}
}
