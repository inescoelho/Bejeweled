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
import javax.swing.SwingConstants;

import Game.Bejeweled;

public class ErrorGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Jogo
	 */
	Bejeweled game;	
	/**
	 * Menu principal
	 */
	MenuGUI menuPrincipal;
	private JLabel BackGround;
	private JLabel jLabel1;
	private JButton End;

	public ErrorGUI(Bejeweled bj) {
		this.game = bj;
		
		bj.getContentor().removeAll();
		this.setLayout(null);
		
		// START >> jLabel1
		jLabel1 = new JLabel("", SwingConstants.CENTER);
		jLabel1.setText("N\u00E3o \u00E9 possivel iniciar jogo do tipo Teste!");
		jLabel1.setForeground(new Color(255, 255, 255));
		jLabel1.setBounds(0, 200, 800, 40);
		jLabel1.setFont(new Font(jLabel1.getFont().getFontName(), Font.PLAIN,
				20));
		this.add(jLabel1);
		
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
		BackGround.setIcon(new ImageIcon("Resources/Background/Error.jpg"));
		BackGround.setBounds(0, 0, 800, 600);
		add(BackGround);
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
