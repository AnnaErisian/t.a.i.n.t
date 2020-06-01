package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public abstract class WandPartMaterialStats extends AbstractMaterialStats {

    public final static String LOC_ELEMENTS = "stat.wand.elements.name";
    public final static String LOC_ELEMENTS_DESC = "stat.wand.elements.desc";

    public final static String LOC_FIRE_POTENCY = "stat.wand.elements.potency.fire";
    public final static String LOC_ICE_POTENCY = "stat.wand.elements.potency.ice";
    public final static String LOC_LIGHTNING_POTENCY = "stat.wand.elements.potency.lightning";
    public final static String LOC_EARTH_POTENCY = "stat.wand.elements.potency.earth";
    public final static String LOC_NECRO_POTENCY = "stat.wand.elements.potency.necro";
    public final static String LOC_SORCERY_POTENCY = "stat.wand.elements.potency.sorcery";
    public final static String LOC_HEALING_POTENCY = "stat.wand.elements.potency.healing";

    public final static String FIRE_COLOR = CustomFontColor.encodeColor(255, 0, 0);
    public final static String ICE_COLOR = CustomFontColor.encodeColor(64, 176, 167);
    public final static String LIGHTNING_COLOR = CustomFontColor.encodeColor(22, 107, 142);
    public final static String EARTH_COLOR = CustomFontColor.encodeColor(85, 170, 85);
    public final static String NECROMANCY_COLOR = CustomFontColor.encodeColor(108, 71, 163);
    public final static String SORCERY_COLOR = CustomFontColor.encodeColor(94, 243, 209);
    public final static String HEALING_COLOR = CustomFontColor.encodeColor(246, 209, 120);

    public static final String TYPE = "wand_stats";

    public float fire = 0;
    public float ice = 0;
    public float lightning = 0;
    public float earth = 0;
    public float necro = 0;
    public float sorc = 0;
    public float healing = 0;

    public WandPartMaterialStats(String type, float fire, float ice, float lightning, float earth, float necro, float sorc, float healing) {
        super(type);
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
        List<String> info = getElementLines();

        return info;
    }

    protected List<String> getElementLines() {
        List<String> info = Lists.newArrayList();

        if(fire != 0) {
            info.add(formatNumberPercent(LOC_FIRE_POTENCY, FIRE_COLOR, fire));
        }

        if(ice != 0) {
            info.add(formatNumberPercent(LOC_ICE_POTENCY, ICE_COLOR, ice));
        }

        if(lightning != 0) {
            info.add(formatNumberPercent(LOC_LIGHTNING_POTENCY, LIGHTNING_COLOR, lightning));
        }

        if(earth != 0) {
            info.add(formatNumberPercent(LOC_EARTH_POTENCY, EARTH_COLOR, earth));
        }

        if(necro != 0) {
            info.add(formatNumberPercent(LOC_NECRO_POTENCY, NECROMANCY_COLOR, necro));
        }

        if(sorc != 0) {
            info.add(formatNumberPercent(LOC_SORCERY_POTENCY, SORCERY_COLOR, sorc));
        }

        if(healing != 0) {
            info.add(formatNumberPercent(LOC_HEALING_POTENCY, HEALING_COLOR, healing));
        }

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.addAll(getElementLines());


        return info;
    }
}
