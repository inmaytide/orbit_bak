package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.system.domain.Function;

import java.util.List;

public interface FunctionService extends ExistByCodeAdapter, BasicService<Function> {

    List<Function> listByMenuId(Long menuId);

}
