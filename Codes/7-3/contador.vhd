library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity contador is
  port (raiz:in std_logic_vector(2 downto 0);
       oalfa:out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end contador;

architecture Behavioral of contador is
  type bank is array (6 downto 0) of std_logic_vector(2 downto 0);
  signal matriz1:bank :=("101","111","110","011","100","010","001");
  signal contador,alfa: integer range 0 to 7;
  signal ayuda: std_logic_vector(4 downto 0);
  signal ayuda2,ayuda3: std_logic_vector(2 downto 0);
  signal ayuda4: std_logic_vector(3 downto 0);
  signal help: integer range 0 to 24;

begin

  ayuda4<=conv_std_logic_vector((conv_integer(ayuda(2 downto 0))+conv_integer(ayuda(4 downto 3))),4);
  ayuda3<="000" when ayuda2="111" else ayuda2;
  help<=alfa*contador;
  ayuda<=conv_std_logic_vector(help,5);
  ayuda2<="000" when ayuda4(2 downto 0)="111" else ayuda4(2 downto 0) when ayuda4<7 else ayuda4(2 downto 0)+1;
  oalfa<=matriz1(conv_integer(ayuda3));

process(clk,rst) begin
  if rst='1' then
		alfa<=conv_integer(raiz);
		contador<=6;
  elsif clk' event and clk='1' then
        if contador>0 then
 		  contador<=contador-1;
 	   else contador<=6;
		   end if;
  end if;

end process;
end Behavioral;
