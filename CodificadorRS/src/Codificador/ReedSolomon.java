package Codificador;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Double;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class ReedSolomon implements ActionListener{//implementando el listener de eventos
 
    private JButton bt1, bt2, bt3, bt4,bt5,bt6;
    private TextInputNumeros textBox1,textBox2;
    private TextInputNumerosComas textBox3;
    private TextShowViz textBox4,textBox8,textBox9;
    private TextShowViz textBox7;
    private TextInputNumeros textBox6;
    private TextInputNumerosPuntos textBox5;
    private Label text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11;
    private Checkbox checkBox1;
    private XYDataset ds;
    private JFreeChart chart;
    private ChartPanel cp;
    private JFrame jf;
    private JMenu menu;
    private JMenuBar barra;
    private JRadioButtonMenuItem calculadora;
    private ButtonGroup choices;
    private ArrayList<String> puerto=new ArrayList<String>();
    private JComboBox listaCom;
    private double[][] ruido;
    private String modo="ocio";
    private Boolean enviando=false;
    private int cruido=0;
    private String ruido2="";
    private Boolean lineal=true;
    
    public static String nl = System.getProperty("line.separator");
        	
    @SuppressWarnings("deprecation")
	public ReedSolomon(){//constructor de la clase
    	
    			
    	
    	jf = new JFrame("Codigo Reed Solomon");
        jf.setLayout(null);//Configurar como se dispondra el espacio del jframe
        Dimension d = new Dimension();//objeto para obtener el ancho de la pantalla
 
        textBox1 = new TextInputNumeros(3);
        textBox2 = new TextInputNumeros(3);
        textBox3 = new TextInputNumerosComas(3);
        textBox4=new TextShowViz(3);
        textBox5=new TextInputNumerosPuntos(3);
        textBox6=new TextInputNumeros(3);
        textBox7=new TextShowViz(3);
        textBox8=new TextShowViz(3);
        textBox9=new TextShowViz(3);
        
        text1= new Label("Inserte el valor de n:");
        text2= new Label("Inserte el valor de k:");
        text3= new Label("Inserte el mensaje a transmitir:");
        text4=new Label("Datos recibidos:");
        text5=new Label("1/sigma:");
        text6=new Label("Corrimiento:");
        text7=new Label("Tasa de error:");
        text8=new Label("Salida codificador:");
        text9=new Label("Entrada decodificador:");
        text10=new Label("Salida decodificador:");
        text11=new Label("Configuración de conexión:");
                        
        bt1 = new JButton("Generar codigo");
        bt2 = new JButton("Enviar");
        bt3=new JButton("Graficar Error");
        bt4=new JButton("Test RS");
        bt5=new JButton("Fijar Error");
        bt6=new JButton("Conectar");
                
        checkBox1=new Checkbox("Canal no lineal");
        barra= new JMenuBar();
        menu= new JMenu("Menu");
        calculadora= new JRadioButtonMenuItem("Calculadora",true);
        puerto=Conexion.puertosDisponibles();
        listaCom= new JComboBox(puerto.toArray());
        //PCTOFPGA.initCommy("COM6","781250"); //95.367 kB/s    
        double[][] data = { {0.1, 0.2, 0.3}, {1, 2, 3} };
        ds = createDataset(data);
        chart = ChartFactory.createXYLineChart("Distribución del error","x", "y", ds, PlotOrientation.VERTICAL, true, true,false);
        cp = new ChartPanel(chart);
        
                
        //Position Settings
        
        text1.setBounds(40,30,111,20);
        text2.setBounds(210,30,111,20);
        text3.setBounds(20,100,200, 20);
        text4.setBounds(20,175,200,20);
        text8.setBounds(20,175+20,200,20);
        text9.setBounds(20,175+60,200,20);
        text10.setBounds(20,175+100,200,20);
        text5.setBounds(20,250+100,70,20);
        text6.setBounds(20,280+100,70,20);
        text7.setBounds(20,380+100,100,20);
        text11.setBounds(400,450,200,20);
      
                        
        textBox1.setBounds(155,30,30,20);
        textBox2.setBounds(325,30, 30, 20);
        textBox3.setBounds(20,120,200+80,20);
        textBox4.setBounds(20,175+40,200+80,20);
        textBox8.setBounds(20,175+80,200+80,20);
        textBox9.setBounds(20,175+120,200+80,20);
        textBox5.setBounds(120,250+100,30,20);
        textBox6.setBounds(120,280+100,30,20);
        textBox7.setBounds(200,380+100,100,20);
        
        bt1.setBounds(150,60,130,25);
        bt2.setBounds(20,145,100,25);
        bt3.setBounds(20,310+100,130,25);
        bt4.setBounds(20,410+100,130,25);
        bt5.setBounds(170,310+100,130,25);
        bt6.setBounds(400,510,130,25);
        
        bt2.setEnabled(false);
        bt3.setEnabled(false);
        bt4.setEnabled(false);
        bt5.setEnabled(false);
        
        barra.setBounds(0,0,100,25);
        checkBox1.setBounds(20, 345+100, 130, 25);
        listaCom.setBounds(400, 480, 130, 25);
        
        cp.setBounds(400,30,600,400);
        
        //////
        jf.add(text1);
        jf.add(text2);
        jf.add(text3);
        jf.add(text4);
        jf.add(text5);
        jf.add(text6);
        jf.add(text7);
        jf.add(text8);
        jf.add(text9);
        jf.add(text10);
        jf.add(text11);
                        
        jf.add(textBox1);
        jf.add(textBox2);
        jf.add(textBox3);
        jf.add(textBox4);
        jf.add(textBox5);
        jf.add(textBox6);
        jf.add(textBox7);
        jf.add(textBox8);
        jf.add(textBox9);
        
        jf.add(bt1);
        jf.add(bt2);
        jf.add(bt3);
        jf.add(bt4);
        jf.add(bt5);
        jf.add(bt6);
        
        jf.add(checkBox1);
        jf.add(listaCom);
        jf.add(cp);
        jf.add(barra);
        barra.add(menu);
        menu.add(calculadora);
                
        //Adding Listeners
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
        bt4.addActionListener(this);
        bt5.addActionListener(this);
        bt6.addActionListener(this);
        calculadora.addActionListener(this);
        
 
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finaliza el programa cuando se da click en la X        
        jf.setResizable(false);//para configurar si se redimensiona la ventana
        jf.setLocation((int) ((d.getWidth()/2)+290), 50);//para ubicar inicialmente donde se muestra la ventana (x, y)
        jf.setSize(1024, 600);//configurando tamaño de la ventana (ancho, alto)
        jf.setVisible(true);//configurando visualización de la venta
    }
         
    public static void main(String[] args)throws IOException {
         ReedSolomon gj = new ReedSolomon();//uso de constructor para la ventana
   }
    
    
    @Override
    public void actionPerformed(ActionEvent e){//sobreescribimos el metodo del listener
    	if(e.getSource()==calculadora ){
    		Form_principal marco = new Form_principal();
    	}
    	
    	if(e.getActionCommand().equals("Conectar")){
    	System.out.println(listaCom.getSelectedItem().toString());
    		if(PCTOFPGA.initCommy(listaCom.getSelectedItem().toString(),"781250")){//95.367 kB/s
    			bt2.setEnabled(true);
    	        bt3.setEnabled(true);
    	        bt4.setEnabled(true);
    	        bt5.setEnabled(true);
    		}
    	}
    	
        if(e.getActionCommand().equals("Generar codigo")){//ESTE ES EL EVENTO DEL BOTON HOLA
        	int n = Integer.parseInt(textBox1.getText());
        	int k = Integer.parseInt(textBox2.getText());
        	try {
				Reed_Solomon.RS(n,k);
				JOptionPane.showMessageDialog(null, "Codigo RS("+n+","+k+") creado satisfactoriamente");
				textBox1.setText("");
				textBox2.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        if(e.getActionCommand().equals("Enviar")){
        	int n=0;
        	int k=0;
        	int m=0;
        	try {
        		n = Integer.parseInt(textBox1.getText());
            	k = Integer.parseInt(textBox2.getText());
            	m = (int)Math.ceil((Math.log(n))/(Math.log(2)));
            	if(n<k){
            		JOptionPane.showMessageDialog(null, "n no puede ser menor a k");
            	}
            	else if(n==k){
            		JOptionPane.showMessageDialog(null, "n no puede ser igual a k");
            	}
            	else if(((n-k)/2.0)<1){
            		JOptionPane.showMessageDialog(null, "(n-k)/2 no puede ser menor a 1");
            	}
            	else if(!validarMensaje(n,k,m)){
            		if(modo.equals("enviar")){
            			JOptionPane.showMessageDialog(null, " El mensaje no es de longitud k.\n Recuerde que los datos a enviar se escriben separados\n por comas.\n\n e.g:7,3,2 para un codigo RS(7,3).");
            		}
            		
            	}
            	else{
            		if(modo.equals("ruido")&&enviando){
            			
            		}
            		else{
            			loQueHaceEnviar(n, k);System.out.println("ENVIO");
            		}
            		
            		if(modo.equals("enviar")&&!enviando){
            			enviando=true;
            			JOptionPane.showMessageDialog(null, " Ahora puede enviar palabras codigo.\n\n Recuerde que estas deben ser de longitud k y  que los datos a enviar se escriben separados\n por comas.\n\n e.g:7,3,2 para un codigo RS(7,3).");
            		}
            		else if(modo.equals("ruido")&&!enviando){
            			enviando=true;
            			JOptionPane.showMessageDialog(null, " Ahora puede enviar el vector de ruido.\n\n Recuerde que este debe de ser de longitud 2*n*m y  que los datos a enviar se escriben separados\n por comas.\n\n"+((lineal)?"Tenga presente que esta trabajando con un canal lineal.":"Tenga presente que esta trabajando con un canal no-lineal.")+"\n\ne.g:0,0,0,0,255,255,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 para un codigo RS(7,3).");
            		}
            		else if(modo.equals("ruido")&&enviando){
            			if((2*n*m-cruido)<=0){
            				String[] arreglar = ruido2.split(",");
            		    	String  f2="";
            		    	String  f3="";
            		    	for(int j=0;j<n;j++){
            		    		for(int i=0;i<m;i++){
            		        		f2=arreglar[(i*2)+m*j*2]+","+arreglar[(i*2+1)+m*j*2]+","+f2;
            		        	}
            		    		f3+=f2;
            		    		f2="";
            		    	}
            				f3+="0,0,0,0,47";
            				textBox3.setText(f3);
            				loQueHaceEnviar(n, k);System.out.println("ENVIO");
            				textBox3.setText("");
            				JOptionPane.showMessageDialog(null, "Ya has fijado el vector de ruido, ahora estas en modo enviar");
            				modo="enviar";
            			}
            			else{
            				JOptionPane.showMessageDialog(null, "Te faltan "+(2*n*m-cruido)+" Bytes para terminar de fijar el ruido");
            			}
            			
            			
            		}
            	}
            } catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Olvido ingresar alguno de los parámetros del código RS (n ó k)");
			}
        		
        }
        	
        	
        		
        	
   
        
        if(e.getActionCommand().equals("Graficar Error")){
        	graficar(crearCurvaRuido((int)Math.ceil(Double.parseDouble(textBox1.getText())),Double.parseDouble(textBox5.getText()),(int)Math.ceil(Double.parseDouble(textBox6.getText()))));
        }
        
        if(e.getActionCommand().equals("Test RS")){
        	testingNoise(1000);
        }
        
        if(e.getActionCommand().equals("Fijar Error")){
        	int n = Integer.parseInt(textBox1.getText());
        	int k = Integer.parseInt(textBox2.getText());
        	String cadena = ((checkBox1.getState())?"59":"58")+","+convOctetos(this.ruido)+"0,0,0,0,47";
        	System.out.println(cadena);
        	PCTOFPGA.Commy(cadena.split(","),k+n,false);
        	modo="enviar";
        }
        
    }
    
    private String convOctetos(double[][] ruido){
    	int n = Integer.parseInt(textBox1.getText());
    	int m = (int)Math.ceil((Math.log(n))/(Math.log(2)));
    	String f="";
    	for(int x=0;x<ruido[1].length;x++){
    		String a=(Integer.toHexString((int)ruido[1][x]));
    		String c="";
    		if((a.length()%2)!=0){
    			a="0"+a;
    		}
    		for(;a.length()!=4;){
    			a="0"+a;
    		}
    		c+=String.valueOf(a.toCharArray()[2]);
    		c+=String.valueOf(a.toCharArray()[3]);
    		c+=",";
    		c+=String.valueOf(a.toCharArray()[0]);
    		c+=String.valueOf(a.toCharArray()[1]);
    		String[] b = c.split(",");
    		b[0]=String.valueOf((Integer.parseInt(b[0],16)));
    		b[1]=String.valueOf((Integer.parseInt(b[1],16)));
    		for(String d:b){
    			f+=d+",";
    		}
    		
    	}
    	System.out.println(f);
    	//f="7,3,2,1,2,3,7,8,9,10,22,1,2,3,4,5,6";
    	String[] arreglar = f.split(",");
    	String  f2="";
    	String  f3="";
    	for(int j=0;j<n;j++){
    		for(int i=0;i<m;i++){
        		f2=arreglar[(i*2)+m*j*2]+","+arreglar[(i*2+1)+m*j*2]+","+f2;
        	}
    		f3+=f2;
    		f2="";
    	}
    	
    	
    	
    	return f3;
    }
    
    public Boolean validarMensaje(int n,int k,int m){
    	String[] cadena_entra = textBox3.getText().split(",");
    	Boolean retornar=false;
    	if(cadena_entra[0].equals("47")&&modo.equals("ocio")){
    		modo="enviar";
    		retornar=true;
    	}
    	else if(((cadena_entra[0].equals("58"))||(cadena_entra[0].equals("59")))&&modo.equals("ocio")){
    		lineal=(cadena_entra[0].equals("58"));
    		modo="ruido";
    		retornar=true;
    	}
    	else if(modo.equals("enviar")){
    		retornar=cadena_entra.length==k;
    	}
    	else if(modo.equals("ruido")){
    		
    		
    		if(cruido<=(2*n*m)){
    			for (String dato:cadena_entra){
    				cruido++;
        			ruido2+=dato+((cruido<(2*n*m))?",":"");
        		}
    			retornar=true;
    		}
    	}
    	return retornar;    	
    	
	}
    
    private void loQueHaceEnviar(int n,int k){

    	String[] cadena_entra = textBox3.getText().split(",");
    	String mostrar,mostrar1="";
    	mostrar="";
    	mostrar1="";
    	textBox4.setText(null);
    	textBox8.setText(null);
    	textBox9.setText(null);
    	char[] cadena_sale=PCTOFPGA.Commy(cadena_entra,k+n,true).toCharArray();
    	for(int i=0;i<cadena_sale.length;i++){
    		if(i<k){
    			mostrar+=Integer.toString((int)cadena_sale[i])+((i<cadena_sale.length-1)?",":"");	
    		}
    		else if(i<n+k){
    			mostrar1+=Integer.toString((int)cadena_sale[i])+((i<cadena_sale.length-1)?",":"");
    		}
    		
    	}
    	mostrar+=this.nl;
    	mostrar1+=this.nl;
    	
    	if(modo.equals("enviar")&&cadena_entra.length!=1){
    	  textBox4.setText(codificar(textBox3.getText(),n,k));
    	}
    	textBox9.setText(mostrar);
    	textBox8.setText(mostrar1);
    	
		try {
			FileWriter write = new FileWriter("prueba.txt");
			write.write(mostrar);
			write.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
    }
    
    private void testingNoise(int pruebas){
    	int n = Integer.parseInt(textBox1.getText());
    	int k = Integer.parseInt(textBox2.getText());
    	int m=(int)Math.ceil((Math.log(n))/(Math.log(2)));
    	
    	
    	
    	int errores=0;
    	String llego="";
    	//String comp="7,3,2";
    	//String comp="14,6,5,2,10,11,6,1,11";
    	//String comp2="111011010101110100001";
    	    	
    	char[][] data1=new char[pruebas][k];
    	char[][] data2 = new char[pruebas][n*m];
    	int[] erroresBit = new int[n*m];
    	    	
    	for(int j=0;j<pruebas;j++){
    		/////////////LO NUEVO///////////////////////////////
    		int aleatorio=0;
        	String loqueEntra="";
        	String loqueSale="";
        	String cadena_bits2="";
        	String bitsfull="";
        	for(int i=0;i<k;i++){
        		aleatorio=1+(int)Math.round(((Math.random())*(n-1)));
        		loqueEntra+=Integer.toString(aleatorio)+((i<k-1)?",":"");
        	}
        	System.out.println(loqueEntra);
        	
        	loqueSale=codificar(loqueEntra,n,k);
        	System.out.println(loqueSale);
        	String[] loqueSaleArray=loqueSale.split(",");
        	
        	for(int i=0;i<n;i++){
        		
    			cadena_bits2=Integer.toBinaryString(Integer.parseInt(loqueSaleArray[i]));
    			while(cadena_bits2.length()<m){//Concatenador de ceros
    				cadena_bits2="0"+cadena_bits2;
    			}
    			bitsfull+=cadena_bits2;
    		}

        	String comp2=bitsfull;
        	String comp=loqueEntra;
    		   		
    		///////////////////LO VIEJO///////////////////////////
    		String cadena_bits="";
    		char[] cadena_sale=PCTOFPGA.Commy(comp.split(","),k+n,true).toCharArray();
    		//System.out.println(cadena_sale);
    	
    		for(int i=0;i<k;i++){
    			data1[j][i]=cadena_sale[i];
    		}
    	
    		for(int i=0;i<n;i++){
    			cadena_bits=Integer.toBinaryString((int)cadena_sale[i+k]);
    			while(cadena_bits.length()<m){//Concatenador de ceros
    				cadena_bits="0"+cadena_bits;
    			}
    			for(int h=0;h<m;h++){
    				data2[j][h+i*m]=cadena_bits.toCharArray()[h];
    				if(data2[j][h+i*m]!=comp2.toCharArray()[h+i*m]){
    					erroresBit[h+i*m]++;
    				}
    			}
    		}
    		llego="";
    		for(int h=0;h<k;h++){
				llego+=Integer.toString((int)data1[j][h])+((h<k-1)?",":"");
			}
    		System.out.println(llego);
    		System.out.println(data2[j]);
    		System.out.println(errores);
    		if(!llego.endsWith(comp)){
    			errores++;
    		}
    		  		
    	}
    	
    	DecimalFormat df = new DecimalFormat("0.000");
    	textBox7.setText(String.valueOf(df.format(((((double)errores)/pruebas)*100)))+"%");
    	String aux="";
    	double[][] auxd = new double[2][n*m];
    	
    	for(int bit=0;bit<(m*n);bit++){
    		auxd[0][bit]=(double)bit;
    	}
    	for(int h=0;h<(n*m);h++){
    		aux+=String.valueOf(erroresBit[h])+" ";
    		auxd[0][h]=(double)h;
    		auxd[1][h]=(double)erroresBit[h];
    	}
    	System.out.println(aux);
    	graficar(auxd);
    	
    	
    }
    
    private void graficar(double[][] data){
    	jf.remove(cp);
    	ds = createDataset(data);
        chart = ChartFactory.createXYLineChart("Distribución del error","x", "y", ds, PlotOrientation.VERTICAL, true, true,false);
        cp = new ChartPanel(chart);
        cp.setBounds(400,20,600,400);
        jf.add(cp);
        jf.repaint();
    }
    
    private static XYDataset createDataset(double[][] data) {
    	DefaultXYDataset ds = new DefaultXYDataset();
        ds.addSeries("series1", data);
        return ds;
    }
    
    private double[][] crearCurvaRuido(int n,double ax,int dez){
    	int m=(int)Math.ceil((Math.log(n))/(Math.log(2)));
    	double[][] data = new double[2][m*n];
    	double[][] data2 = new double[2][m*n];
    	ruido=new double[2][m*n];
    	for(int bit=0;bit<(m*n);bit++){
    		data[0][bit]=(double)bit;
    		data2[0][bit]=(double)bit;
			//System.out.printf("%f ", data[0][bit]);
		}
    	int prov=0;
    	for(int bit=0;bit<(m*n);bit++){
			prov=((int)Math.round((Math.exp(-Math.pow(ax*(data[0][bit]-((m*n-1)/2.0)), 2)))*(65535)));
			data[1][bit]=(double)prov;
			//System.out.printf("%f,",data[1][bit]);
		}
		dez=dez%(m*n);
		for(int bit=0;bit<(m*n);bit++){
			data2[1][bit]=((bit+dez)<(m*n))?data[1][bit+dez]:data[1][(bit+dez)-(m*n)];
			System.out.println(data2[1][bit]);
		}
		ruido=data2;
		return data2;
    }
    
    private String codificar(String loqueEntra,int n,int k){
    	int m=(int)Math.ceil((Math.log(n))/(Math.log(2)));
    	String salidaS="";
    	String[] cadena_entra = loqueEntra.split(",");
    	int[] user = new int[cadena_entra.length];
    	galoisfield.bits=m;
    	galoisfield.crearGF();
    	pol_generador.bits=m;
    	pol_generador.error=n-k;
    	pol_generador.crear_pg();
    	int[] pg=pol_generador.pg;
    	int[] omult= new int[n-k];
    	int[] registro=new int[n-k];
    	int[] salida=new int[n];
    	for(int i=0;i<cadena_entra.length;i++){
    		user[i]=Integer.parseInt(cadena_entra[i]);
    	}
    	for(int i=0;i<k;i++){
    		salida[i]=user[i];
    		int fb=(user[i]^registro[registro.length-1]);
    		for(int j=(n-k-1);j>0;j--){
    			omult[j]=pol_generador.mult(pg[j],fb);
    			registro[j]=omult[j]^registro[j-1];
    		}
    		omult[0]=pol_generador.mult(pg[0],fb);
			registro[0]=omult[0];
    		
    	}
    	for(int i=k;i<n;i++){
    		salida[i]=registro[n-1-i];
    	}
    	for(int i=0;i<salida.length;i++){
    		salidaS+=Integer.toString(salida[i])+((i<salida.length-1)?",":"");
    	}
    	return salidaS;
    	
    }
}