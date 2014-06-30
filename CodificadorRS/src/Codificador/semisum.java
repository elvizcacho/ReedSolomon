//Validada

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

import org.jfree.io.IOUtils;

public class semisum {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity semisum is"+nl+"  PORT (a,b:IN STD_LOGIC;"+nl+"       c,d:OUT STD_LOGIC);"+nl+"end semisum;"+nl+nl);
		String architecture=new String("architecture Behavioral of semisum is"+nl+"begin"+nl+"  d<=a xor b;"+nl+"  c<=a and b;"+nl+"end Behavioral;");
		FileWriter write=new FileWriter("semisum.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
	
	public static void ucf() throws IOException{
		String architecture="#Entradas externas"+nl+
		"NET \"rst\" LOC = \"U10\";"+nl+
		"NET \"entrada\" LOC = \"E16\" | IOSTANDARD = LVCMOS33;"+nl
		+nl+
		"#Salidas externas"+nl+
		"NET \"leds(7)\" LOC = \"W21\";"+nl+
		"NET \"leds(6)\" LOC = \"Y22\";"+nl+
		"NET \"leds(5)\" LOC = \"V20\";"+nl+
		"NET \"leds(4)\" LOC = \"V19\";"+nl+
		"NET \"leds(3)\" LOC = \"U19\";"+nl+
		"NET \"leds(2)\" LOC = \"U20\";"+nl+
		"NET \"leds(1)\" LOC = \"T19\";"+nl+
		"NET \"leds(0)\" LOC = \"R20\";"+nl+
		"NET \"salida\" LOC = \"F15\" | IOSTANDARD = LVCMOS33;"+nl
		+nl+
		"#Entradas internas"+nl+
		"NET \"clk\" LOC = \"E12\"| IOSTANDARD = LVCMOS33;"+nl;
		FileWriter write=new FileWriter("ucf_vamos.ucf");
		write.write(architecture);
		write.close();
	}
}
