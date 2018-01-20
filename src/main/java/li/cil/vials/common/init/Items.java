package li.cil.vials.common.init;


import li.cil.vials.common.API;
import li.cil.vials.common.Constants;
import li.cil.vials.common.ProxyCommon;
import li.cil.vials.common.Settings;
import li.cil.vials.common.integration.tconstruct.SmelteryRegistration;
import li.cil.vials.common.integration.tconstruct.ProxyTinkersConstruct;
import li.cil.vials.common.item.ItemVial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

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

    public static void register(IForgeRegistry<Item> registry, ProxyCommon proxy) {

        int vial_small = Settings.vial_small;
        if (vial_small > 0)
            vial_16 = proxy.registerItem(registry,Constants.NAME_VIAL_16, new ItemVial(vial_small));
        int vial_normal = Settings.vial_standard;
        if (vial_normal > 0)
            vial_144 = proxy.registerItem(registry,Constants.NAME_VIAL_144, new ItemVial(vial_normal));
        int vial_large = Settings.vial_large;
        if (vial_large > 0)
            vial_288 = proxy.registerItem(registry,Constants.NAME_VIAL_288, new ItemVial(vial_large));
        int vial_huge = Settings.vial_huge;
        if (vial_huge > 0)
            vial_432 = proxy.registerItem(registry,Constants.NAME_VIAL_432, new ItemVial(vial_huge));
        int vial_giant = Settings.vial_giant;
        if (vial_giant > 0)
            vial_576 = proxy.registerItem(registry,Constants.NAME_VIAL_576, new ItemVial(vial_giant));
        int vial_extreme = Settings.vial_extreme;
        if (vial_extreme > 0)
            vial_1296 = proxy.registerItem(registry,Constants.NAME_VIAL_1296, new ItemVial(vial_extreme));

    }
//    public Item registerItem(final String name, Item item) {
//        item.setUnlocalizedName(API.MOD_ID + "." + name).
//                setCreativeTab(API.creativeTab).
//                setRegistryName(name);
//
//        MinecraftForge.EVENT_BUS.register(item);
//        if (Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID))
//            SmelteryRegistration.registerTableCasting(item);
//
//        return item;
//    }

    public static void addRecipes(final IForgeRegistry<IRecipe> registry) {
        if(Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID)){
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

        Ingredient paneGlass = new OreIngredient("paneGlass");
        Ingredient glass = new OreIngredient("blockGlass");

        for (String oreDictNugget : oreDictNuggets) {
            NonNullList<Ingredient> objects = NonNullList.withSize(3 * 3, Ingredient.EMPTY);
            Ingredient oreIngredient = new OreIngredient(oreDictNugget);

            objects.set(1, paneGlass);
            objects.set(3, paneGlass);
            objects.set(4, oreIngredient);
            objects.set(5, paneGlass);
            objects.set(7, paneGlass);


            ShapedRecipes value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_16));
            value.setRegistryName(Constants.NAME_VIAL_16);
            registry.register(value);


        }
        for (String oreDict : oreDictIngots) {
            NonNullList<Ingredient> objects = NonNullList.withSize(3 * 3, Ingredient.EMPTY);
            Ingredient oreIngredient = new OreIngredient(oreDict);
            objects.set(1, paneGlass);
            objects.set(3, paneGlass);
            objects.set(4, oreIngredient);
            objects.set(5, paneGlass);
            objects.set(7, paneGlass);


            ShapedRecipes value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_144));
            value.setRegistryName(Constants.NAME_VIAL_144);
            registry.register(value);


        }


//        GameRegistry.addRecipe(new ShapedOreRecipe(vial_288,
//                        " g ",
//                        "gig",
//                        "ggg",
//                        'i',vial_144,
//                        'g',"blockGlass"));

        NonNullList<Ingredient> objects = NonNullList.withSize(3 * 3, glass);
        objects.set(0, Ingredient.EMPTY);
        objects.set(2, Ingredient.EMPTY);
        objects.set(4, Ingredient.fromItem(vial_144));
        ShapedRecipes value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_288));
        value.setRegistryName(Constants.NAME_VIAL_288);
        registry.register(value);

//        GameRegistry.addRecipe(new ShapedOreRecipe(vial_432,
//                " g ",
//                "gig",
//                "ggg",
//                'i',vial_288,
//                'g',"blockGlass"));
        objects = NonNullList.withSize(3 * 3, glass);
        objects.set(0, Ingredient.EMPTY);
        objects.set(2, Ingredient.EMPTY);
        objects.set(4, Ingredient.fromItem(vial_288));
        value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_432));
        value.setRegistryName(Constants.NAME_VIAL_432);
        registry.register(value);

//        GameRegistry.addRecipe(new ShapedOreRecipe(vial_576,
//                " g ",
//                "gig",
//                "ggg",
//                'i',vial_432,
//                'g',"blockGlass"));
        objects = NonNullList.withSize(3 * 3, glass);
        objects.set(0, Ingredient.EMPTY);
        objects.set(2, Ingredient.EMPTY);
        objects.set(4, Ingredient.fromItem(vial_432));
        value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_576));
        value.setRegistryName(Constants.NAME_VIAL_576);
        registry.register(value);

//        GameRegistry.addRecipe(new ShapedOreRecipe(vial_1296,
//                "ggg",
//                "gig",
//                "ggg",
//                'i',vial_576,
//                'g',"blockGlass"));
        objects = NonNullList.withSize(3 * 3, glass);
        objects.set(4, Ingredient.fromItem(vial_576));
        value = new ShapedRecipes(API.MOD_ID, 3, 3, objects, new ItemStack(vial_1296));
        value.setRegistryName(Constants.NAME_VIAL_1296);
        registry.register(value);

    }
    }


    // --------------------------------------------------------------------- //

    private Items() {
    }

}
