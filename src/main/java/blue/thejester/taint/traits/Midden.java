package blue.thejester.taint.traits;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import thebetweenlands.common.config.BetweenlandsConfig;

import javax.annotation.Nullable;
import java.util.List;

public class Midden extends AbstractProjectileTrait {

    public static final Midden midden = new Midden();

    private static final float BONUS = 1.0f;

    public Midden() {
        super("midden", TextFormatting.GOLD);
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (event.getEntity().dimension == BetweenlandsConfig.WORLD_AND_DIMENSION.dimensionId) {
            event.setNewSpeed(event.getNewSpeed() + BONUS);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (player.dimension == BetweenlandsConfig.WORLD_AND_DIMENSION.dimensionId) {
            return super.damage(tool, player, target, damage, newDamage + BONUS, isCritical);
        } else {
            return super.damage(tool, player, target, damage, newDamage, isCritical);
        }
    }

    @Override
    public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
        if (projectileBase.dimension == BetweenlandsConfig.WORLD_AND_DIMENSION.dimensionId) {
            projectileBase.motionX += (projectileBase.motionX * BONUS * 0.1f);
            projectileBase.motionY += (projectileBase.motionY * BONUS * 0.1f);
            projectileBase.motionZ += (projectileBase.motionZ * BONUS * 0.1f);
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String speed  = String.format(LOC_Extra + ".speed", getModifierIdentifier());
        String damage = String.format(LOC_Extra + ".damage", getModifierIdentifier());

        return ImmutableList.of(
                Util.translateFormatted(speed , Util.df.format(BONUS)),
                Util.translateFormatted(damage, Util.df.format(BONUS))
        );
    }
}
