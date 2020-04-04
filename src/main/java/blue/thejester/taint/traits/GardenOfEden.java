package blue.thejester.taint.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.IArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class GardenOfEden extends AbstractTrait implements IArmorTrait {

    public static final GardenOfEden garden_of_eden = new GardenOfEden();

    public GardenOfEden() {
        super("garden_of_eden", 0xffffff);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && entity instanceof EntityLivingBase && random.nextFloat() < 0.05) {
            ((EntityLivingBase)entity).heal(2/3.0f);
        }
    }

    @Override
    public void onAbilityTick(int arg0, World arg1, EntityPlayer arg2) {
        if (random.nextFloat() < 0.05) {
            arg2.heal(arg0 / 6.0f);
        }
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer entityPlayer, ArmorModifications armorModifications, ItemStack itemStack, DamageSource damageSource, double v, int i) {
        return armorModifications;
    }

    @Override
    public void onItemPickup(ItemStack itemStack, EntityItem entityItem, EntityItemPickupEvent entityItemPickupEvent) {

    }

    @Override
    public float onHeal(ItemStack itemStack, EntityPlayer entityPlayer, float v, float v1, LivingHealEvent livingHealEvent) {
        return v1;
    }

    @Override
    public float onHurt(ItemStack itemStack, EntityPlayer entityPlayer, DamageSource damageSource, float v, float v1, LivingHurtEvent livingHurtEvent) {
        return v1;
    }

    @Override
    public float onDamaged(ItemStack itemStack, EntityPlayer entityPlayer, DamageSource damageSource, float v, float v1, LivingDamageEvent livingDamageEvent) {
        return v1;
    }

    @Override
    public void onKnockback(ItemStack itemStack, EntityPlayer entityPlayer, LivingKnockBackEvent livingKnockBackEvent) {

    }

    @Override
    public void onFalling(ItemStack itemStack, EntityPlayer entityPlayer, LivingFallEvent livingFallEvent) {

    }

    @Override
    public void onJumping(ItemStack itemStack, EntityPlayer entityPlayer, LivingEvent.LivingJumpEvent livingJumpEvent) {

    }

    @Override
    public void onArmorEquipped(ItemStack itemStack, EntityPlayer entityPlayer, int i) {

    }

    @Override
    public void onArmorRemoved(ItemStack itemStack, EntityPlayer entityPlayer, int i) {

    }

    @Override
    public int onArmorDamage(ItemStack itemStack, DamageSource damageSource, int i, int i1, EntityPlayer entityPlayer, int i2) {
        return i1;
    }

    @Override
    public int onArmorHeal(ItemStack itemStack, DamageSource damageSource, int i, int i1, EntityPlayer entityPlayer, int i2) {
        return i1;
    }

    @Override
    public boolean disableRendering(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return false;
    }
}
