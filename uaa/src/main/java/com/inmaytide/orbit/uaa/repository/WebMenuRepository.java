package com.inmaytide.orbit.uaa.repository;

import com.inmaytide.orbit.uaa.domain.WebMenu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebMenuRepository extends CrudRepository<WebMenu, Long> {

}
