package blue.thejester.taint.tools;

import blue.thejester.taint.modules.Tools;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ToolNBT;

import java.util.List;

public class ToolStaff extends ToolWand {
    public ToolStaff() {
        super("staff",
                new PartMaterialType(Tools.wandGem, WandGemMaterialStats.TYPE),
                new PartMaterialType(Tools.wandGem, WandGemMaterialStats.TYPE),
                new PartMaterialType(Tools.wandSocket, WandSocketMaterialStats.TYPE),
                new PartMaterialType(Tools.wandCore, WandCoreMaterialStats.TYPE),
                new PartMaterialType(Tools.wandCore, WandCoreMaterialStats.TYPE));
    }

    @Override
    protected float cooldownFactor() {
        return 2.5f;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        WandNBT data = new WandNBT();

        WandGemMaterialStats wandGem1 = materials.get(0).getStatsOrUnknown(WandGemMaterialStats.TYPE);
        WandGemMaterialStats wandGem2 = materials.get(1).getStatsOrUnknown(WandGemMaterialStats.TYPE);
        WandSocketMaterialStats wandSocket = materials.get(2).getStatsOrUnknown(WandSocketMaterialStats.TYPE);
        WandCoreMaterialStats wandCore1 = materials.get(3).getStatsOrUnknown(WandCoreMaterialStats.TYPE);
        WandCoreMaterialStats wandCore2 = materials.get(4).getStatsOrUnknown(WandCoreMaterialStats.TYPE);

        data.gemFirst(wandGem1, wandGem2);
        data.socket(wandSocket);
        data.core(wandCore1, wandCore2);
        data.gemLast(wandGem1, wandGem2);

        // 3 free modifiers
        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }
}
