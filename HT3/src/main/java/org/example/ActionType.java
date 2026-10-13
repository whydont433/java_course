package org.example;

public enum ActionType {
    BASE_ATTACK("базовая атака"), SPECIAL_SKILL("применение особого умения или магии"), REST("пропуск хода ради восстановления сил");
    private final String rusType;

    ActionType(String rusType) {
        this.rusType = rusType;
    }

    public String getRusType() {
        return rusType;
    }
}
