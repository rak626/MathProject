public class Matrix {
    private final int[][] array;

    Matrix() {
        array = new int[MathConstant.ROW_SIZE][MathConstant.COL_SIZE];
    }

    public int[][] getArray() {
        return array;
    }

    public void setArray(int row, int col) {
        //validate
        if (MathUtils.validatePosition(row, col)) {
            array[row][col] = 1;
        }
    }

    public void printMatrix() {
        for (int[] rows : this.array) {
            for (int cols : rows) {
                System.out.print(cols + " ");
            }
            System.out.println();
        }
    }
}
