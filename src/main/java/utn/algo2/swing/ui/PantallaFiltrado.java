package utn.algo2.swing.ui;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaFiltrado<T> extends Pantalla<T> implements ActionListener {

	private JTable table;
	private List<Entidad<T>> entidades;
	private ModeloTabla<T> modeloTabla;
	private Runnable callbackModificar;
	private Runnable callbackCrear;
	private Runnable callbackBorrar;
	
	public PantallaFiltrado(ArrayList<Atributo<T>> atributos) {
		super(atributos);
	}

	/* Visual */

	protected boolean esEditable(Atributo<T> atributo) {
		return true;
	}

	protected boolean hayQueAgregarlo(Atributo<T> atributo) {
		return atributo.esFiltrable();
	}

	protected void agregarTabla(ArrayList<Atributo<T>> atributos) {
		modeloTabla = new ModeloTabla<T>();
		modeloTabla.setNombresDeColumnas(atributos);

		table = new JTable(modeloTabla);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		this.add(new JScrollPane(table));
	}

	protected void agregarBotones(Panel panelBotones) {

		JButton botonFiltrar = new JButton("Filtrar");
		botonFiltrar.setActionCommand(Action.FILTRAR.name());
		botonFiltrar.addActionListener(this);
		panelBotones.add(botonFiltrar);
		
		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Action.CREAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);

		JButton botonModificar = new JButton("Modificar");
		botonModificar.setActionCommand(Action.MODIFICAR.name());
		botonModificar.addActionListener(this);
		panelBotones.add(botonModificar);

		JButton botonBorrar = new JButton("Borrar");
		botonBorrar.setActionCommand(Action.BORRAR.name());
		botonBorrar.addActionListener(this);
		panelBotones.add(botonBorrar);
	}

	/* Actions */

	@Override
	protected void verificarMasPosibilidades(String actionCommand) {
		if (actionCommand == Action.CREAR.name())
			crear();
		if (actionCommand == Action.MODIFICAR.name())
			modificar();
		if (actionCommand == Action.BORRAR.name())
			borrar();
		if (actionCommand == Action.FILTRAR.name())
			filtrar();
	}

	private void crear() {
		callbackCrear.run();
	}

	private void modificar() {
		getEntidadSeleccionada();
		callbackModificar.run();
	}

	private void borrar() {
		getEntidadSeleccionada();
		callbackBorrar.run();
	}

	private void getEntidadSeleccionada() {
		int fila = table.getSelectedRow();
		if (fila == -1)
			return;
		entidad = modeloTabla.getEntidadAt(fila);
	}

	private void filtrar() {
		List<Entidad<T>> entidadesFiltradas = new ArrayList<Entidad<T>>(
				entidades);
		for (Atributo<T> atributo : atributos) {
			String valorIngresado = referenciasACamposDeTexto.get(atributo)
					.getText();
			if (valorIngresado != null) {
				entidadesFiltradas = entidadesFiltradas
						.stream()
						.filter((Entidad<T> entidad) -> entidad
								.getValor(atributo).toString()
								.startsWith(valorIngresado))
						.collect(Collectors.toList());
			}
		}
		modeloTabla.actualizarEntidades(entidadesFiltradas);
	}

	/* Callbacks */

	public void onCrear(Runnable callback) {
		this.callbackCrear = callback;
	}

	public void onModificar(Runnable callback) {
		this.callbackModificar = callback;
	}

	public void onBorrar(Runnable callback) {
		this.callbackBorrar = callback;
	}

	/* Getters and Setters */

	public void cargarEntidades(List<Entidad<T>> entidades) {
		this.entidades = entidades;
		modeloTabla.actualizarEntidades(entidades);
	}

}
