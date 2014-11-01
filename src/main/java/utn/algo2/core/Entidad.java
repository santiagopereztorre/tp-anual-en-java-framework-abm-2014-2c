package utn.algo2.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class Entidad<T> {

	private Class<?> unaClase;
	private T unObjeto;
	private Hashtable<String, String> hashConValores = new Hashtable<String, String>();

	public Entidad() {

	}

	public Entidad(Class<?> unaClase) {
		this.unaClase = unaClase;
	}

	public T crearObjeto() {
		this.unObjeto = crearInstancia(this.unaClase);

		for (Method method : this.unaClase.getMethods()) {
			if (isSetter(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = this.getValor(nombreVariable);
				try {
					method.invoke(unObjeto, valor);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return unObjeto;
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

	private boolean isGetClass(Method method) {
		return method.getName().startsWith("getClass");
	}

	private boolean isGetter(Method method) {
		return method.getName().startsWith("get");
	}

	private boolean isSetter(Method method) {
		return method.getName().startsWith("set");
	}

	private String getNombreVariable(Method method) {
		return method.getName().substring("set".length());
	}

	public T getObjeto() {
		return this.unObjeto;
	}

	public void setObjeto(T unObjeto) {
		this.unObjeto = unObjeto;
		actualizarHashConValores();
	}

	private void actualizarHashConValores() {
		for (Method method : this.unaClase.getMethods()) {
			if (isGetter(method) && !isGetClass(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = null;
				try {
					valor = (String) method.invoke(this.unObjeto);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				this.setValor(nombreVariable, valor);
			}
		}
	}

	public Class<?> getClase() {
		return unaClase;
	}

	public void setClase(Class<?> unaClase) {
		this.unaClase = unaClase;
	}

	public Hashtable<String, String> getHashConValores() {
		return hashConValores;
	}

	public void setHashConValores(Hashtable<String, String> hashConValores) {
		this.hashConValores = hashConValores;
	}

	public void setValor(String aKey, String aValue) {
		this.hashConValores.put(aKey.toLowerCase(), aValue);
	}

	public String getValor(String aKey) {
		return this.hashConValores.get(aKey.toLowerCase());
	}

	public boolean isEmpty() {
		return hashConValores.isEmpty();
	}
}
