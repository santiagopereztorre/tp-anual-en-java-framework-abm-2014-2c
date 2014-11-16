package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
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
	private Runnable callbackModificacion;
	private Runnable callbackCreacion;

	private enum Actions {
		FILTRAR, MODIFICAR, CREAR,
	}

	public PantallaFiltrado(ArrayList<Atributo<T>> fields) {
		super(fields);
	}

	/* Visual */

	protected void agregarTabla(ArrayList<Atributo<T>> fields) {
		modeloTabla = new ModeloTabla<T>();
		modeloTabla.setColumnNames(fields);

		table = new JTable(modeloTabla);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	protected void agregarBotones() {
		Panel panelBotones = new Panel();
		panelBotones
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.getContentPane().add(panelBotones);

		JButton botonFiltrar = new JButton("Filtrar");
		botonFiltrar.setActionCommand(Actions.FILTRAR.name());
		botonFiltrar.addActionListener(this);
		panelBotones.add(botonFiltrar);

		JButton botonModificar = new JButton("Modificar");
		botonModificar.setActionCommand(Actions.MODIFICAR.name());
		botonModificar.addActionListener(this);
		panelBotones.add(botonModificar);

		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Actions.CREAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}

	/* Actions */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Actions.CREAR.name())
			crear();
		if (e.getActionCommand() == Actions.MODIFICAR.name())
			modificar();
		if (e.getActionCommand() == Actions.FILTRAR.name())
			filtrar();
	}

	private void crear() {
		callbackCreacion.run();
	}

	private void modificar() {
		int row = table.getSelectedRow();
		if (row == -1)
			return;
		entidad = modeloTabla.getEntidadAt(row);
		callbackModificacion.run();
	}

	private void filtrar() {
		List<Entidad<T>> entidadesFiltradas = new ArrayList<Entidad<T>>(
				entidades);
		for (Atributo<T> field1 : fields) {
			String texto = referenciasACamposDeTexto.get(field1).getText();
			Field field = field1.getField();
			if (texto != null) {
				entidadesFiltradas = entidadesFiltradas
						.stream()
						.filter((Entidad<T> entidad) -> entidad.getValor(field).toString()
								.startsWith(texto))
						.collect(Collectors.toList());
			}
		}
		modeloTabla.actualizarEntidades(entidadesFiltradas);
	}

	/* Callbacks */

	public void onModificar(Runnable modificacion) {
		this.callbackModificacion = modificacion;
	}

	public void onCrear(Runnable creacionFiltrado) {
		this.callbackCreacion = creacionFiltrado;
	}

	/* Getters and Setters */

	public void cargarEntidades(List<Entidad<T>> entidades) {
		this.entidades = entidades;
		modeloTabla.actualizarEntidades(entidades);
	}

}
