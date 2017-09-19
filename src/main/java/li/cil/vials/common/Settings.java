package li.cil.vials.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * User configurable stuff via config file.
 */
public final class Settings {

    public static int fillingTime = 5;
    public static int vial_small = 16;
    public static int vial_standard = 144;
    public static int vial_large = 2 * 144;
    public static int vial_huge = 3 * 144;
    public static int vial_giant = 4 * 144;
    public static int vial_extreme = 9 * 144;


    private static final String CONFIG_VERSION = "1";

    private static final String CATEGORY_INTEGRATION = "integration";
    private static final String CATEGORY_VIALS = "vials";



    private static final String FILLING_TIME = "hardeningTime";
    private static final String VIAL_SMALL = "vialSmall";
    private static final String VIAL_STANDARD = "vialStandard";
    private static final String VIAL_LARGE = "vialLarge";
    private static final String VIAL_HUGE = "vialHuge";
    private static final String VIAL_GIANT = "vialGiant";
    private static final String VIAL_EXTREME = "vialExtreme";



    private static final String COMMENT_FILLING_TIME = "Time the fluid takes to 'harden' in the casting basin of Tinkers Construct, when filling a vial from a smeltery.\n" +
            "This does not change the time in which the fluid 'flows', just the time after this is completed.";
    private static final String COMMENT_VIAL_SMALL = "Capacity for the Small Vial. 0 to deactivate";
    private static final String COMMENT_VIAL_STANDARD = "Capacity for the Standard Vial. 0 to deactivate";
    private static final String COMMENT_VIAL_LARGE = "Capacity for the Large Vial. 0 to deactivate";
    private static final String COMMENT_VIAL_HUGE = "Capacity for the Huge Vial. 0 to deactivate";
    private static final String COMMENT_VIAL_GIANT = "Capacity for the Giant Vial. 0 to deactivate";
    private static final String COMMENT_VIAL_EXTREME= "Capacity for the Extreme Vial. 0 to deactivate";

    // --------------------------------------------------------------------- //

    public static void load(final File configFile) {
        final Configuration config = new Configuration(configFile, CONFIG_VERSION);

        config.load();

        upgradeConfig(config);

        fillingTime = config.getInt(FILLING_TIME,CATEGORY_INTEGRATION,
                fillingTime,1,1000,COMMENT_FILLING_TIME);


        vial_small = config.getInt(VIAL_SMALL,CATEGORY_VIALS,
                vial_small,0,Integer.MAX_VALUE,COMMENT_VIAL_SMALL);
        vial_standard = config.getInt(VIAL_STANDARD,CATEGORY_VIALS,
                vial_standard,0,Integer.MAX_VALUE,COMMENT_VIAL_STANDARD);
        vial_large = config.getInt(VIAL_LARGE,CATEGORY_VIALS,
                vial_large,0,Integer.MAX_VALUE,COMMENT_VIAL_LARGE);
        vial_huge = config.getInt(VIAL_HUGE,CATEGORY_VIALS,
                vial_huge,0,Integer.MAX_VALUE,COMMENT_VIAL_HUGE);
        vial_giant = config.getInt(VIAL_GIANT,CATEGORY_VIALS,
                vial_giant,0,Integer.MAX_VALUE,COMMENT_VIAL_GIANT);
        vial_extreme = config.getInt(VIAL_EXTREME,CATEGORY_VIALS,
                vial_extreme,0,Integer.MAX_VALUE,COMMENT_VIAL_EXTREME);


        if (config.hasChanged()) {
            config.save();
        }
    }

    private static void upgradeConfig(final Configuration config) {
        final String loadedVersion = config.getLoadedConfigVersion();
        int loadedVersionInt;
        try {
            loadedVersionInt = Integer.parseInt(loadedVersion);
        } catch (final NumberFormatException e) {
            loadedVersionInt = 0;
        }

        // Incremental upgrade logic: fall through starting at old version.
        switch (loadedVersionInt) {
            case 0:

            default:
                break;
        }
    }



    // --------------------------------------------------------------------- //

    private Settings() {
    }
}
