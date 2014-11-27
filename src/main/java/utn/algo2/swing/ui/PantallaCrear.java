package utn.algo2.swing.ui;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;
import utn.algo2.exception.ValorNoCumpleCondicionException;
import utn.algo2.validaciones.NotNull;

@SuppressWarnings("serial")
public class PantallaCrear<T> extends Pantalla<T>{

	public PantallaCrear(ArrayList<Atributo<T>> atributos) {
		super(atributos);
	}
	
	/* Visual */
	
	public void borrarCampos() {
		for(Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
		    JTextField value = entry.getValue();
		    value.setText("");
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
		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Action.CREAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}

	@Override
	protected void verificarMasPosibilidades(String actionCommand) {
				
		if (actionCommand == Action.CREAR.name()){
			try{
				crear();
			}catch (ValorNoCumpleCondicionException e1) {
				e1.getMessage();
			}
		}	

	}
	
	/* Actions */

	private void crear() throws ValorNoCumpleCondicionException {
		entidad = new Entidad<T>();
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			if(entry.getKey().esNotNull()){
				NotNull noNulo = new NotNull();
				if(!noNulo.evaluar(entry.getValue().getText())){
					mostrarError(entry.getKey().getName() + " no puede ser nulo");
					throw new ValorNoCumpleCondicionException(entry.getKey().getName() + " no puede ser nulo");
				}
			}
			entidad.setValor(entry.getKey(), entry.getValue().getText());
		}
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
