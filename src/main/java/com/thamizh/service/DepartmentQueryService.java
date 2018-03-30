package com.thamizh.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.thamizh.domain.Department;
import com.thamizh.domain.*; // for static metamodels
import com.thamizh.repository.DepartmentRepository;
import com.thamizh.service.dto.DepartmentCriteria;

import com.thamizh.service.dto.DepartmentDTO;
import com.thamizh.service.mapper.DepartmentMapper;

/**
 * Service for executing complex queries for Department entities in the database.
 * The main input is a {@link DepartmentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepartmentDTO} or a {@link Page} of {@link DepartmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentQueryService extends QueryService<Department> {

    private final Logger log = LoggerFactory.getLogger(DepartmentQueryService.class);


    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentQueryService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Return a {@link List} of {@link DepartmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findByCriteria(DepartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Department> specification = createSpecification(criteria);
        return departmentMapper.toDto(departmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepartmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findByCriteria(DepartmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Department> specification = createSpecification(criteria);
        final Page<Department> result = departmentRepository.findAll(specification, page);
        return result.map(departmentMapper::toDto);
    }

    /**
     * Function to convert DepartmentCriteria to a {@link Specifications}
     */
    private Specifications<Department> createSpecification(DepartmentCriteria criteria) {
        Specifications<Department> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Department_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Department_.name));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Department_.city));
            }
        }
        return specification;
    }

}
