import java.util.Random;
import java.util.stream.Stream;

public class MatrixMain
{
    public static void main(String... args) throws Exception {
/*         Matrix matrix = new Matrix(3);
       matrix.put(0, 2, 1);
        matrix.put(0, 1, 3);
        matrix.put(1, 2, 6);
        matrix.print();
        Matrix trans = matrix.transpose();
        trans.print();

        Matrix result = Matrix.multiply(matrix, trans);
        result.print();
*/

        /*
        SparseMatrixImpl mtrx = generate(10);

        SparseMatrixImpl mtrx2 = generate(10);
        SparseMatrixImpl result = new SparseMartixSupportImpl().multiply(mtrx,mtrx2);
        mtrx.print();
        mtrx2.print();
        result.print();
        */

/*
        SparseMatrixImpl mtrx = generate(20000);
        SparseMartixSupportImpl support= new SparseMartixSupportImpl();
     //   mtrx.print();
        SparseMatrixImpl matrix2 = support.fromStream(support.toStream(mtrx));
      //  matrix2.print();

*/

        SparseMatrixSupport<SparseMatrixImpl> support = new SparseMartixSupportImpl();
        SparseMatrixImpl mtrx1 = (SparseMatrixImpl) generate(1000000,1000);
        SparseMatrixImpl mtrx2 = (SparseMatrixImpl) generate(1000000,1000);
        SparseMatrixImpl res = support.multiply(mtrx1,mtrx2);
        System.out.println(res.getNotNullElementsCount());
      //  mtrx1.print();
      //  mtrx2.print();
      //  res.print();


/*
        SparseMatrixImpl matrix = new SparseMatrixImpl(3);
        matrix.put(0, 2, 1);
        matrix.put(0, 1, 3);
        matrix.put(1, 2, 6);
        matrix.print();
        matrix.delete(0,2);
        matrix.delete(0,2);
        matrix.print();
        */
   //     System.out.println(support.fromStream(support.toStream(mtrx1)));
    }

    static SparseMatrixImpl generate(int n, int z)
    {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SparseMatrixImpl matrix = new SparseMatrixImpl(n);
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < z; i++)
        {
            matrix.put(random.nextInt(n),random.nextInt(n),random.nextInt(n) + 1);
        }
        System.out.println("Generation complete");
        return matrix;
    }
}