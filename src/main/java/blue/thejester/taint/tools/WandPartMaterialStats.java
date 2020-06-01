package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public abstract class WandPartMaterialStats extends AbstractMaterialStats {

    public final static String LOC_FIRE_POTENCY = "stat.wand.elements.potency.fire";
    public final static String LOC_ICE_POTENCY = "stat.wand.elements.potency.ice";
    public final static String LOC_LIGHTNING_POTENCY = "stat.wand.elements.potency.lightning";
    public final static String LOC_EARTH_POTENCY = "stat.wand.elements.potency.earth";
    public final static String LOC_NECRO_POTENCY = "stat.wand.elements.potency.necro";
    public final static String LOC_SORCERY_POTENCY = "stat.wand.elements.potency.sorcery";
    public final static String LOC_HEALING_POTENCY = "stat.wand.elements.potency.healing";

    public final static String LOC_FIRE_POTENCY_DESC = "stat.wand.elements.potency.fire.desc";
    public final static String LOC_ICE_POTENCY_DESC = "stat.wand.elements.potency.ice.desc";
    public final static String LOC_LIGHTNING_POTENCY_DESC = "stat.wand.elements.potency.lightning.desc";
    public final static String LOC_EARTH_POTENCY_DESC = "stat.wand.elements.potency.earth.desc";
    public final static String LOC_NECRO_POTENCY_DESC = "stat.wand.elements.potency.necro.desc";
    public final static String LOC_SORCERY_POTENCY_DESC = "stat.wand.elements.potency.sorcery.desc";
    public final static String LOC_HEALING_POTENCY_DESC = "stat.wand.elements.potency.healing.desc";

    public final static String FIRE_COLOR = CustomFontColor.encodeColor(255, 0, 0);
    public final static String ICE_COLOR = CustomFontColor.encodeColor(64, 176, 167);
    public final static String LIGHTNING_COLOR = CustomFontColor.encodeColor(22, 107, 142);
    public final static String EARTH_COLOR = CustomFontColor.encodeColor(85, 170, 85);
    public final static String NECROMANCY_COLOR = CustomFontColor.encodeColor(108, 71, 163);
    public final static String SORCERY_COLOR = CustomFontColor.encodeColor(94, 243, 209);
    public final static String HEALING_COLOR = CustomFontColor.encodeColor(246, 209, 120);
    public final static String GREY_COLOR = CustomFontColor.encodeColor(80, 80, 80);

    public static final String TYPE = "wand_stats";
    private static final String LOC_POTENCY_DESC = "stat.wand.elements.desc";

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

//    protected List<String> getElementLines() {
//        List<String> info = Lists.newArrayList();
//
//        if(fire != 0) {
//            info.add(formatNumberPercent(LOC_FIRE_POTENCY, FIRE_COLOR, fire));
//        }
//
//        if(ice != 0) {
//            info.add(formatNumberPercent(LOC_ICE_POTENCY, ICE_COLOR, ice));
//        }
//
//        if(lightning != 0) {
//            info.add(formatNumberPercent(LOC_LIGHTNING_POTENCY, LIGHTNING_COLOR, lightning));
//        }
//
//        if(earth != 0) {
//            info.add(formatNumberPercent(LOC_EARTH_POTENCY, EARTH_COLOR, earth));
//        }
//
//        if(necro != 0) {
//            info.add(formatNumberPercent(LOC_NECRO_POTENCY, NECROMANCY_COLOR, necro));
//        }
//
//        if(sorc != 0) {
//            info.add(formatNumberPercent(LOC_SORCERY_POTENCY, SORCERY_COLOR, sorc));
//        }
//
//        if(healing != 0) {
//            info.add(formatNumberPercent(LOC_HEALING_POTENCY, HEALING_COLOR, healing));
//        }
//
//        return info;
//    }
//
//    protected List<String> getElementDescLines() {
//        List<String> info = Lists.newArrayList();
//
//        if(fire != 0) {
//            info.add(Util.translate(LOC_FIRE_POTENCY_DESC));
//        }
//
//        if(ice != 0) {
//            info.add(Util.translate(LOC_ICE_POTENCY_DESC));
//        }
//
//        if(lightning != 0) {
//            info.add(Util.translate(LOC_LIGHTNING_POTENCY_DESC));
//        }
//
//        if(earth != 0) {
//            info.add(Util.translate(LOC_EARTH_POTENCY_DESC));
//        }
//
//        if(necro != 0) {
//            info.add(Util.translate(LOC_NECRO_POTENCY_DESC));
//        }
//
//        if(sorc != 0) {
//            info.add(Util.translate(LOC_SORCERY_POTENCY_DESC));
//        }
//
//        if(healing != 0) {
//            info.add(Util.translate(LOC_HEALING_POTENCY_DESC));
//        }
//
//        return info;
//    }

protected List<String> getElementLines() {
    List<String> info = Lists.newArrayList();

    info.add(String.format("%s%s%s/%s%s%s/%s%s%s/%s%s%s/%s%s%s/%s%s%s/%s%s",
            FIRE_COLOR, Util.df.format(fire*100), GREY_COLOR,
            ICE_COLOR, Util.df.format(ice*100), GREY_COLOR,
            LIGHTNING_COLOR, Util.df.format(lightning*100), GREY_COLOR,
            EARTH_COLOR, Util.df.format(earth*100), GREY_COLOR,
            NECROMANCY_COLOR, Util.df.format(necro*100), GREY_COLOR,
            SORCERY_COLOR, Util.df.format(sorc*100), GREY_COLOR,
            HEALING_COLOR, Util.df.format(healing*100)));

    return info;
}

    protected List<String> getElementDescLines() {
        List<String> info = Lists.newArrayList();
        info.add(Util.translate(LOC_POTENCY_DESC));

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        return getElementDescLines();
    }
}
