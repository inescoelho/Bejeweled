/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */

package Game;

import java.util.ArrayList;

public class Score {
	/**
	 * Nivel do Jogo
	 */
	int level;
	/**
	 * Pontuacao do Jogo
	 */
	int score;
	/**
	 * Pontuacao obtida pela destruicao de uma peca no respetivo nivel
	 */
	int pontPeca;

	// CONSTRUTOR
	Score() {
		this.level = 0;
		this.score = 0;
	}

	// METODOS - Getters and Setters
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPontPeca() {
		return pontPeca;
	}

	public void setPontPeca(int pontPeca) {
		this.pontPeca = pontPeca;
	}

	/**
	 * Calcula a pontuacao obtida pela destruicao de um conjunto de epcas do
	 * mesmo tipo
	 * 
	 * @param pecas
	 *            conjunto de pecas do mesmo tipo destruidas
	 */
	public void calculaPontuacao(ArrayList<Peca> pecas) {
		int pont, n, pontosPorPeca;

		System.out.println("PONTOS" + this.getPontPeca());

		n = pecas.size();
		if (n > 3)
			pontosPorPeca = (n - 3) * 2 * this.getPontPeca();
		else
			pontosPorPeca = this.getPontPeca();

		pont = pecas.size() * pontosPorPeca;

		this.setScore(this.getScore() + pont);

		System.out.println("PONTOS" + this.getPontPeca() + " Com Mod: "
				+ pontosPorPeca + " Pontuacao: " + pont + " N_Pecas: " + n);

		return;
	}

}
