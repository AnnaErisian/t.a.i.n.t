package blue.thejester.taint;

import blue.thejester.taint.asm.ClassTransformer;
import blue.thejester.taint.core.CommonProxy;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(modid = Taint.MODID, name = Taint.NAME, version = Taint.VERSION, dependencies = Taint.DEPENDS)
public class Taint
{
    public static final String MODID = "taint";
    public static final String NAME = "Tinker's Alloying, Integrations, and Tools";
    public static final String VERSION = "1.0";
    public static final String DEPENDS = "required-after:mantle;"
            + "required-after:tconstruct@[1.12-2.7.2.15,);required-after:forge@[14.23.5.2768,);"
            + "required-after:botania;required-after:plustic;required-after:nuclearcraft;"
            + "required-after:thermalfoundation;after:draconicevolution;"
            + "required-after:twilightforest@[3.7,);required-after:crafttweaker;"
            + "required-after:conarm;required-after:tinkersaether;required-after:ebwizardry;";
    //required-after:iceandfire; LLibrary is crappy and breaks like hell, so we can't use it in dev

    public static Logger logger;

    // The instance of your mod that Forge uses.  Optional.
    @Mod.Instance(Taint.MODID)
    public static Taint instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="blue.thejester.taint.core.ClientOnlyProxy", serverSide="blue.thejester.taint.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
        logger.log(Level.DEBUG, ClassTransformer.transformations + "/1 ASM Transformations were applied from Taint.");
    }

    @EventHandler
    public void registerModels(ModelRegistryEvent event)
    {
        proxy.registerModels(event);
    }
}
