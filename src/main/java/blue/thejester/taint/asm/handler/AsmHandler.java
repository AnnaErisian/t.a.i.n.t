package blue.thejester.taint.asm.handler;

import blue.thejester.taint.traits.potioneffects.SlipperyPotion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;

public class AsmHandler {

    public static float slipFix(float original, EntityLivingBase entity, Block b)
    {
        boolean wearsBoots = false;

        if(entity.getActivePotionEffect(SlipperyPotion.potion) != null) {
            return 0.95F;
        }

        return original;
    }
}
