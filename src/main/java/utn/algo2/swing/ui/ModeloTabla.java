package utn.algo2.swing.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class ModeloTabla<T> extends AbstractTableModel {

	private String[] columnNames = {};
	private Object[][] data = {};
	private List<Entidad<T>> entidadesPorFila = new ArrayList<Entidad<T>>();
	private ArrayList<Atributo<T>> atributos;
	
	/* Obligatorios */

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	/* Getters and Setters */

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public void setNombresDeColumnas(ArrayList<Atributo<T>> atributos) {
		this.atributos = atributos;
		this.columnNames = new String[atributos.size()];
		int i = 0;
		for (Atributo<T> atributo : atributos) {
			columnNames[i] = atributo.getName();
			i++;
		}
		actualizarTabla();
	}
	
	/* Interfaz */
	
	public Entidad<T> getEntidadAt(int fila) {
		return entidadesPorFila.get(fila);
	}

	public void actualizarEntidades(List<Entidad<T>> entidades) {
		if (entidades.isEmpty()) {
			actualizarData(new Object[0][0]);
			return;
		}
		Integer cantidadAtributos = atributos.size();
		Integer cantidadEntidades = entidades.size();
		Object[][] data = new Object[cantidadEntidades][cantidadAtributos];
		for (int i = 0; i < cantidadEntidades; i++) {
			Entidad<T> entidad = entidades.get(i);
			for (int j = 0; j < cantidadAtributos; j++) {
				data[i][j] = entidad.getValor(atributos.get(j));
			}
		}
		this.entidadesPorFila = entidades;
		actualizarData(data);
	}
	
	/* Complementario */
	
	private void actualizarTabla() {
		fireTableDataChanged();
	}
	
	private void actualizarData(Object[][] data) {
		this.data = data;
		actualizarTabla();
	}
}
