package blue.thejester.taint.traits;

import blue.thejester.taint.traits.potioneffects.RocketeerPotion;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.Optional;

public class Rocketeer extends AbstractArmorTrait {

    public static final Rocketeer rocketeer = new Rocketeer("rocketeer", 0xffffff);

    public Rocketeer(String identifier, int color) {
        super(identifier, color);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void timing(LivingDeathEvent event) {
        if (event.getEntity().getEntityWorld().isRemote
                || !(event.getEntity() instanceof EntityPlayerMP)) {
            return;
        }

        if (hasDeathSaveArmor((EntityPlayer)event.getEntity())) {
            trySave(event, (EntityPlayerMP) event.getEntity());
        }
    }

    private void trySave(LivingDeathEvent event, EntityPlayerMP player) {
        //if we don't have rocketeer potion, save us (res 10s@10 levitate 1s@70)'
        if(player.getActivePotionEffect(RocketeerPotion.potion) == null) {
            event.setCanceled(true);
            player.clearActivePotions();
            player.setHealth(1);
            player.fallDistance = 0;
            MinecraftForge.EVENT_BUS.register(new Object() {
                @SubscribeEvent
                public void onServerTick(TickEvent.ServerTickEvent event0) {
                    if (!event.getEntityLiving().isBurning()) {
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                    event.getEntityLiving().extinguish();
                }
            });
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20*1, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20*10, 10-1));
            player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20*1, 70-1));
            player.addPotionEffect(new PotionEffect(RocketeerPotion.potion, 60*20));
        }
    }

    //https://github.com/Landmaster/PlusTiC/blob/1.12/src/main/java/landmaster/plustic/traits/DeathSaveTrait.java
    private boolean hasDeathSaveArmor(EntityPlayer player) {
        return Optional.ofNullable(ArmorAbilityHandler.getArmorAbilitiesData(player))
                .map(ArmorAbilityHandler.IArmorAbilities::getAbilityMap)
                .filter(map -> map.containsKey(identifier))
                .isPresent();
    }
}
