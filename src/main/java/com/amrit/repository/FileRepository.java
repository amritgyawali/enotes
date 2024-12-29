package com.amrit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amrit.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {

}
