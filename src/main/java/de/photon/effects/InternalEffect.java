package de.photon.effects;

import com.google.common.base.Preconditions;
import de.photon.effects.util.PotionUtils;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class InternalEffect
{
    /**
     * All effects are registered here
     */
    @Unmodifiable
    public static final Map<String, InternalEffect> REGISTERED_EFFECTS = Stream.of(new InternalEffect("ignoredamage", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.FIRE_RESISTANCE), PotionUtils.permanentEffectFromType(PotionEffectType.DAMAGE_RESISTANCE, 5))),
                                                                                   new InternalEffect("nightvision", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.NIGHT_VISION))),
                                                                                   new InternalEffect("regeneration", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.REGENERATION, 127))),
                                                                                   new InternalEffect("saturation", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.SATURATION, 127))),
                                                                                   new InternalEffect("slowfall", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.SLOW_FALLING))),
                                                                                   new InternalEffect("speedmine", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.FAST_DIGGING, 127))),
                                                                                   new InternalEffect("strength", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.INCREASE_DAMAGE, 127))),
                                                                                   new InternalEffect("waterbreathing", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.WATER_BREATHING))),
                                                                                   new InternalEffect("weakness", Set.of(PotionUtils.permanentEffectFromType(PotionEffectType.WEAKNESS, 127))))
                                                                               // Create a mapping from the name to the effect.
                                                                               .collect(Collectors.toUnmodifiableMap(InternalEffect::getName, effect -> effect));

    @NotNull String name;
    @Unmodifiable @EqualsAndHashCode.Exclude @NotNull Set<PotionEffect> coveredPotions;

    /**
     * @param name           the (long) name of the effect.
     * @param coveredPotions The effects should be given to a player when this {@link InternalEffect} is activated
     */
    public InternalEffect(@NotNull String name, @NotNull Set<PotionEffect> coveredPotions)
    {
        Preconditions.checkNotNull(name, "The name of an InternalEffect must not be null.");
        Preconditions.checkNotNull(coveredPotions, "The coveredPotions of an InternalEffect must not be null.");
        Preconditions.checkArgument(!coveredPotions.isEmpty(), "The coveredPotions of an InternalEffect must not be empty.");

        this.name = name;
        // Make sure the coveredPotions are immutable.
        this.coveredPotions = Set.copyOf(coveredPotions);
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
        // If any of the effects has been found, only remove them. Else, add them.
        boolean addEffects = true;

        for (PotionEffect coveredPotion : this.coveredPotions) {
            if (livingEntity.hasPotionEffect(coveredPotion.getType())) {
                livingEntity.removePotionEffect(coveredPotion.getType());
                addEffects = false;
            }
        }

        if (addEffects) {
            for (PotionEffect coveredPotion : this.coveredPotions) {
                livingEntity.addPotionEffect(coveredPotion);
            }
        }

        return addEffects;
    }
}
