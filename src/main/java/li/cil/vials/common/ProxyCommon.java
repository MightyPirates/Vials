package li.cil.vials.common;

import li.cil.vials.common.api.CreativeTab;
import li.cil.vials.common.init.Items;
import li.cil.vials.common.integration.Integration;
import li.cil.vials.common.integration.tconstruct.ProxyTinkersConstruct;
import li.cil.vials.common.integration.tconstruct.SmelteryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.function.Supplier;

/**
 * Takes care of common setup.
 */
public class ProxyCommon {
    public void onPreInit(final FMLPreInitializationEvent event) {
        // Load our settings first to have all we need for remaining init.
        Settings.load(event.getSuggestedConfigurationFile());

        // Initialize API.
        API.creativeTab = new CreativeTab();


        // Register blocks and items.
        Items.register(this);

        // Mod integration.
        Integration.preInit(event);
    }

    public void onInit(final FMLInitializationEvent event) {
        // Register Ore Dictionary entries.


        // Hardcoded recipes!
        Items.addRecipes();


        // Mod integration.
        Integration.init(event);
    }

    public void onPostInit(final FMLPostInitializationEvent event) {
        // Mod integration.
        Integration.postInit(event);
    }

    public void onMissingMappings(final FMLMissingMappingsEvent event) {


    }

    // --------------------------------------------------------------------- //

    public Block registerBlock(final String name, final Supplier<Block> constructor, final Class<? extends TileEntity> tileEntity) {
        final Block block = constructor.get().
                setHardness(5).
                setResistance(10).
                setUnlocalizedName(API.MOD_ID + "." + name).
                setCreativeTab(API.creativeTab).
                setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.registerTileEntityWithAlternatives(tileEntity, API.MOD_ID + ": " + name, name);

        final Item itemBlock = new ItemBlock(block).
                setRegistryName(name);
        GameRegistry.register(itemBlock);

        return block;
    }

    public Item registerItem(final String name, Item item) {
        item.setUnlocalizedName(API.MOD_ID + "." + name).
                setCreativeTab(API.creativeTab).
                setRegistryName(name);
        GameRegistry.register(item);
        MinecraftForge.EVENT_BUS.register(item);
        if (Loader.isModLoaded(ProxyTinkersConstruct.MOD_ID))
            SmelteryRegistration.registerTableCasting(item);

        return item;
    }


}
