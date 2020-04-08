package blue.thejester.taint.traits;

public class PraiseTheSun extends DaytimeTrait {

	public static final DaytimeTrait praiseit = new PraiseTheSun("praisethesun", 0xffffff, 6000);

    public PraiseTheSun(String name, int color, int besttime) {
        super(name, color, besttime);
    }
}
