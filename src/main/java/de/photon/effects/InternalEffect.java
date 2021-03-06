package de.photon.effects;

import de.photon.effects.util.PotionUtils;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class InternalEffect
{
    /**
     * All effects are registered here
     */
    public static final Set<InternalEffect> REGISTERED_EFFECTS;

    static
    {
        REGISTERED_EFFECTS = new HashSet<>();

        // Ignore damage
        REGISTERED_EFFECTS.add(new InternalEffect("Ignoredamage",
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.FIRE_RESISTANCE),
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.DAMAGE_RESISTANCE, 5)
        ));

        // Nightvision
        REGISTERED_EFFECTS.add(new InternalEffect("Nightvision", PotionUtils.permanentEffectFromType(PotionEffectType.NIGHT_VISION)));

        // Speedmine
        REGISTERED_EFFECTS.add(new InternalEffect("Speedmine",
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.FAST_DIGGING, 127)));

        // Saturation
        REGISTERED_EFFECTS.add(new InternalEffect("Saturation",
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.SATURATION, 127)));
    }

    @Getter
    private final String name;
    private final PotionEffect[] coveredPotions;

    /**
     * @param name           the (long) name of the effect.
     * @param coveredPotions The effects should be given to a player when this {@link InternalEffect} is activated
     */
    public InternalEffect(String name, PotionEffect... coveredPotions)
    {
        this.name = name;
        this.coveredPotions = coveredPotions;
    }

    /**
     * This toggles the {@link PotionEffect}s modified by this {@link InternalEffect}.
     *
     * @param livingEntity the {@link LivingEntity} this is applied to.
     *
     * @return whether true if {@link PotionEffect}s were added and false if {@link PotionEffect}s were removed.
     */
    public boolean toggleEffects(final LivingEntity livingEntity)
    {
        boolean addEffects = true;

        for (PotionEffect coveredPotion : this.coveredPotions)
        {
            if (livingEntity.hasPotionEffect(coveredPotion.getType()))
            {
                livingEntity.removePotionEffect(coveredPotion.getType());
                addEffects = false;
            }
        }

        if (addEffects)
        {
            for (PotionEffect coveredPotion : this.coveredPotions)
            {
                livingEntity.addPotionEffect(coveredPotion);
            }
        }

        return addEffects;
    }
}
