package utn.algo2.baseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class PersistidorEnMemoria<T> implements Persistidor<T> {

	private List<T> objetos = new ArrayList<T>();

	public T obtener() {
		T objeto = this.objetos.get(0);
		this.remover(objeto);
		return objeto;
	}

	public List<T> obtenerTodo() {
		return objetos;
	}
	
	public void guardar(T objeto) {
		System.out.println("Guarde persona: " + objeto); // TODO eliminar (solo para pruebas)
		this.objetos.add(objeto);
	}
	
	public void modificar(T viejo, T nuevo) {
		System.out.println("Cambie: " + viejo + " por " + nuevo);
		int pos = objetos.indexOf(viejo);
		objetos.set(pos, nuevo);
	}

	public void remover(T objeto) {
		objetos.remove(objeto);
	}

}
