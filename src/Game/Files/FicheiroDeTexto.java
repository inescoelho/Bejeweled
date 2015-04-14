package Game.Files;

import java.io.*;

public class FicheiroDeTexto {
	private BufferedReader fR;
	private static BufferedWriter fW;

	public void abreLeitura(String nomeDoFicheiro) throws IOException {
		fR = new BufferedReader(new FileReader(nomeDoFicheiro));
	}

	public static void abreEscrita(String nomeDoFicheiro) throws IOException {
		fW = new BufferedWriter(new FileWriter(nomeDoFicheiro));
	}

	// Metodo para ler uma linha do ficheiro
	// Devolve a linha lida
	public String leLinha() throws IOException {
		return fR.readLine();
	}

	// Método para escrever uma linha no ficheiro
	// Recebe a linha a escrever
	public void escreveLinha(String linha) throws IOException {
		fW.write(linha, 0, linha.length());
		fW.newLine();
	}

	// Metodo para ler um numero do ficheiro
	// Devolve o numero lido
	public int[] leNumero() throws IOException {
		int[] result = new int[2];
		String st = fR.readLine();
		if (st != null) {
			result[0] = 0;
			result[1] = Integer.parseInt(st);
		} else {
			result[0] = -1;
		}
		return result;
	}

	// Método para escrever um número inteiro no ficheiro
	// Recebe o número a escrever
	public void escreveNumero(double num) throws IOException {
		String st = "";
		st = String.valueOf(num);
		escreveLinha(st);
	}

	// Metodo para fechar um ficheiro aberto em modo leitura
	public void fechaLeitura() throws IOException {
		fR.close();
	}

	// Método para fechar um ficheiro aberto em modo escrita
	public void fechaEscrita() throws IOException {
		fW.close();
	}
}
