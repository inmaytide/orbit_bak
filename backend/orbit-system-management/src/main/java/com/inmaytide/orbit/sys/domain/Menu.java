package com.inmaytide.orbit.sys.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @author Moss
 * @since July 31, 2018
 */
@Entity
@Table(name = "sys_menu")
@EntityListeners(AuditingEntityListener.class)
public class Menu extends AbstractEntity {

    private static final long serialVersionUID = 6299615913583454532L;

}
