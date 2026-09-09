package adt;

public class Array2D<T> {
    private T[][] data;
    private int rows;
    private int cols;

    // Constructor
    @SuppressWarnings("unchecked")
    public Array2D(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = (T[][]) new Object[rows][cols];
    }

    // Obtener elemento
    public T get(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return data[row][col];
    }

    // Establecer elemento
    public void set(int row, int col, T value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        data[row][col] = value;
    }

    // Obtener número de filas
    public int getRows() {
        return rows;
    }

    // Obtener número de columnas
    public int getCols() {
        return cols;
    }

    // Método para imprimir el Array2D
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}