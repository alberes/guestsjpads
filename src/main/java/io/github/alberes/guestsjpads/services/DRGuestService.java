package io.github.alberes.guestsjpads.services;

import io.github.alberes.guestsjpads.controllers.mapper.GuestMapper;
import io.github.alberes.guestsjpads.dao.GuestDAO;
import io.github.alberes.guestsjpads.domains.Guest;
import io.github.alberes.guestsjpads.services.exception.DuplicateRecordException;
import io.github.alberes.guestsjpads.services.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DRGuestService {

    private final GuestDAO dao;

    private final GuestMapper mapper;

    public Guest save(Guest guest){
        Guest guestDB = this.dao.find(guest.getLegalEntityNumber());
        if(guestDB != null){
            log.error("Registration with " + guest.getLegalEntityNumber() + " has already been registered!");
            throw new DuplicateRecordException(
                    "Registration with " + guest.getLegalEntityNumber() + " has already been registered!");
        }
        return this.dao.save(guest);
    }

    public Guest find(String legalEntityNumber){
        Guest guest = this.dao.find(legalEntityNumber);
        if(guest == null){
            log.error("Object not found! legalEntityNumber: " +
                    legalEntityNumber + " type: " + Guest.class.getName());
            throw new ObjectNotFoundException("Object not found! legalEntityNumber: " +
                    legalEntityNumber + " type: " + Guest.class.getName());
        }
        return guest;
    }

    public void update(Guest guest){
        Guest guestDB = this.find(guest.getLegalEntityNumber());
        guestDB.setName(guest.getName());
        guestDB.setBirthday(guest.getBirthday());
        this.dao.update(guestDB);
    }

    public void delete(String legalEntityNumber){
        this.dao.find(legalEntityNumber);
        this.dao.delete(legalEntityNumber);
    }
}
