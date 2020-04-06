package blue.thejester.taint.tools;

import blue.thejester.taint.modules.Tools;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import shnupbups.tinkersaether.traits.Reach;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ToolGlaive extends SwordCore {


    private static final float DURABILITY_MODIFIER = 1.8f;
    private static final UUID GLAIVE_REACH_MODIFIER = UUID.fromString("a2dd4317-5694-43fa-8b0b-ae52ab9c8bbc");

    public ToolGlaive() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.swordBlade),
                PartMaterialType.extra(TinkerTools.binding));

        this.addCategory(Category.WEAPON);

        setTranslationKey("glaive").setRegistryName("glaive");
    }

    // no offhand for you
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {2};
    }

    @Override
    public float damagePotential() {
        return 1.1f;
    }

    @Override
    public double attackSpeed() {
        return 1.2;
    }

    @Override
    public float damageCutoff() {
        return 22f;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return DURABILITY_MODIFIER;
    }

    // based on code in Scythe
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {

        // only do AOE attack if the attack meter is charged
        if(player.getCooledAttackStrength(0.5F) <= 0.9f) {
            return super.onLeftClickEntity(stack, player, target);
        }

        // AOE attack!
        player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
        player.spawnSweepParticles();

        boolean hit = false;
        // and then sweep
        if(!ToolHelper.isBroken(stack)) {
            for(EntityLivingBase entitylivingbase : getAoeEntities(player, target)) {
                if(entitylivingbase != player && !player.isOnSameTeam(entitylivingbase)) {
                    hit |= ToolHelper.attackEntity(stack, this, player, entitylivingbase, null, false);
                }
            }
        }

        if(hit) {
            player.resetCooldown();
        }

        // subtract the default box and then half as this number is the amount to increase the box by
        return hit;

    }

    private List<EntityLivingBase> getAoeEntities(EntityLivingBase player, Entity target) {
        AxisAlignedBB box = new AxisAlignedBB(target.posX-4, target.posY-2, target.posZ-4, target.posX + 4, target.posY + 2, target.posZ + 4);
        List<EntityLivingBase> entities = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, box);
        Vec3d vecLook = player.getLookVec().normalize();
        float distanceTarget = player.getDistance(target);
        entities.removeIf(new Predicate<EntityLivingBase>() {
            @Override
            public boolean test(EntityLivingBase entityLivingBase) {
                //vector from them to us
                Vec3d vecDirection = player.getPositionVector().subtractReverse(entityLivingBase.getPositionVector()).normalize();
                float distanceOther = player.getDistance(entityLivingBase);
                double cos = Math.abs(vecLook.dotProduct(vecDirection));
                double distDiff = Math.abs(distanceOther - distanceTarget);
                return cos < 0.70 || distDiff > 1.0;
            }
        });
        return entities;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = new ToolNBT();

        HandleMaterialStats rod1 = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
        HandleMaterialStats rod2 = materials.get(1).getStatsOrUnknown(MaterialTypes.HANDLE);
        HeadMaterialStats head = materials.get(2).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats binding = materials.get(3).getStatsOrUnknown(MaterialTypes.EXTRA);

        data.head(head);
        data.extra(binding);
        data.handle(rod1, rod2);

        data.modifiers = DEFAULT_MODIFIERS;
        data.attack *= 1.15f;
        data.attack += 2f;

        return data;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND && stack.getItem() == Tools.glaive) {
            multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(GLAIVE_REACH_MODIFIER, "Tool modifier", 2.0, Constants.AttributeModifierOperation.ADD));
        }

        return multimap;
    }
}
