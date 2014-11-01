package utn.algo2.baseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class PersistidorEnMemoria<T> implements Persistidor<T> {

	private List<T> objetos = new ArrayList<T>();
	
	public void add(T object) {
		this.objetos.add(object);
	}

}
