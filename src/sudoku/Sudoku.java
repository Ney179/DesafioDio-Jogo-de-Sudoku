package sudoku;

import java.util.Scanner;

public class Sudoku {
	private static final int SIZE = 9; // Tamanho do tabuleiro
	public int[][] board;

	public Sudoku() {
		board = new int[SIZE][SIZE]; // O tabuleiro come�a vazio
	}

	// Verifica se � seguro colocar um n�mero
	private boolean isSafe(int row, int col, int num) {
		return !isInRow(row, num) && !isInCol(col, num) && !isInSubgrid(row - row % 3, col - col % 3, num);
	}

	private boolean isInRow(int row, int num) {
		for (int col = 0; col < SIZE; col++) {
			if (board[row][col] == num) {
				return true;
			}
		}
		return false;
	}

	private boolean isInCol(int col, int num) {
		for (int row = 0; row < SIZE; row++) {
			if (board[row][col] == num) {
				return true;
			}
		}
		return false;
	}

	private boolean isInSubgrid(int startRow, int startCol, int num) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row + startRow][col + startCol] == num) {
					return true;
				}
			}
		}
		return false;
	}

	// M�todo para exibir o tabuleiro
	public void printBoard() {
		for (int r = 0; r < SIZE; r++) {
			for (int d = 0; d < SIZE; d++) {
				System.out.print(board[r][d] + " ");
			}
			System.out.print("\n");
		}
	}

	// M�todo para verificar se o tabuleiro est� completo
	private boolean isBoardComplete() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == 0) {
					return false; // Se encontrar uma c�lula vazia, o tabuleiro n�o est� completo
				}
			}
		}
		return true; // Todas as c�lulas est�o preenchidas
	}

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		System.out.println("Tabuleiro de Sudoku gerado (inicialmente vazio):");
		sudoku.printBoard();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Digite a linha (0-8), coluna (0-8) e n�mero (1-9) para inserir (ou -1 para sair): ");
			int row = scanner.nextInt();
			if (row == -1) {
				System.out.println("Obrigado por jogar!");
				break; // Sair do jogo
			}
			int col = scanner.nextInt();
			int num = scanner.nextInt();

			// Verifica se a entrada � v�lida
			if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || num < 1 || num > 9) {
				System.out.println("Entrada inv�lida! Tente novamente.");
				continue; // Volta para o in�cio do loop
			}

			// Verifica se � seguro inserir o n�mero
			if (sudoku.isSafe(row, col, num)) {
				sudoku.board[row][col] = num;
				System.out.println("N�mero inserido com sucesso!");

				// Verifica se o tabuleiro est� completo
				if (sudoku.isBoardComplete()) {
					System.out.println("Parab�ns! Voc� completou o tabuleiro de Sudoku!");
					break; // Opcional: sair do jogo ap�s completar
				}
			} else {
				System.out.println("Movimento inv�lido! Tente novamente.");
			}

			System.out.println("Tabuleiro atualizado:");
			sudoku.printBoard();
		}

		scanner.close();
	}
}