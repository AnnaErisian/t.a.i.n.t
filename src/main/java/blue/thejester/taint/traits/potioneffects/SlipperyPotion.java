package blue.thejester.taint.traits.potioneffects;

import blue.thejester.taint.Taint;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Taint.MODID)
public class SlipperyPotion extends Potion {

    public static final SlipperyPotion potion = new SlipperyPotion("taint_slippery", false, 0x333333);

    protected SlipperyPotion(String name, boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setRegistryName(name);
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        evt.getRegistry().register(potion);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<ItemStack>();
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect) {
        return false;
    }

    @Override
    public boolean shouldRender(PotionEffect effect) {
        return false;
    }
}
