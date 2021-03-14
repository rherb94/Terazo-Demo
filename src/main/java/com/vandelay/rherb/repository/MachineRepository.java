package com.vandelay.rherb.repository;

import com.vandelay.rherb.domain.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    Page<Machine> findAllByFactoryId(Pageable page, Long id);
}
