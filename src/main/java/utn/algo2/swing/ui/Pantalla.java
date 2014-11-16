package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public abstract class Pantalla<T> extends JDialog implements ActionListener {

	protected Entidad<T> entidad;
	protected ArrayList<Atributo<T>> atributos;
	protected Hashtable<Atributo<T>, JTextField> referenciasACamposDeTexto = new Hashtable<Atributo<T>, JTextField>();
	private JLabel labelError;
	protected Runnable callback;
	private Runnable callbackVolver;
	
	public Pantalla(ArrayList<Atributo<T>> atributos) {
		this.atributos = atributos;
		configurarPantalla();
		agregarCampoDeMensajeError();
		agregarCamposDeTexto(atributos);
		agregarTabla(atributos);
		agregarBotones();
	}

	/* Visual */

	private void configurarPantalla() {
		this.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 14));
		this.getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setBounds(100, 100, 426, 300);
		this.setModalityType(ModalityType.MODELESS);
		this.setSize(700, 300);

	}

	private void agregarCampoDeMensajeError() {
		Panel panel = new Panel();
		labelError = new JLabel();
		panel.add(labelError);
		getContentPane().add(panel);
	}
	
	public void mostrarError(String mensaje) {
		labelError.setText(mensaje);
	}

	private void agregarCamposDeTexto(ArrayList<Atributo<T>> atributos) {
		for (Atributo<T> atributo : atributos) {
			JLabel label = new JLabel(atributo.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);
			campoDeTexto.setEditable(esEditable(atributo));

//			JLabel labelnull = new JLabel("Nullable: " + !field.isAnnotationPresent(NotNull.class) + "   ");
			
			Panel panel = new Panel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(label);
			panel.add(campoDeTexto);
//			panel.add(labelnull);
			
//			if(field.isAnnotationPresent(Label.class)){
//				JLabel widget = new JLabel("Es label   ");
//				panel.add(widget);
//			}
//			
//			if(field.isAnnotationPresent(Control.class)){
//				JLabel widget = new JLabel("Es Control    ");
//				panel.add(widget);
//			}
//			
//			JLabel tienevalidacion = new JLabel("Tiene validacion: " + field.isAnnotationPresent(ValidacionPersonalizada.class)+ "   ");
//			panel.add(tienevalidacion);
//			
//			if(field.isAnnotationPresent(ValidacionPersonalizada.class)){
//				JLabel validacion = new JLabel("Validacion: " + obtengoValidacion(field).toString());
//				panel.add(validacion);
//			}
			
			if (hayQueAgregarlo(atributo))
				getContentPane().add(panel);

			referenciasACamposDeTexto.put(atributo, campoDeTexto);
		}
	}
	
	private void agregarBotones() {
		Panel panelBotones = new Panel();
		panelBotones
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		getContentPane().add(panelBotones);
		
		agregarBotones(panelBotones);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setActionCommand(Action.VOLVER.name());
		botonVolver.addActionListener(this);
		panelBotones.add(botonVolver);
	}
	
	/* Redefinibles */

	protected boolean esEditable(Atributo<T> atributo) {
		return !atributo.esSoloLectura();
	}

	protected boolean hayQueAgregarlo(Atributo<T> atributo) {
		return true;
	}
	
	protected void agregarTabla(ArrayList<Atributo<T>> atributos) {};
	
	protected abstract void agregarBotones(Panel panelBotones);

	/* Actions */

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Action.VOLVER.name())
			volver();
		
		verificarMasPosibilidades(e.getActionCommand());
	}
	
	protected abstract void verificarMasPosibilidades(String actionCommand);

	private void volver() {
		callbackVolver.run();
	}
	
	/* Callbacks */
	
	public void onVolver(Runnable callback) {
		this.callbackVolver = callback;
	}

	/* Getters and Setters */

	public Entidad<T> getEntidad() {
		return entidad;
	}
}
