import java.util.*;

class SparseMatrixImpl implements Iterable<Integer>{
    final private List<Map<Integer,Integer>> rows;
    final private int N;

    SparseMatrixImpl(int n) {
        if(n < 1)
            throw new RuntimeException("Matrix size must be greater than 0");
        N = n;
        rows = new ArrayList<>(N);
        int i = 0;
        while(i < N) {
            rows.add(new HashMap<>());
            i++;
        }
    }

    public void put(int row, int column, Integer value) {
        checkBoundaries(row, column);
        if(value != null && value != 0) {
            rows.get(row).put(column, value);
        }
    }

    public Integer get(int row, int column) {
        checkBoundaries(row, column);
        return rows.get(row).get(column);
    }

    public void delete(int row, int column) {
        checkBoundaries(row, column);
        rows.get(row).remove(column);

    }

    public int getNotNullElementsCount() {
        int size = 0;
        for(Map row: rows) {
            size += row.size();
        }
        return size;
    }

    public int getN() {
        return N;
    }

    public void print() {
        System.out.println("the sparse Matrix is ");
        for (int rowIndex = 0; rowIndex < N; rowIndex++) {
            for (int columnIndex = 0; columnIndex < N; columnIndex++) {
                System.out.print(get(rowIndex, columnIndex) + "\t");
            }
            System.out.println();
        }
    }

    public SparseMatrixImpl multiply( SparseMatrixImpl second) {
        if(second.N != N)
            throw new RuntimeException("Second matrix must be the same size as first");
        SparseMatrixImpl secondTrans = second.getTranspose();
        SparseMatrixImpl resultMatrix = new SparseMatrixImpl(N);
        for(int rowIndex = 0;rowIndex < N;rowIndex++) {
            if(rows.get(rowIndex).size() == 0)
                continue;
            for (int columnIndex = 0; columnIndex < N;columnIndex++) {
                if(secondTrans.rows.get(columnIndex).size() == 0)
                    continue;
                resultMatrix.put(rowIndex, columnIndex, vectorMultiply(rows.get(rowIndex), secondTrans.rows.get(columnIndex)));
            }
        }
        return resultMatrix;
    }

    public SparseMatrixImpl getTranspose() {
        SparseMatrixImpl trans = new SparseMatrixImpl(N);
        for (int rowIndex = 0; rowIndex < N; rowIndex++) {
            for (Integer colindex: rows.get(rowIndex).keySet()) {
                trans.put(colindex, rowIndex, rows.get(rowIndex).get(colindex));
            }
        }
        return trans;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int request = 0;
            private int currentRow = 0;
            private int currentColumn = 0;

            @Override
            public boolean hasNext() {
                request++;
                //             System.out.println(currentColumn + " " + currentRow);
                return currentRow < N && currentColumn < N;
            }

            @Override
            public Integer next() {
                if (request < 3)
                    return N;
                if(currentColumn == N-1) {
                    Integer res = get(currentRow++, currentColumn);
                    currentColumn = 0;
                    return res;
                }
                else
                    return get(currentRow,currentColumn++);
            }
        };
    }

    private int vectorMultiply(Map<Integer, Integer> first,Map<Integer, Integer> second) {
        int result = 0;
        for(Integer columnIndex: first.keySet()) {
            if(second.get(columnIndex)==null)
                continue;
            result += first.get(columnIndex) * second.get(columnIndex);
        }
        return result;
    }

    private void checkBoundaries(int row, int column) {
        if(row < 0 || column < 0 || row >= N || column >= N)
            throw new RuntimeException("Matrix out of bounds");
    }
}


