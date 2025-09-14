package com.joalvarez.shared.data.mapper.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import com.joalvarez.shared.exception.GenericException;
import com.joalvarez.shared.LoggerHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BaseMapper<O extends Serializable, E>
	implements GenericMapper<O, E>, LoggerHelper {

    private Class<O> dtoClass;
    private Class<E> domainClass;
    protected final ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public BaseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        final Class<?>[] arguments = GenericTypeResolver.resolveTypeArguments(this.getClass(), BaseMapper.class);
        if (Objects.nonNull(arguments) && arguments.length > 0) {
            this.dtoClass = (Class<O>) arguments[0];
            this.domainClass = (Class<E>) arguments[1];
        }
    }

    public O toDTO(E entity) {
        return this.objectMapper.convertValue(entity, this.dtoClass);
    }

    public List<O> toDTOs(List<E> entities) {
		return entities.stream()
				   .map(this::toDTO)
				   .toList();
    }

    public E fromDTO(O entity) {
        return this.objectMapper.convertValue(entity, this.domainClass);
    }

	public List<E> fromDTOs(List<O> entities) {
		return entities.stream()
			.map(this::fromDTO)
			.toList();
	}

	public String objectToJson(Object obj) {
		try {
			return this.objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			this.warn("Error to parse {} to json", obj);
			throw new GenericException(
				HttpStatus.BAD_REQUEST,
				1000,
				obj.toString()
			);
		}
	}
}