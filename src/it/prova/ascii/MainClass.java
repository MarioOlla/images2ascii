package it.prova.ascii;

public class MainClass {

	public static void main(String[] args) {
		
		/*
		 * parametri in ordine:
		 * -nome file di input
		 * -estensione file di input
		 * -nome file di output
		 * -altezza immagine
		 * -larghezza immagine
		 * -risoluzione : da un minimo di 1 (risoluzione massima) in su
		 * -immagine in negativo
		 * -mostra progresso in console
		 * -converti anche in png
		 */
		ImageASCIIfier.ASCIIfy("test", "png", "output", 1080, 1920, 4, false, false, false);
		
	}
	
}//fine classe
