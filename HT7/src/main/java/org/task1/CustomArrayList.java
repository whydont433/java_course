package org.task1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Динамический массив, который хранит элементы во внутреннем массиве и автоматически
 * увеличивает его ёмкость.
 */
public class CustomArrayList<E> implements CustomList<E> {
    private static final int DEFAULT_SIZE = 10;

    private Object[] elements;
    private int size;

    /**
     * Создаёт пустой список с ёмкостью 10.
     */
    public CustomArrayList() {
        elements = new Object[DEFAULT_SIZE];
    }

    /**
     * Добавляет элемент в конец списка.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Элемент не может быть Null");
        }
        if (size == elements.length) {
            grow();
        }
        elements[size++] = element;
    }

    private void grow() {
        int newSize = elements.length * 3 / 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    /**
     * Возвращает элемент по индексу.
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        return elementAt(index);
    }

    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) elements[index];
    }

    /**
     * Удаляет элемент по индексу и возвращает его.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        E removed = elementAt(index);
        int tail = size - index - 1;
        if (tail > 0) {
            System.arraycopy(elements, index + 1, elements, index, tail);
        }
        elements[--size] = null;
        return removed;
    }

    /**
     * Возвращает количество элементов.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуст ли список.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает итератор по элементам от первого к последнему.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int cursor;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elementAt(cursor++);
            }
        };
    }
}
