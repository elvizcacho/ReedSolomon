package Codificador;

//import java.util.Scanner;



public class PCTOFPGA {

	
	public static Boolean initCommy(String port,String baudrate){
		return Conexion.inicializacion(port,baudrate);
	}
	
	public static String Commy(String[] aEnviar1,int limit,Boolean leer) {
		String aEnviar="";
		for(int i=0;i<aEnviar1.length;i++){
			aEnviar+=(char)(Integer.parseInt(aEnviar1[i]));
		}
		
		char[] cadena_enviar=aEnviar.toCharArray();
		for(char palabra:cadena_enviar){
			Conexion.escribir(palabra);
		}
				
		return (leer)?Conexion.leer(limit):"";
	}

}
