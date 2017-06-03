package li.cil.vials.common.api;

import li.cil.vials.common.init.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Our creative tab! There are many like it, but this one is... kind of the same. Oh well.
 */
public final class CreativeTab extends CreativeTabs {
    public CreativeTab() {
        super("vials");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem() {
        final Item item = Items.vial_144;
        assert item != null;
        return item;
    }
}
