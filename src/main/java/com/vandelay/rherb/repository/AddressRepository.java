package com.vandelay.rherb.repository;

import com.vandelay.rherb.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
