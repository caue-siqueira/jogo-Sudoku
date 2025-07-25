import javax.swing.*;
import java.awt.*;

public class SudokuGUI extends JFrame {
    private final SudokuGame game;
    private final JTextField[][] cells = new JTextField[9][9];

    public SudokuGUI(SudokuGame game) {
        this.game = game;
        setTitle("Sudoku");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUI();
    }

    private void buildUI() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        Font font = new Font("SansSerif", Font.BOLD, 18);

        for (int i = 0; i < 9; i++) {
    for (int j = 0; j < 9; j++) {
        JTextField tf = new JTextField();
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(font);

        // Definir bordas escuras para destacar blocos 3x3
        int top = (i % 3 == 0) ? 2 : 1;
        int left = (j % 3 == 0) ? 2 : 1;
        int bottom = (i == 8) ? 2 : 1;
        int right = (j == 8) ? 2 : 1;

        tf.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.DARK_GRAY));

        cells[i][j] = tf;
        gridPanel.add(tf);
        

    }
}

        JButton verificarBtn = new JButton("Verificar");
        verificarBtn.addActionListener(e -> {
            updateBoardFromFields();
            JOptionPane.showMessageDialog(this, "Status: " + game.getStatus());
        });

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> {
            game.clearUserInputs();
            refreshBoard();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verificarBtn);
        buttonPanel.add(limparBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
       
    }

    private void updateBoardFromFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText();
                if (!text.isBlank()) {
                    try {
                        int value = Integer.parseInt(text);
                        game.insertValue(i, j, value);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }

    private void refreshBoard() {
        SudokuCell[][] board = game.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = board[i][j];
                cells[i][j].setText(cell.getValue() != null ? String.valueOf(cell.getValue()) : "");
                cells[i][j].setEditable(!cell.isFixed());
            }
        }
    }

    public void loadInitialBoard() {
        refreshBoard();
    }


    
}
