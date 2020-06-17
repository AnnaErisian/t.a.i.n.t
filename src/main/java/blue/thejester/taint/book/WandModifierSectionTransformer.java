package blue.thejester.taint.book;

import blue.thejester.taint.traits.modifier.IWandModifier;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.ContentListingSectionTransformer;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class WandModifierSectionTransformer extends ContentListingSectionTransformer {

    public WandModifierSectionTransformer() {
        super("modifiers");
    }


    @Override
    protected void processPage(BookData book, ContentListing listing, PageData page) {
        if(page.content instanceof WandModifierContent) {
            IModifier modifier = TinkerRegistry.getModifier(((WandModifierContent) page.content).modifierName);
            if(modifier != null) {
                listing.addEntry(modifier.getLocalizedName(), page);
            }
        }
    }
}
