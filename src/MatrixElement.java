public class MatrixElement {
    String coEff;
    Matrix matrix;

    public MatrixElement(String coEff, Matrix matrix) {
        this.coEff = coEff;
        this.matrix = matrix;
    }

    public String getCoEff() {
        return coEff;
    }

    public Matrix getMatrix() {
        return matrix;
    }
}
