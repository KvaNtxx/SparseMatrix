package my.util.matrix;

import java.util.Random;
//import my.util.matrix.behavior.SparseMartixSupportImpl;
import my.util.matrix.behavior.SparseMatrixImpl;

public class SparseMatrixTests
{
    public static void main(String... args) throws Exception {
        SparseMatrixImpl mtrx = generateSparseMatrix(2,3,1000);
        SparseMatrixImpl mtrx2 = generateSparseMatrix(3,1,1000);
       // SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl result = mtrx.multiply(mtrx2);
        mtrx.print();
        mtrx2.print();
        result.print();
        /*
        SparseMatrixImpl mtrx3 = generateSparseMatrix(5, 10);
        SparseMatrixImpl mtrx4 = support.fromStream(support.toStream(mtrx3));
        mtrx3.print();
        mtrx4.print();
*/
    }

    static SparseMatrixImpl generateSparseMatrix(int rowCount,int columnCount,int maxNotNullElements)
    {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SparseMatrixImpl matrix = new SparseMatrixImpl(rowCount,columnCount);
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < maxNotNullElements; i++)
        {
            matrix.put(random.nextInt(rowCount),random.nextInt(columnCount),random.nextInt(10));
        }
        System.out.println("Generation complete");
        return matrix;
    }
}