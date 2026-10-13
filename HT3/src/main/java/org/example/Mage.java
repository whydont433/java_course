package org.example;

public class Mage extends Hero implements Castable, Restable {
    private final int maxMana;
    private int mana;

    public Mage(String name, int maxHealth, int baseAttack, int maxMana) {
        super(name, maxHealth, baseAttack);
        if (maxMana < 0) {
            this.maxMana = 0;
        } else {
            this.maxMana = maxMana;
        }
        this.mana = this.maxMana;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public String toString() {
        String defaultInfo = super.toString();
        return defaultInfo + " [Мана: " + getMana() + "/" + getMaxMana() + "]";
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            throw new NullPointerException("Цель атаки не может быть null");
        }
        if (mana >= 10) {
            System.out.println(getName() + " применяет магический выстрел по герою " + target.getName());
            mana -= 10;
            target.takeDamage(getBaseAttack() * 2);
        } else {
            System.out.println(getName() + " бьет посохом героя " + target.getName() + " и восстанавливает ману");
            mana += Math.min(maxMana - mana, 5);
            target.takeDamage(getBaseAttack() / 2);
        }
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
        return mana >= 25;
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            throw new NullPointerException("Цель огненной глыбы не может быть null");
        }
        if (canCast()) {
            System.out.println(getName() + " использует огненную глыбу на героя " + target.getName());
            mana -= 25;
            target.takeDamage(getBaseAttack() * 3);
        } else {
            System.out.println(getName() + " пытается создать огненную глыбу, но ему не удаётся: не хватает маны!");
        }
    }

    @Override
    public boolean needsRest() {
        return mana == 0 || getMaxHealth() * 0.3 > getHealth();
    }

    @Override
    public void rest() {
        mana = maxMana;
        heal(getBaseAttack());
        System.out.println(getName() + " использовал исцеляющую медитацию");
    }
}