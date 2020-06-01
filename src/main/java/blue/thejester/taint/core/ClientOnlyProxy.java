package blue.thejester.taint.core;

import blue.thejester.taint.Taint;
import blue.thejester.taint.modules.Tools;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;

public class ClientOnlyProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

    /**
     * From Tinker's Aether
     */
    @Override
    public void registerFluidModels(Fluid fluid) {
        if (fluid != null) {
            Block block = fluid.getBlock();
            if (block != null) {
                Item item = Item.getItemFromBlock(block);
                ClientOnlyProxy.FluidStateMapper mapper = new ClientOnlyProxy.FluidStateMapper(fluid);
                ModelBakery.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);

                ModelLoader.setCustomStateMapper(block, mapper);
            }

        }
    }

    @Override
    public void init() {
        super.init();
        initToolGuis();
    }

    /**
     * From Tinker's Aether
     */
    public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
        public final Fluid fluid;
        public final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.fluid = fluid;
            this.location = new ModelResourceLocation(new ResourceLocation(Taint.MODID, "fluid_block"), fluid.getName());
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return location;
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return location;
        }
    }


    @Override
    public void registerToolModel(ToolCore tc) {
        ModelRegisterUtil.registerToolModel(tc);
    }

    @Override
    public void registerModifierModel(IModifier mod, ResourceLocation rl) {
        ModelRegisterUtil.registerModifierModel(mod, rl);
    }

    @Override
    public void initToolGuis() {
        ToolBuildGuiInfo daggerInfo = new ToolBuildGuiInfo(Tools.dagger);
        daggerInfo.addSlotPosition(33 - 20 - 1, 42 + 20); // handle
        daggerInfo.addSlotPosition(33 + 20 - 5, 42 - 20 + 4); // blade
        daggerInfo.addSlotPosition(33 - 2 - 1, 42 + 2); // binding
        TinkerRegistryClient.addToolBuilding(daggerInfo);


        ToolBuildGuiInfo waraxeInfo = new ToolBuildGuiInfo(Tools.waraxe);
        waraxeInfo.addSlotPosition(33 - 20 - 1, 42 + 20); // handle
        waraxeInfo.addSlotPosition(33 + 20 - 5, 42 - 20 + 4); // head
        waraxeInfo.addSlotPosition(33 - 2 - 1, 42 + 2); // guard
        TinkerRegistryClient.addToolBuilding(waraxeInfo);

        ToolBuildGuiInfo spearInfo = new ToolBuildGuiInfo(Tools.spear);
        spearInfo.addSlotPosition(33 - 10 - 14, 42 + 10 + 12); // handle1
        spearInfo.addSlotPosition(33 + 10 - 10, 42 + 10 + 6); // handle2
        spearInfo.addSlotPosition(33 + 14, 42 - 10 - 2); // head
        spearInfo.addSlotPosition(33 - 8, 42 - 10 + 4); // binding
        TinkerRegistryClient.addToolBuilding(spearInfo);

        ToolBuildGuiInfo glaiveInfo = new ToolBuildGuiInfo(Tools.glaive);
        glaiveInfo.addSlotPosition(33 - 10 - 14, 42 + 10 + 12); // handle1
        glaiveInfo.addSlotPosition(33 + 10 - 10, 42 + 10 + 6); // handle2
        glaiveInfo.addSlotPosition(33 + 14, 42 - 10 - 2); // head
        glaiveInfo.addSlotPosition(33 - 8, 42 - 10 + 4); // binding
        TinkerRegistryClient.addToolBuilding(glaiveInfo);

        ToolBuildGuiInfo bucklerInfo = new ToolBuildGuiInfo(Tools.buckler);
        bucklerInfo.addSlotPosition(33 - 6, 42 - 8); // head
        bucklerInfo.addSlotPosition(33 - 6, 42 + 10); // handle
        TinkerRegistryClient.addToolBuilding(bucklerInfo);

        ToolBuildGuiInfo shieldInfo = new ToolBuildGuiInfo(Tools.shield);
        shieldInfo.addSlotPosition(33 - 6, 42 - 8); // head
        shieldInfo.addSlotPosition(33 - 6, 42 + 10); // handle
        TinkerRegistryClient.addToolBuilding(shieldInfo);


        ToolBuildGuiInfo wandInfo = new ToolBuildGuiInfo(Tools.wand);
        wandInfo.addSlotPosition(33 - 20 - 1, 42 + 20); // gem
        wandInfo.addSlotPosition(33 + 20 - 5, 42 - 20 + 4); // socket
        wandInfo.addSlotPosition(33 - 2 - 1, 42 + 2); // core
        TinkerRegistryClient.addToolBuilding(wandInfo);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public <T extends Item & IToolPart> void registerToolPartModel(T part) {
        ModelRegisterUtil.registerPartModel(part);
    }
}
