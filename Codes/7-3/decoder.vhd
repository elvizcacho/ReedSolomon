library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity decoder is
  port (entrada:in std_logic_vector(2 downto 0);
       salida:out std_logic_vector(2 downto 0);
       clk,rst : in std_logic);
end decoder;

architecture Behavioral of decoder is

component syndrome
  port (entrada:in std_logic_vector(2 downto 0);
       s0,s1,s2,s3:out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end component;

component bm_algorithm 
  port (s0,s1,s2,s3:in std_logic_vector(2 downto 0);
       c1,c2:out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end component;

component chien 
  port (b1,b2:out std_logic_vector(2 downto 0);
       c1,c2:in std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end component;

component forney
  port (n0,n1,n2,n3:out std_logic_vector(2 downto 0);
       m0:out std_logic_vector(2 downto 0);
       s0,s1,s2,s3:in std_logic_vector(2 downto 0);
       c1,c2:in std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end component;

component correction
  port (n0,n1,n2,n3:in std_logic_vector(2 downto 0);
       m0:in std_logic_vector(2 downto 0);
       b1,b2:in std_logic_vector(2 downto 0);
       entrada:in std_logic_vector(2 downto 0);
       salida: out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end component;

type bank is array (3 downto 0) of std_logic_vector(2 downto 0);
type bank2 is array (6 downto 0) of std_logic_vector(2 downto 0);
signal s:bank;
signal entrada1,entrada2,entrada3,entrada4:bank2;
signal s0,s1,s2,s3,c1,c2,b1,b2,n0,n1,n2,n3,m0,aentrada,auxentrada4,nula: std_logic_vector(2 downto 0);
signal control: integer range 0 to 7;
signal enable: std_logic;

begin

SY: syndrome port map(entrada=>entrada,clk=>clk,rst=>rst,s0=>s0,s1=>s1,s2=>s2,s3=>s3);
BM: bm_Algorithm port map(clk=>clk,rst=>rst,s0=>s0,s1=>s1,s2=>s2,s3=>s3,c1=>c1,c2=>c2);
CH: chien port map (clk=>clk,rst=>rst,c1=>c1,c2=>c2,b1=>b1,b2=>b2);
F: forney port map (clk=>clk,rst=>rst,s0=>s(0),s1=>s(1),s2=>s(2),s3=>s(3),c1=>c1,c2=>c2,n0=>n0,n1=>n1,n2=>n2,n3=>n3,m0=>m0);
CT: correction port map (clk=>clk,rst=>rst,entrada=>auxentrada4,salida=>salida,n0=>n0,n1=>n1,n2=>n2,n3=>n3,m0=>m0,b1=>b1,b2=>b2);
nula<=(OTHERS=>'0');
auxentrada4<=entrada4(control);
aentrada<=entrada;

process(clk,rst) begin
  if rst='1' then
    control<=0;
    s<=(nula,nula,nula,nula);
    enable<='0';
    entrada1<=(nula,nula,nula,nula,nula,nula,nula);
    entrada2<=(nula,nula,nula,nula,nula,nula,nula);
    entrada3<=(nula,nula,nula,nula,nula,nula,nula);
    entrada4<=(nula,nula,nula,nula,nula,nula,nula);
  elsif clk' event and clk='1' then
    enable<='1';
      entrada1(control)<=aentrada;
		 if control=6 then
        control<=0;
        s<=(s3,s2,s1,s0);
        entrada2<=entrada1;
        entrada2(control)<=aentrada;
        entrada3<=entrada2;
        entrada4<=entrada3;
      else
        control<=control+1;
      end if;
  end if;
end process;

end Behavioral;