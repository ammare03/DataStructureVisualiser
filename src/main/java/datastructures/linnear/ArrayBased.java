package datastructures.linnear;

import exceptions.OverflowException;
import exceptions.UnderflowException;

public interface ArrayBased<T> {
    String getIndexState();
    String getIndexStateAfterPop() throws UnderflowException;
    String getIndexStateAfterPush(T data) throws OverflowException;
}
