import java.util.*;

class SparseMatrixImpl implements Iterable<Integer>{
    private List<Map<Integer,Integer>> rows;
    private int N;

    SparseMatrixImpl(int n)
    {
        if(n < 1)
            throw new RuntimeException("Matrix size must be greater than 0");
        N = n;

        rows = new ArrayList<>(N);
        int i = 0;
        while(i < N)
        {
            rows.add(new HashMap<>());
            i++;
        }
    }

    public void put(int row, int column, Integer value)
    {
        if(value != null && value != 0) {
            rows.get(row).put(column, value);
        }
    }
/*
    public void push(Integer value)
    {
        if(validForPush && currentColumn < N && currentRow < N) {
            if(currentColumn == N - 1) {
                put(currentRow++, currentColumn, value);
                currentColumn = 0;
            }
            else
                put (currentRow, currentColumn++, value);
        }
    }
*/
    public Integer get(int row, int column)
    {
        return rows.get(row).get(column);
    }

    public void delete(int row, int column) {
         rows.get(row).remove(column);

    }

    public int getNotNullElementsCount() {
        int size = 0;
        for(Map row: rows)
        {
            size += row.size();
        }
        return size;
    }

    public int getN() {
        return N;
    }

    public void print()
    {
        System.out.println("the sparse Matrix is ");
        for (int rowindex = 0; rowindex < N; rowindex++) {
            for (int colindex = 0; colindex < N; colindex++) {
                System.out.print(get(rowindex, colindex) + "\t");
            }
            System.out.println();
        }
    }

    public SparseMatrixImpl multiply( SparseMatrixImpl second)
    {
        SparseMatrixImpl secondTrans = second.getTranspose();
        SparseMatrixImpl resultMatrix = new SparseMatrixImpl(N);
        for(int i = 0;i < N;i++)
        {
            if(rows.get(i).size() == 0)
                continue;
            for (int b = 0; b < N;b++)
            {

                if(secondTrans.rows.get(b).size() == 0)
                    continue;
                resultMatrix.put(i,b,vectorMultiply(rows.get(i),secondTrans.rows.get(b)));

            }
        }
        return resultMatrix;
    }

    private int vectorMultiply(Map<Integer, Integer> first,Map<Integer, Integer> second)
    {

        int i = 0;
        for(Integer column: first.keySet())
        {
            if(second.get(column)==null)
                continue;
            i += first.get(column) * second.get(column);
        }
        return i;
    }

    public SparseMatrixImpl getTranspose()
    {
        SparseMatrixImpl trans = new SparseMatrixImpl(N);
        for (int rowindex = 0; rowindex < N; rowindex++) {
            for (Integer colindex: rows.get(rowindex).keySet()) {
                trans.put(colindex, rowindex, rows.get(rowindex).get(colindex));
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

}


