package blue.thejester.taint.traits;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;


public class Mossy extends AbstractTraitLeveled {

    public static final Mossy mossy1 = new Mossy(1);

    private static final String TAG_STORED_XP = "stored_xp";
    private static final String TAG_LAST_HEAL = "heal_timestamp";

    private static final int DELAY = 20 * 7 + 10; // every 7.5s

    public Mossy(int level) {
        super("mossy", 0x43ab32, 4, level);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        // only in the hotbar of a player
        if(!world.isRemote && entity instanceof EntityLivingBase) {
            // must be in hotbar or offhand for players
            if(entity instanceof EntityPlayer
                    && !InventoryPlayer.isHotbar(itemSlot)
                    && ((EntityPlayer) entity).getHeldItemOffhand() != tool) {
                return;
            }

            // needs ot be repaired and is in hotbar or offhand
            if(needsRepair(tool)) {
                if(useXp(tool, world)) {
                    ToolHelper.healTool(tool, getDurabilityPerXP(tool), (EntityLivingBase) entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPickupXp(PlayerPickupXpEvent event) {
        // try mainhand first, then offhand
        List<ItemStack> tools = Lists.newArrayList(event.getEntityPlayer().getHeldItemMainhand(),
                event.getEntityPlayer().getHeldItemOffhand());

        EntityXPOrb entityXPOrb = event.getOrb();

        for(ItemStack itemStack : tools) {
            if(!itemStack.isEmpty() && hasMossyTrait(itemStack)) {
                int stored = storeXp(entityXPOrb.xpValue, itemStack);
                entityXPOrb.xpValue -= stored;
            }
        }
    }

    private boolean hasMossyTrait(ItemStack itemStack) {
        NBTTagList tagList = TagUtil.getTraitsTagList(TagUtil.getTagSafe(itemStack));

        for(int i = 0; i < tagList.tagCount(); i++) {
            if(identifier.equals(tagList.getStringTagAt(i))) {
                return true;
            }
        }

        return false;
    }

    private boolean needsRepair(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItemDamage() > 0 && !ToolHelper.isBroken(itemStack);
    }

    private int getLevel(ItemStack tool) {
        return TinkerUtil.getModifierTag(tool, name).getInteger("level");
    }

    private int getDurabilityPerXP(ItemStack itemStack) {
        return 2 + getLevel(itemStack);
    }

    // 100 * 3^(level-1)
    private int getMaxXp(int level) {
        if(level <= 1) {
            return 100;
        }

        return getMaxXp(level - 1) * 3;
    }

    private boolean canStoreXp(ItemStack tool, int storedXp) {
        return storedXp < getMaxXp(getLevel(tool));
    }

    private int getStoredXp(ItemStack tool) {
        return tool.getTagCompound().getInteger(TAG_STORED_XP);
    }

    private long getLastHeal(ItemStack tool) {
        return tool.getTagCompound().getInteger(TAG_LAST_HEAL);
    }

    private void setStoredXp(ItemStack tool, int newXp) {
        tool.getTagCompound().setInteger(TAG_STORED_XP, newXp);
    }
    private void setLastHeal(ItemStack tool, long newLastHeal) {
        tool.getTagCompound().setLong(TAG_LAST_HEAL, newLastHeal);
    }

    private int storeXp(int amount, ItemStack itemStack) {

        int change = 0;
        if(canStoreXp(itemStack, getStoredXp(itemStack))) {
            int max = getMaxXp(getLevel(itemStack));
            change = Math.min(amount, max - getStoredXp(itemStack));
            setStoredXp(itemStack, getStoredXp(itemStack) + change);
        }
        return change;
    }

    private boolean useXp(ItemStack itemStack, World world) {
        int storedXp = getStoredXp(itemStack);
        long lastHeal = getLastHeal(itemStack);
        if(storedXp > 0 && world.getTotalWorldTime() - lastHeal > DELAY) {
            setStoredXp(itemStack, storedXp - 1);
            setLastHeal(itemStack, world.getTotalWorldTime());
            return true;
        }
        return false;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, name);
        return ImmutableList.of(
                Util.translateFormatted(loc, getStoredXp(tool))
        );
    }
}
