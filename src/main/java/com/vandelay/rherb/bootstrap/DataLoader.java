package com.vandelay.rherb.bootstrap;

import com.vandelay.rherb.domain.*;
import com.vandelay.rherb.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final MachineRepository machineRepository;
    private final AddressRepository addressRepository;

    public DataLoader(FactoryRepository factoryRepository, WarehouseRepository warehouseRepository, InventoryRepository inventoryRepository, MachineRepository machineRepository, AddressRepository addressRepository) {
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
        this.machineRepository = machineRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    public void init() {
       log.debug("Bootstrapping data..");
    }

    @Override
    public void run(String... args) throws Exception {
        int warehouseCount = warehouseRepository.findAll().size();
        int factoryCount = factoryRepository.findAll().size();
        if (warehouseCount == 0 || factoryCount == 0) {
            loadData();
        }
    }

    @Transactional
    private void loadData() {
        Address address = Address.builder()
                .buildingName("Pier 10")
                .city("New York")
                .stateProvince("NY")
                .streetLine1("186 West 81st Street")
                .zipPostalCode("90210")
                .build();

        Warehouse warehouse = Warehouse.builder()
                .name("Pier 10 Holdings")
                .description("Key East Coast shipping/receiving location for storing finished product, ready for distribution.")
                .address(address)
                .build();
        warehouseRepository.save(warehouse);

        Inventory inventoryItem = Inventory.builder()
                .warehouse(warehouse)
                .sku("456789")
                .quantity(500)
                .name("Tires")
                .description("New Michelin tires 255/45/18")
                .build();
        inventoryRepository.save(inventoryItem);

        inventoryItem = Inventory.builder()
                .warehouse(warehouse)
                .sku("456789")
                .quantity(500)
                .name("Tires")
                .description("New Michelin tires 255/45/18")
                .build();
        inventoryRepository.save(inventoryItem);

        inventoryItem = Inventory.builder()
                .warehouse(warehouse)
                .sku("13153251")
                .quantity(250)
                .name("Glass")
                .description("For auto glass replacements")
                .build();
        inventoryRepository.save(inventoryItem);


        address = Address.builder()
                .buildingName("Pier 5")
                .city("Chicago")
                .stateProvince("IL")
                .streetLine1("C warehouse circle")
                .zipPostalCode("90210")
                .build();

        Factory factory = Factory.builder()
                .name("Newark Latex Mfg")
                .description("Key East Coast facility for raw latex material to be processed into final products for the medical industry.")
                .address(address)
                .build();
        factoryRepository.save(factory);

        Machine machine = Machine.builder()
                .factory(factory)
                .name("Extruder AB-100")
                .description("Extruder with 1,000fpm output capacity")
                .build();
        machineRepository.save(machine);
        machine = Machine.builder()
                .factory(factory)
                .name("FCA Force X-15")
                .description("Auto engine assembly")
                .build();
        machineRepository.save(machine);
        machine = Machine.builder()
                .factory(factory)
                .name("Falcon 11")
                .description("Rocket gearing")
                .build();
        machineRepository.save(machine);
    }
}