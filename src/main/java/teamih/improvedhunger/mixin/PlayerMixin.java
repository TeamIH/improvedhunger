package teamih.improvedhunger.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import teamih.improvedhunger.config.ConfigHandler;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyVariable(method = "causeFoodExhaustion", at = @At("HEAD"))
    public float onCauseFoodExhaustion(float exhaustion) {
        int modifier = ConfigHandler.HUNGERDECAYMODIFIER.get();
        //System.out.println(exhaustion * (modifier/100));
        return exhaustion * (modifier/100);
    }
}
