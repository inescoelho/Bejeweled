/* PROJECTO POO 2012/2013
    Bejeweled
    Realizado por: André Estévão (2011157298) e M. Inês Coelho (2004104311) - Turma: TP3
    Curso: Licenciatura em Engenharia Informática
 */

package Game;

import java.io.Serializable;
import java.util.ArrayList;

import Game.Files.FicheiroDeTexto;
import Game.GraphicalEngine.DarkSide3D;
import Game.GraphicalEngine.EngineConfig;

public class Grid implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Localizacao do ficheiro com a grelha de teste
	 */
	private static String TesteGridFile = "Resources//Ficheiros//grid.txt";
	/**
	 * Tabuleiro de jogo
	 */
	private Peca[][] gameGrid;
	/**
	 * Dimensao (numero de elementos em cada linha) da grelha de jogo
	 */
	private int dimensao;
	/**
	 * Razao do tamanho da imagem das pecas que aparece no ecra de jogo
	 */
	private float razaoTamPecas;
	/**
	 * Array que armazena as coordenadas X no espaco das pecas, para efeitos do
	 * posicionamento das pecas no espaco no ecra de jogo
	 */
	private float[] coordX;
	/**
	 * Array que armazena as coordenadas Y no espaco das pecas, para efeitos do
	 * posicionamento das pecas no espaco no ecra de jogo
	 */
	private float[] coordY;

	// CONSTRUTOR
	/**
	 * Inicializa objeto Grid com grelha de jogo vazia com a dimensao
	 * especificada
	 * 
	 * @param dim
	 *            dimensao da grelha
	 */
	Grid(int dim) {
		this.gameGrid = new Peca[dim][dim];
		this.dimensao = dim;
		this.coordX = this.calculaCoordenada();
		this.coordY = this.InverteArrayCoordenadas(coordX);
	}

	/**
	 * Inicializa objeto Grid com a grelha de jogo especificada
	 * 
	 * @param grid
	 *            grelha de jogo
	 */
	Grid(Peca[][] grid) {
		this.gameGrid = grid;
		this.dimensao = grid.length;
		this.coordX = this.calculaCoordenada();
		this.coordY = this.InverteArrayCoordenadas(coordX);
	}

	// METODOS - Getters and Setters
	public Peca[][] getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(Peca[][] gameGrid) {
		this.gameGrid = gameGrid;
	}

	public int getDimensao() {
		return dimensao;
	}

	public void setDimensao(int dimensao) {
		this.dimensao = dimensao;
	}

	public float getRazaoTamPecas() {
		return razaoTamPecas;
	}

	// nada de mexer nisto la fora, e definido com cada nova grelha :P
	// public void setRazaoTamPecas(float razaoTamPecas) {
	// this.razaoTamPecas = razaoTamPecas;
	// }

	public float[] getCoordX() {
		return coordX;
	}

	public void setCoordX(float[] coordX) {
		this.coordX = coordX;
	}

	public float[] getCoordY() {
		return coordY;
	}

	public void setCoordY(float[] coordY) {
		this.coordY = coordY;
	}

	// METODOS

	/**
	 * Calcula, de acordo com a dimensao do tabuleiro, a distancia entre as
	 * pecas no espaco, a razao do seu tamanho e armazena num array as posicoes
	 * que as pecas devem ocupar no eixo dos xx
	 * 
	 * @return array com as posicoes que as pecas devem ocupar no eixo dos xx
	 */
	private float[] calculaCoordenada() {
		float[] posicoes = new float[this.getDimensao()];

		float distancia_pecas = (float) (20.0 / (this.dimensao - 1));
		this.razaoTamPecas = distancia_pecas;
		float pos = -10;

		for (int i = 0; i < this.getDimensao(); i++) {
			posicoes[i] = pos;
			pos += distancia_pecas;
		}

		return posicoes;
	}

	/**
	 * Inverte o array com as posicoes que as pecas devem ocupar no eixo dos xx,
	 * para criar as coordenadas no espaço yy
	 * 
	 * @return array com as posicoes que as pecas devem ocupar no eixo dos yy
	 */
	private float[] InverteArrayCoordenadas(float[] array) {
		float[] posicoes = new float[this.getDimensao()];

		for (int i = 0; i < this.getDimensao(); i++) {
			posicoes[i] = array[this.getDimensao() - 1 - i];
		}

		return posicoes;
	}

	/**
	 * Carrega a grelha de jogo, com dimensao fixa (8*8) a partir de um ficheiro
	 * de texto
	 * 
	 * @return grelha de jogo
	 */
	public void createGameFromFile() {
		Peca[][] gameGrid = this.getGameGrid();
		FicheiroDeTexto texto = new FicheiroDeTexto();
		int[] tipo = new int[2];

		try {
			texto.abreLeitura(TesteGridFile);
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					tipo = texto.leNumero();
					if (tipo[0] == -1) {
						System.out.print("ERROR: Ficheiro Corrompido");
						System.exit(-1);
					} else {
						gameGrid[i][j] = new Peca(tipo[1], 0, i, j,
								this.getCoordX()[i], this.getCoordY()[j],
								this.getCoordX()[i], this.getCoordY()[j], false);
					}
				}
			texto.fechaLeitura();
		} catch (Exception e) {
			System.out
					.println("Nao e possivel carregar grelha a partir do ficheiro.");
		}

		return;
	}

	/**
	 * Cria a grelha de jogo com a dimensao especificada nos parametros da
	 * classe
	 */
	public void createGrid() {
		int tipo, i, j, dim;
		Peca[][] table;

		dim = this.getDimensao();
		table = new Peca[dim][dim];

		for (i = 0; i < dim; i++)
			for (j = 0; j < dim; j++) {
				tipo = selectTipo(table, i, j);
				table[i][j] = new Peca(tipo, 0, i, j, this.getCoordX()[i],
						this.getCoordY()[j], this.getCoordX()[i],
						this.getCoordY()[j], false);
			}

		
		this.setGameGrid(table);
		if (!this.confirmaJogadasPossiveis()) this.createGrid();

		return;
	}

	/**
	 * Cria um tipo de peca random de forma que, ao posicionar a peca no
	 * tabuleiro, nao se formem conjuntos
	 * 
	 * @param table
	 *            grelha de jogo que esta a ser criada
	 * @param i
	 *            posicao x da peca a criar na grelha
	 * @param j
	 *            posicao y da peca a criar na grelha
	 * 
	 * @return tipo da peca a criar
	 */
	int selectTipo(Peca[][] table, int i, int j) {
		int tipo, tipo1, tipo2;
		boolean flag1 = false, flag2 = false;

		do {
			tipo = (int) (Math.random() * 6);

			if (i > 1) {
				tipo1 = table[i - 1][j].getTipo();
				tipo2 = table[i - 2][j].getTipo();
				if (tipo == tipo1 && tipo == tipo2)
					flag1 = true;
				else
					flag1 = false;
			}

			if (j > 1) {
				tipo1 = table[i][j - 1].getTipo();
				tipo2 = table[i][j - 2].getTipo();
				if (tipo == tipo1 && tipo == tipo2)
					flag2 = true;
				else
					flag2 = false;
			}
		} while (flag1 || flag2);

		return tipo;
	}

	/**
	 * Confirma se o movimento e possivel e, nesse caso, troca as pecas e faz a
	 * jogada
	 * 
	 * @param aux1
	 *            primeira peca a mover
	 * @param aux2
	 *            segunda peca a mover
	 * @param animation
	 *            motor grafico do jogo
	 * @param pont
	 *            pontuacao do jogador
	 * 
	 * @return (true) se ainda houver jogadas possiveis, (false) caso contrario
	 */
	public boolean movePecas(Peca aux1, Peca aux2, DarkSide3D animation,
			Score pont) {
		boolean mov = false, movPossivel = false;
		int x1, y1, x2, y2;

		x1 = aux1.getPosx();
		y1 = aux1.getPosy();
		x2 = aux2.getPosx();
		y2 = aux2.getPosy();

		mov = confirmaMovimento(x1, y1, x2, y2);

		if (mov) {
			this.trocaPecas(aux1, aux2);
			this.fazJogada(animation, pont);
		}

		movPossivel = this.confirmaJogadasPossiveis();

		return movPossivel;
	}

	/**
	 * Percorre a grelha, verificando se ha jogadas possiveis
	 * 
	 * @return (true) se ainda houver jogadas possiveis, (false) caso contrario
	 */
	boolean confirmaJogadasPossiveis() {
		int dim = this.getDimensao();
		boolean flag = false;
		int line, column;

		for (line = 0; line < dim; line++)
			for (column = 0; column < dim - 1; column++) {
				flag = this.confirmaMovimento(column, line, column + 1, line);
				if (flag)
					return true;
			}

		for (column = 0; column < dim; column++)
			for (line = 0; line < dim - 1; line++) {
				flag = this.confirmaMovimento(column, line, column, line + 1);
				if (flag)
					return true;
			}

		return flag;
	}

	/**
	 * Recebe a localizacao de 2 pecas na grelha e verifica se a sua troca forma
	 * um conjunto
	 * 
	 * @param x1
	 *            posicao x da primeira peca a mover
	 * @param y1
	 *            posicao y da primeira peca a mover
	 * @param x2
	 *            posicao x da segunda peca a mover
	 * @param y2
	 *            posicao y da segunda peca a mover
	 * 
	 * @return (true) se o movimento for possivel (false) caso contrario
	 */
	boolean confirmaMovimento(int x1, int y1, int x2, int y2) {
		boolean mov = false;

		for (int cond = 1; cond <= 8; cond++) {
			if (y1 == y2) // troca vertical
			{
				if (x1 < x2)
					mov = confirmaMovVertical(x1, x2, y1, cond);
				else
					mov = confirmaMovVertical(x2, x1, y1, cond);
			} else// troca horizontal
			{
				if (y1 < y2)
					mov = confirmaMovHorizontal(x1, y1, y2, cond);
				else
					mov = confirmaMovHorizontal(x1, y2, y1, cond);
			}
			if (mov)
				break;
		}

		return mov;
	}

	/**
	 * No caso da troca de pecas ser vertical, verifica se, de acordo com o
	 * numero da condicao, se formam conjuntos ao trocar as duas pecas
	 * 
	 * @param x1
	 *            posicao x da primeira peca a mover
	 * @param x2
	 *            posicao x da segunda peca a mover
	 * @param y2
	 *            posicao y da segunda peca a mover
	 * @param condicao
	 *            numero da condicao a verificar
	 * @return (true) se o movimento for possivel (false) caso contrario
	 */
	boolean confirmaMovVertical(int x1, int x2, int y, int condicao) {
		Peca[][] gr = this.getGameGrid();
		boolean mov = false;
		int dim = this.getDimensao();

		switch (condicao) {
		case 1:// confirma cima
			if (x1 > 1 && gr[x2][y].getTipo() == gr[x1 - 1][y].getTipo()
					&& gr[x2][y].getTipo() == gr[x1 - 2][y].getTipo())
				mov = true;
			break;
		case 2: // confirma baixo
			if (x2 < dim - 2 && gr[x1][y].getTipo() == gr[x2 + 1][y].getTipo()
					&& gr[x1][y].getTipo() == gr[x2 + 2][y].getTipo())
				mov = true;
			break;
		case 3: // x1 - confirma esq
			if (y > 1 && gr[x1][y].getTipo() == gr[x2][y - 1].getTipo()
					&& gr[x1][y].getTipo() == gr[x2][y - 2].getTipo())
				mov = true;
			break;
		case 4: // x1 - confirma dir
			if (y < dim - 2 && gr[x1][y].getTipo() == gr[x2][y + 1].getTipo()
					&& gr[x1][y].getTipo() == gr[x2][y + 2].getTipo())
				mov = true;
			break;
		case 5: // x1 - confirma uma peca esq e outra dir
			if (y != 0 && y != dim - 1
					&& gr[x1][y].getTipo() == gr[x2][y - 1].getTipo()
					&& gr[x1][y].getTipo() == gr[x2][y + 1].getTipo())
				mov = true;
			break;
		case 6: // x2 - confirma esq
			if (y > 1 && gr[x2][y].getTipo() == gr[x1][y - 1].getTipo()
					&& gr[x2][y].getTipo() == gr[x1][y - 2].getTipo())
				mov = true;
			break;
		case 7: // x2 - confirma dir
			if (y < dim - 2 && gr[x2][y].getTipo() == gr[x1][y + 1].getTipo()
					&& gr[x2][y].getTipo() == gr[x1][y + 2].getTipo())
				mov = true;
			break;
		case 8: // x2 - confirma uma peca esq e outra dir
			if (y != 0 && y != dim - 1
					&& gr[x2][y].getTipo() == gr[x1][y - 1].getTipo()
					&& gr[x2][y].getTipo() == gr[x1][y + 1].getTipo())
				mov = true;
			break;
		}

		return mov;
	}

	/**
	 * No caso da troca de pecas ser horizontal, verifica se, de acordo com o
	 * numero da condicao, se formam conjuntos ao trocar as duas pecas
	 * 
	 * @param x1
	 *            posicao x da primeira peca a mover
	 * @param x2
	 *            posicao x da segunda peca a mover
	 * @param y2
	 *            posicao y da segunda peca a mover
	 * @param condicao
	 *            numero da condicao a verificar
	 * @return (true) se o movimento for possivel (false) caso contrario
	 */
	boolean confirmaMovHorizontal(int x, int y1, int y2, int condicao) {
		Peca[][] gr = this.getGameGrid();
		boolean mov = false;
		int dim = this.getDimensao();

		switch (condicao) {
		case 1:// confirma esquerda
			if (y1 > 1 && gr[x][y2].getTipo() == gr[x][y1 - 1].getTipo()
					&& gr[x][y2].getTipo() == gr[x][y1 - 2].getTipo())
				mov = true;
			break;
		case 2: // confirma direita
			if (y2 < dim - 2 && gr[x][y1].getTipo() == gr[x][y2 + 1].getTipo()
					&& gr[x][y1].getTipo() == gr[x][y2 + 2].getTipo())
				mov = true;
			break;
		case 3: // y1 - confirma cima
			if (x > 1 && gr[x][y1].getTipo() == gr[x - 1][y2].getTipo()
					&& gr[x][y1].getTipo() == gr[x - 2][y2].getTipo())
				mov = true;
			break;
		case 4: // y1 - confirma baixo
			if (x < dim - 2 && gr[x][y1].getTipo() == gr[x + 1][y2].getTipo()
					&& gr[x][y1].getTipo() == gr[x + 2][y2].getTipo())
				mov = true;
			break;
		case 5: // y1 - confirma uma peca em cima e outra em baixo
			if (x != 0 && x != dim - 1
					&& gr[x][y1].getTipo() == gr[x - 1][y2].getTipo()
					&& gr[x][y1].getTipo() == gr[x + 1][y2].getTipo())
				mov = true;
			break;
		case 6: // y2 - confirma cima
			if (x > 1 && gr[x][y2].getTipo() == gr[x - 1][y1].getTipo()
					&& gr[x][y2].getTipo() == gr[x - 2][y1].getTipo())
				mov = true;
			break;
		case 7: // y2 - confirma baixo
			if (x < dim - 2 && gr[x][y2].getTipo() == gr[x + 1][y1].getTipo()
					&& gr[x][y2].getTipo() == gr[x + 2][y1].getTipo())
				mov = true;
			break;
		case 8: // y2 - confirma uma peca em cima e outra em baixo
			if (x != 0 && x != dim - 1
					&& gr[x][y2].getTipo() == gr[x - 1][y1].getTipo()
					&& gr[x][y2].getTipo() == gr[x + 1][y1].getTipo())
				mov = true;
			break;
		}

		return mov;
	}

	/**
	 * Troca a posicao de duas pecas na grelha e atualiza os seus parametros
	 * 
	 * @param aux1
	 *            primeira peca a mover
	 * @param aux2
	 *            segunda peca a mover
	 */
	void trocaPecas(Peca aux1, Peca aux2) {
		int x1, y1, x2, y2;

		x1 = aux1.getPosx();
		y1 = aux1.getPosy();
		x2 = aux2.getPosx();
		y2 = aux2.getPosy();

		aux1.atualizaPosicao(x2, y2, this.getCoordX()[x2],
				this.getCoordY()[y2], true);
		aux2.atualizaPosicao(x1, y1, this.getCoordX()[x1],
				this.getCoordY()[y1], true);
		this.getGameGrid()[x1][y1] = aux2;
		this.getGameGrid()[x2][y2] = aux1;
	}

	/**
	 * Deteta os conjuntos de Pecas formados, remove as pecas e adiciona novas
	 * pecas a grelha, verificando se estas, por sua vez, também formam
	 * conjuntos
	 * 
	 * @param animation
	 *            Motor Grafico
	 * @param pont
	 *            pontuacao do jogador
	 */
	void fazJogada(DarkSide3D animation, Score pont) {
		ArrayList<Peca> pecasToRemoveH = new ArrayList<Peca>();
		ArrayList<Peca> pecasToRemoveV = new ArrayList<Peca>();
		ArrayList<Peca> pecasNovas = new ArrayList<Peca>();
		ArrayList<Peca> pecasToAdd = new ArrayList<Peca>();
		ArrayList<Peca> pecasToRemove = new ArrayList<Peca>();
		Peca aux;
		int i, tam;

		while (true) {
			detetaConjuntos(pecasToRemoveH, pecasToRemoveV, pecasNovas, pont);

			if (pecasToRemoveH.isEmpty() && pecasToRemoveV.isEmpty())
				break;

			// remove as pecas que pertenciam a conjuntos e foram colocadas nos
			// arrays
			tam = pecasToRemoveH.size();
			for (i = 0; i < tam; i++) {
				aux = pecasToRemoveH.get(0);
				aux.setMoving(true);
				pecasToRemoveH.remove(0);
				aux.destroiPeca(this.getGameGrid(), pecasToRemove);
			}

			tam = pecasToRemoveV.size();
			for (i = 0; i < tam; i++) {
				aux = pecasToRemoveV.get(0);
				aux.setMoving(true);
				pecasToRemoveV.remove(0);
				aux.destroiPeca(this.getGameGrid(), pecasToRemove);
			}

			// adiciona os powerups formados a grelha
			tam = pecasNovas.size();
			for (i = 0; i < tam; i++) {
				aux = pecasNovas.get(0);
				pecasNovas.remove(0);
				this.getGameGrid()[aux.getPosx()][aux.getPosy()] = aux;
				pecasToAdd.add(aux);
			}

			// as pecas caem para ocupar os espacos deixados livres pelas pecas
			// removidas
			gravidade();

			// cria novas pecas para ocuparem os espacos vazios
			criaPecas(pecasToAdd);

			for (i = 0; i < pecasToRemove.size(); i++)
				System.out.println(pecasToRemove.get(i).toString());

			// passa os arrays de Pecas a inserir e a eliminar para o motor
			// grafico e esvazia os arrays
			animation.removePecas(pecasToRemove);
			animation.adicionaPecas(pecasToAdd);
			pecasToRemove.clear();
			pecasToAdd.clear();

			// espera antes de verificar novamente se formaram novos conjuntos
			try {
				Thread.sleep(EngineConfig.MATCH_DETECTION_TIMEOUT);
			} catch (InterruptedException e) {
			}
		}

	}

	/**
	 * Pesquisam a grelha de forma a verificar a formacao de Conjuntos Verticais
	 * e Horizontais
	 * 
	 * @param pecasToRemoveH
	 *            Armazena as pecas a remover provenientes de conjuntos
	 *            horizontais
	 * @param pecasToRemoveV
	 *            Armazena as pecas a remover provenientes de conjuntos
	 *            verticais
	 * @param pecasNovas
	 *            Armazena as pecas novas (powerups) a criar na grelha
	 * 
	 * @return numero de conjuntos formados;
	 */
	void detetaConjuntos(ArrayList<Peca> pecasToRemoveH,
			ArrayList<Peca> pecasToRemoveV, ArrayList<Peca> pecasNovas,
			Score pont) {
		int dim = this.getDimensao();
		Peca[][] gr = this.getGameGrid();
		ArrayList<Peca> pecas = new ArrayList<Peca>();
		int i, j, k, qtdd;

		// deteta Conjuntos Horizontais
		for (j = 0; j < dim; j++)
			for (i = 0; i < dim - 2; i++) {
				if (gr[i][j].getTipo() == gr[i + 1][j].getTipo()) {
					qtdd = detetaConjuntosHorizontais(i, j, pecasNovas);
					pecas.clear();

					for (k = 0; k < qtdd; k++) {
						pecasToRemoveH.add(gr[i + k][j]);
						pecas.add(gr[i + k][j]);
					}

					if (qtdd == 0)
						i++;
					else {
						i += qtdd - 1;
						pont.calculaPontuacao(pecas);
					}
				}
			}

		// deteta conjuntos verticais
		for (i = 0; i < dim; i++)
			for (j = 0; j < dim - 2; j++) {
				if (gr[i][j].getTipo() == gr[i][j + 1].getTipo()) {
					qtdd = detetaConjuntosVerticais(i, j, pecasNovas,
							pecasToRemoveH);
					pecas.clear();

					for (k = 0; k < qtdd; k++) {
						if (!pecasToRemoveH.contains(gr[i][j + k])) {
							pecasToRemoveV.add(gr[i][j + k]);
							pecas.add(gr[i][j + k]);
						}
					}

					if (qtdd == 0)
						j++;
					else {
						j += qtdd - 1;
						pont.calculaPontuacao(pecas);
					}
				}
			}

		return;
	}

	/**
	 * Detecta a existência de conjuntos horizontais a partir da posicao i,j
	 * 
	 * @param i
	 *            posicao x da peca
	 * @param j
	 *            posicao y da peca
	 * @param pecasNovas
	 *            Armazena as pecas novas (powerups) a criar na grelha
	 * @return devolve a quantidade de pecas iguais seguidas horizontalmente a
	 *         partir das coordenadas i,j ou 0 caso o numero seja menor que 3
	 */
	int detetaConjuntosHorizontais(int i, int j, ArrayList<Peca> pecasNovas) {
		int dim = this.getDimensao();
		Peca[][] gr = this.getGameGrid();
		int qtdd;

		qtdd = 2;
		while (gr[i][j].getTipo() == gr[i + 2][j].getTipo()) {
			qtdd++;
			i++;
			if (i == dim - 2)
				break;
		}

		if (qtdd < 3)
			qtdd = 0;
		else if (qtdd == 4)
			pecasNovas.add(new PowerUpLinha(gr[i - 2][j].getTipo(), i - 2, j,
					this.getCoordX()[i - 2], this.getCoordY()[j]));
		else if (qtdd == 5)
			pecasNovas.add(new PowerUpTipo(gr[i - 3][j].getTipo(), i - 3, j,
					this.getCoordX()[i - 3], this.getCoordY()[j]));

		return qtdd;
	}

	/**
	 * Detecta a existência de conjuntos verticais a partir da posicao i,j
	 * 
	 * @param i
	 *            posicao x da peca
	 * @param j
	 *            posicao y da peca
	 * @param pecasNovas
	 *            Armazena as pecas novas (powerups) a criar na grelha
	 * @param pecasToRemoveH
	 *            Pecas a remover na grelha proveniente de conjuntos horizontais
	 *            formados
	 * @return devolve a quantidade de pecas iguais seguidas verticalmente a
	 *         partir das coordenadas i,j ou 0 caso o numero seja menor que 3
	 */
	int detetaConjuntosVerticais(int i, int j, ArrayList<Peca> pecasNovas,
			ArrayList<Peca> pecasToRemoveH) {
		int dim = this.getDimensao();
		Peca[][] gr = this.getGameGrid();
		int qtdd, k, pos = j;
		boolean flag = true;

		qtdd = 2;
		while (gr[i][pos].getTipo() == gr[i][pos + 2].getTipo()) {
			qtdd++;
			pos++;
			if (pos == dim - 2)
				break;
		}

		if (qtdd < 3)
			return 0;

		for (k = 0; k < qtdd; k++) {
			if (pecasToRemoveH.contains(gr[i][j + k])) {
				verificaPecasCriadas(pecasNovas, i, j + k);
				pecasNovas.add(new PowerUpOrtogonal(gr[i][j + k].getTipo(), i,
						j + k, this.getCoordX()[i], this.getCoordY()[j + k]));
				flag = false;
			}
		}

		if (flag && qtdd == 4)
			pecasNovas.add(new PowerUpLinha(gr[i][j].getTipo(), i, j, this
					.getCoordX()[i], this.getCoordY()[(j)]));
		else if (flag && qtdd == 5)
			pecasNovas.add(new PowerUpTipo(gr[i][j].getTipo(), i, j, this
					.getCoordY()[i], this.getCoordY()[(j)]));

		return qtdd;
	}

	/**
	 * Verifica se foi criado algum powerup com o conjunto de pecas que agora
	 * pertence ao cjt ortogonal e, se sim, elimina esse powerup
	 * 
	 * @param pecasNovas
	 *            Armazena as pecas novas (powerups) a criar na grelha
	 * @param i
	 *            posicao x da peca
	 * @param j
	 *            posicao y da peca
	 */
	void verificaPecasCriadas(ArrayList<Peca> pecasNovas, int i, int j) {
		int pos;
		Peca thing;
		boolean flag = false;

		for (pos = 0; pos < pecasNovas.size(); pos++) {
			thing = pecasNovas.get(pos);
			if (thing.getPosy() == j && thing.getPowerup() == 1) {
				if (i >= thing.getPosx() && i <= thing.getPosx() + 3)
					flag = true;
			}

			else if (thing.getPosy() == j && thing.getPowerup() == 3) {
				if (i >= thing.getPosx() && i <= thing.getPosx() + 4)
					flag = true;
			}

			if (flag)
				pecasNovas.remove(pos);
		}

		return;
	}

	/**
	 * Faz com que as pecas caiam verticalmente de forma a preencher os espacos
	 * vazios da grelha
	 */
	void gravidade() {
		int dim = this.getDimensao();
		Peca[][] gr = this.getGameGrid();
		int line, column, buracos, k;
		Peca aux;

		for (column = 0; column < dim; column++) {
			buracos = 0;
			for (line = dim - 1; line >= 0; line--) {
				if (gr[column][line] == null) {
					buracos++;
				} else if (buracos != 0) {
					aux = this.getGameGrid()[column][line];
					aux.atualizaPosicao(column, line + buracos,
							this.getCoordX()[column], this.getCoordY()[line
									+ buracos], true);
					this.getGameGrid()[column][line + buracos] = aux;
				}
			}
			for (k = 0; k < buracos; k++) {
				gr[column][k] = null;
			}
		}
	}

	/**
	 * Cria novas pecas para preencherem os espacos vazios da grelha
	 */
	void criaPecas(ArrayList<Peca> pecasToAdd) {
		int dim = this.getDimensao();
		Peca[][] gr = this.getGameGrid();
		int line, column, tipo;

		for (column = 0; column < dim; column++)
			for (line = 0; line < dim; line++) {
				if (gr[column][line] == null) {
					tipo = (int) (Math.random() * 6);
					gr[column][line] = new Peca(tipo, 0, column, line,
							EngineConfig.INITIAL_POSITION,
							EngineConfig.INITIAL_POSITION,
							this.getCoordX()[column], this.getCoordY()[line],
							true);
					pecasToAdd.add(gr[column][line]);
				}
			}
	}

}
