package Codificador;

import java.util.List;
import java.util.ArrayList;
import app.Com;
import app.Parameters;
import core.SerialPort;

public class Conexion {

	static SerialPort puerto = new SerialPort(); 
	static List<String> listaPuertos; 
	static Com comx;
	public static String recibido = "";
	public static String caracter = "";
	
	public static Boolean inicializacion(String puerto,String baudrate) {
		Boolean check = false;
		Parameters settings;
		try {
			settings = new Parameters();
			settings.setPort(puerto); 
			settings.setBaudRate(baudrate);
			comx = new Com(settings);
			check=true;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			check=false;
		} 
		return check;
	}
	
	public static ArrayList<String> puertosDisponibles(){
		ArrayList<String> puertos = new ArrayList<String>();
		try {
			listaPuertos = puerto.getFreeSerialPort();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Puertos Disponibles:\n");
		for (String string : listaPuertos) { 
			//System.out.println(string);
			puertos.add(string);
		}
		return puertos;
	}
	
	public static String leer(int limit){
		int a=0;
		int i=0;
		recibido="";
		while(i<limit){
			try {
				a =(int)comx.receiveSingleChar();
				
				//System.out.printf("%c",a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			recibido +=String.valueOf((char)a);
			i++;
			} //si llega "0x00" para de leer
		return recibido;
				
	}
	
	public static void escribir(char a){
		try {
			comx.sendSingleData(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
