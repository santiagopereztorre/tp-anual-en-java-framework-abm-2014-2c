package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.swing.ui.PantallaInicio;

public class VisualizadorSwing implements Visualizador {
	
	private Field[] fields;
	
	public void run() {
		new PantallaInicio(fields);
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

}
