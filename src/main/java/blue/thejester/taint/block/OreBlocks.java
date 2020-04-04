package blue.thejester.taint.block;

import blue.thejester.taint.Taint;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.signature.BasicSignature;
import vazkii.botania.common.item.block.ItemBlockMod;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class OreBlocks {
    public static final Block oreArdorum = new BlockOre("oreArdorum");
    public static final Block oreTermium = new BlockOre("oreTermium");
    public static final Block oreAdipatum = new BlockOre("oreAdipatum");
    public static final Block oreCaersin = new BlockOre("oreCaersin");
    public static final Block oreNeulite = new BlockOre("oreNeulite");
    public static final Block oreAtercaeum = new BlockOre("oreAtercaeum");
    public static final Block oreOscurum = new BlockOre("oreOscurum");
    public static final Block oreInurose = new BlockOre("oreInurose");
    public static final Block oreCibarite = new BlockOre("oreCibarite");

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

        e.getRegistry().register(new ItemBlockMod(oreArdorum).setRegistryName(oreArdorum.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreTermium).setRegistryName(oreTermium.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreAdipatum).setRegistryName(oreAdipatum.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreCaersin).setRegistryName(oreCaersin.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreNeulite).setRegistryName(oreNeulite.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreAtercaeum).setRegistryName(oreAtercaeum.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreOscurum).setRegistryName(oreOscurum.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreInurose).setRegistryName(oreInurose.getRegistryName()));
        e.getRegistry().register(new ItemBlockMod(oreCibarite).setRegistryName(oreCibarite.getRegistryName()));
    }
}
