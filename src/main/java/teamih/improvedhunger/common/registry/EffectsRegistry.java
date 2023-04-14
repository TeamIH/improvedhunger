package teamih.improvedhunger.common.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamih.improvedhunger.ImprovedHunger;
import teamih.improvedhunger.common.effect.WellFedEffect;

public class EffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ImprovedHunger.MODID);

    public static final RegistryObject<MobEffect> WELLFED_EFFECT = MOB_EFFECTS.register("wellfed", () -> new WellFedEffect(MobEffectCategory.BENEFICIAL, 0));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
