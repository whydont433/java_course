package org.example;

public class Arena {
    private final Hero fighter1;
    private final Hero fighter2;
    private int roundCounter;

    public Arena(Hero fighter1, Hero fighter2) {
        if (fighter1 == null || fighter2 == null) {
            throw new NullPointerException("Бойцы арены не могут быть null");
        }
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.roundCounter = 0;
    }

    public void startTournament() {
        while (fighter1.isAlive() && fighter2.isAlive()) {
            roundCounter++;
            System.out.println("========== Раунд " + roundCounter + " ==========");

            Hero attacker;
            Hero defender;
            if (Math.random() < 0.5) {
                attacker = fighter1;
                defender = fighter2;
            } else {
                attacker = fighter2;
                defender = fighter1;
            }

            ActionType action = attacker.makeTurn(defender);
            System.out.println("Игрок " + attacker.getName() + " совершил действие: " + action.getRusType());
            if (!defender.isAlive()) {
                break;
            }

            action = defender.makeTurn(attacker);
            System.out.println("Игрок " + defender.getName() + " совершил действие: " + action.getRusType());

            System.out.println(fighter1);
            System.out.println(fighter2);
        }

        Hero winner;
        if (fighter1.isAlive()) {
            winner = fighter1;
        } else {
            winner = fighter2;
        }
        System.out.println("Бой окончен! Победитель: " + winner.getName());
    }
}