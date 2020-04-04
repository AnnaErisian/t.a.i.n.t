package blue.thejester.taint.helper.fluid;

import blue.thejester.taint.Taint;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

public class Create {
    public static FluidMolten plain(String identifier, int color, int temp) {
        FluidMolten fluid = new FluidMolten(identifier, color);
        fluid.setTemperature(temp);
        FluidRegistry.registerFluid(fluid);
        BlockMolten blockFluid = new BlockMolten(fluid);
        blockFluid.setTranslationKey("molten_"+fluid.getName());
        blockFluid.setRegistryName("molten_"+fluid.getName());
        ForgeRegistries.BLOCKS.register(blockFluid);
        FluidRegistry.addBucketForFluid(fluid);
        Taint.proxy.registerFluidModels(fluid);
        return fluid;
    }

    public static FluidMolten plain(String identifier, int color) {
        return plain(identifier, color, 1000);
    }
}
