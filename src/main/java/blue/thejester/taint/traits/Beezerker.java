package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

import static java.lang.Math.max;

public class Beezerker extends AbstractArmorTrait {

    private static final String CHARGE_TAG = "beepower";

    public static final Beezerker beezerker = new Beezerker("beezerker", 0xffffff);

    public Beezerker(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(!source.isUnblockable()) {
            //when hit, convert all but 1 of the damage into poison (high enough damage will become higher levels of poison, breakpoints at each 8 damage). When poisoned, also gain Strength and Haste to match.
            int level = (int) (newDamage / 8);
            int d = (int) (25*(damage-1));
            int a = 0;
            if(level > 0) {
                d = (int) (12*(damage-1));
                a = 1;
            }
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, d, a));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, d, a));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, d, a));

            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
            Beezerker.Data data = modtag.getTagData(Beezerker.Data.class);
            data.charge = d-5; //small anti-buffer so the charge will end before the potions
            modtag.save();

            return 1;
        }
        return newDamage;
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer entity) {
        if(world.isRemote || world.getTotalWorldTime() % 5 > 0) {
            return;
        }
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, getModifierIdentifier());
        Beezerker.Data data = modtag.getTagData(Beezerker.Data.class);


        if(data.charge > 0) {
            //we should be active - check if we're still poisoned though
            if(entity.getActivePotionEffect(MobEffects.POISON) == null) {
                //we're active but not poisoned - cancel the activation and remove the relevant effects
                data.charge = 0;
                entity.removePotionEffect(MobEffects.STRENGTH);
                entity.removePotionEffect(MobEffects.HASTE);
            }
        }

        data.charge = max(0,data.charge-5);

        modtag.save();
    }

    public static class Data extends ModifierNBT {

        float charge;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            charge = tag.getFloat(CHARGE_TAG);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setFloat(CHARGE_TAG, charge);
        }
    }
}
