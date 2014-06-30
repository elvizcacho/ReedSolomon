package Codificador;

import java.awt.Event;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class TextInputNumeros extends JTextField{
	
	public TextInputNumeros(int jum){
		super(jum);
		this.addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent e){
			      char caracter = e.getKeyChar();
			      if((((caracter < '0') || (caracter > '9')) &&(caracter != '\b'))){//&&!(caracter==',') con comita
			         e.consume();
			      }
			   }
		});
	}
}
