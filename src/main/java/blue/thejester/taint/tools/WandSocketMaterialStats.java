package blue.thejester.taint.tools;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class WandSocketMaterialStats extends WandPartMaterialStats {

    public final static String LOC_UPGRADE_SLOTS = "stat.wandsock.upgrade.slots.name";
    public final static String LOC_CAPACITY_BONUS = "stat.wandsock.capacity.name";
    public final static String LOC_UPGRADE_SLOTS_DESC = "stat.wandsock.upgrade.slots.desc";
    public final static String LOC_CAPACITY_BONUS_DESC = "stat.wandsock.capacity.desc";
    public final static String COLOR_SLOTS     = CustomFontColor.encodeColor(215, 100, 100);
    public final static String COLOR_DURABILITY      = CustomFontColor.encodeColor(120, 160, 205);

    public static final String TYPE = "wand_socket";

    static {
        Material.UNKNOWN.addStats(new WandSocketMaterialStats(1, 3, 0, 0, 0, 0, 0, 0, 0));
    }

    public int capacityBonus;
    public int upgradeSlots;

    public WandSocketMaterialStats(int capacityBonus, int upgradeSlots, float fire, float ice, float lightning, float earth, float necro, float sorc, float healing) {
        super(TYPE, fire, ice, lightning, earth, necro, sorc, healing);
        this.capacityBonus = capacityBonus;
        this.upgradeSlots = upgradeSlots;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatNumber(LOC_UPGRADE_SLOTS, COLOR_SLOTS, upgradeSlots));
        info.add(formatNumber(LOC_CAPACITY_BONUS, COLOR_DURABILITY, capacityBonus));

        info.addAll(getElementLines());

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_UPGRADE_SLOTS_DESC));
        info.add(Util.translate(LOC_CAPACITY_BONUS_DESC));

        info.addAll(getElementDescLines());

        return info;
    }
}
