package blue.thejester.taint.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.network.EntityMovementChangePacket;

import javax.annotation.Nonnull;
import java.util.List;

//Buckler: no inherent reflection and low damage/knockback, but faster blocking. Pan head, binding.  Damage Potential 0.3, Knockback 0.5, Attack Speed 2.2, DMG Cutoff 6
public class ToolBuckler extends SwordCore {


    public ToolBuckler() {
        super(PartMaterialType.head(TinkerTools.panHead),
                PartMaterialType.extra(TinkerTools.binding));

        this.addCategory(Category.WEAPON);

        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

        setTranslationKey("buckler").setRegistryName("buckler");
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {0};
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public float damagePotential() {
        return 0.3f;
    }

    @Override
    public float damageCutoff() {
        return 6f;
    }

    @Override
    public double attackSpeed() {
        return 2.2f;
    }

    @Override
    public float knockback() {
        return 0.5f;
    }

    //From Battlesign Code
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(!ToolHelper.isBroken(itemStackIn)) {
            playerIn.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
    }

    //From Battlesign Code
    // Extra damage reduction when blocking with a battlesign
    @SubscribeEvent(priority = EventPriority.LOW) // lower priority so we get called later since we change tool NBT
    public void reducedDamageBlocked(LivingHurtEvent event) {
        // don't affect unblockable or magic damage or explosion damage
        // projectiles are handled in LivingAttackEvent
        if(event.getSource().isUnblockable() ||
                event.getSource().isMagicDamage() ||
                event.getSource().isExplosion() ||
                event.getSource().isProjectile() ||
                event.isCanceled()) {
            return;
        }
        if(!shouldBlockDamage(event.getEntityLiving())) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack battlesign = player.getActiveItemStack();

        // got hit by something: reduce damage
        int damage = event.getAmount() < 2f ? 1 : Math.round(event.getAmount() / 2f);
        // reduce damage. After this event the damage will be halved again because we're blocking so we have to factor this in
        event.setAmount(event.getAmount() * 0.7f);
        if(isInEarlyBlock(event.getEntityLiving())) {
            //the normal blocking halving won't happen, so do it here
            event.setAmount(event.getAmount() * 0.0f);
        }

        // bounce the enemy back, but don't do any real damage
        if(event.getSource().getTrueSource() != null) {
            event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(player), 0.01f);
        }
        ToolHelper.damageTool(battlesign, damage, player);
    }

    //From Battlesign Code, reflection removed
    //We still need this to deal with the first 5 frames
    @SubscribeEvent
    public void deflectProjectiles(LivingAttackEvent event) {
        // only blockable projectile damage
        if(event.getSource().isUnblockable() || !event.getSource().isProjectile() || event.getSource().getImmediateSource() == null) {
            return;
        }
        if(!shouldBlockDamage(event.getEntityLiving())) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack battlesign = player.getActiveItemStack();

        // ensure the player is looking at the projectile (aka not getting shot into the back)
        Entity projectile = event.getSource().getImmediateSource();
        Vec3d motion = new Vec3d(projectile.motionX, projectile.motionY, projectile.motionZ);
        Vec3d look = player.getLookVec();

        // this gives a factor of how much we're looking at the incoming arrow
        double strength = -look.dotProduct(motion.normalize());
        // we're looking away. oh no.
        if(strength < 0.1) {
            return;
        }

        // caught that bastard! block it!
        event.setCanceled(true);

        // and drop it to the ground
        // calc speed of the projectile
        double speed = projectile.motionX * projectile.motionX + projectile.motionY * projectile.motionY + projectile.motionZ * projectile.motionZ;
        speed = Math.sqrt(speed/4);

        // and redirect it back, slow and weak
        projectile.motionX = projectile.motionX * -speed;
        projectile.motionZ = projectile.motionY * -speed;
        projectile.motionZ = projectile.motionY * -speed;

        projectile.rotationYaw = (float) (Math.atan2(projectile.motionX, projectile.motionZ) * 180.0D / Math.PI);
        projectile.rotationPitch = (float) (Math.atan2(projectile.motionY, speed) * 180.0D / Math.PI);

        // notify clients from change, otherwise people will get veeeery confused
        TinkerNetwork.sendToAll(new EntityMovementChangePacket(projectile));

        // special treatement for arrows
        if(projectile instanceof EntityArrow) {
            ((EntityArrow) projectile).shootingEntity = player;

            // the inverse is done when the event is cancelled in arrows etc.
            // we reverse it so it has no effect. yay
            projectile.motionX /= -0.10000000149011612D;
            projectile.motionY /= -0.10000000149011612D;
            projectile.motionZ /= -0.10000000149011612D;
        }

        // use durability equal to the damage prevented
        ToolHelper.damageTool(battlesign, (int) event.getAmount(), player);
    }

    //From Battlesign Code
    protected boolean shouldBlockDamage(Entity entity) {
        // hit entity is a player?
        if(!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer) entity;
        // needs to be blocking with a battlesign
        //Change from the TCon code - instead of the default isActiveItemStackBlocking, we have our own
        if(!isActiveItemStackBlocking(player) || player.getActiveItemStack().getItem() != this) {
            return false;
        }

        // broken battlesign.
        return !ToolHelper.isBroken(player.getActiveItemStack());

    }

    protected boolean isInEarlyBlock(Entity entity) {
        // hit entity is a player?
        if(!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer) entity;
        // needs to be blocking with a battlesign
        //Change from the TCon code - instead of the default isActiveItemStackBlocking, we have our own
        if(!isActiveItemStackEarlyBlocking(player) || player.getActiveItemStack().getItem() != this) {
            return false;
        }

        // broken battlesign.
        return !ToolHelper.isBroken(player.getActiveItemStack());

    }

    private boolean isActiveItemStackBlocking(EntityPlayer player) {

        if (player.isHandActive() && !player.getActiveItemStack().isEmpty())
        {
            return player.getActiveItemStack().getItem().getItemUseAction(player.getActiveItemStack()) == EnumAction.BLOCK;
        }
        else
        {
            return false;
        }
    }


    private boolean isActiveItemStackEarlyBlocking(EntityPlayer player) {
        if (player.isHandActive() && !player.getActiveItemStack().isEmpty())
        {
            Item item = player.getActiveItemStack().getItem();

            if (item.getItemUseAction(player.getActiveItemStack()) != EnumAction.BLOCK)
            {
                //not blocking at all
                return false;
            }
            else
            {
                return item.getMaxItemUseDuration(player.getActiveItemStack()) - player.getItemInUseCount() < 5;
            }
        }
        else
        {
            //not blocking at all
            return false;
        }
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = new ToolNBT();

        HeadMaterialStats head = materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats binding = materials.get(1).getStatsOrUnknown(MaterialTypes.EXTRA);

        data.head(head);
        data.extra(binding);

        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }
}
