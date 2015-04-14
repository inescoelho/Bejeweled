/* PROJECTO POO 2012/2013
Bejeweled
Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
Curso: Licenciatura em Engenharia Informática
 */

package Game;

/**
 * Classe controladora de som
 * 
 * @param soundTrig
 *            Liga ou desliga o som.
 * @param soundGame
 *            Decide qual das duas musicas toca.
 * @param clip
 * 			Armazena a informação do som.
 */

import java.io.*;
import javax.sound.sampled.*;

public class Sound {
	/**
	 * Variavel controladora de som - liga/desliga som
	 */
	public boolean soundTrig;
	/**
	 * Variavel controladora de som (false) musica de menu - Star Wars Main
	 * Theme (true) musica de jogo - Imperial March
	 */
	public boolean soundGame;
	/**
	 * Clip de som
	 */
	public Clip clip;

	// CONSTRUTORES
	public Sound(boolean st, boolean sg) throws Exception {
		this.setSoundTrig(st);
		this.setSoundGame(sg);
		inicializa();
	}

	public Sound() throws Exception {
		this.setSoundTrig(true);
		this.setSoundGame(false);
		inicializa();
	}

	// METODOS - Getters and Setters
	public boolean getSoundTrig() {
		return soundTrig;
	}

	public void setSoundTrig(boolean st) {
		soundTrig = st;
	}

	public void setSoundGame(boolean sg) {
		soundGame = sg;
	}

	// METODOS
	/**
	 * Inicializa o clip de som
	 */
	public void inicializa() throws Exception {
		// Carrega cada um dos ficheiros de som individualmente.
		AudioInputStream maintheme = AudioSystem.getAudioInputStream(new File(
				"Resources\\Musica\\maintheme.wav"));
		AudioInputStream imperialmarch = AudioSystem
				.getAudioInputStream(new File(
						"Resources\\Musica\\imperialmarch.wav"));
		clip = AudioSystem.getClip();

		/**
		 * Verifica se está a decorrer um jogo ou não para decidir qual das duas
		 * musicas comeca a tocar.
		 */
		if (this.soundGame) {
			clip.open(imperialmarch);
		} else {
			clip.open(maintheme);
		}

		/**
		 * Caso o som esteja ligado, executa o metodo para tocar o som.
		 */
		if (this.soundTrig) {
			this.tocaSom(clip);
		}
	}

	/**
	 * Metodo para tocar o som.
	 * 
	 * @param c
	 *            clip de som
	 */
	public void tocaSom(Clip c) {
		c.start();
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
