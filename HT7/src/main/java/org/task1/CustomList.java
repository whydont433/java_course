package org.task1;

/**
 * Список элементов с доступом по индексу.
 */
public interface CustomList<E> extends Iterable<E> {

    /** Добавляет элемент в конец. */
    void add(E element);

    /** Возвращает элемент по индексу. */
    E get(int index);

    /** Удаляет и возвращает элемент по индексу, сдвигая хвост влево. */
    E remove(int index);

    /** Возвращает количество элементов. */
    int size();

    /** Возвращает true, если список пуст. */
    boolean isEmpty();
}