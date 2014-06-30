//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class multgalois {
	
public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits) throws IOException{
		int gfextend=(int) (Math.pow(2, bits)-1);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity multgalois is"+nl+"  port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"end multgalois;"+nl+nl);
		String architecture="architecture Behavioral of multgalois is"+nl;
		architecture+="  type bank is array ("+(gfextend-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  type bank2 is array ("+gfextend+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal carry:std_logic;"+nl;
		architecture+="  signal semimult,carry2,mult2,mult3,p,q:std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal matriz1:bank :="+galoisfield.alfa_binario()+nl;
		architecture+="  signal matriz2:bank2:="+galoisfield.binario_alfa()+nl;
		architecture+="  component sumadornbits"+nl;
		architecture+="    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="    		x:out std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="         carryout:out std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="begin"+nl;
		architecture+="  p<=matriz2(conv_integer(a));"+nl;
		architecture+="  q<=matriz2(conv_integer(b));"+nl;
		architecture+="  D: sumadornbits port map(a=>p,b=>q,x=>semimult,carryout=>carry);"+nl;
		architecture+="  carry2<=\"";
		for(int i=1;i<bits;i++){architecture+="0";}
		architecture+="\" & carry;"+nl;
		architecture+="  E: sumadornbits port map(a=>semimult,b=>carry2,x=>mult2);"+nl;
		architecture+="  mult3<=(others=>'0') when mult2=\"";
		for(int i=0;i<bits;i++){architecture+="1";}
		architecture+="\" else mult2;"+nl;
		architecture+="  mult<=(others=>'0') when a=\"";
		for(int i=0;i<bits;i++){architecture+="0";}
		architecture+="\" or b=\"";
		for(int i=0;i<bits;i++){architecture+="0";}
		architecture+="\" else matriz1(conv_integer(mult3));"+nl;
		architecture+="end Behavioral;";		
		FileWriter write=new FileWriter("multgalois.vhd");
		write.write(library+entity+architecture);
		write.close();
	}

}
