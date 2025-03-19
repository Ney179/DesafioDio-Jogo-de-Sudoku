package sudoku;

import java.util.Scanner;

public class Sudoku {
	private static final int SIZE = 9; // Tamanho do tabuleiro
	public int[][] board;

	public Sudoku() {
		board = new int[SIZE][SIZE]; // O tabuleiro começa vazio
	}

	// Verifica se é seguro colocar um número
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

	// Método para exibir o tabuleiro
	public void printBoard() {
		for (int r = 0; r < SIZE; r++) {
			for (int d = 0; d < SIZE; d++) {
				System.out.print(board[r][d] + " ");
			}
			System.out.print("\n");
		}
	}

	// Método para verificar se o tabuleiro está completo
	private boolean isBoardComplete() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == 0) {
					return false; // Se encontrar uma célula vazia, o tabuleiro não está completo
				}
			}
		}
		return true; // Todas as células estão preenchidas
	}

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		System.out.println("Tabuleiro de Sudoku gerado (inicialmente vazio):");
		sudoku.printBoard();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Digite a linha (0-8), coluna (0-8) e número (1-9) para inserir (ou -1 para sair): ");
			int row = scanner.nextInt();
			if (row == -1) {
				System.out.println("Obrigado por jogar!");
				break; // Sair do jogo
			}
			int col = scanner.nextInt();
			int num = scanner.nextInt();

			// Verifica se a entrada é válida
			if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || num < 1 || num > 9) {
				System.out.println("Entrada inválida! Tente novamente.");
				continue; // Volta para o início do loop
			}

			// Verifica se é seguro inserir o número
			if (sudoku.isSafe(row, col, num)) {
				sudoku.board[row][col] = num;
				System.out.println("Número inserido com sucesso!");

				// Verifica se o tabuleiro está completo
				if (sudoku.isBoardComplete()) {
					System.out.println("Parabéns! Você completou o tabuleiro de Sudoku!");
					break; // Opcional: sair do jogo após completar
				}
			} else {
				System.out.println("Movimento inválido! Tente novamente.");
			}

			System.out.println("Tabuleiro atualizado:");
			sudoku.printBoard();
		}

		scanner.close();
	}
}