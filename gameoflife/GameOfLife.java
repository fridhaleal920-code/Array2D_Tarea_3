package gameoflife;

import adt.Array2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameOfLife {
    private Array2D<Integer> grid;      // 1 = viva, 0 = muerta
    private int rows;
    private int cols;
    private int generation;

    // Constructor que inicializa desde CSV
    public GameOfLife(String csvFile, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.generation = 0;
        this.grid = new Array2D<>(rows, cols);
        initializeFromCSV(csvFile);
    }

    // Inicializar desde archivo CSV
    private void initializeFromCSV(String csvFile) {
        // Inicializar todo a 0 (muerto)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.set(i, j, 0);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < rows) {
                String[] values = line.split(",");
                for (int col = 0; col < Math.min(values.length, cols); col++) {
                    int value = Integer.parseInt(values[col].trim());
                    grid.set(row, col, value);
                }
                row++;
            }
            System.out.println("Configuración inicial cargada desde: " + csvFile);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
            System.out.println("Usando configuración por defecto (Glider pattern)");
            setupDefaultConfiguration();
        } catch (NumberFormatException e) {
            System.out.println("Error en formato del CSV: " + e.getMessage());
            System.out.println("Usando configuración por defecto (Glider pattern)");
            setupDefaultConfiguration();
        }
    }

    // Configuración por defecto (Glider pattern)
    private void setupDefaultConfiguration() {
        System.out.println("Configuración por defecto: Glider pattern");
        // Glider pattern en una matriz 3x3
        if (rows >= 3 && cols >= 3) {
            grid.set(0, 1, 1);
            grid.set(1, 2, 1);
            grid.set(2, 0, 1);
            grid.set(2, 1, 1);
            grid.set(2, 2, 1);
        }
    }

    // Contar vecinos vivos de una célula
    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        // Revisar las 8 celdas vecinas
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Saltar la célula misma
                int newRow = row + i;
                int newCol = col + j;
                // Verificar límites (fuera del tablero se considera muerto)
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    count += grid.get(newRow, newCol);
                }
            }
        }
        return count;
    }

    // Calcular siguiente generación
    public void nextGeneration() {
        Array2D<Integer> newGrid = new Array2D<>(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                int current = grid.get(i, j);

                // Aplicar reglas del Juego de la Vida
                if (current == 1) {
                    // Reglas para células vivas
                    if (liveNeighbors == 2 || liveNeighbors == 3) {
                        newGrid.set(i, j, 1);  // Sobrevive
                    } else {
                        newGrid.set(i, j, 0);  // Muere por soledad o sobrepoblación
                    }
                } else {
                    // Reglas para células muertas
                    if (liveNeighbors == 3) {
                        newGrid.set(i, j, 1);  // Nacimiento
                    } else {
                        newGrid.set(i, j, 0);  // Permanece muerta
                    }
                }
            }
        }

        grid = newGrid;
        generation++;
    }

    // Mostrar el tablero en consola
    public void display() {
        System.out.println("Generación: " + generation);
        System.out.print("   ");
        for (int j = 0; j < cols; j++) {
            System.out.print((j < 10 ? " " : "") + j + " ");
        }
        System.out.println();
        System.out.println("   " + "-".repeat(Math.max(0, cols * 3)));

        for (int i = 0; i < rows; i++) {
            System.out.print((i < 10 ? " " : "") + i + " |");
            for (int j = 0; j < cols; j++) {
                int value = grid.get(i, j);
                // Usar caracteres Unicode para mejor visualización
                if (value == 1) {
                    System.out.print(" ■ ");
                } else {
                    System.out.print(" · ");
                }
            }
            System.out.println("|");
        }
        System.out.println("   " + "-".repeat(Math.max(0, cols * 3)));
        System.out.println();
    }

    // Método para simular generaciones
    public void simulate(int generations) {
        System.out.println("=== JUEGO DE LA VIDA ===");
        System.out.println("Tablero " + rows + "x" + cols);
        display();

        for (int gen = 1; gen <= generations; gen++) {
            System.out.println("--- Generación " + gen + " ---");
            nextGeneration();
            display();

            // Pequeña pausa para visualizar (opcional)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("=== FIN DE LA SIMULACIÓN ===");
    }

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getGeneration() { return generation; }
    public Array2D<Integer> getGrid() { return grid; }
}