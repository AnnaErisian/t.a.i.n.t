package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import blue.thejester.taint.tools.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tools.TinkerModifiers;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class Tools implements IModule {

    public static ToolDagger dagger;
    public static ToolWaraxe waraxe;
    public static ToolSpear spear;
    public static ToolGlaive glaive;
    public static ToolShield shield;
    public static ToolBuckler buckler;

    @Override
    public void init() {

    }

    @Override
    public void initLate() {
        MinecraftForge.EVENT_BUS.register(shield); // shield events
        MinecraftForge.EVENT_BUS.register(buckler); // shield events
    }

    @SubscribeEvent
    public static void initItems(RegistryEvent.Register<Item> event) {
        dagger = new ToolDagger();
        initToolItem(event.getRegistry(), dagger);

        waraxe = new ToolWaraxe();
        initToolItem(event.getRegistry(), waraxe);

        spear = new ToolSpear();
        initToolItem(event.getRegistry(), spear);

        glaive = new ToolGlaive();
        initToolItem(event.getRegistry(), glaive);

        shield = new ToolShield();
        initToolItem(event.getRegistry(), shield);

        buckler = new ToolBuckler();
        initToolItem(event.getRegistry(), buckler);

        // register modifiers
        for (IModifier modifier: new IModifier[] {
                TinkerModifiers.modBaneOfArthopods,
                TinkerModifiers.modBeheading,
                TinkerModifiers.modDiamond,
                TinkerModifiers.modEmerald,
                TinkerModifiers.modFiery,
                TinkerModifiers.modFins,
                TinkerModifiers.modGlowing,
                TinkerModifiers.modHaste,
                TinkerModifiers.modKnockback,
                TinkerModifiers.modLuck,
                TinkerModifiers.modMendingMoss,
                TinkerModifiers.modNecrotic,
                TinkerModifiers.modReinforced,
                TinkerModifiers.modSharpness,
                TinkerModifiers.modShulking,
                TinkerModifiers.modSilktouch,
                TinkerModifiers.modSmite,
                TinkerModifiers.modSoulbound,
                TinkerModifiers.modWebbed,
        }) {
            Taint.proxy.registerModifierModel(modifier,
                    new ResourceLocation(Taint.MODID, "models/item/modifiers/"+modifier.getIdentifier()));
        }
    }

    private static void initToolItem(IForgeRegistry<Item> reg, ToolCore c) {
        reg.register(c);
        TinkerRegistry.registerToolStationCrafting(c);
        Taint.proxy.registerToolModel(c);
    }
}
