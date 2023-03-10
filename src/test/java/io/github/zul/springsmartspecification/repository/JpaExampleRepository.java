package io.github.zul.springsmartspecification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.zul.springsmartspecification.domain.JpaExample;

public interface JpaExampleRepository
        extends JpaRepository<JpaExample, Long>, JpaSmartSpecificationRepository<JpaExample> {

}
