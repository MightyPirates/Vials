package li.cil.vials.common.integration.tconstruct;

import li.cil.vials.common.Settings;
import li.cil.vials.common.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by lordjoda on 17.04.2017.
 */
public class SmelteryRegistration {
    public static void registerTableCasting(Item item) {
        IFluidHandler fluidHandler = FluidUtil.getFluidHandler(new ItemStack(item));
        IFluidTankProperties iFluidTankProperties = fluidHandler.getTankProperties()[0];

        TinkerRegistry.registerBasinCasting(new ICastingRecipe() {
            @Override
            public ItemStack getResult(@Nullable ItemStack cast, Fluid fluid) {
                ItemStack output = new ItemStack(item);
                IFluidHandler fluidHandler = FluidUtil.getFluidHandler(output);
                fluidHandler.fill(getFluid(cast,fluid),true);
                return output;
            }



            @Override
            public boolean matches(@Nullable ItemStack cast, Fluid fluid) {

                if (cast != null && cast.getItem() == item) {
                    IFluidHandler fluidHandler = FluidUtil.getFluidHandler(cast);
                    assert fluidHandler != null;
                    IFluidTankProperties iFluidTankProperties = fluidHandler.getTankProperties()[0];

                    return iFluidTankProperties.getContents() == null;
                }
                return false;
            }
            @Override
            public boolean switchOutputs() {
                return false;
            }

            @Override
            public boolean consumesCast() {
                return true;
            }

            @Override
            public int getTime() {
                return Settings.fillingTime;
            }

            @Override
            public int getFluidAmount() {
                return iFluidTankProperties.getCapacity();
            }
        });
    }

    public static void addReciepes(){
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
            for (ItemStack itemStack : OreDictionary.getOres(oreDictNugget)) {
                TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_16), itemStack, FluidRegistry.getFluid("glass"),1500);
            }

        }
        for (String oreDict : oreDictIngots) {
            for (ItemStack itemStack : OreDictionary.getOres(oreDict)) {
                TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_144), itemStack, FluidRegistry.getFluid("glass"),4000);
            }

        }
        TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_288),new ItemStack(Items.vial_144), FluidRegistry.getFluid("glass"),6000);
        TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_432),new ItemStack(Items.vial_288), FluidRegistry.getFluid("glass"),6000);
        TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_576),new ItemStack(Items.vial_432), FluidRegistry.getFluid("glass"),6000);
        TinkerRegistry.registerTableCasting(new ItemStack(Items.vial_1296),new ItemStack(Items.vial_576), FluidRegistry.getFluid("glass"),8000);

    }
}
