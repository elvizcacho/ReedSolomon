//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class forney {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int error=(n-k)/2;
		int maximo=(int) Math.ceil((error+1)/2);
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity forney is"+nl+"  port (";
		for(int i=0;i<2*error;i++){entity+="n"+i;if(i!=(2*error-1))entity+=",";}
		entity+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<maximo;i++){entity+="m"+i;if(i!=(maximo-1))entity+=",";}
		entity+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=0;i<2*error;i++){entity+="s"+i;if(i!=(2*error-1))entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){entity+="c"+i;if(i!=error)entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end forney;"+nl+nl;
		String architecture="architecture Behavioral of forney is"+nl+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  signal sn,n,zero,";
		for(int i=0;i<=error;i++){architecture+="snc"+i+",";}
		for(int i=1;i<=error;i++)for(int j=1;j<=i;j++)architecture+="r"+i+j+",";
		for(int i=0;i<2*error;i++)architecture+="an"+i+",";
		for(int i=0;i<maximo;i++){architecture+="am"+i;if(i!=(maximo-1))architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal enable: std_logic;"+nl;
		architecture+="  signal k:integer range 0 to "+n+";"+nl+nl;
		architecture+="	 constant c0: std_logic_vector("+(bits-1)+" downto 0):=\"";for(int i=0; i<(bits-1);i++)architecture+="0";architecture+="1\";"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="  zero<=\"";for(int i=0;i<bits;i++)architecture+="0";architecture+="\";"+nl+nl;
		architecture+="  sn<=";for(int i=0;i<2*error;i++)architecture+="  s"+i+" when k="+i+" else"+nl;architecture+="  zero;"+nl+nl;
		for(int i=0;i<=error;i++)architecture+="  Mu"+i+": multgalois port map(a=>sn,b=>c"+i+",mult=>snc"+i+");"+nl;
		architecture+=nl+"  n<=snc0";for(int i=1;i<=error;i++)architecture+=" xor r"+i+i;architecture+=";"+nl;
		for(int i=0;i<maximo;i++)architecture+="  am"+i+"<=c"+(2*i+1)+";"+nl;
		architecture+=nl+"process(clk,rst) begin"+nl+nl;
		architecture+="if rst='1' then"+nl;
		architecture+="  enable<='0';"+nl;
		for(int i=1;i<=error;i++)for(int j=1;j<=i;j++)architecture+="  r"+i+j+"<=zero;"+nl;
		for(int i=0;i<2*error;i++)architecture+="  n"+i+"<=zero;"+nl;
		for(int i=0;i<maximo;i++)architecture+="  m"+i+"<=zero;"+nl;
		architecture+="  k<=0;"+nl;
		architecture+="elsif clk' event and clk='1' then"+nl;
		for(int i=1;i<=error;i++){
			for(int j=1;j<=i;j++){
				architecture+="  r"+i+j+"<=";
				if(j==1)architecture+="snc"+i+";"+nl;
				else architecture+="r"+i+(j-1)+";"+nl;
			}
		}
		architecture+="  enable<='1';"+nl;
		architecture+="    if k="+(n-1)+" then"+nl;
		architecture+="      k<=0;"+nl;
		for(int i=0;i<2*error;i++)architecture+="      n"+i+"<=an"+i+";"+nl;
		for(int i=0;i<maximo;i++)architecture+="      m"+i+"<=am"+i+";"+nl;
		architecture+="    else"+nl;
		architecture+="      k<=k+1;"+nl;
		architecture+="    end if;"+nl;
		architecture+="  if k=0 then"+nl;
		architecture+="    an0<=n;"+nl;
		for(int i=1;i<2*error;i++)architecture+="  elsif k="+i+" then"+nl+"    an"+i+"<=n;"+nl;	
		architecture+="  end if;"+nl;	
		architecture+="end if;"+nl+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;"+nl;
		FileWriter write=new FileWriter("forney.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}