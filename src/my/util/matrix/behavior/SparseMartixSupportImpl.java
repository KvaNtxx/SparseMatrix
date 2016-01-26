package my.util.matrix.behavior;

import my.util.matrix.SparseMatrixSupport;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SparseMartixSupportImpl implements SparseMatrixSupport<SparseMatrixImpl> {
    @Override
    public Stream<Integer> toStream(SparseMatrixImpl matrix) {

        Iterator<Integer> sourceIterator = matrix.iterator();

        Iterable<Integer> iterable = () -> sourceIterator;
        Stream<Integer> targetStream = StreamSupport.stream(iterable.spliterator(), false);

        return targetStream;
    }

    @Override
    public SparseMatrixImpl fromStream(Stream<Integer> stream) {

        final SparseMatrixImpl[] matrix = {null};
        final long[] request = {0};
        stream.forEach(e -> {
            if(request[0]++ < 2) {
                matrix[0] = new SparseMatrixImpl(e);
            }
            else {
                if(e!=null)
                    matrix[0].put((int)(request[0]-3)/matrix[0].getN(),(int)(request[0]-3)%matrix[0].getN(),e);
            }
        });
        return matrix[0];
    }

    @Override
    public SparseMatrixImpl multiply(SparseMatrixImpl first, SparseMatrixImpl second) {
        return first.multiply(second);
    }
}
