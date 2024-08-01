package com.demoj11.demoj11.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoj11.demoj11.entity.PhoneEntity;

public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID> {

}
