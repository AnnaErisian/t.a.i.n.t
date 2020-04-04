package blue.thejester.taint.item;

import blue.thejester.taint.Taint;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class ModItems {

    static TraitProfiler traitProfiler;

    public static void init() {
        traitProfiler = new TraitProfiler("trait_profiler");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().registerAll(traitProfiler);
        for (MetalMaterial mat : MetalMaterial.values()) {
            event.getRegistry().registerAll(mat.nugget);
        }
        for (MetalMaterial mat : MetalMaterial.values()) {
            event.getRegistry().registerAll(mat.ingot);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (MetalMaterial mat : MetalMaterial.values()) {
            event.getRegistry().registerAll(mat.block);
        }
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        for (MetalMaterial mat : MetalMaterial.values()) {
            event.getRegistry().registerAll(mat.blockItem);
        }
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        for (MetalMaterial mat : MetalMaterial.values()) {
            registerRender(Item.getItemFromBlock(mat.block));
            registerRender(mat.ingot);
            registerRender(mat.nugget);
        }
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }

}
