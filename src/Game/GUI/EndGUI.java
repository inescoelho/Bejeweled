/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */

package Game.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Game.Bejeweled;
import Game.Score;
import Game.Speed;

public class EndGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton End;
	private JLabel BackGround;
	private int tipo;
	private Score Score;
	private boolean tipoClassic;
	private Speed gameLevel;
	/**
	 * Jogo
	 */
	Bejeweled game;

	public EndGUI(Bejeweled bj) {
		this.game = bj;
		this.tipo = 0;

		bj.getContentor().removeAll();
		this.setLayout(null);

		// START >> jLabel1
		jLabel1 = new JLabel("", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 255, 255));
		jLabel1.setBounds(0, 350, 800, 40);
		jLabel1.setText("O jogo n\u00E3o foi terminado.");
		jLabel1.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel1);

		// START >> jLabel2
		jLabel2 = new JLabel("", SwingConstants.CENTER);
		jLabel2.setForeground(new Color(255, 255, 255));
		jLabel2.setBounds(400, 250, 200, 40);
		jLabel2.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel2);

		// START >> jLabel3
		jLabel3 = new JLabel("", SwingConstants.CENTER);
		jLabel3.setForeground(new Color(255, 255, 255));
		jLabel3.setBounds(200, 250, 200, 40);
		jLabel3.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel3);

		// START >> End
		End = new JButton();
		End.setBackground(new Color(255, 255, 255));
		End.setText("OK");
		End.setBounds(325, 450, 150, 23);
		this.add(End);
		End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				EndActionPerformed(evt);
			}
		});

		// Background
		BackGround = new JLabel();
		BackGround.setIcon(new ImageIcon("Resources/Background/noGame.jpg"));
		BackGround.setBounds(0, 0, 800, 600);
		add(BackGround);

	}

	// METODOS - Getters and Setters
	public Bejeweled getGame() {
		return game;
	}

	public void setGame(Bejeweled game) {
		this.game = game;
	}

	public Speed getGameLevel() {
		return gameLevel;
	}

	public void setGameLevel(Speed gameLevel) {
		this.gameLevel = gameLevel;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Score getScore() {
		return Score;
	}

	public void setScore(Score score) {
		Score = score;
	}

	public boolean isTipoClassic() {
		return tipoClassic;
	}

	public void setTipoClassic(boolean tipoClassic) {
		this.tipoClassic = tipoClassic;
	}

	// METODOS
	public void Configure(int tipo, Score score) {
		String frase;
		this.setScore(score);

		jLabel1.setBounds(0, 200, 800, 40);

		switch (tipo) {
		case 1: // No More Moves
			BackGround.setIcon(new ImageIcon(
					"Resources/Background/NoMoreMoves.jpg"));
			jLabel1.setText("Não há mais movimentos possíveis.");
			jLabel2.setBounds(0, 250, 800, 40);
			jLabel2.setText("A preparar novo tabuleiro.");
			this.setTipo(1);
			break;
		case 2: // Game Lost
			frase = chooseRandomPhraseLOST();
			BackGround.setIcon(new ImageIcon(
					"Resources/Background/GameLost.jpg"));
			jLabel1.setText(frase);
			jLabel2.setText("NIVEL: " + score.getLevel());
			jLabel3.setText("PONTUACAO: " + score.getScore());
			this.setTipo(2);
			break;
		case 3: // Final
			frase = chooseRandomPhraseEND();
			BackGround.setIcon(new ImageIcon("Resources/Background/Final.jpg"));
			jLabel1.setText(frase);
			jLabel2.setText("NIVEL: " + score.getLevel());
			jLabel3.setText("PONTUACAO: " + score.getScore());
			this.setTipo(3);
			break;

		case 4: // Grelha de Teste
			frase = chooseRandomPhraseEND();
			BackGround.setIcon(new ImageIcon("Resources/Background/Teste.jpg"));
			jLabel1.setText(frase);
			jLabel2.setText("NIVEL: " + score.getLevel());
			jLabel3.setText("PONTUACAO: " + score.getScore());
			this.setTipo(4);
			break;
		}
	}

	private String chooseRandomPhraseLOST() {
		ArrayList<String> collection = new ArrayList<String>();
		int option;

		collection.add("I find your lack of skills disturbing!");
		collection.add("Hm, try again, you must!");
		collection.add("It was a trap.");
		collection.add("What a piece of junk!");
		collection.add("You were  the choosen one!");
		collection.add("I’ve got a bad feeling about this...");
		collection.add("Into the garbage chute, flyboy!");
		collection.add("You rebel scum.");
		collection.add("Fear is the path to the dark side.");
		collection.add("Ahh, hard to see, the Dark Side is.");

		option = (int) (Math.random() * collection.size());

		return collection.get(option);
	}

	private String chooseRandomPhraseEND() {
		ArrayList<String> collection = new ArrayList<String>();
		int option;

		collection.add("The Dark Side of the Force is the pathway to many.");
		collection.add("The Force is strong within you.");
		collection.add("Keep using the force.");
		collection.add("May the Force be with you.");

		option = (int) (Math.random() * collection.size());

		return collection.get(option);
	}

	/**
	 * 
	 */
	private void EndActionPerformed(ActionEvent evt) {
		switch (this.getTipo()) {
		case 0:
			this.removeAll();
			this.getGame().getContentor().add(new MenuGUI(this.getGame()));
			this.getGame().getFrame().setVisible(true);
			break;
		case 1:
			this.getGameLevel().startNewGrid();
			break;
		case 2:
			this.getGame().verifyHighScore(this.isTipoClassic(),
					this.getScore().getScore());
			break;
		case 3:
			this.getGame().verifyHighScore(this.isTipoClassic(),
					this.getScore().getScore());
			break;
		case 4:
			this.removeAll();
			this.getGame().getContentor().add(new MenuGUI(this.getGame()));
			this.getGame().getFrame().setVisible(true);
			break;
		}

		return;
	}

}
