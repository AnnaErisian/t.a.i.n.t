package blue.thejester.taint.traits.wands;

import electroblob.wizardry.constants.Element;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Elementalist extends AbstractTrait implements IWandTrait {

    public static Elementalist elementalist = new Elementalist("wand_elementalist", 0x28962f);

    public Elementalist(String identifier, int color) {
        super(identifier, color);
    }
    @Override
    public int modifyBlastLevel(int level, Element element) {
        if(element == Element.FIRE || element == Element.ICE || element == Element.EARTH || element == Element.LIGHTNING) {
            return level + 1;
        }
        return level;
    }

    @Override
    public int modifyCooldownLevel(int level, Element element) {
        if(element == Element.FIRE || element == Element.ICE || element == Element.EARTH || element == Element.LIGHTNING) {
            return level + 1;
        }
        return level;
    }

    @Override
    public int modifyDurationLevel(int level, Element element) {
        if(element == Element.FIRE || element == Element.ICE || element == Element.EARTH || element == Element.LIGHTNING) {
            return level + 1;
        }
        return level;
    }

    @Override
    public int modifyRangeLevel(int level, Element element) {
        if(element == Element.FIRE || element == Element.ICE || element == Element.EARTH || element == Element.LIGHTNING) {
            return level + 1;
        }
        return level;
    }
}
