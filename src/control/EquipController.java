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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.RegitEquipVO;

public class EquipController implements Initializable {
	@FXML
	private ComboBox<String> cbEquipType;
	@FXML
	private TextField txtEquipName;
	@FXML
	private TextField txtEquipNum;
	@FXML
	private TextField txtEquipPrice;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnRegit;
	@FXML
	private Button btnCancel;
	@FXML
	private TableView<RegitEquipVO> equipTable = new TableView<>();

	RegitEquipVO regitEquip = new RegitEquipVO();
	ObservableList<RegitEquipVO> data = FXCollections.observableArrayList();
	ObservableList<RegitEquipVO> selectEquipment;// ���̺��� ������ ���� ����
	int selectedIndex;// ���̺��� ������ �뿩������ �ε��� ����
	int no;// ���̺��� ���õ� �뿩���� ��ȣ ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnEdit.setDisable(true);
		btnDelete.setDisable(true);

		// Ÿ�� �޺��ڽ�
		cbEquipType.setItems(FXCollections.observableArrayList("�ǾƳ�", "����", "����", "����", "��Ÿ"));

		// customer table
		equipTable.setEditable(false);

		TableColumn colNo = new TableColumn("�����ȣ");
		colNo.setPrefWidth(70);
		colNo.setCellValueFactory(new PropertyValueFactory<>("eqNo"));

		TableColumn colType = new TableColumn("Ÿ��");
		colType.setPrefWidth(70);
		colType.setCellValueFactory(new PropertyValueFactory<>("eqType"));

		TableColumn colName = new TableColumn("ǰ��");
		colName.setPrefWidth(150);
		colName.setCellValueFactory(new PropertyValueFactory<>("eqName"));

		TableColumn colNum = new TableColumn("����");
		colNum.setPrefWidth(40);
		colNum.setCellValueFactory(new PropertyValueFactory<>("eqNum"));

		TableColumn colPrice = new TableColumn("�뿩����");
		colPrice.setPrefWidth(100);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("eqPrice"));

		TableColumn colRentNum = new TableColumn("�뿩����");
		colRentNum.setPrefWidth(100);
		colRentNum.setCellValueFactory(new PropertyValueFactory<>("eqRentNum"));

		equipTable.getColumns().addAll(colNo, colType, colName, colNum, colPrice, colRentNum);
		equipTable.setItems(data);

		equipTotalList();

		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		btnRegit.setOnAction(event -> handlerBtnRegitAction(event));
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
		equipTable.setOnMouseClicked(event -> handlerMouseClickAction(event));

	}

	// ���콺 Ŭ��-> ���� ��������
	public void handlerMouseClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			btnRegit.setDisable(true);
			selectEquipment = equipTable.getSelectionModel().getSelectedItems();
			selectedIndex = equipTable.getSelectionModel().getSelectedIndex();
			no = selectEquipment.get(0).getEqNo();
			try {
				cbEquipType.setDisable(true);
				txtEquipName.setEditable(false);

				cbEquipType.setValue(selectEquipment.get(0).getEqType());
				txtEquipName.setText(selectEquipment.get(0).getEqName());
				txtEquipNum.setText(selectEquipment.get(0).getEqNum() + "");
				txtEquipPrice.setText(selectEquipment.get(0).getEqPrice() + "");

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

	// ��Ϲ�ư
	public void handlerBtnRegitAction(ActionEvent event) {
		int rentNum = 0;// �뿩����
		if (cbEquipType.getSelectionModel().getSelectedItem() == null || txtEquipName.getText().equals("")
				|| txtEquipNum.getText().equals("") || txtEquipPrice.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ���");
			alert.setHeaderText("���� ��� ����!!");
			alert.setContentText("��� �׸��� �Է��� �ּ���!");
			alert.showAndWait();
		} else {
			try {
				data.removeAll(data);

				RegitEquipVO evo = null;
				RegitEquipDAO edao = new RegitEquipDAO();

				evo = new RegitEquipVO(cbEquipType.getSelectionModel().getSelectedItem().toString(),
						txtEquipName.getText(), Integer.parseInt(txtEquipNum.getText().trim()),
						Integer.parseInt(txtEquipPrice.getText().trim()), rentNum);

				edao = new RegitEquipDAO();
				edao.getEquipRegiste(evo);

				if (edao != null) {

					equipTotalList();

					Alert alert3 = new Alert(AlertType.WARNING);
					alert3.setTitle("�����Է�");
					alert3.setHeaderText(txtEquipName.getText() + " ������ ���������� �߰��Ǿ����ϴ�.");
					alert3.setContentText("�߰���� �Ǵ� ��Ҹ� ���ּ���!");

					alert3.showAndWait();

					equipInit();
				}

			} catch (Exception e) {
				equipTotalList();
				Alert alert4 = new Alert(AlertType.WARNING);
				alert4.setTitle("�����Է�");
				alert4.setHeaderText("���� �����Է� ����!");
				alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
				alert4.showAndWait();
				System.out.println("���� ���:" + e);
			}

		}
	}

	// ���� ����â �ʱ�ȭ
	public void equipInit() {
		cbEquipType.getSelectionModel().select(null);
		txtEquipName.clear();
		txtEquipNum.clear();
		txtEquipPrice.clear();

		cbEquipType.setDisable(false);
		txtEquipName.setEditable(true);

	}

	// ���� ���� ����
	public void handlerBtnDeleteAction(ActionEvent event) {
		RegitEquipDAO eDao = null;
		eDao = new RegitEquipDAO();

		try {
			eDao.getEquipDelete(equipTable.getSelectionModel().getSelectedItem().getEqNo());
			data.removeAll(data);
			// ��ü����
			equipTotalList();
			equipInit();
			btnRegit.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���� ���� ����
	public void handlerBtnEditAction(ActionEvent event) {
		RegitEquipDAO eDao = new RegitEquipDAO();
		if (txtEquipNum.getText().equals("") || txtEquipPrice.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("���� ���� ���� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
			alert2.showAndWait();
		} else {
			if (eDao.getEquipAmount(txtEquipName.getText()) < eDao.getEquipRentAmount(txtEquipName.getText())) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�����Է�");
				alert2.setHeaderText("���� ���� ���� ����!");
				alert2.setContentText("������ �뿩�������� �����ϴ�. ������ �ٽ� Ȯ���ϼ���.");
				alert2.showAndWait();
			} else {
				btnRegit.setDisable(false);
				selectedIndex = equipTable.getSelectionModel().getSelectedIndex();
				no = selectEquipment.get(0).getEqNo();

				RegitEquipVO evo = equipTable.getSelectionModel().getSelectedItem();
				RegitEquipDAO edao = null;
				try {

					data.remove(selectedIndex);
					evo = new RegitEquipVO(no, cbEquipType.getSelectionModel().getSelectedItem().toString(),
							txtEquipName.getText(), Integer.parseInt(txtEquipNum.getText().trim()),
							Integer.parseInt(txtEquipPrice.getText().trim()));

					edao = new RegitEquipDAO();
					edao.getEquipUpdate(evo, evo.getEqNo());

					data.removeAll(data);
					equipTotalList();
					btnEdit.setDisable(true);
					btnDelete.setDisable(true);
					if (edao != null) {
						equipInit();
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("��������");
						alert.setHeaderText(txtEquipName.getText() + " ������ ���������� �����Ǿ����ϴ�.");
						alert.setContentText("������ �Ϸ�Ǿ����ϴ�.!");

						alert.showAndWait();

					}

				} catch (Exception e) {
					equipTotalList();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("��������");
					alert.setHeaderText("�뿩�� �������� ����!");
					alert.setContentText("������ ��� �Է��� �ּ���!");
					alert.showAndWait();
					System.out.println("�뿩�� ���:" + e);
				}
			}
		}
	}

	// ������ü ����Ʈ
	public void equipTotalList() {
		Object[][] cusTotalData;

		RegitEquipDAO eDao = new RegitEquipDAO();
		RegitEquipVO eVo = null;
		ArrayList<String> title;
		ArrayList<RegitEquipVO> list;

		title = eDao.getEquipColumnName();
		int columnCount = title.size();

		list = eDao.getEquipTotal();
		int rowCount = list.size();

		cusTotalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			eVo = list.get(index);
			data.add(eVo);
		}

	}

}
