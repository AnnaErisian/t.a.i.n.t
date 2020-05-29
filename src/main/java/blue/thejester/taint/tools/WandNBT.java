package blue.thejester.taint.tools;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.Tags;

public class WandNBT extends ToolNBT {

    private static final String TAG_FIRE = "fire_potency";
    private static final String TAG_ICE = "ice_potency";
    private static final String TAG_LIGHTNING = "lightning_potency";
    private static final String TAG_EARTH = "earth_potency";
    private static final String TAG_NECROMANCY = "necro_potency";
    private static final String TAG_SORCERY = "sorc_potency";
    private static final String TAG_HEALING = "heal_potency";

    public float fire = 0;
    public float ice = 0;
    public float lightning = 0;
    public float earth = 0;
    public float necro = 0;
    public float sorc = 0;
    public float healing = 0;

    public WandNBT() {
        super();
    }

    public WandNBT(NBTTagCompound tag) {
        super(tag);
        read(tag);
    }

    @Override
    public void read(NBTTagCompound tag) {
        super.read(tag);
        this.fire = tag.getFloat(TAG_FIRE);
        this.ice = tag.getFloat(TAG_ICE);
        this.lightning = tag.getFloat(TAG_LIGHTNING);
        this.earth = tag.getFloat(TAG_EARTH);
        this.necro = tag.getFloat(TAG_NECROMANCY);
        this.sorc = tag.getFloat(TAG_SORCERY);
        this.healing = tag.getFloat(TAG_HEALING);
    }

    @Override
    public void write(NBTTagCompound tag) {
        super.write(tag);
        tag.setFloat(TAG_FIRE, fire);
        tag.setFloat(TAG_ICE, ice);
        tag.setFloat(TAG_LIGHTNING, lightning);
        tag.setFloat(TAG_EARTH, earth);
        tag.setFloat(TAG_NECROMANCY, necro);
        tag.setFloat(TAG_SORCERY, sorc);
        tag.setFloat(TAG_HEALING, healing);
    }

    public ToolNBT wandCore(WandCoreMaterialStats... cores) {

        fire = 0;
        ice = 0;
        lightning = 0;
        earth = 0;
        necro = 0;
        sorc = 0;
        healing = 0;

        // average all stats
        for(WandCoreMaterialStats core : cores) {
            if(core != null) {
                fire += core.fire;
                ice += core.ice;
                lightning += core.lightning;
                earth += core.earth;
                necro += core.necro;
                sorc += core.sorc;
                healing += core.healing;
            }
        }

        return this;
    }
}
