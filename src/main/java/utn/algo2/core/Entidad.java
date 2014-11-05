package utn.algo2.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entidad<T> {

	private Class<?> clase;
	private List<Atributo<T>> atributos = new ArrayList<Atributo<T>>();
	
	/* Interfaz */
	
	public T crearObjeto() {
		T objeto = crearInstancia(this.clase);
		atributos.forEach((Atributo<T> atributo) -> atributo.setIn(objeto));
		return objeto;
	}

	private void actualizarAtributosFrom(T unObjeto) {
		for (Field field: this.clase.getFields()) {
			Atributo<T> atributo = new Atributo<T>(field, this.clase);
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
		actualizarAtributosFrom(unObjeto);
	}

	public Class<?> getClase() {
		return clase;
	}

	public void setClase(Class<?> unaClase) {
		this.clase = unaClase;
		this.atributos.forEach((Atributo<T> atributo) -> atributo.setClase(unaClase));
	}

	public void putValor(Field unField, String unValor) {
		Atributo<T> atributo = new Atributo<T>(unField, unValor, this.clase);
		addIfNotExists(atributo);
	}

	public String getValor(Field unField) {
		Atributo<T> atributoBuscado = new Atributo<T>(unField);
		int index = this.atributos.indexOf(atributoBuscado);
		return this.atributos.get(index).getValor();
	}

	public boolean isEmpty() {
		return this.atributos.isEmpty();
	}
	
	/* Complementarios */

	private void addIfNotExists(Atributo<T> atributo) {
		if (this.atributos.contains(atributo)) {
			int index = this.atributos.indexOf(atributo);
			this.atributos.set(index, atributo);
		} else {
			this.atributos.add(atributo);
		}
	}
}
