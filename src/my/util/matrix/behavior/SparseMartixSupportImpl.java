package my.util.matrix.behavior;

import my.util.matrix.SparseMatrixSupport;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class SparseMartixSupportImpl implements SparseMatrixSupport<SparseMatrixImpl> {
    @Override
    public Stream<Integer> toStream(SparseMatrixImpl matrix) {
        Stream<Integer> targetStream = Stream.generate(new SparseMatrixSuppier(matrix));
        return targetStream.limit( (long) matrix.getRowCount()*(long) matrix.getColumnCount() + 2L);
    }

    @Override
    public SparseMatrixImpl fromStream(Stream<Integer> stream) {
 //       stream.limit(10).forEach(e->System.out.print(e + "\t"));
        final SparseMatrixImpl[] matrix = {null};
        stream.forEach(new Consumer<Integer>() {
            long request = 0;
            int rowCount, columnCount;
            int currentRow = 0;
            int currentColumn = 0;

            @Override
            public void accept(Integer integer) {
                request++;
                if (request == 1) {
                    rowCount = integer;
                    return;
                }
                if (request == 2) {
                    columnCount = integer;
                    return;
                }
                if (request == 3) {
                    matrix[0] = new SparseMatrixImpl(rowCount, columnCount);
                    put(integer);
                } else {
                    put(integer);
                }
            }

            public void put(Integer value) {
                if (currentColumn == columnCount - 1) {
                    matrix[0].put(currentRow++, currentColumn, value);
                    currentColumn = 0;
                } else matrix[0].put(currentRow, currentColumn++, value);
            }
        });
        return matrix[0];
    }

    @Override
    public SparseMatrixImpl multiply(SparseMatrixImpl first, SparseMatrixImpl second) {
        return first.multiply(second);
    }
}