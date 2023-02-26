package teamih.improvedhunger.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigHandler {

    private static final Builder SERVER_BUILDER = new Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static final String CATEGORY_HUNGER = "hunger";

    public static IntValue HUNGERDECAYMODIFIER;
    public static DoubleValue CONSTANTHUNGER;

    static {
        setupHungerConfig();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    private static void setupHungerConfig() {

        ConfigHandler.SERVER_BUILDER.comment("Hunger decay settings").push(CATEGORY_HUNGER);

        HUNGERDECAYMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Hunger decay modifier % Default: 100")
                .defineInRange("hungerDecayModifier", 100, 0, 200);

        CONSTANTHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Constant hunger decay - value to add to exhaustion each Player tick Default: 0.01")
                .defineInRange("constantHungerDecayAmount", 0.01, 0.0, 1.0);

        ConfigHandler.SERVER_BUILDER.pop();
    }
}
