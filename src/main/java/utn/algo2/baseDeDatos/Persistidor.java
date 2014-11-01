package utn.algo2.baseDeDatos;

public interface Persistidor<T> {

	public void guardar(T object);

	public T obtener();
}
