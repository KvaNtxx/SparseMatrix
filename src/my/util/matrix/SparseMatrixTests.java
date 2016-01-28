package my.util.matrix;

import java.util.Iterator;
import java.util.Random;
import my.util.matrix.behavior.SparseMartixSupportImpl;
import my.util.matrix.behavior.SparseMatrixImpl;

public class SparseMatrixTests
{
    public static void main(String... args) throws Exception {
        /*
        SparseMatrixImpl mtrx = generateSparseMatrix(5,5,10);
        SparseMatrixImpl mtrx2 = generateSparseMatrix(5,5,10);
        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl result = mtrx.multiply(mtrx2);

        mtrx.print();
        mtrx2.print();
        result.print();
        System.out.println(result.getNotNullElementsCount());*/
        /*
        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl mtrx3 = generateSparseMatrix(5,4, 10);
        SparseMatrixImpl mtrx4 = support.fromStream(support.toStream(mtrx3));
        mtrx3.print();
        mtrx4.print();
*/      long t1 = System.currentTimeMillis();
        SparseMatrixImpl mtrx4 = generateSparseMatrix(5, 5, 10);
        SparseMatrixImpl mtrx5 = generateSparseMatrix(5, 5, 10);
        mtrx4.print();
        mtrx5.print();
        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
//        SparseMatrixImpl mtrx4 = support.fromStream(support.toStream(mtrx5));

        SparseMatrixImpl mtrx6 = support.multiply(mtrx4, mtrx5);
        mtrx6.print();
        long t2 = System.currentTimeMillis();
        System.out.println((t2-t1)/1000);
        /*
        Iterator<Integer> iter = mtrx5.iterator();
        iter.hasNext();
        while(iter.hasNext())
            System.out.print(iter.next() + "\t");
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