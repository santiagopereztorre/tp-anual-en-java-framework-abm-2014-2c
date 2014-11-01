package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.swing.ui.PantallaCrear;

public class VisualizadorSwing implements Visualizador {
	
	private Field[] fields;
	
	public void run() {
		new PantallaCrear(fields);
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

}
