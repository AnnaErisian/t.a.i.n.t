package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import blue.thejester.taint.core.RecipeRegistry;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.tools.*;
import blue.thejester.taint.traits.ImmortalCenturion;
import blue.thejester.taint.traits.modifier.ModPotency;
import blue.thejester.taint.traits.modifier.ModSlots;
import blue.thejester.taint.traits.wands.*;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.registry.WizardryItems;
import landmaster.plustic.traits.Botanical;
import landmaster.plustic.traits.Elemental;
import landmaster.plustic.traits.Mirabile;
import landmaster.plustic.traits.Terrafirma;
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
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.TinkerTraits;
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

    public static ToolPart wandCore;
    public static ToolPart wandGem;
    public static ToolPart wandSocket;

    public static Category categoryMagic = new Category("magicwands");
    public static final ModifierAspect aspectMagicOnly = new ModifierAspect.CategoryAspect(categoryMagic);

    public static Modifier modFirePotency;
    public static Modifier modIcePotency;
    private static Modifier modLightningPotency;
    private static Modifier modEarthPotency;
    private static Modifier modNecromancyPotency;
    private static Modifier modSorceryPotency;
    private static Modifier modHealingPotency;
    private static Modifier modUpgradeSlots;


    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void onItemReg(RegistryEvent.Register<Item> event) {
        addWandStats();

        addWandModifiers();
    }

    private static void addWandModifiers() {
        modFirePotency = new ModPotency("taint_potency_fire", 0xff6666, Element.FIRE);
        modIcePotency = new ModPotency("taint_potency_ice", 0x66ff99, Element.ICE);
        modLightningPotency = new ModPotency("taint_potency_lightning", 0x66ff99, Element.LIGHTNING);
        modEarthPotency = new ModPotency("taint_potency_earth", 0x66ff99, Element.EARTH);
        modNecromancyPotency = new ModPotency("taint_potency_necro", 0x66ff99, Element.NECROMANCY);
        modSorceryPotency = new ModPotency("taint_potency_sorc", 0x66ff99, Element.SORCERY);
        modHealingPotency = new ModPotency("taint_potency_healing", 0x66ff99, Element.HEALING);

        modUpgradeSlots = new ModSlots("taint_upgrade_slots", 0xffffff);
    }

    private static void addWandModifierItems() {
        //We have to do this later so the items will actually exist in the ObjectHolder paradigm
        modHealingPotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 7), 1, 1);
        modSorceryPotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 6), 1, 1);
        modEarthPotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 5), 1, 1);
        modNecromancyPotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 4), 1, 1);
        modLightningPotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 3), 1, 1);
        modIcePotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 2), 1, 1);
        modFirePotency.addItem(new ItemStack(WizardryItems.magic_crystal, 1, 1), 1, 1);

        modUpgradeSlots.addItem(WizardryItems.astral_diamond, 8, 1);
    }

    private static void addWandStats() {
        //GEMS
        TinkerRegistry.addMaterialStats(TinkerMaterials.flint,                                    new WandGemMaterialStats(0, 0.10f, 300, 0.15f, 0.0f, 0f, 0.8f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                     new WandGemMaterialStats(0, 0.13f, 700, 0.0f, 0.7f, 0f, 0.0f, 0.0f, 0.35f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                   new WandGemMaterialStats(0, 0.12f, 600, 0.15f, 0.08f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze,                                   new WandGemMaterialStats(1, 0.15f, 800, 0.8f, 0.0f, 0f, 0.0f, 0.17f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                   new WandGemMaterialStats(0, 0.12f, 500, 0.1f, 0.0f, 0f, 0.44f, 0.54f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"), new WandGemMaterialStats(2, 0.18f, 900, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.1f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime,                              new WandGemMaterialStats(1, 0.20f, 1000, 0.0f, 0.25f, 0f, 0.75f, 0.0f, 0.15f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum,                                 new WandGemMaterialStats(1, 0.18f, 800, 0.15f, 0.0f, 0f, 0.0f, 0.0f, 0.0f, 0.95f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                   new WandGemMaterialStats(1, 0.17f, 900, 0.0f, 0.0f, 0f, 0.0f, 0.0f, 0.3f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("ruby"),             new WandGemMaterialStats(2, 0.25f, 800, 1.1f, 0.0f, 0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("sapphire"),         new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 1.1f, 0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("peridot"),          new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0f, 0.0f, 0.0f, 1.1f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("malachite_gem"),    new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0f, 0.0f, 0.0f, 0.0f, 1.1f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("tanzanite"),        new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0f, 0.0f, 1.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("amber"),            new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 0f, 1.1f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("topaz"),            new WandGemMaterialStats(2, 0.25f, 800, 0.0f, 0.0f, 1.1f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.fierymetal,                                    new WandGemMaterialStats(2, 0.35f, 1000, 1.25f, 0.0f, 0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.knightmetal,                                   new WandGemMaterialStats(2, 0.38f, 1000, 0.0f, 0.0f, 0f, 0.15f, 0.95f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.steeleaf,                                      new WandGemMaterialStats(2, 0.30f, 900, 0.0f, 0.0f, 0f, 1.05f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.gravitite,                                     new WandGemMaterialStats(3, 0.87f, 2600, 0.0f, 0.3f, 0.8f, 0.0f, 0.0f, 0.20f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime,                                    new WandGemMaterialStats(3, 0.34f, 2400, 0.0f, 0.95f, 0.15f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime,                                new WandGemMaterialStats(3, 0.36f, 2400, 0.0f, 1.05f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine,                               new WandGemMaterialStats(3, 0.40f, 1100, 0.0f, 0.9f, 0f, 0.0f, 0.0f, 0.55f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),       new WandGemMaterialStats(3, 0.32f, 800, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.3f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"), new WandGemMaterialStats(2, 0.33f, 1100, 0.0f, 0.0f, 0f, 0.0f, 0.25f, 0.95f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"), new WandGemMaterialStats(3, 0.34f, 1100, 0.0f, 0.35f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),          new WandGemMaterialStats(3, 0.35f, 1200, 0.0f, 0.2f, 0f, 0.2f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                          new WandGemMaterialStats(3, 0.39f, 700, 0.3f, 0.0f, 0f, 0.75f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                           new WandGemMaterialStats(3, 0.40f, 1200, 0.0f, 0.25f, 0.2f, 0.0f, 0.0f, 0.7f, 0.2f));
        TinkerRegistry.addMaterialStats(Betweenlands.scabyst,                                     new WandGemMaterialStats(3, 0.50f, 2000, 0.0f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f, 1.1f));
        TinkerRegistry.addMaterialStats(Betweenlands.octine,                                      new WandGemMaterialStats(3, 0.47f, 1700, 1.05f, 0.0f, 0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(Betweenlands.valonite,                                    new WandGemMaterialStats(3, 0.55f, 1900, 0.0f, 1f, 0f, 0.0f, 0.25f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                          new WandGemMaterialStats(3, 0.44f, 2200, 0.0f, 0.0f, 0.9f, 0.2f, 0.15f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                          new WandGemMaterialStats(3, 0.42f, 2300, 0.0f, 0.9f, 0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                      new WandGemMaterialStats(3, 0.46f, 2000, 0.1f, 0.0f, 0f, 0.7f, 0.55f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                        new WandGemMaterialStats(3, 0.55f, 1900, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                         new WandGemMaterialStats(3, 0.50f, 2200, 0.95f, 0.0f, 0f, 0.0f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                         new WandGemMaterialStats(3, 0.48f, 1900, 0.0f, 0.9f, 0f, 0.2f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                           new WandGemMaterialStats(3, 0.52f, 1800, 0.2f, 0.0f, 0f, 0.0f, 0.1f, 0.15f, 1f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime,                               new WandGemMaterialStats(3, 0.57f, 2800, 0.9f, 0.0f, 0.1f, 0.0f, 0.1f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("amethyst"),         new WandGemMaterialStats(3, 0.62f, 1600, 0.0f, 0.0f, 0.1f, 0.0f, 0.2f, 0.95f, 0.1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                            new WandGemMaterialStats(3, 0.58f, 2600, 0.2f, 0.1f, 1f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                            new WandGemMaterialStats(3, 0.65f, 2700, 0.0f, 0.0f, 0f, 0.9f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                          new WandGemMaterialStats(3, 0.60f, 2900, 0.0f, 0.25f, 0f, 0.0f, 0.95f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.zanite,                                        new WandGemMaterialStats(3, 0.63f, 2200, 0.0f, 0.85f, 0.3f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(Netherstar.netherstar,                                    new WandGemMaterialStats(3, 0.75f, 4000, 0.2f, 0.0f, 0f, 0.0f, 1.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                         new WandGemMaterialStats(3, 0.74f, 2300, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                           new WandGemMaterialStats(3, 0.71f, 2000, 0.0f, 0.0f, 0f, 0.0f, 0.35f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                        new WandGemMaterialStats(3, 0.66f, 2000, 0.0f, 0.3f, 0f, 0.9f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                          new WandGemMaterialStats(3, 0.68f, 2400, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                           new WandGemMaterialStats(3, 0.70f, 2600, 0.8f, 0.0f, 0f, 0.3f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenAmber,                                   new WandGemMaterialStats(3, 0.85f, 2800, 0.1f, 0.0f, 0f, 0.3f, 0.0f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("boron_nitride"),    new WandGemMaterialStats(3, 0.82f, 2100, 0.0f, 0.0f, 0.2f, 0.0f, 0.8f, 0.3f, 0.1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                       new WandGemMaterialStats(3, 0.81f, 2000, 0.0f, 0.2f, 0.3f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                            new WandGemMaterialStats(3, 0.84f, 2600, 0.0f, 0.0f, 0f, 0.95f, 0.25f, 0.1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                         new WandGemMaterialStats(3, 0.92f, 2200, 0.3f, 0.0f, 0f, 0.0f, 0.4f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                        new WandGemMaterialStats(3, 1.00f, 3500, 0.6f, 0.6f, 0.6f, 0.6f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                        new WandGemMaterialStats(3, 1.00f, 3000, 0.0f, 0.0f, 0f, 0.0f, 0.6f, 0.6f, 0.6f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                         new WandGemMaterialStats(3, 0.90f, 2400, 0.0f, 0.0f, 0.6f, 0.2f, 0.0f, 0.4f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                         new WandGemMaterialStats(3, 0.95f, 2600, 0.9f, 0.25f, 0f, 0.0f, 0.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.gelonixite.material,                        new WandGemMaterialStats(3, 1.05f, 3000, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.equistalite.material,                       new WandGemMaterialStats(3, 1.05f, 3200, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cenetenium.material,                        new WandGemMaterialStats(3, 1.05f, 3500, 0.4f, 0.0f, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altumaerite.material,                       new WandGemMaterialStats(3, 1.05f, 3300, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.anguigenium.material,                       new WandGemMaterialStats(3, 1.05f, 3400, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.roviqurium.material,                        new WandGemMaterialStats(3, 1.05f, 3200, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cerecesnium.material,                       new WandGemMaterialStats(3, 1.10f, 4200, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f, 1.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altisavanium.material,                      new WandGemMaterialStats(3, 1.10f, 4000, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f));

        //CORES
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone,                                     new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.3f, 0.85f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.reed,                                     new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.35f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.feather,                                  new WandCoreMaterialStats(3, 1.2f, 0.0f, 0.0f, 0.9f, 0.0f, 0.2f, 0.05f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                   new WandCoreMaterialStats(3, 0.7f, 0.4f, 0.0f, 0.0f, 0.7f, 0.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper,                                    new WandCoreMaterialStats(3, 0.6f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.4f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                     new WandCoreMaterialStats(3, 0.85f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                   new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.35f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                   new WandCoreMaterialStats(3, 0.8f, 0.25f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blaze,                                    new WandCoreMaterialStats(3, 1.2f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ice,                                      new WandCoreMaterialStats(3, 1.1f, 0.0f, 0.95f, 0.0f, 0.0f, 0.0f, 0.1f, 0.15f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"), new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.0f, 0.0f, 0.0f, 0.35f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"), new WandCoreMaterialStats(3, 0.85f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"), new WandCoreMaterialStats(3, 1f, 0.4f, 0.1f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("electrum"),         new WandCoreMaterialStats(3, 0.95f, 0.15f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("nickel"),           new WandCoreMaterialStats(3, 0.9f, 0.4f, 0.0f, 0.0f, 0.75f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lead"),             new WandCoreMaterialStats(3, 0.85f, 0.2f, 0.0f, 0.0f, 0.0f, 0.95f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("chocolate"),        new WandCoreMaterialStats(3, 0.65f, 0.0f, 0.3f, 0.0f, 0.2f, 0.0f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("manasteel"),        new WandCoreMaterialStats(3, 0.95f, 0.0f, 0.15f, 0.0f, 0.2f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.ravenFeather,                                  new WandCoreMaterialStats(3, 1.3f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f, 0.4f));
        TinkerRegistry.addMaterialStats(TConstruct.steeleaf,                                      new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.3f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge,                                   new WandCoreMaterialStats(3, 1.8f, 0.0f, 0.7f, 0.0f, 0.0f, 0.3f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel,                                    new WandCoreMaterialStats(3, 0.95f, 0.75f, 0.0f, 0.6f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),       new WandCoreMaterialStats(3, 1f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lumium_plustic"),   new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.35f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),          new WandCoreMaterialStats(3, 1.1f, 0.0f, 0.1f, 0.0f, 0.2f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("terrasteel"),       new WandCoreMaterialStats(3, 1.2f, 0.15f, 0.15f, 0.15f, 0.1f, 0.0f, 0.95f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("mirion"),           new WandCoreMaterialStats(3, 1.25f, 0.0f, 0.0f, 0.2f, 0.0f, 0.45f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                          new WandCoreMaterialStats(3, 0.9f, 0.3f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.slipsteel.material,                         new WandCoreMaterialStats(3, 1.05f, 0.8f, 0.0f, 0.0f, 0.4f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                           new WandCoreMaterialStats(3, 0.88f, 0.0f, 0.3f, 0.1f, 0.0f, 0.0f, 0.7f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                      new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.0f, 0.0f, 0.7f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                          new WandCoreMaterialStats(3, 1.1f, 0.0f, 0.0f, 0.9f, 0.2f, 0.15f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                          new WandCoreMaterialStats(3, 1.2f, 0.0f, 1.05f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.skyrootLeaf,                                   new WandCoreMaterialStats(3, 1.05f, 0.0f, 0.45f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.betweensteel.material,                      new WandCoreMaterialStats(3, 1f, 0.1f, 0.0f, 0.3f, 0.0f, 0.7f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                            new WandCoreMaterialStats(3, 1.2f, 0.0f, 0.3f, 0.0f, 0.8f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cannium.material,                           new WandCoreMaterialStats(3, 1.3f, 0.3f, 0.0f, 0.0f, 0.8f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                           new WandCoreMaterialStats(3, 0.95f, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                         new WandCoreMaterialStats(3, 0.85f, 1.2f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                        new WandCoreMaterialStats(3, 1.25f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.peractium.material,                         new WandCoreMaterialStats(3, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                            new WandCoreMaterialStats(3, 1.15f, 0.2f, 0.2f, 1f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                          new WandCoreMaterialStats(3, 1.1f, 0.0f, 0.3f, 0.0f, 0.0f, 1.1f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                         new WandCoreMaterialStats(3, 1.3f, 0.0f, 0.9f, 0.0f, 0.4f, 0.0f, 0.0f, 0.15f));
        TinkerRegistry.addMaterialStats(ModuleBase.crystalLeaf,                                   new WandCoreMaterialStats(4, 1.6f, 0.0f, 0.6f, 0.55f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenOakLeaf,                                 new WandCoreMaterialStats(4, 1.5f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.55f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenFeather,                                 new WandCoreMaterialStats(4, 1.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f, 0.5f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                         new WandCoreMaterialStats(4, 1.05f, 0.0f, 0.0f, 0.3f, 0.0f, 0.2f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                        new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.3f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                           new WandCoreMaterialStats(4, 1.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.45f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                          new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.4f, 0.7f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                           new WandCoreMaterialStats(4, 1.2f, 0.85f, 0.0f, 0.0f, 0.45f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.antenium.material,                          new WandCoreMaterialStats(4, 0.9f, 0.0f, 0.4f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.accelerase.material,                        new WandCoreMaterialStats(4, 1.1f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.45f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.soldrite.material,                          new WandCoreMaterialStats(4, 0.95f, 0.0f, 0.8f, 0.0f, 0.55f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                            new WandCoreMaterialStats(4, 1.05f, 0.15f, 0.0f, 0.0f, 0.95f, 0.0f, 0.1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                       new WandCoreMaterialStats(4, 1.05f, 0.0f, 0.35f, 0.2f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                        new WandCoreMaterialStats(4, 1.3f, 0.0f, 0.0f, 0.0f, 0.0f, 0.45f, 0.4f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.urielium.material,                          new WandCoreMaterialStats(4, 1.15f, 0.45f, 0.0f, 0.0f, 0.05f, 0.0f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                         new WandCoreMaterialStats(4, 1.1f, 0.8f, 0.35f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                        new WandCoreMaterialStats(4, 1.65f, 0.4f, 0.4f, 0.4f, 0.4f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                         new WandCoreMaterialStats(4, 1.2f, 0.0f, 0.0f, 0.6f, 0.2f, 0.0f, 0.3f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ptemnium.material,                          new WandCoreMaterialStats(4, 1.15f, 0.0f, 0.0f, 0.4f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                         new WandCoreMaterialStats(5, 1.3f, 0.45f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(MetalMaterial.gelonixite.material,                        new WandCoreMaterialStats(5, 1.05f, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.equistalite.material,                       new WandCoreMaterialStats(5, 1.35f, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cenetenium.material,                        new WandCoreMaterialStats(5, 1.15f, 0.4f, 0.0f, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altumaerite.material,                       new WandCoreMaterialStats(5, 1.35f, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.anguigenium.material,                       new WandCoreMaterialStats(5, 1.3f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.roviqurium.material,                        new WandCoreMaterialStats(5, 1.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cerecesnium.material,                       new WandCoreMaterialStats(5, 1.4f, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f, 1.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altisavanium.material,                      new WandCoreMaterialStats(5, 1.1f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f));

        //SOCKETS
        TinkerRegistry.addMaterialStats(MetalMaterial.accelerase.material,                              new WandSocketMaterialStats(150, 14, 0.0f, 0.0f, 0.95f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.adamantite.material,                              new WandSocketMaterialStats(500, 15, 0.6f, 0.6f, 0.6f, 0.6f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.aerium.material,                                  new WandSocketMaterialStats(150, 12, 0.2f, 0.15f, 1.1f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.antenium.material,                                new WandSocketMaterialStats(200, 14, 0.0f, 0.2f, 0.0f, 0.0f, 0.9f, 0.1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ascalite.material,                                new WandSocketMaterialStats(170, 12, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.betweensteel.material,                            new WandSocketMaterialStats(190, 11, 0.0f, 0.0f, 0.3f, 0.0f, 0.6f, 0.0f, 0.35f));
        TinkerRegistry.addMaterialStats(MetalMaterial.black_rosite.material,                            new WandSocketMaterialStats(110, 11, 0.0f, 0.0f, 0.0f, 0.7f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime,                                      new WandSocketMaterialStats(120, 11, 0.0f, 1.2f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone,                                           new WandSocketMaterialStats(20, 3, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("boron_nitride"),          new WandSocketMaterialStats(100, 14, 0.0f, 0.0f, 0.2f, 0.0f, 0.8f, 0.2f, 0.1f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze,                                         new WandSocketMaterialStats(40, 7, 0.85f, 0.0f, 0.0f, 0.0f, 0.25f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,                                         new WandSocketMaterialStats(15, 5, 0.4f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cannium.material,                                 new WandSocketMaterialStats(60, 11, 0.3f, 0.0f, 0.0f, 0.8f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("chocolate"),              new WandSocketMaterialStats(20, 5, 0.0f, 0.3f, 0.2f, 0.0f, 0.0f, 0.0f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,                                         new WandSocketMaterialStats(25, 5, 0.15f, 0.15f, 0.9f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.dupondium.material,                               new WandSocketMaterialStats(220, 15, 0.8f, 0.2f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum,                                       new WandSocketMaterialStats(30, 7, 0.15f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f, 0.85f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("elementium"),             new WandSocketMaterialStats(40, 9, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("enderium_plustic"),       new WandSocketMaterialStats(50, 9, 0.0f, 0.0f, 0.0f, 0.0f, 0.35f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.escalite.material,                                new WandSocketMaterialStats(30, 11, 0.3f, 0.0f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.faerite.material,                                 new WandSocketMaterialStats(90, 11, 0.0f, 0.2f, 0.1f, 0.0f, 0.0f, 0.75f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.fierkite.material,                                new WandSocketMaterialStats(140, 13, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(TConstruct.fierymetal,                                          new WandSocketMaterialStats(50, 11, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.goldenAmber,                                         new WandSocketMaterialStats(140, 14, 0.15f, 0.0f, 0.0f, 0.25f, 0.0f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(ModuleBase.gravitite,                                           new WandSocketMaterialStats(130, 14, 0.0f, 0.35f, 0.75f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ice,                                            new WandSocketMaterialStats(0,  5, 0.0f, 1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.infernium.material,                               new WandSocketMaterialStats(90, 12, 0.9f, 0.0f, 0.0f, 0.0f, 0.6f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("iridium"),                new WandSocketMaterialStats(66, 11, 0.0f, 0.1f, 0.0f, 0.25f, 0.8f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,                                           new WandSocketMaterialStats(30, 5, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f));
        TinkerRegistry.addMaterialStats(TConstruct.knightmetal,                                         new WandSocketMaterialStats(50, 11, 0.0f, 0.0f, 0.0f, 0.0f, 1f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime,                                    new WandSocketMaterialStats(40, 7, 0.0f, 0.3f, 0.0f, 0.7f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lead"),                   new WandSocketMaterialStats(30, 7, 0.2f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.littium.material,                                 new WandSocketMaterialStats(90, 12, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 1f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lordslime.material,                               new WandSocketMaterialStats(130, 12, 0.0f, 0.9f, 0.0f, 0.2f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.luginite.material,                                new WandSocketMaterialStats(140, 11, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("lumium_plustic"),         new WandSocketMaterialStats(40, 9, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.lunite.material,                                  new WandSocketMaterialStats(160, 13, 0.0f, 0.0f, 0.0f, 0.9f, 0.3f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime,                                     new WandSocketMaterialStats(140, 12, 0.9f, 0.0f, 0.1f, 0.0f, 0.0f, 0.2f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("manasteel"),              new WandSocketMaterialStats(20, 7, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("mirion"),                 new WandSocketMaterialStats(100, 11, 0.0f, 0.0f, 0.2f, 0.0f, 0.4f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("nickel"),                 new WandSocketMaterialStats(60, 9, 0.4f, 0.0f, 0.0f, 0.7f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(Betweenlands.octine,                                            new WandSocketMaterialStats(85, 11, 1f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orichalcum.material,                              new WandSocketMaterialStats(195, 15, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.4f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.orinase.material,                                 new WandSocketMaterialStats(160, 13, 0.8f, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper,                                          new WandSocketMaterialStats(-100, 3, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.3f));
        TinkerRegistry.addMaterialStats(MetalMaterial.peractium.material,                               new WandSocketMaterialStats(80, 12, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.8f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.piridium.material,                                new WandSocketMaterialStats(120, 11, 0.0f, 0.0f, 0.9f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("platinum_plustic"),       new WandSocketMaterialStats(55, 9, 0.0f, 0.2f, 0.8f, 0.0f, 0.0f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.ptemnium.material,                                new WandSocketMaterialStats(220, 15, 0.0f, 0.0f, 0.3f, 0.0f, 0.7f, 0.15f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.rallium.material,                                 new WandSocketMaterialStats(110, 13, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.9f));
        TinkerRegistry.addMaterialStats(MetalMaterial.sestertium.material,                              new WandSocketMaterialStats(125, 12, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("signalum_plustic"),       new WandSocketMaterialStats(40, 7, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.1f, 0.9f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,                                         new WandSocketMaterialStats(25, 7, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.3f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime,                                          new WandSocketMaterialStats(120, 9, 0.0f, 1f, 0.3f, 0.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.slipsteel.material,                               new WandSocketMaterialStats(80, 7, 0.8f, 0.0f, 0.1f, 0.4f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.solairite.material,                               new WandSocketMaterialStats(140, 14, 0.3f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.7f));
        TinkerRegistry.addMaterialStats(MetalMaterial.soldrite.material,                                new WandSocketMaterialStats(160, 14, 0.0f, 0.9f, 0.0f, 0.3f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge,                                         new WandSocketMaterialStats(400, 12, 0.0f, 0.6f, 0.0f, 0.0f, 0.3f, 0.0f, 0.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.starsteel.material,                               new WandSocketMaterialStats(140, 14, 0.0f, 0.0f, 0.6f, 0.2f, 0.0f, 0.4f, 0.2f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel,                                          new WandSocketMaterialStats(80, 5, 0.6f, 0.0f, 0.6f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.superlumium.material,                             new WandSocketMaterialStats(115, 14, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.25f, 0.8f));
        TinkerRegistry.addMaterialStats(MetalMaterial.tempestite.material,                              new WandSocketMaterialStats(115, 13, 0.0f, 0.3f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.terium.material,                                  new WandSocketMaterialStats(140, 14, 0.0f, 0.0f, 0.0f, 0.95f, 0.0f, 0.1f, 0.35f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("terrasteel"),             new WandSocketMaterialStats(80, 11, 0.1f, 0.1f, 0.1f, 0.1f, 0.0f, 1f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.unseelium.material,                               new WandSocketMaterialStats(120, 13, 0.0f, 0.0f, 0.3f, 0.0f, 0.0f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.urielium.material,                                new WandSocketMaterialStats(250, 15, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.stone,                                          new WandSocketMaterialStats(10, 2, 0.0f, 0.0f, 0.0f, 0.95f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.obsidian,                                       new WandSocketMaterialStats(60, 6, 0.6f, 0.1f, 0.0f, 0.6f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.endstone,                                       new WandSocketMaterialStats(40, 9, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.5f, 0.7f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.netherrack,                                     new WandSocketMaterialStats(30, 6, 0.7f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood,                                           new WandSocketMaterialStats(20, 3, 0.0f, 0.0f, 0.0f, 0.7f, 0.0f, 0.0f, 0.6f));
        TinkerRegistry.addMaterialStats(TinkerRegistry.getMaterial("livingwood_plustic"),     new WandSocketMaterialStats(25, 4, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.7f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.gelonixite.material,                              new WandSocketMaterialStats(200, 16, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f));
        TinkerRegistry.addMaterialStats(MetalMaterial.equistalite.material,                             new WandSocketMaterialStats(290, 16, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cenetenium.material,                              new WandSocketMaterialStats(400, 16, 0.4f, 0.0f, 0.0f, 1.2f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altumaerite.material,                             new WandSocketMaterialStats(210, 16, 0.0f, 0.4f, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.anguigenium.material,                             new WandSocketMaterialStats(270, 16, 1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.roviqurium.material,                              new WandSocketMaterialStats(220, 16, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 1.2f, 0.0f));
        TinkerRegistry.addMaterialStats(MetalMaterial.cerecesnium.material,                             new WandSocketMaterialStats(350, 18, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f, 1.2f));
        TinkerRegistry.addMaterialStats(MetalMaterial.altisavanium.material,                            new WandSocketMaterialStats(250, 18, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f));

        //TRAITS
        addAllWandTrait(MetalMaterial.escalite.material, Nourishing.nourishing);
        addAllWandTrait(MetalMaterial.accelerase.material, BasicWandTrait.turbocaster);
        addAllWandTrait(MetalMaterial.betweensteel.material, BasicWandTrait.necropolis);
        addAllWandTrait(MetalMaterial.antenium.material, BasicWandTrait.archdemon);
        addAllWandTrait(Betweenlands.octine, BasicWandTrait.flare);
        addAllWandTrait(Betweenlands.octine, BasicWandTrait.heckfire);
        addAllWandTrait(Betweenlands.valonite, BasicWandTrait.glacial);
        addAllWandTrait(Betweenlands.valonite, BasicWandTrait.quake);
        addAllWandTrait(Betweenlands.scabyst, BasicWandTrait.chronic);
        addAllWandTrait(Netherstar.netherstar, Ritualist.ritualist);
        addAllWandTrait(MetalMaterial.adamantite.material, BasicWandTrait.enhancer);
        addAllWandTrait(ModuleBase.goldenOakLeaf, BasicWandTrait.regal);
        addAllWandTrait(ModuleBase.goldenOakLeaf, BasicWandTrait.inferno);
        addAllWandTrait(MetalMaterial.lordslime.material, BasicWandTrait.adherent);
        addAllWandTrait(MetalMaterial.orichalcum.material, BasicWandTrait.turbocaster);
        addAllWandTrait(MetalMaterial.orichalcum.material, BasicWandTrait.longshot);
        addAllWandTrait(MetalMaterial.tempestite.material, BasicWandTrait.glacial);
        addAllWandTrait(MetalMaterial.peractium.material, BasicWandTrait.plague);
        addAllWandTrait(MetalMaterial.cannium.material, BasicWandTrait.eternalSleep);
        addAllWandTrait(MetalMaterial.dupondium.material, ImmortalMagus.immortalMagus);
        addAllWandTrait(MetalMaterial.slipsteel.material, BasicWandTrait.carbon);
        addAllWandTrait(MetalMaterial.littium.material, Stonebinder.stonebinder);
        addAllWandTrait(MetalMaterial.aerium.material, BasicWandTrait.voltaic);
        MetalMaterial.aerium.material.addTrait(BasicWandTrait.ampere, WandGemMaterialStats.TYPE);
        addAllWandTrait(MetalMaterial.rallium.material, BasicWandTrait.stonemason);
        addAllWandTrait(MetalMaterial.infernium.material, BasicWandTrait.inferno);
        addAllWandTrait(MetalMaterial.infernium.material, BasicWandTrait.heckfire);
        addAllWandTrait(MetalMaterial.soldrite.material, BasicWandTrait.doctor);
        addAllWandTrait(MetalMaterial.fierkite.material, BasicWandTrait.ampere);
        addAllWandTrait(MetalMaterial.fierkite.material, BasicWandTrait.fulgur);
        addAllWandTrait(MetalMaterial.ascalite.material, BasicWandTrait.flare);
        addAllWandTrait(MetalMaterial.ascalite.material, BasicWandTrait.inferno);
        addAllWandTrait(MetalMaterial.piridium.material, BasicWandTrait.heatsink);
        addAllWandTrait(MetalMaterial.sestertium.material, Elementalist.elementalist);
        addAllWandTrait(MetalMaterial.urielium.material, BasicWandTrait.archdemon);
        addAllWandTrait(MetalMaterial.luginite.material, BasicWandTrait.antarctic);
        addAllWandTrait(MetalMaterial.superlumium.material, BasicWandTrait.borderless);
        addAllWandTrait(MetalMaterial.superlumium.material, BasicWandTrait.firstAid);
        addAllWandTrait(MetalMaterial.lunite.material, BasicWandTrait.newWorld);
        addAllWandTrait(MetalMaterial.solairite.material, BasicWandTrait.blueFlame);
        addAllWandTrait(MetalMaterial.solairite.material, BotanicalMana.mana);
        addAllWandTrait(MetalMaterial.starsteel.material, BasicWandTrait.ohm);
        addAllWandTrait(MetalMaterial.starsteel.material, BotanicalMana.mana);
        addAllWandTrait(MetalMaterial.black_rosite.material, BasicWandTrait.lapine);
        addAllWandTrait(MetalMaterial.orinase.material, BasicWandTrait.inferno);
        addAllWandTrait(MetalMaterial.orinase.material, TinkerTraits.sharp);
        addAllWandTrait(MetalMaterial.terium.material, Stonebinder.stonebinder);
        addAllWandTrait(MetalMaterial.unseelium.material, BotanicalMana.mana);
        addAllWandTrait(MetalMaterial.ptemnium.material, BasicWandTrait.farcaster);
        addAllWandTrait(MetalMaterial.faerite.material, BotanicalMana.mana);

        addAllWandTrait(MetalMaterial.gelonixite.material, BasicWandTrait.antarctic);
        addAllWandTrait(MetalMaterial.gelonixite.material, Stonebinder.stonebinder);
        addAllWandTrait(MetalMaterial.equistalite.material, BasicWandTrait.necropolis);
        addAllWandTrait(MetalMaterial.equistalite.material, Warmage.warmage);
        addAllWandTrait(MetalMaterial.cenetenium.material, BasicWandTrait.stonemason);
        addAllWandTrait(MetalMaterial.cenetenium.material, ImmortalMagus.immortalMagus);
        addAllWandTrait(MetalMaterial.altumaerite.material, BasicWandTrait.lightningRod);
        addAllWandTrait(MetalMaterial.altumaerite.material, Nourishing.nourishing);
        addAllWandTrait(MetalMaterial.anguigenium.material, BasicWandTrait.archdemon);
        addAllWandTrait(MetalMaterial.anguigenium.material, ForceOfNature.forceOfNature);
        addAllWandTrait(MetalMaterial.roviqurium.material, BasicWandTrait.madness);
        addAllWandTrait(MetalMaterial.roviqurium.material, Elementalist.elementalist);
        addAllWandTrait(MetalMaterial.cerecesnium.material, BasicWandTrait.enhancer);
        addAllWandTrait(MetalMaterial.cerecesnium.material, BotanicalMana.mana);
        addAllWandTrait(MetalMaterial.cerecesnium.material, Botanical.botanical.get(0));
        addAllWandTrait(MetalMaterial.altisavanium.material, Ritualist.ritualist);
        addAllWandTrait(MetalMaterial.altisavanium.material, TinkerTraits.dense);
        addAllWandTrait(MetalMaterial.altisavanium.material, TinkerTraits.writable);

        addAllWandTrait(TConstruct.steeleaf, BasicWandTrait.carbon);
        addAllWandTrait(TConstruct.fierymetal, BasicWandTrait.inferno);
        TConstruct.fierymetal.addTrait(BasicWandTrait.heckfire, WandGemMaterialStats.TYPE);
        addAllWandTrait(TConstruct.ravenFeather, BasicWandTrait.farcaster);
        addAllWandTrait(TConstruct.knightmetal, Warmage.warmage);
        addAllWandTrait(ModuleBase.goldenFeather, BasicWandTrait.longshot);
        addAllWandTrait(ModuleBase.skyrootLeaf, BasicWandTrait.flashFreeze);
        addAllWandTrait(ModuleBase.zanite, Stonebinder.stonebinder);
        addAllWandTrait(ModuleBase.gravitite, BasicWandTrait.coldSteel);
        addAllWandTrait(ModuleBase.gravitite, BasicWandTrait.accelerator);
        addAllWandTrait(ModuleBase.crystalLeaf, BasicWandTrait.blizzard);
        addAllWandTrait(TinkerRegistry.getMaterial("elementium"), BotanicalMana.mana);
        addAllWandTrait(TinkerRegistry.getMaterial("elementium"), BasicWandTrait.quake);
        addAllWandTrait(TinkerRegistry.getMaterial("terrasteel"), BotanicalMana.mana);
        addAllWandTrait(TinkerRegistry.getMaterial("terrasteel"), Terrafirma.terrafirma.get(0));
        addAllWandTrait(TinkerRegistry.getMaterial("manasteel"), BotanicalMana.mana);
        addAllWandTrait(TinkerRegistry.getMaterial("mirion"), BotanicalMana.mana);
        addAllWandTrait(TinkerRegistry.getMaterial("mirion"), Mirabile.mirabile);
        addAllWandTrait(TinkerRegistry.getMaterial("amethyst"), BasicWandTrait.apocrypha);
        addAllWandTrait(TinkerMaterials.knightslime, BasicWandTrait.regal);
        addAllWandTrait(TinkerMaterials.electrum, BasicWandTrait.voltaic);
        addAllWandTrait(TinkerMaterials.flint, BasicWandTrait.quake);
        addAllWandTrait(TinkerRegistry.getMaterial("sapphire"), BasicWandTrait.heatsink);
        addAllWandTrait(TinkerMaterials.copper, BasicWandTrait.ampere);
        addAllWandTrait(TinkerRegistry.getMaterial("ruby"), BasicWandTrait.flare);
        addAllWandTrait(TinkerMaterials.cactus, BasicWandTrait.needler);
        addAllWandTrait(TinkerMaterials.endstone, BasicWandTrait.coldSteel);
        addAllWandTrait(TinkerRegistry.getMaterial("platinum_plustic"), BasicWandTrait.borderless);
        addAllWandTrait(TinkerMaterials.steel, BasicWandTrait.carbon);
        addAllWandTrait(TinkerMaterials.feather, BasicWandTrait.fulgur);
        addAllWandTrait(TinkerMaterials.lead, BasicWandTrait.newWorld);
        addAllWandTrait(TinkerRegistry.getMaterial("malachite_gem"), Warmage.warmage);
        TinkerRegistry.getMaterial("malachite_gem").addTrait(BasicWandTrait.inferno, WandGemMaterialStats.TYPE);
        addAllWandTrait(TinkerRegistry.getMaterial("topaz"), ForceOfNature.forceOfNature);
        addAllWandTrait(TinkerRegistry.getMaterial("livingwood_plustic"), Botanical.botanical.get(0));
        TinkerRegistry.getMaterial("livingwood_plustic").addTrait(Botanical.botanical.get(1), WandGemMaterialStats.TYPE);
        addAllWandTrait(TinkerRegistry.getMaterial("iridium"), BasicWandTrait.lightningRod);
        addAllWandTrait(TinkerRegistry.getMaterial("boron_nitride"), Stonebinder.stonebinder);
        addAllWandTrait(TinkerMaterials.sponge, BasicWandTrait.firstAid);
        addAllWandTrait(TinkerMaterials.stone, BasicWandTrait.quake);
        addAllWandTrait(TinkerMaterials.bone, BasicWandTrait.plague);
        addAllWandTrait(TinkerMaterials.paper, BasicWandTrait.chronic);
        addAllWandTrait(TinkerMaterials.wood, BasicWandTrait.aerosol);
        addAllWandTrait(TinkerMaterials.prismarine, BasicWandTrait.heatsink);
        addAllWandTrait(TinkerMaterials.bronze, BasicWandTrait.flare);
        addAllWandTrait(TinkerRegistry.getMaterial("amber"), BasicWandTrait.voltaic);
        addAllWandTrait(TinkerMaterials.blaze, BasicWandTrait.flare);
        addAllWandTrait(TinkerMaterials.ice, BasicWandTrait.heatsink);
        addAllWandTrait(TinkerMaterials.reed, BasicWandTrait.farcaster);
        addAllWandTrait(TinkerMaterials.silver, BasicWandTrait.borderless);
        addAllWandTrait(TinkerRegistry.getMaterial("signalum_plustic"), BasicWandTrait.blueFlame);
        addAllWandTrait(TinkerRegistry.getMaterial("chocolate"), BasicWandTrait.regal);
        addAllWandTrait(TinkerMaterials.obsidian, BasicWandTrait.glacial);
        addAllWandTrait(TinkerMaterials.obsidian, BasicWandTrait.flare);
        addAllWandTrait(TinkerRegistry.getMaterial("lumium_plustic"), BasicWandTrait.borderless);
        addAllWandTrait(TinkerMaterials.iron, BasicWandTrait.newWorld);
        addAllWandTrait(TinkerRegistry.getMaterial("nickel"), BasicWandTrait.newWorld);
        addAllWandTrait(TinkerRegistry.getMaterial("peridot"), BasicWandTrait.borderless);
        addAllWandTrait(TinkerMaterials.netherrack, BasicWandTrait.flare);
        addAllWandTrait(TinkerRegistry.getMaterial("enderium_plustic"), BasicWandTrait.longshot);
        addAllWandTrait(TinkerMaterials.slime, BasicWandTrait.farcaster);
        addAllWandTrait(TinkerRegistry.getMaterial("tanzanite"), BasicWandTrait.heatsink);
        addAllWandTrait(TinkerMaterials.blueslime, BasicWandTrait.chronic);
        addAllWandTrait(TinkerMaterials.magmaslime, BasicWandTrait.archdemon);

    }

    private static  void addAllWandTrait(Material mat, ITrait trait) {
        mat.addTrait(trait, WandGemMaterialStats.TYPE);
        mat.addTrait(trait, WandCoreMaterialStats.TYPE);
        mat.addTrait(trait, WandSocketMaterialStats.TYPE);
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        addWandModifierItems();
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
