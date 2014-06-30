//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class bm_algorithm {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int error=n-k;
		int maximo=(error/2)+1;
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity bm_algorithm is"+nl+"  port (";
		for(int i=0;i<error;i++){entity+="s"+i;if(i!=(error-1))entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<maximo;i++){entity+="c"+i;if(i!=(maximo-1))entity+=",";}
		entity+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end bm_algorithm;"+nl+nl;
		String architecture="architecture Behavioral of bm_algorithm is"+nl+nl;
		architecture+="  type bank is array ("+(maximo-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal c,p,h:bank;"+nl;
		architecture+="  signal d,dm,dm2,ddm2,sk,";
		for(int i=1;i<maximo;i++){architecture+="sk"+i+",ck"+i+",ckc"+i+",selh"+i;if(i!=(maximo-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal k: integer range 0 to "+n+";"+nl;
		architecture+="  signal L,lp: integer range 0 to "+(maximo-1)+";"+nl+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  component inverse"+nl+"    port (ain:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         aout:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="begin"+nl+nl+"  sk<=";
		for(int i=0;i<error;i++){architecture+="  s"+i+" when k="+(i+1)+" else"+nl;}
		architecture+="  (others=>'0');"+nl+nl;
		for(int i=1;i<maximo;i++){
			architecture+="  sk"+i+"<=";
			for(int j=0;j<error;j++){architecture+="  s"+j+" when k-"+i+"="+(j+1)+" else"+nl;}
			architecture+="  (others=>'0');"+nl+nl;
		}
		for(int i=1;i<maximo;i++){
			architecture+="  selh"+i+"<=";
			for(int j=0;j<(maximo-1);j++){architecture+="  h("+j+") when "+i+"-lp="+j+" else"+nl;}
			architecture+="  (others=>'0');"+nl+nl;
		}
		for(int i=1;i<maximo;i++){architecture+="  ck"+i+"<=sk"+i+" when L>"+(i-1)+" else (others=>'0');"+nl;}		
        architecture+=nl;		
        for(int i=1;i<maximo;i++){architecture+="  M"+(i-1)+": multgalois port map(a=>ck"+i+",b=>c("+i+"),mult=>ckc"+i+");"+nl;}
        for(int i=0;i<maximo;i++){architecture+="  M"+(i+maximo-1)+": multgalois port map(a=>ddm2,b=>p("+i+"),mult=>h("+i+"));"+nl;}
        architecture+="  M"+(2*maximo-1)+": multgalois port map(a=>dm2,b=>d,mult=>ddm2);"+nl;
		architecture+="  I0: inverse port map(ain=>dm,aout=>dm2);"+nl+nl;
		architecture+="  d<=sk";
		for(int i=1;i<maximo;i++){architecture+=" xor ckc"+i;if(i==(maximo-1))architecture+=";"+nl+nl;}
		architecture+="process(clk,rst) begin"+nl+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="		k<=1;"+nl;
		architecture+=reset(maximo, bits);
		for(int i=1;i<maximo;i++){
			architecture+="    c"+i+"<=(\""; for(int j=0;j<bits;j++){architecture+="0";if(j==(bits-1))architecture+="\");"+nl;}
		}
		architecture+="elsif clk' event and clk='1' then"+nl;
		architecture+="  if k>0 and k<"+(error+1)+" then"+nl;
		architecture+="    if d=\"";for(int i=0;i<bits;i++)architecture+="0";architecture+="\" then"+nl;
		architecture+="      lp<=lp+1;"+nl;
		architecture+="    else"+nl;
		for(int i=1;i<maximo;i++){architecture+="      c("+i+")<=c("+i+") xor selh"+i+";"+nl;}
		architecture+="      if (2*L)>=k then"+nl;
		architecture+="        lp<=lp+1;"+nl;
		architecture+="		 else"+nl;
		architecture+="        p<=c;"+nl+"        L<=k-L;"+nl+"        dm<=d;"+nl+"        lp<=1;"+nl;
		architecture+="      end if;"+nl;
		architecture+="    end if;"+nl;
		architecture+="  end if;"+nl;
		architecture+="  if k="+n+" then"+nl;
		architecture+="    k<=1;"+nl;
		architecture+=reset(maximo, bits);
		for(int i=1;i<maximo;i++)architecture+="    c"+i+"<=c("+i+");"+nl;
		architecture+="  else"+nl;	
		architecture+="    k<=k+1;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end if;"+nl+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;";	
		FileWriter write=new FileWriter("bm_algorithm.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
	
	public static String reset(int maximo,int bits){
		String architecture="    L<=0;"+nl;
		architecture+="    p<=(";
		for(int i=0;i<maximo;i++){
			architecture+="\"";
			for(int j=0;j<bits;j++){if(i!=(maximo-1) || j!=(bits-1))architecture+="0";else architecture+="1";}
			architecture+="\"";
			if(i!=(maximo-1))architecture+=",";else architecture+=");"+nl;
		}
		architecture+="    c<=(";
		for(int i=0;i<maximo;i++){
			architecture+="\"";
			for(int j=0;j<bits;j++){if(i!=(maximo-1) || j!=(bits-1))architecture+="0";else architecture+="1";}
			if(i!=(maximo-1))architecture+="\",";else architecture+="\");"+nl;
		}
		architecture+="    dm<=(\""; for(int i=0;i<bits;i++){if(i!=(bits-1))architecture+="0";else architecture+="1\");"+nl;}
		architecture+="    lp<=1;"+nl;	
		return architecture;
	}
}
