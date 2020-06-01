package blue.thejester.taint.tools;

import blue.thejester.taint.modules.Tools;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.client.particle.Particles;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

public class ToolWarWand extends ToolWand {
    public ToolWarWand() {
        super("warwand",
                new PartMaterialType(Tools.wandGem, WandGemMaterialStats.TYPE, MaterialTypes.HEAD),
                new PartMaterialType(Tools.wandSocket, WandSocketMaterialStats.TYPE),
                new PartMaterialType(Tools.wandCore, WandCoreMaterialStats.TYPE),
                new PartMaterialType(TinkerTools.toolRod, MaterialTypes.HANDLE));
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        WandNBT data = new WandNBT();

        WandGemMaterialStats wandGem = materials.get(0).getStatsOrUnknown(WandGemMaterialStats.TYPE);
        HeadMaterialStats wandHead = materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD);
        WandSocketMaterialStats wandSocket = materials.get(1).getStatsOrUnknown(WandSocketMaterialStats.TYPE);
        WandCoreMaterialStats wandCore = materials.get(2).getStatsOrUnknown(WandCoreMaterialStats.TYPE);
        HandleMaterialStats handle = materials.get(3).getStatsOrUnknown(MaterialTypes.HANDLE);

        data.head(wandHead);
        int oriDur = data.durability;
        data.gemFirst(wandGem);
        data.durability += oriDur;
        data.durability /= 2;
        data.socket(wandSocket);
        data.core(wandCore);
        data.handle(handle);
        data.gemLast(wandGem);

        // 3 free modifiers
        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }


    @Override
    public float damagePotential() {
        return 0.75f;
    }

    @Override
    public float damageCutoff() {
        return 16f;
    }

    @Override
    public double attackSpeed() {
        return 2f;
    }

    @Override
    public float knockback() {
        return 1.0f;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player){
        return false;
    }

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


    // changes the passed in damagesource, but the default method calls we use always create a new object
    public static boolean dealHybridDamage(DamageSource source, Entity target, float damage) {
        if(target instanceof EntityLivingBase) {
            damage /= 2f;
        }

        // <half damage normal, >half damage armor bypassing
        boolean hit = target.attackEntityFrom(source, Math.max(damage-1, 1));
        if(hit && target instanceof EntityLivingBase) {
            EntityLivingBase targetLiving = (EntityLivingBase) target;
            // reset things to deal damage again
            targetLiving.hurtResistantTime = 0;
            targetLiving.attackEntityFrom(source.setDamageBypassesArmor(), damage+1);

            int count = Math.round(damage / 2f);
            if(count > 0) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, targetLiving, count);
            }
        }
        return hit;
    }

}
