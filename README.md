
# Implementation of a reconfigurable coder and decoder Reed-Solomon on a FPGA.

Detection and correction codes have been widely used in the last decades, because of them it is possible to guarantee reliable transmissions, avoiding the loss of information.Thanks to these codes, it is possible to send information to so long distances as those we can find in the space and with no loss of information. Because of this, the study of the correction and detection codes has been studied from '60s to now.

Among all codes which have been implemented and purposed, there is one which is presented in the most applications. Reed-Solomon codes have been implemented in areas such as mobile telephony, storage devices (CD), space crafts (the Galileo spacecraft to Jupiter in 1989, the Magellan spacecraft to Venus same year or the Ulysses spacecraft to the sun in 1990) and of course, on satellite transmissions of Digital Video Broadcasting (DVB), digital television ISDB-T and on xDLS systems of wired communications.

This repository is about the implementation of Reed-Solomon codes, you will find a book on PDF writteng by me. In the first chapter the
objectives of this work are exposed, continuing with the introduction of the different kind of error correction and detection codes. After that, we explain the importance of the Galois Fields to build up these codes and how the addition and multiplication operation are implemented on hardware in order to work with Reed-Solomon codes on a FPGA. Later, it is described the idea behind Reed-Solomon coding, the coding algorithm is shown along with its implementation. This implementation is generalized to any Reed-Solomon code. After that we explain Reed-Solomon decoding and each module that composes it. We generalize the algorithm and the implementation to be able to build up any Reed-Solomon decoding. In the chapter 6 it is described the type of communication that was used between FPGA and the computer which is used to test the Reed-Solomon codes, besides, we explain about the control panel that handles the configuration of coding, noise module and decoding inside FPGA in order to validate the code. Finally, we expose the results and conclusions of the implementation and it is proposed some future works in the area.

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
