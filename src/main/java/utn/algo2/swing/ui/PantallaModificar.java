package utn.algo2.swing.ui;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaModificar<T> extends Pantalla<T>{

	public PantallaModificar(ArrayList<Atributo<T>> atributos) {
		super(atributos);
	}

	/* Visual */

	public void cargarCampos(Entidad<T> entidadAModificar) {
		entidad = entidadAModificar;
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			String valor = entidadAModificar.getValor(entry.getKey()).toString();
			entry.getValue().setText(valor);
		}
	}
	
	/* Redifiniciones */

	@Override
	protected boolean esEditable(Atributo<T> atributo) {
		return !atributo.esSoloLectura();
	}

	@Override
	protected boolean esVisible(Atributo<T> atributo) {
		return true;
	}
	
	@Override
	protected void agregarBotones(Panel panelBotones) {
		JButton botonCrear = new JButton("Modificar");
		botonCrear.setActionCommand(Action.MODIFICAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}

	@Override
	protected void verificarMasPosibilidades(String actionCommand) {
		if (actionCommand == Action.MODIFICAR.name())
			modificar();
	}
	
	/* Actions */

	private void modificar() {
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			entidad.actualizarValor(entry.getKey(), entry.getValue().getText());
		}
		callback.run();
	}

	/* Callbacks */

	public void onModificar(Runnable callback) {
		this.callback = callback;
	}

}
