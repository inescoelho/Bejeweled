package Game.GraphicalEngine;

import Game.Nivel;
import Game.Peca;

/**
 * Thread which being executed, sends an piece clicked event to the game logic.
 * 
 * @author CK
 * 
 */
public class MouseEventHandler extends Thread {

	private Nivel level;
	private Peca piece;

	public MouseEventHandler(Nivel nivel, Peca p) {
		this.level = nivel;
		this.piece = p;
	}

	public void run() {
		// do the switch
		this.level.inputPecas(piece);
	}
}
