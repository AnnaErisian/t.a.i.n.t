package blue.thejester.taint.traits;

import landmaster.plustic.traits.DeathSaveTrait;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

public class MasterOfTheUniverse extends DeathSaveTrait {
        public static final MasterOfTheUniverse master_of_the_universe = new MasterOfTheUniverse();

        public MasterOfTheUniverse() {
            super("master_of_the_universe", 0xffffff, 1, (stack) -> {
                return !stack.isEmpty() && ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("boneDragon"));},
                    "msg.taint.mastermodifier.use");
        }
    }