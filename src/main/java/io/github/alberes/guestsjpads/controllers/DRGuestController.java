package io.github.alberes.guestsjpads.controllers;

import io.github.alberes.guestsjpads.controllers.dto.GuestDto;
import io.github.alberes.guestsjpads.controllers.mapper.GuestMapper;
import io.github.alberes.guestsjpads.domains.Guest;
import io.github.alberes.guestsjpads.services.DRGuestService;
import io.github.alberes.guestsjpads.services.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dr/guests")
@RequiredArgsConstructor
public class DRGuestController implements GenericController{

    private final DRGuestService drService;

    private final GuestService service;

    private final GuestMapper mapper;

    @Value("${spring.datasource.dr.url}")
    private String url;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid GuestDto dto){
        Guest guest = this.mapper.toEntity(dto);
        this.drService.save(guest);
        this.service.save(guest);
        return ResponseEntity
                .created(this.createURI("/{legalEntityNumber}", guest.getLegalEntityNumber())).build();
    }

    @GetMapping("/{legalEntityNumber}")
    public ResponseEntity<GuestDto> find(@PathVariable String legalEntityNumber){
        Guest guest = this.drService.find(legalEntityNumber);
        GuestDto dto = this.mapper.toDto(guest);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> update(
            @PathVariable String legalEntityNumber,
            @RequestBody GuestDto dto){
        Guest guest = this.mapper.toEntity(dto);
        guest.setLegalEntityNumber(legalEntityNumber);
        this.drService.update(guest);
        this.service.update(guest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> delete(@PathVariable String legalEntityNumber){
        this.drService.delete(legalEntityNumber);
        this.service.delete(legalEntityNumber);
        return ResponseEntity.noContent().build();
    }
}
