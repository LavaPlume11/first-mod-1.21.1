package me.xander.firstmod.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum LemmingVariant {
    GOLD(0),
    BLUE(1);

    private static final LemmingVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(
            LemmingVariant::getId)).toArray(LemmingVariant[]::new);
    private final int id;

    LemmingVariant(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public static LemmingVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
