package Game.Files;
/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */ 

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FicheiroDeObjetos {

	private ObjectInputStream iS; 
	private ObjectOutputStream oS;
	
	//Metodo para abrir um ficheiro para leitura 
	public void abreLeitura(String nomeDoFicheiro) throws IOException{
			iS = new ObjectInputStream(new FileInputStream(nomeDoFicheiro));
	}
	//Metodo para abrir um ficheiro para escrita
	//Recebe o nome do ficheiro
	public void abreEscrita(String nomeDoFicheiro) throws IOException{
		oS = new ObjectOutputStream(new FileOutputStream(nomeDoFicheiro));
	}
	
	//Metodo para ler um objecto do ficheiro
	//Devolve o objecto lido 
	public Object leObjecto() throws IOException,ClassNotFoundException{
		return iS.readObject();
	}
	//Metodo para escrever um objecto no ficheiro
	//Recebe o objecto a escrever
	public void escreveObjecto(Object o) throws IOException{
		oS.writeObject(o);
	}
	
	//Metodo para fechar um ficheiro aberto em modo leitura 
	public void fechaLeitura() throws IOException{
		iS.close();
	}
	//Método para fechar um ficheiro aberto em modo escrita
	public void fechaEscrita() throws IOException{
		oS.close();
	}
	
}
