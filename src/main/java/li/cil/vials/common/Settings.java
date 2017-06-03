package li.cil.vials.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * User configurable stuff via config file.
 */
public final class Settings {


    private static final String CONFIG_VERSION = "1";


    // --------------------------------------------------------------------- //

    public static void load(final File configFile) {
        final Configuration config = new Configuration(configFile, CONFIG_VERSION);

        config.load();

        upgradeConfig(config);



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
