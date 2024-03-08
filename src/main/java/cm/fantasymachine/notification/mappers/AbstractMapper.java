package cm.fantasymachine.notification.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<D, E>{

    public abstract D toDomainObject(E entity);
    public abstract E toEntityObject(D domainObject);

    public final List<D> toDomainObjectList(Collection<E> entities){

        return entities.stream()
                .map(this::toDomainObject)
                .collect(Collectors.toList());
    }

    public final List<E> toEntityList(Collection<D> objects){

        return objects.stream()
                .map(this::toEntityObject)
                .collect(Collectors.toList());
    }


}
