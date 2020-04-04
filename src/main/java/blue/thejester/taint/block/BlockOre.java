package blue.thejester.taint.block;

import blue.thejester.taint.Taint;
import blue.thejester.taint.core.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockOre extends Block {
    public BlockOre(String name) {
        super(Material.ROCK);
        this.setHardness(3F);
        this.setSoundType(SoundType.STONE);
        setTranslationKey(name);
        setRegistryName(new ResourceLocation(Taint.MODID, name));
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}
