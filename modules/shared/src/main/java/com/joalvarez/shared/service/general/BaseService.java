package com.joalvarez.shared.service.general;

import com.joalvarez.shared.data.dao.general.GenericDAO;
import com.joalvarez.shared.data.dto.general.BaseDTO;
import com.joalvarez.shared.data.mapper.general.GenericMapper;
import com.joalvarez.shared.LoggerHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class BaseService<D extends GenericDAO<E, K>, M extends GenericMapper<O, E>, O extends BaseDTO, E, K>
	implements GenericService<O, K>, LoggerHelper {

    protected final D dao;
    protected final M mapper;

    public BaseService(D dao, M mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<O> getAll() {
        return this.mapper.toDTOs(this.dao.findAll());
    }

    @Override
    public Optional<O> findById(K id) {
        return this.dao.findById(id)
                .map(this.mapper::toDTO);
    }

	@Override
	public O getById(K id) {
		return this.findById(id).orElse(null);
	}

    @Override
    public O save(O entity) {
        return this.mapper.toDTO(
                this.dao.save(
                        this.mapper.fromDTO(entity)
                )
        );
    }

    @Override
    public O update(O entity) {
        return this.save(entity);
    }

    @Override
    public void deleteById(K id) {
        this.dao.deleteById(id);
    }

	@Override
	public List<O> saveAll(List<O> dtos) {
		return this.mapper.toDTOs(
				this.dao.saveAll(
					this.mapper.fromDTOs(dtos)
					)
				);
	}

	@Override
	public Page<O> getPaginatedWithFilters(Pageable pageable, Map<String, String> filters) {
		filters.keySet().retainAll(this.validFields());

		return this.dao.getPaginatedWithFilters(pageable, filters)
			.map(this.mapper::toDTO);
	}

	protected abstract Set<String> validFields();
}