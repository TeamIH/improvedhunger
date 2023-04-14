package teamih.improvedhunger.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import teamih.improvedhunger.common.registry.EffectsRegistry;
import teamih.improvedhunger.config.ConfigHandler;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyVariable(method = "causeFoodExhaustion", at = @At("HEAD"))
    public float onCauseFoodExhaustion(float exhaustion) {
        float modifier = ConfigHandler.HUNGERDECAYMODIFIER.get();
        if (((Player)(Object)this).hasEffect(EffectsRegistry.WELLFED_EFFECT.get())) {
            Double wellFedModifer = ConfigHandler.WELLFEDDECAYMODIFIER.get();
            modifier *= wellFedModifer.floatValue();
        }

        return exhaustion * (modifier/100);
    }
}
