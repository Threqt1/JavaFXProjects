module practice.javafx.bin2dec {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens practice.javafx.bin2dec to javafx.fxml;
    exports practice.javafx.bin2dec;
}