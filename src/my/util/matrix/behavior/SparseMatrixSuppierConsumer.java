package my.util.matrix.behavior;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by n.litvyak on 27.01.2016.
 */
public class SparseMatrixSuppierConsumer implements Supplier<Integer>{

    final private SparseMatrixImpl matrix;
    final private int columnCount;
    final private int rowCount;
    private int request = 0;
    private int currentColumn = 0;
    private int currentRow = 0;

    SparseMatrixSuppierConsumer(SparseMatrixImpl matrix)
    {
        this.matrix = matrix;
        rowCount = matrix.getRowCount();
        columnCount = matrix.getColumnCount();
    }

    @Override
    public Integer get() {
        Integer res;
        request++;
        if(request == 1)
            return rowCount;
        if(request == 2)
            return  columnCount;

        if (currentColumn == columnCount - 1) {
            res = matrix.get(currentRow++, currentColumn);
            currentColumn = 0;
        }
        else
           res = matrix.get(currentRow,currentColumn++);
        return res;
    }

}
