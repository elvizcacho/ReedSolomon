library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity semisum is
  PORT (a,b:IN STD_LOGIC;
       c,d:OUT STD_LOGIC);
end semisum;

architecture Behavioral of semisum is
begin
  d<=a xor b;
  c<=a and b;
end Behavioral;