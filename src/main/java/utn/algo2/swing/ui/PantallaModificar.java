package utn.algo2.swing.ui;

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

	protected void agregarBotones() {
		JButton botonCrear = new JButton("Modificar");
		botonCrear.addActionListener(this);
		this.add(botonCrear);
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
