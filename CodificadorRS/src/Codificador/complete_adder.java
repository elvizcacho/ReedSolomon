//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class complete_adder {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity complete_adder is"+nl+"  PORT (x,y,Cin:IN STD_LOGIC;"+nl+"       cout,sum:OUT STD_LOGIC);"+nl+"end complete_adder;"+nl+nl);
		String architecture=new String("architecture estructural of complete_adder is"+nl+"  signal p,q,r:std_logic;"+nl+"  component semisum"+nl+"    port(a,b:in std_logic;"+nl+"	c,d:out std_logic);"+nl+"  end component;"+nl+"begin"+nl+"  U2:semisum port map(a=>x,b=>y,c=>p,d=>q);"+nl+"  U1:semisum port map(a=>q,b=>Cin,c=>r,d=>sum);"+nl+"  cout<=p or r;"+nl+"end estructural;");
		FileWriter write=new FileWriter("complete_adder.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}