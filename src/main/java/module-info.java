
module com.appregistros.proyectohibernate {

    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;

    requires org.hibernate.orm.core;

    requires java.sql;

    opens com.appregistros.proyectohibernate.controller to javafx.fxml;
    opens com.appregistros.proyectohibernate.model to org.hibernate.orm.core;

    exports com.appregistros.proyectohibernate;
    exports com.appregistros.proyectohibernate.controller;
    exports com.appregistros.proyectohibernate.model;
    exports com.appregistros.proyectohibernate.dao;
    exports com.appregistros.proyectohibernate.database;
}
