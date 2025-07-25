import javax.swing.SwingUtilities;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();

        int[][] valoresIniciais;

        if (args.length == 0) {
            // Geração aleatória de 15 valores fixos válidos
            valoresIniciais = gerarValoresFixosAleatorios(15);
        } else {
            valoresIniciais = Arrays.stream(args)
                .map(str -> str.split(","))
                .map(parts -> new int[] {
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
                })
                .toArray(int[][]::new);
        }

        game.startNewGame(valoresIniciais);

        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI(game);
            gui.loadInitialBoard();
        });
    }

    public static int[][] gerarValoresFixosAleatorios(int quantidade) {
        Random random = new Random();
        Set<String> usados = new HashSet<>();
        List<int[]> lista = new ArrayList<>();

        while (lista.size() < quantidade) {
            int linha = random.nextInt(9);
            int coluna = random.nextInt(9);
            int valor = 1 + random.nextInt(9);
            String chave = linha + "," + coluna;

            if (!usados.contains(chave)) {
                usados.add(chave);
                lista.add(new int[]{linha, coluna, valor});
            }
        }

        return lista.toArray(new int[0][]);
    }
}
