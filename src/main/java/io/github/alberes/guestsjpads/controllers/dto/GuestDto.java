package io.github.alberes.guestsjpads.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record GuestDto(
        @NotBlank(message = "Obligatory field")
        @Size(min = 11, max = 11, message = "Fill this field with size 11")
        String legalEntityNumber,
        @NotBlank(message = "Obligatory field")
        @Size(min = 10, max = 100, message = "Fill this field with size between 10 and 100")
        String name,
        @NotNull(message = "Fill this field")
        LocalDate birthday) {
}
