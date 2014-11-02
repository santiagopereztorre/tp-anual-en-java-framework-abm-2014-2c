package utn.algo2.visualizacion;

import java.lang.reflect.Field;

import utn.algo2.core.Entidad;
import utn.algo2.swing.ui.PantallaCrear;
import utn.algo2.swing.ui.PantallaModificar;

public class VisualizadorSwing<T> implements Visualizador<T> {

	private Field[] fields;
	private PantallaCrear<T> pantallaCrear = null;
	private PantallaModificar<T> pantallaModificar = null;

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public void abrirPantallaCrear() {
		if (pantallaCrear == null) {
			pantallaCrear = new PantallaCrear<T>(fields);
		}
		pantallaCrear.setVisible(true);
	}

	public void cerrarPantallaCrear() {
		pantallaCrear.borrarCampos();
		pantallaCrear.setVisible(false);
	}

	public Entidad<T> getCreado() {
		return pantallaCrear.getEntidad();
	}

	public void abrirPantallaModificar(Entidad<T> entidadAModificar) {
		if (pantallaModificar == null) {
			pantallaModificar = new PantallaModificar<T>(fields);
		}
		pantallaModificar.cargarCampos(entidadAModificar);
		pantallaModificar.setVisible(true);
		
	}

	public void cerrarPantallaModificar() {
		pantallaModificar.setVisible(false);
	}

	public Entidad<T> getModificado() {
		return pantallaModificar.getEntidad();
	}

}
