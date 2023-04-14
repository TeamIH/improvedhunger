package teamih.improvedhunger.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import teamih.improvedhunger.common.event.ImprovedHungerEventHook;
import teamih.improvedhunger.common.registry.EffectsRegistry;

public class CommonProxy {

    public void initialize() {

    }

    public void attachLifecycle(IEventBus modEventBus) {
        EffectsRegistry.register(modEventBus);
        modEventBus.addListener(this::onCommonSetup);
    }

    public void attachEventHandlers(IEventBus eventBus) {

        eventBus.addListener(ImprovedHungerEventHook::onLivingTick);
        eventBus.addListener(ImprovedHungerEventHook::onItemUseFinish);

    }

    private void onCommonSetup(FMLCommonSetupEvent event) {

    }

}
