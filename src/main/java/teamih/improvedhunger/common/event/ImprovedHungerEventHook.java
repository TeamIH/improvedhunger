package teamih.improvedhunger.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import teamih.improvedhunger.config.ConfigHandler;

public class ImprovedHungerEventHook {

    public static void onLivingTick(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player) {
            Double exhaustion = ConfigHandler.CONSTANTHUNGER.get();
            Player player = (Player) event.getEntity();

            if (!player.isCreative() && !player.isDeadOrDying()) player.causeFoodExhaustion(exhaustion.floatValue());
        }

    }

}
