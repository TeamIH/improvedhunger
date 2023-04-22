package teamih.improvedhunger.common.event;

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

            if (!player.isCreative() && !player.isDeadOrDying()) player.causeFoodExhaustion(exhaustion.floatValue());

            if (!player.isCreative()) {
                if (ConfigHandler.HASTEBUFF.get()) {
                    if (player.getFoodData().getFoodLevel() >= ConfigHandler.HASTEHUNGER.get()) {
                        if (!player.hasEffect(MobEffects.DIG_SPEED) || player.getEffect(MobEffects.DIG_SPEED).getDuration() < 140) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 220, 0));
                    }
                }
            }

        }


    }

    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().isEdible() && event.getEntity() instanceof Player) {
            int min = ConfigHandler.WELLFEDMINHUNGER.get();
            int hunger = event.getItem().getFoodProperties(null).getNutrition();
            int duration = ((min * (hunger * hunger)) + (min * hunger) - ((min * (min * min)) + (min * min))) * 20;

            if (duration > 0) {
                event.getEntity().addEffect(new MobEffectInstance(EffectsRegistry.WELLFED_EFFECT.get(), duration));
            }
        }
    }

}
