package com.inmaytide.orbit.commons.id;

import java.io.Serializable;

public interface IdGenerator<T> {

    T generate();

}
