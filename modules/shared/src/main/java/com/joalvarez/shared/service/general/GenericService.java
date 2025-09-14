package com.joalvarez.shared.service.general;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericService<O, K> {

    List<O> getAll();

    Optional<O> findById(K id);

	O getById(K id);

    O save(O entity);

    O update(O entity);

    void deleteById(K id);

	List<O> saveAll(List<O> entities);

	Page<O> getPaginatedWithFilters(Pageable pageable, Map<String, String> filters);
}