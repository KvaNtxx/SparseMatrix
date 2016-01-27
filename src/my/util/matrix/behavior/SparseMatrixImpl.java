package my.util.matrix.behavior;

import java.util.*;

public class SparseMatrixImpl implements Iterable<Integer>{
    final private List<Map<Integer,Integer>> rows;
    final private int rowCount;
    final private int columnCount;


    public SparseMatrixImpl(int rowCount,int columnCount) {
        if(rowCount < 1 || columnCount < 1)
            throw new RuntimeException("Matrix size must be greater than 0");
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        rows = new ArrayList<>(rowCount);
        int i = 0;
        while(i < rowCount) {
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

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCountCount() {
        return columnCount;
    }

    public void print() {
        System.out.println("The sparse matrix is ");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                System.out.print(get(rowIndex, columnIndex) + "\t");
            }
            System.out.println();
        }
    }

    public SparseMatrixImpl multiply( SparseMatrixImpl second) {
        if(second.rowCount != columnCount)
            throw new RuntimeException("Second matrix must be the same size as first");
        SparseMatrixImpl secondTrans = second.getTranspose();
        SparseMatrixImpl resultMatrix = new SparseMatrixImpl(rowCount,second.columnCount);
        for(int rowIndex = 0;rowIndex < rowCount;rowIndex++) {
            if(rows.get(rowIndex).size() == 0)
                continue;
            for (int columnIndex = 0; columnIndex < second.columnCount;columnIndex++) {
                if(secondTrans.rows.get(columnIndex).size() == 0)
                    continue;
                resultMatrix.put(rowIndex, columnIndex, vectorMultiply(rows.get(rowIndex), secondTrans.rows.get(columnIndex)));
            }
        }
        return resultMatrix;
    }

    public SparseMatrixImpl getTranspose() {
        SparseMatrixImpl trans = new SparseMatrixImpl(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
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
                return currentRow < rowCount && currentColumn < rowCount;
            }

            @Override
            public Integer next() {
                if (request < 3)
                    return rowCount;
                if(currentColumn == rowCount -1) {
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
        if(row < 0 || column < 0 || row >= rowCount || column >= columnCount)
            throw new RuntimeException("Matrix out of bounds");
    }
}