package blue.thejester.taint.modules;

import blue.thejester.taint.Taint;
import slimeknights.tconstruct.library.materials.Material;

import java.util.LinkedHashSet;
import java.util.Set;

public interface IModule {

    Set<IModule> modules = new LinkedHashSet();

    void init();

    void initLate();

    default void setRenderInfo(Material mat, int color) {
        if(!Taint.proxy.isDedicatedServer()) {
            mat.setRenderInfo(color);
        }
    }

}
