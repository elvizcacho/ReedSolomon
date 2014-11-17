
# Implementacion of a reconfigurable coder and decoder Reed-Solomon on a FPGA.

Reed-Solomon_v2.0 program can create any Reed-Solomon coder and decoder to be programmed on a FPGA. PC communicates with the FPGA through UART protocol so you can send data to the coder and then these data are became in codewords to be transmitted on a channel that is emulated into the FPGA, the codewords are corrupted with a probability which is assigned by the user and then these codewords are sending to the decoder to detect and correct the errors; in this way you can prove any Reed-Solomon code that you create on the software and which is programmed on the FPGA.

CodificadorRS folder:

Contains the source of a software programmed on JAVA which writes the VHDL entities of any Reed-Solomon coder and decoder.

Codes:

Contains the .bit files for RS(7,3),RS(15,3),RS(15,5),RS(15,7),RS(15,9) ready to be programmed on a FPGA Spartan 3AN. The folder called 15-7 contains the .vhd that were created by the program ReedSolomon_v2.0.jar
for a code RS(7,3).

The book: 'Implementation of a reconfigurable coder and decoder Reed-Solomon on a FPGA' is a description about the thesis degree, unfortunately this book has not been translated to English and is only available in a Spanish version.

If somebody could translate the book, It would be awesome!!

In the book is explained how the whole thesis was developed and implemented. Also there is a explanation of how to use the software and the algorithms used in the hardware implementation and the generalzation of these to create any RS code.

If you have any question about this project you can contact me via email.

elvizcacho@gmail.com

-Sebastián Vizcaíno
