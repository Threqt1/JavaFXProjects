module practice.javafx.borderradiuspreviewer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens practice.javafx.borderradiuspreviewer to javafx.fxml;
    exports practice.javafx.borderradiuspreviewer;
}