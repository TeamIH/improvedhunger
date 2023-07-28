package teamih.improvedhunger.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import teamih.improvedhunger.common.registry.EffectsRegistry;
import teamih.improvedhunger.config.ConfigHandler;


public class ImprovedHungerEventHook {

    public static void onLivingTick(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player) {
            Double exhaustion = ConfigHandler.CONSTANTHUNGER.get();
            Player player = (Player) event.getEntity();

            if (!player.isCreative() && !player.isDeadOrDying()) player.causeFoodExhaustion(exhaustion.floatValue());

            if (!player.isCreative()) {
                if (ConfigHandler.HASTEBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() >= ConfigHandler.HASTEHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.DIG_SPEED) || player.getEffect(MobEffects.DIG_SPEED).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 220, 0, true, true));
                    }
                }

                if (ConfigHandler.SPEEDBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() >= ConfigHandler.SPEEDHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.MOVEMENT_SPEED) || player.getEffect(MobEffects.MOVEMENT_SPEED).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 220, 0, true, true));
                    }
                }

                if (ConfigHandler.STRENGTHBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() >= ConfigHandler.STRENGTHHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.DAMAGE_BOOST) || player.getEffect(MobEffects.DAMAGE_BOOST).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 220, 0, true, true));
                    }
                }

                if (ConfigHandler.FATIGUEDEBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() <= ConfigHandler.FATIGUEHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.DIG_SLOWDOWN) || player.getEffect(MobEffects.DIG_SLOWDOWN).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 220, 0, true, true));
                    }
                }

                if (ConfigHandler.SLOWNESSDEBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() <= ConfigHandler.SLOWNESSHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 220, 0, true, true));
                    }
                }

                if (ConfigHandler.WEAKNESSDEBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() <= ConfigHandler.WEAKNESSHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.WEAKNESS) || player.getEffect(MobEffects.WEAKNESS).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 220, 0, true, true));
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

}
