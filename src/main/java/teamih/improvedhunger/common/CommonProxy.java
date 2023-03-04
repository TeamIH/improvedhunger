package teamih.improvedhunger.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import teamih.improvedhunger.common.event.ImprovedHungerEventHook;

public class CommonProxy {

    public void initialize() {

    }

    public void attachLifecycle(IEventBus modEventBus) {
        modEventBus.addListener(this::onCommonSetup);
    }

    public void attachEventHandlers(IEventBus eventBus) {

        eventBus.addListener(ImprovedHungerEventHook::onLivingTick);

    }

    private void onCommonSetup(FMLCommonSetupEvent event) {

    }

}
