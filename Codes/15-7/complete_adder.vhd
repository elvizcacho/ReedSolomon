library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity complete_adder is
  PORT (x,y,Cin:IN STD_LOGIC;
       cout,sum:OUT STD_LOGIC);
end complete_adder;

architecture estructural of complete_adder is
  signal p,q,r:std_logic;
  component semisum
    port(a,b:in std_logic;
	c,d:out std_logic);
  end component;
begin
  U2:semisum port map(a=>x,b=>y,c=>p,d=>q);
  U1:semisum port map(a=>q,b=>Cin,c=>r,d=>sum);
  cout<=p or r;
end estructural;