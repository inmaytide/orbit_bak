module orbit.commons {

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires orbit.constant;
    requires hibernate.core;
    requires spring.data.commons;
    requires hibernate.jpa;
    requires orbit.util;

    exports com.inmaytide.orbit.commons.deser;
    exports com.inmaytide.orbit.commons.ser;
    exports com.inmaytide.orbit.commons.domain;
    exports com.inmaytide.orbit.commons.id;

}