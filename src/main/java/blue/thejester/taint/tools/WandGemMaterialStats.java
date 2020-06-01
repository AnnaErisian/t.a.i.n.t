package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class WandGemMaterialStats extends WandPartMaterialStats {

    public final static String LOC_TIER = "stat.wandhead.tier.name";
    public final static String LOC_POTENCY_MOD = "stat.wandhead.potency.name";
    public final static String LOC_CAPACITY = "stat.wandhead.capacity.name";
    public final static String LOC_TIER_DESC = "stat.wandhead.tier.desc";
    public final static String LOC_POTENCY_MOD_DESC = "stat.wandhead.potency.desc";
    public final static String LOC_CAPACITY_DESC = "stat.wandhead.capacity.desc";
    public final static String COLOR_TIER = CustomFontColor.encodeColor(215, 100, 100);
    public final static String COLOR_POTENCY_MOD = CustomFontColor.encodeColor(120, 160, 205);
    public final static String COLOR_CAPACITY = CustomFontColor.encodeColor(120, 220, 160);

    public static final String TYPE = "wand_gem";

    static {
        Material.UNKNOWN.addStats(new WandGemMaterialStats(0, 1, 0, 0, 0, 0, 0, 0, 0, 0));
    }

    public int tier;
    public float potencyMod;
    public int capacity;

    public WandGemMaterialStats(int tier, float potencyMod, int capacity, float fire, float ice, float lightning, float earth, float necro, float sorc, float healing) {
        super(TYPE, fire, ice, lightning, earth, necro, sorc, healing);
        this.tier = tier;
        this.potencyMod = potencyMod;
        this.capacity = capacity;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatNumber(LOC_CAPACITY, COLOR_CAPACITY, capacity));
        info.add(formatNumber(LOC_TIER, COLOR_TIER, tier));
        info.add(formatNumber(LOC_POTENCY_MOD, COLOR_POTENCY_MOD, potencyMod));

        info.addAll(getElementLines());

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_CAPACITY_DESC));
        info.add(Util.translate(LOC_POTENCY_MOD_DESC));
        info.add(Util.translate(LOC_TIER_DESC));
        info.add(Util.translate(LOC_ELEMENTS_DESC));

        return info;
    }
}
