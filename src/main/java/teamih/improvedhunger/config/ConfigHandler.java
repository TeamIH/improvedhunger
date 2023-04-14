package teamih.improvedhunger.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigHandler {

    private static final Builder SERVER_BUILDER = new Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static final String CATEGORY_HUNGER = "hunger";
    public static final String CATEGORY_BUFFS = "buffs";

    public static final String SUBCATEGORY_WELLFED = "wellfed";

    public static IntValue HUNGERDECAYMODIFIER;
    public static IntValue CONSUMPTIONMODIFIER;
    public static IntValue WELLFEDMINHUNGER;


    public static DoubleValue CONSTANTHUNGER;
    public static DoubleValue WELLFEDHEALING;
    public static DoubleValue WELLFEDDECAYMODIFIER;

    public static BooleanValue DEFAULTCONSUMPTION;

    static {
        setupHungerConfig();
        setupBuffConfig();

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

        ConfigHandler.SERVER_BUILDER.pop();

    }

    private static void setupBuffConfig() {

        ConfigHandler.SERVER_BUILDER.comment("Buff settings").push(CATEGORY_BUFFS);

        ConfigHandler.SERVER_BUILDER.comment("Well Fed Buff").push(SUBCATEGORY_WELLFED);

        WELLFEDHEALING = ConfigHandler.SERVER_BUILDER
                .comment("Amount of health to restore per second during well fed buff (Default: 0.5)")
                .defineInRange("wellfedhealingpersecond", 0.5, 0.0, 1.0);

        WELLFEDMINHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger value (Default: 2)")
                .comment("The length of the buff is calculated on the following formula for a curve:")
                .comment("")
                .comment("([min]*[food]^2) + ([min]*[food]) - (([min]*[min]^2) + ([min]*[min]))")
                .comment("")
                .comment("The curve rewards an exponentially longer buff for more filling food.")
                .comment("NOTE: the buff is not applied for foods of the minimum hunger restored and less")
                .defineInRange("wellfedminimumhunger", 2, 1, 4);

        WELLFEDDECAYMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Well fed buff hunger decay modifier (Default: 0.5)")
                .comment("Hunger decay multiplied by this modifier while under Well Fed buff")
                .comment("NOTE: default halves the rate. 0 = no decay, 1 = normal decay")
                .defineInRange("wellfedhungerdecay", 0.5, 0.0, 1.0);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.pop();

    }
}
