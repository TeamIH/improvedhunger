package teamih.improvedhunger.common.event;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import teamih.improvedhunger.common.registry.EffectsRegistry;
import teamih.improvedhunger.config.ConfigHandler;


public class ImprovedHungerEventHook {

    public static void onLivingTick(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player) {
            Double exhaustion = ConfigHandler.CONSTANTHUNGER.get();
            Player player = (Player) event.getEntity();
            int foodLevel = player.getFoodData().getFoodLevel();

            if (!player.isCreative() && !player.isDeadOrDying()) player.causeFoodExhaustion(exhaustion.floatValue());

            if (!player.isCreative()) {
                if (ConfigHandler.HASTEBUFF.get()) {
                    if (foodLevel >= ConfigHandler.HASTEHUNGER.get()) {
                        addBuff(MobEffects.DIG_SPEED, player);
                    }
                }

                if (ConfigHandler.SPEEDBUFF.get()) {
                    if (foodLevel >= ConfigHandler.SPEEDHUNGER.get()) {
                        addBuff(MobEffects.MOVEMENT_SPEED, player);
                    }
                }

                if (ConfigHandler.STRENGTHBUFF.get()) {
                    if (foodLevel >= ConfigHandler.STRENGTHHUNGER.get()) {
                        addBuff(MobEffects.DAMAGE_BOOST, player);
                    }
                }

                if (ConfigHandler.FATIGUEDEBUFF.get()) {
                    if (foodLevel <= ConfigHandler.FATIGUEHUNGER.get()) {
                        addDebuff(MobEffects.DIG_SLOWDOWN, player);
                    }
                }

                if (ConfigHandler.SLOWNESSDEBUFF.get()) {
                    if (foodLevel <= ConfigHandler.SLOWNESSHUNGER.get()) {
                        addDebuff(MobEffects.MOVEMENT_SLOWDOWN, player);
                    }
                }

                if (ConfigHandler.WEAKNESSDEBUFF.get()) {
                    if (foodLevel <= ConfigHandler.WEAKNESSHUNGER.get()) {
                        addDebuff(MobEffects.WEAKNESS, player);
                    }
                }
            }


        }


    }

    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().isEdible() && event.getEntity() instanceof Player) {
            int minWellFedHunger = ConfigHandler.WELLFEDMINHUNGER.get();
            int minAbsorbHunger = ConfigHandler.ABSORBMINHUNGER.get();
            int minResistanceHunger = ConfigHandler.RESISTANCEMINHUNGER.get();
            int hunger = event.getItem().getFoodProperties(null).getNutrition();
            int wellfedDuration = ((minWellFedHunger * (hunger * hunger)) + (minWellFedHunger * hunger) - ((minWellFedHunger * (minWellFedHunger * minWellFedHunger)) + (minWellFedHunger * minWellFedHunger))) * 20;
            int absorbDuration = (int) Math.round(wellfedDuration * ConfigHandler.ABSORBDURATIONMODIFIER.get());
            int resistanceDuration = (int) Math.round(absorbDuration * ConfigHandler.RESISTANCEDURATIONMODIFIER.get());

            if (wellfedDuration > 0) {
                event.getEntity().addEffect(new MobEffectInstance(EffectsRegistry.WELLFED_EFFECT.get(), wellfedDuration));
            }

            if (hunger >= minAbsorbHunger) {
                if (absorbDuration > 0) {
                    float saturation = hunger * event.getItem().getFoodProperties(null).getSaturationModifier() * 2;
                    int absorbLevel = (int) Math.ceil(saturation/minAbsorbHunger) - 1;
                    event.getEntity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, absorbDuration, absorbLevel, true, true));
                }
            }

            if (hunger >= minResistanceHunger) {
                if (resistanceDuration > 0) {
                    int resistanceLevel = (int) Math.floor(hunger/minResistanceHunger);
                    event.getEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, resistanceDuration, resistanceLevel, true, true));
                }
            }
        }
    }

    private static void addDebuff(MobEffect effect, Player player) {

        if (!player.hasEffect(effect)) {
            int duration = ConfigHandler.DEBUFFDURATION.get() * 20;
            player.addEffect(new MobEffectInstance(effect, duration, 0, true, true));
        } else if (player.getEffect(effect).getDuration() < 20) {
            int amp = player.getEffect(effect).getAmplifier();
            if (amp < ConfigHandler.DEBUFFLEVEL.get() - 1) {
                ++amp;
            }
            int duration = (ConfigHandler.DEBUFFDURATION.get() * 20) * (amp + 1);
            player.addEffect(new MobEffectInstance(effect, duration , amp, true, true));
        }

    }

    private static void addBuff(MobEffect effect, Player player) {
        if (!player.hasEffect(effect) || player.getEffect(effect).getDuration() < 20) {
            int duration = ConfigHandler.DEBUFFDURATION.get() * 20;
            player.addEffect(new MobEffectInstance(effect, duration, 0, true, true));
        }
    }

}
