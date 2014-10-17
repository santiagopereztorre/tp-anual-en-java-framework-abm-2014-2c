package utn.algo2;

import utn.algo2.baseDeDatos.Persistidor;
import utn.algo2.baseDeDatos.PersistidorEnMemoria;
import utn.algo2.core.ABMManager;


public class PruebaDeEscritorio {
	
	public static void main(String[] args) {
		
		Persistidor<Persona> persistidor = new PersistidorEnMemoria<Persona>();
		
		ABMManager<Persona> abm = new ABMManager<Persona>(persistidor);
		
		abm.ejecutar();	// El programa queda aca hasta qe se sale de la ventana
		
	}

}
