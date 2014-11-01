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

	public Entidad<T> pantallaCrear() {
		PantallaCrear<T> crear = new PantallaCrear<T>(fields);
		return crear.getEntidad();
	}

	public Entidad<T> pantallaModificar(
			Entidad<T> entidad) {
		PantallaModificar<T> modificar = new PantallaModificar<T>(fields, entidad);
		return modificar.getEntidad();
	}

}
