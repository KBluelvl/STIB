package model.dto;

import java.util.Objects;

public class Dto<K> {
    protected K key;

    public Dto(K key){
        this.key = key;
    }

    public K getKey(){
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dto<?> dto = (Dto<?>) o;
        return Objects.equals(key, dto.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
