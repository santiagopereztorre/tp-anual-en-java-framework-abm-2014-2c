package utn.algo2.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

import utn.algo2.annotations.Filtrable;
import utn.algo2.annotations.Personalizada;
import utn.algo2.annotations.SoloLectura;
import utn.algo2.exception.CasteoInvalidoException;
import utn.algo2.exception.ValorNoCumpleCondicionException;

public class Atributo<T> {

	private Field field;
	private Object valor;

	public Atributo(Field field) {
		this.field = field;
		this.field.setAccessible(true);
	}

	public Atributo(Field field, Object valor) {
		this.field = field;
		this.field.setAccessible(true);
		this.valor = valor;
	}

	/* Metodos */

	public void setIn(T destino) throws CasteoInvalidoException,
			ValorNoCumpleCondicionException {

		if (valor.equals(""))
			return;

		Object valorCasteado = cast(valor, field.getType());

		evaluarAnotaciones(valorCasteado, destino);

		settearValor(valorCasteado, destino);
	}

	private Object cast(Object valor, Class<?> type) {
		Class<?> clase = null;
		Object casteador = null;
		Method metodoCastear = null;
		Object valorCasteado = null;

		try {
			clase = Class
					.forName("utn.algo2.casteadores." + type.getSimpleName() + "Casteador");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			casteador = clase.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}

		metodoCastear = getMetodo("castear", clase, String.class);

		try {
			valorCasteado = metodoCastear.invoke(casteador, valor);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return valorCasteado;
	}

	public void getValorFrom(T fuente) {
		try {
			valor = field.get(fuente).toString();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			valor = "";
		}
	}

	/* Complementarios */

	private void evaluarAnotaciones(Object valorCasteado, T destino)
			throws ValorNoCumpleCondicionException {

		for (Annotation a : field.getAnnotations()) {
			String nombreAnotacion = a.annotationType().getSimpleName();

			Method metodo = null;
			Object objeto = null;

			if (nombreAnotacion.equals("Personalizada")) {
				Personalizada personalizada = (Personalizada) a;

				metodo = getMetodo(personalizada.metodo(), destino.getClass(),
						valorCasteado.getClass());

				objeto = destino;

			} else {
				Class<?> clase = null;

				try {
					clase = Class.forName("utn.algo2.validaciones."
							+ nombreAnotacion);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				metodo = getMetodo("evaluar", clase, Object.class);

				try {
					objeto = clase.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}

			invocar(metodo, objeto, valorCasteado);
		}
	}

	private void invocar(Method metodo, Object objeto, Object valorArgumento)
			throws ValorNoCumpleCondicionException {

		boolean resultadoCondicion = true;

		try {
			resultadoCondicion = (boolean) metodo
					.invoke(objeto, valorArgumento);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		if (!resultadoCondicion) {
			throw new ValorNoCumpleCondicionException("Campo "
					+ field.getName() + " no cumple condicion "
					+ objeto.getClass().getSimpleName() + "."
					+ metodo.getName());
		}
	}

	private void settearValor(Object valorField, T destino) {
		try {
			field.set(destino, valorField);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/* Complementario */

	private Method getMetodo(String nombreMetodo, Class<?> clase,
			Class<?> claseArgumento) {

		Method metodo = null;

		try {
			metodo = clase.getMethod(nombreMetodo, claseArgumento);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return metodo;
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

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public String getName() {
		return field.getName();
	}

	// /*
	// * Pregunto si tiene annotations
	// */
	//
	// public boolean esNulable() {
	// return !this.field.isAnnotationPresent(NotNull.class);
	// }
	//
	// public boolean esControl() {
	// return !this.field.isAnnotationPresent(Control.class);
	// }
	//
	// public boolean esLabel() {
	// return !this.field.isAnnotationPresent(Label.class);
	// }
	//
	// public boolean tieneValidacion() {
	// return !this.field.isAnnotationPresent(ValidacionPersonalizada.class);
	// }
	//
	// public Validacion2 obtengoValidacion() {
	// ValidacionPersonalizada annotation = this.field
	// .getAnnotation(ValidacionPersonalizada.class);
	// return annotation.metodo();
	// }
	//
	// @SuppressWarnings("unchecked")
	// public boolean cumpleValidacion() {
	// if (this.tieneValidacion()) {
	// Validacion2 validacion = this.obtengoValidacion();
	// validacion.getValidador().evaluaValidacion(this.valor);
	// }
	// return true;
	// }

	/* Anotaciones */

	public boolean esSoloLectura() {
		return field.isAnnotationPresent(SoloLectura.class);
	}

	public boolean esFiltrable() {
		return field.isAnnotationPresent(Filtrable.class);
	}
}
