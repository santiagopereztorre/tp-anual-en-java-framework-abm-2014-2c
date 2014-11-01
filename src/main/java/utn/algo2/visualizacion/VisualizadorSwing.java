package utn.algo2.visualizacion;

import java.lang.reflect.Field;

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
			Entidad entidad) {
		PantallaModificar<T> modificar = new PantallaModificar<T>(fields, entidad);
		return modificar.getDato();
	}

}
