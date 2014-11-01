package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.lang.reflect.*;
import java.util.Hashtable;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private T anObject;
	private Class<?> aClass;
	private Field[] fields;
	private Method[] methods;

	public ABMManager(T objeto, Persistidor<T> persistidor,
			Visualizador<T> visualizador) {
		this.anObject = objeto;
		this.aClass = objeto.getClass();
		this.fields = this.aClass.getFields();
		this.methods = this.aClass.getMethods();
		this.persistidor = persistidor;
		this.visualizador = visualizador;
		this.visualizador.setFields(fields);
	}

	/**
	 * Ejecuta el ABM Manager
	 */
	public void ejecutar() {
		Hashtable<String, String> hashConValores = visualizador.pantallaCrear();
		guardarEntidad(hashConValores);
		Hashtable<String, String> hashConValoresAModificar = recuperarEntidad();
		Hashtable<String, String> hashConValoresModificados = visualizador.pantallaModificar(hashConValoresAModificar);
		guardarEntidad(hashConValoresModificados);
	}

	private void guardarEntidad(Hashtable<String, String> hashConValores) {
		T entidadAGuardar = crearObjecto(aClass);
		for (Method method : methods) {
			if (isSetter(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = getValorAGuardar(hashConValores, nombreVariable);
				try {
					method.invoke(entidadAGuardar, valor);
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
		this.persistidor.guardar(entidadAGuardar);
	}
	
	private Hashtable<String, String> recuperarEntidad() {
		Hashtable<String, String> hashConValoresAModificar = new Hashtable<String, String>();
		T entidad = persistidor.obtener();
		for (Method method : methods) {
			if (isGetter(method) && !isGetClass(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = null;
				try {
					valor = (String) method.invoke(entidad);
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
				hashConValoresAModificar.put(nombreVariable.toLowerCase(), valor);
			}
		}
		return hashConValoresAModificar;
	}

	private String getValorAGuardar(Hashtable<String, String> datos,
			String nombre) {
		return datos.get(nombre.toLowerCase());
	}

	private String getNombreVariable(Method method) {
		return method.getName().substring("set".length());
	}

	private boolean isSetter(Method method) {
		return method.getName().startsWith("set");
	}

	private boolean isGetter(Method method) {
		return method.getName().startsWith("get");
	}

	private boolean isGetClass(Method method) {
		return method.getName().startsWith("getClass");
	}

	private T crearObjecto(Class<?> unaClase) {
		try {
			return (T) unaClase.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
}
