package org.example;

import java.util.Scanner;

public class Main {
    private static final int MIN_HEALTH = 1;
    private static final int MAX_HEALTH = 10000;
    private static final int MIN_ATTACK = 1;
    private static final int MAX_ATTACK = 1000;
    private static final int MIN_ARMOR = 1;
    private static final int MAX_ARMOR = 100;
    private static final int MIN_MANA = 1;
    private static final int MAX_MANA = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать на Арену Героев!");

        Hero fighter1 = createHero(scanner, 1);
        if (fighter1 == null) {
            return;
        }
        Hero fighter2 = createHero(scanner, 2);
        if (fighter2 == null) {
            return;
        }

        Arena arena = new Arena(fighter1, fighter2);
        arena.startTournament();
        scanner.close();
    }

    private static Hero createHero(Scanner scanner, int number) {
        System.out.println("Создание персонажа №" + number);
        Integer type = readNumber(scanner, "Выберите тип персонажа (1 - Воин, 2 - Маг):", 1, 2);
        if (type == null) {
            return null;
        }
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("Введите имя:");
        if (!scanner.hasNextLine()) {
            System.out.println("Ошибка: имя не введено");
            return null;
        }
        String name = scanner.nextLine().trim();

        Integer maxHealth = readNumber(scanner, "Введите максимальное здоровье (" + MIN_HEALTH + "-" + MAX_HEALTH + "):", MIN_HEALTH, MAX_HEALTH);
        if (maxHealth == null) {
            return null;
        }
        Integer baseAttack = readNumber(scanner, "Введите базовую атаку (" + MIN_ATTACK + "-" + MAX_ATTACK + "):", MIN_ATTACK, MAX_ATTACK);
        if (baseAttack == null) {
            return null;
        }

        if (type == 1) {
            Integer armor = readNumber(scanner, "Введите броню (" + MIN_ARMOR + "-" + MAX_ARMOR + "):", MIN_ARMOR, MAX_ARMOR);
            if (armor == null) {
                return null;
            }
            return new Warrior(name, maxHealth, baseAttack, armor);
        }
        Integer maxMana = readNumber(scanner, "Введите максимальную ману (" + MIN_MANA + "-" + MAX_MANA + "):", MIN_MANA, MAX_MANA);
        if (maxMana == null) {
            return null;
        }
        return new Mage(name, maxHealth, baseAttack, maxMana);
    }

    private static Integer readNumber(Scanner scanner, String message, int min, int max) {
        System.out.println(message);
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: нужно ввести целое число");
            return null;
        }
        int value = scanner.nextInt();
        if (value < min || value > max) {
            System.out.println("Ошибка: значение должно быть от " + min + " до " + max);
            return null;
        }
        return value;
    }
}