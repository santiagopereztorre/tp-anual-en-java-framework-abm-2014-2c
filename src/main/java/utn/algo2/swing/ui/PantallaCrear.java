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
public class PantallaCrear<T> extends Pantalla<T> implements ActionListener {

	public PantallaCrear(ArrayList<Atributo<T>> atributos) {
		super(atributos);
	}
	
	/* Visual */
	
	protected void agregarBotones(Panel panelBotones) {
		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Action.CREAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}
	
	public void borrarCampos() {
		for(Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
		    JTextField value = entry.getValue();
		    value.setText("");
		}
	}
	
	/* Actions */

	@Override
	protected void verificarMasPosibilidades(String actionCommand) {
		if (actionCommand == Action.CREAR.name())
			crear();
	}

	private void crear() {
		entidad = new Entidad<T>();
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			entidad.setValor(entry.getKey(), entry.getValue().getText());
		}
		callback.run();
	}

	/* Callback */

	public void onCrear(Runnable callback) {
		this.callback = callback;
	}
}
