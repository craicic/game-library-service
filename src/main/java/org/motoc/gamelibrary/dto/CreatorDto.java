package org.motoc.gamelibrary.dto;

import lombok.*;
import org.motoc.gamelibrary.model.enumeration.CreatorRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto for creator
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorDto {


    private long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Role cannot be null")
    private CreatorRole role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ContactDto contact;

}