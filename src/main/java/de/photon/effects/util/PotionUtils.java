package de.photon.effects.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PotionUtils
{
    /**
     * Creates a new permanent {@link org.bukkit.potion.PotionEffect} with 0 amplifier and no particles.
     *
     * @param type the {@link PotionEffectType} the {@link PotionEffect} should have.
     */
    public static PotionEffect permanentEffect(PotionEffectType type)
    {
        return permanentEffect(type, 0);
    }

    /**
     * Creates a new permanent {@link org.bukkit.potion.PotionEffect} with no particles.
     *
     * @param type      the {@link PotionEffectType} the {@link PotionEffect} should have.
     * @param amplifier the amplifier of the resulting {@link PotionEffect}.
     */
    public static PotionEffect permanentEffect(PotionEffectType type, int amplifier)
    {
        return permanentEffect(type, amplifier, false);
    }

    /**
     * Creates a new permanent {@link org.bukkit.potion.PotionEffect}.
     *
     * @param type      the {@link PotionEffectType} the {@link PotionEffect} should have.
     * @param amplifier the amplifier of the resulting {@link PotionEffect}.
     * @param particles whether the {@link PotionEffect} should have visible particles.
     */
    public static PotionEffect permanentEffect(PotionEffectType type, int amplifier, boolean particles)
    {
        return new PotionEffect(type, PotionEffect.INFINITE_DURATION, amplifier, particles, particles);
    }
}
