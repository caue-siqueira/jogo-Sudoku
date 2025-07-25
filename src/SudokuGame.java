import java.util.stream.IntStream;

public class SudokuGame {
    private final SudokuCell[][] board = new SudokuCell[9][9];
    private boolean started = false;

    public SudokuGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuCell(null, false);
            }
        }
    }

    public void startNewGame(int[][] initialValues) {
        for (int[] value : initialValues) {
            int row = value[0], col = value[1], val = value[2];
            board[row][col] = new SudokuCell(val, true);
        }
        started = true;
    }

    public boolean insertValue(int row, int col, int value) {
        if (board[row][col].isFixed() || board[row][col].getValue() != null) {
            return false;
        }
        board[row][col].setValue(value);
        return true;
    }

    public boolean removeValue(int row, int col) {
        if (board[row][col].isFixed()) {
            return false;
        }
        board[row][col].clearValue();
        return true;
    }

    public SudokuCell[][] getBoard() {
        return board;
    }

    public void clearUserInputs() {
        for (SudokuCell[] row : board) {
            for (SudokuCell cell : row) {
                if (!cell.isFixed()) {
                    cell.clearValue();
                    cell.clearPencilMarks();
                }
            }
        }
    }

    public boolean isBoardFull() {
        return IntStream.range(0, 9)
                .allMatch(i -> IntStream.range(0, 9)
                        .allMatch(j -> board[i][j].getValue() != null));
    }

    public boolean hasErrors() {
        // Checa linhas, colunas e blocos
        return hasConflicts();
    }

    private boolean hasConflicts() {
        // Verifica repetições em linhas, colunas e quadrantes
        for (int i = 0; i < 9; i++) {
            if (hasDuplicates(getRow(i)) || hasDuplicates(getColumn(i)) || hasDuplicates(getBlock(i))) {
                return true;
            }
        }
        return false;
    }

    private Integer[] getRow(int row) {
        return IntStream.range(0, 9)
                .mapToObj(j -> board[row][j].getValue())
                .filter(v -> v != null)
                .toArray(Integer[]::new);
    }

    private Integer[] getColumn(int col) {
        return IntStream.range(0, 9)
                .mapToObj(i -> board[i][col].getValue())
                .filter(v -> v != null)
                .toArray(Integer[]::new);
    }

    private Integer[] getBlock(int block) {
        int rowStart = (block / 3) * 3;
        int colStart = (block % 3) * 3;
        return IntStream.range(0, 9)
                .mapToObj(i -> board[rowStart + i / 3][colStart + i % 3].getValue())
                .filter(v -> v != null)
                .toArray(Integer[]::new);
    }

    private boolean hasDuplicates(Integer[] values) {
        return IntStream.range(0, values.length)
                .anyMatch(i -> IntStream.range(i + 1, values.length)
                        .anyMatch(j -> values[i].equals(values[j])));
    }

    public String getStatus() {
        if (!started) return "Não iniciado";
        if (hasErrors()) return "Jogo com erro";
        return isBoardFull() ? "Completo" : "Incompleto";
    }
}
