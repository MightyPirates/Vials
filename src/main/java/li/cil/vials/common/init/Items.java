package li.cil.vials.common.init;


import li.cil.vials.common.Constants;
import li.cil.vials.common.ProxyCommon;
import li.cil.vials.common.integration.tconstruct.SmelteryRegistration;
import li.cil.vials.common.integration.tconstruct.ProxyTinkersConstruct;
import li.cil.vials.common.item.ItemVial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

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

        vial_16 = proxy.registerItem(Constants.NAME_VIAL_16, new ItemVial(16));
        vial_144 = proxy.registerItem(Constants.NAME_VIAL_144, new ItemVial(144));
        vial_288 = proxy.registerItem(Constants.NAME_VIAL_288, new ItemVial(2 * 144));
        vial_432 = proxy.registerItem(Constants.NAME_VIAL_432, new ItemVial(3 * 144));
        vial_576 = proxy.registerItem(Constants.NAME_VIAL_576, new ItemVial(4 * 144));
        vial_1296 = proxy.registerItem(Constants.NAME_VIAL_1296, new ItemVial(9 * 144));


    }

    public static void addRecipes() {
        if(Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID)){
            SmelteryRegistration.addReciepes();
        }
        else {
            ArrayList<String> oreDictIngots = new ArrayList<>();
            ArrayList<String> oreDictNuggets = new ArrayList<>();
            for (String s : OreDictionary.getOreNames()) {
                if(s.contains("ingot")){
                    oreDictIngots.add(s);
                }
                else if(s.contains("nugget")){
                    oreDictNuggets.add(s);
                }
            }
            for (String oreDictNugget : oreDictNuggets) {
                GameRegistry.addRecipe(new ShapedOreRecipe(vial_16,
                        " p ",
                        "pip",
                        " p ",
                        'i',oreDictNugget,
                        'p',"paneGlass"));

            }
            for (String oreDict : oreDictIngots) {
                GameRegistry.addRecipe(new ShapedOreRecipe(vial_144,
                        " g ",
                        "gig",
                        " g ",
                        'i',oreDict,
                        'g',"blockGlass"));


            }
        GameRegistry.addRecipe(new ShapedOreRecipe(vial_288,
                " g ",
                "gig",
                "ggg",
                'i',vial_144,
                'g',"blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(vial_432,
                " g ",
                "gig",
                "ggg",
                'i',vial_288,
                'g',"blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(vial_576,
                " g ",
                "gig",
                "ggg",
                'i',vial_432,
                'g',"blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(vial_1296,
                "ggg",
                "gig",
                "ggg",
                'i',vial_576,
                'g',"blockGlass"));

        }
    }


    // --------------------------------------------------------------------- //

    private Items() {
    }
}
