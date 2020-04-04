package blue.thejester.taint.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.client.particle.Particles;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

public class ToolWaraxe extends SwordCore {


    private static final float DURABILITY_MODIFIER = 1.05f;

    public ToolWaraxe() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.axeHead),
                PartMaterialType.extra(TinkerTools.crossGuard
                ));

        this.addCategory(Category.WEAPON);

        setTranslationKey("waraxe").setRegistryName("waraxe");
    }


    @Override
    public float damagePotential() {
        return 1.2f;
    }

    @Override
    public float damageCutoff() {
        return 20f;
    }

    @Override
    public double attackSpeed() {
        return 1.4f;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return DURABILITY_MODIFIER;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = buildDefaultTag(materials);

        data.attack += 1.0f;
        data.durability *= DURABILITY_MODIFIER;

        return data;
    }
}
