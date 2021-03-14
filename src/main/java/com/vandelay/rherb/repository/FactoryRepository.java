package com.vandelay.rherb.repository;

import com.vandelay.rherb.domain.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
    List<Factory> findAllByAddressId(Long id);
}
