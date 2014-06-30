//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class decoder {
	
	public static String nl = System.getProperty("line.separator");  //enter	

	public static void vhd(int bits,int n,int k) throws IOException{
		int error=(n-k)/2;
		int maximo=(int) Math.ceil((error+1)/2);
		int gfextend=(int) (Math.pow(2, bits)-1);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity=new String("entity decoder is"+nl+"  port (entrada:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       salida:out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst : in std_logic);"+nl+"end decoder;"+nl+nl);
		String architecture="architecture Behavioral of decoder is"+nl+nl;
		architecture+="component syndrome"+nl+"  port (entrada:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<2*error;i++){architecture+="s"+i;if(i!=(2*error-1))architecture+=",";}
		architecture+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end component;"+nl+nl;
		architecture+="component bm_algorithm "+nl+"  port (";
		for(int i=0;i<2*error;i++){architecture+="s"+i;if(i!=(2*error-1))architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){architecture+="c"+i;if(i!=error)architecture+=",";}
		architecture+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end component;"+nl+nl;
		architecture+="component chien "+nl+"  port (";
		for(int i=0;i<error;i++){architecture+="b"+(i+1);if(i!=(error-1))architecture+=",";}
		architecture+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){architecture+="c"+i;if(i!=error)architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end component;"+nl+nl;
		architecture+="component forney"+nl+"  port (";
		for(int i=0;i<2*error;i++){architecture+="n"+i;if(i!=(2*error-1))architecture+=",";}
		architecture+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<maximo;i++){architecture+="m"+i;if(i!=(maximo-1))architecture+=",";}
		architecture+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<2*error;i++){architecture+="s"+i;if(i!=(2*error-1))architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){architecture+="c"+i;if(i!=error)architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end component;"+nl+nl;
		architecture+="component correction"+nl+"  port (";
		for(int i=0;i<2*error;i++){architecture+="n"+i;if(i!=(2*error-1))architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<maximo;i++){architecture+="m"+i;if(i!=(maximo-1))architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){architecture+="b"+i;if(i!=error)architecture+=",";}
		architecture+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		architecture+="entrada:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		architecture+="salida: out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end component;"+nl+nl;
		architecture+="type bank is array ("+(2*error-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="type bank2 is array ("+(n-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="signal s:bank;"+nl;
		architecture+="signal entrada1,entrada2,entrada3,entrada4:bank2;"+nl;
		architecture+="signal ";
		for(int i=0;i<2*error;i++)architecture+="s"+i+",";
		for(int i=1;i<=error;i++)architecture+="c"+i+",";
		for(int i=1;i<=error;i++)architecture+="b"+i+",";
		for(int i=0;i<2*error;i++)architecture+="n"+i+",";
		for(int i=0;i<maximo;i++)architecture+="m"+i+",";
		architecture+="aentrada,auxentrada4,nula: std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="signal control: integer range 0 to "+n+";"+nl;
		architecture+="signal enable: std_logic;"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="SY: syndrome port map(entrada=>entrada,clk=>clk,rst=>rst";for(int i=0;i<2*error;i++)architecture+=",s"+i+"=>s"+i;architecture+=");"+nl;
		architecture+="BM: bm_Algorithm port map(clk=>clk,rst=>rst";for(int i=0;i<2*error;i++)architecture+=",s"+i+"=>s"+i;for(int i=1;i<=error;i++)architecture+=",c"+i+"=>c"+i;architecture+=");"+nl;
		architecture+="CH: chien port map (clk=>clk,rst=>rst";for(int i=1;i<=error;i++)architecture+=",c"+i+"=>c"+i;for(int i=1;i<=error;i++)architecture+=",b"+i+"=>b"+i;architecture+=");"+nl;
		architecture+="F: forney port map (clk=>clk,rst=>rst";for(int i=0;i<2*error;i++)architecture+=",s"+i+"=>s("+i+")";for(int i=1;i<=error;i++)architecture+=",c"+i+"=>c"+i;for(int i=0;i<2*error;i++)architecture+=",n"+i+"=>n"+i;for(int i=0;i<maximo;i++)architecture+=",m"+i+"=>m"+i;architecture+=");"+nl;
		architecture+="CT: correction port map (clk=>clk,rst=>rst,entrada=>auxentrada4,salida=>salida";for(int i=0;i<2*error;i++)architecture+=",n"+i+"=>n"+i;for(int i=0;i<maximo;i++)architecture+=",m"+i+"=>m"+i;for(int i=1;i<=error;i++)architecture+=",b"+i+"=>b"+i;architecture+=");"+nl;
		architecture+="nula<=(OTHERS=>'0');"+nl;
		architecture+="auxentrada4<=entrada4(control);"+nl;
		architecture+="aentrada<=entrada;"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="    control<=0;"+nl;
		architecture+="    s<=(";for(int i=0;i<2*error;i++){architecture+="nula";if(i!=(2*error-1))architecture+=",";else architecture+=");"+nl;}
		architecture+="    enable<='0';"+nl;
		architecture+="    entrada1<=(";for(int i=0;i<n;i++){architecture+="nula";if(i!=(n-1))architecture+=",";else architecture+=");"+nl;}
		architecture+="    entrada2<=(";for(int i=0;i<n;i++){architecture+="nula";if(i!=(n-1))architecture+=",";else architecture+=");"+nl;}
		architecture+="    entrada3<=(";for(int i=0;i<n;i++){architecture+="nula";if(i!=(n-1))architecture+=",";else architecture+=");"+nl;}
		architecture+="    entrada4<=(";for(int i=0;i<n;i++){architecture+="nula";if(i!=(n-1))architecture+=",";else architecture+=");"+nl;}
		architecture+="  elsif clk' event and clk='1' then"+nl;
		architecture+="    enable<='1';"+nl;
		architecture+="      entrada1(control)<=aentrada;"+nl;	
		architecture+="		 if control="+(n-1)+" then"+nl;
		architecture+="        control<=0;"+nl;
		architecture+="        s<=(";for(int i=(2*error-1);i>=0;i--){architecture+="s"+i;if(i!=0)architecture+=",";else architecture+=");"+nl;}
		architecture+="        entrada2<=entrada1;"+nl;
		architecture+="        entrada2(control)<=aentrada;"+nl;
		architecture+="        entrada3<=entrada2;"+nl;
		architecture+="        entrada4<=entrada3;"+nl;
		architecture+="      else"+nl;
		architecture+="        control<=control+1;"+nl;
		architecture+="      end if;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl+nl;
		architecture+="end Behavioral;";
		FileWriter write=new FileWriter("decoder.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
	
}
