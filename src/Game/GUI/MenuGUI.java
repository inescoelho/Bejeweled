/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */ 

package Game.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.Bejeweled;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/

@SuppressWarnings("serial")
public class MenuGUI extends JPanel {
	private JButton NewGame;
	private JButton Teste;
	private JButton Help;
	private JButton HighScores;
	private JButton Home;
	private JButton Definitions;
	private JButton About;
	private JLabel Logo;
	Bejeweled game;

	/**
	 * Inicializa os componentes do menu principal
	 */
	public MenuGUI (Bejeweled bj){
		game = bj;
		
		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		//TITLE	
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("Resources/Icons/TxtMenu.png"));
		label.setBounds(328, 79, 150, 60);
		add(label);
		
		//LOGO
		Logo = new JLabel();
		Logo.setBackground(new Color(255, 216, 0));
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);
		
		//START >>  NewGame
		NewGame = new JButton();
		NewGame.setBackground(new Color(255, 216, 0));
		NewGame.setText("Novo Jogo");
		NewGame.setBounds(325, 200, 150, 23);
		this.add(NewGame);
		NewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NewGameActionPerformed(evt);
			}
		});
		
		//START >>  Teste
		Teste = new JButton();
		Teste.setBackground(new Color(255, 216, 0));
		Teste.setText("Teste");
		Teste.setBounds(325, 250, 150, 23);
		this.add(Teste);
		Teste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TesteActionPerformed(evt);
			}
		});		
		
		//START >>  Help
		Help = new JButton();
		Help.setBackground(new Color(255, 216, 0));
		Help.setText("Ajuda");
		Help.setBounds(325, 300, 150, 23);
		this.add(Help);
		Help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HelpActionPerformed(evt);
			}
		});
		
		//START >>  HighScores
		HighScores = new JButton();
		HighScores.setBackground(new Color(255, 216, 0));
		HighScores.setText("Pontuações");
		HighScores.setBounds(325, 350, 150, 23);
		this.add(HighScores);
		HighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HighScoresActionPerformed(evt);
			}
		});
		
		//START >>  Definitions
		Definitions = new JButton();
		Definitions.setBackground(new Color(255, 216, 0));
		Definitions.setText("Definições");
		Definitions.setBounds(325, 400, 150, 23);
		this.add(Definitions);
		Definitions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DefinitionsActionPerformed(evt);
			}
		});
		
		//START >>  About
		About = new JButton();
		About.setBackground(new Color(255, 216, 0));
		About.setText("Sobre");
		About.setBounds(325, 450, 150, 23);
		this.add(About);
		About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AboutActionPerformed(evt);
			}
		});
		
		//START >>  Home
		Home = new JButton();
		Home.setBackground(new Color(255, 216, 0));
		Home.setText("Sair");
		Home.setBounds(325, 500, 150, 23);
		this.add(Home);
		Home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HomeActionPerformed(evt);
			}
		});
		
	}
	
	public Bejeweled getGame() {
		return game;
	}

	public void setGame(Bejeweled game) {
		this.game = game;
	}

	private void NewGameActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new NewGameGUI(this.game, this));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void TesteActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new TesteGUI(this.game, this));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void HelpActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new HelpGUI(this.game, this));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void HighScoresActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new HighScoresGUI(this.game, this));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void DefinitionsActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new DefinitionGUI(this.game, this));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void AboutActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new AboutGUI(this.game, this));
//		this.getGame().getContentor().add(new NewHsGUI(this.game, 3, 1000));
		this.getGame().getFrame().setVisible(true);
	}
	
	private void HomeActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
}
