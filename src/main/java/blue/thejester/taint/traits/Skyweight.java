package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class Skyweight extends AbstractArmorTrait {
    protected static final UUID[] SPEED_MODIFIERS = new UUID[]{ UUID.fromString("2e584ddf-b497-438a-b4d1-091819c6482a"),
            UUID.fromString("5e4b606e-f3cc-4e5f-89a6-b36b621ad50e"),
            UUID.fromString("d8d60705-96ce-4360-b61b-a8ab8b1c722c"),
            UUID.fromString("3bca26c6-2582-4bfc-9206-16415f0786f1") };
    private static final double SPEED_PER_LEVEL = 0.15D;

    public static final Skyweight skyweight = new Skyweight("skyweight", 0x00ff00);

    public Skyweight(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SPEED_MODIFIERS[slot.getIndex()], "Skyweight trait modifier", SPEED_PER_LEVEL, 2));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(SPEED_PER_LEVEL)));
    }
}
