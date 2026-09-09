package gui;

import adt.Array2D;
import chess.ChessBoard;
import gameoflife.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLifeGUI extends JFrame {
    private GameOfLife game;
    private JPanel gridPanel;
    private JLabel generationLabel;
    private JButton nextButton;
    private JButton autoButton;
    private JButton resetButton;
    private Timer timer;
    private int cellSize = 30;

    public GameOfLifeGUI() {
        setTitle("Juego de la Vida - Array2D ADT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializar el juego (10x10, desde config.csv)
        game = new GameOfLife("config.csv", 10, 10);

        // Panel superior con controles
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Panel central con el tablero
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));
        add(gridPanel, BorderLayout.CENTER);

        // Panel inferior con información
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.SOUTH);

        // Dibujar el tablero inicial
        updateGrid();

        // Timer para animación automática
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGeneration();
            }
        });

        // Mostrar también el tablero de ajedrez en una ventana separada
        showChessBoard();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        nextButton = new JButton("Siguiente Generación");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGeneration();
            }
        });

        autoButton = new JButton("Auto Play");
        autoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    autoButton.setText("Auto Play");
                } else {
                    timer.start();
                    autoButton.setText("Detener");
                }
            }
        });

        resetButton = new JButton("Reiniciar");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    autoButton.setText("Auto Play");
                }
                game = new GameOfLife("config.csv", 10, 10);
                updateGrid();
            }
        });

        panel.add(nextButton);
        panel.add(autoButton);
        panel.add(resetButton);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        generationLabel = new JLabel("Generación: 0");
        JLabel infoLabel = new JLabel(" | ■ = Viva  · = Muerta");
        JLabel rowsLabel = new JLabel(" | Filas: 10 | Columnas: 10");

        panel.add(generationLabel);
        panel.add(infoLabel);
        panel.add(rowsLabel);

        return panel;
    }

    private void updateGrid() {
        gridPanel.removeAll();
        Array2D<Integer> grid = game.getGrid();

        for (int i = 0; i < game.getRows(); i++) {
            for (int j = 0; j < game.getCols(); j++) {
                JPanel cell = new JPanel();
                int value = grid.get(i, j);

                if (value == 1) {
                    cell.setBackground(Color.BLACK);
                    cell.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                    JLabel label = new JLabel("■");
                    label.setForeground(Color.WHITE);
                    label.setFont(new Font("Arial", Font.BOLD, 14));
                    cell.add(label);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

                    JLabel label = new JLabel("·");
                    label.setForeground(Color.LIGHT_GRAY);
                    label.setFont(new Font("Arial", Font.PLAIN, 10));
                    cell.add(label);
                }

                gridPanel.add(cell);
            }
        }

        generationLabel.setText("Generación: " + game.getGeneration());
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void nextGeneration() {
        game.nextGeneration();
        updateGrid();
    }

    private void showChessBoard() {
        JFrame chessFrame = new JFrame("Tablero de Ajedrez");
        chessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chessFrame.setSize(500, 550);
        chessFrame.setLocationRelativeTo(null);

        JPanel chessPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String[][] pieces = getPiecesArray();

                int cellSize = 50;
                int startX = 20;
                int startY = 30;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if ((i + j) % 2 == 0) {
                            g.setColor(new Color(240, 217, 181));
                        } else {
                            g.setColor(new Color(181, 136, 99));
                        }
                        g.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);

                        String piece = pieces[i][j];
                        if (!piece.equals(" ")) {
                            g.setColor(Color.BLACK);
                            g.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
                            g.drawString(piece, startX + j * cellSize + 12, startY + i * cellSize + 35);
                        }
                    }
                }

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                for (int i = 0; i < 8; i++) {
                    g.drawString(String.valueOf(8 - i), 5, startY + i * cellSize + 30);
                    g.drawString(String.valueOf(8 - i), startX + 8 * cellSize + 10, startY + i * cellSize + 30);
                    char letter = (char) ('a' + i);
                    g.drawString(String.valueOf(letter), startX + i * cellSize + 20, startY + 8 * cellSize + 20);
                }
            }
        };

        chessFrame.add(chessPanel);
        chessFrame.setVisible(true);
    }

    private String[][] getPiecesArray() {
        String[][] pieces = new String[8][8];

        pieces[0][0] = "\u265C"; pieces[0][1] = "\u265E"; pieces[0][2] = "\u265D";
        pieces[0][3] = "\u265B"; pieces[0][4] = "\u265A"; pieces[0][5] = "\u265D";
        pieces[0][6] = "\u265E"; pieces[0][7] = "\u265C";

        for (int j = 0; j < 8; j++) {
            pieces[1][j] = "\u265F";
        }

        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = " ";
            }
        }

        for (int j = 0; j < 8; j++) {
            pieces[6][j] = "\u2659";
        }

        pieces[7][0] = "\u2656"; pieces[7][1] = "\u2658"; pieces[7][2] = "\u2657";
        pieces[7][3] = "\u2655"; pieces[7][4] = "\u2654"; pieces[7][5] = "\u2657";
        pieces[7][6] = "\u2658"; pieces[7][7] = "\u2656";

        return pieces;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameOfLifeGUI().setVisible(true);
            }
        });
    }
}