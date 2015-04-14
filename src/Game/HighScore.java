package Game;
/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */ 

import java.io.Serializable;

public class HighScore implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Nome do Jogador
	 */
	private String nome;
	/**
	 * Pontuacao obtida pelo jogador
	 */
	private int pont;
	
	//CONSTRUTOR
	/**
	 * Pontuacao 
	 * 
	 * @param n nome do jogador
	 * @param p pontuacao obtida 
	 */
	public HighScore(String n, int p) 
	{
		this.nome = n;
		this.pont = p;
	}

	//METODOS - Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPont() {
		return pont;
	}

	public void setPont(int pont) {
		this.pont = pont;
	}

	@Override
	public String toString() {
		return nome + " " + pont;
	}
	
}