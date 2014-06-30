library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity syndrome is
  port (entrada:in std_logic_vector(2 downto 0);
       s0,s1,s2,s3:out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end syndrome;

architecture computation of syndrome is

  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

  component contador
    port (raiz:in std_logic_vector(2 downto 0);
         oalfa:out std_logic_vector(2 downto 0);
         clk,rst : in std_logic);
  end component;

  signal imult,omult0,omult1,omult2,omult3: std_logic_vector(2 downto 0);
  signal bmult0,bmult1,bmult2,bmult3: std_logic_vector(2 downto 0);
  signal osum0,osum1,osum2,osum3: std_logic_vector(2 downto 0);
  signal fb0,fb1,fb2,fb3: std_logic_vector(2 downto 0);
  signal control: integer range 0 to 7;

begin
  imult<=entrada;
  C0: contador port map(raiz=>"001",clk=>clk,rst=>rst,oalfa=>bmult0);
  C1: contador port map(raiz=>"010",clk=>clk,rst=>rst,oalfa=>bmult1);
  C2: contador port map(raiz=>"011",clk=>clk,rst=>rst,oalfa=>bmult2);
  C3: contador port map(raiz=>"100",clk=>clk,rst=>rst,oalfa=>bmult3);
  M0: multgalois port map(a=>imult,b=>bmult0,mult=>omult0);
  M1: multgalois port map(a=>imult,b=>bmult1,mult=>omult1);
  M2: multgalois port map(a=>imult,b=>bmult2,mult=>omult2);
  M3: multgalois port map(a=>imult,b=>bmult3,mult=>omult3);
  osum0<=fb0 xor omult0;
  osum1<=fb1 xor omult1;
  osum2<=fb2 xor omult2;
  osum3<=fb3 xor omult3;

process(clk,rst) begin
  if rst='1' then
    fb0<=(others=>'0');
    fb1<=(others=>'0');
    fb2<=(others=>'0');
    fb3<=(others=>'0');
    s0<=(others=>'0');
    s1<=(others=>'0');
    s2<=(others=>'0');
    s3<=(others=>'0');
    control<=1;
  elsif clk' event and clk='1' then
    control<=control+1;
    if control=7 then
      control<=1;
      s0<=osum0;
      s1<=osum1;
      s2<=osum2;
      s3<=osum3;
      fb0<=(others=>'0');
      fb1<=(others=>'0');
      fb2<=(others=>'0');
      fb3<=(others=>'0');
    else
      fb0<=osum0;
      fb1<=osum1;
      fb2<=osum2;
      fb3<=osum3;
    end if;
  end if;

end process;
end computation;
