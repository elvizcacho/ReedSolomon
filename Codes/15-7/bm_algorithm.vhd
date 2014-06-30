library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity bm_algorithm is
  port (s0,s1,s2,s3:in std_logic_vector(2 downto 0);
       c1,c2:out std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end bm_algorithm;

architecture Behavioral of bm_algorithm is

  type bank is array (2 downto 0) of std_logic_vector(2 downto 0);
  signal c,p,h:bank;
  signal d,dm,dm2,ddm2,sk,sk1,ck1,ckc1,selh1,sk2,ck2,ckc2,selh2: std_logic_vector(2 downto 0);
  signal k: integer range 0 to 7;
  signal L,lp: integer range 0 to 2;

  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

  component inverse
    port (ain:in std_logic_vector(2 downto 0);
         aout:out std_logic_vector(2 downto 0));
  end component;

begin

  sk<=  s0 when k=1 else
  s1 when k=2 else
  s2 when k=3 else
  s3 when k=4 else
  (others=>'0');

  sk1<=  s0 when k-1=1 else
  s1 when k-1=2 else
  s2 when k-1=3 else
  s3 when k-1=4 else
  (others=>'0');

  sk2<=  s0 when k-2=1 else
  s1 when k-2=2 else
  s2 when k-2=3 else
  s3 when k-2=4 else
  (others=>'0');

  selh1<=  h(0) when 1-lp=0 else
  h(1) when 1-lp=1 else
  (others=>'0');

  selh2<=  h(0) when 2-lp=0 else
  h(1) when 2-lp=1 else
  (others=>'0');

  ck1<=sk1 when L>0 else (others=>'0');
  ck2<=sk2 when L>1 else (others=>'0');

  M0: multgalois port map(a=>ck1,b=>c(1),mult=>ckc1);
  M1: multgalois port map(a=>ck2,b=>c(2),mult=>ckc2);
  M2: multgalois port map(a=>ddm2,b=>p(0),mult=>h(0));
  M3: multgalois port map(a=>ddm2,b=>p(1),mult=>h(1));
  M4: multgalois port map(a=>ddm2,b=>p(2),mult=>h(2));
  M5: multgalois port map(a=>dm2,b=>d,mult=>ddm2);
  I0: inverse port map(ain=>dm,aout=>dm2);

  d<=sk xor ckc1 xor ckc2;

process(clk,rst) begin

  if rst='1' then
		k<=1;
    L<=0;
    p<=("000","000","001");
    c<=("000","000","001");
    dm<=("001");
    lp<=1;
    c1<=("000");
    c2<=("000");
elsif clk' event and clk='1' then
  if k>0 and k<5 then
    if d="000" then
      lp<=lp+1;
    else
      c(1)<=c(1) xor selh1;
      c(2)<=c(2) xor selh2;
      if (2*L)>=k then
        lp<=lp+1;
		 else
        p<=c;
        L<=k-L;
        dm<=d;
        lp<=1;
      end if;
    end if;
  end if;
  if k=7 then
    k<=1;
    L<=0;
    p<=("000","000","001");
    c<=("000","000","001");
    dm<=("001");
    lp<=1;
    c1<=c(1);
    c2<=c(2);
  else
    k<=k+1;
  end if;
end if;

end process;
end Behavioral;