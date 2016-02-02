package my.util.matrix.behavior;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SparseMatrixImpl implements Iterable<Integer>{
    final private Map<Integer,Map<Integer,Integer>> rows;
    final private int rowCount;
    final private int columnCount;


    public SparseMatrixImpl(int rowCount,int columnCount) {
        if(rowCount < 1 || columnCount < 1)
            throw new IllegalArgumentException("The number of rows and columns must be greater than 0");
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        rows = new HashMap<>(rowCount);
    }

    public void put(int row, int column, Integer value) {
        if(value == null)
            return;
        checkBoundaries(row, column);
        if(rows.get(row) == null)
            rows.put(row,new HashMap<>());
        rows.get(row).put(column, value);
    }

    public Integer get(int row, int column) {
        checkBoundaries(row, column);
        if(rows.get(row)==null)
            return null;
        return rows.get(row).get(column);
    }

    public void delete(int row, int column) {
        checkBoundaries(row, column);
        if(rows.get(row)==null)
            return;
        rows.get(row).remove(column);
        if(rows.get(row).isEmpty())
            rows.remove(row);
    }

    public int getNotNullElementsCount() {
        int size = 0;
        for(Integer row: rows.keySet()) {
            size += rows.get(row).size();
        }
        return size;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
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
            throw new IllegalArgumentException
                    ("Number of columns of the first matrix must be the same as number of rows of the second matrix");
        SparseMatrixImpl secondTrans = second.getTranspose();
        SparseMatrixImpl resultMatrix = new SparseMatrixImpl(rowCount,second.columnCount);
        for(Integer rowIndex : rows.keySet()) {
            for (Integer columnIndex:secondTrans.rows.keySet()) {
                try {
                    resultMatrix.put(rowIndex, columnIndex, vectorMultiply(rows.get(rowIndex), secondTrans.rows.get(columnIndex)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMatrix;
    }

    public SparseMatrixImpl getTranspose() {
        SparseMatrixImpl trans = new SparseMatrixImpl(columnCount, rowCount);
        for (Integer rowIndex: rows.keySet()) {
            for (Integer columnIndex: rows.get(rowIndex).keySet()) {
                trans.put(columnIndex, rowIndex, rows.get(rowIndex).get(columnIndex));
            }
        }
        return trans;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private long request = 0;
            private int currentRow = 0;
            private int currentColumn = 0;

            @Override
            public boolean hasNext() {
                return currentRow < rowCount && currentColumn < columnCount;
            }

            @Override
            public Integer next() {
                request++;
                if (request == 1)
                    return rowCount;
                if (request == 2)
                    return  columnCount;
                if(currentColumn == columnCount -1) {
                    Integer res = get(currentRow++, currentColumn);
                    currentColumn = 0;
                    return res;
                }
                else
                    return get(currentRow,currentColumn++);
            }
        };
    }

    private Integer vectorMultiply(Map<Integer, Integer> first,Map<Integer, Integer> second) {
        if(Collections.disjoint(first.keySet(),second.keySet()))
            return null;
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
            throw new IndexOutOfBoundsException("Matrix out of bounds");
    }


}