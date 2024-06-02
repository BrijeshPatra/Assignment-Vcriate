package com.project.demo.repositories;

import com.project.demo.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {

    List<File> findByOwnerId(Long ownerId);
    Optional<File> findTopByNameAndOwnerOrderByVersionDesc(String name);

    Optional<File> findTopByName(String fileName);
}   
