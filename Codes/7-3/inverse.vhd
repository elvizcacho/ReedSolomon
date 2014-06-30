library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity inverse is
  port (ain:in std_logic_vector(2 downto 0);
       aout:out std_logic_vector(2 downto 0));
end inverse;

architecture Behavioral of inverse is

  type bank is array (7 downto 0) of std_logic_vector(2 downto 0);
  signal matriz1:bank :=("100","011","010","111","110","101","001","000");

begin
  aout<=matriz1(conv_integer(ain));
end Behavioral;