//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class coder {
	
	public static String nl = System.getProperty("line.separator");  //enter	

	public static void vhd(int bits,int n,int k) throws IOException{
		int error=n-k;
		int gfextend=(int) (Math.pow(2, bits)-1);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity coder is"+nl+"  port (ain:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       aout:out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst : in std_logic);"+nl+"end coder;"+nl+nl);
		String architecture="architecture Behavioral of coder is"+nl+nl;
		architecture+="  signal ";
		for(int i=0;i<error;i++){architecture+="omult"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal ";
		for(int i=1;i<error;i++){architecture+="insum"+i+",";}
		architecture+="insum"+(n-k)+": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal ";
		for(int i=1;i<error;i++){architecture+="osum"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal feedback,sw1,sw2,obanco: std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal control: integer range 0 to " +gfextend+";"+nl;
		architecture+="  signal estado,enable: std_logic;"+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="begin"+nl;
		for(int i=0;i<error;i++){architecture+="  M"+i+":"+" multgalois port map(a=>sw1,b=>"+pol_generador.imprimir(i)+",mult=>omult"+i+");"+nl;}
		for(int i=1;i<error;i++){architecture+="  osum"+i+"<=insum"+i+" xor omult"+i+";"+nl;}
		architecture+="  feedback<=insum"+(n-k)+" xor obanco;"+nl;
		architecture+="  obanco<=ain when control<"+k+" else (others=>'0');"+nl;
		architecture+="  sw1<=feedback when control<"+k+" else (others=>'0');"+nl;
		architecture+="  sw2<=obanco when control<"+k+" else insum"+(n-k)+";"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="if rst='1' then"+nl;
		for(int i=1;i<error;i++){architecture+="  insum"+i+"<=(others=>'0');"+nl;}
		architecture+="  insum"+(n-k)+"<=(others=>'0');"+nl;
		architecture+="  estado<='0';"+nl+"  control<=0;"+nl+"  aout<=(others=>'0');"+nl+"  enable<='0';"+nl;
		architecture+="elsif clk' event and clk='1' then";
		architecture+="  enable<='1';"+nl;
		architecture+="  insum1<=omult0;"+nl;
		for(int i=2;i<error;i++){architecture+="  insum"+i+"<=osum"+(i-1)+";"+nl;}
		architecture+="  insum"+(n-k)+"<=osum"+(error-1)+";"+nl+"  aout<=sw2;"+nl;
		architecture+="  if enable='1' then"+nl+"    control<=control+1;"+nl+"  end if;"+nl;
		architecture+="  case estado is"+nl;
		architecture+="    when '0' =>"+nl;
		architecture+="      if control="+k+" then"+nl;
		architecture+="         estado<='1';"+nl;
		architecture+="      end if;"+nl;
		architecture+="    when others =>"+nl;
		architecture+="      if control="+(n-1)+" then"+nl;
		architecture+="         estado<='0';"+nl;
		architecture+="         control<=0;"+nl;
		architecture+="      end if;"+nl;
		architecture+="   end case;"+nl;
		architecture+="end if;"+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;"+nl;
		FileWriter write=new FileWriter("coder.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}
