package com.joalvarez.shared.data.repository.general;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E, K> extends JpaRepository<E, K>, JpaSpecificationExecutor<E> {}