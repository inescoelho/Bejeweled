/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */
package Game;

import java.util.ArrayList;

import Game.GraphicalEngine.EngineConfig;

public class PowerUpOrtogonal extends Peca {
	private static final long serialVersionUID = 1L;

	// CONSTRUTORES
	/**
	 * Inicia uma nova peca para colocar na grelha com coordenadas destino
	 */
	public PowerUpOrtogonal(int tp, int x, int y, float cX, float cY) {
		super(tp, 2, x, y, EngineConfig.INITIAL_POSITION,
				EngineConfig.INITIAL_POSITION, cX, cY, true);
	}

	// METODOS
	/**
	 * Remove da grelha a linha e a coluna onde pertence a peca e coloca as
	 * pecas no array de pecas a Remover do ecra
	 * 
	 * @param grid
	 *            grelha de jogo
	 * @param pecasToRemove
	 *            armazena as pecas a remover do ecra
	 */
	@Override
	public void destroiPeca(Peca[][] grid, ArrayList<Peca> pecasToRemove) {
		int line = this.getPosy();
		int column = this.getPosx();
		
		if (grid[column][line] != null) {
			grid[column][line].setMoving(true);
			pecasToRemove.add(grid[column][line]);
			grid[column][line] = null;
		}
		
		
		
		// destroi linha
		for (int i = 0; i < grid.length; i++)
			if (grid[i][line] != null) {
				grid[i][line].destroiPeca(grid, pecasToRemove);
			}

		// destroi coluna
		for (int i = 0; i < grid.length; i++)
			if (grid[column][i] != null) {
				grid[column][i].destroiPeca(grid, pecasToRemove);
			}
	}
}
