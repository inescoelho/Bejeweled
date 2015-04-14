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
import javax.swing.SwingConstants;

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
public class HighScoresGUI extends JPanel {
	/**
	 * Icon com o nome do menu
	 */
	private JLabel label;
	/**
	 * Logotipo principal
	 */
	private JLabel Logo;
	/**
	 * Titulo - Classico
	 */
	private JLabel Titulo1;
	/**
	 * Titulo - Velocidade
	 */
	private JLabel Titulo2;
	/**
	 * Texto - highscores modalidade Classico
	 */
	private JLabel TextoClassico;
	/**
	 * Texto - highscores modalidade Speed
	 */
	private JLabel TextoVelocidade;
	/**
	 * Volta ao menu Principal
	 */
	private JButton EndButton;
	/**
	 * Menu principal
	 */
	MenuGUI menuPrincipal;
	/**
	 * Jogo
	 */
	Bejeweled game;
	
	/**
	 * Inicializa os componentes do menu
	 */
	public HighScoresGUI(Bejeweled bj, MenuGUI menu) {
		this.game = bj;
		this.menuPrincipal = menu;
		
		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);
			
		//TITULO
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("Resources/Icons/TxtPontuacoes.png"));
		label.setBounds(342, 79, 115, 60);
		add(label);
		
		//LOGO
		Logo = new JLabel();
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);
				
		//Titulo dos HighScores - Classico
		Titulo1 = new JLabel("CLASSICO", SwingConstants.CENTER);
		Titulo1.setForeground(new Color(255, 216, 0));
		Titulo1.setBounds(200, 150, 200, 60);
		add(Titulo1);
		
		//Titulo dos HighScores - Velocidade
		Titulo2 = new JLabel("VELOCIDADE", SwingConstants.CENTER);
		Titulo2.setForeground(new Color(255, 216, 0));
		Titulo2.setBounds(400, 150, 200, 60);
		add(Titulo2);
		
		int posicao = 200;
		//START >>  Texto
		for (int player = 0; player < 10; player ++)
		{
			TextoClassico = new JLabel("", SwingConstants.CENTER);
			TextoClassico.setForeground(new Color(255, 216, 0));
			TextoClassico.setText((player+1) + " - " + this.getGame().getPontuacoesClassico().get(player).toString());
			TextoClassico.setBounds(200, posicao, 200, 16);
			posicao +=20;	
			this.add(TextoClassico);
		}
		
		posicao = 200;
		//START >>  Texto
		for (int player = 0; player < 10; player ++)
		{
			TextoVelocidade = new JLabel("", SwingConstants.CENTER);
			TextoVelocidade.setForeground(new Color(255, 216, 0));
			TextoVelocidade.setText((player+1) + " - " + this.getGame().getPontuacoesSpeed().get(player).toString());
			TextoVelocidade.setBounds(400, posicao, 200, 16);
			posicao +=20;	
			this.add(TextoVelocidade);
		}
			
		//START >>  EndButton
		EndButton = new JButton();
		EndButton.setBackground(new Color(255, 216, 0));
		EndButton.setText("OK");
		EndButton.setBounds(325, 500, 150, 23);
		this.add(EndButton);
		EndButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				EndActionPerformed(evt);
			}
		});
	}
	
	public Bejeweled getGame() {
		return game;
	}

	public void setGame(Bejeweled game) {
		this.game = game;
	}

	public MenuGUI getMenuPrincipal() {
		return menuPrincipal;
	}

	public void setMenuPrincipal(MenuGUI menuPrincipal) {
		this.menuPrincipal = menuPrincipal;
	}

	/**
	 * Volta ao menu Principal
	 */
	private void EndActionPerformed(ActionEvent evt) {
		this.removeAll();
		this.getGame().getContentor().add(new MenuGUI(this.getGame()));
		this.getGame().getFrame().setVisible(true);
	}

}
