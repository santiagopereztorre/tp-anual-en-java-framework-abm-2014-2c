package utn.algo2.baseDeDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utn.algo2.Persona;

public class PersistidorArchivo implements Persistidor<Persona> {
	
	String dir = "x";

	@Override
	public void guardar(Persona objecto) {
		
		try {
			FileOutputStream fos = new FileOutputStream(dir + "/" + objecto.toString());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject((Serializable) objecto);
			oos.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover(Persona objeto) {
		try {
			File f = new File(dir + "/" + objeto.toString());
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Persona obtener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Persona> obtenerTodo() {
		try {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			File f = new File(dir);
			String[] arr = f.list();
			
			List<Persona> personas = new ArrayList<>();
			
			if (arr == null) 
				return personas;
			
			for (String s : arr) 
			{
				fis = new FileInputStream(dir + "/" + s);
				ois = new ObjectInputStream(fis);
				personas.add((Persona) ois.readObject());
				ois.close();
				fis.close();
			}
			
			return personas;
			

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


}
