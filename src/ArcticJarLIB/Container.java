package ArcticJarLIB;

import java.util.LinkedHashMap;
import java.util.Optional;

public interface Container<K, V> {

    public LinkedHashMap<K, V> getContainer();

    public default Object put(K targetKey, V targetObject) {
        getContainer().put(targetKey, targetObject);
        return getContainer().get(targetKey);
    }

    public Object add(K targetKey, V targetObject);

    public default Optional<Object> get(K targetKey) {
        try {

            return Optional.of(getContainer().get(targetKey));

        } catch (Exception e) { return Optional.empty(); }
    }

}
