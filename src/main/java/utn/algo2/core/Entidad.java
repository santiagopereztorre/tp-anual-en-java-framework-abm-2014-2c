package utn.algo2.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entidad<T> {

	private Class<?> unaClase;
	private List<Atributo<T>> atributos = new ArrayList<Atributo<T>>();
	
	public T crearObjeto() {
		T otroObjeto = crearInstancia(this.unaClase);
		for (Atributo<T> atributo : this.atributos) {
			atributo.setIn(otroObjeto);
		}
		return otroObjeto;
	}

	private void actualizarHashConFields(T unObjeto) {
		for (Field field: this.unaClase.getFields()) {
			Atributo<T> atributo = new Atributo<T>(field, this.unaClase);
			atributo.getValorFrom(unObjeto);
			this.atributos.add(atributo);
		}
	}
	
	/* Reflection */
	
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
	
	/* Setters y Getters */

	public void setObjeto(T unObjeto) {
		actualizarHashConFields(unObjeto);
	}

	public Class<?> getClase() {
		return unaClase;
	}

	public void setClase(Class<?> unaClase) {
		this.unaClase = unaClase;
		this.atributos.forEach((Atributo<T> atributo) -> atributo.setClase(unaClase));
	}

	public void putValor(Field aKey, String aValue) {
		Atributo<T> atributo = new Atributo<T>(aKey, aValue, this.unaClase);
		addIfNotExists(atributo);
	}

	private void addIfNotExists(Atributo<T> atributo) {
		if (this.atributos.contains(atributo)) {
			int index = this.atributos.indexOf(atributo);
			this.atributos.set(index, atributo);
		} else {
			this.atributos.add(atributo);
		}
	}

	public String getValor(Field aKey) {
		Atributo<T> atributoBuscado = new Atributo<T>(aKey, "");
		int index = this.atributos.indexOf(atributoBuscado);
		return this.atributos.get(index).getValor();
	}

	public boolean isEmpty() {
		return this.atributos.isEmpty();
	}
}
