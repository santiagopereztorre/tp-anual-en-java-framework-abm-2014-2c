package utn.algo2.baseDeDatos;

public interface Persistidor<T> {

	public T obtener();
	public void guardar(T objecto);
	public void modificar(T viejo, T nuevo);
	public void remover(T objeto);
}
