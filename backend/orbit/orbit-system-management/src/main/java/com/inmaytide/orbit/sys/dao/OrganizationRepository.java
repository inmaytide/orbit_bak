package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Organization;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "select ifnull(max(sort), -1) + 1 as sort from sys_organization", nativeQuery = true)
    Integer getSort();


    List<Organization> findByParent(Long parent, Sort sort);

}
