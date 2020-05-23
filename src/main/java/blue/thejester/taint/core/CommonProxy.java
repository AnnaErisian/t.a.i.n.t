package blue.thejester.taint.core;

import blue.thejester.taint.block.OreBlocks;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.item.ModItems;
import blue.thejester.taint.modules.*;
import blue.thejester.taint.world.OreGenerator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.client.CreativeTab;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.ToolCore;

import java.util.Arrays;

public class CommonProxy {

    public static CreativeTab taintCreativeTab = new CreativeTab("TaintTab", new ItemStack(Items.IRON_HOE));

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
     */
    public void preInit() {

        ModItems.init();
//        IModule.modules.addAll(Arrays.asList(new Tools()));
        IModule.modules.addAll(Arrays.asList(new BotaniaArmor(), new Betweenlands(), new Fluids(), new Netherstar(), new NewMaterials(), new Tools(), new NewMaterialsVanillaTools()));
        IModule.modules.forEach(IModule::preInit);

        ModItems.init();
        MetalMaterial.makeItems();
        MetalMaterial.sendSmelteryIMC();

//        taintCreativeTab.setDisplayIcon(MetalMaterial.adamantite.ingotStack);

    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes,
     * send FMLInterModComms messages to other mods.
     */
    public void init() {
        MetalMaterial.registerOreDict();
        MetalMaterial.makeRecipes();
        OreBlocks.preInit();
        GameRegistry.registerWorldGenerator(new OreGenerator(), 1000);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    public void postInit() {
        IModule.modules.forEach(IModule::postInit);
    }

    /**
     * is this a dedicated server?
     *
     * @return true if this is a dedicated server, false otherwise
     */
    public boolean isDedicatedServer() {
        return true;
    }

    public void registerFluidModels(Fluid fluid) {

    }

    public void registerToolModel(ToolCore dagger) {

    }

    public void registerModifierModel(IModifier mod, ResourceLocation rl) {

    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void initToolGuis() {

    }
}
