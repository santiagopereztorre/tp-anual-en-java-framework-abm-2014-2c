package utn.algo2.swing.ui;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaModificar<T> extends Pantalla<T> implements ActionListener {

	public PantallaModificar(ArrayList<Atributo<T>> atributos) {
		super(atributos);
	}

	/* Visual */

	protected void agregarBotones(Panel panelBotones) {
		JButton botonCrear = new JButton("Modificar");
		botonCrear.setActionCommand(Action.MODIFICAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}
	
	/* Actions */

	@Override
	protected void verificarMasPosibilidades(String actionCommand) {
		if (actionCommand == Action.MODIFICAR.name())
			modificar();
	}

	private void modificar() {
		entidad = new Entidad<T>();
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			entidad.setValor(entry.getKey(), entry.getValue().getText());
		}
		callback.run();
	}

	/* Callbacks */

	public void onModificar(Runnable callback) {
		this.callback = callback;
	}

	/* Getters and Setters */

	public void cargarCampos(Entidad<T> entidadAModificar) {
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			String valor = entidadAModificar.getValor(entry.getKey()).toString();
			entry.getValue().setText(valor);
		}
	}
}
