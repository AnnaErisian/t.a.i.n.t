package blue.thejester.taint.traits.wands;

import electroblob.wizardry.constants.Element;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import electroblob.wizardry.constants.Element;

public class BasicWandTrait extends AbstractTrait implements IWandTrait {

    private static final int COOLDOWN = 1;
    private static final int DURATION = 2;
    private static final int RANGE = 4;
    private static final int BLAST = 8;

    public static BasicWandTrait blueFlame = new BasicWandTrait("wand_blue_flame", 0xb20a0a, Element.FIRE, COOLDOWN);
    public static BasicWandTrait inferno = new BasicWandTrait("wand_inferno", 0xb20a0a, Element.FIRE, DURATION);
    public static BasicWandTrait flare = new BasicWandTrait("wand_flare", 0xb20a0a, Element.FIRE, RANGE);
    public static BasicWandTrait heckfire = new BasicWandTrait("wand_heckfire", 0xb20a0a, Element.FIRE, BLAST);
    public static BasicWandTrait archdemon = new BasicWandTrait("wand_archdemon", 0xb20a0a, Element.FIRE);

    public static BasicWandTrait flashFreeze = new BasicWandTrait("wand_flash_freeze", 0x74e1ed, Element.ICE, COOLDOWN);
    public static BasicWandTrait glacial = new BasicWandTrait("wand_glacial", 0x74e1ed, Element.ICE, DURATION);
    public static BasicWandTrait heatsink = new BasicWandTrait("wand_heatsink", 0x74e1ed, Element.ICE, RANGE);
    public static BasicWandTrait blizzard = new BasicWandTrait("wand_blizzard", 0x74e1ed, Element.ICE, BLAST);
    public static BasicWandTrait antarctic = new BasicWandTrait("wand_antarctic", 0x74e1ed, Element.ICE);

    public static BasicWandTrait ampere = new BasicWandTrait("wand_ampere", 0x00fffa, Element.LIGHTNING, COOLDOWN);
    public static BasicWandTrait ohm = new BasicWandTrait("wand_ohm", 0x00fffa, Element.LIGHTNING, DURATION);
    public static BasicWandTrait voltaic = new BasicWandTrait("wand_voltaic", 0x00fffa, Element.LIGHTNING, RANGE);
    public static BasicWandTrait fulgur = new BasicWandTrait("wand_fulgur", 0x00fffa, Element.LIGHTNING, BLAST);
    public static BasicWandTrait lightningRod = new BasicWandTrait("wand_lightning_rod", 0x00fffa, Element.LIGHTNING);

    public static BasicWandTrait lapine = new BasicWandTrait("wand_lapine", 0x28962f, Element.EARTH, COOLDOWN);
    public static BasicWandTrait carbon = new BasicWandTrait("wand_carbon", 0x28962f, Element.EARTH, DURATION);
    public static BasicWandTrait newWorld = new BasicWandTrait("wand_new_world", 0x28962f, Element.EARTH, RANGE);
    public static BasicWandTrait quake = new BasicWandTrait("wand_quake", 0x28962f, Element.EARTH, BLAST);
    public static BasicWandTrait stonemason = new BasicWandTrait("wand_stonemason", 0x28962f, Element.EARTH);

    public static BasicWandTrait needler = new BasicWandTrait("wand_needler", 0x28962f, Element.NECROMANCY, COOLDOWN);
    public static BasicWandTrait eternalSleep = new BasicWandTrait("wand_eternal_sleep", 0x28962f, Element.NECROMANCY, DURATION);
    public static BasicWandTrait apocrypha = new BasicWandTrait("wand_apocrypha", 0x28962f, Element.NECROMANCY, RANGE);
    public static BasicWandTrait plague = new BasicWandTrait("wand_plague", 0x28962f, Element.NECROMANCY, BLAST);
    public static BasicWandTrait necropolis = new BasicWandTrait("wand_necropolis", 0x28962f, Element.NECROMANCY);

    public static BasicWandTrait accelerator = new BasicWandTrait("wand_accelerator", 0x28962f, Element.SORCERY, COOLDOWN);
    public static BasicWandTrait coldSteel = new BasicWandTrait("wand_coldSteel", 0x28962f, Element.SORCERY, DURATION);
    public static BasicWandTrait farcaster = new BasicWandTrait("wand_farcaster", 0x28962f, Element.SORCERY, RANGE);
    public static BasicWandTrait regal = new BasicWandTrait("wand_regal", 0x28962f, Element.SORCERY, BLAST);
    public static BasicWandTrait madness = new BasicWandTrait("wand_madness", 0x28962f, Element.SORCERY);

    public static BasicWandTrait firstAid = new BasicWandTrait("wand_firstAid", 0x28962f, Element.HEALING, COOLDOWN);
    public static BasicWandTrait chronic = new BasicWandTrait("wand_chronic", 0x28962f, Element.HEALING, DURATION);
    public static BasicWandTrait borderless = new BasicWandTrait("wand_borderless", 0x28962f, Element.HEALING, RANGE);
    public static BasicWandTrait aerosol = new BasicWandTrait("wand_aerosol", 0x28962f, Element.HEALING, BLAST);
    public static BasicWandTrait doctor = new BasicWandTrait("wand_doctor", 0x28962f, Element.HEALING);

    public static BasicWandTrait turbocaster = new BasicWandTrait("turbocaster", 0x28962f, COOLDOWN);
    public static BasicWandTrait adherent = new BasicWandTrait("wand_adherent", 0x28962f, DURATION);
    public static BasicWandTrait longshot = new BasicWandTrait("wand_longshot", 0x28962f, RANGE);
    public static BasicWandTrait enhancer = new BasicWandTrait("wand_enhancer", 0x28962f);

    private Element element;
    private int effect;
    private boolean allElement;

    public BasicWandTrait(String identifier, int color, Element element, int effect) {
        super(identifier, color);
        this.element = element;
        this.effect = effect;
    }

    public BasicWandTrait(String identifier, int color, int effect) {
        super(identifier, color);
        this.effect = effect;
        this.allElement = true;
    }

    public BasicWandTrait(String identifier, int color, Element element) {
        super(identifier, color);
        this.element = element;
        effect = COOLDOWN | DURATION | RANGE | BLAST;
    }

    public BasicWandTrait(String identifier, int color) {
        super(identifier, color);
        this.allElement = true;
        effect = COOLDOWN | DURATION | RANGE | BLAST;
    }

    @Override
    public int modifyBlastLevel(int level, Element element) {
        if(allElement || this.element == element) {
            if((this.effect & BLAST) != 0) {
                return level + 1;
            }
        }
        return level;
    }

    @Override
    public int modifyCooldownLevel(int level, Element element) {
        if(allElement || this.element == element) {
            if((this.effect & COOLDOWN) != 0) {
                return level + 1;
            }
        }
        return level;
    }

    @Override
    public int modifyDurationLevel(int level, Element element) {
        if(allElement || this.element == element) {
            if((this.effect & DURATION) != 0) {
                return level + 1;
            }
        }
        return level;
    }

    @Override
    public int modifyRangeLevel(int level, Element element) {
        if(allElement || this.element == element) {
            if((this.effect & RANGE) != 0) {
                return level + 1;
            }
        }
        return level;
    }
}
