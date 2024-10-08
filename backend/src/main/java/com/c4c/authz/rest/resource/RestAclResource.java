package com.c4c.authz.rest.resource;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Rest acl resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class RestAclResource extends CommonResourceAttributes implements Serializable {

  /**
   * The Id.
   */
  private UUID id;

  /**
   * The Tenant id.
   */
  private UUID tenantId;
  /**
   * The Name.
   */
  @NotEmpty(message = "Rest resource name can not be empty.")
  @Size(max = 50, message = "Rest resource name should be less than 50 characters.")
  private String name;

  /**
   * The Path.
   */
  @NotEmpty(message = "Rest resource path can not be empty.")
  @Size(max = 4098, message = "Rest resource path should be less than 4098 characters.")
  private String path;
}
