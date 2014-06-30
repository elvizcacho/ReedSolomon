-----------------------------
-----------------------------
--   N= clk/(32*baudrate)  --
-----------------------------
-----------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity reloj_oversampling is
  generic (N : positive := 2); --El valor de N depende del baudrate que vayamos a usar.
  port (clk,rst:in std_logic;
       reloj_uart:out std_logic);
end reloj_oversampling;

architecture Behavioral of reloj_oversampling is
  signal contador: integer range 1 to N;
  signal sing_reloj_uart: std_logic;
begin
  reloj_uart<=sing_reloj_uart;
process(clk,rst) begin
  IF rst='1' then
    sing_reloj_uart<='0';
    contador<=1;
  elsif clk' event and clk='1' then
    if(contador<N) then
      contador<=contador+1;
    else
      sing_reloj_uart<=not sing_reloj_uart;
      contador<=1;
    end if;
  end if;
end process;
end Behavioral;
