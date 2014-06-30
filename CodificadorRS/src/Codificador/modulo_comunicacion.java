package Codificador;

import java.io.FileWriter;
import java.io.IOException;

public class modulo_comunicacion {

public static String nl = System.getProperty("line.separator");  //enter
	
	public static void vhd(int bits,int n,int k) throws IOException{
		String library = new String("library IEEE;"+nl+"use IEEE.STD_LOGIC_1164.ALL;"+nl+"use IEEE.STD_LOGIC_ARITH.ALL;"+nl+"use IEEE.STD_LOGIC_UNSIGNED.ALL;"+nl+nl);
		String entity="entity Modulo_comunicacion is"+nl+nl;
		entity+="  generic (cn : positive := "+n+";ck : positive := "+k+";cm : positive := "+bits+"); --n"+nl+nl;
		entity+="  port (clk,rst,entrada:in std_logic;"+nl;
		entity+="        leds:OUT STD_LOGIC_VECTOR(7 downto 0);"+nl;
		entity+="        reloj_serial,salida:out std_logic);"+nl;
		entity+="  end Modulo_comunicacion;"+nl+nl;
		String architecture="architecture Behavioral of Modulo_comunicacion is"+nl+nl;
		architecture+="  component recepcion_uart"+nl;
		architecture+="    port (clk,rst,entrada:in std_logic;"+nl;
		architecture+="          registro_lectura:out std_logic_vector(7 downto 0);"+nl;
		architecture+="          estado_recepcion:out std_logic_vector(1 downto 0);"+nl;
		architecture+="          reloj_serial:out std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="  component transmision_uart"+nl;
		architecture+="    port (clk,rst,transmitir:in std_logic;"+nl;
		architecture+="          registro_lectura:in std_logic_vector(7 downto 0);"+nl;
		architecture+="          estado_transmision:out std_logic_vector(1 downto 0);"+nl;
		architecture+="          salida:out std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="  component coder"+nl;
		architecture+="    port (ain:in std_logic_vector((cm-1) downto 0);"+nl;
		architecture+="          aout:out std_logic_vector((cm-1) downto 0);"+nl;
		architecture+="          clk,rst : in std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="  component decoder"+nl;
		architecture+="    port (entrada:in std_logic_vector((cm-1) downto 0);"+nl;
		architecture+="          salida:out std_logic_vector((cm-1) downto 0);"+nl;
		architecture+="          clk,rst : in std_logic);"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="  component aleatorio"+nl;
		architecture+="    port (rst  : in  std_logic;"+nl;
		architecture+="          clk    : in  std_logic;"+nl;
		architecture+="          seed	:  in std_logic_vector (15 downto 0);"+nl;
		architecture+="          count  : out std_logic_vector (15 downto 0));"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="  component aleatorio_corto"+nl;
		architecture+="    port (rst  : in  std_logic;"+nl;
		architecture+="          clk    : in  std_logic;"+nl;
		architecture+="          seed   :  in std_logic_vector (15 downto 0);"+nl;
		architecture+="          count  : out std_logic_vector (4 downto 0));"+nl;
		architecture+="  end component;"+nl+nl;
		architecture+="type bank1 is array ((ck-1) downto 0) of std_logic_vector(7 downto 0);"+nl;
		architecture+="type bank2 is array ((ck-1) downto 0) of std_logic_vector(7 downto 0);"+nl;
		architecture+="type bank3 is array ((cn-1) downto 0) of std_logic_vector(7 downto 0);"+nl;
		architecture+="type bank4 is array (((cn*cm)-1) downto 0) of integer range 0 to 65535;"+nl+nl;
		architecture+="signal m:bank1;"+nl;
		architecture+="signal o:bank2;"+nl;
		architecture+="signal n:bank3;"+nl;
		architecture+="signal noise:bank4:=(";for(int i=0;i<(n*bits)-1;i++)architecture+="0,";architecture+="0);"+nl;
		architecture+="signal registro_lectura,registro_escritura: std_logic_vector(7 downto 0);"+nl;
		architecture+="signal estado: std_logic_vector(3 downto 0);";
		architecture+="signal ain,aout,aout2,aout3,entrada_decoder,entrada_decoder2,entrada_decoder3,salida_decoder: std_logic_vector((cm-1) downto 0);"+nl;
		architecture+="signal transmitir,enable,enable2,enable3,lineal: std_logic;"+nl;
		architecture+="signal estado_recepcion,estado_transmision: std_logic_vector(1 downto 0);"+nl;
		architecture+="signal control2,control4: integer range 0 to ck;"+nl;
		architecture+="signal contador_entra: integer range 0 to ck+2;  --revisar"+nl;
		architecture+="signal contador_sale:  integer range 0 to cn;"+nl;
		architecture+="signal control3,control5:  integer range 0 to cn;"+nl;
		architecture+="signal contador:  integer range 0 to 6; --(N del reloj_serial)*2+1"+nl;
		architecture+="signal rstcoder,rstdecoder,dato2,dato3: std_logic;"+nl;
		architecture+="signal random1:std_logic_vector (15 downto 0);"+nl;
		architecture+="signal random2:std_logic_vector (4 downto 0);"+nl;
		architecture+="signal contador2: integer range 0 to cn*cm;"+nl;
		architecture+="signal temp:std_logic_vector(15 downto 0);"+nl;
		architecture+="constant nula: std_logic_vector(7 downto 0):=\"00000000\";"+nl;
		architecture+="constant nula2: std_logic_vector(cm-1 downto 0):=\"";for(int i=0; i<bits;i++)architecture+="0";architecture+="\";"+nl;
		architecture+="signal randint: integer range 0 to 65535;"+nl+nl;
		architecture+="begin"+nl+nl;
		architecture+="R: recepcion_uart port map(clk=>clk,rst=>rst,reloj_serial=>reloj_serial,entrada=>entrada,registro_lectura=>registro_lectura,estado_recepcion=>estado_recepcion);"+nl;
		architecture+="T: transmision_uart port map(clk=>clk,rst=>rst,transmitir=>transmitir,registro_lectura=>registro_escritura,estado_transmision=>estado_transmision,salida=>salida);"+nl;
		architecture+="C: coder port map (clk=>clk,rst=>rstcoder,ain=>ain,aout=>aout);"+nl;
		architecture+="D: decoder port map (clk=>clk,rst=>rstdecoder,entrada=>entrada_decoder3,salida=>salida_decoder);"+nl;
		architecture+="A1: aleatorio port map (clk=>clk,rst=>rst,seed=>\"1010101010101010\",count=>random1);"+nl;
		architecture+="A2: aleatorio_corto port map (clk=>clk,rst=>rst,seed=>\"1010010100101001\",count=>random2);"+nl;
		architecture+="enable<='1' when estado_recepcion=\"11\" else '0';"+nl;
		architecture+="enable2<='1' when salida_decoder/=\"000\" else '0';"+nl;
		architecture+="enable3<='1' when estado_transmision=\"11\" else '0';"+nl+nl;
		architecture+="aout2<=";
		for(int i=0;i<n;i++){architecture+=galoisfield.imp_alfa((i+i)%galoisfield.gfextend)+" when aout="+galoisfield.imp_alfa((i)%galoisfield.gfextend)+"else"+nl;}
		architecture+="nula2;"+nl+nl;
		architecture+="entrada_decoder2<=";
		for(int i=0;i<n;i++){architecture+=galoisfield.imp_alfa((i)%galoisfield.gfextend)+" when entrada_decoder="+galoisfield.imp_alfa((i+i)%galoisfield.gfextend)+"else"+nl;}
		architecture+="nula2;"+nl+nl;
		architecture+="aout3<=aout2 when lineal='1' else aout;"+nl;
		architecture+="entrada_decoder3<=entrada_decoder2 when lineal='1' else entrada_decoder;"+nl;
		architecture+="randint<=conv_integer(random1);"+nl+nl;
		architecture+="process(clk,rst) begin"+nl;
		architecture+="  if rst='1' then"+nl;
		architecture+="    registro_escritura<=(others=>'0');"+nl;
		architecture+="    leds<=(others=>'0');"+nl;
		architecture+="    estado<=\"0000\";"+nl;
		architecture+="    transmitir<='0';"+nl;
		architecture+="    m<=(others=>(others=>'1'));"+nl;
		architecture+="    leds<=not nula;"+nl;
		architecture+="    o<=(others=>(others=>'1'));"+nl;
		architecture+="    ain<=(others=>'0');"+nl;
		architecture+="    entrada_decoder<=(others=>'0');"+nl;
		architecture+="    rstcoder<='0';"+nl;
		architecture+="    rstdecoder<='1';"+nl;
		architecture+="    dato2<='0';"+nl;
		architecture+="    dato3<='0';"+nl;
		architecture+="    temp<=(others=>'0');"+nl;
		architecture+="    lineal<='0';"+nl+nl;
		architecture+="  elsif clk' event and clk='1' then"+nl+nl;
		architecture+="    case estado is"+nl;
		architecture+="      when \"0000\" => -- Estado de ocio"+nl;
		architecture+="        if enable='1' then"+nl;
		architecture+="          estado<=\"1011\";"+nl;
		architecture+="        else"+nl;
		architecture+="          transmitir<='0';"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="		 when \"1100\" => --estado de espera"+nl;
		architecture+="        if enable='0' then"+nl;
		architecture+="          estado<=\"1011\";"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"1011\" =>--Selección modo comando o modo envio"+nl;
		architecture+="        if enable='1' then"+nl;
		architecture+="          estado<=\"1100\";"+nl;
		architecture+="        elsif registro_lectura=\"00111011\" then --\"00111011\" = ':+1'"+nl;
		architecture+="          lineal<='1';"+nl;
		architecture+="          estado<=\"1101\";"+nl;
		architecture+="        elsif registro_lectura=\"00111010\" then --\"00111010\" = ':'"+nl;
		architecture+="          lineal<='0';"+nl;
		architecture+="          estado<=\"1101\";"+nl;
		architecture+="        elsif registro_lectura=\"00101111\" then --\"00101111\" = '/'"+nl;
		architecture+="          estado<=\"1111\";"+nl;
		architecture+="          registro_escritura<=(others=>'0');"+nl;
		architecture+="          leds<=(others=>'0');"+nl;
		architecture+="          transmitir<='0';"+nl;
		architecture+="          m<=(others=>(others=>'1'));"+nl;
		architecture+="          leds<=not nula;"+nl;
		architecture+="          o<=(others=>(others=>'1'));"+nl;
		architecture+="          ain<=(others=>'0');"+nl;
		architecture+="          entrada_decoder<=(others=>'0');"+nl;
		architecture+="          rstcoder<='0';"+nl;
		architecture+="          rstdecoder<='1';"+nl;
		architecture+="        else"+nl;
		architecture+="          estado<=\"0000\";"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"1101\" =>"+nl;
		architecture+="        if enable='1' then"+nl;
		architecture+="          estado<=\"1110\";"+nl;
		architecture+="        elsif contador2<cm*cn then"+nl;
		architecture+="          if dato2='1' then"+nl;
		architecture+="            temp(15 downto 8)<=registro_lectura;"+nl;
		architecture+="          else"+nl;
		architecture+="            temp(7 downto 0)<=registro_lectura;"+nl;
		architecture+="          end if;"+nl;
		architecture+="          noise(contador2)<=conv_integer(temp);"+nl;
		architecture+="        else"+nl;
		architecture+="          estado<=\"0000\";"+nl;
		architecture+="          contador2<=0;"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"1110\" => --Cuenta los datos de entrada."+nl;
		architecture+="        if enable='0' then"+nl;
		architecture+="          estado<=\"1101\";"+nl;
		architecture+="          if dato2='1' then"+nl;
		architecture+="            dato2<='0';"+nl;
		architecture+="            contador2<=contador2+1;"+nl;
		architecture+="          elsif dato3='1' then"+nl;
		architecture+="            dato2<='1';"+nl;
		architecture+="          else"+nl;
		architecture+="            dato3<='1';"+nl;
		architecture+="          end if;"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"1111\" => -- Estado de ocio"+nl;
		architecture+="        if enable='1' then"+nl;
		architecture+="          estado<=\"0001\";"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"0001\" => --Guarda los datos de entrada en m"+nl;
		architecture+="        if enable='1' then"+nl;
		architecture+="          estado<=\"0010\";"+nl;
		architecture+="        elsif contador_entra<(ck) then"+nl;
		architecture+="          m(contador_entra-1)<=registro_lectura;"+nl;
		architecture+="        else"+nl;
		architecture+="          estado<=\"0011\";"+nl;
		architecture+="          rstcoder<='1';"+nl;
		architecture+="          m(contador_entra-1)<=registro_lectura;"+nl;
		architecture+="          contador_entra<=0;"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"0010\" => --Cuenta los datos de entrada."+nl;
		architecture+="        if enable='0' then"+nl;
		architecture+="          estado<=\"0001\";"+nl;
		architecture+="          contador_entra<=contador_entra+1;"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"0011\" =>  --Inicializo Coder"+nl;
		architecture+="        if control3<cn then"+nl;
		architecture+="          rstcoder<='0';"+nl;
		architecture+="          ain<=(others=>'0');"+nl;
		architecture+="          control3<=control3+1;"+nl;
		architecture+="        else"+nl;
		architecture+="          estado<=\"0100\";"+nl;
		architecture+="          leds<=\"";for(int i=0; i<(8-bits);i++)architecture+="0";architecture+="\"&aout;--ficty"+nl;
		architecture+="          control3<=0;"+nl;
		architecture+="        end if;"+nl+nl;
		architecture+="      when \"0100\" => --Envia los simbolos guardados en el arreglo m al coder y los envia al decoder"+nl;
		architecture+="        if control2<ck then"+nl;
		architecture+="          ain<=m(control2)((cm-1) downto 0);"+nl;
		architecture+="          control2<=control2+1;"+nl;
		architecture+="        end if;"+nl;
		architecture+="        if control5<cn then"+nl;
		architecture+="          n(control5)<=\"";for(int i=0; i<(8-bits);i++)architecture+="0";architecture+="\"&entrada_decoder3;"+nl;
		architecture+="        end if;"+nl;
		architecture+="        if control3<cn then"+nl;
		architecture+="          if control3=0 and control2=2 then   ---reficty"+nl;
		architecture+="            rstdecoder<='0';"+nl;
		architecture+="          end if;"+nl;
		architecture+="          if control3=1 and control2=1 then"+nl;  
		architecture+="            control3<=0;"+nl;
		architecture+="            control5<=0;"+nl;
		architecture+="          else"+nl;
		for(int i=0;i<bits;i++){
			architecture+="          if randint<noise("+i+"+(cm*control3)) then    ----no entendi"+nl;
			architecture+="            entrada_decoder("+i+")<=aout3("+i+") xor '1';"+nl;
			architecture+="          else"+nl;
			architecture+="            entrada_decoder("+i+")<=aout3("+i+");"+nl;
			architecture+="          end if;"+nl;
		}	
		architecture+="          control3<=control3+1;"+nl
					 +"          if control3>0 then"+nl
		             +"            control5<=control5+1;"+nl
		             +"          end if;"+nl
		             +"        end if;"+nl
		             +"        else"+nl
		             +"          control5<=0;"+nl
		             +"          control3<=0;"+nl
		             +"          control2<=0;"+nl
		             +"          estado<=\"0101\";"+nl
		             +"        end if;"+nl+nl
		             +"      when \"0101\" => --guarda los datos de salida del decoder en el registro o"+nl
		             +"		   if enable2='1' then"+nl
		             +"          if control4<ck then"+nl
		             +"            o(control4)<=\"";for(int i=0; i<(8-bits);i++)architecture+="0";architecture+="\"&salida_decoder;"+nl;
		architecture+="            control4<=control4+1;"+nl
					 +"          else"+nl
					 +"            control4<=0;"+nl
					 +"            estado<=\"0110\";"+nl
					 +"            entrada_decoder<=(OTHERS=>'0');"+nl
					 +"			 end if;"+nl
					 +"        end if;"+nl+nl
					 +"      when \"0110\" => --Envia los datos alamcenados en el registro o"+nl
					 +"        if enable3='1' then"+nl
					 +"          estado<=\"0111\";"+nl
					 +"        end if;"+nl
					 +"		   if contador_sale<ck then"+nl
					 +"          registro_escritura<=o(contador_sale);"+nl
					 +"          transmitir<='1';"+nl
					 +"        else"+nl
					 +"          estado<=\"1000\";"+nl
					 +"          contador_sale<=0;"+nl
					 +"        end if;"+nl+nl
					 +"      when \"0111\" => --Cuenta los datos enviados"+nl
					 +"		   if enable3='0' then"+nl
					 +"          estado<=\"0110\";"+nl
					 +"          contador_sale<=contador_sale+1;"+nl
					 +"        end if;"+nl+nl
					 +"      when \"1000\" => --Envia los datos alamcenados en el registro n"+nl
					 +"        if enable3='1' then"+nl
					 +"          estado<=\"1001\";"+nl
					 +"        end if;"+nl
					 +"        if contador_sale<cn then"+nl
					 +"          registro_escritura<=n(contador_sale);"+nl
					 +"          transmitir<='1';"+nl
					 +"        else"+nl
					 +"          rstdecoder<='1';"+nl
					 +"          transmitir<='0';"+nl
					 +"          estado<=\"1111\";"+nl
					 +"          contador_sale<=0;"+nl 
					 +"          registro_escritura<=\"00000000\";"+nl
					 +"          leds<=(others=>'0');"+nl
					 +"          estado<=\"1111\";"+nl
					 +"          m<=(others=>(others=>'1'));"+nl
					 +"          leds<=not nula;"+nl
					 +"          o<=(others=>(others=>'1'));"+nl
					 +"          ain<=(others=>'0');"+nl
					 +"          contador<=0;"+nl
					 +"        end if;"+nl
					 +"      when \"1001\" => --Cuenta los datos enviados"+nl
					 +"        if enable3='0' then"+nl
					 +"          estado<=\"1000\";"+nl
					 +"          contador_sale<=contador_sale+1;"+nl
					 +"        end if;"+nl+nl
					 +"		 when others =>"+nl
		             +"        estado<=\"0000\";"+nl
		             +"    end case;"+nl
		             +"  end if;"+nl
		             +"end process;"+nl+nl
		             +"end Behavioral;"+nl;
		FileWriter write=new FileWriter("modulo_comunicacion.vhd");
		write.write(library+entity+architecture);
		write.close();
	}
	
}
