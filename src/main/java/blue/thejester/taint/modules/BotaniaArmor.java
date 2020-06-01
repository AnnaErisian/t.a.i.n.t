package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import blue.thejester.taint.traits.Gaian;
import c4.conarm.lib.materials.*;
import landmaster.plustic.traits.Mana;
import landmaster.plustic.traits.Mirabile;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class BotaniaArmor implements IModule {
    @Override
    public void preInit() {
        //This one all happens in onItemReg, after PlusTic creates the material definitions
    }

    @SubscribeEvent
    public static void onItemReg(RegistryEvent.Register<Item> event) {
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("manasteel"),
                new CoreMaterialStats(16,14),
                new PlatesMaterialStats(0.98f, 6, 1),
                new TrimMaterialStats(4.5f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),
                new CoreMaterialStats(15f, 14.5f),
                new PlatesMaterialStats(1.08f, 6f, 2f),
                new TrimMaterialStats(4f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("terrasteel"),
                new CoreMaterialStats(19.9f, 21f),
                new PlatesMaterialStats(1.12f, 13f, 5f),
                new TrimMaterialStats(14f));
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("terrasteel"), Gaian.gaian, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("terrasteel"), Mana.mana, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("terrasteel"), Mana.mana, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("terrasteel"), Mana.mana, ArmorMaterialType.TRIM);
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("mirion"),
                new CoreMaterialStats(19, 20),
                new PlatesMaterialStats(1.1f, 12.5f, 4f),
                new TrimMaterialStats(13f));
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("mirion"), Mirabile.mirabile, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("mirion"), Mana.mana, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("mirion"), Mana.mana, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkerRegistry.getMaterial("mirion"), Mana.mana, ArmorMaterialType.TRIM);
    }

    public void postInit() {
    }

    @Override
    public void init() {

    }
}
