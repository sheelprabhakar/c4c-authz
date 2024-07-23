package com.c4c.authn.adapter.api;

import com.c4c.authn.rest.resource.RestResource;
import com.c4c.authn.rest.resource.TenantResource;
import com.c4c.authn.rest.resource.UserResource;
import com.c4c.authn.rest.resource.auth.JwtRequest;
import com.c4c.authn.rest.resource.auth.JwtResponse;
import com.c4c.authn.rest.resource.lookup.CityResource;
import com.c4c.authn.rest.resource.lookup.CountryResource;
import com.c4c.authn.rest.resource.lookup.StateResource;
import java.util.List;
import java.util.UUID;

/**
 * The interface Rest adapter v 1.
 */
public interface RestAdapterV1 {
  /**
   * Save user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  UserResource save(UserResource userResource);

  /**
   * Update user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  UserResource update(UserResource userResource);

  /**
   * Authenticate jwt response.
   *
   * @param request the request
   * @return the jwt response
   */
  JwtResponse authenticate(JwtRequest request);

  /**
   * Logout.
   */
  void logout();

  /**
   * Refresh token jwt response.
   *
   * @param refreshToken the refresh token
   * @return the jwt response
   */
  JwtResponse refreshToken(String refreshToken);

  /**
   * Countries list.
   *
   * @return the list
   */
  List<CountryResource> countries();

  /**
   * States list.
   *
   * @param countryId the country id
   * @return the list
   */
  List<StateResource> states(int countryId);

  /**
   * Cities list.
   *
   * @param stateId the state id
   * @return the list
   */
  List<CityResource> cities(int stateId);

  /**
   * Create tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  TenantResource createTenant(TenantResource tenantResource);

  /**
   * Update tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  TenantResource updateTenant(TenantResource tenantResource);

  /**
   * Read tenant tenant resource.
   *
   * @param tenantId the tenant id
   * @return the tenant resource
   */
  TenantResource readTenant(UUID tenantId);

  /**
   * Read tenants list.
   *
   * @return the list
   */
  List<TenantResource> readTenants();

  /**
   * Delete tenant.
   *
   * @param tenantId the tenant id
   */
  void deleteTenant(UUID tenantId);

  /**
   * Create rest resource rest resource.
   *
   * @param restResource the rest resource
   * @return the rest resource
   */
  RestResource createRestResource(RestResource restResource);
}
