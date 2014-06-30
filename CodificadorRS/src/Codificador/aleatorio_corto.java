package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class aleatorio_corto {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity aleatorio_corto is"+nl+"  PORT (rst,clk:in_std_logic;"+nl+"       seed   : in std_logic_vector (15 downto 0);"+nl+"       count  : out std_logic_vector (4 downto 0));"+nl+"end aleatorio_corto;"+nl+nl);
		String architecture;
		architecture="architecture rtl of aleatorio_corto is"+nl;
		architecture+="  signal count_i      : std_logic_vector (15 downto 0);"+nl;
		architecture+="  signal feedback     : std_logic;"+nl+nl;
		architecture+="begin"+nl;
		architecture+="  feedback <= count_i(10) xor count_i(12) xor count_i(11) xor count_i(15);"+nl;
		architecture+="  count <=  conv_std_logic_vector(conv_integer(count_i) mod 32,5) when conv_integer(count_i) mod 32<21 else  conv_std_logic_vector((conv_integer(count_i) mod 32)-20,5);"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="	 if rst = '1' then"+nl;
		architecture+="    count_i <= seed;"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="    count_i <= count_i(14 downto 0) & feedback;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl;
		architecture+="end architecture;"+nl;
		FileWriter write=new FileWriter("aleatorio_corto.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}

