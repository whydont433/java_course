package org.task3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/** Создание реестров студентов. */
public final class StudentRegistry {

    private StudentRegistry() {
    }

    /** Создаёт HashMap, где ключ - id студента. */
    public static Map<Integer, Student> toHashMap(Collection<Student> students) {
        Map<Integer, Student> map = new HashMap<>();
        fill(map, students);
        return map;
    }

    /** Создаёт TreeMap с сортировкой id по убыванию. */
    public static NavigableMap<Integer, Student> toDescendingTreeMap(Collection<Student> students) {
        NavigableMap<Integer, Student> map = new TreeMap<>(Comparator.reverseOrder());
        fill(map, students);
        return map;
    }

    /** Возвращает студентов с оценкой в диапазоне [minGrade, maxGrade]. */
    public static List<Student> findStudentsByGradeRange(
            Map<Integer, Student> students, double minGrade, double maxGrade) {
        if (!isValidGrade(minGrade) || !isValidGrade(maxGrade) || minGrade > maxGrade) {
            throw new IllegalArgumentException(
                    "Некорректный диапазон: [" + minGrade + ", " + maxGrade + "]");
        }

        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.grade() >= minGrade && student.grade() <= maxGrade) {
                result.add(student);
            }
        }
        return result;
    }

    /** Возвращает не более n студентов с наибольшими id. Карта должна быть отсортирована по убыванию. */
    public static List<Student> getTopNStudentsById(NavigableMap<Integer, Student> students, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n не может быть отрицательным: " + n);
        }

        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (result.size() == n) {
                break;
            }
            result.add(student);
        }
        return result;
    }

    private static void fill(Map<Integer, Student> map, Collection<Student> students) {
        for (Student student : students) {
            if (map.put(student.id(), student) != null) {
                throw new IllegalArgumentException("Повторяющийся id: " + student.id());
            }
        }
    }

    private static boolean isValidGrade(double grade) {
        return grade >= 0.0 && grade <= 10.0;
    }
}