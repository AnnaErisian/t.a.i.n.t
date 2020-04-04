package blue.thejester.taint.traits;

import blue.thejester.taint.traits.potioneffects.SlipperyPotion;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class Slippery_Armor extends AbstractArmorTrait {

    final static int MAX_LEVEL = 2;
    public static final Slippery_Armor slippery = new Slippery_Armor("slippery", 0xffffff, 1);

    public int level = 1;

    public Slippery_Armor(String identifier, int color, int level) {
        super(identifier, color);
        this.level = level;
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        super.onAbilityTick(level, world, player);
        if(world.getWorldTime() % 20 == 0) {
            player.addPotionEffect(new PotionEffect(SlipperyPotion.potion, 200, 0, false, false));
        }
    }

    @Override
    public void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt) {
        evt.setStrength(evt.getStrength() * 1.3f);
    }
}
