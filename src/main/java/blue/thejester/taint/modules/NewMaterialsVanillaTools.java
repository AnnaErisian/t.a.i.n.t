package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import blue.thejester.taint.helper.fluid.Create;
import blue.thejester.taint.item.MetalMaterial;
import blue.thejester.taint.item.vanitools.*;
import blue.thejester.taint.traits.*;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import landmaster.plustic.traits.Apocalypse;
import landmaster.plustic.traits.Elemental;
import landmaster.plustic.traits.Mana;
import landmaster.plustic.traits.armor.DunansTransport;
import nc.integration.tconstruct.conarm.trait.NCArmorTraits;
import nc.integration.tconstruct.trait.NCTraits;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
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

import static blue.thejester.taint.item.MetalMaterial.*;

public class NewMaterialsVanillaTools implements IModule {

    private static MetalMaterial[] minableMetals = {
            ardorum,
            termium,
            adipatum,
            caersin,
            neulite,
            atercaeum,
            oscurum,
            inurose,
            cibarite
    };

    @Override
    public void preInit() {

        createToolAndArmorMaterials();

        for(MetalMaterial mat : minableMetals) {
            mat.tAxe = new ModAxeItem(mat.name() + "_axe", mat.tMat, 8f, -2.9f);
            mat.tPick = new ModPickItem(mat.name() + "_pickaxe", mat.tMat);
            mat.tSword = new ModSwordItem(mat.name() + "_sword", mat.tMat);
            mat.tHoe = new ModHoeItem(mat.name() + "_hoe", mat.tMat);
            mat.tShovel = new ModShovelItem(mat.name() + "_shovel", mat.tMat);
            mat.aBoots = new ModArmorItem(mat.name() + "_boots", mat.aMat, 1, EntityEquipmentSlot.FEET);
            mat.aLeggings = new ModArmorItem(mat.name() + "_leggings", mat.aMat, 2, EntityEquipmentSlot.LEGS);
            mat.aChestplate = new ModArmorItem(mat.name() + "_chestplate", mat.aMat, 1, EntityEquipmentSlot.CHEST);
            mat.aHelmet = new ModArmorItem(mat.name() + "_helmet", mat.aMat, 1, EntityEquipmentSlot.HEAD);
        }

    }

    private void createToolAndArmorMaterials() {
        cmat(ardorum, 4, 2000, 7.5f, 3.3f, 15, 40,   3, 8, 6, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2f);
        cmat(termium, 4, 2250, 8.5f, 4f, 16, 42,     4, 8, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.2f);
        cmat(adipatum, 4, 2500, 9.0f, 3.5f, 17, 44,  4, 8, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 242f);
        cmat(caersin, 4, 2300, 8.8f, 3.5f, 18, 40,   4, 8, 6, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2f);
        cmat(neulite, 4, 2900, 9.2f, 3.2f, 19, 44,   4, 8, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.4f);
        cmat(atercaeum, 4, 3000, 9.5f, 4f, 26, 46,   4, 9, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.8f);
        cmat(oscurum, 4, 4000, 9.5f, 3.2f, 22, 54,   4, 9, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.4f);
        cmat(inurose, 4, 3500, 10.0f, 3.9f, 23, 50,  4, 9, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.5f);
        cmat(cibarite, 4, 3000, 11.0f, 4.5f, 24, 46, 4, 9, 7, 4, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.6f);
    }

    private void cmat(MetalMaterial mat, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability,
                      int armorDurability, int bootDef, int chestDef, int legDef, int helmDef, SoundEvent equipSound, float toughness) {
        mat.tMat = EnumHelper.addToolMaterial("material_"+mat.name(), harvestLevel, maxUses, efficiency, damage, enchantability);
        mat.aMat = EnumHelper.addArmorMaterial("material_"+mat.name()+"_armor", Taint.MODID+":"+mat.name(),armorDurability,
                new int[]{bootDef, chestDef, legDef, helmDef}, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, toughness);
    }

    @Override
    public void postInit() {

    }
}
