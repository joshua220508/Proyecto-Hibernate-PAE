
module com.appregistros.proyectohibernate {

    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens com.appregistros.proyectohibernate to javafx.fxml;
    opens com.appregistros.proyectohibernate.model to org.hibernate.orm.core, javafx.base;
    opens com.appregistros.proyectohibernate.controller to javafx.fxml;

    exports com.appregistros.proyectohibernate;
    exports com.appregistros.proyectohibernate.model;
}
