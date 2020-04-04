package blue.thejester.taint.item;

import blue.thejester.taint.Taint;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.ArrayList;
import java.util.List;

public class TraitProfiler extends Item {

    public TraitProfiler(String name) {
        setTranslationKey(name);
        setRegistryName(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        printCSV();
        printReadable();
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private void printCSV() {
        StringBuilder builder = new StringBuilder("name, headdur, headlevel, headspeed, headattack, handlemod, handledur, extradur, bowspeed, bowrange, bowdamage, stringmod, shaftmod, shaftbonus, fletchmod, fletchacc, coredur, coredef, platemod, platedur, platetough, trimdur, trait...");
        for (Material mat : TinkerRegistry.getAllMaterials()) {
            MatRow row = new MatRow();
            row.name = mat.getLocalizedName();
            for (IMaterialStats stats : mat.getAllStats()) {
                switch(stats.getIdentifier()) {
                    case "head" : {
                        row.setHeadStats((HeadMaterialStats) stats);
                        break;
                    }
                    case "handle" : {
                        row.setHandleStats((HandleMaterialStats) stats);
                        break;
                    }
                    case "extra" : {
                        row.setExtraStats((ExtraMaterialStats) stats);
                        break;
                    }
                    case "bow" : {
                        row.setBowStats((BowMaterialStats) stats);
                        break;
                    }
                    case "bowstring" : {
                        row.setStringStats((BowStringMaterialStats) stats);
                        break;
                    }
                    case "shaft" : {
                        row.setShaftStats((ArrowShaftMaterialStats) stats);
                        break;
                    }
                    case "fletching" : {
                        row.setFletchingStats((FletchingMaterialStats) stats);
                        break;
                    }
                    case "core" : {
                        row.setCoreStats((CoreMaterialStats) stats);
                        break;
                    }
                    case "plates" : {
                        row.setPlateStats((PlatesMaterialStats) stats);
                        break;
                    }
                    case "trim" : {
                        row.setTrimStats((TrimMaterialStats) stats);
                        break;
                    }
                }
            }
            for (ITrait trait : mat.getAllTraits()) {
                row.addTrait(trait);
            }
            builder.append(row.toString());
            builder.append("\n");
        }
        Taint.logger.info(builder.toString());
    }

    private void printReadable() {
        StringBuilder builder = new StringBuilder();
        for (Material mat : TinkerRegistry.getAllMaterials()) {
            builder.append("\n========================\n");
            builder.append(mat.identifier).append(" (").append(mat.getLocalizedName()).append(")\n");
            for (IMaterialStats stats : mat.getAllStats()) {
                builder.append("  -" + stats.getLocalizedName() + "-").append("\n    ");
                for (String s : stats.getLocalizedInfo()) {
                    builder.append(s.replaceAll(TextFormatting.RESET.toString(), "").replaceAll("[^\\x00-\\x7F]", "")).append(", ");
                }
                builder.append("\n    Traits: ");
                for (ITrait trait : mat.getAllTraitsForStats(stats.getIdentifier())) {
                    builder.append(trait.getLocalizedName()).append(", ");
                }
                builder.append("\n");
            }
            builder.append("  =====\n");
            for (ITrait trait : mat.getAllTraits()) {
                builder.append("    " + trait.getLocalizedName()).append(": ").append(trait.getLocalizedDesc().replaceAll("\n", "\\n"));
                builder.append("\n");
            }
        }
        Taint.logger.info(builder.toString());
    }

    private static class MatRow {
        public String name;
        public float headdur, headlevel, headspeed, headattack, handlemod, handledur, extradur, bowspeed, bowrange, bowdamage, stringmod, shaftmod, shaftbonus, fletchmod, fletchacc, coredur, coredef, platemod, platedur, platetough, trimdur;
        public List<String> traits;
        public MatRow() {
            name = "";
            headdur = Float.NEGATIVE_INFINITY;
            headlevel = Float.NEGATIVE_INFINITY;
            headspeed = Float.NEGATIVE_INFINITY;
            headattack = Float.NEGATIVE_INFINITY;
            handlemod = Float.NEGATIVE_INFINITY;
            handledur = Float.NEGATIVE_INFINITY;
            extradur = Float.NEGATIVE_INFINITY;
            bowspeed = Float.NEGATIVE_INFINITY;
            bowrange = Float.NEGATIVE_INFINITY;
            bowdamage = Float.NEGATIVE_INFINITY;
            stringmod = Float.NEGATIVE_INFINITY;
            shaftmod = Float.NEGATIVE_INFINITY;
            shaftbonus = Float.NEGATIVE_INFINITY;
            fletchmod = Float.NEGATIVE_INFINITY;
            fletchacc = Float.NEGATIVE_INFINITY;
            coredur = Float.NEGATIVE_INFINITY;
            coredef = Float.NEGATIVE_INFINITY;
            platemod = Float.NEGATIVE_INFINITY;
            platedur = Float.NEGATIVE_INFINITY;
            platetough = Float.NEGATIVE_INFINITY;
            trimdur = Float.NEGATIVE_INFINITY;
            traits = new ArrayList<>();
        }

//        headdur, headlevel, headspeed, headattack,
        public void setHeadStats(HeadMaterialStats h) {
            headdur = h.durability;
            headlevel = h.harvestLevel;
            headspeed = h.miningspeed;
            headattack = h.attack;
        }
//        handlemod, handledur,
        public void setHandleStats(HandleMaterialStats h) {
            handledur = h.durability;
            handlemod = h.modifier;
        }
//        extradur,
        public void setExtraStats(ExtraMaterialStats e) {
            extradur = e.extraDurability;
        }
//        bowspeed, bowrange, bowdamage,
        public void setBowStats(BowMaterialStats b) {
            bowspeed = b.drawspeed;
            bowrange = b.range;
            bowdamage = b.bonusDamage;
        }
//        stringmod,
        public void setStringStats(BowStringMaterialStats b) {
            stringmod = b.modifier;
        }
//        shaftmod, shaftbonus,
        public void setShaftStats(ArrowShaftMaterialStats s) {
            shaftbonus = s.bonusAmmo;
            shaftmod = s.modifier;
        }
//        fletchmod, fletchacc,
        public void setFletchingStats(FletchingMaterialStats f) {
            fletchacc = f.accuracy;
            fletchmod = f.modifier;
        }
//        coredur, coredef,
        public void setCoreStats(CoreMaterialStats c) {
            coredef = c.defense;
            coredur = c.durability;
        }
//        platemod, platedur, platetough,
        public void setPlateStats(PlatesMaterialStats p) {
            platedur = p.durability;
            platemod = p.modifier;
            platetough = p.toughness;
        }
//        trimdur)
        public void setTrimStats(TrimMaterialStats t) {
            trimdur = t.extraDurability;
        }

        public void addTrait(ITrait t) {
            traits.add(t.getLocalizedName());
        }

        @Override
        public String toString() {
            return String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,",
                    name,
                    headdur, headlevel, headspeed, headattack,
                    handlemod, handledur,
                    extradur,
                    bowspeed, bowrange, bowdamage,
                    stringmod,
                    shaftmod, shaftbonus,
                    fletchmod, fletchacc,
                    coredur, coredef,
                    platemod, platedur, platetough,
                    trimdur).replace("-Infinity", "-Inf")
                    + String.join(",", traits);
        }
    }

}