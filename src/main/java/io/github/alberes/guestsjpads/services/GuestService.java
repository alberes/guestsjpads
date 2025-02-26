package io.github.alberes.guestsjpads.services;

import io.github.alberes.guestsjpads.domains.Guest;
import io.github.alberes.guestsjpads.repositories.GuestRepository;
import io.github.alberes.guestsjpads.services.exception.DuplicateRecordException;
import io.github.alberes.guestsjpads.services.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestService {

    private final GuestRepository repository;

    @Modifying
    @Transactional
    public Guest save(Guest guest){
        Optional<Guest> guestDb = this.repository.findById(guest.getLegalEntityNumber());
        if(guestDb.isPresent()){
            log.error("Registration with " + guest.getLegalEntityNumber() + " has already been registered!");
            throw new DuplicateRecordException(
                    "Registration with " + guest.getLegalEntityNumber() + " has already been registered!");
        }
        return this.repository.save(guest);
    }

    public Guest find(String legalEntityNumber){
        return this.repository.findById(legalEntityNumber).orElseThrow(() ->
                new ObjectNotFoundException("Object not found! legalEntityNumber: " +
                        legalEntityNumber + " type: " + Guest.class.getName())
        );
    }

    @Modifying
    @Transactional
    public Guest update(Guest guest){
        Guest guestDB = this.find(guest.getLegalEntityNumber());
        guestDB.setName(guest.getName());
        guestDB.setBirthday(guest.getBirthday());
        return this.repository.save(guestDB);
    }

    @Modifying
    @Transactional
    public void delete(String legalEntityNumber){
        this.find(legalEntityNumber);
        this.repository.deleteById(legalEntityNumber);
    }

    public Page<Guest> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        return this.repository.findAll(
                PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy));
    }
}
