package com.pribavkindenis.requesthub.mapper;

public interface Mapper<E, D> {

    D entityToDto(E entity);
    E dtoToEntity(D dto);
    void updateEntity(E entity, D dto);

}
