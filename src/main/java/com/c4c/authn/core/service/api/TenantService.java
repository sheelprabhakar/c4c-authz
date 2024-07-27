package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.TenantEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * The interface Tenant service.
 */
public interface TenantService {
    /**
     * Create tenant entity.
     *
     * @param map the map
     * @return the tenant entity
     */
    TenantEntity create(TenantEntity map);

    /**
     * Update tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    TenantEntity update(TenantEntity tenantEntity);

    /**
     * Find by id tenant entity.
     *
     * @param tenantId the tenant id
     * @return the tenant entity
     */
    TenantEntity findById(UUID tenantId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TenantEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<TenantEntity> findByPagination(int pageNo, int pageSize);

    /**
     * Delete by id.
     *
     * @param tenantId the tenant id
     */
    void deleteById(UUID tenantId);

    /**
     * Delete all by id.
     *
     * @param tenantIds the tenant ids
     */
    void deleteAllById(List<UUID> tenantIds);
}
