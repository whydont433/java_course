package org.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class StudentRegistryTest {

    private static final Student A = new Student(1, "Александр", 5.0);
    private static final Student B = new Student(2, "Борис", 7.5);
    private static final Student C = new Student(3, "Ваня", 10.0);
    private static final Student D = new Student(4, "Глеб", 0.0);
    private static final Student E = new Student(5, "Даша", 7.5);

    private Map<Integer, Student> hashMap;
    private NavigableMap<Integer, Student> treeMap;

    @BeforeEach
    void setUp() {
        List<Student> all = List.of(A, B, C, D, E);
        hashMap = StudentRegistry.toHashMap(all);
        treeMap = StudentRegistry.toDescendingTreeMap(all);
    }


    @Test
    void mapsUseIdAsKey() {
        assertEquals(5, hashMap.size());
        assertEquals(C, hashMap.get(3));
        assertEquals(List.of(5, 4, 3, 2, 1), new ArrayList<>(treeMap.keySet()));
    }


    @Test
    void findIncludesBothBounds() {
        List<Student> result = StudentRegistry.findStudentsByGradeRange(hashMap, 5.0, 7.5);

        assertEquals(3, result.size());
        assertEquals(Set.of(A, B, E), new HashSet<>(result));
    }

    @Test
    void findWithFullRangeReturnsAll() {
        List<Student> result = StudentRegistry.findStudentsByGradeRange(hashMap, 0.0, 10.0);

        assertEquals(5, result.size());
        assertEquals(Set.of(A, B, C, D, E), new HashSet<>(result));
    }

    @Test
    void findWithNoMatchesReturnsEmptyList() {
        assertTrue(StudentRegistry.findStudentsByGradeRange(hashMap, 8.0, 9.0).isEmpty());
    }

    @Test
    void findOnEmptyMapReturnsEmptyList() {
        assertTrue(StudentRegistry.findStudentsByGradeRange(new HashMap<>(), 0.0, 10.0).isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"-0.1, 5.0", "5.0, 10.1", "7.0, 6.0"})
    void findRejectsInvalidRange(double min, double max) {
        assertThrows(IllegalArgumentException.class,
                () -> StudentRegistry.findStudentsByGradeRange(hashMap, min, max));
    }


    @Test
    void topNReturnsHighestIdsInDescendingOrder() {
        assertEquals(List.of(E, D), StudentRegistry.getTopNStudentsById(treeMap, 2));
    }

    @Test
    void topNLargerThanSizeReturnsAll() {
        assertEquals(List.of(E, D, C, B, A), StudentRegistry.getTopNStudentsById(treeMap, 10));
    }

    @Test
    void topNZeroReturnsEmptyList() {
        assertTrue(StudentRegistry.getTopNStudentsById(treeMap, 0).isEmpty());
    }

    @Test
    void topNOnEmptyMapReturnsEmptyList() {
        NavigableMap<Integer, Student> empty = new TreeMap<>(Comparator.reverseOrder());
        assertTrue(StudentRegistry.getTopNStudentsById(empty, 3).isEmpty());
    }

    @Test
    void topNRejectsNegativeN() {
        assertThrows(IllegalArgumentException.class,
                () -> StudentRegistry.getTopNStudentsById(treeMap, -1));
    }


    @Test
    void methodsDoNotModifyInputAndReturnArrayList() {
        Map<Integer, Student> hashCopy = new HashMap<>(hashMap);
        Map<Integer, Student> treeCopy = new HashMap<>(treeMap);

        List<Student> found = StudentRegistry.findStudentsByGradeRange(hashMap, 0.0, 10.0);
        List<Student> top = StudentRegistry.getTopNStudentsById(treeMap, 3);

        assertEquals(hashCopy, hashMap);
        assertEquals(treeCopy, treeMap);
        assertInstanceOf(ArrayList.class, found);
        assertInstanceOf(ArrayList.class, top);
    }
}