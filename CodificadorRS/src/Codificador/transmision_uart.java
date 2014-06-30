package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class transmision_uart {
	
	public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd() throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity transmision_uart is"+nl+"  port(clk,rst,transmitir:in std_logic;"+nl+"      registro_lectura:in std_logic_vector(7 downto 0);"+nl+"      estado_transmision:out std_logic_vector(1 downto 0);"+nl+"      salida:out std_logic);"+nl+"end transmision_uart;"+nl+nl;		
		String architecture="architecture Behavioral of transmision_uart is"+nl;
		architecture+="  signal control1: integer range 0 to 15;"+nl;
		architecture+="  signal control2: integer range 0 to 7;"+nl;
		architecture+="  signal estado: std_logic_vector(1 downto 0);"+nl;
		architecture+="  signal reloj_uart:std_logic;"+nl;
		architecture+="  signal sign_registro: std_logic_vector(7 downto 0);"+nl+nl;
		architecture+="  component reloj_oversampling"+nl;
		architecture+="    port (clk,rst:in std_logic;"+nl;
		architecture+="          reloj_uart:out std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="RU: reloj_oversampling port map(clk=>clk,rst=>rst,reloj_uart=>reloj_uart);"+nl;
		architecture+="estado_transmision<=estado;"+nl+nl;
		architecture+="process(reloj_uart,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="    control1<=0;"+nl;
		architecture+="    control2<=0;"+nl;
		architecture+="    estado<=\"00\";"+nl;
		architecture+="    sign_registro<=(OTHERS=>'0');"+nl;
		architecture+="  elsif reloj_uart' event and reloj_uart='1' then"+nl;
		architecture+="    case estado is"+nl;
		architecture+="      when \"00\" =>"+nl;
		architecture+="        if transmitir='1' then"+nl;
		architecture+="          estado<=\"01\";"+nl;
		architecture+="          sign_registro<=registro_lectura;"+nl;
		architecture+="        end if;"+nl;
		architecture+="      when \"01\" =>"+nl;
		architecture+="		   if control1>=15 then"+nl;
		architecture+="          control1<=0;"+nl;
		architecture+="          estado<=\"10\";"+nl;
		architecture+="        else"+nl;
		architecture+="          control1<=control1+1;"+nl;
		architecture+="        end if;"+nl;
		architecture+="      when \"10\" =>"+nl;
		architecture+="        if control1=15 then"+nl;
		architecture+="          control1<=0;"+nl;
		architecture+="        if control2=7 then"+nl;
		architecture+="          estado<=\"11\";"+nl;
		architecture+="        else"+nl;
		architecture+="          control2<=control2+1;"+nl;
		architecture+="        end if;"+nl;
		architecture+="        else"+nl;
		architecture+="          control1<=control1+1;"+nl;
		architecture+="        end if;"+nl;
		architecture+="      when \"11\" =>"+nl;
		architecture+="        if control1=15 then"+nl;
		architecture+="          estado<=\"00\";"+nl;
		architecture+="          control1<=0;"+nl;
		architecture+="          control2<=0;"+nl;
		architecture+="        else"+nl;
		architecture+="          control1<=control1+1;"+nl;
		architecture+="        end if;"+nl;
		architecture+="      when others =>"+nl;
		architecture+="        control1<=0;"+nl;
		architecture+="        control2<=0;"+nl;
		architecture+="        estado<=\"00\";"+nl;
		architecture+="    end case;"+nl;
		architecture+="  end if;"+nl;
		architecture+="end process;"+nl+nl;
		architecture+="salida<= '0' when estado<=\"00\" else"+nl;
		architecture+="'1' when estado<=\"01\" else"+nl;
		architecture+="not sign_registro(control2) when estado<=\"10\" else '0'; --todo negado para le implementacion"+nl+nl;
		architecture+="end Behavioral;"+nl;
		FileWriter write=new FileWriter("transmision_uart.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
}




