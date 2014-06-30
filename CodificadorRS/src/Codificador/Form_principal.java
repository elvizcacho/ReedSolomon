package Codificador;

import javax.swing.*;
import javax.swing.event.*;

import org.omg.CORBA.portable.ValueOutputStream;

import java.awt.*;
import java.awt.event.*;
import java.io.Console;


public class Form_principal extends JFrame implements ActionListener, ChangeListener, ItemListener{
    
	private JMenuBar barra1;
	private JRadioButtonMenuItem itmalfas, itmbinario, itmconversor;
    private JMenu modo;
    private JPanel psuperior, pinferior, pmedio;
    private JButton num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, suma, mult, igual, clear, borrar;
    private JTextField textBox1;
    private Label text1;
    private JComboBox<Object> bits;
    private JRadioButton binario,alfa;
    private ButtonGroup select,menu;
    private String numero="0";
    int num,opcion;
    int numero1,numero2, ayuda, ayuda2, operacion, oper, ayuda3=0;
    int bit[]=new int[8];
    
    public Form_principal(){
    	
    	super("Calculadora Galois Field");
    	barra1= new JMenuBar();
        modo= new JMenu("Modo");
        menu= new ButtonGroup();
        itmalfas= new JRadioButtonMenuItem("Alfas",true);
        itmbinario= new JRadioButtonMenuItem("Binario");  
        itmconversor= new JRadioButtonMenuItem("Conversor");
        bits=new JComboBox<Object>(new Object[]{3, 4, 5, 6, 7, 8});
        binario=new JRadioButton("Binario");
        alfa=new JRadioButton("Alfa",true);
        select=new ButtonGroup();
        text1= new Label("Seleccione el numero de bits: ");
        text1.setAlignment(Label.RIGHT);;
        textBox1 = new JTextField(20);
        textBox1.setHorizontalAlignment(SwingConstants.RIGHT);
        num1 = new JButton("1");
        num2 = new JButton("2");
        num3 = new JButton("3");
        num4 = new JButton("4");
        num5 = new JButton("5");
        num6 = new JButton("6");
        num7 = new JButton("7");
        num8 = new JButton("8");
        num9 = new JButton("9");
        num0 = new JButton("0");
        suma = new JButton("+");
        mult = new JButton("x");
        igual = new JButton("=");
        clear = new JButton("C");
        borrar = new JButton("<-");
        psuperior=new JPanel();
        pinferior=new JPanel();
        pmedio=new JPanel();
        opcion=2;
        textBox1.setText("0");
        galoisfield.bits=3;
        galoisfield.crearGF();
        iniciar();
    }
    
    public void iniciar(){
    	setSize(250,300);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	barra1.add(modo);
    	barra1.add(new JSeparator());
    	barra1.add(text1);
    	barra1.add(bits);
    	bits.addItemListener(this);
    	modo.add(itmalfas);
    	itmalfas.addActionListener(this); 
    	modo.add(itmbinario);
    	itmbinario.addActionListener(this); 
    	modo.add(itmconversor);
    	itmconversor.addActionListener(this);
    	menu.add(itmbinario);
    	menu.add(itmalfas);
    	menu.add(itmconversor);
    	select.add(binario);
    	select.add(alfa);
    	construyePanelSuperior();
    	construyePanelmedio();
    	construyePanelInferior();
    	setJMenuBar(barra1);
    	setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(psuperior);
        add(pmedio);
        add(pinferior);
        binario.setVisible(false);
        alfa.setVisible(false);
        pack();
        setVisible(true);
    }
    
    public void construyePanelSuperior(){
    	psuperior.setLayout(new FlowLayout());
    	psuperior.add(textBox1);
    	//psuperior.add(binario);binario.addChangeListener(this);
    	//psuperior.add(alfa);alfa.addChangeListener(this);
    }
    
    public void construyePanelmedio(){
    	pmedio.setLayout(new FlowLayout());
    	//psuperior.add(textBox1);
    	pmedio.add(binario);binario.addChangeListener(this);
    	pmedio.add(alfa);alfa.addChangeListener(this);
    }
    
    public void construyePanelInferior(){
    	pinferior.setLayout(new GridLayout(5,4,8,8));
    	pinferior.add(num7);num7.addActionListener(this);
    	pinferior.add(num8);num8.addActionListener(this);
    	pinferior.add(num9);num9.addActionListener(this);
    	pinferior.add(num4);num4.addActionListener(this);
    	pinferior.add(num5);num5.addActionListener(this);
    	pinferior.add(num6);num6.addActionListener(this);
    	pinferior.add(num1);num1.addActionListener(this);
    	pinferior.add(num2);num2.addActionListener(this);
    	pinferior.add(num3);num3.addActionListener(this);
    	pinferior.add(clear);clear.addActionListener(this);
    	pinferior.add(num0);num0.addActionListener(this);
    	pinferior.add(borrar);borrar.addActionListener(this);
    	pinferior.add(suma);suma.addActionListener(this);
    	pinferior.add(mult);mult.addActionListener(this);
    	pinferior.add(igual);igual.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getSource()==itmbinario) {
    		if(opcion!=1)numero="0";
    		num2.setEnabled(false);num3.setEnabled(false);num4.setEnabled(false);num5.setEnabled(false);num6.setEnabled(false);num7.setEnabled(false);num8.setEnabled(false);num9.setEnabled(false);
    		binario.setVisible(false);alfa.setVisible(false);
    		opcion=1;
    	}
        if (e.getSource()==itmalfas) {
        	if(opcion!=2)numero="0";
        	num2.setEnabled(true);num3.setEnabled(true);num4.setEnabled(true);num5.setEnabled(true);num6.setEnabled(true);num7.setEnabled(true);num8.setEnabled(true);num9.setEnabled(true);
        	binario.setVisible(false);alfa.setVisible(false);
        	opcion=2;
        }
        if (e.getSource()==itmconversor) {
        	if(opcion!=3)numero="0";
        	binario.setVisible(true);alfa.setVisible(true);
        	opcion=3;
        }
        if (e.getSource()==num1) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"1";
        }
        if (e.getSource()==num2) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"2";
        }
        if (e.getSource()==num3) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"3";
        }
        if (e.getSource()==num4) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"4";
        }
        if (e.getSource()==num5) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"5";
        }
        if (e.getSource()==num6) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"6";
        }
        if (e.getSource()==num7) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"7";
        }
        if (e.getSource()==num8) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"8";
        }
        if (e.getSource()==num9) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"9";
        }
        if (e.getSource()==num0) {
        	if (operacion!=0){operacion=0;numero="0";}numero=numero+"0";
        }
        if (e.getSource()==clear) {
        	numero="0";numero1=0;numero2=0;
        }
        if (e.getSource()==borrar) {
        	numero=numero.substring(0, numero.length()-1);
        }
        
        num=Integer.parseInt(numero);
    	textBox1.setText(Integer.toString(num));
    	
    	
        if (e.getSource()==suma) {
        	if(operacion==0){
        		if(ayuda==0){numero1=num;ayuda=1;}
        		else{numero2=num;numero1=calcular();numero=Integer.toString(numero1);}	
        	}
        	operacion=1;
        	oper=1;
        }
        if (e.getSource()==mult) {
        	if(operacion==0){
        		if(ayuda==0){numero1=num;ayuda=1;}
        		else {numero2=num;numero1=calcular();numero=Integer.toString(numero1);}	
        	}
        	operacion=2;
        	oper=2;
        }
        if (e.getSource()==igual) {
        	numero2=num;
        	calcular();
        	numero1=0;numero2=0;numero="0";ayuda=0; ayuda2=0; operacion=0; oper=0;
    	}
    }
  
	public void stateChanged(ChangeEvent e) {
		if (binario.isSelected() && ayuda3==0) {
			num2.setEnabled(false);num3.setEnabled(false);num4.setEnabled(false);num5.setEnabled(false);num6.setEnabled(false);num7.setEnabled(false);num8.setEnabled(false);num9.setEnabled(false);
			num=desconvertir(num);ayuda3=1;
		}
		if (alfa.isSelected() && ayuda3==1) {
			num2.setEnabled(true);num3.setEnabled(true);num4.setEnabled(true);num5.setEnabled(true);num6.setEnabled(true);num7.setEnabled(true);num8.setEnabled(true);num9.setEnabled(true);	    
			num=convertir(num);ayuda3=0;
		}
		operacion=1;
    	textBox1.setText(Integer.toString(num));
	}
	
	public void itemStateChanged(ItemEvent e) {
		galoisfield.bits=bits.getSelectedIndex()+3;
		System.out.println(galoisfield.bits);
		galoisfield.crearGF();
	}
	
	public int calcular(){
		int resultado=0;
		int help;
		if(opcion==1){
			numero1=convertir(numero1);System.out.println(numero1);
			numero2=convertir(numero2);System.out.println(numero2);
		}
		if(opcion<3){
			if(verificar(numero1)==1 && verificar(numero2)==1){
				if(oper==2){resultado=(numero1+numero2)%galoisfield.gfextend;}
				if(oper==1){help=galoisfield.gf[numero1]^galoisfield.gf[numero2];resultado=galoisfield.inv_gf[help];}
		
			}
		}
		if(opcion==1){resultado=desconvertir(resultado);}
		textBox1.setText(Integer.toString(resultado));
		return resultado;
	}
    
	public int convertir(int a){
		int resultado=0;
		int help = a;
		int help2=0;
		for(int i=0;i<7;i++){help2=(int) Math.pow(10, (7-i));bit[i]=help/help2;help=help%help2;}bit[7]=help;
		for(int i=0;i<8;i++){resultado=(int) (resultado+(bit[i]*Math.pow(2, (7-i))));}
		if(verificar(resultado)==1){resultado=galoisfield.inv_gf[resultado];return resultado;}
		else return 0;
	}
	
	public int desconvertir(int a){
		int resultado=0,help=0;
		if(verificar(a)==1)help=galoisfield.gf[a];
		int help2=0;
		for(int i=0;i<8;i++){help2=help%2;help=help/2;resultado=resultado+(help2*(int) Math.pow(10, i));}
		return resultado;
	}
	
	public int verificar(int a){
		if(a<galoisfield.gfextend){return 1;}
		else{JOptionPane.showMessageDialog( null, "El número no pertenece al campo de Galois" , "No se puede realizar la operación", JOptionPane.PLAIN_MESSAGE);numero="0";numero1=0;numero2=0;return 0;}
	}
		
    public static void main(String[] args) {
       Form_principal marco = new Form_principal();
       marco.setBounds(20,20,400,300);
       marco.setVisible(true);
    }

}