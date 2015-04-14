package Game.GraphicalEngine;

/**
 * Class to store the 3D engine's constants
 * 
 * @author CK
 * 
 */
public class EngineConfig {

	/**
	 * Profundidade (z) das pecas no cenario
	 */
	public static final float PIECES_Z = 0.0f;
	public static final float CAMERA_ROTATING_SPEED = 1.0f;
	public static final float CAMERA_SPEED_MOFIDIER = 25;
	public static final int COLOR_DEPTH = 32;
	public static final float OBJECT_SCALE = 0.5f;
	public static final float PIECES_PULSATING_SCALE_MAX = 1.333f;
	public static final float PIECES_PULSATING_SCALE_MIN = 0.666f;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int PIECES_PULSATING_SPEED = 6;
	public static final float PIECES_ROTATING_SPEED = 1.00f;
	public static final float DEATHSTAR_ROTATING_SPEED = 0.025f;
	public static final float PIECES_MOVING_SPEED = 0.005f;
	/**
	 * Used to control when a piece arrived to it's distance (is within the tolerance)
	 */
	public static final float PIECES_MOVING_DISTANCE_TOLERANCE = 0.01f;
	/**
	 * Posicao inicial das pecas adicionadas ao cenario
	 */
	public static final float INITIAL_POSITION = 15f;
	/**
	 * Tempo usado para detectar modificacoes da grid a' medida que as pecas se deslocam.
	 */
	public static final long MATCH_DETECTION_TIMEOUT = 500;
	public static final String MODELS_DIRECTORY = "models";
	public static final String TEXTURES_DIRECTORY = "models";
}
