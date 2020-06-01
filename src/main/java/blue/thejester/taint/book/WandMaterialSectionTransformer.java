package blue.thejester.taint.book;

import blue.thejester.taint.tools.WandCoreMaterialStats;
import blue.thejester.taint.tools.WandGemMaterialStats;
import blue.thejester.taint.tools.WandPartMaterialStats;
import blue.thejester.taint.tools.WandSocketMaterialStats;
import slimeknights.mantle.client.book.data.content.PageContent;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.sectiontransformer.AbstractMaterialSectionTransformer;
import slimeknights.tconstruct.library.materials.Material;

public class WandMaterialSectionTransformer extends AbstractMaterialSectionTransformer {

    public WandMaterialSectionTransformer() {
        super("materials");
    }

    @Override
    protected boolean isValidMaterial(Material material) {
        return material.hasStats(WandSocketMaterialStats.TYPE)
                || material.hasStats(WandGemMaterialStats.TYPE)
                || material.hasStats(WandCoreMaterialStats.TYPE);
    }

    @Override
    protected PageContent getPageContent(Material material) {
        return new WandMaterialContent(material);
    }
}
