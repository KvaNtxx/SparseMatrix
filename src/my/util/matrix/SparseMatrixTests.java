package my.util.matrix;

import java.util.Iterator;
import java.util.Random;
import my.util.matrix.behavior.SparseMartixSupportImpl;
import my.util.matrix.behavior.SparseMatrixImpl;

public class SparseMatrixTests
{
    public static void main(String... args) throws Exception {

        long t1 = System.currentTimeMillis();

        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl mtrx1 = generateSparseMatrix(1000000, 1000000, 1000);
        SparseMatrixImpl mtrx2 = generateSparseMatrix(1000000, 1000000, 1000);
//        mtrx1.print();
//        mtrx2.print();
        SparseMatrixImpl mtrx3 = support.multiply(mtrx1, mtrx2);
//        mtrx3.print();
        SparseMatrixImpl mtrx4 = generateSparseMatrix(10000, 10000, 1000);
        SparseMatrixImpl mtrx5 = support.fromStream(support.toStream(mtrx4));
//        mtrx5.print();
        long t2 = System.currentTimeMillis();
        System.out.println("Execution time: " + (t2-t1)/1000 + " sec " );

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