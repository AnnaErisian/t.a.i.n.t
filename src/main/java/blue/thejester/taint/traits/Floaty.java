package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Floaty extends AbstractArmorTrait {

    public static final Floaty floaty = new Floaty("floaty", 0xffffff);

    public Floaty(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        player.fallDistance = 0f;
        if(player.motionY < -0.5) {
            player.motionY = -0.5;
            player.velocityChanged = true;
        }
    }

}
