package utn.algo2.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Atributo<T> {

	private Field field;
	private String valor;
	private Class<?> clase;

	public Atributo(Field aKey) {
		this.field = aKey;
	}

	public Atributo(Field aKey, Class<?> unaClase) {
		this.field = aKey;
		this.clase = unaClase;
	}
	
	public Atributo(Field aKey, String aValue) {
		this.field = aKey;
		this.valor = aValue;
	}
	
	public Atributo(Field aKey, String aValue, Class<?> unaClase) {
		this.field = aKey;
		this.valor = aValue;
		this.clase = unaClase;
	}
	
	/* Metodos */

	public void setIn(T destino) {
		aplicarSetter(destino);
	}
	
	public void getValorFrom(T fuente) {
		aplicarGetter(fuente);
	}
	
	/* Complementarios */
	
	private Method obtenerSetter() {
		Method metodo = null;
		try {
			Class<?>[] args = new Class[1];
			args[0] = String.class;
			metodo = this.clase.getMethod("set" + capitalize(this.field.getName()), args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return metodo;
	}
	
	private Method obtenerGetter() {
		Method metodo = null;
		try {
			metodo = this.clase.getMethod("get" + capitalize(this.field.getName()), new Class[0]);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return metodo;
	}
	
	private void aplicarSetter(T objeto) {
		Method metodo = obtenerSetter();
		try {
			metodo.invoke(objeto, valor);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private void aplicarGetter(T unObjeto) {
		Method metodo = obtenerGetter();
		try {
			valor = (String) metodo.invoke(unObjeto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	/* Overrides */

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object otro) {
		if (otro instanceof Atributo) {
			Atributo<T> otroAtributo = (Atributo<T>) otro;
			if (this.field.getName().equals(otroAtributo.field.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/* Getters y Setters */

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public void setClase(Class<?> clase) {
		this.clase = clase;
	}

}
