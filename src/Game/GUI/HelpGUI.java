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
public class HelpGUI extends JPanel {
	/**
	 * Icon com o nome do menu
	 */
	private JLabel label;
	/**
	 * Logotipo principal
	 */
	private JLabel Logo;
	/**
	 * Texto
	 */
	private JLabel jLabel1;
	/**
	 * Texto
	 */
	private JLabel jLabel2;
	/**
	 * Texto
	 */
	private JLabel lblVelocidade;
	/**
	 * Volta ao menu Principal
	 */
	private JButton End;
	/**
	 * Menu principal
	 */
	MenuGUI menuPrincipal;
	/**
	 * Jogo
	 */
	Bejeweled game;
	private JLabel lblNewLabel_1;
	private JLabel lblAzulElimina;
	private JLabel lblVermelhoElimina;
	private JLabel lblVerdeElimina;
	
	/**
	 * Inicializa os componentes do menu
	 */
	public HelpGUI(Bejeweled bj, MenuGUI menu) {
		this.game = bj;
		this.menuPrincipal = menu;
		
		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		//TITLE
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("Resources/Icons/TxtAjuda.png"));
		label.setBounds(372, 79, 56, 60);
		add(label);
		
		//LOGO
		Logo = new JLabel();
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);

		//START >>  jLabel1
		jLabel1 = new JLabel();
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setText("Cl\u00E1ssico - consiste em dez n\u00EDveis diferentes. Caso n\u00E3o hajam combina\u00E7\u00F5es poss\u00EDveis, o jogo acaba.");
		jLabel1.setBounds(125, 230, 565, 16);
		this.add(jLabel1);
		
		//START >>  jLabel2
		jLabel2 = new JLabel();
		jLabel2.setForeground(new Color(255, 216, 0));
		jLabel2.setText("Existem dois modos de jogo:");
		jLabel2.setBounds(125, 210, 243, 16);
		this.add(jLabel2);
		
		//START >> Texto
		lblVelocidade = new JLabel("Velocidade - dez n\u00EDveis com tempo limite. Um novo tabuleiro \u00E9 criado em caso de impossibilidade.");
		lblVelocidade.setBounds(125, 250, 568, 16);
		lblVelocidade.setForeground(new Color(255, 216, 0));
		add(lblVelocidade);
		
		JLabel lblNewLabel = new JLabel("O objetivo do jogo \u00E9 fazer conjuntos de tr\u00EAs tipos de pe\u00E7as diferentes em todas as jogadas. ");
		lblNewLabel.setBounds(125, 170, 545, 28);
		lblNewLabel.setForeground(new Color(255, 216, 0));
		add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Power-Ups: pe\u00E7as especiais com efeitos ben\u00E9ficos. Existem tr\u00EAs tipos diferentes.");
		lblNewLabel_1.setBounds(125, 280, 500, 28);
		lblNewLabel_1.setForeground(new Color(255, 216, 0));
		add(lblNewLabel_1);
		
		lblAzulElimina = new JLabel("Azul - elimina a linha horizontal onde se situa.");
		lblAzulElimina.setForeground(new Color(255, 216, 0));
		lblAzulElimina.setBounds(125, 300, 500, 28);
		add(lblAzulElimina);
		
		lblVermelhoElimina = new JLabel("\r\nVermelho - elimina todas as pe\u00E7as iguais \u00E0 do power-up.");
		lblVermelhoElimina.setForeground(new Color(255, 216, 0));
		lblVermelhoElimina.setBounds(125, 320, 500, 28);
		add(lblVermelhoElimina);
		
		lblVerdeElimina = new JLabel("\r\nVerde - elimina a linha e a coluna inteiras \u00E0 qual o power-up pertence.");
		lblVerdeElimina.setForeground(new Color(255, 216, 0));
		lblVerdeElimina.setBounds(125, 340, 500, 28);
		add(lblVerdeElimina);
		
		//START >>  End
		End = new JButton();
		End.setBackground(new Color(255, 216, 0));
		End.setText("OK");
		End.setBounds(325, 500, 150, 23);
		this.add(End);
		End.addActionListener(new ActionListener() {
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
