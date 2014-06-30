library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity forney is
  port (n0,n1,n2,n3:out std_logic_vector(2 downto 0);
       m0:out std_logic_vector(2 downto 0);
       s0,s1,s2,s3:in std_logic_vector(2 downto 0);
       c1,c2:in std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end forney;

architecture Behavioral of forney is

  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

  signal sn,n,zero,snc0,snc1,snc2,r11,r21,r22,an0,an1,an2,an3,am0: std_logic_vector(2 downto 0);
  signal enable: std_logic;
  signal k:integer range 0 to 7;

	 constant c0: std_logic_vector(2 downto 0):="001";

begin

  zero<="000";

  sn<=  s0 when k=0 else
  s1 when k=1 else
  s2 when k=2 else
  s3 when k=3 else
  zero;

  Mu0: multgalois port map(a=>sn,b=>c0,mult=>snc0);
  Mu1: multgalois port map(a=>sn,b=>c1,mult=>snc1);
  Mu2: multgalois port map(a=>sn,b=>c2,mult=>snc2);

  n<=snc0 xor r11 xor r22;
  am0<=c1;

process(clk,rst) begin

if rst='1' then
  enable<='0';
  r11<=zero;
  r21<=zero;
  r22<=zero;
  n0<=zero;
  n1<=zero;
  n2<=zero;
  n3<=zero;
  m0<=zero;
  k<=0;
elsif clk' event and clk='1' then
  r11<=snc1;
  r21<=snc2;
  r22<=r21;
  enable<='1';
    if k=6 then
      k<=0;
      n0<=an0;
      n1<=an1;
      n2<=an2;
      n3<=an3;
      m0<=am0;
    else
      k<=k+1;
    end if;
  if k=0 then
    an0<=n;
  elsif k=1 then
    an1<=n;
  elsif k=2 then
    an2<=n;
  elsif k=3 then
    an3<=n;
  end if;
end if;

end process;
end Behavioral;
