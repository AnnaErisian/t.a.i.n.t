package blue.thejester.taint.modules;

import blue.thejester.taint.helper.fluid.Create;
import blue.thejester.taint.traits.Ignitive;
import blue.thejester.taint.traits.Midden;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import landmaster.plustic.traits.Terrafirma;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTraits;
import thebetweenlands.common.item.misc.ItemMisc;
import thebetweenlands.common.registries.BlockRegistry;
import thebetweenlands.common.registries.FluidRegistry;
import thebetweenlands.common.registries.ItemRegistry;

public class Betweenlands implements IModule {
    private Fluid syrmoriteFluid;
    private Fluid octineFluid;

    @Override
    public void init() {
        {
            Material valonite = new Material("valonite", 0xD8B7D9);
            valonite.addTrait(TinkerTraits.enderference, MaterialTypes.HEAD);
            NewMaterials.addToToolAll(valonite, TinkerTraits.established);
            NewMaterials.addToToolAll(valonite, Midden.midden);
            valonite.setCraftable(true).setCastable(false);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.indomitable, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.shielding, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.heavy, ArmorMaterialType.TRIM);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.shielding, ArmorMaterialType.TRIM);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.heavy, ArmorMaterialType.PLATES);
            ArmorMaterials.addArmorTrait(valonite, ArmorTraits.shielding, ArmorMaterialType.PLATES);
            valonite.addItem("gemValonite", 1, Material.VALUE_Gem);
            valonite.addItem("blockValonite", 1, Material.VALUE_Gem);
            TinkerRegistry.addMaterialStats(valonite,
                    new CoreMaterialStats(7, 10),
                    new PlatesMaterialStats(1.6f, 3.5f, 2),
                    new TrimMaterialStats(17),
                    new HeadMaterialStats(880, 7, 7, 4),
                    new HandleMaterialStats(1.25f, 50),
                    new ExtraMaterialStats(88));
            setRenderInfo(valonite, 0xD8B7D9);

            MaterialIntegration valoniteMi = new MaterialIntegration(valonite).setRepresentativeItem("gemValonite");
            TinkerRegistry.integrate(valoniteMi).preInit();
        }
        {
            Material octine = new Material("octine", 0xffe634);
            octine.setCraftable(false).setCastable(true);
            octine.addTrait(Ignitive.ignitive, MaterialTypes.HEAD);
            NewMaterials.addToToolAll(octine, Midden.midden);
            NewMaterials.addToToolAll(octine, TinkerTraits.superheat);
            octine.addItem("ingotOctine", 1, Material.VALUE_Ingot);
            octine.addItem("blockOctine", 1, Material.VALUE_Block);
            TinkerRegistry.addMaterialStats(octine,
                    new HeadMaterialStats(660, 6.5f, 5.5f, 2),
                    new HandleMaterialStats(1.1f, 15),
                    new ExtraMaterialStats(66),
                    new BowMaterialStats(0.2f, 0.5f, 1));
            setRenderInfo(octine, 0xffe634);

            octineFluid = Create.plain("octine", 0xffe634);
            MaterialIntegration octineMi = new MaterialIntegration(octine, octineFluid).setRepresentativeItem("ingotOctine");
            TinkerRegistry.integrate(octineMi).preInit();
        }
        {
            Material syrmorite = new Material("syrmorite", 0x4e58a7);
            syrmorite.setCraftable(false).setCastable(true);
            ArmorMaterials.addArmorTrait(syrmorite, ArmorTraits.steady, ArmorMaterialType.CORE);
            NewMaterials.addToArmorAll(syrmorite, ArmorTraits.lightweight);
            syrmorite.addItem("ingotSyrmorite", 1, Material.VALUE_Ingot);
            syrmorite.addItem("blockSyrmorite", 1, Material.VALUE_Block);
            TinkerRegistry.addMaterialStats(syrmorite,
                    new CoreMaterialStats(16, 16),
                    new PlatesMaterialStats(0.9f, 6, 1),
                    new TrimMaterialStats(6.3f));
            setRenderInfo(syrmorite, 0x4e58a7);

            syrmoriteFluid = Create.plain("syrmorite", 0x4e58a7);
            MaterialIntegration syrmoriteMi = new MaterialIntegration(syrmorite, syrmoriteFluid).setRepresentativeItem("ingotSyrmorite");
            TinkerRegistry.integrate(syrmoriteMi).preInit();
        }

        {
            Material scabyst = new Material("scabyst", 0x97bdb7);
            scabyst.setCraftable(true).setCastable(false);
            scabyst.addTrait(Terrafirma.terrafirma.get(0), ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(scabyst, ArmorTraits.aquaspeed, ArmorMaterialType.PLATES);
            NewMaterials.addToArmorAll(scabyst, ArmorTraits.shielding);
            scabyst.addItem("gemScabyst", 1, Material.VALUE_Gem);
            scabyst.addItem("blockScabyst", 1, Material.VALUE_Gem);
            TinkerRegistry.addMaterialStats(scabyst,
                    new CoreMaterialStats(15, 10),
                    new PlatesMaterialStats(1.6f, 6.5f, 0),
                    new TrimMaterialStats(17));
            setRenderInfo(scabyst, 0x97bdb7);

            MaterialIntegration scabystMi = new MaterialIntegration(scabyst).setRepresentativeItem("gemScabyst");
            TinkerRegistry.integrate(scabystMi).preInit();
        }
    }

    public void initLate() {
        TinkerRegistry.registerMelting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.SYRMORITE_INGOT.getID()), syrmoriteFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.SYRMORITE_NUGGET.getID()), syrmoriteFluid, 144 / 9);
        TinkerRegistry.registerMelting(new ItemStack(BlockRegistry.SYRMORITE_BLOCK), syrmoriteFluid, 144 * 9);
        TinkerRegistry.registerTableCasting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.SYRMORITE_NUGGET.getID()), TinkerSmeltery.castNugget, syrmoriteFluid, 144 / 9);
        TinkerRegistry.registerTableCasting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.SYRMORITE_INGOT.getID()), TinkerSmeltery.castIngot, syrmoriteFluid, 144);
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(BlockRegistry.SYRMORITE_BLOCK),  syrmoriteFluid, 144 * 9, 800));

        TinkerRegistry.registerMelting(new ItemStack(ItemRegistry.OCTINE_INGOT), octineFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.OCTINE_NUGGET.getID()), octineFluid, 144 / 9);
        TinkerRegistry.registerMelting(new ItemStack(BlockRegistry.OCTINE_BLOCK), octineFluid, 144 * 9);
        TinkerRegistry.registerTableCasting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.OCTINE_NUGGET.getID()), TinkerSmeltery.castNugget, octineFluid, 144 / 9);
        TinkerRegistry.registerTableCasting(new ItemStack(ItemRegistry.OCTINE_INGOT), TinkerSmeltery.castIngot, octineFluid, 144);
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(BlockRegistry.OCTINE_BLOCK),  octineFluid, 144 * 9, 800));

        TinkerRegistry.registerMelting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.TAR_DRIP.getID()), FluidRegistry.TAR, 100);
        TinkerRegistry.registerTableCasting(new ItemStack(ItemRegistry.ITEMS_MISC, 1, ItemMisc.EnumItemMisc.TAR_DRIP.getID()), ItemStack.EMPTY, FluidRegistry.TAR, 100);
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(BlockRegistry.TAR_SOLID),  FluidRegistry.TAR, 1000, 300));
        TinkerRegistry.registerMelting(new ItemStack(BlockRegistry.TAR_SOLID), FluidRegistry.TAR, 1000);


    }
}
