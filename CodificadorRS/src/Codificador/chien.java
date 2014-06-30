//Validado

package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class chien {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n, int k) throws IOException{
		int error=(n-k)/2;
		int gfextend=(int) (Math.pow(2, bits)-1); // gfextend funciona como mascara 
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity chien is"+nl+"  port (";
		for(int i=0;i<error;i++){entity+="b"+(i+1);if(i!=(error-1))entity+=",";}
		entity+=":out std_logic_vector("+(bits-1)+" downto 0);"+nl+"       ";
		for(int i=1;i<=error;i++){entity+="c"+i;if(i!=error)entity+=",";}
		entity+=":in std_logic_vector("+(bits-1)+" downto 0);"+nl+"       clk,rst: in std_logic);"+nl+"end chien;"+nl+nl;
		String architecture="architecture Behavioral of chien is"+nl+nl;
		architecture+="  component multgalois"+nl+"    port (a,b:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         mult:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  component contador"+nl+"    port (raiz:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         oalfa:out std_logic_vector("+(bits-1)+" downto 0);"+nl+"         clk,rst : in std_logic);"+nl+"  end component;"+nl+nl;
		architecture+="  component inverse"+nl+"    port (ain:in std_logic_vector("+(bits-1)+" downto 0);"+nl+"         aout:out std_logic_vector("+(bits-1)+" downto 0));"+nl+"  end component;"+nl+nl;
		architecture+="  type bank is array ("+(gfextend-1)+" downto 0) of std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal matriz1:bank :="+galoisfield.alfa_binario()+nl;
		architecture+="  signal getsum,nula,uno,invroot,root,";
		for(int i=1;i<=error;i++){architecture+="ff"+i+",tosum"+i+",ab"+i+",reg_"+i+",sel_alfa"+i;if(i!=error)architecture+=",";}
		architecture+=": std_logic_vector("+(bits-1)+" downto 0);"+nl;
		architecture+="  signal zero,enable : std_logic;"+nl;
		architecture+="	 signal control: integer range 0 to "+gfextend+";"+nl;
		architecture+="  signal help_out: integer range 0 to "+error+";"+nl;
		architecture+="	 constant c0: std_logic_vector("+(bits-1)+" downto 0):=\"";for(int i=0; i<(bits-1);i++)architecture+="0";architecture+="1\";"+nl+nl;
		architecture+="begin"+nl+nl;
		for(int i=1;i<=error;i++){architecture+="  M"+(i-1)+": multgalois port map(a=>sel_alfa"+i+",b=>reg_"+i+",mult=>tosum"+i+");"+nl;}
		architecture+="  I0: inverse port map(ain=>root,aout=>invroot);"+nl+nl;
		architecture+="  root<=matriz1(control);"+nl;
		architecture+="  nula<=(OTHERS =>'0');"+nl;
		architecture+="  uno<=\"";for(int i=0;i<bits;i++){if(i!=(bits-1))architecture+=0;else architecture+=1;}architecture+="\";"+nl;
		architecture+="  getsum<=";for(int i=1;i<=error;i++){architecture+="tosum"+i+" xor ";}architecture+="c0;"+nl;
		architecture+="  zero<='1' when getsum=nula else '0';"+nl;
		for(int i=1;i<=error;i++){architecture+="  reg_"+i+"<=c"+i+" when control=0 else ff"+i+";"+nl;}
		for(int i=1;i<=error;i++){architecture+="  sel_alfa"+i+"<=uno when control=0 else "+galoisfield.imp_alfa(i)+";"+nl+nl;}
		architecture+="process(clk,rst) begin"+nl+nl;
		architecture+=" if rst='1' then"+nl;
		for(int i=1;i<=error;i++)architecture+="  ff"+i+"<=nula;"+nl+"  b"+i+"<=nula;"+nl+"  ab"+i+"<=nula;"+nl;
		architecture+="  control<=0;"+nl+"  help_out<=0;"+nl+"  enable<='0';"+nl;
		architecture+="elsif clk' event and clk='1' then"+nl;
		architecture+="  enable<='1';"+nl;
		for(int i=1;i<=error;i++)architecture+="    ff"+i+"<=tosum"+i+";"+nl;	
		architecture+="    if zero='1' then"+nl;
		for(int i=1;i<=error;i++){
			if(i==1)architecture+="      if ";else architecture+="      elsif ";
			architecture+="help_out="+(i-1)+" then"+nl;
			architecture+="        help_out<=help_out+1;"+nl;
			architecture+="  	   ab"+i+"<=invroot;"+nl;
			architecture+="        if control="+(n-1)+" then"+nl;
			for(int j=1;j<=error;j++){
				architecture+="          b"+j+"<=";
				if(j<i)architecture+="ab"+j+";"+nl;
				if(j==i)architecture+="invroot;"+nl;
				if(j>i)architecture+="nula;"+nl;
				if(j==error)architecture+="        end if;"+nl;
			}
			if(i==error)architecture+="      end if;"+nl;
		}
		architecture+="    elsif control="+(n-1)+" then"+nl; 
		for(int i=1;i<=error;i++)architecture+="      b"+i+"<=ab"+i+";"+nl;			
		architecture+="    end if;"+nl;
		architecture+="    if control="+(n-1)+" then"+nl;
		architecture+="      control<=0;"+nl+"      help_out<=0;"+nl;
		for(int i=1;i<=error;i++)architecture+="      ab"+i+"<=nula;"+nl;
		architecture+="    else"+nl;
		architecture+="      control<=control+1;"+nl;
		architecture+="    end if;"+nl;
		architecture+="end if;"+nl+nl;
		architecture+="end process;"+nl;
		architecture+="end Behavioral;";
		FileWriter write=new FileWriter("chien.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}