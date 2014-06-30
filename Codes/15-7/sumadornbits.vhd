library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity sumadornbits is
  PORT (a,b:IN STD_LOGIC_VECTOR(2 downto 0);
       x:OUT STD_LOGIC_VECTOR(2 downto 0);
       carryout:OUT STD_LOGIC);
end sumadornbits;

architecture Behavioral of sumadornbits is
  signal carry:std_logic_vector(3 downto 0);
  component complete_adder
    PORT (x,y,Cin:IN STD_LOGIC;
         cout,sum:OUT STD_LOGIC);
  end component;
begin
  carry(0)<='0';
  Generater:for i in 0 to 2 generate
    U1: complete_adder port map(x=>a(i),y=>b(i),Cin=>carry(i),cout=>carry(i+1),sum=>x(i));
  end generate;
  carryout<=carry(3);
end Behavioral;