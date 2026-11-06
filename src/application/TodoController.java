package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TodoController {

    @FXML
    private TextField inputKegiatan;

    @FXML
    private ListView<TodoItem> listViewKegiatan;

    @FXML
    private Button btnHapus;

    private TodoModel model;

    @FXML
    public void initialize() {
        model = new TodoModel();
        listViewKegiatan.setItems(model.getDaftarKegiatan());

        // PASANG CUSTOM CELL FACTORY
        listViewKegiatan.setCellFactory(param -> new ListCell<TodoItem>() {
            private final CheckBox checkBox = new CheckBox();
            private final Label label = new Label();
            private final HBox container = new HBox(10, checkBox, label);

            {
                // Atur agar label mengisi sisa ruang
                HBox.setHgrow(label, Priority.ALWAYS);
                container.setStyle("-fx-padding: 5 0 5 0; -fx-alignment: center-left;");
                
                // Event Listener: Saat CheckBox diklik
                checkBox.setOnAction(event -> {
                    TodoItem item = getItem();
                    if (item != null) {
                        // Jika dicentang -> 'done', jika tidak -> 'todo'
                        String newStatus = checkBox.isSelected() ? "done" : "todo";
                        model.updateStatus(item, newStatus);
                        updateLabelStyle(item);
                    }
                });
            }

            @Override
            protected void updateItem(TodoItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.getTitle());
                    // Set checkbox state berdasarkan status di database
                    checkBox.setSelected(item.getStatus().equals("done"));
                    updateLabelStyle(item);
                    setGraphic(container);
                }
            }

            // Helper untuk mencoret teks jika selesai
            private void updateLabelStyle(TodoItem item) {
                if (item.getStatus().equals("done")) {
                    label.setStyle("-fx-text-fill: #ADB5BD; -fx-strikethrough: true;");
                } else {
                    label.setStyle("-fx-text-fill: #212529; -fx-strikethrough: false;");
                }
            }
        });

        btnHapus.setDisable(true);
        listViewKegiatan.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            btnHapus.setDisable(newVal == null);
        });
    }

    @FXML
    void handleTambah(ActionEvent event) {
        String kegiatanBaru = inputKegiatan.getText().trim();
        if (kegiatanBaru.isEmpty()) {
            showAlert("Kegiatan tidak boleh kosong!");
            return;
        }
        model.tambahKegiatan(kegiatanBaru);
        inputKegiatan.clear();
    }

    @FXML
    void handleHapus(ActionEvent event) {
        TodoItem terpilih = listViewKegiatan.getSelectionModel().getSelectedItem();
        if (terpilih != null) {
            model.hapusKegiatan(terpilih);
        } else {
            showAlert("Pilih kegiatan yang ingin dihapus!");
        }
    }

    @FXML
    void handleExit(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}