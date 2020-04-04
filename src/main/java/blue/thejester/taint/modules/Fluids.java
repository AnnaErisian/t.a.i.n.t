package blue.thejester.taint.modules;

import blue.thejester.taint.helper.fluid.Create;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;

public class Fluids implements IModule{

    @Override
    public void init() {
        Fluid netherstar = Create.plain("netherstar", 0xfffbed, 10000);
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("netherStar", 1, 250), netherstar, 2500));
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Items.NETHER_STAR, 1), netherstar, 250, 2400));

        Fluid prismarine = Create.plain("prismarine", 0x37c2e5, 1000);
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("gemPrismarine", 1, 144), prismarine, 500));
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("dustPrismarine", 1, 144), prismarine, 500));
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("blockPrismarine", 1, 144*4), prismarine, 500));
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("blockPrismarineBrick", 1, 144*9), prismarine, 500));
        TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.Oredict("blockPrismarineDark", 1, 144*8), prismarine, 500));
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Items.PRISMARINE_SHARD, 1), prismarine, 144, 400));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(Blocks.PRISMARINE, 1), prismarine, 144*4, 800));

        Fluid thermoconducting = Create.plain("thermoconducting", 0x4F593A, 1000);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", thermoconducting.getName()); // name of the fluid
        tag.setString("ore", "Thermoconducting"); // ore-suffix: ingotFoo, blockFoo, oreFoo,...
        tag.setBoolean("toolforge", false); // if set to true, blockFoo can be used to build a toolforge
        FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
    }

    public void initLate() {
    }
}
