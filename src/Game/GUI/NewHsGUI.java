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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Game.Bejeweled;
import Game.HighScore;

public class NewHsGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JTextField nome;
	private JLabel nomeTxt;
	private JButton End;
	private JLabel BackGround;
	private HighScore Score;
	Bejeweled game;

	public NewHsGUI(Bejeweled bj, int lugar, HighScore hs) {
		this.game = bj;
		this.Score = hs;

		bj.getContentor().removeAll();
		this.setLayout(null);

		// START >> Titulo
		jLabel1 = new JLabel("Novo Record!", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 255, 255));
		jLabel1.setBounds(0, 150, 800, 40);
		jLabel1.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel1);

		// START >>Pontuacao
		jLabel2 = new JLabel("Posicao: "+ lugar + "       Pontuacao: "+ this.getScore().getPont(), SwingConstants.CENTER);
		jLabel2.setForeground(new Color(255, 255, 255));
		jLabel2.setBounds(0, 250, 800, 40);
		jLabel2.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel2);
		
		// START >> Texto Inserir Nome
		nomeTxt = new JLabel("Insira Nome:", SwingConstants.CENTER);
		nomeTxt.setForeground(new Color(255, 255, 255));
		nomeTxt.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		nomeTxt.setBounds(200, 320, 150, 16);
		this.add(nomeTxt);

		// START >> Novo Nome
		nome = new JTextField();
		nome.setBounds(350, 320, 200, 23);
		this.add(nome);

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
		BackGround
		.setIcon(new ImageIcon("Resources/Background/NewHS.jpg"));
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

	public HighScore getScore() {
		return Score;
	}

	public void setScore(HighScore score) {
		Score = score;
	}

	/**
	 * Volta ao menu Principal
	 */
	private void EndActionPerformed(ActionEvent evt) {
		String input;
		
		try {
			input = this.nome.getText();
		} catch (Exception e) {
			input = "Player";
		}
		
		this.getScore().setNome(input);
		
		this.removeAll();
		this.getGame().getContentor().add(new MenuGUI(this.getGame()));
		this.getGame().getFrame().setVisible(true);
	}
}
