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

        // Nightvision
        REGISTERED_EFFECTS.add(new InternalEffect("nightvision", "nv", InternalPermission.NIGHTVISION, PotionUtils.permanentEffectFromType(PotionEffectType.NIGHT_VISION)));

        // Ignore damage
        REGISTERED_EFFECTS.add(new InternalEffect("ignoredamage", "ignoredmg", InternalPermission.IGNOREDAMAGE,
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.FIRE_RESISTANCE),
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.DAMAGE_RESISTANCE, 5)
        ));

        // Speedmine
        REGISTERED_EFFECTS.add(new InternalEffect("speedmine", null, InternalPermission.SPEEDMINE,
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE)));

        // Saturation
        REGISTERED_EFFECTS.add(new InternalEffect("saturation", "igndmg", InternalPermission.SATURATION,
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.FIRE_RESISTANCE),
                                                  PotionUtils.permanentEffectFromType(PotionEffectType.DAMAGE_RESISTANCE, 5)));
    }

    @Getter
    private final String name;
    @Getter
    private final String shortForm;
    @Getter
    private final InternalPermission permission;
    private final PotionEffect[] coveredPotions;

    /**
     * @param name           the (long) name of the effect.
     * @param shortForm      the short name of the effect
     * @param coveredPotions The effects should be given to a player when this {@link InternalEffect} is activated
     */
    public InternalEffect(String name, String shortForm, InternalPermission permission, PotionEffect... coveredPotions)
    {
        this.name = name;
        this.shortForm = shortForm;
        this.permission = permission;
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
                addEffects = false;
                break;
            }
        }

        if (addEffects)
        {
            for (PotionEffect coveredPotion : this.coveredPotions)
            {
                livingEntity.removePotionEffect(coveredPotion.getType());
            }
        }
        else
        {
            for (PotionEffect coveredPotion : this.coveredPotions)
            {
                livingEntity.addPotionEffect(coveredPotion);
            }
        }

        return addEffects;
    }
}
