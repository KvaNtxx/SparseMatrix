import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by n.litvyak on 22.01.2016.
 */

class Matrix
{
    ArrayList<HashMap<Integer,Integer>> rows= new ArrayList<>();

    final int N;

    Matrix(int n)
    {
        N = n;
        int i=0;
        while(i < N)
        {
            rows.add(new HashMap<Integer, Integer>());
            i++;
        }
    }

    void put(int row, int column, Object value)
    {
        rows.get(row).put(column, (Integer) value);
    }

    Object get(int row, int column)
    {
        return rows.get(row).get(column);
    }

    void print()
    {
        System.out.println("the sparse Matrix is ");
        for (int rowindex = 0; rowindex < N; rowindex++) {
            for (int colindex = 0; colindex < N; colindex++) {
                System.out.print(get(rowindex, colindex) + "\t");
            }
            System.out.println();
        }
    }

    public static Matrix multiply(Matrix first,Matrix second)
    {
        return null;
    }

    Matrix transponate()
    {
        Matrix trans = new Matrix(N);
        for (int rowindex = 0; rowindex < N; rowindex++) {
            for (int colindex = 0; colindex < rows.get(rowindex).size(); colindex++) {
                trans.put(colindex, rowindex, rows.get(rowindex).get(colindex));
            }
        }
        return trans;
    }
}

public class MatrixMain
{
    public static void main(String... args) throws InterruptedException {
        Matrix matrix = new Matrix(100000);
        matrix.put(0, 2, 1);

        Matrix trans = matrix.transponate();
        Thread.sleep(100000);
    }
}

