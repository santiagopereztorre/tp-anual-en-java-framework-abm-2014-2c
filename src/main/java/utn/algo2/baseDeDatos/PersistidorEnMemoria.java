package utn.algo2.baseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class PersistidorEnMemoria<T> implements Persistidor<T> {

	private List<T> objetos = new ArrayList<T>();

	public void guardar(T object) {
		System.out.println(object); // TODO eliminar (solo para pruebas)
		this.objetos.add(object);
	}

	public T obtener() {
		T entidad = this.objetos.get(0);
		objetos.remove(entidad);
		return entidad;
	}

}
