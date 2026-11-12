package com.appregistros.proyectohibernate.controller;

import com.appregistros.proyectohibernate.dao.CategoriaDAO;
import com.appregistros.proyectohibernate.model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CategoriaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Categoria> tablaCategorias;

    @FXML
    private TableColumn<Categoria, Long> colId;

    @FXML
    private TableColumn<Categoria, String> colNombre;

    @FXML
    private TableColumn<Categoria, String> colDescripcion;

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private final ObservableList<Categoria> listaCategorias =
            FXCollections.observableArrayList();

    @FXML
    public void irALibros() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/appregistros/proyectohibernate/libro-view.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) tablaCategorias.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de libros");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tablaCategorias.setItems(listaCategorias);

        tablaCategorias.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, categoriaAnterior, categoriaSeleccionada) -> {

                    if (categoriaSeleccionada != null) {
                        txtNombre.setText(categoriaSeleccionada.getNombre());
                        txtDescripcion.setText(categoriaSeleccionada.getDescripcion());

                        btnModificar.setDisable(false);
                        btnEliminar.setDisable(false);
                        btnGuardar.setDisable(true);
                    }
                });

        cargarCategorias();
    }

    @FXML
    public void guardarCategoria() {

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Validación", "El nombre de la categoría es obligatorio.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion.isEmpty() ? null : descripcion);

        try {
            categoriaDAO.guardar(categoria);

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Éxito", "Categoría guardada correctamente.");

            limpiarCampos();
            cargarCategorias();

        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error", "No se pudo guardar la categoría: " + e.getMessage());
        }
    }

    @FXML
    public void modificarCategoria() {

        Categoria seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Validación", "Seleccione una categoría de la tabla.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Validación", "El nombre de la categoría es obligatorio.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setId(seleccionada.getId());
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion.isEmpty() ? null : descripcion);

        try {
            boolean resultado = categoriaDAO.modificar(categoria);

            if (resultado) {
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Éxito", "Categoría modificada correctamente.");

                limpiarCampos();
                cargarCategorias();

            } else {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Aviso", "No se encontró la categoría para modificar.");
            }

        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error", "No se pudo modificar la categoría: " + e.getMessage());
        }
    }

    @FXML
    public void eliminarCategoria() {

        Categoria seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Validación", "Seleccione una categoría de la tabla.");
            return;
        }

        try {
            categoriaDAO.eliminar(seleccionada.getId());

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Éxito", "Categoría eliminada correctamente.");

            limpiarCampos();
            cargarCategorias();

        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error", "No se pudo eliminar la categoría. "
                            + "Compruebe si tiene libros asociados. " + e.getMessage());
        }
    }

    @FXML
    public void cargarCategorias() {

        try {
            listaCategorias.setAll(categoriaDAO.listar());

        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error", "No se pudieron cargar las categorías: " + e.getMessage());
        }
    }

    @FXML
    public void limpiarCampos() {

        txtNombre.clear();
        txtDescripcion.clear();

        tablaCategorias.getSelectionModel().clearSelection();

        btnGuardar.setDisable(false);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
