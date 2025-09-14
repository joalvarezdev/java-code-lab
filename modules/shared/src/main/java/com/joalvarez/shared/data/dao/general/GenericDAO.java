package com.joalvarez.shared.data.dao.general;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericDAO<E, K> {

    Optional<E> findById(K id);

    List<E> findAll();

    E update(E entity);

    E save(E entity);

    void deleteById(K id);

	List<E> saveAll(List<E> entities);

	Page<E> getPaginatedWithFilters(Pageable pageable, Map<String, String> filters);
}