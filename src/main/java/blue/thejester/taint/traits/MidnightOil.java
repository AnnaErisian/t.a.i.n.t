package blue.thejester.taint.traits;

public class MidnightOil extends DaytimeTrait {

	public static final DaytimeTrait midnightoil = new MidnightOil("midnight_oil", 0xffffff, 18000);

    public MidnightOil(String name, int color, int besttime) {
        super(name, color, besttime);
    }
}
