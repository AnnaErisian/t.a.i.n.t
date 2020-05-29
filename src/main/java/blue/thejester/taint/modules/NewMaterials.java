package blue.thejester.taint.modules;

import blue.thejester.taint.helper.fluid.Create;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.tools.WandCoreMaterialStats;
import blue.thejester.taint.traits.*;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.traits.armor.DunansTransport;
import nc.integration.tconstruct.conarm.trait.NCArmorTraits;
import nc.integration.tconstruct.trait.NCTraits;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.commons.lang3.tuple.Pair;
import shnupbups.tinkersaether.traits.Antigrav;
import shnupbups.tinkersaether.traits.Launching;
import shnupbups.tinkersaether.traits.Zany;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.TinkerTraits;

public class NewMaterials implements IModule {

    @Override
    public void preInit() {
        initMaterials();

//        MetalMaterial.tinardite.fluid = Create.plain("tinardite", 0x7a6463);
//        MetalMaterial.daemotium.fluid = Create.plain("daemotium", 0x3e333f);


        initAlloys();
    }

    private void initMaterials() {
        createTier2Materials();
        createTier3Materials();
        createTier4Materials();
        createTier5Materials();
        createTier6Materials();
        createTier7Materials();
        createTier8Materials();

    }

    private void createTier8Materials() {
        {
            int color = 0xa79142;
            String cn = "Atercaeum";
            MetalMaterial mm = MetalMaterial.atercaeum;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(22.8f, 20.6f),
                    new PlatesMaterialStats(1.02f, 12f, 3.4f),
                    new TrimMaterialStats(17f),
                    new HeadMaterialStats(1780, 12f, 6.8f, 10),
                    new HandleMaterialStats(1.1f, 200),
                    new ExtraMaterialStats(500),
                    new BowMaterialStats(0.75f, 1.40f, 5.2f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.momentum, MaterialTypes.HEAD);
            mat.addTrait(TinkerTraits.lightweight);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.featherweight, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.lightweight);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x5798a7;
            String cn = "Orichalcum";
            MetalMaterial mm = MetalMaterial.orichalcum;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2800, 10.6f, 11.2f, 10),
                    new HandleMaterialStats(1.09f, 280),
                    new ExtraMaterialStats(520)
            );
            setRenderInfo(mat, color);
            mat.addTrait(AbsoluteDominion.absoluteDominion, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.coldblooded);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xa04b15;
            String cn = "Urielium";
            MetalMaterial mm = MetalMaterial.urielium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(22.8f, 20.2f),
                    new PlatesMaterialStats(1.02f, 12f, 3.4f),
                    new TrimMaterialStats(17f),
                    new HeadMaterialStats(2250, 9.11f, 10.8f, 10),
                    new HandleMaterialStats(1.15f, 320),
                    new ExtraMaterialStats(411),
                    new BowMaterialStats(0.7f, 1.55f, 9.11f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Ignitive.ignitive);
            mat.addTrait(TinkerTraits.superheat);
            ArmorMaterials.addArmorTrait(mat, BlazingGrace.blazing_grace, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.superhot);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xee1111;
            String cn = "Dupondium";
            MetalMaterial mm = MetalMaterial.dupondium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(22.8f, 20.2f),
                    new PlatesMaterialStats(1.2f, 12f, 3.4f),
                    new TrimMaterialStats(18f)
            );
            setRenderInfo(mat, color);
            ArmorMaterials.addArmorTrait(mat, ImmortalCenturion.immortalCenturion1, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.ambitious);
            addToArmorAll(mat, ArmorTraits.steady);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x2d00b5;
            String cn = "Starsteel";
            MetalMaterial mm = MetalMaterial.starsteel;
            String ln = mm.name();
            Material mat = new Material(cn, color+0x201000);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(18.2f, 20.8f),
                    new PlatesMaterialStats(1.22f, 15f, 3.2f),
                    new TrimMaterialStats(16f)
            );
            setRenderInfo(mat, color);
            ArmorMaterials.addArmorTrait(mat, Superstar.superstar, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.featherweight);
            addToArmorAll(mat, Mana.mana);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xe2d9cf;
            String cn = "Solairite";
            MetalMaterial mm = MetalMaterial.solairite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2585, 11.5f, 10.6f, 10),
                    new HandleMaterialStats(1.15f, 320),
                    new ExtraMaterialStats(411),
                    new BowMaterialStats(0.7f, 1.55f, 9.11f),
                    new WandCoreMaterialStats(1,1,1,1,1,1, 1)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Ignitive.ignitive, MaterialTypes.HEAD);
            addToToolAll(mat, Luminous.luminous);
            addToToolAll(mat, Mana.mana);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x7e9096;
            String cn = "Ptemnium";
            MetalMaterial mm = MetalMaterial.ptemnium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2333, 13f, 8.8f, 10),
                    new HandleMaterialStats(1.05f, 430),
                    new ExtraMaterialStats(560),
                    new BowMaterialStats(0.82f, 1.45f, 5.6f),
                    new WandCoreMaterialStats(1,1,1,1,1,1, 1)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.momentum, MaterialTypes.HEAD);
            addToToolAll(mat, Silky.silky);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x93539c;
            String cn = "Adamantite";
            MetalMaterial mm = MetalMaterial.adamantite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(23.8f, 21.4f),
                    new PlatesMaterialStats(1.23f, 19f, 5f),
                    new TrimMaterialStats(16f),
                    new WandCoreMaterialStats(1,1,1,1,1,1, 1)
            );
            setRenderInfo(mat, color);
            ArmorMaterials.addArmorTrait(mat, MasterOfTheUniverse.master_of_the_universe, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.ambitious);
            addToArmorAll(mat, ArmorTraits.heavy);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xdee7ea;
            String cn = "Cibarite";
            MetalMaterial mm = MetalMaterial.cibarite;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xd0784d;
            String cn = "Inurose";
            MetalMaterial mm = MetalMaterial.inurose;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0x251c26;
            String cn = "Oscurum";
            MetalMaterial mm = MetalMaterial.oscurum;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier7Materials() {
        {
            int color = 0xaf61fe;
            String cn = "Neulite";
            MetalMaterial mm = MetalMaterial.neulite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2525, 10f, 10f, 9),
                    new HandleMaterialStats(1.2f, 50),
                    new ExtraMaterialStats(100),
                    new BowMaterialStats(1.55f, 1.75f, 6.5f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(RampingStatusInflictor.nuclear_winter, MaterialTypes.HEAD);
            mat.addTrait(NCTraits.WITHERING);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xfbf6cb;
            String cn = "Superlumium";
            MetalMaterial mm = MetalMaterial.superlumium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(1200, 13f, 9f, 5),
                    new HandleMaterialStats(1.0f, 150),
                    new ExtraMaterialStats(350),
                    new BowMaterialStats(1.78f, 1.60f, 6.5f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Hyperspeed.hyperspeed, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.enderference);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x474a3f;
            String cn = "Terium";
            MetalMaterial mm = MetalMaterial.terium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(25f, 20.8f),
                    new PlatesMaterialStats(1.4f, 13f, 4f),
                    new TrimMaterialStats(12f),
                    new HeadMaterialStats(1780, 8.6f, 8.4f, 9),
                    new HandleMaterialStats(1.8f, -1000),
                    new ExtraMaterialStats(350),
                    new BowMaterialStats(0.1f, 0.60f, 13f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.stonebound);
            mat.addTrait(TinkerTraits.petramor);
            ArmorMaterials.addArmorTrait(mat, Stoneborn.stoneborn, ArmorMaterialType.CORE);
            addToArmorAll(mat, NCArmorTraits.POISONOUS);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xa0bba7;
            String cn = "Soldrite";
            MetalMaterial mm = MetalMaterial.soldrite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(18f, 21.0f),
                    new PlatesMaterialStats(1.2f, 11f, 4f),
                    new TrimMaterialStats(8f)
            );
            setRenderInfo(mat, color);
            ArmorMaterials.addArmorTrait(mat, SnakeEater.snakeEater, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.calcic);
            addToArmorAll(mat, ArmorTraits.ambitious);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xa9b46d;
            String cn = "Accelerase";
            MetalMaterial mm = MetalMaterial.accelerase;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(16f, 19.6f),
                    new PlatesMaterialStats(1.18f, 15f, 3.5f),
                    new TrimMaterialStats(9f),
                    new HeadMaterialStats(780, 14f, 9.4f, 9),
                    new HandleMaterialStats(0.99f, 99),
                    new ExtraMaterialStats(99),
                    new BowMaterialStats(10f, 1.60f, -10f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(BlackHole.blackhole);
            ArmorMaterials.addArmorTrait(mat, DunansTransport.dunanstransport);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xe9b4e6;
            String cn = "Antenium";
            MetalMaterial mm = MetalMaterial.antenium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2250, 9.8f, 9.8f, 9),
                    new HandleMaterialStats(1.09f, 280),
                    new ExtraMaterialStats(390),
                    new BowMaterialStats(1.2f, 1.60f, 3.3f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.autosmelt, MaterialTypes.HEAD);
            addToToolAll(mat, Ignitive.ignitive);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier6Materials() {
        {
            int color = 0x7b1687;
            String cn = "Unseelium";
            MetalMaterial mm = MetalMaterial.unseelium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(17f, 19.4f),
                    new PlatesMaterialStats(1.3f, 16f, 4f),
                    new TrimMaterialStats(8f),
                    new HeadMaterialStats(2100, 8.2f, 11f, 7),
                    new HandleMaterialStats(1.15f, 190),
                    new ExtraMaterialStats(180),
                    new BowMaterialStats(1.9f, 2.1f, 6f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(FaeFlight.faeFlight, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.alien);
            ArmorMaterials.addArmorTrait(mat, FlightMastery.flightmastery, ArmorMaterialType.CORE);
            addToToolAll(mat, Mana.mana);
            addToArmorAll(mat, Mana.mana);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xbefffd;
            String cn = "Tempestite";
            MetalMaterial mm = MetalMaterial.tempestite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(17f, 20.4f),
                    new PlatesMaterialStats(1.25f, 14f, 3f),
                    new TrimMaterialStats(19f),
                    new HeadMaterialStats(1780, 8.8f, 10.6f, 5),
                    new HandleMaterialStats(1.05f, 200),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(0.3f, 1.0f, 12f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Annihilator.annihilator, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.aquadynamic);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.voltaic);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.aquaspeed);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xbefffd;
            String cn = "Rallium";
            MetalMaterial mm = MetalMaterial.rallium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(17f, 19.8f),
                    new PlatesMaterialStats(1.45f, 14f, 4f),
                    new TrimMaterialStats(22f)
            );
            setRenderInfo(mat, color);
            ArmorMaterials.addArmorTrait(mat, Beezerker.beezerker);
            addToArmorAll(mat, ArmorTraits.lightweight);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xb7b4db;
            String cn = "Fierkite";
            MetalMaterial mm = MetalMaterial.fierkite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2440, 10.6f, 9.6f, 8),
                    new HandleMaterialStats(1.15f, 200),
                    new ExtraMaterialStats(280)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Propelled.propelled, MaterialTypes.HEAD);
            addToToolAll(mat, Elemental.elemental);
            mat.addTrait(TinkerTraits.lightweight);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x553e82;
            String cn = "Orinase";
            MetalMaterial mm = MetalMaterial.orinase;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(2500, 9.6f, 10.8f, 8),
                    new HandleMaterialStats(1.25f, 380),
                    new ExtraMaterialStats(425)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Luminous.luminous, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.superheat);
            addToToolAll(mat, TinkerTraits.sharp);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0x1dd7d7;
            String cn = "Caersin";
            MetalMaterial mm = MetalMaterial.caersin;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier5Materials() {
        {
            int color = 0xf2b67b;
            String cn = "Ascalite";
            MetalMaterial mm = MetalMaterial.ascalite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(1777, 7.2f, 8.2f, 8),
                    new HandleMaterialStats(0.95f, 150),
                    new ExtraMaterialStats(210),
                    new CoreMaterialStats(14f, 20.2f),
                    new PlatesMaterialStats(1.05f, 7f, 1.6f),
                    new TrimMaterialStats(12.4f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Antigrav.antigrav, MaterialTypes.HEAD);
            addToToolAll(mat, PraiseTheSun.praiseit);
            ArmorMaterials.addArmorTrait(mat, Floaty.floaty);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xd6edf0;
            String cn = "Aerium";
            MetalMaterial mm = MetalMaterial.aerium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(21f, 17.4f),
                    new PlatesMaterialStats(1.4f, 6f, 2f),
                    new TrimMaterialStats(6f),
                    new HeadMaterialStats(1000, 11f, 8.6f, 8),
                    new HandleMaterialStats(1.2f, 190),
                    new ExtraMaterialStats(190),
                    new BowMaterialStats(2.4f, 2.2f, 6f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Launching.launching, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.lightweight);
            ArmorMaterials.addArmorTrait(mat, AirMastery.airmastery, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.featherweight);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x62390f;
            String cn = "Cannium";
            MetalMaterial mm = MetalMaterial.cannium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(17f, 19.4f),
                    new PlatesMaterialStats(1.2f, 14f, 2f),
                    new TrimMaterialStats(11f),
                    new HeadMaterialStats(2100, 9.4f, 8.2f, 5),
                    new HandleMaterialStats(1f, 390),
                    new ExtraMaterialStats(500)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Butchery.butchery, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.established);
            //TODO test Soul Catcher
            ArmorMaterials.addArmorTrait(mat, SoulCatcher.soulcatcher, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.invigorating);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0xe0e6e9;
            String cn = "Lunite";
            MetalMaterial mm = MetalMaterial.lunite;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(1666, 8.2f, 7.2f, 8),
                    new HandleMaterialStats(1.25f, 50),
                    new ExtraMaterialStats(160),
                    new CoreMaterialStats(17f, 19.8f),
                    new PlatesMaterialStats(1.15f, 9f, 2f),
                    new TrimMaterialStats(1.2f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.insatiable, MaterialTypes.HEAD);
            addToToolAll(mat, MidnightOil.midnightoil);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.shielding);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.blessed);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
        {
            int color = 0x363e1c;
            String cn = "Betweensteel";
            MetalMaterial mm = MetalMaterial.betweensteel;
            String ln = mm.name();
            Material mat = new Material(cn, color+0x202010);
            TinkerRegistry.addMaterialStats(mat,
                    new CoreMaterialStats(22f, 20.6f),
                    new PlatesMaterialStats(0.9f, 4f, 3f),
                    new TrimMaterialStats(12f),
                    new HeadMaterialStats(1900, 9f, 9f, 8),
                    new HandleMaterialStats(1.1f, 190),
                    new ExtraMaterialStats(350)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Apocalypse.apocalypse, MaterialTypes.HEAD);
            addToArmorAll(mat, Midden_Armor.midden_armor);
            addToToolAll(mat, Midden.midden);
            addToToolAll(mat, Ignitive.ignitive);
            addToArmorAll(mat, ArmorTraits.aquaspeed);
            addToToolAll(mat, ArmorTraits.steady);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xb8af1b;
            String cn = "Adipatum";
            MetalMaterial mm = MetalMaterial.adipatum;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier4Materials() {
        {
            int color = 0xe5913d;
            String cn = "Ardorum";
            MetalMaterial mm = MetalMaterial.ardorum;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(204, 6f, 4f, 6),
                    new HandleMaterialStats(0.85f, 70),
                    new ExtraMaterialStats(60),
                    new BowMaterialStats(0.5f, 1.5f, 5f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.autosmelt);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0x325c48;
            String cn = "Peractium";
            MetalMaterial mm = MetalMaterial.peractium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(820, 4.2f, 5.8f, 7),
                    new HandleMaterialStats(0.85f, 70),
                    new ExtraMaterialStats(60),
                    new BowMaterialStats(0.5f, 1.5f, 4.5f),
                    new CoreMaterialStats(13.2f, 20.4f),
                    new PlatesMaterialStats(0.9f, 2f, 1f),
                    new TrimMaterialStats(4.6f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(SoulDrain.soulDrain);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.spiny);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.vengeful);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xeaeaea;
            String cn = "Sestertium";
            MetalMaterial mm = MetalMaterial.sestertium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(590, 4.2f, 4.1f, 6),
                    new HandleMaterialStats(0.85f, 70),
                    new ExtraMaterialStats(50),
                    new BowMaterialStats(0.9f, 1.2f, 4f),
                    new CoreMaterialStats(13.2f, 15f),
                    new PlatesMaterialStats(0.8f, -1f, 1f),
                    new TrimMaterialStats(2.8f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Tetraboost.tetraboost);
            mat.addTrait(Tetraboost.tetraboost2, MaterialTypes.HEAD);
            ArmorMaterials.addArmorTrait(mat, Tetraboost_Armor.tetraboost);
            ArmorMaterials.addArmorTrait(mat, Tetraboost_Armor.tetraboost2, ArmorMaterialType.CORE);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xff6b00;
            String cn = "Infernium";
            MetalMaterial mm = MetalMaterial.infernium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(1600, 7.42f, 9.15f, 7),
                    new HandleMaterialStats(0.7f, 370),
                    new ExtraMaterialStats(100),
                    new BowMaterialStats(0.6f, 1.3f, 5f),
                    new CoreMaterialStats(21f, 20f),
                    new PlatesMaterialStats(1f, 14f, 3f),
                    new TrimMaterialStats(5.8f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(TinkerTraits.coldblooded, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.holy);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.shielding, ArmorMaterialType.CORE);
            addToArmorAll(mat, ArmorTraits.combustible);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0x226b34;
            String cn = "Littium";
            MetalMaterial mm = MetalMaterial.littium;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(512, 15.5f, 4.6f, 6),
                    new HandleMaterialStats(0.80f, 90),
                    new ExtraMaterialStats(20)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Smashing.smashing, MaterialTypes.HEAD);
            addToToolAll(mat, Zany.zany);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0xd78e4c;
            String cn = "Lordslime";
            MetalMaterial mm = MetalMaterial.lordslime;
            String ln = mm.name();
            Material mat = new Material(cn, color);
            TinkerRegistry.addMaterialStats(mat,
                    new HeadMaterialStats(1350, 6.2f, 6.2f, 6),
                    new HandleMaterialStats(1.15f, 90),
                    new ExtraMaterialStats(120),
                    new CoreMaterialStats(18.4f, 18.6f),
                    new PlatesMaterialStats(1.15f, 13.4f, 2f),
                    new TrimMaterialStats(11.8f)
            );
            setRenderInfo(mat, color);
            mat.addTrait(Decimator.decimator, MaterialTypes.HEAD);
            addToToolAll(mat, TinkerTraits.unnatural);
            addToToolAll(mat, TinkerTraits.crumbling);
            ArmorMaterials.addArmorTrait(mat, Rocketeer.rocketeer, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.invigorating);
            ArmorMaterials.addArmorTrait(mat, ArmorTraits.dramatic);

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }

        {
            int color = 0x536681;
            String cn = "Termium";
            MetalMaterial mm = MetalMaterial.termium;
            String ln = mm.name();
            Material mat = null;

            mm.fluid = Create.plain(ln, color);
            MaterialIntegration integration = new MaterialIntegration(mat, mm.fluid, mm.getOreName());
            integration.setRepresentativeItem("ingot"+cn);
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier3Materials() {
        {
            int color = 0x554444;
            Material blackRosite = new Material("Black Rosite", color);
            TinkerRegistry.addMaterialStats(blackRosite,
                    new HeadMaterialStats(900, 6f, 5f, 5),
                    new HandleMaterialStats(1.03f, 120),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(1.4f, 1.7f, 3f)
            );
            setRenderInfo(blackRosite, color);
            blackRosite.addTrait(AphroditeBlessing.aphroditeBlessing, MaterialTypes.HEAD);
            addToToolAll(blackRosite, FlowerPower.flowerpower);

            MetalMaterial.black_rosite.fluid = Create.plain("black_rosite", color);
            MaterialIntegration blackRositeIntegration = new MaterialIntegration(blackRosite, MetalMaterial.black_rosite.fluid, MetalMaterial.black_rosite.getOreName()).setRepresentativeItem("ingotBlackRosite");
            TinkerRegistry.integrate(blackRositeIntegration).preInit();
        }

        {
            int color = 0xc9db99;
            Material piridium = new Material("Piridium", color);
            TinkerRegistry.addMaterialStats(piridium,
                    new CoreMaterialStats(17f, 16f),
                    new PlatesMaterialStats(1.1f, 10f, 2f),
                    new TrimMaterialStats(4.2f)
            );
            setRenderInfo(piridium, color);
            ArmorMaterials.addArmorTrait(piridium, PermanentStatus_Armor.jump_boost, ArmorMaterialType.CORE);
            addToArmorAll(piridium, ArmorTraits.featherweight);

            MetalMaterial.piridium.fluid = Create.plain("piridium", color);
            MaterialIntegration piridiumIntegration = new MaterialIntegration(piridium, MetalMaterial.piridium.fluid, MetalMaterial.piridium.getOreName()).setRepresentativeItem("ingotPiridium");
            TinkerRegistry.integrate(piridiumIntegration).preInit();
        }

        {
            int color = 0xc9db99;
            Material luginite = new Material("Luginite", color);
            TinkerRegistry.addMaterialStats(luginite,
                    new HeadMaterialStats(1300, 6.8f, 5.2f, 5),
                    new HandleMaterialStats(0.7f, 70),
                    new ExtraMaterialStats(43),
                    new BowMaterialStats(0.95f, 1.2f, 2f),
                    new CoreMaterialStats(17f, 18.2f),
                    new PlatesMaterialStats(1.0f, 20f, 2f),
                    new TrimMaterialStats(4.2f)
            );
            setRenderInfo(luginite, color);
            addToToolAll(luginite, TinkerTraits.aquadynamic);
            ArmorMaterials.addArmorTrait(luginite, PermanentStatus_Armor.water_breathing, ArmorMaterialType.CORE);
            addToArmorAll(luginite, ArmorTraits.aquaspeed);

            MetalMaterial.luginite.fluid = Create.plain("luginite", color);
            MaterialIntegration integration = new MaterialIntegration(luginite, MetalMaterial.luginite.fluid, MetalMaterial.luginite.getOreName()).setRepresentativeItem("ingotLuginite");
            TinkerRegistry.integrate(integration).preInit();
        }
    }

    private void createTier2Materials() {
        {
            Material escalite = new Material("Escalite", 0x25a8c0);
            TinkerRegistry.addMaterialStats(escalite,
                    new HeadMaterialStats(9, 8f, 2.5f, 2),
                    new HandleMaterialStats(1.1f, -40),
                    new ExtraMaterialStats(-20),
                    new CoreMaterialStats(2f, 14f),
                    new PlatesMaterialStats(0.45f, 1f, 0f),
                    new TrimMaterialStats(0.5f)
            );
            setRenderInfo(escalite, 0x25a8c0);
            escalite.addTrait(Delicious.delicious, MaterialTypes.HEAD);
            addToToolAll(escalite, TinkerTraits.tasty);
            ArmorMaterials.addArmorTrait(escalite, Delicious_Armor.delicious, ArmorMaterialType.CORE);
            ArmorMaterials.addArmorTrait(escalite, ArmorTraits.tasty);

            MetalMaterial.escalite.fluid = Create.plain("escalite", 0x25a8c0);
            MaterialIntegration escaliteMi = new MaterialIntegration(escalite, MetalMaterial.escalite.fluid, MetalMaterial.escalite.getOreName()).setRepresentativeItem("ingotEscalite");
            TinkerRegistry.integrate(escaliteMi).preInit();
        }
        {
            Material slipsteel = new Material("Slipsteel", 0xaaa38f);
            TinkerRegistry.addMaterialStats(slipsteel,
                    new HeadMaterialStats(480, 6f, 6f, 3),
                    new HandleMaterialStats(0.88f, 120),
                    new ExtraMaterialStats(35),
                    new BowMaterialStats(0.3f, 0.4f, 6f)
            );
            setRenderInfo(slipsteel, 0xaaa38f);
            slipsteel.addTrait(Luminous.luminous, MaterialTypes.HEAD);
            addToToolAll(slipsteel, Slippery.slippery);

            MetalMaterial.slipsteel.fluid = Create.plain("slipsteel", 0xaaa38f);
            MaterialIntegration slipsteelIntegration = new MaterialIntegration(slipsteel, MetalMaterial.slipsteel.fluid, MetalMaterial.slipsteel.getOreName()).setRepresentativeItem("ingotSlipsteel");
            TinkerRegistry.integrate(slipsteelIntegration).preInit();
        }
        {
            Material faerite = new Material("Faerite", 0xff00f2);
            TinkerRegistry.addMaterialStats(faerite,
                    new CoreMaterialStats(8f, 16f),
                    new PlatesMaterialStats(0.9f, 7f, 0.5f),
                    new TrimMaterialStats(3.3f),
                    new HeadMaterialStats(380, 5f, 3f, 2),
                    new HandleMaterialStats(1.05f, 180),
                    new ExtraMaterialStats(30),
                    new BowMaterialStats(1.3f, 1.8f, 4f)
            );
            setRenderInfo(faerite, 0xff00f2);
            faerite.addTrait(FaeFlight.faeFlight, MaterialTypes.HEAD);
            addToToolAll(faerite, TinkerTraits.alien);
            addToToolAll(faerite, Mana.mana);
            ArmorMaterials.addArmorTrait(faerite, ArmorTraits.alien);
            ArmorMaterials.addArmorTrait(faerite, Mana.mana);

            MetalMaterial.faerite.fluid = Create.plain("faerite", 0xff00f2);
            MaterialIntegration faeriteMi = new MaterialIntegration(faerite, MetalMaterial.faerite.fluid, MetalMaterial.faerite.getOreName()).setRepresentativeItem("ingotFaerite");
            TinkerRegistry.integrate(faeriteMi).preInit();
        }
    }

    public void initAlloys() {
        initTier2Alloys();
        initTier3Alloys();
        initTier4Alloys();
        initTier5Alloys();
        initTier6Alloys();
        initTier7Alloys();
        initTier8Alloys();

    }

    private void initTier8Alloys() {
        sendTiCAlloyInfo("dupondium", 1, fluid("soldrite", 3), fluid("atercaeum", 4), fluid("netherstar", 25));
        sendTiCAlloyInfo("starsteel", 1, fluid("unseelium", 3), fluid("cibarite", 2));
        sendTiCAlloyInfo("adamantite", 2, fluid("dupondium", 5), fluid("atercaeum", 7), fluid("starsteel", 5), fluid("ptemnium", 6));
        sendTiCAlloyInfo("ptemnium", 2, fluid("inurose", 5), fluid("accelerase", 4), fluid("urielium", 3));
        sendTiCAlloyInfo("orichalcum", 1, fluid("inurose", 5), fluid("cibarite", 5), fluid("oscurum", 5));
        sendTiCAlloyInfo("solairite", 3, fluid("oscurum", 2), fluid("antenium", 6));
        sendTiCAlloyInfo("urielium", 2, fluid("solairite", 4), fluid("thermoconducting", 6));
    }

    private void initTier7Alloys() {
        sendTiCAlloyInfo("soldrite", 18, fluid("lordslime", 18), fluid("valkyrie", 18));
        sendTiCAlloyInfo("superlumium", 18, fluid("gravitite", 36), fluid("lumium", 18), fluid("experience", 125));
        sendTiCAlloyInfo("antenium", 18, fluid("valkyrie", 16), fluid("neulite", 36), fluid("lava", 250));
        sendTiCAlloyInfo("accelerase", 2, fluid("neulite", 2), fluid("fierkite", 2));
        sendTiCAlloyInfo("terium", 36, fluid("neulite", 36), fluid("sestertium", 18), fluid("sand", 250));
    }

    private void initTier6Alloys() {
        sendTiCAlloyInfo("unseelium", 2, fluid("faerite", 2), fluid("caersin", 2));
        sendTiCAlloyInfo("orinase", 3, fluid("caersin", 3), fluid("octine", 2), fluid("cannium", 1));
        sendTiCAlloyInfo("rallium", 9, fluid("caersin", 18), fluid("endacid", 125));
        sendTiCAlloyInfo("tempestite", 27, fluid("luginite", 18), fluid("endacid", 250), fluid("enderium", 27));
        sendTiCAlloyInfo("fierkite", 4, fluid("tempestite", 2), fluid("aerium", 4), fluid("unseelium", 3));
    }

    private void initTier5Alloys() {
        sendTiCAlloyInfo("lunite", 27, fluid("peractium", 18), fluid("tar", 125), fluid("adipatum", 36));
        sendTiCAlloyInfo("betweensteel", 5, fluid("octine", 3), fluid("syrmorite", 4), fluid("lunite", 1));
        sendTiCAlloyInfo("cannium", 4, fluid("betweensteel", 3), fluid("black_rosite", 4));
        sendTiCAlloyInfo("ascalite", 2, fluid("adipatum", 2), fluid("enderium", 3));
        sendTiCAlloyInfo("aerium", 27, fluid("ascalite", 18), fluid("sestertium", 36), fluid("sponge", 100));
    }

    private void initTier4Alloys() {
        sendTiCAlloyInfo("lordslime", 72, fluid("piridium", 72), fluid("blueslime", 250), fluid("ardorum", 72));
        sendTiCAlloyInfo("littium", 27, fluid("ardorum", 36), fluid("water", 100), fluid("mana", 10));
        sendTiCAlloyInfo("infernium", 3, fluid("obsidian", 4), fluid("manyullyn", 2), fluid("termium", 1));
        sendTiCAlloyInfo("peractium", 4, fluid("iridium", 2), fluid("termium", 1), fluid("knightmetal", 3));
        sendTiCAlloyInfo("sestertium", 18, fluid("fierymetal", 12), fluid("lordslime", 24), fluid("cryotheum", 125), fluid("petrotheum", 125));
    }

    private void initTier3Alloys() {
        sendTiCAlloyInfo("piridium", 12, fluid("knightslime", 18), fluid("aerotheum", 100));
        sendTiCAlloyInfo("luginite", 9, fluid("hot_spring_water", 125), fluid("prismarine", 72), fluid("aluminum", 18));
        sendTiCAlloyInfo("black_rosite", 8, fluid("mirion", 18), fluid("poison", 125), fluid("pyrotheum", 100));
    }

    private void initTier2Alloys() {
        sendTiCAlloyInfo("slipsteel", 12, fluid("steel", 18), fluid("refined_fuel", 125));
        sendTiCAlloyInfo("faerite", 96, fluid("manasteel", 108), fluid("prismarine", 36), fluid("redstone", 250));
        sendTiCAlloyInfo("escalite", 2, fluid("gold", 2), fluid("marshmallow", 5), fluid("milk_chocolate", 4));
    }

    public void postInit() {
        TinkerRegistry.registerMelting(TinkerCommons.matSlimeBallBlue, TinkerFluids.blueslime, 250);
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

    public static void addToArmorAll(Material mat, ITrait trait) {
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(mat, trait, ArmorMaterialType.CORE);
    }

    public static void addToToolAll(Material white_steel, ITrait trait) {

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
