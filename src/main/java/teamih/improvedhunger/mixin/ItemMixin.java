package teamih.improvedhunger.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamih.improvedhunger.config.ConfigHandler;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    public void customGetUseDuration(ItemStack stack, CallbackInfoReturnable<Integer> ci) {
        if (!ConfigHandler.DEFAULTCONSUMPTION.get() && stack.isEdible()) {
            int hunger = stack.getFoodProperties(null).getNutrition();
            ci.setReturnValue(hunger * ConfigHandler.CONSUMPTIONMODIFIER.get() + ConfigHandler.CONSUMPTIONMODIFIER.get());
            ci.cancel();
        }
    }
}
