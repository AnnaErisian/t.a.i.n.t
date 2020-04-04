package blue.thejester.taint.modules;

import blue.thejester.taint.helper.fluid.Create;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.traits.*;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import c4.conarm.lib.traits.AbstractArmorTrait;
import landmaster.plustic.traits.*;
import landmaster.plustic.traits.armor.Invariant;
import nc.integration.tconstruct.conarm.trait.NCArmorTraits;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.commons.lang3.tuple.Pair;
import shnupbups.tinkersaether.traits.Launching;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.TinkerTraits;

public class NewMaterials implements IModule {

    @Override
    public void init() {
        initMaterials();

        MetalMaterial.tinardite.fluid = Create.plain("tinardite", 0x7a6463);
        MetalMaterial.daemotium.fluid = Create.plain("daemotium", 0x3e333f);

        initAlloys();
    }

    private void initMaterials() {
        {
            Material sageslime = new Material("Sageslime", 0xb6772b);
            TinkerRegistry.addMaterialStats(sageslime,
                    new CoreMaterialStats(12f, 15.5f),
                    new PlatesMaterialStats(0.6f, 13.2f, 1f),
                    new TrimMaterialStats(6f),
                    new HeadMaterialStats(450, 6.2f, 3.2f, 2),
                    new HandleMaterialStats(1.03f, 400),
                    new ExtraMaterialStats(110),
                    new BowMaterialStats(1.6f, 2.8f, 0.6f),
                    new FletchingMaterialStats(1f, 1.1f),
                    new BowStringMaterialStats(1.2f)
            );
            setRenderInfo(sageslime, 0xb6772b);
            sageslime.addTrait(ArmorTraits.invigorating, ArmorMaterialType.CORE);
            sageslime.addTrait(ArmorTraits.dramatic, ArmorMaterialType.PLATES);
            sageslime.addTrait(ArmorTraits.dramatic, ArmorMaterialType.TRIM);
            sageslime.addTrait(TinkerTraits.unnatural, MaterialTypes.HEAD);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.HANDLE);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.EXTRA);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.BOW);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.BOWSTRING);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.FLETCHING);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.PROJECTILE);
            sageslime.addTrait(TinkerTraits.crumbling, MaterialTypes.SHAFT);

            MetalMaterial.sageslime.fluid = Create.plain("sageslime", 0xb6772b);
            MaterialIntegration sageslimeMi = new MaterialIntegration(sageslime, MetalMaterial.sageslime.fluid, MetalMaterial.sageslime.getOreName()).setRepresentativeItem("ingotSageslime");
            TinkerRegistry.integrate(sageslimeMi).preInit();
        }
        {
            Material betweensteel = new Material("Betweensteel", 0x171a06);
            TinkerRegistry.addMaterialStats(betweensteel,
                    new CoreMaterialStats(17f, 17f),
                    new PlatesMaterialStats(0.95f, 6.0f, 1.0f),
                    new TrimMaterialStats(6.3f),
                    new HeadMaterialStats(770, 6.5f, 5.5f, 3),
                    new HandleMaterialStats(1.1f, 25),
                    new ExtraMaterialStats(77),
                    new BowMaterialStats(5.0f, 0.5f, 1f)
            );
            setRenderInfo(betweensteel, 0x171a06);
            ArmorMaterials.addArmorTrait(betweensteel, ArmorTraits.steady, ArmorMaterialType.CORE);
            addToArmorAll(betweensteel, Midden_Armor.midden_armor);
            addToArmorAll(betweensteel, StatusInflictor_Armor.tarcoated);
            betweensteel.addTrait(TinkerTraits.superheat, MaterialTypes.HEAD);
            addToToolAll(betweensteel, Midden.midden);
            addToToolAll(betweensteel, StatusInflictor.tarcoated);

            MetalMaterial.betweensteel.fluid = Create.plain("betweensteel", 0x171a06);
            MaterialIntegration betweensteelMi = new MaterialIntegration(betweensteel, MetalMaterial.betweensteel.fluid, MetalMaterial.betweensteel.getOreName()).setRepresentativeItem("ingotBetweensteel");
            TinkerRegistry.integrate(betweensteelMi).preInit();
        }
        {
            Material chaoite = new Material("Chaoite", 0x988597);
            TinkerRegistry.addMaterialStats(chaoite,
                    new CoreMaterialStats(15f, 18f),
                    new PlatesMaterialStats(1.2f, 8.4f, 1.8f),
                    new TrimMaterialStats(14f),
                    new HeadMaterialStats(660, 8f, 10.4f, 4),
                    new HandleMaterialStats(0.64f, 90),
                    new ExtraMaterialStats(150),
                    new BowMaterialStats(1.1f, 0.8f, 3f)
            );
            setRenderInfo(chaoite, 0x988597);
            chaoite.addTrait(StatusInflictor.antigravity, MaterialTypes.HEAD);
            chaoite.addTrait(Launching.launching, MaterialTypes.HEAD);
            chaoite.addTrait(Launching.launching);
            ArmorMaterials.addArmorTrait(chaoite, Floaty.floaty);

            MetalMaterial.chaoite.fluid = Create.plain("chaoite", 0x988597);
            MaterialIntegration chaoiteMi = new MaterialIntegration(chaoite, MetalMaterial.chaoite.fluid, MetalMaterial.chaoite.getOreName()).setRepresentativeItem("ingotChaoite");
            TinkerRegistry.integrate(chaoiteMi).preInit();
        }
        {
            Material tritanium = new Material("Tritanium", 0xffe6a1);
            TinkerRegistry.addMaterialStats(tritanium,
                    new CoreMaterialStats(30f, 22f),
                    new PlatesMaterialStats(1.1f, 20f, 4.5f),
                    new TrimMaterialStats(5.5f),
                    new HeadMaterialStats(2000, 9f, 9f, 2),
                    new HandleMaterialStats(0.7f, 200),
                    new ExtraMaterialStats(75)
            );
            setRenderInfo(tritanium, 0xffe6a1);
            tritanium.addTrait(Melty.melty);

            MetalMaterial.tritanium.fluid = Create.plain("tritanium", 0xffe6a1);
            MaterialIntegration tritaniumMi = new MaterialIntegration(tritanium, MetalMaterial.tritanium.fluid, MetalMaterial.tritanium.getOreName()).setRepresentativeItem("ingotTritanium");
            TinkerRegistry.integrate(tritaniumMi).preInit();
        }
        {
            Material white_steel = new Material("White Steel", 0xFFFFFF);
            TinkerRegistry.addMaterialStats(white_steel,
                    new CoreMaterialStats(13.8f, 15.5f),
                    new PlatesMaterialStats(1.06f, 11f, 2.5f),
                    new TrimMaterialStats(3.2f),
                    new HeadMaterialStats(750, 8.1f, 7.24f, 3),
                    new HandleMaterialStats(0.85f, 150),
                    new ExtraMaterialStats(75),
                    new BowMaterialStats(0.95f, 3f, 2.2f)
            );
            setRenderInfo(white_steel, 0xFFFFFF);
            addToToolAll(white_steel, GlowingTool.glowing);
            ArmorMaterials.addArmorTrait(white_steel, GlowingAllArmor.glowing);

            MetalMaterial.white_steel.fluid = Create.plain("white_steel", 0xFFFFFF);
            MaterialIntegration white_steelMi = new MaterialIntegration(white_steel, MetalMaterial.white_steel.fluid, MetalMaterial.white_steel.getOreName()).setRepresentativeItem("ingotWhiteSteel");
            TinkerRegistry.integrate(white_steelMi).preInit();
        }
        {
            Material oil_steel = new Material("Oil Steel", 0xaaa38f);
            TinkerRegistry.addMaterialStats(oil_steel,
                    new CoreMaterialStats(12f, 15f),
                    new PlatesMaterialStats(0.85f, 5f, 5f),
                    new TrimMaterialStats(3.5f),
                    new HeadMaterialStats(600, 5f, 5f, 3),
                    new HandleMaterialStats(1.08f, 70),
                    new ExtraMaterialStats(60),
                    new BowMaterialStats(0.3f, 1.2f, 12f)
            );
            setRenderInfo(oil_steel, 0xaaa38f);
            ArmorMaterials.addArmorTrait(oil_steel, Slippery_Armor.slippery);
            oil_steel.addTrait(Slippery.slippery);

            MetalMaterial.oil_steel.fluid = Create.plain("oil_steel", 0xaaa38f);
            MaterialIntegration oil_steelMi = new MaterialIntegration(oil_steel, MetalMaterial.oil_steel.fluid, MetalMaterial.oil_steel.getOreName()).setRepresentativeItem("ingotOilSteel");
            TinkerRegistry.integrate(oil_steelMi).preInit();
        }
        {
            Material lordslime = new Material("Lordslime", 0xd78e4c);
            TinkerRegistry.addMaterialStats(lordslime,
                    new CoreMaterialStats(20f, 18f),
                    new PlatesMaterialStats(0.7f, 18.2f, 2f),
                    new TrimMaterialStats(12f),
                    new HeadMaterialStats(1700, 7.7f, 7.7f, 5),
                    new HandleMaterialStats(1.1f, 500),
                    new ExtraMaterialStats(125),
                    new BowMaterialStats(1.7f, 2.6f, 2.5f),
                    new FletchingMaterialStats(1f, 1.2f),
                    new BowStringMaterialStats(1.25f)
            );
            setRenderInfo(lordslime, 0xd78e4c);
            lordslime.addTrait(TinkerTraits.unnatural);
            lordslime.addTrait(TinkerTraits.crumbling);
            ArmorMaterials.addArmorTrait(lordslime, ArmorTraits.invigorating);
            ArmorMaterials.addArmorTrait(lordslime, ArmorTraits.dramatic);

            MetalMaterial.lordslime.fluid = Create.plain("lordslime", 0xd78e4c);
            MaterialIntegration lordslimeMi = new MaterialIntegration(lordslime, MetalMaterial.lordslime.fluid, MetalMaterial.lordslime.getOreName()).setRepresentativeItem("ingotLordslime");
            TinkerRegistry.integrate(lordslimeMi).preInit();
        }
        {
            Material faerite = new Material("Faerite", 0xff00f2);
            TinkerRegistry.addMaterialStats(faerite,
                    new CoreMaterialStats(13f, 19f),
                    new PlatesMaterialStats(1f, 10f, 2f),
                    new TrimMaterialStats(2.8f),
                    new HeadMaterialStats(770, 8f, 8f, 4),
                    new HandleMaterialStats(1.05f, 180),
                    new ExtraMaterialStats(30),
                    new BowMaterialStats(1.3f, 1.8f, 7f)
            );
            setRenderInfo(faerite, 0xff00f2);
            faerite.addTrait(FaeFlight.faeFlight);
            faerite.addTrait(Terrafirma.terrafirma.get(0));
            ArmorMaterials.addArmorTrait(faerite, PermanentStatus_Armor.jump_boost, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(faerite, ArmorTraits.featherweight);

            MetalMaterial.faerite.fluid = Create.plain("faerite", 0xff00f2);
            MaterialIntegration faeriteMi = new MaterialIntegration(faerite, MetalMaterial.faerite.fluid, MetalMaterial.faerite.getOreName()).setRepresentativeItem("ingotFaerite");
            TinkerRegistry.integrate(faeriteMi).preInit();
        }
        {
            Material alubronze = new Material("Alubronze", 0xf7d99d);
            TinkerRegistry.addMaterialStats(alubronze,
                    new CoreMaterialStats(16f, 14f),
                    new PlatesMaterialStats(1.1f, 5.2f, 1.5f),
                    new TrimMaterialStats(6.5f),
                    new HeadMaterialStats(480, 7.2f, 3.5f, 2),
                    new HandleMaterialStats(1.05f, 60),
                    new ExtraMaterialStats(70),
                    new BowMaterialStats(0.6f, 1.5f, 5f)
            );
            setRenderInfo(alubronze, 0xf7d99d);
            alubronze.addTrait(TinkerTraits.dense);
            alubronze.addTrait(TinkerTraits.lightweight);
            ArmorMaterials.addArmorTrait(alubronze, ArmorTraits.lightweight);

            MetalMaterial.alubronze.fluid = Create.plain("alubronze", 0xf7d99d);
            MaterialIntegration alubronzeMi = new MaterialIntegration(alubronze, MetalMaterial.alubronze.fluid, MetalMaterial.alubronze.getOreName()).setRepresentativeItem("ingotAlubronze");
            TinkerRegistry.integrate(alubronzeMi).preInit();
        }
        {
            Material caelite = new Material("Caelite", 0x65755b);
            TinkerRegistry.addMaterialStats(caelite,
                    new CoreMaterialStats(18f, 20f),
                    new PlatesMaterialStats(0.9f, 11f, 2.5f),
                    new TrimMaterialStats(3.5f),
                    new HeadMaterialStats(680, 7.5f, 7.5f, 3),
                    new HandleMaterialStats(0.58f, 150),
                    new ExtraMaterialStats(50),
                    new BowMaterialStats(0.65f, 1.2f, 4.5f)
            );
            setRenderInfo(caelite, 0x65755b);
            caelite.addTrait(TinkerTraits.cheap);
            caelite.addTrait(TinkerTraits.cheap, MaterialTypes.HEAD);
            caelite.addTrait(TinkerTraits.lightweight, MaterialTypes.HEAD);
            ArmorMaterials.addArmorTrait(caelite, ArmorTraits.featherweight);


            MetalMaterial.caelite.fluid = Create.plain("caelite", 0x65755b);
            MaterialIntegration caeliteMi = new MaterialIntegration(caelite, MetalMaterial.caelite.fluid, MetalMaterial.caelite.getOreName()).setRepresentativeItem("ingotCaelite");
            TinkerRegistry.integrate(caeliteMi).preInit();
        }
        {
            Material atmium = new Material("Atmium", 0xd5d56a);
            TinkerRegistry.addMaterialStats(atmium,
                    new HeadMaterialStats(600, 7.5f, 8.2f, 4),
                    new HandleMaterialStats(0.8f, 180),
                    new ExtraMaterialStats(60),
                    new BowMaterialStats(1.2f, 1.1f, 3f)
            );
            atmium.addTrait(Annihilator.annihilator, MaterialTypes.HEAD);
            atmium.addTrait(TinkerTraits.poisonous);
            atmium.addTrait(Apocalypse.apocalypse);
            setRenderInfo(atmium, 0xd5d56a);

            MetalMaterial.atmium.fluid = Create.plain("atmium", 0xd5d56a);
            MaterialIntegration atmiumMi = new MaterialIntegration(atmium, MetalMaterial.atmium.fluid, MetalMaterial.atmium.getOreName()).setRepresentativeItem("ingotAtmium");
            TinkerRegistry.integrate(atmiumMi).preInit();
        }
        {
            Material pelagium = new Material("Pelagium", 0x89705b);
            TinkerRegistry.addMaterialStats(pelagium,
                    new CoreMaterialStats(9.2f, 6f),
                    new PlatesMaterialStats(0.6f, -1.5f, 0.5f),
                    new TrimMaterialStats(1.5f),
                    new HeadMaterialStats(80, 3f, 2f, 1),
                    new HandleMaterialStats(1.55f, 50),
                    new ExtraMaterialStats(15),
                    new BowMaterialStats(0.25f, 0.45f, -0.5f)
            );
            pelagium.addTrait(TinkerTraits.lightweight, MaterialTypes.HEAD);
            addToToolAll(pelagium, TinkerTraits.aquadynamic);
            ArmorMaterials.addArmorTrait(pelagium, ArmorTraits.lightweight, ArmorMaterialType.CORE);
            addToArmorAll(pelagium, ArmorTraits.aquaspeed);
            setRenderInfo(pelagium, 0x89705b);

            MetalMaterial.pelagium.fluid = Create.plain("pelagium", 0x89705b);
            MaterialIntegration pelagiumMi = new MaterialIntegration(pelagium, MetalMaterial.pelagium.fluid, MetalMaterial.pelagium.getOreName()).setRepresentativeItem("ingotPelagium");
            TinkerRegistry.integrate(pelagiumMi).preInit();
        }
        {
            Material sonium = new Material("Sonium", 0xc7e1cb);
            TinkerRegistry.addMaterialStats(sonium,
                    new CoreMaterialStats(14f, 16f),
                    new PlatesMaterialStats(0.92f, 4f, 1f),
                    new TrimMaterialStats(4f),
                    new HeadMaterialStats(440, 7f, 5f, 3),
                    new HandleMaterialStats(0.9f, 100),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(0.5f, 1.5f, 4f)
            );
            setRenderInfo(sonium, 0xc7e1cb);
            ArmorMaterials.addArmorTrait(sonium, PermanentStatus_Armor.hasty, ArmorMaterialType.CORE);
            addToArmorAll(sonium, ArmorTraits.featherweight);
            sonium.addTrait(TinkerTraits.lightweight);

            MetalMaterial.sonium.fluid = Create.plain("sonium", 0xc7e1cb);
            MaterialIntegration soniumMi = new MaterialIntegration(sonium, MetalMaterial.sonium.fluid, MetalMaterial.sonium.getOreName()).setRepresentativeItem("ingotSonium");
            TinkerRegistry.integrate(soniumMi).preInit();
        }
        {
            Material unseelium = new Material("Unseelium", 0x7b1687);
            TinkerRegistry.addMaterialStats(unseelium,
                    new CoreMaterialStats(17f, 21f),
                    new PlatesMaterialStats(1.3f, 16f, 4f),
                    new TrimMaterialStats(8f),
                    new HeadMaterialStats(1400, 8f, 11f, 5),
                    new HandleMaterialStats(1.05f, 200),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(1.1f, 1.7f, 12f)
            );
            setRenderInfo(unseelium, 0x7b1687);
            unseelium.addTrait(FaeFlight.faeFlight);
            unseelium.addTrait(RampingStatusInflictor.toxic);
            ArmorMaterials.addArmorTrait(unseelium, PermanentStatus_Armor.jump_boost, ArmorMaterialType.CORE);
            addToArmorAll(unseelium, NCArmorTraits.POISONOUS);

            MetalMaterial.unseelium.fluid = Create.plain("unseelium", 0x7b1687);
            MaterialIntegration unseeliumMi = new MaterialIntegration(unseelium, MetalMaterial.unseelium.fluid, MetalMaterial.unseelium.getOreName()).setRepresentativeItem("ingotUnseelium");
            TinkerRegistry.integrate(unseeliumMi).preInit();
        }
        {
            Material peractium = new Material("Peractium", 0x325c48);
            TinkerRegistry.addMaterialStats(peractium,
                    new HeadMaterialStats(1000, 6f, 9f, 3),
                    new HandleMaterialStats(1f, 60),
                    new ExtraMaterialStats(150),
                    new BowMaterialStats(1.05f, 1.1f, 9f)
            );
            setRenderInfo(peractium, 0x325c48);
            peractium.addTrait(SoulDrain.soulDrain);

            MetalMaterial.peractium.fluid = Create.plain("peractium", 0x325c48);
            MaterialIntegration peractiumMi = new MaterialIntegration(peractium, MetalMaterial.peractium.fluid, MetalMaterial.peractium.getOreName()).setRepresentativeItem("ingotPeractium");
            TinkerRegistry.integrate(peractiumMi).preInit();
        }
        {
            Material caeputescite = new Material("Caeputescite", 0x93af9c);
            TinkerRegistry.addMaterialStats(caeputescite,
                    new CoreMaterialStats(19f, 20f),
                    new PlatesMaterialStats(1.1f, 12.5f, 4f),
                    new TrimMaterialStats(13f),
                    new HeadMaterialStats(1919, 9f, 9f, 5),
                    new HandleMaterialStats(1.1f, 40),
                    new ExtraMaterialStats(90),
                    new BowMaterialStats(1.35f, 1.5f, 5.5f)
            );
            setRenderInfo(caeputescite, 0x93af9c);
            caeputescite.addTrait(TinkerTraits.cheap);
            caeputescite.addTrait(Apocalypse.apocalypse);
            addToArmorAll(caeputescite, NCArmorTraits.WITHERING);
            ArmorMaterials.addArmorTrait(caeputescite, ArmorTraits.lightweight, ArmorMaterialType.CORE);

            MetalMaterial.caeputescite.fluid = Create.plain("caeputescite", 0x93af9c);
            MaterialIntegration caeputesciteMi = new MaterialIntegration(caeputescite, MetalMaterial.caeputescite.fluid, MetalMaterial.caeputescite.getOreName()).setRepresentativeItem("ingotCaeputescite");
            TinkerRegistry.integrate(caeputesciteMi).preInit();
        }
        {
            Material superlumium = new Material("Superlumium", 0xfbf6cb);
            TinkerRegistry.addMaterialStats(superlumium,
                    new CoreMaterialStats(8f, 6f),
                    new PlatesMaterialStats(0.35f, -2f, 0f),
                    new TrimMaterialStats(0.25f),
                    new HeadMaterialStats(200, 18f, 3f, 5),
                    new HandleMaterialStats(0.6f, -50),
                    new ExtraMaterialStats(-80)
            );
            setRenderInfo(superlumium, 0xfbf6cb);
            superlumium.addTrait(Hyperspeed.hyperspeed, MaterialTypes.HEAD);
            superlumium.addTrait(TinkerTraits.lightweight);
            ArmorMaterials.addArmorTrait(superlumium, ArmorTraits.lightweight, ArmorMaterialType.TRIM);
            ArmorMaterials.addArmorTrait(superlumium, ArmorTraits.lightweight, ArmorMaterialType.PLATES);
            ArmorMaterials.addArmorTrait(superlumium, GlowingAllArmor.glowing, ArmorMaterialType.CORE);

            MetalMaterial.superlumium.fluid = Create.plain("superlumium", 0xfbf6cb);
            MaterialIntegration superlumiumMi = new MaterialIntegration(superlumium, MetalMaterial.superlumium.fluid, MetalMaterial.superlumium.getOreName()).setRepresentativeItem("ingotSuperlumium");
            TinkerRegistry.integrate(superlumiumMi).preInit();
        }
        {
            Material starsteel = new Material("Starsteel", 0x2f3d4f);
            TinkerRegistry.addMaterialStats(starsteel,
                    new HeadMaterialStats(204, 6f, 4f, 2),
                    new HandleMaterialStats(1f, 60),
                    new ExtraMaterialStats(100),
                    new BowMaterialStats(0.5f, 1.5f, 7f)
            );
            setRenderInfo(starsteel, 0x2f3d4f);
            starsteel.addTrait(Illuminati.illuminati);
            starsteel.addTrait(GlowingTool.glowing);

            MetalMaterial.starsteel.fluid = Create.plain("starsteel", 0x2f3d4f);
            MaterialIntegration starsteelMi = new MaterialIntegration(starsteel, MetalMaterial.starsteel.fluid, MetalMaterial.starsteel.getOreName()).setRepresentativeItem("ingotStarsteel");
            TinkerRegistry.integrate(starsteelMi).preInit();
        }
        {
            Material yellow_cobalt = new Material("Yellow Cobalt", 0xded623);
            TinkerRegistry.addMaterialStats(yellow_cobalt,
                    new CoreMaterialStats(22f, 20f),
                    new PlatesMaterialStats(0.9f, 8f, 3f),
                    new TrimMaterialStats(14f),
                    new HeadMaterialStats(1200, 12f, 6f, 4),
                    new HandleMaterialStats(0.9f, 120),
                    new ExtraMaterialStats(270),
                    new BowMaterialStats(1f, 1.8f, 11f)
            );
            setRenderInfo(yellow_cobalt, 0xded623);
            yellow_cobalt.addTrait(Apocalypse.apocalypse, MaterialTypes.HEAD);
            addToToolAll(yellow_cobalt, TinkerTraits.lightweight);
            addToToolAll(yellow_cobalt, TinkerTraits.momentum);
            ArmorMaterials.addArmorTrait(yellow_cobalt, NCArmorTraits.WITHERING, ArmorMaterialType.CORE);
            addToArmorAll(yellow_cobalt, ArmorTraits.featherweight);
            addToArmorAll(yellow_cobalt, ArmorTraits.lightweight);

            MetalMaterial.yellow_cobalt.fluid = Create.plain("yellow_cobalt", 0xded623);
            MaterialIntegration yellow_cobaltMi = new MaterialIntegration(yellow_cobalt, MetalMaterial.yellow_cobalt.fluid, MetalMaterial.yellow_cobalt.getOreName()).setRepresentativeItem("ingotYellowCobalt");
            TinkerRegistry.integrate(yellow_cobaltMi).preInit();
        }
        {
            Material aureclase = new Material("Aureclase", 0x0eedee);
            TinkerRegistry.addMaterialStats(aureclase,
                    new HeadMaterialStats(1600, 8f, 9f, 4),
                    new HandleMaterialStats(1.2f, 110),
                    new ExtraMaterialStats(90),
                    new BowMaterialStats(1.1f, 1.6f, 6f)
            );
            setRenderInfo(aureclase, 0x0eedee);
            aureclase.addTrait(AphroditeBlessing.aphroditeBlessing, MaterialTypes.HEAD);
            aureclase.addTrait(Mossy.mossy1, MaterialTypes.HEAD);
            aureclase.addTrait(Mossy.mossy1);

            MetalMaterial.aureclase.fluid = Create.plain("aureclase", 0x0eedee);
            MaterialIntegration aureclaseMi = new MaterialIntegration(aureclase, MetalMaterial.aureclase.fluid, MetalMaterial.aureclase.getOreName()).setRepresentativeItem("ingotAureclase");
            TinkerRegistry.integrate(aureclaseMi).preInit();
        }
        {
            Material cobalt_thorium_g = new Material("Cobalt Thorium G", 0x95c0cb);
            TinkerRegistry.addMaterialStats(cobalt_thorium_g,
                    new CoreMaterialStats(13.5f, 21f),
                    new PlatesMaterialStats(2f, 3.6f, 2f),
                    new TrimMaterialStats(4.7f),
                    new HeadMaterialStats(550, 2f, 11f, 4),
                    new HandleMaterialStats(1.05f, 90),
                    new ExtraMaterialStats(27),
                    new BowMaterialStats(0.38f, 1.5f, 4.5f)
            );
            setRenderInfo(cobalt_thorium_g, 0x95c0cb);
            cobalt_thorium_g.addTrait(RampingStatusInflictor.nuclear_winter, MaterialTypes.HEAD);
            cobalt_thorium_g.addTrait(Apocalypse.apocalypse);
            ArmorMaterials.addArmorTrait(cobalt_thorium_g, NCArmorTraits.WITHERING);

            MetalMaterial.cobalt_thorium_g.fluid = Create.plain("cobalt_thorium_g", 0x95c0cb);
            MaterialIntegration cobalt_thorium_gMi = new MaterialIntegration(cobalt_thorium_g, MetalMaterial.cobalt_thorium_g.fluid, MetalMaterial.cobalt_thorium_g.getOreName()).setRepresentativeItem("ingotCobaltThoriumG");
            TinkerRegistry.integrate(cobalt_thorium_gMi).preInit();
        }
        {
            Material florium = new Material("Florium", 0x21bb21);
            TinkerRegistry.addMaterialStats(florium,
                    new CoreMaterialStats(18.5f, 20f),
                    new PlatesMaterialStats(1.1f, 12.5f, 4f),
                    new TrimMaterialStats(13f),
                    new HeadMaterialStats(1800, 9.5f, 8f, 5),
                    new HandleMaterialStats(1.1f, 40),
                    new ExtraMaterialStats(90),
                    new BowMaterialStats(1.35f, 1.5f, 5.5f)
            );
            setRenderInfo(florium, 0x21bb21);
            ArmorMaterials.addArmorTrait(florium, FlowerPower_Armor.flowerpower, ArmorMaterialType.CORE);
            florium.addTrait(FlowerPower.flowerpower, MaterialTypes.HEAD);
            addToToolAll(florium, Mana.mana);
            ArmorMaterials.addArmorTrait(florium, Mana.mana, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(florium, Mana.mana, ArmorMaterialType.TRIM);
            ArmorMaterials.addArmorTrait(florium, Mana.mana, ArmorMaterialType.PLATES);

            MetalMaterial.florium.fluid = Create.plain("florium", 0x21bb21);
            MaterialIntegration floriumMi = new MaterialIntegration(florium, MetalMaterial.florium.fluid, MetalMaterial.florium.getOreName()).setRepresentativeItem("ingotFlorium");
            TinkerRegistry.integrate(floriumMi).preInit();
        }
        {
            Material gourmium = new Material("Gourmium", 0x5e220d);
            TinkerRegistry.addMaterialStats(gourmium,
                    new CoreMaterialStats(14.5f, 16.7f),
                    new PlatesMaterialStats(1.15f, 0f, 1f),
                    new TrimMaterialStats(10f),
                    new HeadMaterialStats(380, 7f, 7f, 2),
                    new HandleMaterialStats(1f, -20),
                    new ExtraMaterialStats(150),
                    new BowMaterialStats(0.6f, 1.4f, 7f)
            );
            setRenderInfo(gourmium, 0x5e220d);
            ArmorMaterials.addArmorTrait(gourmium, Delicious_Armor.delicious, ArmorMaterialType.CORE);
            gourmium.addTrait(Delicious.delicious, MaterialTypes.HEAD);
            gourmium.addTrait(TinkerTraits.tasty, MaterialTypes.HEAD);
            gourmium.addTrait(TinkerTraits.tasty, ArmorMaterialType.CORE);
            gourmium.addTrait(TinkerTraits.tasty);

            MetalMaterial.gourmium.fluid = Create.plain("gourmium", 0x5e220d);
            MaterialIntegration gourmiumMi = new MaterialIntegration(gourmium, MetalMaterial.gourmium.fluid, MetalMaterial.gourmium.getOreName()).setRepresentativeItem("ingotGourmium");
            TinkerRegistry.integrate(gourmiumMi).preInit();
        }
        {
            Material escalite = new Material("Escalite", 0x25a8c0);
            TinkerRegistry.addMaterialStats(escalite,
                    new HeadMaterialStats(220, 11f, 8f, 3),
                    new HandleMaterialStats(1.1f, 80),
                    new ExtraMaterialStats(110),
                    new BowMaterialStats(1.3f, 1.5f, 2f)
            );
            setRenderInfo(escalite, 0x25a8c0);
            escalite.addTrait(MasterChef.master_chef, MaterialTypes.HEAD);
            escalite.addTrait(TinkerTraits.tasty);
            escalite.addTrait(Delicious.delicious);

            MetalMaterial.escalite.fluid = Create.plain("escalite", 0x25a8c0);
            MaterialIntegration escaliteMi = new MaterialIntegration(escalite, MetalMaterial.escalite.fluid, MetalMaterial.escalite.getOreName()).setRepresentativeItem("ingotEscalite");
            TinkerRegistry.integrate(escaliteMi).preInit();
        }
        {
            Material alcite = new Material("Alcite", 0xf2faff);
            TinkerRegistry.addMaterialStats(alcite,
                    new HeadMaterialStats(1800, 7.5f, 11f, 4),
                    new HandleMaterialStats(1.02f, 60),
                    new ExtraMaterialStats(190),
                    new BowMaterialStats(0.4f, 1.2f, 7f)
            );
            setRenderInfo(alcite, 0xf2faff);
            alcite.addTrait(StatusInflictor.frosty);

            MetalMaterial.alcite.fluid = Create.plain("alcite", 0xf2faff);
            MaterialIntegration alciteMi = new MaterialIntegration(alcite, MetalMaterial.alcite.fluid, MetalMaterial.alcite.getOreName()).setRepresentativeItem("ingotAlcite");
            TinkerRegistry.integrate(alciteMi).preInit();
        }
        {
            Material urielium = new Material("Urielium", 0xe4f5ff);
            TinkerRegistry.addMaterialStats(urielium,
                    new CoreMaterialStats(22f, 21f),
                    new PlatesMaterialStats(1f, 12f, 2f),
                    new TrimMaterialStats(4f),
                    new HeadMaterialStats(2200, 7.5f, 12f, 5),
                    new HandleMaterialStats(1.15f, 60),
                    new ExtraMaterialStats(110),
                    new BowMaterialStats(0.2f, 1.2f, 10f)
            );
            setRenderInfo(urielium, 0xe4f5ff);
            urielium.addTrait(GardenOfEden.garden_of_eden, MaterialTypes.HEAD);
            urielium.addTrait(Ignitive.ignitive, MaterialTypes.HEAD);
            urielium.addTrait(Ignitive.ignitive);
            urielium.addTrait(TinkerTraits.superheat);
            ArmorMaterials.addArmorTrait(urielium, BlazingGrace.blazing_grace);

            MetalMaterial.urielium.fluid = Create.plain("urielium", 0xe4f5ff);
            MaterialIntegration urieliumMi = new MaterialIntegration(urielium, MetalMaterial.urielium.fluid, MetalMaterial.urielium.getOreName()).setRepresentativeItem("ingotUrielium");
            TinkerRegistry.integrate(urieliumMi).preInit();
        }
        {
            Material aggrite = new Material("Aggrite", 0x362c38);
            TinkerRegistry.addMaterialStats(aggrite,
                    new CoreMaterialStats(19.7f, 20f),
                    new PlatesMaterialStats(1f, 13f, 3f),
                    new TrimMaterialStats(3.5f),
                    new HeadMaterialStats(900, 8f, 8.25f, 4),
                    new HandleMaterialStats(0.4f, 200),
                    new ExtraMaterialStats(50),
                    new BowMaterialStats(0.65f, 1.1f, 5f)
            );
            setRenderInfo(aggrite, 0x362c38);
            aggrite.addTrait(TinkerTraits.autosmelt, MaterialTypes.HEAD);
            addToToolAll(aggrite, Ignitive.ignitive);
            ArmorMaterials.addArmorTrait(aggrite, ArmorTraits.indomitable);
            ArmorMaterials.addArmorTrait(aggrite, NCArmorTraits.WITHERING, ArmorMaterialType.CORE);

            MetalMaterial.aggrite.fluid = Create.plain("aggrite", 0x362c38);
            MaterialIntegration aggriteMi = new MaterialIntegration(aggrite, MetalMaterial.aggrite.fluid, MetalMaterial.aggrite.getOreName()).setRepresentativeItem("ingotAggrite");
            TinkerRegistry.integrate(aggriteMi).preInit();
        }
        {
            Material cloudIron = new Material("Cloud Iron", 0xbefffd);
            TinkerRegistry.addMaterialStats(cloudIron,
                    new CoreMaterialStats(19f, 21f),
                    new PlatesMaterialStats(2.1f, 13f, 5f),
                    new TrimMaterialStats(14f),
                    new HeadMaterialStats(1750, 9.5f, 10f, 5),
                    new HandleMaterialStats(1.2f, 250),
                    new ExtraMaterialStats(250),
                    new BowMaterialStats(1.2f, 2.4f, 8f)
            );
            setRenderInfo(cloudIron, 0xbefffd);
            cloudIron.addTrait(RampingStatusInflictor.ice_age, MaterialTypes.HEAD);
            addToToolAll(cloudIron, Apocalypse.apocalypse);
            ArmorMaterials.addArmorTrait(cloudIron, ArmorTraits.dense, ArmorMaterialType.CORE);
            addToArmorAll(cloudIron, NCArmorTraits.WITHERING);
            addToArmorAll(cloudIron, NCArmorTraits.POISONOUS);

            MetalMaterial.cloudiron.fluid = Create.plain("cloudiron", 0xbefffd);
            MaterialIntegration cloudIronMi = new MaterialIntegration(cloudIron, MetalMaterial.cloudiron.fluid, MetalMaterial.cloudiron.getOreName()).setRepresentativeItem("ingotCloudiron");
            TinkerRegistry.integrate(cloudIronMi).preInit();
        }
        {
            Material astralium = new Material("Astralium", 0x141a22);
            TinkerRegistry.addMaterialStats(astralium,
                    new CoreMaterialStats(8f, 6f),
                    new PlatesMaterialStats(0.35f, -2f, 0f),
                    new TrimMaterialStats(0.25f),
                    new HeadMaterialStats(1900, 11f, 11f, 5),
                    new HandleMaterialStats(1.1f, 190),
                    new ExtraMaterialStats(195),
                    new BowMaterialStats(1.8f, 2.8f, 7f)
            );
            setRenderInfo(astralium, 0x141a22);
            astralium.addTrait(RampingStatusInflictor.ice_age, MaterialTypes.HEAD);
            astralium.addTrait(DarkTraveler.darktraveler);
            addToArmorAll(astralium, ArmorTraits.featherweight);

            MetalMaterial.astralium.fluid = Create.plain("astralium", 0x141a22);
            MaterialIntegration astraliumMi = new MaterialIntegration(astralium, MetalMaterial.astralium.fluid, MetalMaterial.astralium.getOreName()).setRepresentativeItem("ingotAstralium");
            TinkerRegistry.integrate(astraliumMi).preInit();
        }
        {
            Material bavarium = new Material("Bavarium", 0x65755b);
            TinkerRegistry.addMaterialStats(bavarium,
                    new HeadMaterialStats(1700, 7f, 12.5f, 5),
                    new HandleMaterialStats(0.9f, 110),
                    new ExtraMaterialStats(280),
                    new BowMaterialStats(0.2f, 1.2f, 10f),
                    new ArrowShaftMaterialStats(1.6f, 15)
            );
            setRenderInfo(bavarium, 0x65755b);
            bavarium.addTrait(Detonator.detonator, MaterialTypes.HEAD);
            bavarium.addTrait(Explosive.explosive, MaterialTypes.SHAFT);
            addToToolAll(bavarium, Apocalypse.apocalypse);

            MetalMaterial.bavarium.fluid = Create.plain("bavarium", 0x65755b);
            MaterialIntegration bavariumMi = new MaterialIntegration(bavarium, MetalMaterial.bavarium.fluid, MetalMaterial.bavarium.getOreName()).setRepresentativeItem("ingotBavarium");
            TinkerRegistry.integrate(bavariumMi).preInit();
        }
        {
            Material assarion = new Material("Assarion", 0xfbad24);
            TinkerRegistry.addMaterialStats(assarion,
                    new CoreMaterialStats(22f, 20f),
                    new PlatesMaterialStats(1.1f, 11f, 3f),
                    new TrimMaterialStats(12f),
                    new HeadMaterialStats(1400, 8.8f, 7.7f, 4),
                    new HandleMaterialStats(1.1f, 400),
                    new ExtraMaterialStats(200),
                    new BowMaterialStats(1f, 0.9f, 4f),
                    new ArrowShaftMaterialStats(0.8f, 0)
            );
            setRenderInfo(assarion, 0xfbad24);
            assarion.addTrait(TinkerTraits.autosmelt, MaterialTypes.HEAD);
            addToToolAll(assarion, TinkerTraits.superheat);
            ArmorMaterials.addArmorTrait(assarion, ArmorTraits.combustible);

            MetalMaterial.assarion.fluid = Create.plain("assarion", 0xfbad24);
            MaterialIntegration assarionMi = new MaterialIntegration(assarion, MetalMaterial.assarion.fluid, MetalMaterial.assarion.getOreName()).setRepresentativeItem("ingotAssarion");
            TinkerRegistry.integrate(assarionMi).preInit();
        }
        {
            Material dyrnwynite = new Material("Dyrnwynite", 0xf9f5fd);
            TinkerRegistry.addMaterialStats(dyrnwynite,
                    new CoreMaterialStats(23f, 20f),
                    new PlatesMaterialStats(1f, 12f, 2f),
                    new TrimMaterialStats(4f),
                    new HeadMaterialStats(2200, 7.5f, 9f, 3),
                    new HandleMaterialStats(1f, 60),
                    new ExtraMaterialStats(110),
                    new BowMaterialStats(0.2f, 1.2f, 10f)
            );
            setRenderInfo(dyrnwynite, 0xf9f5fd);
            dyrnwynite.addTrait(Ignitive.ignitive, MaterialTypes.HEAD);
            addToToolAll(dyrnwynite, TinkerTraits.superheat);
            ArmorMaterials.addArmorTrait(dyrnwynite, ArmorTraits.autoforge);
            ArmorMaterials.addArmorTrait(dyrnwynite, ArmorTraits.superhot);

            MetalMaterial.dyrnwynite.fluid = Create.plain("dyrnwynite", 0xf9f5fd);
            MaterialIntegration dyrnwyniteMi = new MaterialIntegration(dyrnwynite, MetalMaterial.dyrnwynite.fluid, MetalMaterial.dyrnwynite.getOreName()).setRepresentativeItem("ingotDyrnwynite");
            TinkerRegistry.integrate(dyrnwyniteMi).preInit();
        }
        {
            Material infernite = new Material("Infernite", 0xff6b00);
            TinkerRegistry.addMaterialStats(infernite,
                    new CoreMaterialStats(9.5f, 16.2f),
                    new PlatesMaterialStats(0.9f, -8f, 3.5f),
                    new TrimMaterialStats(7f),
                    new HeadMaterialStats(800, 7.6f, 6.8f, 3),
                    new HandleMaterialStats(1.1f, 150),
                    new ExtraMaterialStats(180),
                    new BowMaterialStats(0.8f, 1.1f, 6f),
                    new ArrowShaftMaterialStats(0.8f, 0)
            );
            setRenderInfo(infernite, 0xff6b00);
            infernite.addTrait(Ignitive.ignitive, MaterialTypes.HEAD);
            addToToolAll(infernite, TinkerTraits.flammable);
            addToToolAll(infernite, TinkerTraits.jagged);
            ArmorMaterials.addArmorTrait(infernite, ArmorTraits.aridiculous);
            ArmorMaterials.addArmorTrait(infernite, ArmorTraits.infernal);

            MetalMaterial.infernite.fluid = Create.plain("infernite", 0xff6b00);
            MaterialIntegration inferniteMi = new MaterialIntegration(infernite, MetalMaterial.infernite.fluid, MetalMaterial.infernite.getOreName()).setRepresentativeItem("ingotInfernite");
            TinkerRegistry.integrate(inferniteMi).preInit();
        }
        {
            Material tetramentium = new Material("Tetramentium", 0xbacac6);
            TinkerRegistry.addMaterialStats(tetramentium,
                    new CoreMaterialStats(14f, 19f),
                    new PlatesMaterialStats(1.2f, 9.5f, 4f),
                    new TrimMaterialStats(9f),
                    new HeadMaterialStats(444, 8f, 8f, 4),
                    new HandleMaterialStats(1.04f, 444),
                    new ExtraMaterialStats(444),
                    new BowMaterialStats(1.1f, 1.2f, 5.5f)
            );
            tetramentium.addTrait(Tetraboost.tetraboost2, MaterialTypes.HEAD);
            tetramentium.addTrait(Tetraboost.tetraboost);
            ArmorMaterials.addArmorTrait(tetramentium, Tetraboost_Armor.tetraboost2, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(tetramentium, Tetraboost_Armor.tetraboost, ArmorMaterialType.PLATES);
            ArmorMaterials.addArmorTrait(tetramentium, Tetraboost_Armor.tetraboost, ArmorMaterialType.TRIM);
            setRenderInfo(tetramentium, 0xbacac6);

            MetalMaterial.tetramentium.fluid = Create.plain("tetramentium", 0xbacac6);
            MaterialIntegration tetramentiumMi = new MaterialIntegration(tetramentium, MetalMaterial.tetramentium.fluid, MetalMaterial.tetramentium.getOreName()).setRepresentativeItem("ingotTetramentium");
            TinkerRegistry.integrate(tetramentiumMi).preInit();
        }
        {
            Material cyrium = new Material("Cyrium", 0x5767ca);
            TinkerRegistry.addMaterialStats(cyrium,
                    new CoreMaterialStats(15f, 21f),
                    new PlatesMaterialStats(1.3f, 8f, 4f),
                    new TrimMaterialStats(8f),
                    new HeadMaterialStats(1400, 5f, 9f, 4),
                    new HandleMaterialStats(0.4f, 330),
                    new ExtraMaterialStats(330),
                    new BowMaterialStats(1.4f, 1f, 7f)
            );
            cyrium.addTrait(RampingStatusInflictor.ice_age);
            ArmorMaterials.addArmorTrait(cyrium, StatusInflictor_Armor.frozen_aura, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(cyrium, PermanentStatus_Armor.water_breathing);
            setRenderInfo(cyrium, 0x5767ca);

            MetalMaterial.cyrium.fluid = Create.plain("cyrium", 0x5767ca);
            MaterialIntegration cyriumMi = new MaterialIntegration(cyrium, MetalMaterial.cyrium.fluid, MetalMaterial.cyrium.getOreName()).setRepresentativeItem("ingotCyrium");
            TinkerRegistry.integrate(cyriumMi).preInit();
        }
        {
            Material terium = new Material("Terium", 0x65705e);
            TinkerRegistry.addMaterialStats(terium,
                    new CoreMaterialStats(16f, 22f),
                    new PlatesMaterialStats(1.1f, 9f, 5f),
                    new TrimMaterialStats(9f),
                    new HeadMaterialStats(1800, 10f, 7f, 4),
                    new HandleMaterialStats(0.2f, 450),
                    new ExtraMaterialStats(450),
                    new BowMaterialStats(0.4f, 0.6f, 14f)
            );
            terium.addTrait(RampingStatusInflictor.toxic);
            ArmorMaterials.addArmorTrait(terium, Stoneborn.stoneborn);
            setRenderInfo(terium, 0x65705e);

            MetalMaterial.terium.fluid = Create.plain("terium", 0x65705e);
            MaterialIntegration teriumMi = new MaterialIntegration(terium, MetalMaterial.terium.fluid, MetalMaterial.terium.getOreName()).setRepresentativeItem("ingotTerium");
            TinkerRegistry.integrate(teriumMi).preInit();
        }
        {
            Material dupondium = new Material("Dupondium", 0xff0000);
            TinkerRegistry.addMaterialStats(dupondium,
                    new CoreMaterialStats(19f, 21f),
                    new PlatesMaterialStats(1.4f, 8f, 4f),
                    new TrimMaterialStats(11f),
                    new HeadMaterialStats(2000, 9f, 9f, 5),
                    new HandleMaterialStats(1.2f, 450),
                    new ExtraMaterialStats(450),
                    new BowMaterialStats(0.8f, 1.2f, 12f)
            );
            dupondium.addTrait(Hyperspeed.hyperspeed, MaterialTypes.HEAD);
            addToToolAll(dupondium, TinkerTraits.jagged);
            addToToolAll(dupondium, StatusInflictor.antigravity);
            ArmorMaterials.addArmorTrait(dupondium, MasterOfTheUniverse.master_of_the_universe, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(dupondium, SnakeEater.snakeEater);
            setRenderInfo(dupondium, 0xff0000);

            MetalMaterial.dupondium.fluid = Create.plain("dupondium", 0xff0000);
            MaterialIntegration dupondiumMi = new MaterialIntegration(dupondium, MetalMaterial.dupondium.fluid, MetalMaterial.dupondium.getOreName()).setRepresentativeItem("ingotDupondium");
            TinkerRegistry.integrate(dupondiumMi).preInit();
        }
        {
            Material aerium = new Material("Aerium", 0xd6edf0);
            TinkerRegistry.addMaterialStats(aerium,
                    new CoreMaterialStats(13f, 20f),
                    new PlatesMaterialStats(1.4f, 6f, 3f),
                    new TrimMaterialStats(6f),
                    new HeadMaterialStats(1000, 12f, 6f, 5),
                    new HandleMaterialStats(1.2f, 190),
                    new ExtraMaterialStats(190),
                    new BowMaterialStats(1.4f, 1f, 6f)
            );
            aerium.addTrait(StatusInflictor.ascendant);
            ArmorMaterials.addArmorTrait(aerium, FlightMastery.flightmastery, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(aerium, AirMastery.airmastery);
            setRenderInfo(aerium, 0xd6edf0);

            MetalMaterial.aerium.fluid = Create.plain("aerium", 0xd6edf0);
            MaterialIntegration aeriumMi = new MaterialIntegration(aerium, MetalMaterial.aerium.fluid, MetalMaterial.aerium.getOreName()).setRepresentativeItem("ingotAerium");
            TinkerRegistry.integrate(aeriumMi).preInit();
        }
        {
            Material pyrium = new Material("Pyrium", 0xfab149);
            TinkerRegistry.addMaterialStats(pyrium,
                    new CoreMaterialStats(14f, 21f),
                    new PlatesMaterialStats(1.4f, 7f, 4f),
                    new TrimMaterialStats(7f),
                    new HeadMaterialStats(1200, 8f, 11f, 5),
                    new HandleMaterialStats(0.8f, 240),
                    new ExtraMaterialStats(240),
                    new BowMaterialStats(1.2f, 1f, 8f)
            );
            pyrium.addTrait(TinkerTraits.superheat);
            pyrium.addTrait(Ignitive.ignitive);
            ArmorMaterials.addArmorTrait(pyrium, BlazingGrace.blazing_grace);
            setRenderInfo(pyrium, 0xfab149);

            MetalMaterial.pyrium.fluid = Create.plain("pyrium", 0xfab149);
            MaterialIntegration pyriumMi = new MaterialIntegration(pyrium, MetalMaterial.pyrium.fluid, MetalMaterial.pyrium.getOreName()).setRepresentativeItem("ingotPyrium");
            TinkerRegistry.integrate(pyriumMi).preInit();
        }
        {
            Material sestertium = new Material("Sestertium", 0xeaeaea);
            TinkerRegistry.addMaterialStats(sestertium,
                    new CoreMaterialStats(22f, 22f),
                    new PlatesMaterialStats(1.6f, 8f, 6f),
                    new TrimMaterialStats(13f),
                    new HeadMaterialStats(2500, 12f, 12f, 5),
                    new HandleMaterialStats(1.2f, 500),
                    new ExtraMaterialStats(500),
                    new BowMaterialStats(2.2f, 2.5f, 8f)
            );
            setRenderInfo(sestertium, 0xeaeaea);
            sestertium.addTrait(Tetraboost.tetraboost2, MaterialTypes.HEAD);
            addToToolAll(sestertium, TinkerTraits.enderference);
            ArmorMaterials.addArmorTrait(sestertium, Tetraboost_Armor.tetraboost2, ArmorMaterialType.CORE);
            addToArmorAll(sestertium, ArmorTraits.steady);
            addToArmorAll(sestertium, Invariant.invariant);

            MetalMaterial.sestertium.fluid = Create.plain("sestertium", 0xeaeaea);
            MaterialIntegration sestertiumMi = new MaterialIntegration(sestertium, MetalMaterial.sestertium.fluid, MetalMaterial.sestertium.getOreName()).setRepresentativeItem("ingotSestertium");
            TinkerRegistry.integrate(sestertiumMi).preInit();
        }
        {
            Material orichalcum = new Material("Orichalcum", 0x85c0c2);
            TinkerRegistry.addMaterialStats(orichalcum,
                    new CoreMaterialStats(30f, 24f),
                    new PlatesMaterialStats(2f, 20f, 7f),
                    new TrimMaterialStats(17f),
                    new HeadMaterialStats(4000, 50f, 18f, 5),
                    new HandleMaterialStats(2.5f, 1000),
                    new ExtraMaterialStats(1000),
                    new BowMaterialStats(3f, 3f, 12f),
                    new ArrowShaftMaterialStats(1.8f, 25)
            );
            orichalcum.addTrait(Outrageous.outrageous);
            setRenderInfo(orichalcum, 0x85c0c2);

            MetalMaterial.orichalcum.fluid = Create.plain("orichalcum", 0x85c0c2);
            MaterialIntegration orichalcumMi = new MaterialIntegration(orichalcum, MetalMaterial.orichalcum.fluid, MetalMaterial.orichalcum.getOreName()).setRepresentativeItem("ingotOrichalcum");
            TinkerRegistry.integrate(orichalcumMi).preInit();
        }
        {
            Material adamantite = new Material("Adamantite", 0xc283b5);
            TinkerRegistry.addMaterialStats(adamantite,
                    new CoreMaterialStats(25f, 24f),
                    new PlatesMaterialStats(2f, 20f, 6f),
                    new TrimMaterialStats(14f),
                    new HeadMaterialStats(3000, 14f, 14f, 5),
                    new HandleMaterialStats(2f, 777),
                    new ExtraMaterialStats(666),
                    new BowMaterialStats(0.8f, 4f, 14f)
            );
            setRenderInfo(adamantite, 0xc283b5);
            adamantite.addTrait(AbsoluteDominion.absoluteDominion, MaterialTypes.HEAD);
            addToToolAll(adamantite, Global.global);
            ArmorMaterials.addArmorTrait(adamantite, ImmortalCenturion.immortalCenturion1, ArmorMaterialType.TRIM);
            ArmorMaterials.addArmorTrait(adamantite, ImmortalCenturion.immortalCenturion1, ArmorMaterialType.PLATES);
            ArmorMaterials.addArmorTrait(adamantite, ImmortalCenturion.immortalCenturion2, ArmorMaterialType.CORE);
            addToArmorAll(adamantite, ArmorTraits.lightweight);

            MetalMaterial.adamantite.fluid = Create.plain("adamantite", 0xc283b5);
            MaterialIntegration adamantiteMi = new MaterialIntegration(adamantite, MetalMaterial.adamantite.fluid, MetalMaterial.adamantite.getOreName()).setRepresentativeItem("ingotAdamantite");
            TinkerRegistry.integrate(adamantiteMi).preInit();
        }
    }

    public void initAlloys() {
        sendTiCAlloyInfo("tinardite", 2, fluid("tin", 1), fluid("ardite", 1));
        sendTiCAlloyInfo("white_steel", 2, fluid("gravitite", 1), fluid("steel", 3), fluid("lithium", 2));
        sendTiCAlloyInfo("tritanium", 1, fluid("invar", 2), fluid("valkyrie", 1));
        sendTiCAlloyInfo("betweensteel", 144, fluid("syrmorite", 144), fluid("tar", 500), fluid("octine", 144));
        sendTiCAlloyInfo("chaoite", 2, fluid("valkyrie", 1), fluid("betweensteel", 2), fluid("stone", 20));
        sendTiCAlloyInfo("sageslime", 144, fluid("gold", 72), fluid("blueslime", 500), fluid("glass", 250));
        sendTiCAlloyInfo("alubronze", 2, fluid("alubrass", 1), fluid("bronze", 1));
        sendTiCAlloyInfo("caelite", 2, fluid("magnesium", 3), fluid("signalum", 4));
        sendTiCAlloyInfo("atmium", 1, fluid("constantan", 2), fluid("uranium", 3));
        sendTiCAlloyInfo("sonium", 288, fluid("lumium", 288), fluid("prismarine", 432), fluid("electrum", 216));
        sendTiCAlloyInfo("faerite", 36, fluid("chaoite", 72), fluid("sand", 500));
        sendTiCAlloyInfo("lordslime", 1, fluid("sageslime", 1), fluid("knightslime", 1));
        sendTiCAlloyInfo("oil_steel", 144, fluid("iron", 144), fluid("refined_fuel", 200));
        sendTiCAlloyInfo("peractium", 144, fluid("experience", 750), fluid("enderium", 144), fluid("blood", 500));
        sendTiCAlloyInfo("caeputescite", 2, fluid("caelite", 2), fluid("atmium", 1));
        sendTiCAlloyInfo("superlumium", 36, fluid("lumium", 18), fluid("starmetal", 36), fluid("mana", 100));
//        sendTiCAlloyInfo("starsteel", 1, fluid("starmetal", 1), fluid("astralsorcery.liquidstarlight", 20));
        sendTiCAlloyInfo("yellow_cobalt", 72, fluid("mana", 100), fluid("pelagium", 36), fluid("cobalt", 72));
        sendTiCAlloyInfo("unseelium", 144, fluid("faerite", 144), fluid("netherstar", 400));
        sendTiCAlloyInfo("aureclase", 24, fluid("netherstar", 100), fluid("manganese", 24), fluid("diamond", 333));
        sendTiCAlloyInfo("cobalt_thorium_g", 2, fluid("cobalt", 2), fluid("lordslime", 1), fluid("thorium", 2));

        sendTiCAlloyInfo("florium", 1, fluid("oil_steel", 1), fluid("mirion", 1));
        sendTiCAlloyInfo("gourmium", 1, fluid("milk_chocolate", 1), fluid("pigiron", 3), fluid("iridium", 2));
        sendTiCAlloyInfo("alcite", 72, fluid("hot_spring_water", 1000), fluid("peractium", 18), fluid("caeputescite", 36));
        sendTiCAlloyInfo("urielium", 7, fluid("yellow_cobalt", 2), fluid("knightmetal", 3), fluid("thermoconducting", 4));
        sendTiCAlloyInfo("aggrite", 4, fluid("thermoconducting", 4), fluid("cobalt_thorium_g", 1), fluid("florium", 3));

        sendTiCAlloyInfo("cloudiron", 3, fluid("iridium", 1), fluid("cobalt_thorium_g", 2), fluid("florium", 2));
        sendTiCAlloyInfo("escalite", 18, fluid("gourmium", 18), fluid("hot_spring_water", 250));
        sendTiCAlloyInfo("astralium", 4, fluid("alcite", 3), fluid("superlumium", 3), fluid("starsteel", 4));
        sendTiCAlloyInfo("bavarium", 1, fluid("atmium", 1), fluid("urielium", 1));

        sendTiCAlloyInfo("assarion", 144, fluid("endacid", 1000), fluid("fierymetal", 144), fluid("beryllium", 72));
        sendTiCAlloyInfo("dyrnwynite", 27, fluid("blood", 200), fluid("pyrotheum", 18), fluid("aggrite", 36));
        sendTiCAlloyInfo("infernite", 36, fluid("pyrotheum", 100), fluid("white_steel", 36), fluid("ardite", 72));
        sendTiCAlloyInfo("tetramentium", 1, fluid("cryotheum", 10), fluid("petrotheum", 10), fluid("aerotheum", 10), fluid("pyrotheum", 10));
        sendTiCAlloyInfo("daemotium", 36, fluid("aggrite", 36), fluid("poison", 250), fluid("chaoite", 72));
        sendTiCAlloyInfo("cyrium", 72, fluid("cryotheum", 500), fluid("daemotium", 144));
        sendTiCAlloyInfo("pyrium", 72, fluid("pyrotheum", 500), fluid("daemotium", 144));
        sendTiCAlloyInfo("terium", 72, fluid("petrotheum", 500), fluid("daemotium", 144));
        sendTiCAlloyInfo("aerium", 72, fluid("aerotheum", 500), fluid("daemotium", 144));
        sendTiCAlloyInfo("dupondium", 2, fluid("assarion", 3), fluid("infernite", 1), fluid("daemotium", 1));
        sendTiCAlloyInfo("sestertium", 1, fluid("tetramentium", 12), fluid("yellow_cobalt", 5), fluid("dupondium", 7));
        sendTiCAlloyInfo("orichalcum", 2, fluid("sestertium", 3), fluid("cloudiron", 6), fluid("aureclase", 9));
        sendTiCAlloyInfo("adamantite", 1, fluid("astralium", 2), fluid("orichalcum", 3));
    }

    public void initLate() {
        TinkerRegistry.registerMelting(TinkerCommons.matSlimeBallBlue, TinkerFluids.blueslime, 250);
        TinkerRegistry.registerBasinCasting(MetalMaterial.pelagium.blockItemStack, new ItemStack(Blocks.SPONGE), MetalMaterial.tinardite.fluid, 1296);
    }

    private void addAlloy(Fluid result, int resultQuantity, Fluid in1, int in1amt,
                          Fluid in2, int in2amt) {
        TinkerRegistry.registerAlloy(new FluidStack(result, resultQuantity), new FluidStack(in1, in1amt),
                new FluidStack(in2, in2amt));
    }

    private void addAlloy(Fluid result, int resultQuantity, Fluid in1, int in1amt,
                          Fluid in2, int in2amt, Fluid in3, int in3amt) {
        TinkerRegistry.registerAlloy(new FluidStack(result, resultQuantity), new FluidStack(in1, in1amt),
                new FluidStack(in2, in2amt), new FluidStack(in3, in3amt));
    }

    private void addAlloy(Fluid result, int resultQuantity, Fluid in1, int in1amt,
                          Fluid in2, int in2amt, Fluid in3, int in3amt, Fluid in4, int in4amt) {
        TinkerRegistry.registerAlloy(new FluidStack(result, resultQuantity), new FluidStack(in1, in1amt),
                new FluidStack(in2, in2amt), new FluidStack(in3, in3amt), new FluidStack(in4, in4amt));
    }

    private void addAlloy(Fluid result, int resultQuantity, Fluid in1, int in1amt,
                          Fluid in2, int in2amt, Fluid in3, int in3amt, Fluid in4, int in4amt,
                          Fluid in5, int in5amt) {
        TinkerRegistry.registerAlloy(new FluidStack(result, resultQuantity), new FluidStack(in1, in1amt),
                new FluidStack(in2, in2amt), new FluidStack(in3, in3amt), new FluidStack(in4, in4amt),
                new FluidStack(in5, in5amt));
    }

    private void addAlloy(Fluid result, int resultQuantity, Fluid in1, int in1amt,
                          Fluid in2, int in2amt, Fluid in3, int in3amt, Fluid in4, int in4amt,
                          Fluid in5, int in5amt, Fluid in6, int in6amt) {
        TinkerRegistry.registerAlloy(new FluidStack(result, resultQuantity), new FluidStack(in1, in1amt),
                new FluidStack(in2, in2amt), new FluidStack(in3, in3amt), new FluidStack(in4, in4amt),
                new FluidStack(in5, in5amt), new FluidStack(in6, in6amt));
    }

    public static void sendTiCAlloyInfo(String alloyName, int alloyAmount, Pair<String, Integer>... components) {
        NBTTagList tagList = new NBTTagList();
        NBTTagCompound fluid = new NBTTagCompound();
        fluid.setString("FluidName", alloyName);
        fluid.setInteger("Amount", alloyAmount);
        tagList.appendTag(fluid);

        for (Pair<String, Integer> component : components) {
            fluid = new NBTTagCompound();
            fluid.setString("FluidName", component.getLeft());
            fluid.setInteger("Amount", component.getRight());
            tagList.appendTag(fluid);
        }

        NBTTagCompound message = new NBTTagCompound();
        message.setTag("alloy", tagList);
        FMLInterModComms.sendMessage("tconstruct", "alloy", message);
    }

    private static Pair<String, Integer> fluid(String fluidName, int amount) {
        return Pair.of(fluidName, amount);
    }

    private void addToArmorAll(Material mat, AbstractArmorTrait trait) {
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.CORE);
    }

    private void addToToolAll(Material white_steel, ITrait trait) {

        white_steel.addTrait(trait, MaterialTypes.HEAD);
        white_steel.addTrait(trait, MaterialTypes.HANDLE);
        white_steel.addTrait(trait, MaterialTypes.EXTRA);
        white_steel.addTrait(trait, MaterialTypes.BOW);
        white_steel.addTrait(trait, MaterialTypes.BOWSTRING);
        white_steel.addTrait(trait, MaterialTypes.FLETCHING);
        white_steel.addTrait(trait, MaterialTypes.PROJECTILE);
        white_steel.addTrait(trait, MaterialTypes.SHAFT);
    }
}
