package blue.thejester.taint.core;

import blue.thejester.taint.tools.ToolGlaive;
import blue.thejester.taint.tools.ToolSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shnupbups.tinkersaether.TinkersAether;
import shnupbups.tinkersaether.network.MessageExtendedAttack;
import slimeknights.tconstruct.library.tools.TinkerToolCore;

public class ReachServerHandler implements IMessageHandler<MessageReachAttack, IMessage> {

    @Override
    public IMessage onMessage(MessageReachAttack message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServer().addScheduledTask( new Runnable() {
            @Override
            public void run() {
                Entity entity = player.getEntityWorld().getEntityByID(message.getEntityId());
                ItemStack stack = player.getHeldItemMainhand();
                if(stack.isEmpty() || !(stack.getItem() instanceof ToolSpear || stack.getItem() instanceof ToolGlaive) || entity == null) {
                    return;
                }
                player.attackTargetEntityWithCurrentItem(entity);
            }
        });
        return null;
    }
}
