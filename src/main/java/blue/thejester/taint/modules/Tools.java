package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import blue.thejester.taint.core.RecipeRegistry;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.tools.*;
import landmaster.plustic.PlusTiC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import shnupbups.tinkersaether.modules.ModuleBase;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;
import twilightforest.compat.TConstruct;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class Tools implements IModule {

    public static ToolDagger dagger;
    public static ToolWaraxe waraxe;
    public static ToolSpear spear;
    public static ToolGlaive glaive;
    public static ToolShield shield;
    public static ToolBuckler buckler;
    public static ToolWand wand;
    public static ToolStaff staff;
    public static ToolWarWand warwand;

    public static ToolPart wandCore;
    public static ToolPart wandGem;
    public static ToolPart wandSocket;


    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void onItemReg(RegistryEvent.Register<Item> event) {
        //GEMS
        TinkerRegistry.addMaterialStats(TinkerMaterials.flint,                                   new WandGemMaterialStats(0, 0.10f, 300,  0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                    new WandGemMaterialStats(0, 0.13f, 700,  0.0f, 0.7f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                  new WandGemMaterialStats(0, 0.12f, 600,  0.0f, 0.1f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze,                                  new WandGemMaterialStats(1, 0.15f, 800,  0.8f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                  new WandGemMaterialStats(0, 0.12f, 500,  0.0f, 0.0f, 0.0f, 0.4f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"),new WandGemMaterialStats(2, 0.18f, 900, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime,                             new WandGemMaterialStats(1, 0.20f, 1000, 0.0f, 0.3f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum,                                new WandGemMaterialStats(1, 0.18f, 800,  0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                  new WandGemMaterialStats(1, 0.17f, 900, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("ruby"),            new WandGemMaterialStats(2, 0.25f, 800, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("sapphire"),        new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("peridot"),         new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("malachite_gem"),   new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("tanzanite"),       new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("amber"),           new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("topaz"),           new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.fierymetal,                                   new WandGemMaterialStats(2, 0.35f, 1000, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.knightmetal,                                  new WandGemMaterialStats(2, 0.38f, 1000, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.steeleaf,                                     new WandGemMaterialStats(2, 0.30f, 900, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime,                                   new WandGemMaterialStats(3, 0.34f, 2400, 0.0f, 1.0f, 0.3f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime,                               new WandGemMaterialStats(3, 0.36f, 2400, 0.0f, 1.2f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine,                              new WandGemMaterialStats(3, 0.40f, 1100, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),      new WandGemMaterialStats(3, 0.32f, 800, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"),new WandGemMaterialStats(2, 0.33f, 1100, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"),new WandGemMaterialStats(3, 0.34f, 1100, 0.0f, 0.2f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),         new WandGemMaterialStats(3, 0.35f, 1200, 0.0f, 0.1f, 0.0f, 0.2f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                         new WandGemMaterialStats(3, 0.39f, 700,  0.3f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                          new WandGemMaterialStats(3, 0.40f, 1200, 0.0f, 0.2f, 0.1f, 0.0f, 0.0f, 0.7f, 0.2f));
        TinkerRegistry.addMaterialStats(Betweenlands.scabyst,                                    new WandGemMaterialStats(3, 0.50f, 2000, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f, 1.1f));
        TinkerRegistry.addMaterialStats(Betweenlands.octine,                                     new WandGemMaterialStats(3, 0.47f, 1700, 1.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(Betweenlands.valonite,                                   new WandGemMaterialStats(3, 0.55f, 1900, 0.0f, 1.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                         new WandGemMaterialStats(3, 0.44f, 2200, 0.0f, 0.0f, 0.9f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                         new WandGemMaterialStats(3, 0.42f, 2300, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                     new WandGemMaterialStats(3, 0.46f, 2000, 0.0f, 0.0f, 0.0f, 0.7f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                       new WandGemMaterialStats(3, 0.55f, 1900, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                        new WandGemMaterialStats(3, 0.50f, 2200, 0.9f, 0.0f, 0.0f, 0.0f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                        new WandGemMaterialStats(3, 0.48f, 1900, 0.0f, 0.9f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                          new WandGemMaterialStats(3, 0.52f, 1800, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 1.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime,                              new WandGemMaterialStats(3, 0.57f, 2800, 0.9f, 0.0f, 0.1f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("amethyst"),        new WandGemMaterialStats(3, 0.62f, 1600, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.8f, 0.1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                           new WandGemMaterialStats(3, 0.58f, 2600, 0.2f, 0.2f, 1.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                           new WandGemMaterialStats(3, 0.65f, 2700, 0.0f, 0.0f, 0.0f, 0.9f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                         new WandGemMaterialStats(3, 0.60f, 2900, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.zanite,                                      new WandGemMaterialStats(3, 0.63f, 2200, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(Netherstar.netherstar,                                  new WandGemMaterialStats(3, 0.75f, 4000, 0.2f, 0.0f, 0.0f, 0.0f, 1.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                       new WandGemMaterialStats(3, 0.74f, 2300, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                         new WandGemMaterialStats(3, 0.71f, 2000, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                      new WandGemMaterialStats(3, 0.66f, 2000, 0.0f, 0.3f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                        new WandGemMaterialStats(3, 0.68f, 2400, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                         new WandGemMaterialStats(3, 0.70f, 2600, 0.8f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenAmber,                                 new WandGemMaterialStats(3, 0.85f, 2800, 0.1f, 0.0f, 0.0f, 0.3f, 0.0f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.gravitite,                                   new WandGemMaterialStats(3, 0.87f, 2600, 0.0f, 0.3f, 0.8f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("boron_nitride"),  new WandGemMaterialStats(3, 0.82f, 2100, 0.0f, 0.0f, 0.2f, 0.0f, 0.8f, 0.0f, 0.1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                     new WandGemMaterialStats(3, 0.81f, 2000, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                          new WandGemMaterialStats(3, 0.84f, 2600, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                       new WandGemMaterialStats(3, 0.92f, 2200, 0.3f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                      new WandGemMaterialStats(3, 1.00f, 3500, 0.4f, 0.4f, 0.4f, 0.4f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                      new WandGemMaterialStats(3, 1.00f, 3000, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.4f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                       new WandGemMaterialStats(3, 0.90f, 2400, 0.0f, 0.0f, 0.6f, 0.2f, 0.0f, 0.4f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                       new WandGemMaterialStats(3, 0.95f, 2600, 0.8f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));




        TinkerRegistry.addMaterialStats(TinkerMaterials.bone,                                     new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.reed,                                     new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.feather,                                  new WandCoreMaterialStats(3, 1.20f, 0.0f, 0.0f, 0.9f, 0.0f, 0.2f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                   new WandCoreMaterialStats(3, 0.70f, 0.4f, 0.0f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper,                                    new WandCoreMaterialStats(3, 0.60f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.3f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                     new WandCoreMaterialStats(3, 0.85f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                   new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                   new WandCoreMaterialStats(3, 0.80f, 0.0f, 0.1f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blaze,                                    new WandCoreMaterialStats(3, 1.20f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ice,                                      new WandCoreMaterialStats(3, 1.10f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"), new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"), new WandCoreMaterialStats(3, 0.85f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"), new WandCoreMaterialStats(3, 1.00f,  0.0f, 0.2f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("electrum"),         new WandCoreMaterialStats(3, 0.95f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("nickel"),           new WandCoreMaterialStats(3, 0.90f, 0.4f, 0.0f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lead"),             new WandCoreMaterialStats(3, 0.85f, 0.2f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("chocolate"),        new WandCoreMaterialStats(3, 0.65f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("manasteel"),        new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.ravenFeather,                                  new WandCoreMaterialStats(3, 1.30f, 0.0f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f, 0.4f));
        TinkerRegistry.addMaterialStats(TConstruct.steeleaf,                                      new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.3f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge,                                   new WandCoreMaterialStats(3, 1.80f, 0.0f, 0.6f, 0.0f, 0.0f, 0.3f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel,                                    new WandCoreMaterialStats(3, 0.95f, 0.6f, 0.0f, 0.6f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),       new WandCoreMaterialStats(3, 1.00f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lumium_plustic"),   new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),          new WandCoreMaterialStats(3, 1.10f, 0.0f, 0.1f, 0.0f, 0.2f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("terrasteel"),       new WandCoreMaterialStats(3, 1.20f, 0.1f, 0.1f, 0.1f, 0.1f, 0.0f, 1.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("mirion"),           new WandCoreMaterialStats(3, 1.25f, 0.0f, 0.0f, 0.2f, 0.0f, 0.4f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                          new WandCoreMaterialStats(3, 0.90f, 0.3f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.slipsteel.material,                         new WandCoreMaterialStats(3, 1.05f, 0.8f, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                           new WandCoreMaterialStats(3, 0.88f, 0.0f, 0.2f, 0.1f, 0.0f, 0.0f, 0.7f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                      new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.7f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                          new WandCoreMaterialStats(3, 1.10f, 0.0f, 0.0f, 0.9f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                          new WandCoreMaterialStats(3, 1.20f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.skyrootLeaf,                                   new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.4f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.betweensteel.material,                      new WandCoreMaterialStats(3, 1.00f, 0.0f, 0.0f, 0.3f, 0.0f, 0.6f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                            new WandCoreMaterialStats(3, 1.20f, 0.0f, 0.0f, 0.0f, 0.9f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cannium.material,                           new WandCoreMaterialStats(3, 1.30f, 0.3f, 0.0f, 0.0f, 0.8f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                           new WandCoreMaterialStats(3, 0.95f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 1.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                         new WandCoreMaterialStats(3, 0.85f, 0.9f, 0.0f, 0.0f, 0.0f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                        new WandCoreMaterialStats(3, 1.25f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.peractium.material,                         new WandCoreMaterialStats(3, 0.90f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                            new WandCoreMaterialStats(3, 1.15f, 0.2f, 0.2f, 1.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                          new WandCoreMaterialStats(3, 1.10f, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                         new WandCoreMaterialStats(3, 1.30f, 0.0f, 0.9f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(ModuleBase.crystalLeaf,                                   new WandCoreMaterialStats(4, 1.60f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenOakLeaf,                                 new WandCoreMaterialStats(4, 1.50f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenFeather,                                 new WandCoreMaterialStats(4, 1.80f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                         new WandCoreMaterialStats(4, 1.05f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                        new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.3f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                           new WandCoreMaterialStats(4, 1.10f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                          new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                           new WandCoreMaterialStats(4, 1.20f, 0.8f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.antenium.material,                          new WandCoreMaterialStats(4, 0.90f, 0.0f, 0.2f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.accelerase.material,                        new WandCoreMaterialStats(4, 1.10f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.soldrite.material,                          new WandCoreMaterialStats(4, 0.95f, 0.0f, 0.8f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                            new WandCoreMaterialStats(4, 1.05f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                       new WandCoreMaterialStats(4, 1.05f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                        new WandCoreMaterialStats(4, 1.30f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.4f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.urielium.material,                          new WandCoreMaterialStats(4, 1.15f, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                         new WandCoreMaterialStats(4, 1.10f, 0.8f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                        new WandCoreMaterialStats(4, 1.65f, 0.4f, 0.4f, 0.4f, 0.4f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                         new WandCoreMaterialStats(4, 1.20f, 0.0f, 0.0f, 0.6f, 0.2f, 0.0f, 0.4f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ptemnium.material,                          new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.0f, 0.3f, 0.0f, 0.7f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                         new WandCoreMaterialStats(4, 1.30f, 0.3f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f));







        TinkerRegistry.addMaterialStats(MetalMaterial.accelerase.material,                              new WandSocketMaterialStats(150,14,0.0f,0.0f,0.8f,0.0f,0.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                              new WandSocketMaterialStats(500,15,0.4f,0.4f,0.4f,0.4f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                                  new WandSocketMaterialStats(150,12,0.2f,0.2f,1.0f,0.2f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.antenium.material,                                new WandSocketMaterialStats(200,14,0.0f,0.2f,0.0f,0.0f,0.8f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                                new WandSocketMaterialStats(170,12,0.0f,0.3f,0.0f,0.0f,0.9f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.betweensteel.material,                            new WandSocketMaterialStats(190,11,0.0f,0.0f,0.3f,0.0f,0.6f,0.0f,0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                            new WandSocketMaterialStats(110,11,0.0f,0.0f,0.0f,0.7f,0.6f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime,                                      new WandSocketMaterialStats(120,11,0.0f,1.2f,0.2f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone,                                           new WandSocketMaterialStats(20, 3, 0.0f,0.0f,0.0f,0.3f,0.8f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("boron_nitride"),          new WandSocketMaterialStats(100,14,0.0f,0.0f,0.2f,0.0f,0.8f,0.0f,0.1f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze,                                         new WandSocketMaterialStats(40, 7, 0.8f,0.0f,0.0f,0.0f,0.1f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                         new WandSocketMaterialStats(15, 5, 0.4f,0.0f,0.0f,0.8f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cannium.material,                                 new WandSocketMaterialStats(60, 11,0.3f,0.0f,0.0f,0.8f,0.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("chocolate"),              new WandSocketMaterialStats(20, 5, 0.0f,0.3f,0.0f,0.0f,0.0f,0.0f,0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                         new WandSocketMaterialStats(25, 5, 0.0f,0.1f,0.9f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                               new WandSocketMaterialStats(220,15,0.8f,0.2f,0.0f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum,                                       new WandSocketMaterialStats(30, 7, 0.1f,0.0f,0.0f,0.0f,0.0f,0.0f,0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),             new WandSocketMaterialStats(40, 9, 0.0f,0.0f,0.0f,0.0f,0.0f,0.3f,0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"),       new WandSocketMaterialStats(50, 9, 0.0f,0.0f,0.0f,0.0f,0.2f,0.8f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                                new WandSocketMaterialStats(30, 11,0.3f,0.0f,0.0f,0.7f,0.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                                 new WandSocketMaterialStats(90, 11,0.0f,0.2f,0.1f,0.0f,0.0f,0.7f,0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                                new WandSocketMaterialStats(140,13,0.0f,0.0f,0.9f,0.0f,0.0f,0.0f,0.3f));
        TinkerRegistry.addMaterialStats(TConstruct.fierymetal,                                          new WandSocketMaterialStats(50, 11,1.2f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenAmber,                                         new WandSocketMaterialStats(140,14,0.1f,0.0f,0.0f,0.3f,0.0f,0.8f,0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.gravitite,                                           new WandSocketMaterialStats(130,14,0.0f,0.3f,0.8f,0.0f,0.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ice,                                            new WandSocketMaterialStats(0,  5, 0.0f,1.0f,0.0f,0.0f,0.0f,0.0f,0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                               new WandSocketMaterialStats(90, 12,0.9f,0.0f,0.0f,0.0f,0.6f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),                new WandSocketMaterialStats(66, 11,0.0f,0.1f,0.0f,0.2f,0.8f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                           new WandSocketMaterialStats(30, 5, 0.0f,0.7f,0.0f,0.0f,0.0f,0.5f,0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.knightmetal,                                         new WandSocketMaterialStats(50, 11,0.0f,0.0f,0.0f,0.0f,1.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime,                                    new WandSocketMaterialStats(40, 7, 0.0f,0.3f,0.0f,0.7f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lead"),                   new WandSocketMaterialStats(30, 7, 0.2f,0.0f,0.0f,0.0f,0.9f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                                 new WandSocketMaterialStats(90, 12,0.2f,0.0f,0.0f,0.0f,0.0f,0.2f,1.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                               new WandSocketMaterialStats(130,12,0.0f,0.9f,0.0f,0.2f,0.0f,0.0f,0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                                new WandSocketMaterialStats(140,11,0.0f,0.9f,0.0f,0.0f,0.0f,0.3f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lumium_plustic"),         new WandSocketMaterialStats(40, 9, 0.0f,0.2f,0.0f,0.0f,0.0f,0.0f,0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                                  new WandSocketMaterialStats(160,13,0.0f,0.0f,0.0f,0.9f,0.3f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime,                                     new WandSocketMaterialStats(140,12,0.9f,0.0f,0.1f,0.0f,0.0f,0.2f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("manasteel"),              new WandSocketMaterialStats(20, 7, 0.0f,0.0f,0.0f,0.2f,0.0f,0.9f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("mirion"),                 new WandSocketMaterialStats(100,11,0.0f,0.0f,0.2f,0.0f,0.4f,0.7f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("nickel"),                 new WandSocketMaterialStats(60, 9, 0.4f,0.0f,0.0f,0.7f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(Betweenlands.octine,                                            new WandSocketMaterialStats(85, 11,1.0f,0.0f,0.0f,0.2f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                              new WandSocketMaterialStats(195,15,0.0f,0.0f,0.0f,0.0f,0.4f,0.4f,0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                                 new WandSocketMaterialStats(160,13,0.8f,0.0f,0.0f,0.3f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper,                                          new WandSocketMaterialStats(-100,3,0.0f,0.0f,0.0f,0.0f,0.0f,0.8f,0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.peractium.material,                               new WandSocketMaterialStats(80, 12,0.0f,0.0f,0.0f,0.0f,0.3f,0.8f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                                new WandSocketMaterialStats(120,11,0.0f,0.0f,0.9f,0.2f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"),       new WandSocketMaterialStats(55, 9, 0.0f,0.2f,0.8f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ptemnium.material,                                new WandSocketMaterialStats(220,15,0.0f,0.0f,0.3f,0.0f,0.7f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                                 new WandSocketMaterialStats(110,13,0.0f,0.0f,0.0f,0.0f,0.3f,0.0f,0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                              new WandSocketMaterialStats(125,12,0.5f,0.5f,0.5f,0.5f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"),       new WandSocketMaterialStats(40, 7, 0.0f,0.0f,0.0f,0.0f,0.0f,0.1f,0.9f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                         new WandSocketMaterialStats(25, 7, 0.0f,0.0f,0.0f,0.0f,0.0f,0.3f,0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime,                                          new WandSocketMaterialStats(120,9, 0.0f,1.0f,0.3f,0.2f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.slipsteel.material,                               new WandSocketMaterialStats(80, 7, 0.8f,0.0f,0.0f,0.4f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                               new WandSocketMaterialStats(140,14,0.3f,0.0f,0.0f,0.0f,0.0f,0.0f,0.7f));
        TinkerRegistry.addMaterialStats(MetalMaterial.soldrite.material,                                new WandSocketMaterialStats(160,14,0.0f,0.8f,0.0f,0.2f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge,                                         new WandSocketMaterialStats(400,12,0.0f,0.6f,0.0f,0.0f,0.3f,0.0f,0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                               new WandSocketMaterialStats(140,14,0.0f,0.0f,0.6f,0.2f,0.0f,0.4f,0.2f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel,                                          new WandSocketMaterialStats(80, 5, 0.6f,0.0f,0.6f,0.0f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                             new WandSocketMaterialStats(115,14,0.0f,0.2f,0.0f,0.0f,0.0f,0.0f,0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                              new WandSocketMaterialStats(115,13,0.0f,0.3f,0.0f,0.9f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                                  new WandSocketMaterialStats(140,14,0.0f,0.0f,0.0f,0.9f,0.0f,0.1f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("terrasteel"),             new WandSocketMaterialStats(80, 11,0.1f,0.1f,0.1f,0.1f,0.0f,1.0f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                               new WandSocketMaterialStats(120,13,0.0f,0.0f,0.3f,0.0f,0.0f,0.9f,0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.urielium.material,                                new WandSocketMaterialStats(250,15,0.4f,0.0f,0.0f,0.0f,0.0f,0.7f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.stone,                                          new WandSocketMaterialStats(10, 2, 0.0f,0.0f,0.0f,0.9f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.obsidian,                                       new WandSocketMaterialStats(60, 6, 0.6f,0.0f,0.0f,0.6f,0.0f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.endstone,                                       new WandSocketMaterialStats(40, 9, 0.0f,0.0f,0.0f,0.0f,0.0f,0.5f,0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.netherrack,                                     new WandSocketMaterialStats(30, 6, 0.7f,0.0f,0.0f,0.0f,0.4f,0.0f,0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood,                                           new WandSocketMaterialStats(20, 3, 0.0f,0.0f,0.0f,0.6f,0.0f,0.0f,0.6f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("livingwood_plustic"),     new WandSocketMaterialStats(25, 4, 0.0f,0.0f,0.0f,0.0f,0.0f,0.7f,0.4f));
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {
        MinecraftForge.EVENT_BUS.register(shield); // shield events
        MinecraftForge.EVENT_BUS.register(buckler); // shield events
    }

    @SubscribeEvent
    public static void initItems(RegistryEvent.Register<Item> event) {

        wandCore = makePart(event.getRegistry(), Material.VALUE_Ingot, "wand_core");
        wandGem = makePart(event.getRegistry(), Material.VALUE_Ingot * 2, "wand_gem");
        wandSocket = makePart(event.getRegistry(), Material.VALUE_Ingot, "wand_socket");

        dagger = new ToolDagger();
        initToolItem(event.getRegistry(), dagger, false);

        waraxe = new ToolWaraxe();
        initToolItem(event.getRegistry(), waraxe, false);

        spear = new ToolSpear();
        initToolItem(event.getRegistry(), spear, true);

        glaive = new ToolGlaive();
        initToolItem(event.getRegistry(), glaive, true);

        shield = new ToolShield();
        initToolItem(event.getRegistry(), shield, false);

        buckler = new ToolBuckler();
        initToolItem(event.getRegistry(), buckler, false);

        wand = new ToolWand();
        initToolItem(event.getRegistry(), wand, true);
        RecipeRegistry.addToManaFlaskCharging(wand);

        staff = new ToolStaff();
        initToolItem(event.getRegistry(), staff, true);
        RecipeRegistry.addToManaFlaskCharging(staff);

        warwand = new ToolWarWand();
        initToolItem(event.getRegistry(), warwand, true);
        RecipeRegistry.addToManaFlaskCharging(warwand);

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

    private static ToolPart makePart(IForgeRegistry<Item> registry, int value, String partName) {
        ToolPart part = new ToolPart(value);
        part.setTranslationKey(partName).setRegistryName(partName);
        registry.register(part);
        TinkerRegistry.registerToolPart(part);
        Taint.proxy.registerToolPartModel(part);
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), part));
        return part;
    }

    private static void initToolItem(IForgeRegistry<Item> reg, ToolCore c, boolean forge ) {
        reg.register(c);
        if (forge) {
            TinkerRegistry.registerToolForgeCrafting(c);
        } else {
            TinkerRegistry.registerToolCrafting(c);
        }
        Taint.proxy.registerToolModel(c);
    }
}
