/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */

package Game;

import Game.GUI.EndGUI;
import Game.GraphicalEngine.DarkSide3D;

public abstract class Nivel {
	/**
	 * Numero do nivel do jogo
	 */
	private int level;
	/**
	 * Tabuleiro do jogo
	 */
	private Grid gameGrid;
	/**
	 * Pontuacao do jogador
	 */
	private Score scoreJogador;
	/**
	 * Ultima peca a ser seleccionada pelo jogador; Aparece no ecra seleccionada
	 */
	Peca lastSelection;
	/**
	 * Motor Grafico de Animacao
	 */
	DarkSide3D animation;
	/**
	 * Objeto que criou o Nivel
	 */
	Game Game;
	/**
	 * Menu que aparece no final do jogo
	 */
	EndGUI end;

	// CONSTRUTOR
	/**
	 * Nivel do jogo
	 * 
	 * @param gr
	 *            grelha do jogo
	 * @param pont
	 *            pontuacao do jogador
	 */
	Nivel(Game game, Grid gr, Score pont) {
		this.Game = game;
		this.level = pont.getLevel();
		this.gameGrid = gr;
		this.scoreJogador = pont;
		this.lastSelection = null;
		this.end = new EndGUI(game.getBj_inicio());
	}

	// METODOS - Getters and Setters
	public Grid getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(Grid gameGrid) {
		this.gameGrid = gameGrid;
	}

	public Score getScoreJogador() {
		return scoreJogador;
	}

	public void setScoreJogador(Score scoreJogador) {
		this.scoreJogador = scoreJogador;
	}

	public Peca getLastSelection() {
		return lastSelection;
	}

	public void setLastSelection(Peca lastSelection) {
		this.lastSelection = lastSelection;
	}

	public DarkSide3D getAnimation() {
		return animation;
	}

	public void setAnimation(DarkSide3D animation) {
		this.animation = animation;
	}

	public int getLevel() {
		return level;
	}

	public Game getGame() {
		return Game;
	}

	public void setGame(Game game) {
		this.Game = game;
	}
	
	public EndGUI getEnd() {
		return end;
	}

	public void setEnd(EndGUI end) {
		this.end = end;
	}

	// METODOS
	/**
	 * Inicia o jogo do nivel
	 * 
	 * @return (true) se o jogo foi concluido com sucesso (false) se o jogador
	 *         perdeu o jogo
	 */
	public abstract void playGame(int width, int height);

	/**
	 * Detecta uma peca seleccionada Se nao houver nenhuma peca seleccionada,
	 * esta fica seleccionada, senao confirma se as duas pecas estao juntas e se
	 * a sua troca e possivel. Se a troca for possivel, move as pecas, senao a
	 * ultima peca premida fica seleccionada
	 * 
	 * @param peca2
	 *            peca seleccionada pelo utilizador
	 */
	public abstract void inputPecas(Peca peca2);

	/**
	 * Confirma se duas pecas estao lado a lado
	 * 
	 * @return (true) se estao lado a lado (false) caso contrario
	 */
	boolean confirmaVizinhanca(Peca peca1, Peca peca2) {
		boolean flag;
		int x1, y1, x2, y2;

		x1 = peca1.getPosx();
		y1 = peca1.getPosy();
		x2 = peca2.getPosx();
		y2 = peca2.getPosy();

		// lado a lado horizontalmente
		if (x1 == x2 && (y1 == y2 - 1 || y1 == y2 + 1))
			flag = true;
		// lado a lado verticalmente
		else if (y1 == y2 && (x1 == x2 - 1 || x1 == x2 + 1))
			flag = true;
		// as pecas nao estao lado a lado
		else
			flag = false;

		return flag;
	}

	/**
	 * Verifica a pontuacao obtida e se esta for superior ou igual a necessaria
	 * para passar de nivel, determina a passagem de nivel ou o termino do jogo
	 */
	abstract boolean verifyScore();

	/**
	 * Termina a animação de jogo e inicia o menu de jogo respetivo. Altera a
	 * musica de jogo para o main theme
	 */
	public abstract void terminate();
}
