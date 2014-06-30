library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity correction is
  port (n0,n1,n2,n3:in std_logic_vector(2 downto 0);
       m0:in std_logic_vector(2 downto 0);
       b1,b2:in std_logic_vector(2 downto 0);
       entrada:in std_logic_vector(2 downto 0);
       salida: out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end correction;

architecture Behavioral of correction is

  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

  component inverse
    port (ain:in std_logic_vector(2 downto 0);
         aout:out std_logic_vector(2 downto 0));
  end component;

  component potencia
    port (raiz,potencia:in std_logic_vector(2 downto 0);
         oalfa:out std_logic_vector(2 downto 0));
  end component;

  type bank is array (7 downto 0) of std_logic_vector(2 downto 0);
  signal matriz:bank:=("101","100","110","010","011","001","000","000");
  signal r1,nr1,r2,nr2,r3,nr3,zero,b,num,invden,den,e,auxsal: std_logic_vector(2 downto 0);
  signal enable: std_logic;
  signal pe1,pe2,k:integer range 0 to 7;

begin

  zero<="000";
  salida<=entrada when b=zero else auxsal;

  pe1<=conv_integer(matriz(conv_integer(b1)));
  pe2<=conv_integer(matriz(conv_integer(b2)));

  b<=  b1 when k=pe1 else
  b2 when k=pe2 else
  zero;

  Mn1: multgalois port map(a=>r1,b=>n1,mult=>nr1);
  Mn2: multgalois port map(a=>r2,b=>n2,mult=>nr2);
  Mn3: multgalois port map(a=>r3,b=>n3,mult=>nr3);
  M: multgalois port map(a=>num,b=>invden,mult=>e);
  I1: inverse port map(ain=>b,aout=>r1);
  I2: inverse port map(ain=>den,aout=>invden);
  P2: potencia port map(raiz=>r1,potencia=>"010",oalfa=>r2);
  P3: potencia port map(raiz=>r1,potencia=>"011",oalfa=>r3);
  num<=n0 xor nr1 xor nr2 xor nr3;
  den<=m0;
  auxsal<=entrada xor e;

process(clk,rst) begin
  if rst='1' then
    enable<='0';
    k<=6;
  elsif clk' event and clk='1' then
    enable<='1';
      if k=0 then
        k<=6;
      else
        k<=k-1;
      end if;
  end if;
end process;

end Behavioral;