package io.github.alberes.guestsjpads.controllers.mapper;

import io.github.alberes.guestsjpads.controllers.dto.GuestDto;
import io.github.alberes.guestsjpads.domains.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(source = "legalEntityNumber", target = "legalEntityNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthday", target = "birthday")
    Guest toEntity(GuestDto dto);

    @Mapping(source = "legalEntityNumber", target = "legalEntityNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthday", target = "birthday")
    GuestDto toDto(Guest guest);
}
