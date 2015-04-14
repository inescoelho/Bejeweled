/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */

package Game;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import Game.Files.FicheiroDeObjetos;
import Game.GUI.MenuGUI;
import Game.GUI.NewHsGUI;

public class Bejeweled {
	/**
	 * Localizacao do ficheiro com as pontuacoes do jogo versao Classico
	 */
	private static String hsFileClassico = "Resources//Ficheiros//highscoresClassico.obj";
	/**
	 * Localizacao do ficheiro com as pontuacoes do jogo versao Velocidade
	 */
	private static String hsFileSpeed = "Resources//Ficheiros//highscoresSpeed.obj";
	/**
	 * Array que armazena o valor das melhores pontuacoes e o nome do jogador do jogo versao Classico
	 */
	private ArrayList<HighScore> pontuacoesClassico;
	/**
	 * Array que armazena o valor das melhores pontuacoes e o nome do jogador do jogo versao Velocidade
	 */
	private ArrayList<HighScore> pontuacoesSpeed;
	/**
	 * Dimensao (numero de elementos em cada linha) da grelha de jogo
	 */
	private int dimension;
	/**
	 * Largura do ecra de jogo
	 */
	private int width;
	/**
	 * Comprimento do ecra de jogo
	 */
	private int height;
	/**
	 * Frame do menu de jogo
	 */
	private JFrame frame;
	/**
	 * Contentor da frame do menu de jogo
	 */
	private Container contentor;
	/**
	 * Controlador de som.
	 */
	public static Sound som;
	/**
	 * Tipo de animacao 3D (true) boxes (false) spaceships
	 */
	public boolean simpleModels;

	public static void main(String[] args) {
		new Bejeweled();
	}

	// CONSTRUTOR
	/**
	 * Inicia o jogo com dimensao de grelha predefinida a 8 e carrega para a memoria as pontuacoes guardadas em ficheiro
	 */
	public Bejeweled() {
		this.dimension = 8;
		this.pontuacoesClassico = new ArrayList<HighScore>();
		this.pontuacoesSpeed = new ArrayList<HighScore>();
		this.width = 1024;
		this.height = 768;
		this.simpleModels = false;

		/**
		 * Inicia o som
		 */
		try {
			Bejeweled.som = new Sound();
		} catch (Exception e) {
		}

		/**
		 * Carrega para o array as pontuacoes do jogo tipo Classico em ficheiro ou, caso nao exista, cria pontuacoes por defeito
		 */
		try {
			loadHighScoresFile(hsFileClassico, this.getPontuacoesClassico());
		} catch (Exception e) {
			for (int player = 0; player < 10; player++)
				this.getPontuacoesClassico().add(new HighScore("Player", (10 - player) * 1000));
		}

		/**
		 * Carrega para o array as pontuacoes do jogo tipo Velocidade em ficheiro ou, caso nao exista, cria pontuacoes por defeito
		 */
		try {
			loadHighScoresFile(hsFileSpeed, this.getPontuacoesSpeed());
		} catch (Exception e) {
			for (int player = 0; player < 10; player++)
				this.getPontuacoesSpeed().add(new HighScore("Player", (10 - player) * 1000));
		}

		/**
		 * Inicializa a frame do jogo
		 */
		frame = this.configuraFrame();
		contentor = this.frame.getContentPane();

		contentor.add(new MenuGUI(this));

		frame.setVisible(true);
	}

	// METODOS - Getters and Setters
	public ArrayList<HighScore> getPontuacoesClassico() {
		return pontuacoesClassico;
	}

	public void setPontuacoesClassico(ArrayList<HighScore> pontuacoesClassico) {
		this.pontuacoesClassico = pontuacoesClassico;
	}

	public ArrayList<HighScore> getPontuacoesSpeed() {
		return pontuacoesSpeed;
	}

	public void setPontuacoesSpeed(ArrayList<HighScore> pontuacoesSpeed) {
		this.pontuacoesSpeed = pontuacoesSpeed;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Container getContentor() {
		return contentor;
	}

	public void setContentor(Container contentor) {
		this.contentor = contentor;
	}

	public boolean isSimpleModels() {
		return simpleModels;
	}

	// public void setSimpleModels(boolean simpleModels) {
	// Bejeweled.simpleModels = simpleModels;
	// }

	// METODOS

	/**
	 * Configura a janela do jogo
	 */
	public JFrame configuraFrame() {
		JFrame fr = new JFrame();
		fr.setSize(800, 600);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.getContentPane().setBackground(Color.black);
		fr.setLocationRelativeTo(null); // cria a janela centrada
		fr.setIconImage(new ImageIcon("Resources/Icons/icon.png").getImage());
		fr.setVisible(true);
		fr.setResizable(false);
		return fr;
	}

	/**
	 * Le os HighScores guardados em ficheiro e carrega-os para um array
	 */
	public void loadHighScoresFile(String nome, ArrayList<HighScore> array) throws Exception {
		FicheiroDeObjetos obj = new FicheiroDeObjetos();
		Object aux;
		HighScore aux_hs;

		obj.abreLeitura(nome);

		for (int player = 0; player < 10; player++) {
			aux = obj.leObjecto();
			aux_hs = (HighScore) aux;

			array.add(aux_hs);
		}

		obj.fechaLeitura();
	}

	/**
	 * Guarda os HighScores do array em ficheiro
	 */
	public void saveHighScores(String nome, ArrayList<HighScore> array) {
		FicheiroDeObjetos obj = new FicheiroDeObjetos();

		try {
			obj.abreEscrita(nome);
			for (int player = 0; player < 10; player++)
				obj.escreveObjecto(array.get(player));

			obj.fechaEscrita();
		}

		catch (IOException e) {
			System.out.print("Erro a guardar ficheiro highsores.obj");
			return;
		}
	}

	/**
	 * Inicia o jogo
	 * 
	 * @param gameType
	 *            modalidade de jogo (classico vs speed)
	 * @param testType
	 *            tipo (jogo vs teste)
	 */
	public void startGame(boolean gameType, boolean testType) {
		Game newGame;

		// comeca um novo jogo
		if (testType)
			newGame = new Game(this, gameType);
		else
			newGame = new Game(this, gameType, this.getDimension());

		newGame.startGame(testType);

		return;
	}

	/**
	 * Verifica se a pontuacao obtida no jogo deve constar da lista de HighScores
	 * 
	 * @param tipo
	 *            tipo de jogo (false) classico (true) speed
	 * @param pont
	 *            pontuacao obtida no jogo
	 */
	public void verifyHighScore(boolean tipo, int pont) {
		String nome;
		ArrayList<HighScore> array;
		boolean newHS = false;

		if (!tipo) {
			nome = hsFileClassico;
			array = this.getPontuacoesClassico();
		} else {
			nome = hsFileSpeed;
			array = this.getPontuacoesSpeed();
		}

		for (int player = 0; player < 10; player++)
			if (array.get(player).getPont() < pont) {
				array.add(player, this.addNewScore(player+1, pont));
				array.remove(10);
				this.saveHighScores(nome, array);
				newHS = true;
				break;
			}

		/**
		 * Se a pontuacao nao for um HighScore, retorna ao menu principal
		 */
		if (!newHS) {
			this.getContentor().removeAll();
			this.getContentor().add(new MenuGUI(this));
			this.getFrame().setVisible(true);
		}

		return;
	}

	/**
	 * Cria um novo HighScore, pedindo ao utilizador para insreir o seu nome
	 * 
	 * @param lugar
	 *            posicao em que o jogador ficou
	 * @param pont
	 *            pontuacao obtida pelo jogador
	 * @return novo highscore
	 */
	HighScore addNewScore(int lugar, int pont) {
		HighScore newHS = new HighScore("", pont);
		this.getContentor().removeAll();
		this.getContentor().add(new NewHsGUI(this, lugar, newHS));
		this.getFrame().setVisible(true);
		return newHS;
	}

}