package blue.thejester.taint.tools;

import blue.thejester.taint.modules.Tools;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import shnupbups.tinkersaether.traits.Reach;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.modifiers.ModBeheading;

import java.util.List;
import java.util.UUID;

//Spear: long range tinker weapon, damage/speed as rapier but 0.7 damage potential. Knifeblade, Binding, 2 Rod.
public class ToolSpear extends SwordCore {


    private static final float DURABILITY_MODIFIER = 1.1f;
    private static final UUID SPEAR_REACH_MODIFIER = UUID.fromString("89f0ebfe-c8a0-4e69-bddd-b0a4e32b8f9b");

    public ToolSpear() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.knifeBlade),
                PartMaterialType.extra(TinkerTools.binding));

        this.addCategory(Category.WEAPON);

        setTranslationKey("spear").setRegistryName("spear");
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {2};
    }

    @Override
    public float damagePotential() {
        return 0.8f;
    }

    @Override
    public double attackSpeed() {
        return 2.0f;
    }

    @Override
    public float damageCutoff() {
        return 16f;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return DURABILITY_MODIFIER;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = new ToolNBT();

        HandleMaterialStats rod1 = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
        HandleMaterialStats rod2 = materials.get(1).getStatsOrUnknown(MaterialTypes.HANDLE);
        HeadMaterialStats head = materials.get(2).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats binding = materials.get(3).getStatsOrUnknown(MaterialTypes.EXTRA);

        data.head(head);
        data.extra(binding);
        data.handle(rod1, rod2);

        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND && stack.getItem() == Tools.spear) {
            multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(SPEAR_REACH_MODIFIER, "Tool modifier", 3.5, Constants.AttributeModifierOperation.ADD));
        }

        return multimap;
    }
}
