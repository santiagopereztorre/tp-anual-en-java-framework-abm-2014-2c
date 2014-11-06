package utn.algo2.swing.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class Pantalla<T> extends JDialog implements ActionListener {

	protected Field[] fields;
	protected Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	protected Entidad<T> entidad;
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notComplete = lock.newCondition();

	public Pantalla(Field[] fields) {
		this.fields = fields;

		getContentPane().setLayout(new GridLayout(0, 2));

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);

			JTextField textField = new JTextField();
			this.add(textField);

			referenciasATextField.put(field, textField);
		}
	}

	public void inicializar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.MODELESS);
		this.entidad = new Entidad<T>();

	}

	public void actionPerformed(ActionEvent e) {
		lock.lock();
		try {
			for (Field field : fields) {
				JTextField textoField = referenciasATextField.get(field);
				entidad.putValor(field, textoField.getText());
			}
			notFull.signal();
		} finally {
			lock.unlock();
		}
	}

	public Entidad<T> getEntidad() {
		lock.lock();
		try {
			if (entidad.isEmpty())
				notFull.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return entidad;
	}
}
