package li.cil.vials.client;


import li.cil.vials.common.ProxyCommon;
import li.cil.vials.client.model.ModelVials;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.function.Supplier;

/**
 * Takes care of client-side only setup.
 */
public final class ProxyClient extends ProxyCommon {
    @Override
    public void onPreInit(final FMLPreInitializationEvent event) {
        super.onPreInit(event);
        ModelLoaderRegistry.registerLoader(ModelVials.LoaderVials.INSTANCE);
        //    MinecraftForge.EVENT_BUS.register(TextureLoader.INSTANCE);
    }

    @Override
    public void onInit(final FMLInitializationEvent event) {
        super.onInit(event);


    }

    // --------------------------------------------------------------------- //

    @Override
    public Block registerBlock(final String name, final Supplier<Block> constructor, final Class<? extends TileEntity> tileEntity) {
        final Block block = super.registerBlock(name, constructor, tileEntity);
        final Item item = Item.getItemFromBlock(block);
        assert item != null;
        setCustomItemModelResourceLocation(item);
        return block;
    }

    @Override
    public Item registerItem(final String name, Item item) {
        super.registerItem(name, item);
        setCustomItemModelResourceLocation(item);
        return item;
    }

    // --------------------------------------------------------------------- //

    private static void setCustomItemModelResourceLocation(final Item item) {
        final ResourceLocation registryName = item.getRegistryName();
        assert registryName != null;
       final ModelResourceLocation LOCATION = new ModelResourceLocation(registryName, "inventory");

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return LOCATION;
            }
        });
        ModelBakery.registerItemVariants(item, LOCATION);

    }
}
