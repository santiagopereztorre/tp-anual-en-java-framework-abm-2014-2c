package utn.algo2.core;

import java.util.Hashtable;

public class Entidad {

	private Hashtable<String, String> hashConValores = new Hashtable<String, String>();

	public Hashtable<String, String> getHashConValores() {
		return hashConValores;
	}

	public void setHashConValores(Hashtable<String, String> hashConValores) {
		this.hashConValores = hashConValores;
	}
}
