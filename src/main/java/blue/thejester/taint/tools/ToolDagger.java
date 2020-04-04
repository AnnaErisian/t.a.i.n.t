package blue.thejester.taint.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.particle.Particles;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nonnull;
import java.util.List;

public class ToolDagger extends SwordCore {

    private static final float DURABILITY_MODIFIER = 0.8f;

    public ToolDagger() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.knifeBlade),
                PartMaterialType.extra(TinkerTools.binding
                ));

        this.addCategory(Category.WEAPON);

        this.setTranslationKey("dagger").setRegistryName("dagger");
    }

    @Override
    public float damagePotential() {
        return 0.55f;
    }

    @Override
    public float damageCutoff() {
        return 12f;
    }

    @Override
    public double attackSpeed() {
        return 3.8f;
    }

    @Override
    public float knockback() {
        return 0.6f;
    }

    /**
     * Largely taken from the Rapier in Tinker's Construct
     */
    @Override
    public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
        boolean hit;
        if(player instanceof EntityPlayer) {
            hit = dealHybridDamage(DamageSource.causePlayerDamage((EntityPlayer) player), entity, damage);
        }
        else {
            hit = dealHybridDamage(DamageSource.causeMobDamage(player), entity, damage);
        }

        if(hit && readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.RAPIER_ATTACK, player, 0.8d);
        }

        return hit;
    }


    /**
     * Largely taken from the Rapier in Tinker's Construct, but adjusted to 1/3 normal + 2/3 armor bypassing
     */
    public static boolean dealHybridDamage(DamageSource source, Entity target, float damage) {
        if(target instanceof EntityLivingBase) {
            damage /= 3f;
        }

        // one third damage normal, two thirds damage armor bypassing
        boolean hit = target.attackEntityFrom(source, damage);
        if(hit && target instanceof EntityLivingBase) {
            EntityLivingBase targetLiving = (EntityLivingBase) target;
            // reset things to deal damage again
            targetLiving.hurtResistantTime = 0;
            targetLiving.attackEntityFrom(source.setDamageBypassesArmor(), damage*2);

            int count = Math.round(damage / 1.3f);
            if(count > 0) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, targetLiving, count);
            }
        }
        return hit;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return DURABILITY_MODIFIER;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = buildDefaultTag(materials);

        data.durability *= DURABILITY_MODIFIER;

        return data;
    }
}
