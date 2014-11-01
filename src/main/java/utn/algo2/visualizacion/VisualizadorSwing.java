package utn.algo2.visualizacion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map.Entry;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.core.Entidad;
import utn.algo2.swing.ui.PantallaCrear;
import utn.algo2.swing.ui.PantallaModificar;

public class VisualizadorSwing<T> implements Visualizador<T> {

	private Field[] fields;

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public Entidad pantallaCrear() {
		PantallaCrear crear = new PantallaCrear(fields);
		return crear.getDato();
	}

	public Entidad pantallaModificar(
			Hashtable<String, String> hashConValoresAModificar) {
		PantallaModificar<T> modificar = new PantallaModificar<T>(fields, hashConValoresAModificar);
		return modificar.getDato();
	}

}
