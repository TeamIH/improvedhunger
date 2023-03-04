package teamih.improvedhunger.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigHandler {

    private static final Builder SERVER_BUILDER = new Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static final String CATEGORY_HUNGER = "hunger";

    public static IntValue HUNGERDECAYMODIFIER;
    public static IntValue CONSUMPTIONMODIFIER;

    public static DoubleValue CONSTANTHUNGER;

    public static BooleanValue DEFAULTCONSUMPTION;

    static {
        setupHungerConfig();
        setupConsumptionSpeed();

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

    private static void setupConsumptionSpeed() {

        DEFAULTCONSUMPTION = ConfigHandler.SERVER_BUILDER
                .comment("Use vanilla food consumption rates Default: False")
                .define("vanillaConsumptionRate", false);

        CONSUMPTIONMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Modifier value with consumption rate Default: 8")
                .comment("Lower values produce faster consumption rates")
                .comment("Consumption rate is also dependent on hunger restored")
                .comment("Default value 8 hunger foods such as steak take a little over double the time to consume")
                .comment("At max value 1 hunger foods will take as long as vanilla system")
                .defineInRange("consumptionRateModifier", 8, 0, 16);
    }
}
