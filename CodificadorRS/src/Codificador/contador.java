//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class contador {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int gfextend=(int) (Math.pow(2, bits)-1);
		int max=(int) Math.ceil(Math.log((n-k)*(n-1))/Math.log(2));
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity contador is"+nl+"  port (raiz:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       oalfa:out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end contador;"+nl+nl);
		String architecture="architecture Behavioral of contador is"+nl;
		architecture+="  type bank is array ("+(gfextend-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal matriz1:bank :="+galoisfield.alfa_binario()+nl;
		architecture+="  signal contador,alfa: integer range 0 to "+n+";"+nl;
		architecture+="  signal ayuda: std_logic_vector("+(max-1)+" downto 0);"+nl;
		architecture+="  signal ayuda2,ayuda3: std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal ayuda4: std_logic_vector("+(bits)+" downto 0);"+nl;
		architecture+="  signal help: integer range 0 to "+((n-k)*(n-1))+";"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="  ayuda4<=conv_std_logic_vector((conv_integer(ayuda("+(bits-1)+" downto 0))+conv_integer(ayuda("+(max-1)+" downto "+bits+"))),"+(bits+1)+");"+nl;
		architecture+="  ayuda3<=\"";for(int i=0;i<bits;i++)architecture+=0;architecture+="\" when ayuda2=\"";for(int i=1;i<bits;i++)architecture+="1";architecture+="1\" else ayuda2;"+nl;
		architecture+="  help<=alfa*contador;"+nl;
		architecture+="  ayuda<=conv_std_logic_vector(help,"+max+");"+nl;
		architecture+="  ayuda2<=\"";
		for(int i=0;i<bits;i++){architecture+="0";}
		architecture+="\" when ayuda4("+(bits-1)+" downto 0)=\"";
		for(int i=0;i<bits;i++){architecture+="1";}
		architecture+="\" else ayuda4("+(bits-1)+" downto 0) when ayuda4<"+n+" else ayuda4("+(bits-1)+" downto 0)+1;"+nl;
		architecture+="  oalfa<=matriz1(conv_integer(ayuda3));"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="		alfa<=conv_integer(raiz);"+nl;
		architecture+="		contador<="+(n-1)+";"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="        if contador>0 then"+nl;
		architecture+=" 		  contador<=contador-1;"+nl;
		architecture+=" 	   else contador<="+(n-1)+";"+nl;
		architecture+="		   end if;"+nl;
		architecture+="  end if;"+nl+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;"+nl;
		FileWriter write=new FileWriter("contador.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}