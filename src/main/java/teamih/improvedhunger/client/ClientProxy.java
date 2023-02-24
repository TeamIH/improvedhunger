package teamih.improvedhunger.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamih.improvedhunger.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void initialize() {

        super.initialize();

    }

    @Override
    public void attachLifecycle(IEventBus modEventBus) {

        super.attachLifecycle(modEventBus);

    }

    @Override
    public void attachEventHandlers(IEventBus eventBus) {

        super.attachEventHandlers(eventBus);

    }

    private void onClientSetup(FMLClientSetupEvent event) {

    }
}
