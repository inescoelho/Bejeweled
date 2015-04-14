/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */

package Game;

import java.io.Serializable;
import java.util.ArrayList;

import com.threed.jpct.Object3D;

public class Peca implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Tipos diferentes de peca no tabuleiro Existem 6 tipos diferentes (0-5)
	 */
	private int tipo;
	/**
	 * indica o tipo de power up da peca (0) nenhum (1) PU linha (2): PU
	 * ortogonal (3) PU tipo
	 */
	private int powerup;
	/**
	 * indica a posicao x da peca no array que armazena pecas
	 */
	private int posx;
	/**
	 * indica a posicao y da peca no array que armazena pecas
	 */
	private int posy;
	/**
	 * objecto3d que representa esta peca no ecra
	 */
	private Object3D objecto3D;
	/**
	 * indica a posicao x da peca na grelha visivel no ecra de jogo
	 */
	private float coorX;
	/**
	 * indica a posicao y da peca na grelha visivel no ecra de jogo
	 */
	private float coorY;
	/**
	 * indica a posicao z da peca na grelha visivel no ecra de jogo
	 */
	private float coorZ;
	/**
	 * indica a posicao x de destino da peca na grelha visivel no ecra de jogo
	 */
	private float destCoorX;
	/**
	 * indica a posicao y de destino da peca na grelha visivel no ecra de jogo
	 */
	private float destCoorY;
	/**
	 * indica se a peca se move
	 */
	private boolean moving;
	/**
	 * indica se e uma nova peca na grelha
	 */
	private boolean novaPeca;

	public boolean DEBUG = false;

	// CONSTRUTOR
	/**
	 * Inicia a peca na grelha
	 */
	public Peca(int tp, int pw, int x, int y, float cX, float cY, float destX,
			float destY, boolean novo) {
		this.tipo = tp;
		this.powerup = pw;
		this.posx = x;
		this.posy = y;
		this.coorX = cX;
		this.coorY = cY;
		this.coorZ = 0;
		this.destCoorX = destX;
		this.destCoorY = destY;
		this.moving = novo;
		this.novaPeca = novo;
	}

	// METODOS - Getters and Setters
	public float getCoorX() {
		return coorX;
	}

	public float getCoorY() {
		return coorY;
	}

	public float getCoorZ() {
		return this.coorZ;
	}

	public Object3D getObjecto3D() {
		return objecto3D;
	}

	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}

	public int getPowerup() {
		return powerup;
	}

	public int getTipo() {
		return tipo;
	}

	public float getDestCoorX() {
		return destCoorX;
	}

	public void setDestCoorX(float destCoorX) {
		this.destCoorX = destCoorX;
	}

	public float getDestCoorY() {
		return destCoorY;
	}

	public void setDestCoorY(float destCoorY) {
		this.destCoorY = destCoorY;
	}

	public void setCoorX(float coorX) {
		this.coorX = coorX;
	}

	public void setCoorY(float coorY) {
		this.coorY = coorY;
	}

	public void setCoorZ(float coorZ) {
		this.coorZ = coorZ;
	}

	public void setObjecto3D(Object3D objecto3d) {
		this.objecto3D = objecto3d;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public void setPowerup(int powerup) {
		this.powerup = powerup;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getID() {
		int id = -1;
		if (this.getObjecto3D() != null)
			id = this.getObjecto3D().getID();
		return id;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isNovaPeca() {
		return novaPeca;
	}

	public void setNovaPeca(boolean novaPeca) {
		this.novaPeca = novaPeca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getID();
		result = prime * result + posx;
		result = prime * result + posy;
		result = prime * result + powerup;
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peca other = (Peca) obj;
		if (objecto3D == null) {
			if (other.objecto3D != null)
				return false;
		} else if (this.getID() != other.getID())
			return false;
		if (posx != other.posx)
			return false;
		if (posy != other.posy)
			return false;
		if (powerup != other.powerup)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Peca [posx=" + posx + ", posy=" + posy + ", powerup=" + powerup
				+ ", tipo=" + tipo + ", coorX=" + coorX + ", coorY=" + coorY
				+ ", coorZ=" + coorZ + ", destCoorX=" + destCoorX
				+ ", destCoorY=" + destCoorY + ", moving=" + moving
				+ ", novaPeca=" + novaPeca + "]";
	}

	// METODOS
	/**
	 * Atualiza a posicao da peca na grelha e a sua coordenada destino
	 * 
	 * @param x
	 *            nova posicao x da grelha
	 * @param y
	 *            nova posicao y da grelha
	 * @param coordX
	 *            posicao x destino da grelha
	 * @param coordY
	 *            posicao y destino da grelha
	 */
	public void atualizaPosicao(int x, int y, float coordX, float coordY,
			boolean mov) {
		this.setPosx(x);
		this.setPosy(y);
		this.setDestCoorX(coordX);
		this.setDestCoorY(coordY);
		this.setMoving(mov);
	}

	/**
	 * Remove a peca da grelha e coloca-a no array de pecas a Remover do ecra
	 * 
	 * @param grid
	 *            grelha de jogo
	 * @param pecasToRemove
	 *            armazena as pecas a remover do ecra
	 */
	public void destroiPeca(Peca[][] grid, ArrayList<Peca> pecasToRemove) {
		int x = this.getPosx();
		int y = this.getPosy();

		if (grid[x][y] != null) {
			grid[x][y].setMoving(true);
			pecasToRemove.add(grid[x][y]);
			grid[x][y] = null;
		}
	}

}