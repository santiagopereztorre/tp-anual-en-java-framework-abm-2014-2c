package utn.algo2.casteadores;

import utn.algo2.exception.CasteoInvalidoException;

public class IntegerCasteador implements Casteador {

	@Override
	public Object castear(String valor) throws CasteoInvalidoException {
		Integer valorCasteado = null;
		try {
			valorCasteado = Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			throw new CasteoInvalidoException(valor + " no puede ser casteado a Integer");
		}
		return valorCasteado;
	}

}
