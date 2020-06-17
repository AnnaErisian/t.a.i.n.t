package blue.thejester.taint.book;

import blue.thejester.taint.modules.Tools;
import blue.thejester.taint.tools.WandCoreMaterialStats;
import blue.thejester.taint.tools.WandGemMaterialStats;
import blue.thejester.taint.tools.WandSocketMaterialStats;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementItem;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockCasting;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.block.BlockToolTable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class WandMaterialContent extends ContentMaterial {

    public static final String ID = "wandmaterial";

    private transient Material material;

    public WandMaterialContent(Material material) {
        super(material);
        this.material = material;
    }

    @Override
    public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {

        addTitle(list, material.getLocalizedNameColored(), true);

        //The armor displayed on the sides
        addDisplayItems(list, rightSide ? GuiBook.PAGE_WIDTH - 18 : 0);

        int col_margin = 12;
        int top = 8;
        int left = rightSide ? 0 : col_margin;

        int y = top + 10;
        int x = left + 10;
        int w = GuiBook.PAGE_WIDTH / 2 - 10;

        LinkedHashSet<ITrait> allTraits = new LinkedHashSet<>();

        addStatsDisplay(x, y, w, list, allTraits, WandGemMaterialStats.TYPE);
        addStatsDisplay(x + w, y, w - 10, list, allTraits, WandCoreMaterialStats.TYPE);

        y += 55 + 10 * material.getAllTraitsForStats(WandGemMaterialStats.TYPE).size();
        addStatsDisplay(x, y, w - 0, list, allTraits, WandSocketMaterialStats.TYPE);

        String flavour = parent.parent.parent.strings.get(String.format("%s.flavour", material.getIdentifier()));

        if (flavour != null) {
            TextData flavourData = new TextData("\"" + flavour + "\"");
            flavourData.italic = true;
            list.add(new ElementText(x + w, y, w - 16, 60, flavourData));
        }
    }

    private void addStatsDisplay(int x, int y, int w, ArrayList<BookElement> list, LinkedHashSet<ITrait> allTraits, String stattype) {

        IMaterialStats stats = material.getStats(stattype);

        if(stats == null) {
            return;
        }

        List<ITrait> traits = material.getAllTraitsForStats(stats.getIdentifier());
        allTraits.addAll(traits);

        List<ItemStack> parts = Lists.newLinkedList();
        for(IToolPart part : TinkerRegistry.getToolParts()) {
            if(part.hasUseForStat(stats.getIdentifier())) {
                parts.add(part.getItemstackWithMaterial(material));
            }
        }

        if(parts.size() > 0) {
            ElementItem display = new ElementTinkerItem(x, y + 1, 0.5f, parts);
            list.add(display);
        }

        ElementText name = new ElementText(x + 10, y, w - 10, 10, stats.getLocalizedName());
        name.text[0].underlined = true;
        list.add(name);
        y += 12;

        List<TextData> lineData = Lists.newArrayList();
        lineData.addAll(getStatLines(stats));
        lineData.addAll(getTraitLines(traits, material));

        list.add(new ElementText(x, y, w, GuiBook.PAGE_HEIGHT, lineData));
    }

    public static List<TextData> getStatLines(IMaterialStats stats) {
        List<TextData> lineData = new ArrayList<>();
        for(int i = 0; i < stats.getLocalizedInfo().size(); i++) {
            TextData text = new TextData(stats.getLocalizedInfo().get(i));
            String line = stats.getLocalizedDesc().get(i);
            text.tooltip = LocUtils.convertNewlines(line).split("\n");
            lineData.add(text);
            lineData.add(new TextData("\n"));
        }
        return lineData;
    }

    private void addDisplayItems(ArrayList<BookElement> list, int x) {

        List<ElementItem> displayArmor = Lists.newArrayList();

        int y = 10;

        if(!material.getRepresentativeItem().isEmpty()) {
            displayArmor.add(new ElementTinkerItem(material.getRepresentativeItem()));
        }

        if(material.isCraftable()) {
            ItemStack partbuilder = new ItemStack(TinkerTools.toolTables, 1, BlockToolTable.TableTypes.PartBuilder.meta);
            ElementItem elementItem = new ElementTinkerItem(partbuilder);
            elementItem.tooltip = ImmutableList.of(parent.translate("material.craft_partbuilder"));
            displayArmor.add(elementItem);
        }

        if(material.isCastable()) {
            ItemStack basin = new ItemStack(TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.BASIN.getMeta());
            ElementItem elementItem = new ElementTinkerItem(basin);
            String text = parent.translate("material.craft_casting");
            elementItem.tooltip = ImmutableList.of(String.format(text, material.getFluid().getLocalizedName(new FluidStack(material.getFluid(), 0))));
            displayArmor.add(elementItem);
        }

        ToolCore[] wandTypes = new ToolCore[]{Tools.wand, Tools.staff};

        for (ToolCore wand : wandTypes) {
            if (wand == null) {
                continue;
            }
            ImmutableList.Builder<Material> builder = ImmutableList.builder();
            for (int i = 0; i < wand.getRequiredComponents().size(); i++) {
                builder.add(material);
            }
            ItemStack builtArmor = wand.buildItem(builder.build());
            if (wand.hasValidMaterials(builtArmor)) {
                displayArmor.add(new ElementTinkerItem(builtArmor));
            }
            if (displayArmor.size() == 9) {
                break;
            }
        }

        if(!displayArmor.isEmpty()) {
            for(ElementItem element : displayArmor) {
                element.x = x;
                element.y = y;
                element.scale = 1f;
                y += ElementItem.ITEM_SIZE_HARDCODED;
                list.add(element);
            }
        }
    }
}
