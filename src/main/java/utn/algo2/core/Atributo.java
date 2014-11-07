package utn.algo2.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Atributo<T> {

	private Field field;
	private Object valor;

	public Atributo(Field aKey) {
		this.field = aKey;
		field.setAccessible(true);
	}

	public Atributo(Field aKey, Object aValue) {
		this.field = aKey;
		this.valor = aValue;
		field.setAccessible(true);
	}
	
	/* Metodos */

	public void setIn(T destino) {
		
		Constructor<?> constructor = null;
		Object valorField = null;
		
		try {
			constructor = field.getType().getConstructor(String.class);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			valorField = constructor.newInstance(valor);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		try {
			field.set(destino, valorField);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getValorFrom(T fuente) {
		try {
			valor = field.get(fuente).toString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Complementarios */
	
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

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}
