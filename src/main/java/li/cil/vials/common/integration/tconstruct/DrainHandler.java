package li.cil.vials.common.integration.tconstruct;

import li.cil.vials.common.item.ItemVial;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.smeltery.block.BlockSmelteryIO;

public class DrainHandler {
    public static EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (pos != null) {
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block instanceof BlockSmelteryIO) {
                IFluidHandler fluidHandlerItem = FluidUtil.getFluidHandler(stack);
                IFluidHandler fluidHandlerDrain = getFluidHandler(pos, side, world);

                if (fluidHandlerItem != null) {
                    IFluidTankProperties[] tankProperties = fluidHandlerItem.getTankProperties();
                    if (tankProperties.length != 0) {
                        FluidStack contents = tankProperties[0].getContents();
                        IItemHandler playerInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                        //if full empty vial
                        if (contents != null && contents.amount > 0) {
                            FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainerAndStow(stack, fluidHandlerDrain, playerInventory, tankProperties[0].getCapacity(), player);
                            if(fluidActionResult.isSuccess()){
                               player.setHeldItem(hand,fluidActionResult.getResult());
                            }
                        } else {
                            FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(stack, fluidHandlerDrain, playerInventory, tankProperties[0].getCapacity(), player);
                            if(fluidActionResult.isSuccess()){
                                player.setHeldItem(hand,fluidActionResult.getResult());
                            }
                        }

                        return EnumActionResult.SUCCESS;
                    }
                }


            } else {
                return EnumActionResult.PASS;
            }

        }
        return EnumActionResult.PASS;
    }

    protected static IFluidHandler getFluidHandler(BlockPos pos, EnumFacing direction, World world) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction)) {
            return te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
        }
        return null;
    }
}
