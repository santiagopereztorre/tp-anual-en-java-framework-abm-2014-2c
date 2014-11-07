package utn.algo2.core;

import java.lang.reflect.Field;

public class Atributo<T> {

	private Field field;
	private String valor;

	public Atributo(Field aKey) {
		this.field = aKey;
	}

	public Atributo(Field aKey, String aValue) {
		this.field = aKey;
		this.valor = aValue;
	}
	
	/* Metodos */

	public void setIn(T destino) {
		try {
			field.set(destino, valor);
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
			valor = (String) field.get(fuente);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
