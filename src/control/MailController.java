package control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MailController implements Initializable {

	@FXML
	TextField txtId;
	@FXML
	PasswordField txtPass;
	@FXML
	TextField txtToAddr;
	@FXML
	TextField txtToName;
	@FXML
	TextField txtFromAddr;
	@FXML
	TextField txtFromName;
	@FXML
	Button btnSend;
	@FXML
	Button btnCancel;
	@FXML
	Button btnEnd;
	@FXML
	TextField txtTitle;
	@FXML
	TextField txtFileAddr;
	@FXML
	TextArea txtContents;
	@FXML
	Button btnAttachFile;

	Stage stage = new Stage();
	String name = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtToAddr.setEditable(false);
		txtToName.setEditable(false);
		txtToAddr.setText(MainController.cusEmail);
		txtToName.setText(MainController.cusName);

		btnSend.setOnAction(event -> sendEmail(event));
		btnCancel.setOnAction(event -> sendCancel(event));
		btnEnd.setOnAction(event -> sendEnd(event));
		btnAttachFile.setOnAction(event -> sendAttach(event));

	}

	// 첨부파일
	public void sendAttach(ActionEvent event) {
		FileChooser file = new FileChooser();
		file.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"), new ExtensionFilter("All Files", "*.*"));
		File selectedFile = file.showOpenDialog(stage);
		name = selectedFile.getName();

		if (selectedFile != null) {
			txtFileAddr.setText(selectedFile.getPath());
		}
	}

	// send
	public void sendEnd(ActionEvent event) {
		Stage emailStage = (Stage) btnCancel.getScene().getWindow();
		emailStage.close();
	}

	public void sendCancel(ActionEvent event) {
		txtFileAddr.clear();
		txtFromAddr.clear();
		txtFromName.clear();
		txtId.clear();
		txtTitle.clear();
		txtPass.clear();
		txtContents.clear();
	}

	public void sendEmail(ActionEvent event) {

		EmailAttachment attach = new EmailAttachment();
		attach.setDescription("첨부파일");
		attach.setName(name);
		attach.setPath(txtFileAddr.getText());

		MultiPartEmail email = new MultiPartEmail();

		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication(txtId.getText(), txtPass.getText());
		String rt = "Failure";

		try {
			email.setSSL(true);
			email.setTLS(true);

			email.addTo(txtToAddr.getText(), txtFromName.getText(), "utf-8");

			email.setFrom(txtFromAddr.getText(), txtFromName.getText(), "utf-8");

			email.setSubject(txtTitle.getText());
			email.setMsg(txtContents.getText());
			email.attach(attach);

			rt = email.send();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("메일전송");
			alert.setHeaderText("고객메일전송 성공!");
			alert.setContentText(txtFromName.getText() + " 고객님에게 메일을 전송하였습니다.");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("메일전송");
			alert.setHeaderText("고객메일전송 실패!");
			alert.setContentText(txtFromName.getText() + " 고객님에게 메일을 실패하였습니다.");
			alert.showAndWait();
			System.out.println(e);
		}
		sendCancel(event);

	}

}
