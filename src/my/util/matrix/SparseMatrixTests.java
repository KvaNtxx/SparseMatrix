package my.util.matrix;

import java.util.Random;
import my.util.matrix.behavior.SparseMartixSupportImpl;
import my.util.matrix.behavior.SparseMatrixImpl;

public class SparseMatrixTests
{
    public static void main(String... args) throws Exception {
        SparseMatrixImpl mtrx = generateSparseMatrix(1000000, 1000);
        SparseMatrixImpl mtrx2 = generateSparseMatrix(1000000, 1000);
        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl result = support.multiply(mtrx,mtrx2);
    //    mtrx.print();
    //    mtrx2.print();
    //    result.print();
        SparseMatrixImpl mtrx3 = generateSparseMatrix(5, 10);
        SparseMatrixImpl mtrx4 = support.fromStream(support.toStream(mtrx3));
        mtrx3.print();
        mtrx4.print();

    }

    static SparseMatrixImpl generateSparseMatrix(int n, int maxNotNullElements)
    {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SparseMatrixImpl matrix = new SparseMatrixImpl(n);
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < maxNotNullElements; i++)
        {
            matrix.put(random.nextInt(n),random.nextInt(n),random.nextInt(10));
        }
        System.out.println("Generation complete");
        return matrix;
    }
}