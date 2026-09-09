package chess;

import adt.Array2D;

public class ChessBoard {
    private Array2D<String> board;

    // Constructor que inicializa el tablero
    public ChessBoard() {
        board = new Array2D<>(8, 8);
        initializeBoard();
    }

    // Inicializar el tablero con las piezas Unicode
    private void initializeBoard() {
        // Piezas blancas (mayúsculas) y negras (minúsculas en Unicode)
        // ♔ ♕ ♖ ♗ ♘ ♙ (blancas)
        // ♚ ♛ ♜ ♝ ♞ ♟ (negras)

        // Fila 0 (fila 8 en notación de ajedrez) - Piezas negras
        board.set(0, 0, "\u265C"); // ♜ Torre negra
        board.set(0, 1, "\u265E"); // ♞ Caballo negro
        board.set(0, 2, "\u265D"); // ♝ Alfil negro
        board.set(0, 3, "\u265B"); // ♛ Reina negra
        board.set(0, 4, "\u265A"); // ♚ Rey negro
        board.set(0, 5, "\u265D"); // ♝ Alfil negro
        board.set(0, 6, "\u265E"); // ♞ Caballo negro
        board.set(0, 7, "\u265C"); // ♜ Torre negra

        // Fila 1 (fila 7 en notación de ajedrez) - Peones negros
        for (int j = 0; j < 8; j++) {
            board.set(1, j, "\u265F"); // ♟ Peón negro
        }

        // Filas 2-5 (filas 6-3 en notación) - Vacías
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                board.set(i, j, " ");
            }
        }

        // Fila 6 (fila 2 en notación) - Peones blancos
        for (int j = 0; j < 8; j++) {
            board.set(6, j, "\u2659"); // ♙ Peón blanco
        }

        // Fila 7 (fila 1 en notación) - Piezas blancas
        board.set(7, 0, "\u2656"); // ♖ Torre blanca
        board.set(7, 1, "\u2658"); // ♘ Caballo blanco
        board.set(7, 2, "\u2657"); // ♗ Alfil blanco
        board.set(7, 3, "\u2655"); // ♕ Reina blanca
        board.set(7, 4, "\u2654"); // ♔ Rey blanco
        board.set(7, 5, "\u2657"); // ♗ Alfil blanco
        board.set(7, 6, "\u2658"); // ♘ Caballo blanco
        board.set(7, 7, "\u2656"); // ♖ Torre blanca
    }

    // Método para imprimir el tablero con formato
    public void display() {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < 8; j++) {
                String piece = board.get(i, j);
                // Si es espacio, mostramos un punto para visualizar mejor
                if (piece.equals(" ")) {
                    // Alternar colores para el tablero (efecto visual)
                    if ((i + j) % 2 == 0) {
                        System.out.print("· ");
                    } else {
                        System.out.print("  ");
                    }
                } else {
                    System.out.print(piece + " ");
                }
            }
            System.out.println(" " + (8 - i));
        }
        System.out.println("  a b c d e f g h");
    }

    // Método main para probar el tablero
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.display();
    }
}