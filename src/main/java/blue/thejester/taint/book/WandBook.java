package blue.thejester.taint.book;

import blue.thejester.taint.Taint;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.BookTransformer;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;

@SideOnly(Side.CLIENT)
public class WandBook extends BookData {

    public final static BookData INSTANCE = BookLoader.registerBook(Taint.MODID, false, false);

    public static void init() {

        BookLoader.registerPageType(WandMaterialContent.ID, WandMaterialContent.class);
        INSTANCE.addRepository(new FileRepository(new ResourceLocation(Taint.MODID, "wand_book").toString()));
        INSTANCE.addTransformer(new WandMaterialSectionTransformer());
        INSTANCE.addTransformer(BookTransformer.IndexTranformer());
    }
}
