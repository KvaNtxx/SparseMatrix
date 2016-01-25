/**
 * Created by KvaNt on 23.01.2016.
 */
public interface Matrix<M>{

    void put(int row, int column, Integer value);

    Integer get(int row, int column);

    void delete(int row, int column);

    int getNotNullElementsCount();

    int getN();

    M getTranspose();

    void print();

    M multiply(M second);
}
