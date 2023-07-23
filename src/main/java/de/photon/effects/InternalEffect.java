package de.photon.effects;

import de.photon.effects.util.PotionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public enum InternalEffect
{
    IGNOREDAMAGE(PotionUtils.permanentEffect(PotionEffectType.FIRE_RESISTANCE), PotionUtils.permanentEffect(PotionEffectType.DAMAGE_RESISTANCE, 5)),
    NIGHTVISION(PotionUtils.permanentEffect(PotionEffectType.NIGHT_VISION)),
    REGENERATION(PotionUtils.permanentEffect(PotionEffectType.REGENERATION, 127)),
    SATURATION(PotionUtils.permanentEffect(PotionEffectType.SATURATION, 127)),
    SLOWFALL(PotionUtils.permanentEffect(PotionEffectType.SLOW_FALLING)),
    SPEEDMINE(PotionUtils.permanentEffect(PotionEffectType.FAST_DIGGING, 127)),
    STRENGTH(PotionUtils.permanentEffect(PotionEffectType.INCREASE_DAMAGE, 127)),
    WATERBREATHING(PotionUtils.permanentEffect(PotionEffectType.WATER_BREATHING)),
    WEAKNESS(PotionUtils.permanentEffect(PotionEffectType.WEAKNESS, 127));

    private final Set<PotionEffect> coveredPotions;

    /**
     * @param coveredPotions These effects should be given to a player when this {@link InternalEffect} is activated
     */
    InternalEffect(PotionEffect... coveredPotions)
    {
        this.coveredPotions = Set.of(coveredPotions);
    }

    /**
     * @param name the (case-insensitive) name of the effect.
     *
     * @return the {@link InternalEffect} with the given name or null if none was found.
     */
    public static InternalEffect byName(String name)
    {
        try {
            return InternalEffect.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * This toggles the {@link PotionEffect}s modified by this {@link InternalEffect}.
     *
     * @param livingEntity the {@link LivingEntity} this is applied to.
     *
     * @return true if {@link PotionEffect}s were added and false if {@link PotionEffect}s were removed.
     */
    public boolean toggleEffects(final LivingEntity livingEntity)
    {
        // If any of the effects has been found, only remove them. Else, add them.
        final boolean hasAnyEffect = this.coveredPotions.stream().map(PotionEffect::getType).anyMatch(livingEntity::hasPotionEffect);

        if (hasAnyEffect) this.coveredPotions.stream().map(PotionEffect::getType).forEach(livingEntity::removePotionEffect);
        else this.coveredPotions.forEach(livingEntity::addPotionEffect);
        return !hasAnyEffect;
    }
}
