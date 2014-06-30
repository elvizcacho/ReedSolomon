//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class inverse {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits) throws IOException{
		int gfextend=(int) (Math.pow(2, bits)-1);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity inverse is"+nl+"  port (ain:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       aout:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"end inverse;"+nl+nl;
		String architecture="architecture Behavioral of inverse is"+nl+nl;
		architecture+="  type bank is array ("+gfextend+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal matriz1:bank :="+galoisfield.inv_alfa()+nl+nl;
		architecture+="begin"+nl;
		architecture+="  aout<=matriz1(conv_integer(ain));"+nl;
		architecture+="end Behavioral;";
		FileWriter write=new FileWriter("inverse.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}
