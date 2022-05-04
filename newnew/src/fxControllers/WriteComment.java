package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import static utilities.AlertMessages.throwMessage;

public class WriteComment {
    @FXML
    public TextArea Comment;
    @FXML
    public Button submitCommentButton;

    public void submitCommentButtonSelected(ActionEvent actionEvent)
    {
        System.out.println(Comment.getText());
        throwMessage("Success!", "Comment was submitted successfully.");
    }

}
