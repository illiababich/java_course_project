package utilities;

import javafx.scene.control.Alert;

public class AlertMessages
{
    public static void throwMessage(String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
