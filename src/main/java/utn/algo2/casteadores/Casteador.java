package utn.algo2.casteadores;

import utn.algo2.exception.CasteoInvalidoException;

public interface Casteador {
	
	public Object castear(String valor) throws CasteoInvalidoException;

}
