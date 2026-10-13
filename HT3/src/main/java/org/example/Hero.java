package org.example;


public abstract class Hero {
    private static final int MIN_STAT_VALUE = 1;
    private final String name;
    private final int maxHealth;
    private final int baseAttack;
    private int health;

    protected Hero(String name, int maxHealth, int baseAttack) {
        if (name == null || name.isBlank()) {
            this.name = "Безымянный";
        } else {
            this.name = name;
        }
        if (maxHealth < MIN_STAT_VALUE) {
            this.maxHealth = MIN_STAT_VALUE;
            this.health = MIN_STAT_VALUE;
        } else {
            this.maxHealth = maxHealth;
            this.health = maxHealth;
        }
        if (baseAttack < MIN_STAT_VALUE) {
            this.baseAttack = MIN_STAT_VALUE;
        } else {
            this.baseAttack = baseAttack;
        }
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getName() {
        return name;
    }

    public abstract void attack(Hero target);

    public abstract ActionType makeTurn(Hero target);

    public void takeDamage(int damage) {
        if (getHealth() == 0) {
            return;
        }
        if (damage < 0) {
            return;
        }
        this.health -= damage;
        if (getHealth() <= 0) {
            System.out.println(getName() + " пал в бою!");
            this.health = 0;
        }
    }

    @Override
    public String toString() {
        return getName() + " [HP: " + getHealth() + "/" + getMaxHealth() + "]";
    }

    public void heal(int amount) {
        if (amount < 0) {
            return;
        }
        this.health += Math.min(getMaxHealth() - getHealth(), amount);
    }

    public boolean isAlive() {
        return getHealth() > 0;
    }
}
