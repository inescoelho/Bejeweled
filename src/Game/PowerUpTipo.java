/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */

package Game;

import java.util.ArrayList;

import Game.GraphicalEngine.EngineConfig;

public class PowerUpTipo extends Peca {
	private static final long serialVersionUID = 1L;

	// CONSTRUTORES
	/**
	 * Inicia uma nova peca para colocar na grelha com coordenadas destino
	 */
	public PowerUpTipo(int tp, int x, int y, float cX, float cY) {
		super(tp, 3, x, y, EngineConfig.INITIAL_POSITION,
				EngineConfig.INITIAL_POSITION, cX, cY, true);
	}

	// METODOS
	/**
	 * Remove da grelha todas as pecas do mesmo tipo da peca e coloca as pecas
	 * no array de pecas a Remover do ecra
	 * 
	 * @param grid
	 *            grelha de jogo
	 * @param pecasToRemove
	 *            armazena as pecas a remover do ecra
	 */
	@Override
	public void destroiPeca(Peca[][] grid, ArrayList<Peca> pecasToRemove) {
		int tipo = this.getTipo();
		int x = this.getPosx();
		int y = this.getPosy();

		if (grid[x][y] != null) {
			grid[x][y].setMoving(true);
			pecasToRemove.add(grid[x][y]);
			grid[x][y] = null;
		}
		
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid.length; j++)
				if (grid[i][j] != null && grid[i][j].getTipo() == tipo) {
					grid[i][j].destroiPeca(grid, pecasToRemove);
				}

	}
}
