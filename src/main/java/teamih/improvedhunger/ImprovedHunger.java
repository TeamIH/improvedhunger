package teamih.improvedhunger;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teamih.improvedhunger.client.ClientProxy;
import teamih.improvedhunger.common.CommonProxy;
import teamih.improvedhunger.config.ConfigHandler;

@Mod(ImprovedHunger.MODID)
public class ImprovedHunger {

    public static final String MODID = "improvedhunger";
    public static final String NAME = "Improved Hunger";

    public static Logger log = LogManager.getLogger(NAME);

    private static ImprovedHunger instance;
    private static ModContainer modContainer;
    private final CommonProxy proxy;

    public ImprovedHunger() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_CONFIG);

        instance = this;
        modContainer = ModList.get().getModContainerById(MODID).get();

        this.proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        this.proxy.initialize();
        this.proxy.attachLifecycle(FMLJavaModLoadingContext.get().getModEventBus());
        this.proxy.attachEventHandlers(MinecraftForge.EVENT_BUS);

    }

    public static ImprovedHunger getInstance() { return instance; }

    public static ModContainer getModContainer() { return modContainer; }

    public static CommonProxy getProxy() { return getInstance().proxy; }

    public static ResourceLocation key(String path) { return new ResourceLocation(ImprovedHunger.MODID, path); }

    public static boolean isDoingDataGeneration() { return DatagenModLoader.isRunningDataGen(); }
}
