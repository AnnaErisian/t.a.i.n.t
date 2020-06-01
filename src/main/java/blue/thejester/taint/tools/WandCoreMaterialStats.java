package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class WandCoreMaterialStats extends WandPartMaterialStats {

    public final static String LOC_UPGRADE_CAP = "stat.wandcore.upgrade.cap.name";
    public final static String LOC_CAPACITY_MOD = "stat.wandcore.capacitymod.name";
    public final static String LOC_UPGRADE_CAP_DESC = "stat.wandcore.upgrade.cap.desc";
    public final static String LOC_CAPACITY_MOD_DESC = "stat.wandcore.capacitymod.desc";
    public final static String COLOR_MOD_CAP = CustomFontColor.encodeColor(215, 100, 100);
    public final static String COLOR_CAPACITY_MOD = CustomFontColor.encodeColor(120, 160, 205);

    public static final String TYPE = "wand_core";

    static {
        Material.UNKNOWN.addStats(new WandCoreMaterialStats(3, 1, 0, 0, 0, 0, 0, 0, 0));
    }

    public int upgradeLimit;
    public float capacityMod;

    public WandCoreMaterialStats(int upgradeLimit, float capacityMod, float fire, float ice, float lightning, float earth, float necro, float sorc, float healing) {
        super(TYPE, fire, ice, lightning, earth, necro, sorc, healing);
        this.upgradeLimit = upgradeLimit;
        this.capacityMod = capacityMod;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatNumber(LOC_UPGRADE_CAP, COLOR_MOD_CAP, upgradeLimit));
        info.add(formatNumber(LOC_CAPACITY_MOD, COLOR_CAPACITY_MOD, capacityMod));

        info.addAll(getElementLines());

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_UPGRADE_CAP_DESC));
        info.add(Util.translate(LOC_CAPACITY_MOD_DESC));

        info.addAll(getElementDescLines());

        return info;
    }
}
