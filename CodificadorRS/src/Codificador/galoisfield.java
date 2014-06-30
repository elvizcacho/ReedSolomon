//Validado

package Codificador;

public class galoisfield{
	
	static int gf[];
	static int inv_gf[];
	static int inv_alfa[];
	static int bits;
	static int gfextend;
	
	public static void crearGF(){
		int primitivepol=0,maximo=0;
		gfextend=(int) (Math.pow(2, bits)-1); // gfextend funciona como mascara 
		maximo=(int) (Math.pow(2, (bits-1))-1);
		gf=new int[gfextend];
		inv_alfa=new int[gfextend+1];
		inv_gf=new int[gfextend+1];		
		switch(bits){
			case 3: primitivepol=3;break;
			case 4: primitivepol=3;break;
			case 5:	primitivepol=5;break;
			case 6:	primitivepol=3;break;
			case 7:	primitivepol=9;break;
			case 8:	primitivepol=29;break;
		};
		gf[0]=1;inv_gf[0]=0;inv_gf[1]=0;inv_alfa[0]=0;inv_alfa[1]=1;
		for(int i=1;i<gfextend;i++){
			if(gf[i-1]<=maximo){gf[i]=gf[i-1]<<1;}
			else{gf[i]=gf[i-1]<<1;gf[i]=gf[i]^primitivepol;}
			gf[i]=gf[i]&gfextend;
			inv_gf[gf[i]]=i;
		}
		for(int i=1;i<gfextend;i++)
		{
			inv_alfa[gf[i]]=gf[gfextend-i];
		}
	}
	public static String alfa_binario(){
		String alfa_binario="(";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int i=gfextend;i>0;i--){
			alfa_binario+="\"";
			for(int j=0;j<bits;j++){
				if(((gf[i-1]<<j)&mascara)==mascara){alfa_binario+="1";}
				else {alfa_binario+="0";}
			}
			if(i==1){alfa_binario+="\");";}
			else{alfa_binario+="\",";}
		}
		return alfa_binario;
	}
	public static String binario_alfa(){
		String binario_alfa="(";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int i=(gfextend+1);i>0;i--){
			binario_alfa+="\"";
			for(int j=0;j<bits;j++){
				if(((inv_gf[i-1]<<j)&mascara)==mascara){binario_alfa+="1";}
				else {binario_alfa+="0";}
			}
			if(i==1){binario_alfa+="\");";}
			else{binario_alfa+="\",";}
		}
		return binario_alfa;
	}
	public static String inv_alfa(){
		String invalfa="(";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int i=(gfextend+1);i>0;i--){
			invalfa+="\"";
			for(int j=0;j<bits;j++){
				if(((inv_alfa[i-1]<<j)&mascara)==mascara){invalfa+="1";}
				else {invalfa+="0";}
			}
			if(i==1){invalfa+="\");";}
			else{invalfa+="\",";}
		}
		return invalfa;
	}
	public static String imp_alfa(int num){
		String alfa="\"";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int j=0;j<bits;j++){
			if(((gf[num]<<j)&mascara)==mascara){alfa+="1";}
			else {alfa+="0";}
		}
		alfa+="\"";
		return alfa;
	}
	public static String imp_num(int num){
		String generador="\"";
		int mascara=(int) Math.pow(2, (bits-1));
		for(int j=0;j<bits;j++){
			if(((num<<j)&mascara)==mascara){generador+="1";}
			else {generador+="0";}
		}
		generador+="\"";
		return generador;
	}
}
