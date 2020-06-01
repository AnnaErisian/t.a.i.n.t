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
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class ModItems {

    public static TraitProfiler traitProfiler;
    public static ItemWandBook wandBook;

    public static void init() {
        traitProfiler = new TraitProfiler("trait_profiler");
        wandBook = new ItemWandBook();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().registerAll(traitProfiler);
        event.getRegistry().registerAll(wandBook);
        for (MetalMaterial mat : MetalMaterial.values()) {
            event.getRegistry().registerAll(mat.nugget);
            event.getRegistry().registerAll(mat.ingot);
            registerItemIfNonNull(event.getRegistry(), mat.aBoots);
            registerItemIfNonNull(event.getRegistry(), mat.aChestplate);
            registerItemIfNonNull(event.getRegistry(), mat.aLeggings);
            registerItemIfNonNull(event.getRegistry(), mat.aHelmet);
            registerItemIfNonNull(event.getRegistry(), mat.tSword);
            registerItemIfNonNull(event.getRegistry(), mat.tPick);
            registerItemIfNonNull(event.getRegistry(), mat.tShovel);
            registerItemIfNonNull(event.getRegistry(), mat.tAxe);
            registerItemIfNonNull(event.getRegistry(), mat.tHoe);
        }
    }

    private static void registerItemIfNonNull(IForgeRegistry<Item> registry, Item item) {
        if(item != null) {
            registry.register(item);
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
        registerRender(wandBook);
        for (MetalMaterial mat : MetalMaterial.values()) {
            registerRender(Item.getItemFromBlock(mat.block));
            registerRender(mat.ingot);
            registerRender(mat.nugget);
            registerRenderIfNonNull(mat.aHelmet);
            registerRenderIfNonNull(mat.aLeggings);
            registerRenderIfNonNull(mat.aChestplate);
            registerRenderIfNonNull(mat.aBoots);
            registerRenderIfNonNull(mat.tShovel);
            registerRenderIfNonNull(mat.tPick);
            registerRenderIfNonNull(mat.tAxe);
            registerRenderIfNonNull(mat.tHoe);
            registerRenderIfNonNull(mat.tSword);
        }
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }

    public static void registerRenderIfNonNull(Item item) {
        if(item != null) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
        }
    }

}
