package com.joalvarez.shared.data.mapper.general;

import java.io.Serializable;
import java.util.List;

public interface GenericMapper <T extends Serializable, D>{

    T toDTO(D entity);

    D fromDTO(T entity);

    List<T> toDTOs(List<D> entities);

    List<D> fromDTOs(List<T> entities);
}