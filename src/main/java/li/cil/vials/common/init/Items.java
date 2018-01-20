package li.cil.vials.common.init;


import li.cil.vials.common.Constants;
import li.cil.vials.common.ProxyCommon;
import li.cil.vials.common.Settings;
import li.cil.vials.common.integration.tconstruct.SmelteryRegistration;
import li.cil.vials.common.integration.tconstruct.ProxyTinkersConstruct;
import li.cil.vials.common.item.ItemVial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Manages setup, registration and lookup of items.
 */
public final class Items {

    public static Item vial_16;
    public static Item vial_144;
    public static Item vial_288;
    public static Item vial_432;
    public static Item vial_576;
    public static Item vial_1296;


    // --------------------------------------------------------------------- //

    public static void register(final ProxyCommon proxy) {
        int vial_small = Settings.vial_small;
        if (vial_small > 0)
            vial_16 = proxy.registerItem(Constants.NAME_VIAL_16, new ItemVial(vial_small));
        int vial_normal = Settings.vial_standard;
        if (vial_normal > 0)
            vial_144 = proxy.registerItem(Constants.NAME_VIAL_144, new ItemVial(vial_normal));
        int vial_large = Settings.vial_large;
        if (vial_large > 0)
            vial_288 = proxy.registerItem(Constants.NAME_VIAL_288, new ItemVial(vial_large));
        int vial_huge = Settings.vial_huge;
        if (vial_huge > 0)
            vial_432 = proxy.registerItem(Constants.NAME_VIAL_432, new ItemVial(vial_huge));
        int vial_giant = Settings.vial_giant;
        if (vial_giant > 0)
            vial_576 = proxy.registerItem(Constants.NAME_VIAL_576, new ItemVial(vial_giant));
        int vial_extreme = Settings.vial_extreme;
        if (vial_extreme > 0)
            vial_1296 = proxy.registerItem(Constants.NAME_VIAL_1296, new ItemVial(vial_extreme));


    }

    public static void addRecipes() {
        if (Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID)) {
            SmelteryRegistration.addReciepes();
        } else {
            ArrayList<String> oreDictIngots = new ArrayList<>();
            ArrayList<String> oreDictNuggets = new ArrayList<>();
            Pattern patternIngot = Pattern.compile("^ingot[A-Z].*$");
            Pattern patternNugget = Pattern.compile("^nugget[A-Z].*$");

            for (String s : OreDictionary.getOreNames()) {
                if (patternIngot.matcher(s).matches()) {
                    oreDictIngots.add(s);
                } else if (patternNugget.matcher(s).matches()) {
                    oreDictNuggets.add(s);
                }
            }
            for (String oreDictNugget : oreDictNuggets) {
                GameRegistry.addRecipe(new ShapedOreRecipe(vial_16,
                        " p ",
                        "pip",
                        " p ",
                        'i', oreDictNugget,
                        'p', "paneGlass"));

            }
            for (String oreDict : oreDictIngots) {
                GameRegistry.addRecipe(new ShapedOreRecipe(vial_144,
                        " g ",
                        "gig",
                        " g ",
                        'i', oreDict,
                        'g', "blockGlass"));


            }
            GameRegistry.addRecipe(new ShapedOreRecipe(vial_288,
                    " g ",
                    "gig",
                    "ggg",
                    'i', vial_144,
                    'g', "blockGlass"));
            GameRegistry.addRecipe(new ShapedOreRecipe(vial_432,
                    " g ",
                    "gig",
                    "ggg",
                    'i', vial_288,
                    'g', "blockGlass"));
            GameRegistry.addRecipe(new ShapedOreRecipe(vial_576,
                    " g ",
                    "gig",
                    "ggg",
                    'i', vial_432,
                    'g', "blockGlass"));
            GameRegistry.addRecipe(new ShapedOreRecipe(vial_1296,
                    "ggg",
                    "gig",
                    "ggg",
                    'i', vial_576,
                    'g', "blockGlass"));

        }
    }


    // --------------------------------------------------------------------- //

    private Items() {
    }
}
