library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity recepcion_uart is
  port(clk,rst,entrada:in std_logic;
      registro_lectura:out std_logic_vector(7 downto 0);
      estado_recepcion:out std_logic_vector(1 downto 0);
      reloj_serial:out std_logic);
end recepcion_uart;

architecture Behavioral of recepcion_uart is
  signal control1: integer range 0 to 15;
  signal control2: integer range 0 to 7;
  signal estado: std_logic_vector(1 downto 0);
  signal reloj_uart,sign_entrada:std_logic;
  signal sign_registro: std_logic_vector(7 downto 0);

  component reloj_oversampling
    port (clk,rst:in std_logic;
          reloj_uart:out std_logic);
  end component;

begin

RU: reloj_oversampling port map(clk=>clk,rst=>rst,reloj_uart=>reloj_uart);
estado_recepcion<=estado;
reloj_serial<=reloj_uart;
sign_entrada<= not entrada; --A LA HORA DE IMPLEMENTAR NEGAR LA ENTRADA;

process(reloj_uart,rst) begin

  if rst='1' then
    sign_registro<=(OTHERS=>'0');
	   control1<=0;
    control2<=0;
    estado<="00";
    registro_lectura<=(OTHERS=>'0');
  elsif reloj_uart' event and reloj_uart='1' then
    case estado is
      when "00" =>
        if sign_entrada='0' then
          estado<="01";
        else
          registro_lectura<=sign_registro;
        end if;
      when "01" =>
        if control1>=7 then
          control1<=0;
          estado<="10";
        else
          control1<=control1+1;
        end if;
      when "10" =>
        if control1=15 then
          if control2=7 then
            estado<="11";
            control1<=0;
          else
            control2<=control2+1;
          end if;
        sign_registro(control2)<=sign_entrada;
        control1<=0;
        else
          control1<=control1+1;
        end if;
      when "11" =>
        registro_lectura<=sign_registro;
        if control1>=15 then
          control1<=0;
          control2<=0;
          estado<="00";
        else
          control1<=control1+1;
        end if;
      when others =>
        sign_registro<=(OTHERS=>'0');
        registro_lectura<=(OTHERS=>'0');
        control1<=0;
        control2<=0;
        estado<="00";
    end case;
  end if;
end process;

end Behavioral;