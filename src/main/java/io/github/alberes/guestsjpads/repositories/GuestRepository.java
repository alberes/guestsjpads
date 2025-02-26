package io.github.alberes.guestsjpads.repositories;

import io.github.alberes.guestsjpads.domains.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, String> {
}
