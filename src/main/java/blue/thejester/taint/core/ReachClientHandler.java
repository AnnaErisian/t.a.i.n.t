package blue.thejester.taint.core;

import blue.thejester.taint.Taint;
import blue.thejester.taint.tools.ToolGlaive;
import blue.thejester.taint.tools.ToolSpear;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shnupbups.tinkersaether.TinkersAether;
import shnupbups.tinkersaether.network.MessageExtendedAttack;
import slimeknights.tconstruct.library.utils.EntityUtil;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class ReachClientHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(
            priority = EventPriority.NORMAL,
            receiveCanceled = true
    )
    public static void onMouseEvent(MouseEvent ev) {
        KeyBinding attackButton = Minecraft.getMinecraft().gameSettings.keyBindAttack;
        if (attackButton.getKeyCode() < 0 && ev.getButton() == attackButton.getKeyCode() + 100 && ev.isButtonstate()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player != null) {
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                if (!stack.isEmpty()) {
                    float reach = -1;
                    float baseReach = (float) player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
                    if (stack.getItem() instanceof ToolSpear) {
                        reach = baseReach + 3.5f;
                    } else if(stack.getItem() instanceof ToolGlaive) {
                        reach = baseReach + 2.0f;
                    }
                    if(reach > 0) {
                        RayTraceResult result = EntityUtil.raytraceEntityPlayerLook(player, reach);
                        if (result != null && result.entityHit != null && result.entityHit.hurtResistantTime == 0 && result.entityHit != player && result.entityHit != player.getRidingEntity()) {
                            Taint.network.sendToServer(new MessageReachAttack(result.entityHit.getEntityId()));
                        }
                    }
                }
            }
        }
    }
}
