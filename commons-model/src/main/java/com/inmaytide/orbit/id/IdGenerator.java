package com.inmaytide.orbit.id;

import java.io.Serializable;

public interface IdGenerator<T extends Serializable> {

    T generate();

}
