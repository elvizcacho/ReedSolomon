library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity chien is
  port (b1,b2:out std_logic_vector(2 downto 0);
       c1,c2:in std_logic_vector(2 downto 0);
       clk,rst: in std_logic);
end chien;

architecture Behavioral of chien is

  component multgalois
    port (a,b:in std_logic_vector(2 downto 0);
         mult:out std_logic_vector(2 downto 0));
  end component;

  component contador
    port (raiz:in std_logic_vector(2 downto 0);
         oalfa:out std_logic_vector(2 downto 0);
         clk,rst : in std_logic);
  end component;

  component inverse
    port (ain:in std_logic_vector(2 downto 0);
         aout:out std_logic_vector(2 downto 0));
  end component;

  type bank is array (6 downto 0) of std_logic_vector(2 downto 0);
  signal matriz1:bank :=("101","111","110","011","100","010","001");
  signal getsum,nula,uno,invroot,root,ff1,tosum1,ab1,reg_1,sel_alfa1,ff2,tosum2,ab2,reg_2,sel_alfa2: std_logic_vector(2 downto 0);
  signal zero,enable : std_logic;
	 signal control: integer range 0 to 7;
  signal help_out: integer range 0 to 2;
	 constant c0: std_logic_vector(2 downto 0):="001";

begin

  M0: multgalois port map(a=>sel_alfa1,b=>reg_1,mult=>tosum1);
  M1: multgalois port map(a=>sel_alfa2,b=>reg_2,mult=>tosum2);
  I0: inverse port map(ain=>root,aout=>invroot);

  root<=matriz1(control);
  nula<=(OTHERS =>'0');
  uno<="001";
  getsum<=tosum1 xor tosum2 xor c0;
  zero<='1' when getsum=nula else '0';
  reg_1<=c1 when control=0 else ff1;
  reg_2<=c2 when control=0 else ff2;
  sel_alfa1<=uno when control=0 else "010";

  sel_alfa2<=uno when control=0 else "100";

process(clk,rst) begin

 if rst='1' then
  ff1<=nula;
  b1<=nula;
  ab1<=nula;
  ff2<=nula;
  b2<=nula;
  ab2<=nula;
  control<=0;
  help_out<=0;
  enable<='0';
elsif clk' event and clk='1' then
  enable<='1';
    ff1<=tosum1;
    ff2<=tosum2;
    if zero='1' then
      if help_out=0 then
        help_out<=help_out+1;
  	   ab1<=invroot;
        if control=6 then
          b1<=invroot;
          b2<=nula;
        end if;
      elsif help_out=1 then
        help_out<=help_out+1;
  	   ab2<=invroot;
        if control=6 then
          b1<=ab1;
          b2<=invroot;
        end if;
      end if;
    elsif control=6 then
      b1<=ab1;
      b2<=ab2;
    end if;
    if control=6 then
      control<=0;
      help_out<=0;
      ab1<=nula;
      ab2<=nula;
    else
      control<=control+1;
    end if;
end if;

end process;
end Behavioral;