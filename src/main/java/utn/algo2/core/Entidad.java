package utn.algo2.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entidad<T> {

	private Class<?> clase;
	private List<Atributo<T>> atributos = new ArrayList<Atributo<T>>();
	
	/* Interfaz */
	
	public T crearObjeto() {
		T objeto = crearInstancia(clase);
		atributos.forEach((Atributo<T> atributo) -> atributo.setIn(objeto));
		return objeto;
	}

	public void actualizarAtributosFrom(T unObjeto) {
		for (Field field: clase.getDeclaredFields()) {
			Atributo<T> atributo = new Atributo<T>(field);
			atributo.getValorFrom(unObjeto);
			atributos.add(atributo);
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

	public Class<?> getClase() {
		return clase;
	}

	public void setClase(Class<?> unaClase) {
		clase = unaClase;
	}

	public void setValor(Field unField, Object unValor) {
		Atributo<T> atributo = new Atributo<T>(unField, unValor);
		addIfNotExists(atributo);
	}

	public Object getValor(Field unField) {
		Atributo<T> atributoBuscado = new Atributo<T>(unField);
		int index = atributos.indexOf(atributoBuscado);
		return atributos.get(index).getValor();
	}

	public boolean isEmpty() {
		return atributos.isEmpty();
	}
	
	/* Complementarios */

	private void addIfNotExists(Atributo<T> atributo) {
		if (atributos.contains(atributo)) {
			int index = atributos.indexOf(atributo);
			atributos.set(index, atributo);
		} else {
			atributos.add(atributo);
		}
	}
}
