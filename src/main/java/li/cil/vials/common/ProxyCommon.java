package li.cil.vials.common;

import li.cil.vials.common.api.CreativeTab;
import li.cil.vials.common.init.Items;
import li.cil.vials.common.integration.Integration;
import li.cil.vials.common.integration.tconstruct.ProxyTinkersConstruct;
import li.cil.vials.common.integration.tconstruct.SmelteryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * Takes care of common setup.
 */
@Mod.EventBusSubscriber
public class ProxyCommon {

    private static ProxyCommon proxyCommon;

    public void onPreInit(final FMLPreInitializationEvent event) {
        proxyCommon = this;
        // Load our settings first to have all we need for remaining init.
        Settings.load(event.getSuggestedConfigurationFile());

        // Initialize API.
        API.creativeTab = new CreativeTab();

        // Mod integration.
        Integration.preInit(event);
    }

    public void onInit(final FMLInitializationEvent event) {

        // Mod integration.
        Integration.init(event);
    }

    public void onPostInit(final FMLPostInitializationEvent event) {
        // Mod integration.
        Integration.postInit(event);
    }


    // --------------------------------------------------------------------- //

//    @SubscribeEvent
//    public static void handleRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {
//        Blocks.register(event.getRegistry());
//    }

    @SubscribeEvent
    public static void handleRegisterItemsEvent(final RegistryEvent.Register<Item> event) {
        Items.register(event.getRegistry(), proxyCommon);
    }


    @SubscribeEvent
    public static void handleRegisterCraftingEvent(final RegistryEvent.Register<IRecipe> event) {
        Items.addRecipes(event.getRegistry());
    }

    public Item registerItem(final IForgeRegistry<Item> registry, final String name, final Item item) {
        registry.register(item.
                setUnlocalizedName(API.MOD_ID + "." + name).
                setCreativeTab(API.creativeTab).
                setRegistryName(name));
        if (Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID))
            SmelteryRegistration.registerTableCasting(item);
        return item;
    }

}
