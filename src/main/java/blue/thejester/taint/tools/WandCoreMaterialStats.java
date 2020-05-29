package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class WandCoreMaterialStats extends AbstractMaterialStats {

    public final static String LOC_FIRE = "stat.core.attunement.fire";
    public final static String LOC_ICE = "stat.core.attunement.ice";
    public final static String LOC_LIGHTNING = "stat.core.attunement.lightning";
    public final static String LOC_NECROMANCY = "stat.core.attunement.necromancy";
    public final static String LOC_SORCERY = "stat.core.attunement.sorcery";
    public final static String LOC_HEALING = "stat.core.attunement.healing";

    public static final String TYPE = "wand_core";

    public float fire = 0;
    public float ice = 0;
    public float lightning = 0;
    public float earth = 0;
    public float necro = 0;
    public float sorc = 0;
    public float healing = 0;


    static {
        Material.UNKNOWN.addStats(new WandCoreMaterialStats(0, 0, 0, 0, 0, 0, 0));
    }

    public WandCoreMaterialStats(float fire, float ice, float lightning, float earth, float necro, float sorc, float healing) {
        super(TYPE);
        this.fire = fire;
        this.ice = ice;
        this.lightning = lightning;
        this.earth = earth;
        this.necro = necro;
        this.sorc = sorc;
        this.healing = healing;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();
        
        String line = "";

        if(fire != 0) {
            line += formatNumber(LOC_FIRE, "ff0000", fire);
        }

        if(ice != 0) {
            line += formatNumber(LOC_ICE, "40b0a7", ice);
        }

        if(lightning != 0) {
            line += formatNumber(LOC_LIGHTNING, "166b8e", lightning);
        }

        if(necro != 0) {
            line += formatNumber(LOC_NECROMANCY, "6c47a3", necro);
        }

        if(sorc != 0) {
            line += formatNumber(LOC_SORCERY, "5eead1", sorc);
        }

        if(healing != 0) {
            line += formatNumber(LOC_HEALING, "f6d178", healing);
        }

        if(!line.isEmpty()) {
            info.add(line);
        }

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();


        return info;
    }
}
