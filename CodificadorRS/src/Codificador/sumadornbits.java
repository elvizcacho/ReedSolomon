//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class sumadornbits {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits) throws IOException{
		String architecture;
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity sumadornbits is"+nl+"  PORT (a,b:IN STD_LOGIC_VECTOR("+(bits-1)+" downto 0);"+nl+"       x:OUT STD_LOGIC_VECTOR("+(bits-1)+" downto 0);"+nl+"       carryout:OUT STD_LOGIC);"+nl+"end sumadornbits;"+nl+nl);
		architecture="architecture Behavioral of sumadornbits is"+nl;
		architecture+="  signal carry:std_logic_vector("+bits+" downto 0);"+nl;
		architecture+="  component complete_adder"+nl+"    PORT (x,y,Cin:IN STD_LOGIC;"+nl+"         cout,sum:OUT STD_LOGIC);"+nl+"  end component;"+nl+"begin"+nl+"  carry(0)<='0';"+nl;
		architecture+="  Generater:for i in 0 to "+(bits-1)+" generate"+nl;
		architecture+="    U1: complete_adder port map(x=>a(i),y=>b(i),Cin=>carry(i),cout=>carry(i+1),sum=>x(i));"+nl+"  end generate;"+nl;
		architecture+="  carryout<=carry("+bits+");"+nl+"end Behavioral;";
		FileWriter write=new FileWriter("sumadornbits.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}
