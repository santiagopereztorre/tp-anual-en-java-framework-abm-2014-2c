package utn.algo2.visualizacion;

import java.lang.reflect.Field;
import java.util.Hashtable;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.core.Entidad;
import utn.algo2.swing.ui.PantallaCrear;

public interface Visualizador<T> {

	public void setFields(Field[] fields);
	public Entidad pantallaCrear();
	public Entidad pantallaModificar(Entidad entidadAModificar);
}
