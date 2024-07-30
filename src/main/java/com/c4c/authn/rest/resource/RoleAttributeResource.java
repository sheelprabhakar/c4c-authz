package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Role attribute resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class RoleAttributeResource extends CommonResourceAttributes implements Serializable {

    /**
     * The Role id.
     */
    @NotNull(message = "Role Id can not be null.")
    private UUID roleId;

    /**
     * The Attribute id.
     */
    @NotNull(message = "Attribute Id can not be null.")
    private UUID attributeId;

    /**
     * The Attribute resource.
     */
    private AttributeResource attributeResource;
    /**
     * The Role resource.
     */
    private RoleResource roleResource;
}
