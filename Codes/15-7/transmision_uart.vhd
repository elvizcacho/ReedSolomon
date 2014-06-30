library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity transmision_uart is
  port(clk,rst,transmitir:in std_logic;
      registro_lectura:in std_logic_vector(7 downto 0);
      estado_transmision:out std_logic_vector(1 downto 0);
      salida:out std_logic);
end transmision_uart;

architecture Behavioral of transmision_uart is
  signal control1: integer range 0 to 15;
  signal control2: integer range 0 to 7;
  signal estado: std_logic_vector(1 downto 0);
  signal reloj_uart:std_logic;
  signal sign_registro: std_logic_vector(7 downto 0);

  component reloj_oversampling
    port (clk,rst:in std_logic;
          reloj_uart:out std_logic);
  end component;

begin

RU: reloj_oversampling port map(clk=>clk,rst=>rst,reloj_uart=>reloj_uart);
estado_transmision<=estado;

process(reloj_uart,rst) begin
  if rst='1' then
    control1<=0;
    control2<=0;
    estado<="00";
    sign_registro<=(OTHERS=>'0');
  elsif reloj_uart' event and reloj_uart='1' then
    case estado is
      when "00" =>
        if transmitir='1' then
          estado<="01";
          sign_registro<=registro_lectura;
        end if;
      when "01" =>
		   if control1>=15 then
          control1<=0;
          estado<="10";
        else
          control1<=control1+1;
        end if;
      when "10" =>
        if control1=15 then
          control1<=0;
        if control2=7 then
          estado<="11";
        else
          control2<=control2+1;
        end if;
        else
          control1<=control1+1;
        end if;
      when "11" =>
        if control1=15 then
          estado<="00";
          control1<=0;
          control2<=0;
        else
          control1<=control1+1;
        end if;
      when others =>
        control1<=0;
        control2<=0;
        estado<="00";
    end case;
  end if;
end process;

salida<= '0' when estado<="00" else
'1' when estado<="01" else
not sign_registro(control2) when estado<="10" else '0'; --todo negado para le implementacion

end Behavioral;
