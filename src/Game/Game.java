/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */

package Game;

import Game.GUI.ErrorGUI;

public class Game {
	/**
	 * Onde o objeto Game foi iniciado
	 */
	private Bejeweled bj_inicio;
	/**
	 * Define o tipo de jogo (false) classico (true) speed
	 */
	private boolean tipo;
	/**
	 * Numero total de niveis do jogo. Este valor e 1, em caso de teste. O modo de
	 * jogo contem 10 niveis
	 */
	private int totalNiveis;
	/**
	 * Dimensao (numero de elementos em cada linha) da grelha de jogo
	 */
	private int dimension;
	/**
	 * Pontuacao do jogador
	 */
	private Score pontuacao;

	// CONSTRUTOR
	/**
	 * Utilizado em modo de Teste
	 */
	Game(Bejeweled bj, Boolean testeFlag) {
		this.bj_inicio = bj;
		this.tipo = testeFlag;
		this.totalNiveis = 1;
		this.dimension = 8;
		this.pontuacao = new Score();
	}

	/**
	 * Utilizado em modo de Jogo
	 */
	Game(Bejeweled bj, boolean testeFlag, int dim) {
		this.bj_inicio = bj;
		this.tipo = testeFlag;
		this.totalNiveis = 10;
		this.dimension = dim;
		this.pontuacao = new Score();
	}

	// METODOS - Getters and Setters
	public Bejeweled getBj_inicio() {
		return bj_inicio;
	}

	public void setBj_inicio(Bejeweled bj_inicio) {
		this.bj_inicio = bj_inicio;
	}

	public int getTotalNiveis() {
		return totalNiveis;
	}

	public void setTotalNiveis(int totalNiveis) {
		this.totalNiveis = totalNiveis;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Score getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Score pontuacao) {
		this.pontuacao = pontuacao;
	}

	// METODOS
	/**
	 * Incrementa o numero do nivel e inicia o nivel
	 * 
	 * @param testeFlag
	 *            descrimina o modo de jogo
	 */
	public void startGame(boolean testeFlag) {
		/**
		 * Incrementa o numero do nivel em que vai o jogo
		 */
		this.getPontuacao().setLevel(this.getPontuacao().getLevel() + 1);
		/**
		 * Troca de musica para Imperial March
		 */
		changeMusic(true);
		/**
		 * Cria a grelha de jogo e inicia o jogo do nivel
		 * 
		 */
		createLevel (testeFlag);
	}
	
	/**
	 * Cria a grelha de jogo e inicia o jogo do nivel
	 * 
	 * @param testeFlag
	 *            descrimina o modo de jogo
	 */
	public void createLevel (boolean testeFlag) {
		Grid gameGrid;
		Nivel nivelJogo;

		/**
		 * Cria a grelha de jogo Se testeFlag true, cria grelha a partir do
		 * ficheiro Senao, cria grelha de raiz, com a dimensao pretendida
		 */
		if (testeFlag) {
			gameGrid = new Grid(8);
			gameGrid.createGameFromFile();
		} else {
			gameGrid = new Grid(this.getDimension());
			gameGrid.createGrid();
		}

		/**
		 * Se nao for possivel carregar a grelha de teste, aparece um ecra de
		 * erro
		 */
		if (gameGrid.getGameGrid()[0][0] == null) {
			this.getBj_inicio().getContentor().add(new ErrorGUI(this.getBj_inicio()));
			this.getBj_inicio().getFrame().setVisible(true);
			return;
		}

		/**
		 * Cria o nivel do jogo, consoante a opcao de tipo do jogo seleccionada
		 * pelo utilizador
		 */
		if (!this.isTipo())
			nivelJogo = new Classico(this, gameGrid, this.getPontuacao());
		else
			nivelJogo = new Speed(this, gameGrid, this.getPontuacao());

		System.out.println("Criado nivel " + this.getPontuacao().getLevel()
				+ " do tipo " + this.isTipo());

		/**
		 * Inicia o jogo do nivel
		 */
		nivelJogo.playGame(this.getBj_inicio().getWidth(), this.getBj_inicio().getHeight());

		return;
	}

	/**
	 * Troca de musica
	 * 
	 * @param change
	 *            (true) alterna para Imperial March (false) alterna para Star
	 *            Wars Main Theme
	 */
	void changeMusic(boolean change) {
		if (change == true) {
			// Troca de musica para Imperial March
			Bejeweled.som.clip.stop();
			Bejeweled.som.setSoundGame(true);
			try {
				Bejeweled.som.inicializa();
			} catch (Exception e) {
			}
			
		} else {
			// Troca de musica para Star Wars Main Theme
			Bejeweled.som.clip.stop();
			Bejeweled.som.setSoundGame(false);
			try {
				Bejeweled.som.inicializa();
			} catch (Exception e) {
			}
		}
	}
}
