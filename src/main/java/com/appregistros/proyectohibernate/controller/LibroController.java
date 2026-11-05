package com.appregistros.proyectohibernate.controller;

import com.appregistros.proyectohibernate.dao.LibroDAO;
import com.appregistros.proyectohibernate.model.Libro;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class LibroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, Long> colId;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, Integer> colAnio;

    @FXML
    private TableColumn<Libro, Double> colPrecio;

    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo"));

        colAutor.setCellValueFactory(
                new PropertyValueFactory<>("autor"));

        colAnio.setCellValueFactory(
                new PropertyValueFactory<>("anio_publicacion"));

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio"));

        cargarLibros();
    }

    @FXML
    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            Libro libro = new Libro(0, titulo, autor, anio, precio);

            libroDAO.guardar(libro);

            cargarLibros();
            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarLibros() {
        try {
            List<Libro> libros = libroDAO.listar();

            tablaLibros.setItems(
                    FXCollections.observableArrayList(libros));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtAnio.clear();
        txtPrecio.clear();
    }
}
