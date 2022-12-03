package it.prova.ascii;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageASCIIfier {

	public static void ASCIIfy(String inputFileName, String inputFileExtension, String outputFileName, int imageHigth,
			int imageWidth, int resolution, boolean inverted, boolean showConsoleOutput, boolean convertToPng) {

		BufferedImage immagine = null; // buffer
		File file = null; // file di input

		String inputPath = ".\\resources\\images\\";
		String outputPath = ".\\resources\\texts\\";

		try {// provo a leggere da file un'immagine
			file = new File(inputPath + inputFileName + "." + inputFileExtension);
			immagine = new BufferedImage(imageWidth, imageHigth, BufferedImage.TYPE_4BYTE_ABGR);
			immagine = ImageIO.read(file);
			System.out.println("Lettura immagine eseguita....");
		} catch (IOException e) {
			System.out.println("Si e' verificato un problema: ");
			e.printStackTrace();
		}

		if (convertToPng) {
			try {// salvo l'immagine ottenuta in un nuovo file
				File f = new File(inputPath+inputFileName+".png");
				ImageIO.write(immagine, "png", f);
				System.out.println("Scrittura nuova immagine eseguita....");
			} catch (IOException e) {
				System.out.println("Si e' verificato un problema: ");
				e.printStackTrace();
			}
		}
		
		//sequenze di caratteri ascii da usare come "scala di grigi"
		char[] asciiSymbols = { ' ', '.', '.', ',', '-', '_', '°', 'o', '0', '&', '@' };
		char[] negativeAsciiSymbols = { '.','@', '&', '0', 'o', '°', '_', '-', ',', '.',  ' ' };

		String[] lines = new String[imageHigth / resolution];

		for (int i = 0; i < imageHigth; i += resolution) {
			String line = new String("");
			for (int j = 0; j < imageWidth; j += resolution) {
				float luminance = getLuminance(immagine, i, j, resolution);
				char character;
				if (!inverted) {
					character = asciiSymbols[(int) luminance];
				} else {
					character = negativeAsciiSymbols[(int) luminance];
				}
				line += character + " ";
			}
			lines[i / resolution] = line;
		}
		File output;
		if (inverted)
			output = new File(outputPath + outputFileName + "_inverted.txt");
		else
			output = new File(outputPath + outputFileName + ".txt");
		try {
			FileWriter textWriter = new FileWriter(output);
			for (String line : lines) {
				textWriter.write(line + "\n");
			}
			textWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (showConsoleOutput) {
			for (String line : lines) {
				System.out.println(line);
			}
		}

	}

	private static float getLuminance(BufferedImage immagine, int pixelY, int pixelX, int latoQuadrato) {

		int divisore = latoQuadrato * latoQuadrato;
		Float valore = 0f;

		for (int i = 0; i < latoQuadrato; i++) {
			for (int j = 0; j < latoQuadrato; j++) {
				try {

					int color = immagine.getRGB(j + pixelX, i + pixelY);

					int red = (color >>> 16) & 0xFF;
					int green = (color >>> 8) & 0xFF;
					int blue = (color >>> 0) & 0xFF;

					valore += (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 25.5f;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("i:" + i + " ,j:" + j);
				}

			}
		}

		return valore / divisore;
	}
}
