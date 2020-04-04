package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PermanentStatus_Armor extends AbstractArmorTrait {

    public static final PermanentStatus_Armor hasty = new PermanentStatus_Armor("hasty", 0xffffff, MobEffects.HASTE, 60, 0);
    public static final PermanentStatus_Armor jump_boost = new PermanentStatus_Armor("jump_boost", 0xffffff, MobEffects.JUMP_BOOST, 60, 0);
    public static final PermanentStatus_Armor water_breathing = new PermanentStatus_Armor("water_breathing", 0xffffff, MobEffects.WATER_BREATHING, 60, 0);

    private Potion effect;
    private int duration;
    private int amplifier;

    public PermanentStatus_Armor(String identifier, int color) {
        super(identifier, color);
    }

    public PermanentStatus_Armor(String identifier, int color, Potion eff, int dur)
    {
        this(identifier, color);
        effect = eff;
        duration = dur;
    }

    public PermanentStatus_Armor(String identifier, int color, Potion eff, int dur, int amp) {
        this(identifier, color, eff, dur);
        amplifier = amp;
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        super.onAbilityTick(level, world, player);
        if(world.getWorldTime() % 20 == 0) {
            player.addPotionEffect(new PotionEffect(this.effect, this.duration, this.amplifier, false, false));
        }
    }
}
