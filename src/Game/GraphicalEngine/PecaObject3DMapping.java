package Game.GraphicalEngine;

import com.threed.jpct.Object3D;

/**
 * Container for associating an Object3D with a piece of a given type and powerup
 * 
 * @author CK
 * 
 */
public class PecaObject3DMapping {
	private Object3D object3D;
	private int piecePowerType;
	private int pieceType;

	public PecaObject3DMapping(int type, int poweruplevel) {
		this.piecePowerType = poweruplevel;
		this.pieceType = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PecaObject3DMapping other = (PecaObject3DMapping) obj;
		if (piecePowerType != other.piecePowerType)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}

	public Object3D getObject3D() {
		return object3D;
	}

	public int getPiecePowerType() {
		return piecePowerType;
	}

	public int getPieceType() {
		return pieceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + piecePowerType;
		result = prime * result + pieceType;
		return result;
	}

	public void setObject3D(Object3D object3d) {
		object3D = object3d;
	}

	public void setPiecePowerType(int piecePowerType) {
		this.piecePowerType = piecePowerType;
	}

	public void setPieceType(int pieceType) {
		this.pieceType = pieceType;
	}

	@Override
	public String toString() {
		return "pieceType=" + pieceType + ", piecePowerType=" + piecePowerType;
	}

}
