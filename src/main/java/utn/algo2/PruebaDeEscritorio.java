package utn.algo2;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.baseDeDatos.PersistidorEnMemoria;
import utn.algo2.core.ABMManager;
import utn.algo2.visualizacion.Visualizador;
import utn.algo2.visualizacion.VisualizadorSwing;

public class PruebaDeEscritorio {
	
	public static void main(String[] args) {
		
		Persistidor<Persona> persistidor = new PersistidorEnMemoria<Persona>();
		
		Visualizador visualizador = new VisualizadorSwing();
		
		Persona persona = new Persona();
		
		ABMManager<Persona> abm = new ABMManager<Persona>(persona, persistidor, visualizador);
		
		abm.ejecutar();	// El programa queda aca hasta qe se sale de la ventana
	}

}
