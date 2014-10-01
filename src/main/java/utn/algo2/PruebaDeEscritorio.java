package utn.algo2;

public class PruebaDeEscritorio {

	public static void main(String[] args) {
		
		ABMManager<Persona> manager = new ABMManager<Persona>();
		
		Persona personaCreada = manager.formularioAlta();
		
		Boolean fueModificado = manager.formularioModificion(personaCreada);
		
		Persona personaBuscada = manager.formularioBusqueda();
		
		Boolean fueBorrada = manager.borrar(personaCreada);
	}
}
