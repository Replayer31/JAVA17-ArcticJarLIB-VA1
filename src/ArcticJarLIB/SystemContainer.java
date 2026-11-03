package ArcticJarLIB;

import java.util.LinkedHashMap;
import java.util.Optional;

public class SystemContainer implements Container<SystemContainer.Target, AJSystem> {

    public enum Target {
        APP,
        VOX,
        GRN,
        CFG
    }

    private LinkedHashMap<Target, AJSystem> Container;

    public SystemContainer() {
        this.Container = new LinkedHashMap<>();
    }

    @Override
    public LinkedHashMap<Target, AJSystem> getContainer() { return Container; }

    @Override
    public Object add(Target targetKey, AJSystem system) {
        switch (system.getClass().getSimpleName().toLowerCase()) {
            case "app" -> { Container.put(Target.APP, system); return Container.get(Target.APP); }
            case "voxsystem" -> { Container.put(Target.VOX, system); return Container.get(Target.VOX); }
            case "grnsystem" -> { Container.put(Target.GRN, system); return Container.get(Target.GRN); }
            case "cfgmanager" -> { Container.put(Target.CFG, system); return Container.get(Target.CFG); }
            default -> throw new IllegalStateException("Unexpected value: " + system.getClass().getSimpleName());
        }
    }

}
