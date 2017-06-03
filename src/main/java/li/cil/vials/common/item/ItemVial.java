package li.cil.vials.common.item;

import li.cil.vials.common.API;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by lordjoda on 15.04.2017.
 */
public class ItemVial extends ItemFluidContainer {
    /**
     * @param capacity The maximum capacity of this fluid container_normal.
     */
    public ItemVial(int capacity) {
        super(capacity);

    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(I18n.format(API.MOD_ID + "." + "CAPACITY",capacity) );
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stack);
        assert fluidHandler != null;
        IFluidTankProperties iFluidTankProperties = fluidHandler.getTankProperties()[0];
        String itemStackDisplayName = super.getItemStackDisplayName(stack);
        if (iFluidTankProperties.getContents() != null) {
            itemStackDisplayName += " " + iFluidTankProperties.getContents().getLocalizedName();
        }
        return itemStackDisplayName;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack,capacity){
            @Override
            public int fill(FluidStack resource, boolean doFill)
            {
                if (container.getCount() != 1 || resource == null || resource.amount <capacity || getFluid() != null || !canFillFluidType(resource))
                {
                    return 0;
                }

                if (doFill)
                {
                    FluidStack filled = resource.copy();
                    setFluid(filled);
                }

                return capacity;
            }

            @Nullable
            @Override
            public FluidStack drain(FluidStack resource, boolean doDrain)
            {
                if ( resource == null || resource.amount < capacity)
                {
                    return null;
                }

                FluidStack fluidStack = getFluid();
                if (fluidStack != null && fluidStack.isFluidEqual(resource))
                {
                    if (doDrain)
                    {
                        setFluid(null);
                    }
                    return fluidStack;
                }

                return null;
            }

            @Override
            public FluidStack drain(int maxDrain, boolean doDrain) {
                if (container.getCount() != 1 || maxDrain <= 0)
                {
                    return null;
                }

                FluidStack contained = getFluid();
                if (contained == null || contained.amount <= 0 || !canDrainFluidType(contained))
                {
                    return null;
                }
                if( maxDrain < capacity)
                    return null;

                final int drainAmount = Math.min(contained.amount, maxDrain);

                FluidStack drained = contained.copy();
                drained.amount = drainAmount;

                if (doDrain)
                {
                    contained.amount -= drainAmount;
                    if (contained.amount == 0)
                    {
                        setContainerToEmpty();
                    }
                    else
                    {
                        setFluid(contained);
                    }
                }

                return drained;
            }
        };
    }
}
