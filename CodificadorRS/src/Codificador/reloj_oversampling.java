package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class reloj_oversampling {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String comentario="-----------------------------"+nl+"-----------------------------"+nl+"--   N= clk/(32*baudrate)  --"+nl+"-----------------------------"+nl+"-----------------------------"+nl+nl;
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity reloj_oversampling is"+nl+"  generic (N : positive := 2); --El valor de N depende del baudrate que vayamos a usar."+nl+"  port (clk,rst:in std_logic;"+nl+"       reloj_uart:out std_logic);"+nl+"end reloj_oversampling;"+nl+nl;
		String architecture="architecture Behavioral of reloj_oversampling is"+nl;
		architecture+="  signal contador: integer range 1 to N;"+nl;
		architecture+="  signal sing_reloj_uart: std_logic;"+nl;
		architecture+="begin"+nl;
		architecture+="  reloj_uart<=sing_reloj_uart;"+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="  IF rst='1' then"+nl;
		architecture+="    sing_reloj_uart<='0';"+nl;
		architecture+="    contador<=1;"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="    if(contador<N) then"+nl;
		architecture+="      contador<=contador+1;"+nl;
		architecture+="    else"+nl;
		architecture+="      sing_reloj_uart<=not sing_reloj_uart;"+nl;
		architecture+="      contador<=1;"+nl;
		architecture+="    end if;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;"+nl;
		FileWriter write=new FileWriter("reloj_oversampling.vhd");
		write.write(comentario+library+entity+architecture);
		write.close();
	}
}








