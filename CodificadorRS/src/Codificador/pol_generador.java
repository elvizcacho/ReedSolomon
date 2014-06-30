//Validado

package Codificador;

public class pol_generador {

	static int bits;
	static int error;
	static int gf[];
	static int inv_gf[];
	static int pg[];
	
	public static void crear_pg(){
		gf=galoisfield.gf;
		inv_gf=galoisfield.inv_gf;
		pg=new int[error+1];
		int[] apg1=new int[error+1];
		int[] apg2=new int[error+1];
		pg[0]=gf[1];pg[1]=gf[0];
		for(int i=2;i<(error+1);i++){
			for(int j=0;j<(error+1);j++){
				apg1[j]=mult(pg[j],gf[i]);
				apg2[j]=mult(pg[j],gf[0]);
			}
			pg[error]=apg2[error-1]; 
			pg[0]=apg1[0];
			for(int j=1;j<error;j++){
				pg[j]=apg1[j]^apg2[j-1];
			}
		}
	}
	public static int mult(int num1, int num2){
		int gfextend=(int) (Math.pow(2, bits)-1);
        int alfa1=inv_gf[num1];
        int alfa2=inv_gf[num2];
        int alfa_out=(alfa1+alfa2)%gfextend;
        if((num1==0) || (num2==0)){return 0;}
        else{return gf[alfa_out];}
    }
	public static String imprimir(int num){
		String generador="\"";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int j=0;j<bits;j++){
			if(((pg[num]<<j)&mascara)==mascara){generador+="1";}
			else {generador+="0";}
		}
		generador+="\"";
		return generador;
	}
}
