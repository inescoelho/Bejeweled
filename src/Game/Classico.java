/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */

package Game;

import Game.GraphicalEngine.DarkSide3D;

public class Classico extends Nivel {
	/**
	 * Pontuacao necessaria para passar de cada nivel
	 */
	static int[] pontLevel = { 1000, 2000, 4000, 8000, 12000, 16000, 20000,
			25000, 30000, 35000 };
	/**
	 * Valor de pontuacao base de cada peca, por nivel
	 */
	static int[] pontPeca = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };

	// CONSTRUTOR
	Classico(Game game, Grid gr, Score pont) {
		super(game, gr, pont);
		this.getScoreJogador().setPontPeca(pontPeca[this.getLevel() - 1]);
	}

	// METODOS - Getters and Setters
	public static int[] getPontPeca() {
		return pontPeca;
	}

	public static void setPontPeca(int[] pontPeca) {
		Classico.pontPeca = pontPeca;
	}

	public static int[] getPontLevel() {
		return pontLevel;
	}

	public static void setPontLevel(int[] pontLevel) {
		Classico.pontLevel = pontLevel;
	}

	// METODOS
	/**
	 * Inicia o jogo do nivel
	 * 
	 * @return (true) se o jogo foi concluido com sucesso (false) se o jogador
	 *         perdeu o jogo
	 */
	@Override
	public void playGame(int width, int height) {

		this.animation = new DarkSide3D(this, this.getGame().getBj_inicio()
				.isSimpleModels(), false, 0);
		this.getAnimation().loop(width, height);
		this.terminate();
		return;
	}

	/**
	 * Detecta uma peca seleccionada Se nao houver nenhuma peca seleccionada,
	 * esta fica seleccionada, senao confirma se as duas pecas estao juntas e se
	 * a sua troca e possivel. Se a troca for possivel, move as pecas, senao a
	 * ultima peca premida fica seleccionada
	 * 
	 * @param peca2
	 *            peca seleccionada pelo utilizador
	 */
	@Override
	public void inputPecas(Peca peca2) {
		Peca peca1 = this.getLastSelection();
		boolean flag, moves;

		if (peca1 == null)
			this.setLastSelection(peca2);

		else if (peca1 == peca2)
			this.setLastSelection(null);

		else {
			flag = this.confirmaVizinhanca(peca1, peca2);

			if (flag) {
				moves = this.getGameGrid().movePecas(peca2, peca1,
						this.animation, this.getScoreJogador());
				this.setLastSelection(null);

				verifyScore();

				/**
				 * Se nao houverem mais movimentos possiveis
				 */
				if (!moves) {
					this.getEnd().Configure(2, this.getScoreJogador());
					this.getGame().changeMusic(false);
					this.getAnimation().terminate();
				}
			} else
				this.setLastSelection(peca2);
		}
	}

	/**
	 * Verifica a pontuacao obtida e se esta for superior ou igual a necessaria
	 * para passar de nivel, determina a passagem de nivel ou o termino do jogo
	 */
	@Override
	boolean verifyScore() {
		boolean result = false, end = false;
		int level = this.getScoreJogador().getLevel();
		int score = this.getScoreJogador().getScore();

		if (score >= Classico.getPontLevel()[level - 1]) {
			if (level + 1 > this.getGame().getTotalNiveis())
				end = true;
			else {
				this.getScoreJogador().setLevel(level + 1);
				this.getScoreJogador().setPontPeca(
						Classico.getPontPeca()[level - 1]);
			}
		}

		if (end) {
			if (this.getGame().getTotalNiveis() == 1) {
				this.getEnd().Configure(4, this.getScoreJogador());
			} else {
				this.getEnd().Configure(3, this.getScoreJogador());
			}
			this.getGame().changeMusic(false);
			this.getAnimation().terminate();
		}

		return result;
	}

	/**
	 * Termina a animação de jogo e inicia o menu de jogo respetivo. Altera a
	 * musica de jogo para o main theme
	 */
	@Override
	public void terminate() {
		Bejeweled bj = this.getGame().getBj_inicio();

		this.getEnd().setTipoClassic(false);
		bj.getContentor().removeAll();
		bj.getContentor().add(this.getEnd());
		bj.getFrame().setVisible(true);
		this.getGame().changeMusic(false);
	}
}
