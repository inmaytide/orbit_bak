module orbit.commons {

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires hibernate.core;
    requires spring.data.commons;
    requires hibernate.jpa;
    requires orbit.utils;

    exports com.inmaytide.orbit.commons.json.deser;
    exports com.inmaytide.orbit.commons.json.ser;
    exports com.inmaytide.orbit.commons.domain;
    exports com.inmaytide.orbit.commons.id;
    exports com.inmaytide.orbit.commons;

}