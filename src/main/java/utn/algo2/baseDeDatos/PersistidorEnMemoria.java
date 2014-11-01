package utn.algo2.baseDeDatos;

import java.util.ArrayList;
import java.util.List;

import utn.algo2.Persona;

public class PersistidorEnMemoria<T> implements Persistidor<T> {

	private List<T> objetos = new ArrayList<T>();
	
	public void add(T object) {
		System.out.println(object);
		this.objetos.add(object);
	}

}
