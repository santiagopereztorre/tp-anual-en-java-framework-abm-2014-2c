package utn.algo2.core;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.visualizacion.Visualizador;

import java.lang.reflect.*;
import java.util.Hashtable;

public class ABMManager<T> {

	private Persistidor<T> persistidor;
	private Visualizador<T> visualizador;
	private Class<?> aClass;
	private Field[] fields;
	private Method[] methods;

	public ABMManager(T objeto, Persistidor<T> persistidor,
			Visualizador<T> visualizador) {
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
		Entidad entidadCreada = visualizador.pantallaCrear();
		guardarEntidad(entidadCreada);
		
		Entidad entidadAModificar = recuperarEntidad();
		
		Entidad entidadModificada = visualizador.pantallaModificar(entidadAModificar);
		guardarEntidad(entidadModificada);
	}

	private void guardarEntidad(Entidad entidadCreada) {
		T entidadAGuardar = crearObjeto(aClass);
		for (Method method : methods) {
			if (isSetter(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = getValorAGuardar(entidadCreada.getHashConValores(), nombreVariable);
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
	
	private Entidad recuperarEntidad() {
		Hashtable<String, String> hashConValoresAModificar = new Hashtable<String, String>();
		T objecto = persistidor.obtener();
		for (Method method : methods) {
			if (isGetter(method) && !isGetClass(method)) {
				String nombreVariable = getNombreVariable(method);
				String valor = null;
				try {
					valor = (String) method.invoke(objecto);
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
		Entidad entidad = new Entidad();
		entidad.setHashConValores(hashConValoresAModificar);
		return entidad;
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

	@SuppressWarnings("unchecked")
	private T crearObjeto(Class<?> unaClase) {
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
