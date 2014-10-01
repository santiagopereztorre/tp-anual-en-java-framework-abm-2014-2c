package utn.algo2;

public class PruebaDeEscritorio2 {
	
	public static void main(String[] args) {
		
		Persistidor persistidor = new PersistidorEnMemoria();
		
		Visualizador visualizador = new VisualizadorEnVentanas();
		
		ABMManager<Persona> abm = new ABMManager<Persona>(persistidor, visualizador);
		
		abm.ejecutar();	// El programa queda aca hasta qe se sale de la ventana
		
		System.out.println(persistidor.obtenerTodo()); 
	}

}
