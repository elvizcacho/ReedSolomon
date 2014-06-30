package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class syndrome {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int error=n-k;
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity syndrome is"+nl+"  port (entrada:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<error;i++){entity+="s"+i;if(i!=(error-1))entity+=",";}
		entity+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end syndrome;"+nl+nl;
		String architecture="architecture computation of syndrome is"+nl+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  component contador"+nl+"    port (raiz:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         oalfa:out std_logic_vector("+(bits-1)+" downto 0);"+nl+"         clk,rst : in std_logic);"+nl+"  end component;"+nl+nl;
		architecture+="  signal imult,";
		for(int i=0;i<error;i++){architecture+="omult"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl+"  signal ";
		for(int i=0;i<error;i++){architecture+="bmult"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl+"  signal ";
		for(int i=0;i<error;i++){architecture+="osum"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl+"  signal ";
		for(int i=0;i<error;i++){architecture+="fb"+i;if(i!=(error-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal control: integer range 0 to "+n+";"+nl+nl;
		architecture+="begin"+nl;
		architecture+="  imult<=entrada;"+nl;
		for(int i=0;i<error;i++){architecture+="  C"+i+": contador port map(raiz=>"+galoisfield.imp_num((i+1))+",clk=>clk,rst=>rst,oalfa=>bmult"+i+");"+nl;}
		for(int i=0;i<error;i++){architecture+="  M"+i+": multgalois port map(a=>imult,b=>bmult"+i+",mult=>omult"+i+");"+nl;}
		for(int i=0;i<error;i++){architecture+="  osum"+i+"<=fb"+i+" xor omult"+i+";"+nl;}
		architecture+=nl+"process(clk,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		for(int i=0;i<error;i++){architecture+="    fb"+i+"<=(others=>'0');"+nl;}
		for(int i=0;i<error;i++){architecture+="    s"+i+"<=(others=>'0');"+nl;}
		architecture+="    control<=1;"+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl;	
		architecture+="    control<=control+1;"+nl;	
		architecture+="    if control="+n+" then"+nl;
		architecture+="      control<=1;"+nl;
		for(int i=0;i<error;i++){architecture+="      s"+i+"<=osum"+i+";"+nl;}
		for(int i=0;i<error;i++){architecture+="      fb"+i+"<=(others=>'0');"+nl;}
		architecture+="    else"+nl;
		for(int i=0;i<error;i++){architecture+="      fb"+i+"<=osum"+i+";"+nl;}
		architecture+="    end if;"+nl;
		architecture+="  end if;"+nl+nl;
		architecture+="end process;"+nl;
		architecture+="end computation;"+nl;
		FileWriter write=new FileWriter("syndrome.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}