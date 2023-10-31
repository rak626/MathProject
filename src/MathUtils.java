import java.util.*;

public class MathUtils {
    public static List<List<String>> coEffsRespectToDegree;
    public static Map<String, List<Integer>> degreeMap;
    public static Map<String, Matrix> matrixMap;
    public static Map<Integer, List<MatrixElement>> degreeMatrixMap;

    public static void scanRequiredValues() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Row length:  ");
        MathConstant.ROW_SIZE = scanner.nextInt();
        System.out.println("Enter Col length:  ");
        MathConstant.COL_SIZE = scanner.nextInt();
        System.out.println("Enter Degree:  ");
        MathConstant.DEGREE = scanner.nextInt();

        //generate degree map based on given degree
        MathUtils.generateDegreeMap();

        System.out.println("Enter Input Matrix .......");
        for (int i = 0; i < MathConstant.ROW_SIZE; i++) {
            for (int j = 0; j < MathConstant.COL_SIZE; j++) {
                String curValue = scanner.next();
                if(curValue.contains("x") || curValue.contains("y")){
                    MathUtils.degreeMap.get(curValue).add(i);
                    MathUtils.degreeMap.get(curValue).add(j);
                } else{
                    MathConstant.MATRIX_CONSTANT_VALUE = curValue;
                    MathUtils.degreeMap.get("1").add(i);
                    MathUtils.degreeMap.get("1").add(j);
                }
            }
        }
    }
    public static void sampleScanner() {
        MathConstant.ROW_SIZE = 2;
        MathConstant.COL_SIZE = 3;
        MathConstant.DEGREE = 3;

        MathUtils.generateDegreeMap();

        MathUtils.degreeMap.get("x2y").add(0);
        MathUtils.degreeMap.get("x2y").add(0);

        MathUtils.degreeMap.get("y2").add(0);
        MathUtils.degreeMap.get("y2").add(1);

        MathUtils.degreeMap.get("x2").add(0);
        MathUtils.degreeMap.get("x2").add(2);

        MathUtils.degreeMap.get("x").add(1);
        MathUtils.degreeMap.get("x").add(0);

        MathUtils.degreeMap.get("1").add(1);
        MathUtils.degreeMap.get("1").add(1);

        MathUtils.degreeMap.get("y").add(1);
        MathUtils.degreeMap.get("y").add(2);

    }

    /*
     * function responsible for generating all coEffs respect to given degree
     */
    public static void generateCoEff() {
        coEffsRespectToDegree = new ArrayList<>();
        for (int i = MathConstant.DEGREE; i >= 0; i--) {
            List<String> coEffs = new ArrayList<>();
            String coEff = "";
            int xDeg = i, yDeg = 0;
            while (!(xDeg == 0 && yDeg > i)) {
                coEff = String.format((xDeg == 0 ? "" : (xDeg == 1) ? "x" : "x" + xDeg) + (yDeg == 0 ? "" : (yDeg == 1) ? "y" : "y" + yDeg));
                if (xDeg > 0) xDeg--;
                yDeg++;
                if (coEff.isEmpty()) coEffs.add("1");
                else coEffs.add(coEff);
            }
            coEffsRespectToDegree.add(coEffs);
        }
        Collections.reverse(coEffsRespectToDegree);
    }

    /*
     * function responsible for generating DegreeMap
     * Degree-Map tracks which position this particular matrix element are there
     * */
    public static void generateDegreeMap() {
        degreeMap = new TreeMap<>();
        MathUtils.generateCoEff();
        for (List<String> coEffs : coEffsRespectToDegree) {
            for (String coEff : coEffs) {
                degreeMap.put(coEff, new ArrayList<>());
            }
        }
    }

    /*
     * utility function for checking given row and col
     * is inside matrix scope
     */
    public static boolean validatePosition(int row, int col) {
        return row >= 0 && row < MathConstant.ROW_SIZE && col >= 0 && col < MathConstant.COL_SIZE;
    }

    /*
     * function responsible for generating matrix
     *  as well as mark them as per their correct coefficient
     * */
    public static void generateMatrix() {
        matrixMap = new TreeMap<>();

        for (Map.Entry<String, List<Integer>> dMap : MathUtils.degreeMap.entrySet()) {
            String coefficient = dMap.getKey();
            Matrix matrix = new Matrix();
            if (!dMap.getValue().isEmpty()) {
                int row = dMap.getValue().get(0);
                int col = dMap.getValue().get(1);
                matrix.setArray(row, col);
            }
            matrixMap.put(coefficient, matrix);

        }
    }


    /*
     * utility function responsible for Mapping Matrix
     * with their coefficient as well as their Degree
     * */
    public static void generateDegreeMatrixMap() {
        degreeMatrixMap = new TreeMap<>();

        for (int i = 0; i <= MathConstant.DEGREE; i++) {
            List<String> coEffs = coEffsRespectToDegree.get(i);
            List<MatrixElement> matrixList = new ArrayList<>();
            for (var coEff : coEffs) {
                MatrixElement matrixElement = new MatrixElement(coEff, matrixMap.get(coEff));
                matrixList.add(matrixElement);
            }
            degreeMatrixMap.put(i, matrixList);
        }
    }

    /*
     * utility function helping to print all the matrix
     * based on given degree inputs
     * */
    public static void printRequiredDegreeMatrix(int degree) {
        if (degreeMatrixMap.containsKey(degree)) {
            List<MatrixElement> matrixElements = degreeMatrixMap.get(degree);
            for (var ele : matrixElements) {
                String coEff = ele.getCoEff();
                Matrix matrix = ele.getMatrix();
                System.out.println((coEff.equalsIgnoreCase("1") ? MathConstant.MATRIX_CONSTANT_VALUE : coEff) + " ");
                matrix.printMatrix();
            }
        } else {
            System.out.println("invalid degree input.....");
        }
    }

    public static void printAllDegree() {
        for (int i = 0; i <= MathConstant.DEGREE; i++) {
            printRequiredDegreeMatrix(i);
        }
    }


}
