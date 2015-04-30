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
import javax.swing.SwingConstants;

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
public class AboutGUI extends JPanel {
	/**
	 * Icon com o nome do menu
	 */
	private final JLabel label;

	/**
	 * Logotipo principal
	 */
	private final JLabel Logo;
	/**
	 * Texto
	 */
	private JLabel jLabel1;
	/**
	 * Volta ao menu Principal
	 */
	private final JButton End;
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
	public AboutGUI(Bejeweled bj, MenuGUI menu) {
		this.game = bj;
		this.menuPrincipal = menu;

		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);

		//TITULO
		label = new JLabel("", SwingConstants.CENTER);
		label.setIcon(new ImageIcon("Resources/Icons/TxtSobre.png"));
		label.setBounds(335, 79, 130, 60);
		this.add(label);

		//LOGO
		Logo = new JLabel();
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);

		//START >>  Texto
		jLabel1 = new JLabel("Desenvolvido por: ", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 200, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("André Estévão (2011157298)", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 230, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("Inês Coelho (20041043011)", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 260, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("Programação Orientada a Objetos", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 290, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("LEI 2012-2013", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 320, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("Animação gráfica 3D gentilmente cedida por João Carlos Gonçalves.", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 380, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("Dedicado a todos os fãs SW!", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 430, 800, 16);
		this.add(jLabel1);
		jLabel1 = new JLabel("(e a alguns em especial... eles sabem quem são!)", SwingConstants.CENTER);
		jLabel1.setForeground(new Color(255, 216, 0));
		jLabel1.setBounds(0, 460, 800, 16);
		this.add(jLabel1);

		//START >>  End
		End = new JButton();
		End.setBackground(new Color(255, 216, 0));
		End.setText("OK");
		End.setBounds(325, 500, 150, 23);
		this.add(End);
		End.addActionListener(new ActionListener() {
			@Override
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
