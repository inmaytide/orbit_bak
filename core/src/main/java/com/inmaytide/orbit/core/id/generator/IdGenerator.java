package com.inmaytide.orbit.core.id.generator;

import java.io.Serializable;

public interface IdGenerator<T extends Serializable> {

    T generate();

}
