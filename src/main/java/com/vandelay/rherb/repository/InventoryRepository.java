package com.vandelay.rherb.repository;

import com.vandelay.rherb.domain.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Page<Inventory> findAllByWarehouseId(Pageable page, Long id);
}
