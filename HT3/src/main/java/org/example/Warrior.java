package org.example;

public class Warrior extends Hero implements Castable, Restable {
    private final int armor;

    public Warrior(String name, int maxHealth, int baseAttack, int armor) {
        super(name, maxHealth, baseAttack);
        if (armor < 0) {
            this.armor = 1;
        } else {
            this.armor = armor;
        }
    }

    public int getArmor() {
        return armor;
    }

    @Override
    public void takeDamage(int damage) {
        int finalDamage = damage;
        if (finalDamage >= 0) {
            finalDamage = Math.max(damage - getArmor(), 1);
        }
        super.takeDamage(finalDamage);
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            throw new NullPointerException("Цель атаки не может быть null");
        }
        System.out.println(getName() + " бьет физическим уроном героя " + target.getName());
        target.takeDamage(getBaseAttack());

    }

    @Override
    public ActionType makeTurn(Hero target) {
        if (target == null) {
            throw new NullPointerException("Цель хода не может быть null");
        }
        if (canCast()) {
            castSpecialSkill(target);
            return ActionType.SPECIAL_SKILL;
        }
        if (needsRest()) {
            rest();
            return ActionType.REST;
        }
        attack(target);
        return ActionType.BASE_ATTACK;
    }

    @Override
    public boolean canCast() {
        return getMaxHealth() * 0.5 > getHealth();
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            throw new NullPointerException("Цель удара щитом не может быть null");
        }
        if (canCast()) {
            System.out.println(getName() + " ударил щитом героя " + target.getName() + "!");
            target.takeDamage(getBaseAttack() + (getArmor() * 2));
        } else {
            System.out.println("Воин попытался ударить щитом, но потерял равновесие!");
        }
    }

    @Override
    public String toString() {
        String defaultInfo = super.toString();
        return defaultInfo + " [Броня: " + getArmor() + "]";
    }

    @Override
    public boolean needsRest() {
        return getMaxHealth() * 0.15 > getHealth();
    }

    @Override
    public void rest() {
        heal(getArmor() * 2);
        System.out.println(getName() + " использовал второе дыхание");
    }
}