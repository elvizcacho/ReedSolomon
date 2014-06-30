library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity potencia is
  port (raiz,potencia:in std_logic_vector(2 downto 0);
       oalfa:out std_logic_vector(2 downto 0));
end potencia;

architecture Behavioral of potencia is

  type bank is array (6 downto 0) of std_logic_vector(2 downto 0);
  type bank2 is array (7 downto 0) of std_logic_vector(2 downto 0);
  signal matriz1:bank :=("101","111","110","011","100","010","001");
  signal matriz2:bank2:=("101","100","110","010","011","001","000","000");
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
  oalfa<="000" when conv_integer(raiz)=0 else matriz1(conv_integer(ayuda3));
  alfa<=conv_integer(potencia);
  contador<=conv_integer(matriz2(conv_integer(raiz)));

end Behavioral;
