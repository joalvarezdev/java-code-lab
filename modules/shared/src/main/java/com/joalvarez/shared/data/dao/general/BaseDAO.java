package com.joalvarez.shared.data.dao.general;

import com.joalvarez.shared.data.repository.general.GenericRepository;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseDAO<R extends GenericRepository<E, K>, E, K> implements GenericDAO<E, K> {

	protected final R repository;

	public BaseDAO(R repository) {
		this.repository = repository;
	}

	@Override
	public Optional<E> findById(K id) {
		return this.repository.findById(id);
	}

	@Override
	public List<E> findAll() {
		return this.repository.findAll();
	}

	@Override
	public E update(E entity) {
		return this.repository.save(entity);
	}

	@Override
	public E save(E entity) {
		return this.repository.save(entity);
	}

	@Override
	public void deleteById(K id) {
		this.repository.deleteById(id);
	}

	@Override
	public List<E> saveAll(List<E> entities) {
		return this.repository.saveAll(entities);
	}

	@Override
	public Page<E> getPaginatedWithFilters(Pageable pageable, Map<String, String> filters) {
		return this.repository.findAll(this.buildFromMap(filters), pageable);
	}

	protected Specification<E> buildFromMap(Map<String, String> filters) {
		final List<Specification<E>> specifications = filters.entrySet().stream()
			.filter(entry -> this.filterProcessors().containsKey(entry.getKey()))
			.filter(entry -> StringUtils.isNotBlank(entry.getValue()))
			.map(entry -> this.filterProcessors().get(entry.getKey()).apply(entry.getValue()))
			.filter(Objects::nonNull)
			.toList();

		return specifications.isEmpty()
			? null
			: Specification.allOf(specifications);
	}

	protected abstract Map<String, Function<String, Specification<E>>> filterProcessors();
}