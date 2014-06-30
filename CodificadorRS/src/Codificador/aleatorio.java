package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class aleatorio {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity aleatorio is"+nl+"  PORT (rst,clk:in std_logic;"+nl+"       seed   : in std_logic_vector (15 downto 0);"+nl+"       count  : out std_logic_vector (15 downto 0));"+nl+"end aleatorio;"+nl+nl);
		String architecture;
		architecture="architecture rtl of aleatorio is"+nl;
		architecture+="  signal count_i      : std_logic_vector (15 downto 0);"+nl;
		architecture+="  signal feedback     : std_logic;"+nl+nl;
		architecture+="begin"+nl;
		architecture+="  feedback <= count_i(10) xor count_i(12) xor count_i(11) xor count_i(15);"+nl;
		architecture+="  count <= count_i;"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="	 if rst = '1' then"+nl;
		architecture+="    count_i <= seed;"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="    count_i <= count_i(14 downto 0) & feedback;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl;
		architecture+="end architecture;"+nl;
		FileWriter write=new FileWriter("aleatorio.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}






