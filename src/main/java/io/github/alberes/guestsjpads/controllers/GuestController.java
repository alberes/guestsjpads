package io.github.alberes.guestsjpads.controllers;

import io.github.alberes.guestsjpads.controllers.dto.GuestDto;
import io.github.alberes.guestsjpads.controllers.mapper.GuestMapper;
import io.github.alberes.guestsjpads.domains.Guest;
import io.github.alberes.guestsjpads.services.DRGuestService;
import io.github.alberes.guestsjpads.services.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController implements GenericController{

    private final GuestService service;

    private final DRGuestService drService;

    private final GuestMapper mapper;

    @PostMapping
    public ResponseEntity<GuestDto> save(@RequestBody @Valid GuestDto dto){
        Guest guest = this.mapper.toEntity(dto);
        guest = this.service.save(guest);
        this.drService.save(guest);
        return ResponseEntity.created(
                this.createURI("/{legalEntityNumber}", guest.getLegalEntityNumber()))
                .build();
    }

    @GetMapping("/{legalEntityNumber}")
    public ResponseEntity<GuestDto> find(@PathVariable String legalEntityNumber){
        Guest guest = this.service.find(legalEntityNumber);
        GuestDto dto = this.mapper.toDto(guest);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> update(
            @PathVariable String legalEntityNumber,
            @RequestBody GuestDto dto){
        Guest guest = this.mapper.toEntity(dto);
        guest.setLegalEntityNumber(legalEntityNumber);
        this.service.update(guest);
        this.drService.update(guest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> delete(@PathVariable String legalEntityNumber){
        this.service.delete(legalEntityNumber);
        this.drService.delete(legalEntityNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<GuestDto>> page(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ){
        Page<Guest> pageGuests = this.service.findPage(page, linesPerPage, orderBy, direction);

        if(pageGuests.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        Page<GuestDto> guests = pageGuests
                .map(this.mapper::toDto);
        return ResponseEntity.ok(guests);
    }

}
