package control;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.AddEquipVO;

public class AddEquipController implements Initializable {
	@FXML
	private ComboBox<String> cbEquipName;
	@FXML
	private TextField txtEquipNum;
	@FXML
	private TextField txtEquipPrice;
	@FXML
	private DatePicker rentDate;
	@FXML
	private DatePicker returnDate;
	@FXML
	private ComboBox<String> cbRentState;

	@FXML
	private Button btnEdit;
	@FXML

	private Button btnDelete;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnCancel;
	@FXML
	private TableView<AddEquipVO> addTable = new TableView<>();
	@FXML
	private TextField txtRentalNum;

	AddEquipVO addEquip = new AddEquipVO();
	ObservableList<AddEquipVO> data = FXCollections.observableArrayList();
	ObservableList<AddEquipVO> selectAdd;// ���̺��� ������ ���� ����
	int selectedIndex;// ���̺��� ������ �뿩������ �ε��� ����
	int addNo;// ���̺��� ���õ� �뿩���� ��ȣ ����

	RegitEquipDAO eDao = new RegitEquipDAO();
	AddEquipDAO aDao = new AddEquipDAO();

	static String sumPrice;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtRentalNum.setEditable(false);
		txtEquipPrice.setEditable(false);

		rentDate.setValue(LocalDate.now());
		returnDate.setValue(LocalDate.of(9999, 01, 01));

		btnEdit.setDisable(true);
		btnDelete.setDisable(true);

		cbEquipName.setItems(FXCollections.observableArrayList(eDao.getEquipName()));
		cbRentState.setItems(FXCollections.observableArrayList("�뿩��", "�ݳ�"));

		txtRentalNum.setText(MainController.rentnum);

		// customer table
		addTable.setEditable(false);

		TableColumn colAddNo = new TableColumn("���뿩 ��ȣ");
		colAddNo.setPrefWidth(80);
		colAddNo.setCellValueFactory(new PropertyValueFactory<>("addNo"));

		TableColumn colRentNo = new TableColumn("�����ȣ");
		colRentNo.setPrefWidth(100);
		colRentNo.setCellValueFactory(new PropertyValueFactory<>("rentNo"));

		TableColumn colEquipNo = new TableColumn("�����ȣ");
		colEquipNo.setPrefWidth(70);
		colEquipNo.setCellValueFactory(new PropertyValueFactory<>("equipNo"));

		TableColumn colEquipName = new TableColumn("ǰ��");
		colEquipName.setPrefWidth(70);
		colEquipName.setCellValueFactory(new PropertyValueFactory<>("equipName"));

		TableColumn colAddNum = new TableColumn("����");
		colAddNum.setPrefWidth(80);
		colAddNum.setCellValueFactory(new PropertyValueFactory<>("addNum"));

		TableColumn colAddPrice = new TableColumn("�ݾ�");
		colAddPrice.setPrefWidth(80);
		colAddPrice.setCellValueFactory(new PropertyValueFactory<>("addPrice"));

		TableColumn colRentDay = new TableColumn("�뿩��");
		colRentDay.setPrefWidth(100);
		colRentDay.setCellValueFactory(new PropertyValueFactory<>("addRentDate"));

		TableColumn colReturnDay = new TableColumn("�ݳ���");
		colReturnDay.setPrefWidth(100);
		colReturnDay.setCellValueFactory(new PropertyValueFactory<>("addReturnDate"));

		TableColumn colState = new TableColumn("�뿩����");
		colState.setPrefWidth(80);
		colState.setCellValueFactory(new PropertyValueFactory<>("addState"));

		addTable.getColumns().addAll(colAddNo, colRentNo, colEquipNo, colEquipName, colAddNum, colAddPrice, colRentDay,
				colReturnDay, colState);
		addTable.setItems(data);

		addTotalList();

		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		btnAdd.setOnAction(event -> handlerBtnAddAction(event));
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
		addTable.setOnMouseClicked(event -> handlerMouseClickAction(event));
	}

	// ��ҹ�ư
	public void handlerBtnCancelAction(ActionEvent event) {
		try {
			Stage eStage = (Stage) btnCancel.getScene().getWindow();
			eStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �����߰� ��ư
	public void handlerBtnAddAction(ActionEvent event) {
		int equipNo = 0; // �����ȣ
		int equipPrice = 0; // �뿩�ݾ�
		int equipNum = 0; // �Է� ����
		int equipAmount;// ���� ��� ����
		int equipRentAmount; // ���� �뿩 ����
		String rentState;// �뿩����

		if (cbEquipName.getSelectionModel().getSelectedItem() == null || txtEquipNum.getText().equals("")
				|| cbRentState.getSelectionModel().getSelectedItem() == null) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ���");
			alert.setHeaderText("���� �߰� ����!!");
			alert.setContentText("��� �׸��� �Է��� �ּ���!");
			alert.showAndWait();

		} else {
			equipRentAmount = eDao.getEquipRentAmount(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipAmount = eDao.getEquipAmount(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipNo = eDao.getEquipNum(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipNum = Integer.parseInt(txtEquipNum.getText());
			rentState = cbRentState.getSelectionModel().getSelectedItem().toString();

			// �뿩���¿� ���� ���� ���
			switch (rentState) {
			case "�뿩��":
				equipRentAmount = equipRentAmount + equipNum;
				if (equipAmount < equipRentAmount) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("������ �ٽ� Ȯ���� �ּ���!");
					alert.showAndWait();
					equipRentAmount = equipRentAmount - equipNum;
					return;
				} else {
					break;
				}
			case "�ݳ�":
				equipRentAmount = equipRentAmount - equipNum;
				if (equipRentAmount < 0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("������ �ٽ� Ȯ���� �ּ���!");
					alert.showAndWait();
					equipRentAmount = equipRentAmount + equipNum;
					return;
				} else {
					break;
				}
			}

			try {
				eDao.getRentNumUpdate(equipRentAmount, cbEquipName.getSelectionModel().getSelectedItem().toString());
				equipPrice = eDao.getEquipPrice(cbEquipName.getSelectionModel().getSelectedItem().toString())
						* equipNum;
				txtEquipPrice.setText(equipPrice + "");
				data.removeAll(data);

				AddEquipVO avo = null;
				AddEquipDAO adao = new AddEquipDAO();

				avo = new AddEquipVO(Integer.parseInt(txtRentalNum.getText()), equipNo,
						cbEquipName.getSelectionModel().getSelectedItem().toString(),
						Integer.parseInt(txtEquipNum.getText().trim()), equipPrice, rentDate.getValue().toString(),
						returnDate.getValue().toString(), cbRentState.getSelectionModel().getSelectedItem().toString());

				if (returnDate.getValue().equals(LocalDate.of(9999, 01, 01))) {

					avo.setAddReturnDate("-");

				}
				adao = new AddEquipDAO();
				adao.getAddRegiste(avo);
				eDao.getRentNumUpdate(equipRentAmount, cbEquipName.getSelectionModel().getSelectedItem().toString());
				if (adao != null) {

					addTotalList();

					Alert alert3 = new Alert(AlertType.WARNING);
					alert3.setTitle("�����Է�");
					alert3.setHeaderText(
							cbEquipName.getSelectionModel().getSelectedItem().toString() + " ������ ���������� �߰��Ǿ����ϴ�.");
					alert3.setContentText("�߰���� �Ǵ� ��Ҹ� ���ּ���!");

					alert3.showAndWait();

					addInit();
				}

			} catch (Exception e) {
				addTotalList();
				Alert alert4 = new Alert(AlertType.WARNING);
				alert4.setTitle("�����Է�");
				alert4.setHeaderText("���� �����Է� ����!");
				alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
				alert4.showAndWait();
				System.out.println("���� ���:" + e);
			}
		}
	}

	// �ʱ�ȭ
	public void addInit() {
		cbEquipName.setDisable(false);
		cbEquipName.setValue(null);
		txtEquipNum.clear();
		txtEquipPrice.clear();

		rentDate.setDisable(false);
		returnDate.setValue(LocalDate.of(9999, 01, 01));
		cbRentState.setValue(null);

	}

	// ���콺Ŭ�� �̺�Ʈ
	public void handlerMouseClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			btnAdd.setDisable(true);
			selectAdd = addTable.getSelectionModel().getSelectedItems();
			selectedIndex = addTable.getSelectionModel().getSelectedIndex();
			addNo = selectAdd.get(0).getAddNo();
			try {
				cbEquipName.setDisable(true);
				rentDate.setDisable(true);

				cbEquipName.setValue(selectAdd.get(0).getEquipName());
				rentDate.setValue(LocalDate.parse(selectAdd.get(0).getAddRentDate()));
				returnDate.setValue(LocalDate.now());
				txtEquipNum.setText(selectAdd.get(0).getAddNum() + "");
				txtEquipPrice.setText(selectAdd.get(0).getAddPrice() + "");
				cbRentState.setValue(selectAdd.get(0).getAddState());
				txtRentalNum.setText(selectAdd.get(0).getRentNo() + "");

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
	public void handlerBtnEditAction(ActionEvent event) {
		int equipNo = 0; // �����ȣ
		int equipPrice = 0; // �뿩�ݾ�
		int equipNum = 0; // �Է� ����
		int equipAmount;// ���� ��� ����
		int equipRentAmount; // ���� �뿩 ����
		String rentState;// �뿩����

		if (txtEquipNum.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("���� ���� ���� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
			alert2.showAndWait();
		} else {
			if (returnDate.getValue() == null) {
				returnDate.setValue(LocalDate.of(9999, 01, 01));
			}

			equipRentAmount = eDao.getEquipRentAmount(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipAmount = eDao.getEquipAmount(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipNo = eDao.getEquipNum(cbEquipName.getSelectionModel().getSelectedItem().toString());
			equipNum = Integer.parseInt(txtEquipNum.getText());
			rentState = cbRentState.getSelectionModel().getSelectedItem().toString();

			// �뿩���¿� ���� ���� ���
			switch (rentState) {
			case "�뿩��":
				if (aDao.getRentState(selectAdd.get(0).getAddNo()).equals("�ݳ�")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("�̹� �ݳ��Ǿ� �ֽ��ϴ�.!");
					alert.showAndWait();

					return;
				}
				equipRentAmount = equipRentAmount - aDao.getAddEquipNum(selectAdd.get(0).getAddNo()) + equipNum;
				if (equipAmount < equipRentAmount) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("������ �ٽ� Ȯ���� �ּ���!");
					alert.showAndWait();
					equipRentAmount = equipRentAmount - equipNum;
					return;
				} else {
					equipPrice = eDao.getEquipPrice(cbEquipName.getSelectionModel().getSelectedItem().toString())
							* equipNum;
					txtEquipPrice.setText(equipPrice + "");
					break;
				}

			case "�ݳ�":
				if (aDao.getRentState(selectAdd.get(0).getAddNo()).equals("�ݳ�")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("�̹� �ݳ��Ǿ� �ֽ��ϴ�.!");
					alert.showAndWait();

					return;
				}
				equipRentAmount = equipRentAmount - equipNum;
				if (equipRentAmount < 0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� ���");
					alert.setHeaderText("���� �߰� ����!!");
					alert.setContentText("������ �ٽ� Ȯ���� �ּ���!");
					alert.showAndWait();
					equipRentAmount = equipRentAmount + equipNum;
					return;
				} else {
					break;
				}
			}

			btnAdd.setDisable(false);
			selectedIndex = addTable.getSelectionModel().getSelectedIndex();
			addNo = selectAdd.get(0).getAddNo();

			AddEquipVO avo = addTable.getSelectionModel().getSelectedItem();
			AddEquipDAO adao = null;

			try {
				eDao.getRentNumUpdate(equipRentAmount, cbEquipName.getSelectionModel().getSelectedItem().toString());

				data.remove(selectedIndex);
				avo = new AddEquipVO(addNo, Integer.parseInt(txtRentalNum.getText().trim()), equipNo,
						cbEquipName.getSelectionModel().getSelectedItem().toString(),
						Integer.parseInt(txtEquipNum.getText().trim()),
						Integer.parseInt(txtEquipPrice.getText().trim()), rentDate.getValue().toString(),
						returnDate.getValue().toString(), cbRentState.getSelectionModel().getSelectedItem().toString());

				if (returnDate.getValue().equals(LocalDate.of(9999, 01, 01))) {

					avo.setAddReturnDate("-");

				}
				adao = new AddEquipDAO();
				adao.getAddUpdate(avo, avo.getAddNo());

				data.removeAll(data);
				addTotalList();
				btnEdit.setDisable(true);
				btnDelete.setDisable(true);
				if (adao != null) {
					addInit();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("��������");
					alert.setHeaderText(" ������ ���������� �����Ǿ����ϴ�.");
					alert.setContentText("������ �Ϸ�Ǿ����ϴ�.!");

					alert.showAndWait();

				}

			} catch (Exception e) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("��������");
				alert.setHeaderText("���� �������� ����!");
				alert.setContentText("���� �������� ����!");
				alert.showAndWait();
				System.out.println("�뿩�� ���:" + e);
			}
		}
	}

	
	// ������ư
	public void handlerBtnDeleteAction(ActionEvent event) {
		AddEquipDAO aDao = null;
		aDao = new AddEquipDAO();

		try {
			aDao.getAddDelete(addTable.getSelectionModel().getSelectedItem().getAddNo());
			data.removeAll(data);
			// ��ü����
			addTotalList();
			addInit();
			btnAdd.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����߰� ��ü ����Ʈ
	public void addTotalList() {
		Object[][] cusTotalData;

		AddEquipDAO aDao = new AddEquipDAO();
		AddEquipVO aVo = null;
		ArrayList<String> title;
		ArrayList<AddEquipVO> list;

		title = aDao.getAddColumnName();
		int columnCount = title.size();

		list = aDao.getAddTotal();
		int rowCount = list.size();

		cusTotalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			aVo = list.get(index);
			data.add(aVo);
		}

	}

}
