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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Game.Bejeweled;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */

@SuppressWarnings("serial")
public class DefinitionGUI extends JPanel {
	/**
	 * Icon com o nome do menu
	 */
	private JLabel label;
	/**
	 * Logotipo principal
	 */
	private JLabel Logo;
	/**
	 * Campo de Introducao da nova dimensao da grelha
	 */
	private JTextField NewDim;
	/**
	 * Campo informativo - introduza nova dimensao da grelha
	 */
	private JLabel altDimensao;
	/**
	 * Menagem de erro, caso seja introduzida uma dimensao invalida
	 */
	private JLabel jLabel2;
	/**
	 * Campo informativo - Som on/off
	 */
	JLabel lblLigarDesligar;
	/**
	 * Permite optar por Som on/off
	 */
	JCheckBox checkBox;
	/**
	 * Altera a resolucao do Ecra
	 */
	@SuppressWarnings("rawtypes")
	JComboBox ecraResolution;
	/**
	 * Texto - alterar a resolucao do Ecra
	 */
	private JLabel jLabel3;
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

	/**
	 * Inicializa os componentes do menu
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefinitionGUI(Bejeweled bj, MenuGUI menu) {
		this.game = bj;
		this.menuPrincipal = menu;

		bj.getContentor().removeAll();
		this.setLayout(null);
		this.setBackground(Color.BLACK);

		// TITULO
		label = new JLabel("");
		label.setIcon(new ImageIcon("Resources/Icons/TxtDefinicoes.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(350, 79, 99, 60);
		add(label);

		// LOGO
		Logo = new JLabel();
		Logo.setIcon(new ImageIcon("Resources/Icons/logo.png"));
		Logo.setBounds(0, 0, 800, 125);
		this.add(Logo);

		// START >> jLabel2 - msg de erro
		jLabel2 = new JLabel("", SwingConstants.CENTER);
		jLabel2.setForeground(new Color(255, 216, 0));
		jLabel2.setBounds(0, 380, 800, 16);
		this.add(jLabel2);

		// START >> altDimensao
		altDimensao = new JLabel();
		altDimensao.setForeground(new Color(255, 216, 0));
		altDimensao.setText("Inserir Nova Dimensão da Grelha:");
		altDimensao.setBounds(250, 180, 201, 16);
		this.add(altDimensao);

		// START >> NewDim
		NewDim = new JTextField();
		NewDim.setBounds(460, 180, 47, 23);
		this.add(NewDim);

		// Label de Som
		lblLigarDesligar = new JLabel("Ligar / desligar som:");
		lblLigarDesligar.setForeground(new Color(255, 216, 0));
		lblLigarDesligar.setBounds(250, 230, 150, 14);
		this.add(lblLigarDesligar);

		// Check box
		checkBox = new JCheckBox("");
		checkBox.setBackground(Color.BLACK);
		checkBox.setBounds(460, 230, 21, 23);
		checkBox.setSelected(Bejeweled.som.getSoundTrig());
		this.add(checkBox);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckBoxActionPerformed(e);
			}
		});

		String[] resolutionOptions = { "1024x768", "800x600", "640x480" };

		// START >> jLabel3
		jLabel3 = new JLabel("Resolucao do Ecra:");
		jLabel3.setForeground(new Color(255, 216, 0));
		jLabel3.setBounds(250, 280, 201, 16);
		this.add(jLabel3);

		// Combobox - Seleccionar Resolucao do Ecra
		ecraResolution = new JComboBox(resolutionOptions);
		ecraResolution.setSelectedIndex(2);
		ecraResolution.setBounds(460, 280, 100, 23);
		ecraResolution.setBackground(Color.WHITE);
		this.add(ecraResolution);
		ecraResolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ecraResolutionActionPerformed(e);
			}
		});

		// START >> End
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
	 * Confirma se a dimensao introduzida e valida e volta ao menu Principal
	 */
	private void EndActionPerformed(ActionEvent evt) {
		String input;
		int newdim = 0;

		try {
			input = this.NewDim.getText();
			if (!input.isEmpty())
				newdim = Integer.parseInt(input);
			else
				newdim = this.getGame().getDimension();
		} catch (NumberFormatException e) {
			jLabel2.setText("O valor introduzido não é um numero inteiro!");
			return;
		}

		if (newdim < 5 || newdim > 20) {
			jLabel2.setText("Por favor introduza um valor entre 5 a 20.");
		}

		else {
			this.getGame().setDimension(newdim);
			System.out.println("grid size " + this.getGame().getDimension());
			System.out.println("Resolution " + this.getGame().getWidth() + " " + this.getGame().getHeight());
			this.removeAll();
			this.getGame().getContentor().add(new MenuGUI(this.getGame()));
			this.getGame().getFrame().setVisible(true);
		}
	}

	private void CheckBoxActionPerformed(ActionEvent evt) {
		if (checkBox.isSelected()) {
			Bejeweled.som.setSoundTrig(true);
			try {
				Bejeweled.som.inicializa();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Bejeweled.som.setSoundTrig(false);
			try {
				Bejeweled.som.clip.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void ecraResolutionActionPerformed(ActionEvent evt) {
		int opt;

		opt = ecraResolution.getSelectedIndex();

		switch (opt) {
		case 0:
			this.getGame().setWidth(1024);
			this.getGame().setHeight(768);
			break;
		case 1:
			this.getGame().setWidth(800);
			this.getGame().setHeight(600);
			break;
		case 2:
			this.getGame().setWidth(640);
			this.getGame().setHeight(480);
			break;
		}

		return;
	}
}
