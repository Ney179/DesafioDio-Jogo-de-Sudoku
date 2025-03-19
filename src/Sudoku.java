import java.util.Random;
import java.util.Scanner;

public class Sudoku {

	private static final int SIZE = 9; // Tamanho do tabuleiro
    private static final int SUBGRID_SIZE = 3; // Tamanho do subgrid

    int[][] board;

    public Sudoku() {
        board = new int[SIZE][SIZE];
        generateSudoku();
    }

    // M�todo para gerar um tabuleiro de Sudoku
    private void generateSudoku() {
        fillDiagonal();
        fillRemaining(0, 0);
        removeDigits();
    }

    // Preenche as diagonais do tabuleiro
    private void fillDiagonal() {
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            fillSubgrid(i, i);
        }
    }

    // Preenche um subgrid 3x3
    private void fillSubgrid(int row, int col) {
        Random rand = new Random();
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                int num;
                do {
                    num = rand.nextInt(SIZE) + 1;
                } while (!isSafe(row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    // Preenche o restante do tabuleiro
    private boolean fillRemaining(int row, int col) {
        if (row == SIZE - 1 && col == SIZE) {
            return true; // Tabuleiro preenchido
        }
        if (col == SIZE) {
            row++;
            col = 0;
        }
        if (board[row][col] != 0) {
            return fillRemaining(row, col + 1);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (fillRemaining(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    // Verifica se � seguro colocar um n�mero na posi��o
    boolean isSafe(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInSubgrid(row - row % SUBGRID_SIZE, col - col % SUBGRID_SIZE, num);
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
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Remove alguns d�gitos para criar o quebra-cabe�a
    private void removeDigits() {
        Random rand = new Random();
        int count = 20; // N�mero de d�gitos a serem removidos
        while (count != 0) {
            int i = rand.nextInt(SIZE);
            int j = rand.nextInt(SIZE);
            if (board[i][j] != 0) {
                board[i][j] = 0;
                count--;
            }
        }
    }

    // M�todo para imprimir o tabuleiro
    public void printBoard() {
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(board[r][d] + " ");
            }
            System.out.print("\n");
        }
    }
    
    public static void main(String[] args) {
		
    	Sudoku sudoku = new Sudoku();
        System.out.println("Tabuleiro de Sudoku:");
        sudoku.printBoard();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Preencha o Sudoku. Digite '0' para deixar um espa�o vazio.");
        
        while (true) {
            System.out.print("Digite a linha (0-8), coluna (0-8) e n�mero (1-9) separados por espa�o (ou -1 para sair): ");
            int row = scanner.nextInt();
            if (row == -1) break; // Sair do jogo
            int col = scanner.nextInt();
            int num = scanner.nextInt();

            if (sudoku.isSafe(row, col, num)) {
                sudoku.board[row][col] = num;
                System.out.println("N�mero adicionado com sucesso!");
            } else {
                System.out.println("Movimento inv�lido! Tente novamente.");
            }

            System.out.println("Tabuleiro Atual:");
            sudoku.printBoard();
        }

        scanner.close();
        System.out.println("Obrigado por jogar!");
	}
}
