package utn.algo2.casteadores;

public class IntegerCasteador implements Casteador {

	@Override
	public Object castear(String valor) {
		Integer valorCasteado = new Integer(valor);
		return valorCasteado;
	}

}
