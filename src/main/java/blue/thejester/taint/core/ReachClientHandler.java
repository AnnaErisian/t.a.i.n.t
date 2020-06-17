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
                    if (stack.getItem() instanceof ToolSpear) {
                        reach = 6.5f;
                    } else if(stack.getItem() instanceof ToolGlaive) {
                        reach = 20.0f;
                    }
                    if(reach > 0) {
                        RayTraceResult result = getMouseOverExtended(reach);
                        if (result != null && result.entityHit != null && result.entityHit.hurtResistantTime == 0 && result.entityHit != player && result.entityHit != player.getRidingEntity()) {
                            Taint.network.sendToServer(new MessageReachAttack(result.entityHit.getEntityId()));
                        }
                    }
                }
            }
        }
    }

    protected static RayTraceResult getMouseOverExtended(float distance) {
        RayTraceResult result = null;
        Minecraft mc = Minecraft.getMinecraft();
        Entity renderViewEntity = mc.getRenderViewEntity();
        if (renderViewEntity != null && mc.world != null) {
            double reach = (double)distance;
            //This only get blocks, but we need to know if we're blocked by... a block
            result = renderViewEntity.rayTrace(reach, 0.0F);
            Vec3d eyePos = renderViewEntity.getPositionEyes(0.0F);
            double distanceToBlock = reach;

            if (result != null) {
                distanceToBlock = result.hitVec.distanceTo(eyePos);
            }

            Vec3d lookVec = renderViewEntity.getLook(0.0F);
            Vec3d maxReachPoint = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
            Entity pointedEntity = null;
            Vec3d hitLocation = null;
            //all entities that might be within reach
            List<Entity> entitiesInReach = mc.world.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().expand(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
                public boolean apply(@Nullable Entity entity) {
                    return entity != null && entity.canBeCollidedWith();
                }
            }));
            double distanceToPickedEntity = distanceToBlock;

            for(Entity entity : entitiesInReach) {
                AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double)entity.getCollisionBorderSize());
                RayTraceResult entityRayTraceResult = axisalignedbb.calculateIntercept(eyePos, maxReachPoint);
                if (entityRayTraceResult != null) {
                    double realDistance = eyePos.distanceTo(entityRayTraceResult.hitVec);
                    if (realDistance < distanceToPickedEntity || distanceToPickedEntity == 0.0D) {
                        //If we are riding the same entity as the entity
                        // and things riding us are not allowed to interact with us
                        if (entity.getLowestRidingEntity() == renderViewEntity.getLowestRidingEntity() && !renderViewEntity.canRiderInteract()) {
                            //If we haven't hit something further away, hit this one
                            if (distanceToPickedEntity == 0.0D) {
                                pointedEntity = entity;
                                hitLocation = entityRayTraceResult.hitVec;
                            }
                        } else {
                            pointedEntity = entity;
                            hitLocation = entityRayTraceResult.hitVec;
                            distanceToPickedEntity = realDistance;
                        }
                    }
                } else if (axisalignedbb.contains(eyePos)) {
                    //Special case: If we're INSIDE the entity's bounding box, hit it
                    if (distanceToPickedEntity >= 0.0D) {
                        pointedEntity = entity;
                        hitLocation = entityRayTraceResult == null ? eyePos : entityRayTraceResult.hitVec;
                        distanceToPickedEntity = 0.0D;
                    }
                }
            }

            //If we 'hit' but it's too far away (since the list is from a box, not a sphere), our result is a miss unless we get a real hit later
            if (pointedEntity != null && eyePos.distanceTo(hitLocation) > (double)distance) {
                pointedEntity = null;
                result = new RayTraceResult(RayTraceResult.Type.MISS, hitLocation, EnumFacing.WEST, new BlockPos(hitLocation));
            }
            //otherwise, we do hit this entity, but that might be replaced by something closer
            if (pointedEntity != null && (distanceToPickedEntity < distanceToBlock || result == null)) {
                result = new RayTraceResult(pointedEntity, hitLocation);
            }
        }

        return result;
    }
}
