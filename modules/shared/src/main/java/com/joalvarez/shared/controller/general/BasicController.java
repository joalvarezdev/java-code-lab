package com.joalvarez.shared.controller.general;

import com.joalvarez.shared.data.dao.general.GenericDAO;
import com.joalvarez.shared.data.dto.general.BaseDTO;
import com.joalvarez.shared.data.mapper.general.GenericMapper;
import com.joalvarez.shared.service.general.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public class BasicController<S extends BaseService<D, M, O, E, K>, D extends GenericDAO<E, K>, M extends GenericMapper<O, E>, O extends BaseDTO, E, K> implements GenericController<K, O, S>{

    protected final S service;

    public BasicController(S service) {
        this.service = service;
    }

	@Override
	public List<O> getAll() {
		return this.service.getAll();
	}

	@Override
	public O get(K id) {
		return this.service.getById(id);
	}

	@Override
	public O save(O entity) {
		return this.service.save(entity);
	}

	@GetMapping("filters")
	@Override
	public Page<O> getAllPaginated(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "20") int size,
		@RequestParam(required = false, defaultValue = "id") String sortBy,
		@RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
		@RequestParam Map<String, String> filters
		) {
		return this.service.getPaginatedWithFilters(PageRequest.of(page, size, Sort.by(sortDirection, sortBy)), filters);
	}
}
