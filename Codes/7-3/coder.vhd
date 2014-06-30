library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity coder is
  port (ain:in std_logic_vector(2 downto 0);
       aout:out std_logic_vector(2 downto 0);
       clk,rst : in std_logic);
end coder;

architecture Behavioral of coder is

  signal omult0,omult1,omult2,omult3: std_logic_vector(2 downto 0);
  signal insum1,insum2,insum3,insum4: std_logic_vector(2 downto 0);
  signal osum1,osum2,osum3: std_logic_vector(2 downto 0);
  signal feedback,sw1,sw2,obanco: std_logic_vector(2 downto 0);
  signal control: integer range 0 to 7;
  signal estado,enable: std_logic;
  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

begin
  M0: multgalois port map(a=>sw1,b=>"011",mult=>omult0);
  M1: multgalois port map(a=>sw1,b=>"010",mult=>omult1);
  M2: multgalois port map(a=>sw1,b=>"001",mult=>omult2);
  M3: multgalois port map(a=>sw1,b=>"011",mult=>omult3);
  osum1<=insum1 xor omult1;
  osum2<=insum2 xor omult2;
  osum3<=insum3 xor omult3;
  feedback<=insum4 xor obanco;
  obanco<=ain when control<3 else (others=>'0');
  sw1<=feedback when control<3 else (others=>'0');
  sw2<=obanco when control<3 else insum4;

process(clk,rst) begin
if rst='1' then
  insum1<=(others=>'0');
  insum2<=(others=>'0');
  insum3<=(others=>'0');
  insum4<=(others=>'0');
  estado<='0';
  control<=0;
  aout<=(others=>'0');
  enable<='0';
elsif clk' event and clk='1' then  enable<='1';
  insum1<=omult0;
  insum2<=osum1;
  insum3<=osum2;
  insum4<=osum3;
  aout<=sw2;
  if enable='1' then
    control<=control+1;
  end if;
  case estado is
    when '0' =>
      if control=3 then
         estado<='1';
      end if;
    when others =>
      if control=6 then
         estado<='0';
         control<=0;
      end if;
   end case;
end if;
end process;
end Behavioral;
