package datastructures.nonlinnear;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Traversable<T> extends Iterable<T> {
    @NotNull
    @Override
    default Iterator<T> iterator() {
        return iterator(Traversal.PREORDER);
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        iterator().forEachRemaining(action);
    }

    Iterator<T> iterator(Traversal traversal);
    enum Traversal {
        INORDER,
        PREORDER,
        POSTORDER
    }
}
