package Codificador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class TextInputNumerosComas extends JTextField {

	public TextInputNumerosComas(int col) {
		super(col);
		this.addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent e){
			      char caracter = e.getKeyChar();
			      if((((caracter < '0') || (caracter > '9')) &&(caracter != '\b'))&&!(caracter==',')){
			         e.consume();
			      }
			   }
		});
	}
}
