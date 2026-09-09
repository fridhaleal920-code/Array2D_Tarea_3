package main;

import chess.ChessBoard;
import gameoflife.GameOfLife;
import gui.GameOfLifeGUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== PARTE A: TABLERO DE AJEDREZ ===\n");
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.display();

        System.out.println("\n\n=== PARTE B: JUEGO DE LA VIDA ===\n");
        System.out.println("Abriendo interfaz gráfica...");

        GameOfLifeGUI.main(args);
    }
}