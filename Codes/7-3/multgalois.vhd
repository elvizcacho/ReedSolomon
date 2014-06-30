library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity multgalois is
  port (a,b:in std_logic_vector(2 downto 0);
       mult:out std_logic_vector(2 downto 0));
end multgalois;

architecture Behavioral of multgalois is
  type bank is array (6 downto 0) of std_logic_vector(2 downto 0);
  type bank2 is array (7 downto 0) of std_logic_vector(2 downto 0);
  signal carry:std_logic;
  signal semimult,carry2,mult2,mult3,p,q:std_logic_vector(2 downto 0);
  signal matriz1:bank :=("101","111","110","011","100","010","001");
  signal matriz2:bank2:=("101","100","110","010","011","001","000","000");
  component sumadornbits
    port (a,b:in std_logic_vector(2 downto 0);
    		x:out std_logic_vector(2 downto 0);
         carryout:out std_logic);
  end component;

begin
  p<=matriz2(conv_integer(a));
  q<=matriz2(conv_integer(b));
  D: sumadornbits port map(a=>p,b=>q,x=>semimult,carryout=>carry);
  carry2<="00" & carry;
  E: sumadornbits port map(a=>semimult,b=>carry2,x=>mult2);
  mult3<=(others=>'0') when mult2="111" else mult2;
  mult<=(others=>'0') when a="000" or b="000" else matriz1(conv_integer(mult3));
end Behavioral;