package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;


public class ImmortalCenturion extends AbstractArmorTraitLeveled {

    public static final ImmortalCenturion immortalCenturion1 = new ImmortalCenturion(1);
    public static final ImmortalCenturion immortalCenturion2 = new ImmortalCenturion(2);

    private static final String TAG_STORED_CHARGE = "centurion_charge";
    private static final String TAG_LAST_CHARGED = "centurion_charge_last";

    private static final int BASE_DELAY = 180; // every 9s

    public ImmortalCenturion(int level) {
        super("immortal_centurion", 0x43ab32, 2, level);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer entity) {
        if(!world.isRemote) {
            //If we're on a tick that is a multiple of BASE_DELAY/level and we're under 3*level stored, store 1 damage
            if((world.getTotalWorldTime() != getLastStoredCharge(tool)) && (world.getTotalWorldTime() % (BASE_DELAY / getLevel(tool)) == 0)) {
                int storedDamage = getStoredCharge(tool);
                setLastStoredCharge(tool, world.getTotalWorldTime());
                if(canStoreDamage(tool, storedDamage)) {
//                    entity.sendMessage(new TextComponentTranslation("stored " + (storedDamage + 1)));
                    setStoredCharge(tool, storedDamage + 1);
                }
            }
        }
    }

    private int getLevel(ItemStack tool) {
        return TinkerUtil.getModifierTag(tool, name).getInteger("level");
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        int storedDamage = getStoredCharge(armor);
        if(storedDamage > newDamage) {
            setStoredCharge(armor, (int) (storedDamage - newDamage));
            evt.setCanceled(true);
            return 0;
        }
        else if(storedDamage > 6 && storedDamage >= getMaxDamage(getLevel(armor))) {
            setStoredCharge(armor, 0);
            evt.setCanceled(true);
            return 0;
        } else {
            setStoredCharge(armor, 0);
            return Math.max(0, newDamage-storedDamage);
        }
    }

    // 4*level
    private int getMaxDamage(int level) {
        return 4 * level;
    }

    private boolean canStoreDamage(ItemStack armor, int storedDamage) {
        return storedDamage < getMaxDamage(getLevel(armor));
    }

    private int getStoredCharge(ItemStack tool) {
        return tool.getTagCompound().getInteger(TAG_STORED_CHARGE);
    }

    private void setStoredCharge(ItemStack tool, int newXp) {
        tool.getTagCompound().setInteger(TAG_STORED_CHARGE, newXp);
    }

    private long getLastStoredCharge(ItemStack tool) {
        return tool.getTagCompound().getLong(TAG_LAST_CHARGED);
    }

    private void setLastStoredCharge(ItemStack tool, long newTime) {
        tool.getTagCompound().setLong(TAG_LAST_CHARGED, newTime);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, name);
        return ImmutableList.of(
                Util.translateFormatted(loc, getStoredCharge(tool))
        );
    }
}
