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
    public static final String SUBCATEGORY_ABSORB = "absorb";
    public static final String SUBCATEGORY_RESISTANCE = "resistance";
    public static final String SUBCATEGORY_HASTE = "haste";
    public static final String SUBCATEGORY_SPEED = "speed";
    public static final String SUBCATEGORY_STRENGTH = "strength";
    public static final String SUBCATEGORY_FATIGUE = "mining fatigue";
    public static final String SUBCATEGORY_SLOWNESS = "slowness";
    public static final String SUBCATEGORY_WEAKNESS = "weakness";
    public static final String SUBCATEGORY_LEVEL = "debufflevel";
    public static final String SUBCATEGORY_DEBUFFDURATION = "debuffduration";
    public static final String SUBCATEGORY_BUFFDURATION = "buffduration";

    public static IntValue HUNGERDECAYMODIFIER;
    public static IntValue CONSUMPTIONMODIFIER;
    public static IntValue WELLFEDMINHUNGER;
    public static IntValue ABSORBMINHUNGER;
    public static IntValue RESISTANCEMINHUNGER;
    public static IntValue HASTEHUNGER;
    public static IntValue SPEEDHUNGER;
    public static IntValue STRENGTHHUNGER;
    public static IntValue FATIGUEHUNGER;
    public static IntValue SLOWNESSHUNGER;
    public static IntValue WEAKNESSHUNGER;
    public static IntValue DEBUFFLEVEL;
    public static IntValue DEBUFFDURATION;
    public static IntValue BUFFDURATION;


    public static DoubleValue CONSTANTHUNGER;
    public static DoubleValue WELLFEDHEALING;
    public static DoubleValue WELLFEDDECAYMODIFIER;
    public static DoubleValue ABSORBDURATIONMODIFIER;
    public static DoubleValue RESISTANCEDURATIONMODIFIER;
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
                .comment("Scales all hunger decay by a percentage from 0% to 200%")
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

        ConfigHandler.SERVER_BUILDER.comment("Buff Duration").push(SUBCATEGORY_BUFFDURATION);

        BUFFDURATION = ConfigHandler.SERVER_BUILDER
                .comment("Buff Duration (Default: 30)")
                .comment("Duration in seconds, Min 15 Max 120")
                .comment("Effects duration of Haste, Speed, and Strength buffs")
                .defineInRange("buffduration", 30, 15, 120);

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
                .defineInRange("wellfedminimumhunger", 2, 1, 20);

        WELLFEDDECAYMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Well fed buff hunger decay modifier (Default: 0.5)")
                .comment("Hunger decay multiplied by this modifier while under Well Fed buff")
                .comment("NOTE: default halves the rate. 0 = no decay, 1 = normal decay")
                .defineInRange("wellfedhungerdecay", 0.5, 0.0, 1.0);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Absorption Buff").push(SUBCATEGORY_ABSORB);

        ABSORBMINHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger value (Default: 4)")
                .defineInRange("absorbminimumhunger", 5, 2, 8);

        ABSORBDURATIONMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Absorb buff duration modifier (Default: 0.75)")
                .comment("Duration set to Wellfed duration x modifier.")
                .comment("NOTE: default sets duration to 75% of well fed. 0 = no absorb, 1 = same duration as well fed)")
                .defineInRange("absorbdurationmodifier", 0.75, 0.0, 1.0);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Resistance Buff").push(SUBCATEGORY_RESISTANCE);

        RESISTANCEMINHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger value (Default: 4)")
                .defineInRange("resistanceminimumhunger", 5, 2, 8);

        RESISTANCEDURATIONMODIFIER = ConfigHandler.SERVER_BUILDER
                .comment("Resistance buff duration modifier (Default: 0.75)")
                .comment("Duration set to Absorption duration x modifier.")
                .comment("NOTE: default sets duration to 75% of absorption buff. 0 = no resistance, 1 = same duration as absorption)")
                .defineInRange("resistancedurationmodifier", 0.75, 0.0, 1.0);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Haste Buff").push(SUBCATEGORY_HASTE);

        HASTEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related haste buff (Default: true)")
                .define("usehastebuff", true);

        HASTEHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger level for haste to be applied (Default: 11)")
                .defineInRange("hasteminimumhunger", 11, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Speed Buff").push(SUBCATEGORY_SPEED);

        SPEEDBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related speed buff (Default: true)")
                .define("usespeedbuff", true);

        SPEEDHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Minimum hunger level for speed to be applied (Default: 15)")
                .defineInRange("speedminimumhunger", 15, 0, 20);

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

        ConfigHandler.SERVER_BUILDER.comment("Debuff Duration").push(SUBCATEGORY_DEBUFFDURATION);

        DEBUFFDURATION = ConfigHandler.SERVER_BUILDER
                .comment("Debuff Duration (Default: 30)")
                .comment("Duration in seconds. Min 15 Max 120")
                .comment("Effects duration of Fatigue, Slowness, and Weakness")
                .defineInRange("debuffduration", 30, 15, 120);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Debuff Max Level").push(SUBCATEGORY_LEVEL);

        DEBUFFLEVEL = ConfigHandler.SERVER_BUILDER
                .comment("Debuff Max Level (Default: 1)")
                .comment("Sets the maximum level of the debuffs")
                .defineInRange("debufflevel", 1, 1, 3);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Mining Fatigue Debuff").push(SUBCATEGORY_FATIGUE);

        FATIGUEDEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related mining fatigue debuff (Default: true)")
                .define("useminingfatigedebuff", true);

        FATIGUEHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Maximum hunger level for mining fatigue to be applied (Default: 9)")
                .defineInRange("fatiguemaximumhunger", 9, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Slowness Debuff").push(SUBCATEGORY_SLOWNESS);

        SLOWNESSDEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("Use hunger related slowness debuff (Default: true)")
                .define("useslownessdebuff", true);

        SLOWNESSHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Maximum hunger level for slowness to be applied (Default: 6)")
                .defineInRange("slownessmaximumhunger", 6, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.comment("Weakness Debuff").push(SUBCATEGORY_WEAKNESS);

        WEAKNESSDEBUFF = ConfigHandler.SERVER_BUILDER
                .comment("User hunger related weakness debuff (Default: true)")
                .define("userweaknessdebuff", true);

        WEAKNESSHUNGER = ConfigHandler.SERVER_BUILDER
                .comment("Maximum hunger level for weakness to be applied (Default: 3)")
                .defineInRange("weaknessmaximumhunger", 3, 0, 20);

        ConfigHandler.SERVER_BUILDER.pop();

        ConfigHandler.SERVER_BUILDER.pop();
    }
}
