package utn.algo2.visualizacion;

import java.util.ArrayList;
import java.util.List;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;
import utn.algo2.swing.ui.PantallaCrear;
import utn.algo2.swing.ui.PantallaFiltrado;
import utn.algo2.swing.ui.PantallaModificar;

public class VisualizadorSwing<T> implements Visualizador<T> {

	private ArrayList<Atributo<T>> atributos;
	private PantallaCrear<T> pantallaCrear = null;
	private PantallaModificar<T> pantallaModificar = null;
	private PantallaFiltrado<T> pantallaFiltrado = null;
	
	private Entidad<T> entidadModificada;

	public void setFields(ArrayList<Atributo<T>> atributos) {
		this.atributos = atributos;
	}

	public void abrirPantallaCrear() {
		if (pantallaCrear == null) {
			pantallaCrear = new PantallaCrear<T>(atributos);
		}
		pantallaCrear.borrarCampos();
		pantallaCrear.setVisible(true);
	}

	public void cerrarPantallaCrear() {
		pantallaCrear.setVisible(false);
	}

	public Entidad<T> getCreado() {
		return pantallaCrear.getEntidad();
	}

	public void abrirPantallaModificar(Entidad<T> entidadAModificar) {
		if (pantallaModificar == null) {
			pantallaModificar = new PantallaModificar<T>(atributos);
		}
		this.setEntidadModificada(entidadAModificar);
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
			pantallaFiltrado = new PantallaFiltrado<T>(atributos);
		}
		pantallaFiltrado.cargarEntidades(entidades);
		pantallaFiltrado.setVisible(true);
		
	}

	public void cerrarPantallaFiltrado() {
		pantallaFiltrado.setVisible(false);
	}

	public Entidad<T> getFiltrado() {
		entidadModificada = pantallaFiltrado.getEntidad();
		return entidadModificada;
	}
	
	/* Callbacks */

	@Override
	public void onCrear(Runnable callback) {
		if (pantallaCrear == null) {
			pantallaCrear = new PantallaCrear<T>(atributos);
		}
		pantallaCrear.onCrear(callback);
		
	}

	@Override
	public void onModificar(Runnable callback) {
		if (pantallaModificar == null) {
			pantallaModificar = new PantallaModificar<T>(atributos);
		}
		pantallaModificar.onModificar(callback);
	}

	@Override
	public void onCrearFiltrado(Runnable callback) {
		if (pantallaFiltrado == null) {
			pantallaFiltrado = new PantallaFiltrado<T>(atributos);
		}
		pantallaFiltrado.onCrear(callback);
	}

	@Override
	public void onModificarFiltrado(Runnable callback) {
		if (pantallaFiltrado == null) {
			pantallaFiltrado = new PantallaFiltrado<T>(atributos);
		}
		pantallaFiltrado.onModificar(callback);
	}
	
	@Override
	public void onBorrarFiltrado(Runnable callback) {
		if (pantallaFiltrado == null) {
			pantallaFiltrado = new PantallaFiltrado<T>(atributos);
		}
		pantallaFiltrado.onBorrar(callback);
	}

	@Override
	public void onVolverCrear(Runnable callback) {
		pantallaCrear.onVolver(callback);
	}

	@Override
	public void onVolverModificar(Runnable callback) {
		pantallaModificar.onVolver(callback);
	}

	@Override
	public void onVolverFiltrar(Runnable callback) {
		pantallaFiltrado.onVolver(callback);
	}
	
	/* Necesario */

	@Override
	public void actualizarFiltro(List<Entidad<T>> entidades) {
		pantallaFiltrado.cargarEntidades(entidades);
	}

	public Entidad<T> getEntidadModificada() {
		return entidadModificada;
	}

	public void setEntidadModificada(Entidad<T> entidadModificada) {
		this.entidadModificada = entidadModificada;
	}
	
	/* Errores */

	public void mostrarErrorEnCrear(String mensaje) {
		this.pantallaCrear.mostrarError(mensaje);
	}

	public void mostrarErrorEnModificar(String mensaje) {
		this.pantallaModificar.mostrarError(mensaje);
	}

}
