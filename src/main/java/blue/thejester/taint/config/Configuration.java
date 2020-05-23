package blue.thejester.taint.config;

import blue.thejester.taint.Taint;
import blue.thejester.taint.block.OreBlocks;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Config;

@Config(modid = Taint.MODID)
public class Configuration {

    @Config.Name("Generate Caersin in the Aether")
    public static boolean generateCaersin = true;
    @Config.Name("Generate Adipatum in the Betweenlands")
    public static boolean generateAdipatum = true;
    @Config.Name("Generate Neulite in the End")
    public static boolean generateNeulite = true;
    @Config.Name("Generate Ardorum in the Twilight Forest")
    public static boolean generateArdorum = true;
    @Config.Name("Generate Twermium in the Twilight Forest")
    public static boolean generateTermium = true;
    @Config.Name("Generate Atercaeum in the Beneath")
    public static boolean generateAtercaeum = true;
    @Config.Name("Generate Oscurum in the Beneath")
    public static boolean generateOscurum = true;
    @Config.Name("Generate Inurose in the Beneath")
    public static boolean generateInurose = true;
    @Config.Name("Generate Cibarite in the Beneath")
    public static boolean generateCibarite = true;
}
