package Game.GraphicalEngine;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Game.Nivel;
import Game.Peca;
import Game.Speed;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

/**
 * 3D graphical interface for the game. Runs on it's own thread.
 * 
 * @author CK
 * 
 */
public class DarkSide3D {

	// private FrameBuffer buffer;
	private boolean button_down_laststate = false;
	private Camera camera;
	private Object3D deathstar;
	private SimpleVector deathstar_rotation_vector;
	private boolean doLoop;
	/**
	 * Tempo usado para contar o numero de imagens por segundo. Mais nada.
	 */
	private Ticker fps_ticker;
	/**
	 * nivel do jogo
	 */
	private Nivel game_level;
	private MouseEventHandler meh;
	private Object3DPool object3dpool;
	/**
	 * mapeia um id unico para cada peca da grelha
	 */
	private HashSet<Peca> pieces;

	private float pieces_angle;
	private HashSet<Peca> pieces_ids_pieces_being_removed;
	/**
	 * Tempo "local" para controlar o pulsar das pecas. Deve ser colocado a zero quando se clica numa, para iniciar o efeito do zero.
	 */
	private Ticker pulsating_ticker;
	/**
	 * Ticker used to count down remaining time in a speedy/timered game.
	 */
	private Ticker remainingTimeTicker;
	/**
	 * Temporizador usado para controlar o fluxo de tempo no motor. Usado para compensar computadores mais lentos e manter a velocidade de execucao do programa
	 * estavel.
	 */
	private Ticker ticker;
	/**
	 * Time available to play when in a speedy/timered game.
	 */
	private double time_to_play;
	/**
	 * If true, this is a speedy game, with timer.
	 */
	private boolean using_timer;

	private World world;
	private Object3D bg1;

	/**
	 * Creates the 3D window with hardware acceleration and initializes some internal structures.
	 * 
	 * @param level
	 * @param useSimpleModels
	 */
	public DarkSide3D(Nivel level, boolean useSimpleModels, boolean using_timer, double time_to_play) {
		// useSimpleModels = false;
		if (useSimpleModels)
			System.err.println("using simple models (cubes) - this is very likely to explode!");

		this.game_level = level;
		this.using_timer = using_timer;
		this.time_to_play = time_to_play;

		this.ticker = new Ticker();
		this.pulsating_ticker = new Ticker();
		this.fps_ticker = new Ticker();
		this.remainingTimeTicker = new Ticker();
		this.meh = null;

		// criar e configurar motor grafico
		Config.glVSync = false;
		Config.useMultipleThreads = false;
		Config.glUseVBO = true;
		Config.glColorDepth = EngineConfig.COLOR_DEPTH;
		Config.glMipmap = true;
		Config.glTriangleStrips = true;
		// trilinear is good and is not as heavy as anisotropic filtering
		Config.glTrilinear = true;
		// 1024/0.25 ~16 bits depth buffer precision
		Config.farPlane = 16383f;
		Config.nearPlane = 0.1f;
		// Config.tuneForIndoor();

		this.world = new World();

		Config.lightMul = 1;
		// intensidade da luz nao diminui com a distancia -> luz direccional (tipo sol)
		Config.fadeoutLight = false;
		// Config.linearDiv = 1.0f;
		Config.lightDiscardDistance = -1;
		float intensity = 255.0f;
		// world.addLight(new SimpleVector(495.7883, 933.9013, -80.215836), intensity, intensity, intensity);
		world.addLight(new SimpleVector(-0.822097, -18.9547, 58.682), intensity, intensity, intensity);
		intensity = 64.0f;
		// world.addLight(new SimpleVector(-288.0, -66.0, 315.0), intensity, intensity, intensity);

		// AMBIENT LIGHT
		this.world.setAmbientLight(0, 0, 0);

		this.pieces = new HashSet<>();
		this.pieces_ids_pieces_being_removed = new HashSet<>();
		this.object3dpool = new Object3DPool();

		loadTextures();
		if (useSimpleModels) {
			createModelPool_OLD();
		} else {
			createModelPool();
		}
		createScenery();
		createPieces();

		// posicionar a camera a' frente dos cubos
		this.camera = this.world.getCamera();
		resetCamera();
	}

	public void adicionaPecas(ArrayList<Peca> new_pieces) {
		// System.out.println("DarkSide3D.adicionaPecas():" + new_pieces);
		for (Peca peca : new_pieces) {
			// so se criam os objectos3d, ja que as propriedades e posicoes mudam ao longo do jogo
			createPiece(peca);
			// System.out.println("addded cube with ID " + cube_id + " at " + cube.getTransformedCenter());
		}
		synchronized (pieces) {
			pieces.addAll(new_pieces);
		}
	}

	private float convertDegreesToRadians(float degrees) {
		return (float) (degrees * Math.PI / 180f);
	}

	/**
	 * 
	 */
	private void createModelPool() {
		Object3D o3d = null;
		// only create objects, DO NOT ADD TO THE WORLD
		for (int type = 0; type < 6; type++) {
			for (int powerup = 0; powerup < 4; powerup++) {
				if (type == 0) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "falcon2.3ds", 0.00333f);
				} else if (type == 1) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "imperial_shuttle.3ds", 0.003f);
				} else if (type == 2) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "tie_bomber_2.3ds", 0.012f);
				} else if (type == 3) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "tie_fighter2.3ds", 0.00810f);
				} else if (type == 4) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "twinpod_bespin.3ds", 0.007f);
				} else if (type == 5) {
					o3d = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "x_wing_nogear.3ds", 0.0055f);
				}
				if (powerup == 0) {
					o3d.setAdditionalColor(Color.BLACK);
				} else if (powerup == 1) {
					o3d.setAdditionalColor(Color.BLUE);
				} else if (powerup == 2) {
					o3d.setAdditionalColor(Color.GREEN);
				} else if (powerup == 3) {
					o3d.setAdditionalColor(Color.RED);
				}
				this.object3dpool.addMapping(type, powerup, o3d);
			}
		}
	}

	private void createModelPool_OLD() {

		float piece_size = this.getNivel().getGameGrid().getRazaoTamPecas() * EngineConfig.OBJECT_SCALE / 2.0f;
		// only create objects, DO NOT ADD TO THE WORLD
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.PINK);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(0, 0, o3d);
		}
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.RED);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(1, 0, o3d);
		}
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.MAGENTA);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(2, 0, o3d);
		}
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.GREEN);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(3, 0, o3d);
		}
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.CYAN);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(4, 0, o3d);
		}
		{
			Object3D o3d = Primitives.getBox(piece_size, 1);
			o3d.setAdditionalColor(Color.ORANGE);
			o3d.rotateY((float) (Math.PI / 4.0));
			o3d.rotateMesh();
			this.object3dpool.addMapping(5, 0, o3d);
		}
	}

	private void createPiece(Peca peca) {
		// get base model representing the corresponding piece
		Object3D o3d_base = this.object3dpool.getMapping(peca);
		if (o3d_base != null) {
			// create the model representing the piece based on its template
			Object3D o3d = o3d_base.cloneObject();
			// set piece's Z
			peca.setCoorZ(EngineConfig.PIECES_Z);
			// optimize & prepare piece
			o3d.build();
			o3d.compile();
			// piece is to be clickable
			o3d.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
			// and shown on screen
			o3d.setVisibility(true);
			o3d.setSpecularLighting(true);
			// add it to the scenery/world
			world.addObject(o3d);
			// id do cubo (id que comeca em zero com o primeiro objecto e vai aumentando)
			peca.setObjecto3D(o3d);
			// guardar relacoes entre pecas e objectos3d
			synchronized (pieces) {
				pieces.add(peca);
			}
		} else {
			System.err.println("couldn't get an Objec3D base model for piece " + peca);
		}
	}

	private void createPieces() {
		// get matrix of pieces composing the initial game
		Peca[][] lista_pecas = this.getNivel().getGameGrid().getGameGrid();
		// setup each 3d object according to its piece
		for (Peca[] column : lista_pecas) {
			for (Peca peca : column) {
				createPiece(peca);
			}
		}
	}

	private void createScenery() {
		// load le death star
		deathstar = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "deathstar.3ds", 100);
		deathstar.setCollisionMode(Object3D.COLLISION_CHECK_NONE);
		// the deathstar will have some base illumination
		deathstar.setAdditionalColor(new Color(0, 0, 0));

		// enable specular lighthing
		deathstar.setSpecularLighting(true);

		// prepare and add to world
		deathstar.build();
		// centre rotation pivot so that the deathstar rotates around itself
		SimpleVector null_vector = new SimpleVector(0, 0, 0);
		deathstar.setRotationPivot(null_vector);
		deathstar.compile();
		deathstar.strip();
		world.addObject(deathstar);

		// this is all to scale, center and rotate my sweet deathstar, just the way I like it
		deathstar.rotateX(convertDegreesToRadians(129.3f));
		deathstar.rotateY(convertDegreesToRadians(-35.2f));
		deathstar.rotateZ(convertDegreesToRadians(118.6f));
		SimpleVector position = new SimpleVector(11.5809, -52.6263, -29.4106);
		deathstar.translate(position);
		this.deathstar_rotation_vector = new SimpleVector(1, -1, -1);
		this.deathstar_rotation_vector.rotateX(convertDegreesToRadians(129.3f));
		this.deathstar_rotation_vector.rotateY(convertDegreesToRadians(-35.2f));
		this.deathstar_rotation_vector.rotateZ(convertDegreesToRadians(118.6f));

		bg1 = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "bg1_stars.3ds", 1);
		bg1.setCollisionMode(Object3D.COLLISION_CHECK_NONE);
		bg1.setAdditionalColor(new Color(255, 255, 255));
		bg1.setLighting(Object3D.LIGHTING_NO_LIGHTS);
		bg1.setSpecularLighting(false);
		bg1.setTransparency(1);
		bg1.setTransparencyMode(Object3D.TRANSPARENCY_MODE_ADD);
		bg1.build();
		bg1.setRotationPivot(new SimpleVector(0, 0, 0));
		bg1.compile();
		bg1.strip();
		bg1.translate(0, 0, -50);
		world.addObject(bg1);

		Object3D bg2 = loadModel(EngineConfig.MODELS_DIRECTORY + File.separatorChar + "bg2_blue_cloud.3ds", 2);
		bg2.setCollisionMode(Object3D.COLLISION_CHECK_NONE);
		bg2.setAdditionalColor(new Color(255, 255, 255));
		bg2.setLighting(Object3D.LIGHTING_NO_LIGHTS);
		bg2.setSpecularLighting(false);
		bg2.build();
		bg2.setRotationPivot(new SimpleVector(0, 0, 0));
		bg2.compile();
		bg2.strip();
		bg2.translate(0, 0, -350);
		world.addObject(bg2);
	}

	/**
	 * Devolve o nivel associado a este cenario.
	 * 
	 * @return
	 */
	private Nivel getNivel() {
		return this.game_level;
	}

	/**
	 * Devolve a Peca associada a um ID
	 * 
	 * @param id
	 * @return
	 */
	private Peca getPeca(int id) {
		synchronized (pieces) {
			for (Peca p : this.pieces) {
				if (p.getID() == id)
					return p;
			}
		}
		return null;
	}

	/**
	 * Returns how much time remains to play
	 * 
	 * @return
	 */
	public double getRemainingTime() {
		double remaining_time = this.time_to_play - ticker.getElapsedTime();
		return remaining_time;
	}

	/**
	 * Carrega um objecto3d de um ficheiro 3DS com uma determinada escala
	 * 
	 * @param filename
	 * @param scale
	 * @return
	 */
	private Object3D loadModel(String filename, float scale) {
		Object3D[] model = Loader.load3DS(filename, scale);
		// return model[0];
		Object3D o3d = new Object3D(0);
		for (int i = 0; i < model.length; i++) {
			o3d = Object3D.mergeObjects(o3d, model[i]);
		}
		return o3d;
	}

	/**
	 * Loads all the images in the texture directory to memory. Must be called before loading the models.
	 */
	private void loadTextures() {
		// load all images in texture directory to RAM
		// get all files in directory
		File[] listFiles = new File(EngineConfig.TEXTURES_DIRECTORY).listFiles();
		TextureManager tm = TextureManager.getInstance();
		for (File texturefile : listFiles) {
			String textfilename = texturefile.getName();
			// only load files which correspond to images
			String lowerCase = textfilename.toLowerCase();
			if (lowerCase.endsWith(".bmp") || lowerCase.endsWith(".jpg") || lowerCase.endsWith(".png") || lowerCase.endsWith(".gif")) {
				// System.out.println("loaded texture " + textfilename);
				Texture texture = new Texture(EngineConfig.TEXTURES_DIRECTORY + File.separatorChar + textfilename, true);
				tm.addTexture(textfilename, texture);
			}
		}
	}

	/**
	 * Executa o jogo, em cada iteracao desenha o cenario, verifica os controlos e avalia a logica do jogo
	 * 
	 * @param height
	 * @param width
	 * 
	 */
	public void loop(int width, int height) {
		FrameBuffer buffer = new FrameBuffer(width, height, FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY);

		// OpenGL rulez, software here will be sluggish, CPU is needed elsewhere
		buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);

		Display.setTitle("Bejeweled");

		// esperar que janela seja criada, so' com polling, o jpct nao tem eventos
		// tem de ser feito senao estoira ao criar o objecto Mouse para detectar cliques na janela (o rato tem de estar associado a uma janela)
		while (!buffer.isInitialized()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		doLoop = true;

		ticker.resetTicker();
		ticker.getTimeDeltaLastCall();
		this.remainingTimeTicker.resetTicker();

		double fps = 0;
		pieces_angle = 0;
		int fpscounter = 0;
		int font_size = 30 * (width * height) / (1024 * 768);
		Font font1 = new Font("Arial", Font.PLAIN, font_size);
		GLFont glfont = new GLFont(font1);
		MouseMapper mousemapper = new MouseMapper(buffer);

		while (doLoop) {

			// if someone clicked the close button X, end this
			if (org.lwjgl.opengl.Display.isCloseRequested()) {
				doLoop = false;
			}

			// time difference since last call
			double timeDeltaLastCall = ticker.getTimeDeltaLastCall();

			// check input (mouse and keyboard)
			pollControls(timeDeltaLastCall, mousemapper, buffer);

			// handle pieces
			setupGridPieces(timeDeltaLastCall);

			// handle non interactive objects (scenery)

			deathstar.rotateAxis(deathstar_rotation_vector, (float) (timeDeltaLastCall * EngineConfig.DEATHSTAR_ROTATING_SPEED));
			bg1.translate((float) (timeDeltaLastCall * 0.633), (float) (timeDeltaLastCall * 0.217), 0);
			buffer.clear(Color.BLACK);
			world.renderScene(buffer);
			world.draw(buffer);

			// write some strings to the screen
			int score = this.getNivel().getScoreJogador().getScore();
			int level = this.getNivel().getScoreJogador().getLevel();
			int x, y;
			x = (int) (0.007 * (double) width);
			y = (int) (0.040 * (double) height);
			glfont.blitString(buffer, "FPS: " + Integer.toString((int) fps), x, y, 0, Color.YELLOW);

			x = (int) (0.007 * (double) width);
			y = (int) (0.990 * (double) height);
			glfont.blitString(buffer, "SCORE: " + Integer.toString(score), x, y, 0, Color.RED);

			x = (int) (0.850 * (double) width);
			y = (int) (0.990 * (double) height);
			glfont.blitString(buffer, "LEVEL: " + Integer.toString(level), x, y, 0, Color.GREEN);

			// if using timer, show remaining time and check if the time limit has been hit
			if (this.using_timer) {
				double remaining_time = getRemainingTime();

				if (remaining_time <= 0) {
					Speed speed_level = (Speed) this.game_level;
					speed_level.checkTime();
				}
				// show time on screen
				String time_s = Integer.toString((int) remaining_time);
				x = (int) (0.850 * (double) width);
				y = (int) (0.040 * (double) height);
				glfont.blitString(buffer, "TIME: " + time_s, x, y, 0, Color.WHITE);
			}

			// update image on monitor
			buffer.update();
			buffer.displayGLOnly();

			// handle fps counter
			double et = fps_ticker.getElapsedTime();
			if (et > (1.0 / 2.0)) {
				fps = fpscounter / et;
				fpscounter = 0;
				fps_ticker.resetTicker();
			}
			fpscounter++;
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
	}

	/**
	 * Verifica o estado do teclado+rato e gere o que cada deve fazer.
	 * 
	 * @param timeDeltaLastCall
	 * @param mousemapper
	 * @param buffer
	 */
	private void pollControls(double timeDeltaLastCall, MouseMapper mousemapper, FrameBuffer buffer) {

		if (mousemapper.isButtonDown(0)) {
			if (button_down_laststate == false) {
				button_down_laststate = true;
				int x = mousemapper.getMouseX();
				int y = mousemapper.getMouseY();
				// System.out.println("button clicked at " + x + "," + y);

				processClickedPiece(x, y, buffer);
			}
		} else {
			button_down_laststate = false;
		}

		// QUIT
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			doLoop = false;
		}

		// MOVING SPEED
		float speed_modifier = EngineConfig.CAMERA_SPEED_MOFIDIER;
		float rotating_speed = EngineConfig.CAMERA_ROTATING_SPEED;
		float speedmodifier_pos = (float) (+speed_modifier * timeDeltaLastCall);
		float speedmodifier_neg = (float) (-speed_modifier * timeDeltaLastCall);
		float rotating_speed_neg = (float) (-rotating_speed * timeDeltaLastCall);
		float rotating_speed_pos = (float) (+rotating_speed * timeDeltaLastCall);

		// FASTER WITH SHIFT PRESSED
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speedmodifier_pos *= 10;
			speedmodifier_neg *= 10;
			rotating_speed_neg *= 4;
			rotating_speed_pos *= 4;
		}

		// CAMERA TRANSLATIONS
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.moveCamera(camera.getDirection(), speedmodifier_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.moveCamera(camera.getDirection(), speedmodifier_neg);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.moveCamera(camera.getSideVector(), speedmodifier_neg);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.moveCamera(camera.getSideVector(), speedmodifier_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			camera.moveCamera(camera.getUpVector(), speedmodifier_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			camera.moveCamera(camera.getUpVector(), speedmodifier_neg);
		}

		// CAMERA ROTATIONS
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			camera.rotateCameraAxis(camera.getSideVector(), rotating_speed_neg);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			camera.rotateCameraAxis(camera.getSideVector(), rotating_speed_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			camera.rotateCameraAxis(camera.getUpVector(), rotating_speed_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			camera.rotateCameraAxis(camera.getUpVector(), rotating_speed_neg);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			camera.rotateCameraAxis(camera.getDirection(), rotating_speed_pos);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			camera.rotateCameraAxis(camera.getDirection(), rotating_speed_neg);
		}

		// CAMERA RESET
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			resetCamera();
		}

		// CAMERA DEBUG
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			System.out.println("camera position at " + camera.getPosition());
		}

	}

	/**
	 * Recebe as coordenadas do clique no ecra e manipula a configuracao das pecas.
	 * 
	 * @param x
	 * @param y
	 * @param buffer
	 */
	private void processClickedPiece(int x, int y, FrameBuffer buffer) {

		// obter objecto3d em que foi clicado
		Camera cam = world.getCamera();
		SimpleVector dir = Interact2D.reproject2D3DWS(cam, buffer, x, y).normalize();
		Object[] res = world.calcMinDistanceAndObject3D(cam.getPosition(), dir, 10000);
		Object3D picked = (Object3D) res[1];

		// se algum objecto foi clicado...
		if (picked != null) {
			// obter o ID unico para o objecto
			int picked_id = picked.getID();

			// obter a peca associada ao ID
			Peca p = this.getPeca(picked_id);
			// System.out.println("button clicked at " + x + "," + y + " Peca:" + p);

			// actualizar no jogo o estado da peca
			if (meh != null) {
				// already sent an click event, wait for current
				try {
					meh.join();
				} catch (InterruptedException e) {
				}
			}
			// create another thread to handle the click event
			meh = new MouseEventHandler(this.getNivel(), p);
			meh.start();

			// meh.s
			// this.getNivel().inputPecas(p);

			// reinicar a animacao do pulsar
			pulsating_ticker.resetTicker();
		} else {
			// System.out.println("button clicked at " + x + "," + y);
		}
	}

	/**
	 * Used to remove pieces from the game. The given pieces move outside the screen and are removed from the scenery.
	 * 
	 * @param pieces
	 */
	public void removePecas(ArrayList<Peca> pieces) {
		this.pieces_ids_pieces_being_removed.addAll(pieces);
	}

	/**
	 * Posiciona e orienta a camera na configuracao por defeito.
	 */
	private void resetCamera() {
		camera.setPosition(0, 0, 25);
		SimpleVector dir = new SimpleVector(0, 0, -1);
		SimpleVector up = new SimpleVector(0, 1, 0);
		camera.setOrientation(dir, up);
	}

	/**
	 * Resets the speed timer to zero and starts counting again from the given time.
	 * 
	 * @param new_remaining_time
	 */
	public void resetRemainingTime(double new_remaining_time) {
		this.time_to_play = new_remaining_time;
		ticker.resetTicker();
	}

	/**
	 * Posiciona as pecas no cenario e atribui-lhes as propriedades actuais. Executado em cada iteracao do motor grafico (render).
	 * 
	 * @param timeDeltaLastCall
	 */
	private void setupGridPieces(double timeDeltaLastCall) {

		HashSet<Peca> pieces_to_remove = new HashSet<>();
		float angle_delta = (float) (EngineConfig.PIECES_ROTATING_SPEED * timeDeltaLastCall);
		pieces_angle += angle_delta;

		/**
		 * iterar todas as pecas do cenario
		 */
		synchronized (pieces) {

			for (Peca peca : pieces) {

				Object3D o3d = peca.getObjecto3D();

				// posicionar o cubo
				float x = peca.getCoorX();
				float y = peca.getCoorY();
				float z = peca.getCoorZ();

				// se a peca estiver assinalada que esta a cair, ela que caia.. para baixo :P
				if (peca.isMoving()) {

					SimpleVector current_position = new SimpleVector(x, y, z);
					float destCoorX = peca.getDestCoorX();
					float destCoorY = peca.getDestCoorY();
					SimpleVector destination_position = new SimpleVector(destCoorX, destCoorY, z);
					float distance = current_position.distance(destination_position);
					SimpleVector translation_vector = destination_position.calcSub(current_position);
					translation_vector.scalarMul(EngineConfig.PIECES_MOVING_SPEED);
					x += translation_vector.x;
					y += translation_vector.y;
					z += translation_vector.z;
					if (distance < EngineConfig.PIECES_MOVING_DISTANCE_TOLERANCE) {
						peca.setMoving(false);
					}
				}

				// se a peca tiver sido removida, morreu... como tal e' "transladada"
				if (pieces_ids_pieces_being_removed.contains(peca)) {
					if (z < 40) {
						z += 12.0 * timeDeltaLastCall;
						peca.setCoorZ(z);
						o3d.clearRotation();
					} else {
						pieces_ids_pieces_being_removed.remove(peca);
						pieces_to_remove.add(peca);
					}
				} else {
					// rodar as pecas
					o3d.clearRotation();
					o3d.rotateY(pieces_angle);
				}

				if (peca.DEBUG) {
					o3d.rotateX((float) (5.33 * timeDeltaLastCall));
					o3d.rotateZ((float) (4.71 * timeDeltaLastCall));
				}

				o3d.clearTranslation();
				// reposicionar de acordo com os atributos da peca
				o3d.translate(x, y, z);
				peca.setCoorX(x);
				peca.setCoorY(y);
				peca.setCoorZ(z);

				// se a peca estiver seleccionada, pulsa-a
				Peca selected_piece = this.getNivel().getLastSelection();
				if (selected_piece != null && selected_piece.equals(peca)) {
					// funcao toto para ter o pulsating a variar entre um min e um max
					float scale = EngineConfig.PIECES_PULSATING_SCALE_MAX - EngineConfig.PIECES_PULSATING_SCALE_MIN;
					double f = ((Math.sin(pulsating_ticker.getElapsedTime() * EngineConfig.PIECES_PULSATING_SPEED) + 1) / 2) * scale
							+ EngineConfig.PIECES_PULSATING_SCALE_MIN;
					o3d.setScale((float) f);
				} else {
					o3d.setScale(1.0f);
				}
			}
		}
		// remove from pieces the ones which have to be removed
		if (!pieces_to_remove.isEmpty()) {
			synchronized (pieces) {
				for (Peca piece_to_remove : pieces_to_remove) {
					pieces.remove(piece_to_remove);
					world.removeObject(piece_to_remove.getObjecto3D());
				}
			}
		}
	}

	/**
	 * Tells the 3D engine to stop and closes its window.
	 */
	public void terminate() {
		this.doLoop = false;
	}

}
