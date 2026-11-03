package org.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    private static final int SIZE = 5;

    private static CustomArrayList<Integer> filledList(int n) {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i * 10);
        }
        return list;
    }

    @Test
    void newListIsEmpty() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void addSingleElementKeepsOrder() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("a");

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals("a", list.get(0));
    }

    @Test
    void addSomeElementsKeepsOrder() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    void addMoreThanDefaultSizeKeepsAllElements() {
        int n = 100;
        CustomArrayList<Integer> list = filledList(n);

        assertEquals(n, list.size());
        for (int i = 0; i < n; i++) {
            assertEquals(i * 10, list.get(i));
        }
    }


    @ParameterizedTest
    @ValueSource(ints = {0, SIZE / 2, SIZE - 1})
    void getReturnsElementByIndex(int index) {
        CustomArrayList<Integer> list = filledList(SIZE);
        assertEquals(index * 10, list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, SIZE})
    void getRejectsInvalidIndex(int index) {
        CustomArrayList<Integer> list = filledList(SIZE);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, SIZE})
    void removeRejectsInvalidIndex(int index) {
        CustomArrayList<Integer> list = filledList(SIZE);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));
        assertEquals(SIZE, list.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, SIZE / 2, SIZE - 1})
    void removeFirstMiddleAndLast(int index) {
        CustomArrayList<Integer> list = filledList(SIZE);

        assertEquals(index * 10, list.remove(index));
        assertEquals(SIZE - 1, list.size());

        for (int i = 0; i < list.size(); i++) {
            int originalIndex = i < index ? i : i + 1;
            assertEquals(originalIndex * 10, list.get(i));
        }
    }

    @Test
    void addNullThrowsNpe() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        assertThrows(NullPointerException.class, () -> list.add(null));
        assertTrue(list.isEmpty());
    }

    @Test
    void iteratorInOrder() {
        CustomArrayList<Integer> list = filledList(SIZE);

        int count = 0;
        for (Integer value : list) {
            assertEquals(count * 10, value);
            count++;
        }
        assertEquals(SIZE, count);
    }

    @Test
    void nextForEndedIterator() {
        CustomArrayList<Integer> list = filledList(2);
        Iterator<Integer> it = list.iterator();
        it.next();
        it.next();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

}