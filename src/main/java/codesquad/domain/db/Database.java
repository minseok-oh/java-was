package codesquad.domain.db;

import java.util.Map;

public interface Database<K, V> {
    K append(V data);
    V getById(K id);
    Map<K, V> getAll();
    void deleteById(K id);
}
