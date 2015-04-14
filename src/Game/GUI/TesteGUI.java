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
public class TesteGUI extends JPanel {
	/**
	 * Icon com o nome do menu
	 */
	private JLabel label;
	/**
	 * Logotipo principal
	 */
	private JLabel Logo;
	/**
	 * Comeca Jogo Modalidade Classica
	 */
	private JButton JogoClassico;
	/**
	 * Comeca Jogo Modalidade Velocidade
	 */
	private JButton JogoVelocidade;	
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
	public TesteGUI(Bejeweled bj, MenuGUI menu) {
		this.game = bj;
		this.menuPrincipal = menu;
		
		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		//TITULO
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("Resources/Icons/TxtTeste.png"));
		label.setBounds(375, 79, 49, 60);
		this.add(label);
		
		//LOGO
		Logo = new JLabel();
		Logo.setForeground(Color.BLACK);
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);

		//START >>  JogoClassico
		JogoClassico = new JButton();
		JogoClassico.setBackground(new Color(255, 216, 0));
		JogoClassico.setText("Classico");
		JogoClassico.setBounds(192, 300, 150, 23);
		this.add(JogoClassico);
		JogoClassico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JogoClassicoActionPerformed(evt);
			}
		});

		//START >>  JogoVelocidade
		JogoVelocidade = new JButton();
		JogoVelocidade.setBackground(new Color(255, 216, 0));
		JogoVelocidade.setText("Velocidade");
		JogoVelocidade.setBounds(458, 300, 150, 23);
		this.add(JogoVelocidade);
		JogoVelocidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JogoVelocidadeActionPerformed(evt);
			}
		});
		
		//START >>  EndButton
		EndButton = new JButton();
		EndButton.setBackground(new Color(255, 216, 0));
		EndButton.setText("Voltar");
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
	 * Comeca Jogo Classico
	 */
	private void JogoClassicoActionPerformed(ActionEvent evt) {
		this.getGame().startGame(false, true);
	}
	
	/**
	 * Comeca Jogo Velocidade
	 */
	private void JogoVelocidadeActionPerformed(ActionEvent evt) {
		this.getGame().startGame(true, true);
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