package org.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Сравнивает время выполнения типовых операций у ArrayList и LinkedList.
 */
public class CollectionPerformanceExperiment {

    private static final int N = 10_000;
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 7;

    private static final String[] OPERATIONS = {
            "Добавление в конец",
            "Добавление в начало",
            "Вставка в середину",
            "Доступ по индексу",
            "Удаление из начала",
            "Удаление из конца"
    };

    private static long checksum;

    /** Запускает и печатает таблицу результатов. */
    public static void main(String[] args) {
        System.out.printf("%-22s %8s %15s %15s%n", "Операция", "N", "ArrayList, мс", "LinkedList, мс");

        for (int op = 0; op < OPERATIONS.length; op++) {
            double arrayListMs = measure(op, true);
            double linkedListMs = measure(op, false);
            System.out.printf("%-22s %8d %15.3f %15.3f%n", OPERATIONS[op], N, arrayListMs, linkedListMs);
        }

        System.out.println("Контрольная сумма: " + checksum);
    }

    private static double measure(int op, boolean useArrayList) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            runOnce(op, useArrayList);
        }

        long[] times = new long[MEASURED_RUNS];
        for (int i = 0; i < MEASURED_RUNS; i++) {
            times[i] = runOnce(op, useArrayList);
        }

        Arrays.sort(times);
        return times[MEASURED_RUNS / 2] / 1_000_000.0;
    }

    private static long runOnce(int op, boolean useArrayList) {
        List<Integer> list = useArrayList ? new ArrayList<>() : new LinkedList<>();

        if (op >= 3) {
            fill(list);
        }

        long start = System.nanoTime();
        long result = runOperation(op, list);
        long elapsed = System.nanoTime() - start;

        checksum += result;
        return elapsed;
    }

    private static long runOperation(int op, List<Integer> list) {
        return switch (op) {
            case 0 -> addLast(list);
            case 1 -> addFirst(list);
            case 2 -> insertMiddle(list);
            case 3 -> getByIndex(list);
            case 4 -> removeFirst(list);
            case 5 -> removeLast(list);
            default -> throw new IllegalArgumentException("Неизвестная операция: " + op);
        };
    }

    private static void fill(List<Integer> list) {
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
    }

    private static long addLast(List<Integer> list) {
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        return list.size();
    }

    private static long addFirst(List<Integer> list) {
        for (int i = 0; i < N; i++) {
            list.add(0, i);
        }
        return list.size();
    }

    private static long insertMiddle(List<Integer> list) {
        for (int i = 0; i < N; i++) {
            list.add(list.size() / 2, i);
        }
        return list.size();
    }

    private static long getByIndex(List<Integer> list) {
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += list.get(i);
        }
        return sum;
    }

    private static long removeFirst(List<Integer> list) {
        long sum = 0;
        while (!list.isEmpty()) {
            sum += list.remove(0);
        }
        return sum;
    }

    private static long removeLast(List<Integer> list) {
        long sum = 0;
        while (!list.isEmpty()) {
            sum += list.remove(list.size() - 1);
        }
        return sum;
    }
}