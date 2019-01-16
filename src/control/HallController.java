package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.RegitHallVO;

public class HallController implements Initializable {
	@FXML
	private TextField txtHallName;
	@FXML
	private TextField txtHallArea;
	@FXML
	private TextField txtHallPeopleNum;
	@FXML
	private TextField txtTypeOne;
	@FXML
	private TextField txtTypeTwo;
	@FXML
	private TextField txtTypeThree;
	@FXML
	private TextField txtTypeFour;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnRegit;
	@FXML
	private Button btnCancel;
	@FXML
	private TableView<RegitHallVO> hallTable = new TableView<>();

	RegitHallVO regitHall = new RegitHallVO();
	ObservableList<RegitHallVO> data = FXCollections.observableArrayList();
	ObservableList<RegitHallVO> selectHall;// ���̺��� ������ ���� ����
	int selectedIndex;// ���̺��� ������ �뿩������ �ε��� ����
	int no;// ���̺��� ���õ� �뿩���� ��ȣ ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnEdit.setDisable(true);
		btnDelete.setDisable(true);

		// customer table
		hallTable.setEditable(false);

		TableColumn colNo = new TableColumn("�����ȣ");
		colNo.setPrefWidth(70);
		colNo.setCellValueFactory(new PropertyValueFactory<>("hiNo"));

		TableColumn colName = new TableColumn("�����̸�");
		colName.setPrefWidth(100);
		colName.setCellValueFactory(new PropertyValueFactory<>("hiName"));

		TableColumn colArea = new TableColumn("����");
		colArea.setPrefWidth(70);
		colArea.setCellValueFactory(new PropertyValueFactory<>("hiArea"));

		TableColumn colNum = new TableColumn("�����ο�");
		colNum.setPrefWidth(80);
		colNum.setCellValueFactory(new PropertyValueFactory<>("hiNum"));

		TableColumn colOne = new TableColumn("Ÿ��1");
		colOne.setPrefWidth(80);
		colOne.setCellValueFactory(new PropertyValueFactory<>("genreOne"));

		TableColumn colTwo = new TableColumn("Ÿ��2");
		colTwo.setPrefWidth(80);
		colTwo.setCellValueFactory(new PropertyValueFactory<>("genreTwo"));

		TableColumn colThree = new TableColumn("Ÿ��3");
		colThree.setPrefWidth(80);
		colThree.setCellValueFactory(new PropertyValueFactory<>("genreThree"));

		TableColumn colFour = new TableColumn("Ÿ��4");
		colFour.setPrefWidth(80);
		colFour.setCellValueFactory(new PropertyValueFactory<>("genreFour"));

		hallTable.getColumns().addAll(colNo, colName, colArea, colNum, colOne, colTwo, colThree, colFour);
		hallTable.setItems(data);

		hallTotalList();

		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		btnRegit.setOnAction(event -> handlerBtnRegitAction(event));
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
		hallTable.setOnMouseClicked(event -> handlerMouseClickAction(event));

	}

	// ��ҹ�ư
	public void handlerBtnCancelAction(ActionEvent event) {
		try {
			Stage eStage = (Stage) btnCancel.getScene().getWindow();
			eStage.close();
			
			Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("��� ���� �ý���");
			mainStage.setScene(scene);
			mainStage.close();
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �������� ���
	public void handlerBtnRegitAction(ActionEvent event) {
		if (txtHallName.getText().equals("") || txtHallArea.getText().equals("")
				|| txtHallPeopleNum.getText().equals("") || txtTypeOne.getText().equals("")
				|| txtTypeTwo.getText().equals("") || txtTypeThree.getText().equals("")
				|| txtTypeFour.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ���");
			alert.setHeaderText("���� ��� ����!!");
			alert.setContentText("��� �׸��� �Է��� �ּ���!");
			alert.showAndWait();
		} else {
			try {
				data.removeAll(data);

				RegitHallVO hvo = null;
				RegitHallDAO hdao = new RegitHallDAO();

				hvo = new RegitHallVO(txtHallName.getText(), Integer.parseInt(txtHallArea.getText().trim()),
						Integer.parseInt(txtHallPeopleNum.getText().trim()),
						Integer.parseInt(txtTypeOne.getText().trim()), Integer.parseInt(txtTypeTwo.getText().trim()),
						Integer.parseInt(txtTypeThree.getText().trim()),
						Integer.parseInt(txtTypeFour.getText().trim()));

				hdao = new RegitHallDAO();
				hdao.getHallRegiste(hvo);

				if (hdao != null) {

					hallTotalList();

					Alert alert3 = new Alert(AlertType.WARNING);
					alert3.setTitle("�����Է�");
					alert3.setHeaderText(txtHallName.getText() + " ������ ���������� �߰��Ǿ����ϴ�.");
					alert3.setContentText("�߰���� �Ǵ� ��Ҹ� ���ּ���!");

					alert3.showAndWait();

					hallInit();
				}

			} catch (Exception e) {
				hallTotalList();
				Alert alert4 = new Alert(AlertType.WARNING);
				alert4.setTitle("�����Է�");
				alert4.setHeaderText("���� �����Է� ����!");
				alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
				alert4.showAndWait();
				System.out.println("���� ���:" + e);
			}

		}
	}

	// ���� �Է� �ʱ�ȭ
	private void hallInit() {
		txtHallName.clear();
		txtHallArea.clear();
		txtHallPeopleNum.clear();
		txtTypeOne.clear();
		txtTypeTwo.clear();
		txtTypeThree.clear();
		txtTypeFour.clear();

		txtHallName.setEditable(true);

	}

	// ���콺 Ŭ�� �̺�Ʈ
	public void handlerMouseClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			btnRegit.setDisable(true);
			selectHall = hallTable.getSelectionModel().getSelectedItems();
			selectedIndex = hallTable.getSelectionModel().getSelectedIndex();
			no = selectHall.get(0).getHiNo();
			try {
				txtHallName.setEditable(false);

				txtHallName.setText(selectHall.get(0).getHiName());
				txtHallArea.setText(selectHall.get(0).getHiArea() + "");
				txtHallPeopleNum.setText(selectHall.get(0).getHiNum() + "");
				txtTypeOne.setText(selectHall.get(0).getGenreOne() + "");
				txtTypeTwo.setText(selectHall.get(0).getGenreTwo() + "");
				txtTypeThree.setText(selectHall.get(0).getGenreThree() + "");
				txtTypeFour.setText(selectHall.get(0).getGenreFour() + "");

				btnEdit.setDisable(false);
				btnDelete.setDisable(false);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ������ ���� �Է��ϼ���!");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();

			}
		}
	}

	// ������ư
	public void handlerBtnDeleteAction(ActionEvent event) {
		RegitHallDAO hDao = null;
		hDao = new RegitHallDAO();

		try {
			hDao.getHallDelete(hallTable.getSelectionModel().getSelectedItem().getHiNo());
			data.removeAll(data);
			// ��ü����
			hallTotalList();
			hallInit();
			btnRegit.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������ư
	public void handlerBtnEditAction(ActionEvent event) {
		if (txtHallName.getText().equals("") || txtHallArea.getText().equals("")
				|| txtHallPeopleNum.getText().equals("") || txtTypeOne.getText().equals("")
				|| txtTypeTwo.getText().equals("") || txtTypeThree.getText().equals("")
				|| txtTypeFour.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("���� ���� ���� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
			alert2.showAndWait();
		} else {
			btnRegit.setDisable(false);
			selectedIndex = hallTable.getSelectionModel().getSelectedIndex();
			no = selectHall.get(0).getHiNo();

			RegitHallVO hvo = hallTable.getSelectionModel().getSelectedItem();
			RegitHallDAO hdao = null;
			try {

				data.remove(selectedIndex);
				hvo = new RegitHallVO(no, Integer.parseInt(txtHallArea.getText().trim()),
						Integer.parseInt(txtHallPeopleNum.getText().trim()), Integer.parseInt(txtTypeOne.getText().trim()),
						Integer.parseInt(txtTypeTwo.getText().trim()), Integer.parseInt(txtTypeThree.getText().trim()),
						Integer.parseInt(txtTypeFour.getText().trim()));

				hdao = new RegitHallDAO();
				hdao.getHallUpdate(hvo, hvo.getHiNo());

				data.removeAll(data);
				hallTotalList();
				btnEdit.setDisable(true);
				btnDelete.setDisable(true);
				if (hdao != null) {
					hallInit();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("��������");
					alert.setHeaderText(txtHallName.getText() + " ������ ���������� �����Ǿ����ϴ�.");
					alert.setContentText("������ �Ϸ�Ǿ����ϴ�.!");

					alert.showAndWait();

				}

			} catch (Exception e) {
				hallTotalList();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("��������");
				alert.setHeaderText("�뿩�� �������� ����!");
				alert.setContentText("������ ��� �Է��� �ּ���!");
				alert.showAndWait();
				System.out.println("�뿩�� ���:" + e);
			}
		}
	}

	// ���� ��ü ����Ʈ
	public void hallTotalList() {
		Object[][] cusTotalData;

		RegitHallDAO hDao = new RegitHallDAO();
		RegitHallVO hVo = null;
		ArrayList<String> title;
		ArrayList<RegitHallVO> list;

		title = hDao.getHallColumnName();
		int columnCount = title.size();

		list = hDao.getHallTotal();
		int rowCount = list.size();

		cusTotalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			hVo = list.get(index);
			data.add(hVo);
		}

	}

}
