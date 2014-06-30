//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class correction {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int error=(n-k)/2;
		int maximo=(int) Math.ceil((error+1)/2);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity correction is"+nl+"  port (";
		for(int i=0;i<2*error;i++){entity+="n"+i;if(i!=(2*error-1))entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<maximo;i++){entity+="m"+i;if(i!=(maximo-1))entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){entity+="b"+i;if(i!=error)entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		entity+="entrada:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		entity+="salida: out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end correction;"+nl+nl;
		String architecture="architecture Behavioral of correction is"+nl+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  component inverse"+nl+"    port (ain:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         aout:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  component potencia"+nl+"    port (raiz,potencia:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         oalfa:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  type bank is array ("+n+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal matriz:bank:="+galoisfield.binario_alfa()+nl;
		architecture+="  signal ";
		for(int i=1;i<maximo;i++)architecture+="nm"+i+",";
		for(int i=1;i<2*error;i++)architecture+="r"+i+",nr"+i+",";
		architecture+="zero,b,num,invden,den,e,auxsal: std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal enable: std_logic;"+nl;
		architecture+="  signal ";
		for(int i=1;i<=error;i++)architecture+="pe"+i+",";
		architecture+="k:integer range 0 to "+n+";"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="  zero<=\"";for(int i=0;i<bits;i++)architecture+="0";architecture+="\";"+nl;
		architecture+="  salida<=entrada when b=zero else auxsal;"+nl+nl;
		for(int i=1;i<=error;i++)architecture+="  pe"+i+"<=conv_integer(matriz(conv_integer(b"+i+")));"+nl;
		architecture+=nl+"  b<=";for(int i=1;i<=error;i++)architecture+="  b"+i+" when k=pe"+i+" else"+nl;architecture+="  zero;"+nl+nl;  
		for(int i=1;i<2*error;i++)architecture+="  Mn"+i+": multgalois port map(a=>r"+i+",b=>n"+i+",mult=>nr"+i+");"+nl;
		for(int i=1;i<maximo;i++)architecture+="  Md"+i+": multgalois port map(a=>r"+2*i+",b=>m"+i+",mult=>nm"+i+");"+nl;
		architecture+="  M: multgalois port map(a=>num,b=>invden,mult=>e);"+nl;
		architecture+="  I1: inverse port map(ain=>b,aout=>r1);"+nl;
		architecture+="  I2: inverse port map(ain=>den,aout=>invden);"+nl;	
		for(int i=2;i<2*error;i++)architecture+="  P"+i+": potencia port map(raiz=>r1,potencia=>"+galoisfield.imp_num(i)+",oalfa=>r"+i+");"+nl;
		architecture+="  num<=n0";for(int i=1;i<2*error;i++)architecture+=" xor nr"+i;architecture+=";"+nl;
		architecture+="  den<=m0";for(int i=1;i<maximo;i++)architecture+=" xor nm"+i;architecture+=";"+nl;
		architecture+="  auxsal<=entrada xor e;"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="    enable<='0';"+nl;
		architecture+="    k<="+(n-1)+";"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="    enable<='1';"+nl;
		architecture+="      if k=0 then"+nl;
		architecture+="        k<="+(n-1)+";"+nl;
		architecture+="      else"+nl;
		architecture+="        k<=k-1;"+nl;
		architecture+="      end if;"+nl;	
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl+nl;
		architecture+="end Behavioral;";
		FileWriter write=new FileWriter("correction.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}
