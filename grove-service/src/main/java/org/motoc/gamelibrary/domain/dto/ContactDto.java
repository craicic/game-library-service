package org.motoc.gamelibrary.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @Size(max = 50, message = "Postal code cannot exceed 50 characters")
    private String postCode;

    @Size(max = 255, message = "Street cannot exceed 255 characters")
    private String street;

    @Size(max = 255, message = "City cannot exceed 255 characters")
    private String city;

    @Size(max = 50, message = "Street number cannot exceed 50 characters")
    private String houseNumber;

    @Size(max = 255, message = "Country cannot exceed 255 characters")
    @NotBlank(message = "Country cannot be null or blank")
    private String country;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    private String phoneNumber;

    @Size(max = 255, message = "Website cannot exceed 255 characters")
    private String website;

    @Size(max = 320, message = "Mail address cannot exceed 320 characters")
    private String mailAddress;

}
