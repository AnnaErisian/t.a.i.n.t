package blue.thejester.taint.tools;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;

public class WandNBT extends ToolNBT {

    public static final String TAG_FIRE = "fire_potency";
    public static final String TAG_ICE = "ice_potency";
    public static final String TAG_LIGHTNING = "lightning_potency";
    public static final String TAG_EARTH = "earth_potency";
    public static final String TAG_NECROMANCY = "necro_potency";
    public static final String TAG_SORCERY = "sorc_potency";
    public static final String TAG_HEALING = "heal_potency";
    public static final String TAG_CAST_TIER = "cast_tier";
    public static final String TAG_POTENCY_TIER = "potency_tier";
    public static final String TAG_UPGRADE_SLOTS = "mod_slots";
    public static final String TAG_UPGRADE_LIMIT = "mod_limit";

    public float fire = 0;
    public float ice = 0;
    public float lightning = 0;
    public float earth = 0;
    public float necro = 0;
    public float sorc = 0;
    public float healing = 0;

    public int castTier = 0;
    public int upgradeSlots = 0;
    public int upgradeLimit = 3;

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
        this.castTier = tag.getInteger(TAG_CAST_TIER);
        this.upgradeSlots = tag.getInteger(TAG_UPGRADE_SLOTS);
        this.upgradeLimit = tag.getInteger(TAG_UPGRADE_LIMIT);
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
        tag.setInteger(TAG_CAST_TIER, castTier);
        tag.setInteger(TAG_UPGRADE_SLOTS, upgradeSlots);
        tag.setInteger(TAG_UPGRADE_LIMIT, upgradeLimit);
    }

    private ToolNBT elements(WandPartMaterialStats... parts) {

        for(WandPartMaterialStats part : parts) {
            if(part != null) {
                fire += part.fire;
                ice += part.ice;
                lightning += part.lightning;
                earth += part.earth;
                necro += part.necro;
                sorc += part.sorc;
                healing += part.healing;
            }
        }

        return this;
    }

    /** Initialize the stats with the gem/head. CALL THIS FIRST */
    public ToolNBT gemFirst(WandGemMaterialStats... gems) {
        durability = 0;

        fire = 0;
        ice = 0;
        lightning = 0;
        earth = 0;
        necro = 0;
        sorc = 0;
        healing = 0;

        // average all stats
        for(WandGemMaterialStats gem : gems) {
            if(gem != null) {
                durability += gem.capacity;
                // use highest casting tier
                if(gem.tier > castTier) {
                    castTier = gem.tier;
                }
            }
        }

        elements(gems);

        durability = Math.max(1, durability / gems.length);

        return this;
    }

    /** Add stats from the socket. Call this second! */
    public ToolNBT socket(WandSocketMaterialStats... extras) {
        int dur = 0;
        for(WandSocketMaterialStats socket : extras) {
            if(socket != null) {
                dur += socket.capacityBonus;

                // use highest upgrade slot count
                if(socket.upgradeSlots > upgradeSlots) {
                    upgradeSlots = socket.upgradeSlots;
                }
            }
        }
        this.durability += Math.round((float) dur / (float) extras.length);

        elements(extras);

        return this;
    }

    /** Calculate in handles. call this last! */
    public ToolNBT core(WandCoreMaterialStats... cores) {
        // (Average Head Durability + Average Extra Durability) * Average Handle Modifier + Average Handle Durability

        float modifier = 0f;
        for(WandCoreMaterialStats core : cores) {
            if(core != null) {
                modifier += core.capacityMod;

                // use highest upgrade limit
                if(core.upgradeLimit > upgradeLimit) {
                    upgradeLimit = core.upgradeLimit;
                }
            }
        }

        modifier /= (float) cores.length;
        this.durability = Math.round((float) this.durability * modifier);

        this.durability = Math.max(1, this.durability);

        elements(cores);

        return this;
    }

    public ToolNBT gemLast(WandGemMaterialStats... heads) {
        for(WandGemMaterialStats head : heads) {
            if(head != null) {
                fire *= head.potencyMod;
                ice *= head.potencyMod;
                lightning *= head.potencyMod;
                earth *= head.potencyMod;
                necro *= head.potencyMod;
                healing *= head.potencyMod;
                sorc *= head.potencyMod;
            }
        }

        //not gonna let this get too crazy, but it should keep up with modded
        //Since that is the *whole* point of this integration
        fire = adjustPotency(fire);
        ice = adjustPotency(ice);
        lightning = adjustPotency(lightning);
        earth = adjustPotency(earth);
        necro = adjustPotency(necro);
        healing = adjustPotency(healing);
        sorc = adjustPotency(sorc);

        return this;
    }

    public ToolNBT gemLastStaff(WandGemMaterialStats... heads) {
        for(WandGemMaterialStats head : heads) {
            if(head != null) {
                fire *= head.potencyMod;
                ice *= head.potencyMod;
                lightning *= head.potencyMod;
                earth *= head.potencyMod;
                necro *= head.potencyMod;
                healing *= head.potencyMod;
                sorc *= head.potencyMod;
            }
        }

        //not gonna let this get too crazy, but it should keep up with modded
        //Since that is the *whole* point of this integration
        fire = adjustPotencyStaff(fire);
        ice = adjustPotencyStaff(ice);
        lightning = adjustPotencyStaff(lightning);
        earth = adjustPotencyStaff(earth);
        necro = adjustPotencyStaff(necro);
        healing = adjustPotencyStaff(healing);
        sorc = adjustPotencyStaff(sorc);

        return this;
    }

    /**
     * This gives better than expected values until 118%. after which it falls off a bit
     * @param potency
     * @return
     */
    private static float adjustPotency(float potency){
        return 1.05f * (float) Math.pow(potency, 9f/16f);
    }

    /**
     * This gives better than expected values until 207%. after which it falls off a bit
     * @param potency
     * @return
     */
    private static float adjustPotencyStaff(float potency){
        return 1.2f * (float) Math.pow(potency, 0.75f);
    }
}
