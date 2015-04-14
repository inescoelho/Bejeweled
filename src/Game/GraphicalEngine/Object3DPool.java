package Game.GraphicalEngine;

import java.util.Hashtable;

import Game.Peca;

import com.threed.jpct.Object3D;

/**
 * Maps object3d models with pieces of a given type and powerup
 * 
 * @author CK
 * 
 */
public class Object3DPool {

	private Hashtable<PecaObject3DMapping, Object3D> peca_o3d_mappings;

	public Object3DPool() {
		peca_o3d_mappings = new Hashtable<>();
	}

	/**
	 * Stores an object3d associated with a piece of a given type and powerup
	 * 
	 * @param type
	 * @param poweruplevel
	 * @param o3d
	 */
	public void addMapping(int type, int poweruplevel, Object3D o3d) {
		PecaObject3DMapping m = new PecaObject3DMapping(type, poweruplevel);
		peca_o3d_mappings.put(m, o3d);
	}

	/**
	 * Stores an object3d associated with a piece of a given type and powerup
	 * 
	 * @param type
	 * @param poweruplevel
	 * @param o3d
	 */
	public void addMapping(Peca p, Object3D o3d) {
		PecaObject3DMapping m = new PecaObject3DMapping(p.getTipo(), p.getPowerup());
		peca_o3d_mappings.put(m, o3d);
	}

	/**
	 * Gets an object3d associated with a piece of a given type and powerup
	 * 
	 * @param type
	 * @param poweruplevel
	 * @return
	 */
	public Object3D getMapping(int type, int poweruplevel) {
		PecaObject3DMapping m = new PecaObject3DMapping(type, poweruplevel);
		return peca_o3d_mappings.get(m);
	}

	/**
	 * Gets an object3d associated with a piece of a given type and powerup
	 * 
	 * @param type
	 * @param poweruplevel
	 * @return
	 */
	public Object3D getMapping(Peca p) {
		PecaObject3DMapping m = new PecaObject3DMapping(p.getTipo(), p.getPowerup());
		return peca_o3d_mappings.get(m);
	}

	public int getMappingSize() {
		return peca_o3d_mappings.size();
	}

	@Override
	public String toString() {
		return peca_o3d_mappings.toString();
	}

}
