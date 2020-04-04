package blue.thejester.taint.traits;

import c4.conarm.common.armor.modifiers.ModFrostWalker;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import static c4.conarm.common.armor.modifiers.ModFrostWalker.magmatizeNearby;

public class Tetraboost_Armor extends AbstractArmorTraitLeveled {

    final static int MAX_LEVEL = 1;
    public static final Tetraboost_Armor tetraboost = new Tetraboost_Armor("tetraboost", 0x000000, 1);
    public static final Tetraboost_Armor tetraboost2 = new Tetraboost_Armor("tetraboost", 0x000000, 2);

    public Tetraboost_Armor(String identifier, int color, int level) {
        super(identifier, color, MAX_LEVEL, level);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (!player.world.isRemote) {
            int lv = TinkerUtil.getModifierTag(armor, name).getInteger("level");
            if (source.damageType.equals("magic")) {
                if (random.nextFloat() < (lv * 0.35f)) {
                    if(player.getActivePotionEffect(MobEffects.POISON) != null) {
                        ArmorHelper.damageArmor(armor, source, 2, player);
                        evt.setCanceled(true);
                        player.removePotionEffect(MobEffects.POISON);
                        return 0;
                    }
                }
            } else if (source.damageType.equals("inFire") || source.damageType.equals("onFire")) {
                ArmorHelper.damageArmor(armor, source, 2, player);
                if (random.nextFloat() < (lv * 0.35f)) {
                    evt.setCanceled(true);
                    player.setFire(0);
                    return 0;
                }
            } else if (source.damageType.equals("fall")) {
                ArmorHelper.damageArmor(armor, source, 3, player);
                if(newDamage > 4) {
                    return newDamage / (2 + lv);
                } else {
                    return 0;
                }
            } else if (source == DamageSource.HOT_FLOOR) {
                ArmorHelper.damageArmor(armor, source, 1, player);
                evt.setCanceled(true);
                return 0;
            }
        }

        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote)
        {
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, this.identifier);
            ModFrostWalker.PositionData data = modtag.getTagData(ModFrostWalker.PositionData.class);
            if (data.pos.getX() != player.getPosition().getX() || data.pos.getZ() != player.getPosition().getZ()) {
                NBTTagCompound tag = TinkerUtil.getModifierTag(armor, getIdentifier());
                int level = TinkerUtil.getModifierTag(armor, name).getInteger("level");
                EnchantmentFrostWalker.freezeNearby(player, world, player.getPosition(), level);
                if (level > 1) {
                    magmatizeNearby(player, world, player.getPosition());
                }
                data.pos = player.getPosition();
                modtag.save();
            }
        }
    }
}
