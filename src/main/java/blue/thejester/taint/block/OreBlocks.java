package blue.thejester.taint.block;

import blue.thejester.taint.Taint;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class OreBlocks {
    public static final Block oreArdorum = new BlockOre("ore_ardorum", 5f, 3f, 5);
    public static final Block oreTermium = new BlockOre("ore_termium", 5f, 3f, 6);
    public static final Block oreAdipatum = new BlockOre("ore_adipatum", 5f, 3f, 7);
    public static final Block oreCaersin = new BlockOre("ore_caersin", 5f, 3f, 7);
    public static final Block oreNeulite = new BlockOre("ore_neulite", 5f, 3f, 8);
    public static final Block oreAtercaeum = new BlockOre("ore_atercaeum", 5f, 3f, 9);
    public static final Block oreOscurum = new BlockOre("ore_oscurum", 5f, 3f, 10);
    public static final Block oreInurose = new BlockOre("ore_inurose", 5f, 3f, 10);
    public static final Block oreCibarite = new BlockOre("ore_cibarite", 5f, 3f, 10);

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> e) {
        e.getRegistry().register(oreArdorum);
        e.getRegistry().register(oreTermium);
        e.getRegistry().register(oreAdipatum);
        e.getRegistry().register(oreCaersin);
        e.getRegistry().register(oreNeulite);
        e.getRegistry().register(oreAtercaeum);
        e.getRegistry().register(oreOscurum);
        e.getRegistry().register(oreInurose);
        e.getRegistry().register(oreCibarite);

    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> e) {

        e.getRegistry().register(new ItemBlock(oreArdorum).setRegistryName(oreArdorum.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreTermium).setRegistryName(oreTermium.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreAdipatum).setRegistryName(oreAdipatum.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreCaersin).setRegistryName(oreCaersin.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreNeulite).setRegistryName(oreNeulite.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreAtercaeum).setRegistryName(oreAtercaeum.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreOscurum).setRegistryName(oreOscurum.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreInurose).setRegistryName(oreInurose.getRegistryName()));
        e.getRegistry().register(new ItemBlock(oreCibarite).setRegistryName(oreCibarite.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        ModItems.registerRender(Item.getItemFromBlock(oreArdorum));
        ModItems.registerRender(Item.getItemFromBlock(oreTermium));
        ModItems.registerRender(Item.getItemFromBlock(oreAdipatum));
        ModItems.registerRender(Item.getItemFromBlock(oreCaersin));
        ModItems.registerRender(Item.getItemFromBlock(oreNeulite));
        ModItems.registerRender(Item.getItemFromBlock(oreAtercaeum));
        ModItems.registerRender(Item.getItemFromBlock(oreOscurum));
        ModItems.registerRender(Item.getItemFromBlock(oreInurose));
        ModItems.registerRender(Item.getItemFromBlock(oreCibarite));
    }
}
