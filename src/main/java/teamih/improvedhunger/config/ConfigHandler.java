package teamih.improvedhunger.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigHandler {

    private static final Builder SERVER_BUILDER = new Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static final String CATEGORY_HUNGER = "hunger";
    public static final String CATEGORY_BUFFS = "buffs";
    public static final String CATEGORY_DEBUFFS = "debuffs";

    public static final String SUBCATEGORY_WELLFED = "wellfed";
    public static final String SUBCATEGORY_HASTE = "haste";
    public static final String SUBCATEGORY_SPEED = "speed";
    public static final String SUBCATEGORY_STRENGTH = "strength";
    public static final String SUBCATEGORY_FATIGUE = "mining fatigue";
    public static final String SUBCATEGORY_SLOWNESS = "slowness";
    public static final String SUBCATEGORY_WEAKNESS = "weakness";

    public static IntValue HUNGERDECAYMODIFIER;
    public static IntValue CONSUMPTIONMODIFIER;
    public static IntValue WELLFEDMINHUNGER;
    public static IntValue HASTEHUNGER;
    public static IntValue SPEEDHUNGER;
    public static IntValue STRENGTHHUNGER;
    public static IntValue FATIGUEHUNGER;
    public static IntValue SLOWNESSHUNGER;
    public static IntValue WEAKNESSHUNGER;


    public static DoubleValue CONSTANTHUNGER;
    public static DoubleValue WELLFEDHEALING;
    public static DoubleValue WELLFEDDECAYMODIFIER;

    public static BooleanValue DEFAULTCONSUMPTION;
    public static BooleanValue HASTEBUFF;
    public static BooleanValue SPEEDBUFF;
    public static BooleanValue STRENGTHBUFF;
    public static BooleanValue FATIGUEDEBUFF;
    public static BooleanValue SLOWNESSDEBUFF;
    public static BooleanValue WEAKNESSDEBUFF;

    static {
        setupHungerConfig();
        setupBuffConfig();
        setupDebuffConfig();

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

        ConfigHandler.SERVER_BUILDER.comment("Haste Buff").push(SUBCATEGORY_HASTE);

        HASTEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related haste buff (Default: true)")
                .define("usehastebuff", true);

        HASTEHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger level for haste to be applied (Default: 14)")
                .defineInRange("hasteminimumhunger", 14, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Speed Buff").push(SUBCATEGORY_SPEED);

        SPEEDBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related speed buff (Default: true)")
                .define("usespeedbuff", true);

        SPEEDHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger level for speed to be applied (Default: 16)")
                .defineInRange("speedminimumhunger", 16, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Strength Buff").push(SUBCATEGORY_STRENGTH);

        STRENGTHBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related strength buff (Default: true)")
                .define("usestrengthbuff", true);

        STRENGTHHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger level for strength to be applied (Default: 18)")
                .defineInRange("strengthminimumhunger", 18, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.pop();

    }

    private static void setupDebuffConfig() {

        ConfigHandler.SERVER_BUILDER.comment("Debuff Settings").push(CATEGORY_DEBUFFS);

        ConfigHandler.SERVER_BUILDER.comment("Mining Fatigue Debuff").push(SUBCATEGORY_FATIGUE);

        FATIGUEDEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related mining fatigue debuff (Default: true)")
                .define("useminingfatige", true);

        FATIGUEHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Maximum hunger level for mining fatigue to be applied (Default: 6)")
                .defineInRange("fatiguemaximumhunger", 6, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.pop();
    }
}
