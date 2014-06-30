package Codificador;

import java.io.IOException;

public class Reed_Solomon {

	public static void RS(int n,int k) throws IOException{

	int nbits=(int) Math.ceil(Math.log(n)/Math.log(2));
	galoisfield.bits=nbits;
	galoisfield.crearGF();
	aleatorio.vhd();
	aleatorio_corto.vhd();
	semisum.vhd();
	complete_adder.vhd();
	sumadornbits.vhd(nbits);
	multgalois.vhd(nbits);
	reloj_oversampling.vhd();
	transmision_uart.vhd();
	recepcion_uart.vhd();
	pol_generador.bits=nbits;
	pol_generador.error=n-k;
	pol_generador.crear_pg();
	coder.vhd(nbits, n, k);
	contador.vhd(nbits, n, k);
	syndrome.vhd(nbits, n, k);
	bm_algorithm.vhd(nbits, n, k);
	modulo_comunicacion.vhd(nbits, n, k);
	inverse.vhd(nbits);
	chien.vhd(nbits, n, k);
	forney.vhd(nbits, n, k);
	potencia.vhd(nbits, n, k);
	correction.vhd(nbits, n, k);
	decoder.vhd(nbits, n, k);
	semisum.ucf();
	
	}
}
