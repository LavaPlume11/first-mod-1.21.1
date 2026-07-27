package me.xander.firstmod.gui.hud;

import me.xander.first_mod;
import net.minecraft.util.Identifier;

public enum CustomHeartType {
    CORRUPTED(Identifier.of(first_mod.MOD_ID, "heart/corrupted_full"),Identifier.of(first_mod.MOD_ID, "heart/corrupted_full_blinking"), Identifier.of(first_mod.MOD_ID, "heart/corrupted_half"), Identifier.of(first_mod.MOD_ID, "heart/corrupted_half_blinking"),Identifier.of(first_mod.MOD_ID, "heart/corrupted_full"),Identifier.of(first_mod.MOD_ID, "heart/corrupted_full_blinking"), Identifier.of(first_mod.MOD_ID, "heart/corrupted_half"), Identifier.of(first_mod.MOD_ID, "heart/corrupted_half_blinking"));
    private final Identifier fullTexture;
    private final Identifier fullBlinkingTexture;
    private final Identifier halfTexture;
    private final Identifier halfBlinkingTexture;
    private final Identifier hardcoreFullTexture;
    private final Identifier hardcoreFullBlinkingTexture;
    private final Identifier hardcoreHalfTexture;
    private final Identifier hardcoreHalfBlinkingTexture;

    CustomHeartType(final Identifier fullTexture, final Identifier fullBlinkingTexture, final Identifier halfTexture, final Identifier halfBlinkingTexture, final Identifier hardcoreFullTexture, final Identifier hardcoreFullBlinkingTexture, final Identifier hardcoreHalfTexture, final Identifier hardcoreHalfBlinkingTexture) {
        this.fullTexture = fullTexture;
        this.fullBlinkingTexture = fullBlinkingTexture;
        this.halfTexture = halfTexture;
        this.halfBlinkingTexture = halfBlinkingTexture;
        this.hardcoreFullTexture = hardcoreFullTexture;
        this.hardcoreFullBlinkingTexture = hardcoreFullBlinkingTexture;
        this.hardcoreHalfTexture = hardcoreHalfTexture;
        this.hardcoreHalfBlinkingTexture = hardcoreHalfBlinkingTexture;
    }

    public Identifier getTexture(boolean hardcore, boolean half, boolean blinking) {
        if (!hardcore) {
            if (half) {
                return blinking ? this.halfBlinkingTexture : this.halfTexture;
            } else {
                return blinking ? this.fullBlinkingTexture : this.fullTexture;
            }
        } else if (half) {
            return blinking ? this.hardcoreHalfBlinkingTexture : this.hardcoreHalfTexture;
        } else {
            return blinking ? this.hardcoreFullBlinkingTexture : this.hardcoreFullTexture;
        }
    }

}

