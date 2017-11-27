package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
