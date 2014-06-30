package Codificador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class TextShowViz extends JTextField {

	public TextShowViz(int col) {
		super(col);
		this.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e){
			     e.consume();
			   }
		});
	}
}
