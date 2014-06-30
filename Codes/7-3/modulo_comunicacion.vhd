library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity Modulo_comunicacion is

  generic (cn : positive := 7;ck : positive := 3;cm : positive := 3); --n

  port (clk,rst,entrada:in std_logic;
        leds:OUT STD_LOGIC_VECTOR(7 downto 0);
        reloj_serial,salida:out std_logic);
  end Modulo_comunicacion;

architecture Behavioral of Modulo_comunicacion is

  component recepcion_uart
    port (clk,rst,entrada:in std_logic;
          registro_lectura:out std_logic_vector(7 downto 0);
          estado_recepcion:out std_logic_vector(1 downto 0);
          reloj_serial:out std_logic);
  end component;

  component transmision_uart
    port (clk,rst,transmitir:in std_logic;
          registro_lectura:in std_logic_vector(7 downto 0);
          estado_transmision:out std_logic_vector(1 downto 0);
          salida:out std_logic);
  end component;

  component coder
    port (ain:in std_logic_vector((cm-1) downto 0);
          aout:out std_logic_vector((cm-1) downto 0);
          clk,rst : in std_logic);
  end component;

  component decoder
    port (entrada:in std_logic_vector((cm-1) downto 0);
          salida:out std_logic_vector((cm-1) downto 0);
          clk,rst : in std_logic);
  end component;

  component aleatorio
    port (rst  : in  std_logic;
          clk    : in  std_logic;
          seed	:  in std_logic_vector (15 downto 0);
          count  : out std_logic_vector (15 downto 0));
  end component;

  component aleatorio_corto
    port (rst  : in  std_logic;
          clk    : in  std_logic;
          seed   :  in std_logic_vector (15 downto 0);
          count  : out std_logic_vector (4 downto 0));
  end component;

type bank1 is array ((ck-1) downto 0) of std_logic_vector(7 downto 0);
type bank2 is array ((ck-1) downto 0) of std_logic_vector(7 downto 0);
type bank3 is array ((cn-1) downto 0) of std_logic_vector(7 downto 0);
type bank4 is array (((cn*cm)-1) downto 0) of integer range 0 to 65535;

signal m:bank1;
signal o:bank2;
signal n:bank3;
signal noise:bank4:=(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
signal registro_lectura,registro_escritura: std_logic_vector(7 downto 0);
signal estado: std_logic_vector(3 downto 0);signal ain,aout,aout2,aout3,entrada_decoder,entrada_decoder2,entrada_decoder3,salida_decoder: std_logic_vector((cm-1) downto 0);
signal transmitir,enable,enable2,enable3,lineal: std_logic;
signal estado_recepcion,estado_transmision: std_logic_vector(1 downto 0);
signal control2,control4: integer range 0 to ck;
signal contador_entra: integer range 0 to ck+2;  --revisar
signal contador_sale:  integer range 0 to cn;
signal control3,control5:  integer range 0 to cn;
signal contador:  integer range 0 to 6; --(N del reloj_serial)*2+1
signal rstcoder,rstdecoder,dato2,dato3: std_logic;
signal random1:std_logic_vector (15 downto 0);
signal random2:std_logic_vector (4 downto 0);
signal contador2: integer range 0 to cn*cm;
signal temp:std_logic_vector(15 downto 0);
constant nula: std_logic_vector(7 downto 0):="00000000";
constant nula2: std_logic_vector(cm-1 downto 0):="000";
signal randint: integer range 0 to 65535;

begin

R: recepcion_uart port map(clk=>clk,rst=>rst,reloj_serial=>reloj_serial,entrada=>entrada,registro_lectura=>registro_lectura,estado_recepcion=>estado_recepcion);
T: transmision_uart port map(clk=>clk,rst=>rst,transmitir=>transmitir,registro_lectura=>registro_escritura,estado_transmision=>estado_transmision,salida=>salida);
C: coder port map (clk=>clk,rst=>rstcoder,ain=>ain,aout=>aout);
D: decoder port map (clk=>clk,rst=>rstdecoder,entrada=>entrada_decoder3,salida=>salida_decoder);
A1: aleatorio port map (clk=>clk,rst=>rst,seed=>"1010101010101010",count=>random1);
A2: aleatorio_corto port map (clk=>clk,rst=>rst,seed=>"1010010100101001",count=>random2);
enable<='1' when estado_recepcion="11" else '0';
enable2<='1' when salida_decoder/="000" else '0';
enable3<='1' when estado_transmision="11" else '0';

aout2<="001" when aout="001"else
"100" when aout="010"else
"110" when aout="100"else
"101" when aout="011"else
"010" when aout="110"else
"011" when aout="111"else
"111" when aout="101"else
nula2;

entrada_decoder2<="001" when entrada_decoder="001"else
"010" when entrada_decoder="100"else
"100" when entrada_decoder="110"else
"011" when entrada_decoder="101"else
"110" when entrada_decoder="010"else
"111" when entrada_decoder="011"else
"101" when entrada_decoder="111"else
nula2;

aout3<=aout2 when lineal='1' else aout;
entrada_decoder3<=entrada_decoder2 when lineal='1' else entrada_decoder;
randint<=conv_integer(random1);

process(clk,rst) begin
  if rst='1' then
    registro_escritura<=(others=>'0');
    leds<=(others=>'0');
    estado<="0000";
    transmitir<='0';
    m<=(others=>(others=>'1'));
    leds<=not nula;
    o<=(others=>(others=>'1'));
    ain<=(others=>'0');
    entrada_decoder<=(others=>'0');
    rstcoder<='0';
    rstdecoder<='1';
    dato2<='0';
    dato3<='0';
    temp<=(others=>'0');
    lineal<='0';

  elsif clk' event and clk='1' then

    case estado is
      when "0000" => -- Estado de ocio
        if enable='1' then
          estado<="1011";
        else
          transmitir<='0';
        end if;

		 when "1100" => --estado de espera
        if enable='0' then
          estado<="1011";
        end if;

      when "1011" =>--Selección modo comando o modo envio
        if enable='1' then
          estado<="1100";
        elsif registro_lectura="00111011" then --"00111011" = ':+1'
          lineal<='1';
          estado<="1101";
        elsif registro_lectura="00111010" then --"00111010" = ':'
          lineal<='0';
          estado<="1101";
        elsif registro_lectura="00101111" then --"00101111" = '/'
          estado<="1111";
          registro_escritura<=(others=>'0');
          leds<=(others=>'0');
          transmitir<='0';
          m<=(others=>(others=>'1'));
          leds<=not nula;
          o<=(others=>(others=>'1'));
          ain<=(others=>'0');
          entrada_decoder<=(others=>'0');
          rstcoder<='0';
          rstdecoder<='1';
        else
          estado<="0000";
        end if;

      when "1101" =>
        if enable='1' then
          estado<="1110";
        elsif contador2<cm*cn then
          if dato2='1' then
            temp(15 downto 8)<=registro_lectura;
          else
            temp(7 downto 0)<=registro_lectura;
          end if;
          noise(contador2)<=conv_integer(temp);
        else
          estado<="0000";
          contador2<=0;
        end if;

      when "1110" => --Cuenta los datos de entrada.
        if enable='0' then
          estado<="1101";
          if dato2='1' then
            dato2<='0';
            contador2<=contador2+1;
          elsif dato3='1' then
            dato2<='1';
          else
            dato3<='1';
          end if;
        end if;

      when "1111" => -- Estado de ocio
        if enable='1' then
          estado<="0001";
        end if;

      when "0001" => --Guarda los datos de entrada en m
        if enable='1' then
          estado<="0010";
        elsif contador_entra<(ck) then
          m(contador_entra-1)<=registro_lectura;
        else
          estado<="0011";
          rstcoder<='1';
          m(contador_entra-1)<=registro_lectura;
          contador_entra<=0;
        end if;

      when "0010" => --Cuenta los datos de entrada.
        if enable='0' then
          estado<="0001";
          contador_entra<=contador_entra+1;
        end if;

      when "0011" =>  --Inicializo Coder
        if control3<cn then
          rstcoder<='0';
          ain<=(others=>'0');
          control3<=control3+1;
        else
          estado<="0100";
          leds<="00000"&aout;--ficty
          control3<=0;
        end if;

      when "0100" => --Envia los simbolos guardados en el arreglo m al coder y los envia al decoder
        if control2<ck then
          ain<=m(control2)((cm-1) downto 0);
          control2<=control2+1;
        end if;
        if control5<cn then
          n(control5)<="00000"&entrada_decoder3;
        end if;
        if control3<cn then
          if control3=0 and control2=2 then   ---reficty
            rstdecoder<='0';
          end if;
          if control3=1 and control2=1 then
            control3<=0;
            control5<=0;
          else
          if randint<noise(0+(cm*control3)) then    ----no entendi
            entrada_decoder(0)<=aout3(0) xor '1';
          else
            entrada_decoder(0)<=aout3(0);
          end if;
          if randint<noise(1+(cm*control3)) then    ----no entendi
            entrada_decoder(1)<=aout3(1) xor '1';
          else
            entrada_decoder(1)<=aout3(1);
          end if;
          if randint<noise(2+(cm*control3)) then    ----no entendi
            entrada_decoder(2)<=aout3(2) xor '1';
          else
            entrada_decoder(2)<=aout3(2);
          end if;
          control3<=control3+1;
          if control3>0 then
            control5<=control5+1;
          end if;
        end if;
        else
          control5<=0;
          control3<=0;
          control2<=0;
          estado<="0101";
        end if;

      when "0101" => --guarda los datos de salida del decoder en el registro o
		   if enable2='1' then
          if control4<ck then
            o(control4)<="00000"&salida_decoder;
            control4<=control4+1;
          else
            control4<=0;
            estado<="0110";
            entrada_decoder<=(OTHERS=>'0');
			 end if;
        end if;

      when "0110" => --Envia los datos alamcenados en el registro o
        if enable3='1' then
          estado<="0111";
        end if;
		   if contador_sale<ck then
          registro_escritura<=o(contador_sale);
          transmitir<='1';
        else
          estado<="1000";
          contador_sale<=0;
        end if;

      when "0111" => --Cuenta los datos enviados
		   if enable3='0' then
          estado<="0110";
          contador_sale<=contador_sale+1;
        end if;

      when "1000" => --Envia los datos alamcenados en el registro n
        if enable3='1' then
          estado<="1001";
        end if;
        if contador_sale<cn then
          registro_escritura<=n(contador_sale);
          transmitir<='1';
        else
          rstdecoder<='1';
          transmitir<='0';
          estado<="1111";
          contador_sale<=0;
          registro_escritura<="00000000";
          leds<=(others=>'0');
          estado<="1111";
          m<=(others=>(others=>'1'));
          leds<=not nula;
          o<=(others=>(others=>'1'));
          ain<=(others=>'0');
          contador<=0;
        end if;
      when "1001" => --Cuenta los datos enviados
        if enable3='0' then
          estado<="1000";
          contador_sale<=contador_sale+1;
        end if;

		 when others =>
        estado<="0000";
    end case;
  end if;
end process;

end Behavioral;
