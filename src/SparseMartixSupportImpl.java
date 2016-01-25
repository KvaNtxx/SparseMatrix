import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by KvaNt on 23.01.2016.
 */
public class SparseMartixSupportImpl implements SparseMatrixSupport<SparseMatrixImpl>{
    @Override
    public Stream<Integer> toStream(SparseMatrixImpl matrix) {
        int N = matrix.getN();
        List<Integer> streamList = new ArrayList<>(N*N);
        streamList.add(N);
        streamList.add(N);
        for (int row = 0; row < N; row++)
        {
            for(int column = 0; column < N; column++)
            {
                streamList.add((Integer) matrix.get(row,column));
            }
        }

        return streamList.stream();
    }

    @Override
    public SparseMatrixImpl fromStream(Stream<Integer> stream) {
        List<Integer> list = new ArrayList<>();
        stream.forEachOrdered(list::add);
        int N = list.get(0);
        SparseMatrixImpl matrix = new SparseMatrixImpl(N);
        for(int row = 0; row < N; row++)
        {
            for(int column = 0; column < N; column++)
            {
                matrix.put(row, column , list.get((row*N + column+2)));
            }
        }
        return matrix;
    }

    @Override
    public SparseMatrixImpl multiply(SparseMatrixImpl first, SparseMatrixImpl second)
    {
        return first.multiply(second);
    }
}
