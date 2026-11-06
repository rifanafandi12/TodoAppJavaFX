package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

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
            // Gunakan Text (bukan Label) agar mendukung strikethrough
            private final Text textNode = new Text();
            private final HBox container = new HBox(10, checkBox, textNode);

            {
                HBox.setHgrow(textNode, Priority.ALWAYS);
                container.setStyle("-fx-padding: 5 0 5 0; -fx-alignment: center-left;");

                // Event Listener: Saat CheckBox diklik
                checkBox.setOnAction(event -> {
                    TodoItem item = getItem();
                    if (item != null) {
                        String newStatus = checkBox.isSelected() ? "done" : "todo";
                        // 1. Update ke database
                        model.updateStatus(item, newStatus);
                        // 2. Update status di objek memori
                        item.setStatus(newStatus);
                        // 3. Update tampilan teks secara langsung
                        updateTextStyle(item);
                    }
                });
            }

            @Override
            protected void updateItem(TodoItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    textNode.setText(item.getTitle());
                    // Sinkronkan status checkbox dengan data
                    checkBox.setSelected(item.getStatus().equals("done"));
                    updateTextStyle(item);
                    setGraphic(container);
                }
            }

            // Helper untuk mencoret teks jika selesai
            private void updateTextStyle(TodoItem item) {
                if (item.getStatus().equals("done")) {
                    // Teks abu-abu dan dicoret
                    textNode.setStyle("-fx-fill: #ADB5BD;");
                    textNode.setStrikethrough(true);
                } else {
                    // Teks normal
                    textNode.setStyle("-fx-fill: #212529;");
                    textNode.setStrikethrough(false);
                }
            }
        });

        // Fitur UX: Nonaktifkan tombol Hapus jika tidak ada item yang dipilih
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