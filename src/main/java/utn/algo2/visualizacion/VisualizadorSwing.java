package utn.algo2.visualizacion;

import java.lang.reflect.Field;
import java.util.List;

import utn.algo2.core.Entidad;
import utn.algo2.swing.ui.PantallaCrear;
import utn.algo2.swing.ui.PantallaFiltrado;
import utn.algo2.swing.ui.PantallaModificar;

public class VisualizadorSwing<T> implements Visualizador<T> {

	private Field[] fields;
	private PantallaCrear<T> pantallaCrear = null;
	private PantallaModificar<T> pantallaModificar = null;
	private PantallaFiltrado<T> pantallaFiltrado = null;

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public void abrirPantallaCrear() {
		if (pantallaCrear == null) {
			pantallaCrear = new PantallaCrear<T>(fields);
		}
		pantallaCrear.inicializar();
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
		pantallaModificar.inicializar();
		pantallaModificar.cargarCampos(entidadAModificar);
		pantallaModificar.setVisible(true);
		
	}

	public void cerrarPantallaModificar() {
		pantallaModificar.setVisible(false);
	}

	public Entidad<T> getModificado() {
		return pantallaModificar.getEntidad();
	}
	
	public void abrirPantallaFiltrado(List<Entidad<T>> entidades) {
		if (pantallaFiltrado == null) {
			pantallaFiltrado = new PantallaFiltrado<T>(fields);
		}
		pantallaFiltrado.cargarEntidades(entidades);
		pantallaFiltrado.setVisible(true);
		
	}

	public void cerrarPantallaFiltrado() {
		pantallaFiltrado.setVisible(false);
	}

	public Entidad<T> getFiltrado() {
		return pantallaFiltrado.getEntidad();
	}

}
