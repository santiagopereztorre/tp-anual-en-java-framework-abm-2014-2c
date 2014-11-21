package utn.algo2.baseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class PersistidorEnMemoria<T> implements Persistidor<T> {

	private List<T> objetos = new ArrayList<T>();
	
	public void guardar(T objeto) {
		System.out.println("Guarde persona: " + objeto); // TODO eliminar (solo para pruebas)
		this.objetos.add(objeto);
	}
	
	public void remover(T objeto) {
		System.out.println("Borre: " + objeto);
		objetos.remove(objeto);
	}

	public T obtener() {
		T objeto = this.objetos.get(0);
		this.remover(objeto);
		return objeto;
	}

	public List<T> obtenerTodo() {
		return objetos;
	}

}
