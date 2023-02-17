package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
