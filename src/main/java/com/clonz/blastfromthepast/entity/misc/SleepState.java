package com.clonz.blastfromthepast.entity.misc;

public enum SleepState {
    AWAKE(false),
    LIE_DOWN(true),
    SLEEP(false),
    WAKE_UP(true);

    private final boolean transitional;

    private SleepState(boolean transitional){
        this.transitional = transitional;
    }

    public boolean isTransitional() {
        return this.transitional;
    }

    public static SleepState byOrdinal(int pOrdinal) {
        if (pOrdinal < 0 || pOrdinal > values().length) {
            pOrdinal = 0;
        }

        return values()[pOrdinal];
    }
}
