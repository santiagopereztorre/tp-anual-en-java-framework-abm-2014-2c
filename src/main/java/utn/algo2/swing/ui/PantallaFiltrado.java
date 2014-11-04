package utn.algo2.swing.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaFiltrado<T> extends JDialog {

	private ModeloTabla<T> modeloTabla = null;
	
	public PantallaFiltrado(Field[] fields) {
		getContentPane().setLayout(
				new GridLayout(1,0));

		this.modeloTabla = new ModeloTabla<T>();
		
		JTable table = new JTable(modeloTabla);
		modeloTabla.setColumnNames(fields);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
		
		setSize(400, 400);
	}

	public Entidad<T> getEntidad() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void cargarEntidades(List<Entidad<T>> entidades) {
		modeloTabla.setEntidades(entidades);
	}

}
