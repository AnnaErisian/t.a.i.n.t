package blue.thejester.taint.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;


public class Superstar extends SuperstarShim {

    public static final Superstar superstar = new Superstar(1);
    private static int jumpsUsed;
    private static boolean jumpPressed;

    public Superstar(int level) {
        super("taint_superstar", 0x43ab32, 1, level);
    }


    //Yeah that works nice nice nice
    //It doesn't give you fall negation, get that
    //It does not stack with cloud amulet but shut up
    @Override
    @SideOnly(Side.CLIENT)
    public void updateClient(ItemStack piece, World world, Entity player, int itemSlot, boolean isSelected) {
        //code similar to CloudPendant in Botania
        if (world.isRemote) {
            if (player instanceof EntityPlayerSP && player == Minecraft.getMinecraft().player) {
                EntityPlayerSP playerSp = (EntityPlayerSP) player;

                if (playerSp.onGround) {
                    jumpsUsed = 0;
                } else {
                    if (playerSp.movementInput.jump) {
                        if (!jumpPressed && jumpsUsed < getTotalSuperstarLevel(playerSp)) {
                            playerSp.jump();
                            playerSp.velocityChanged = true;
                            jumpsUsed++;
                        }
                        jumpPressed = true;
                    } else jumpPressed = false;
                }
            }
        }
    }

    private int getTotalSuperstarLevel(EntityPlayer p) {
        Iterable<ItemStack> armorInventoryList = p.getArmorInventoryList();
        int totalLevel = 1;
        for(ItemStack armor : armorInventoryList) {
            if(armor.getItem() instanceof ArmorCore) {
                totalLevel += TinkerUtil.getModifierTag(armor, name).getInteger("level");
            }
        }
        return totalLevel;
    }
}
