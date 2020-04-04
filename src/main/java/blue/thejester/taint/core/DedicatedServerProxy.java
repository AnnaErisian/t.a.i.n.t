package blue.thejester.taint.core;

public class DedicatedServerProxy extends CommonProxy {

    @Override
    public boolean isDedicatedServer() {
        return true;
    }
}
