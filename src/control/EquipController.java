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
	ObservableList<RegitEquipVO> selectEquipment;// 테이블에서 선택한 정보 저장
	int selectedIndex;// 테이블에서 선택한 대여자정보 인덱스 저장
	int no;// 테이블에서 선택된 대여자의 번호 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnEdit.setDisable(true);
		btnDelete.setDisable(true);

		// 타입 콤보박스
		cbEquipType.setItems(FXCollections.observableArrayList("피아노", "무대", "조명", "음향", "기타"));

		// customer table
		equipTable.setEditable(false);

		TableColumn colNo = new TableColumn("설비번호");
		colNo.setPrefWidth(70);
		colNo.setCellValueFactory(new PropertyValueFactory<>("eqNo"));

		TableColumn colType = new TableColumn("타입");
		colType.setPrefWidth(70);
		colType.setCellValueFactory(new PropertyValueFactory<>("eqType"));

		TableColumn colName = new TableColumn("품명");
		colName.setPrefWidth(150);
		colName.setCellValueFactory(new PropertyValueFactory<>("eqName"));

		TableColumn colNum = new TableColumn("수량");
		colNum.setPrefWidth(40);
		colNum.setCellValueFactory(new PropertyValueFactory<>("eqNum"));

		TableColumn colPrice = new TableColumn("대여가격");
		colPrice.setPrefWidth(100);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("eqPrice"));

		TableColumn colRentNum = new TableColumn("대여수량");
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

	// 마우스 클릭-> 정보 가져오기
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
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 정보를 먼저 입력하세요!");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();

			}
		}
	}

	// 취소버튼
	public void handlerBtnCancelAction(ActionEvent event) {
		try {
			Stage eStage = (Stage) btnCancel.getScene().getWindow();
			eStage.close();

			Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("대관 관리 시스템");
			mainStage.setScene(scene);
			mainStage.close();
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 등록버튼
	public void handlerBtnRegitAction(ActionEvent event) {
		int rentNum = 0;// 대여수량
		if (cbEquipType.getSelectionModel().getSelectedItem() == null || txtEquipName.getText().equals("")
				|| txtEquipNum.getText().equals("") || txtEquipPrice.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("정보 등록");
			alert.setHeaderText("설비 등록 실패!!");
			alert.setContentText("모든 항목을 입력해 주세요!");
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
					alert3.setTitle("정보입력");
					alert3.setHeaderText(txtEquipName.getText() + " 정보가 성공적으로 추가되었습니다.");
					alert3.setContentText("추가등록 또는 취소를 해주세요!");

					alert3.showAndWait();

					equipInit();
				}

			} catch (Exception e) {
				equipTotalList();
				Alert alert4 = new Alert(AlertType.WARNING);
				alert4.setTitle("정보입력");
				alert4.setHeaderText("설비 정보입력 실패!");
				alert4.setContentText("정보입력에 실패하였습니다.!");
				alert4.showAndWait();
				System.out.println("설비 등록:" + e);
			}

		}
	}

	// 설비 정보창 초기화
	public void equipInit() {
		cbEquipType.getSelectionModel().select(null);
		txtEquipName.clear();
		txtEquipNum.clear();
		txtEquipPrice.clear();

		cbEquipType.setDisable(false);
		txtEquipName.setEditable(true);

	}

	// 설비 정보 삭제
	public void handlerBtnDeleteAction(ActionEvent event) {
		RegitEquipDAO eDao = null;
		eDao = new RegitEquipDAO();

		try {
			eDao.getEquipDelete(equipTable.getSelectionModel().getSelectedItem().getEqNo());
			data.removeAll(data);
			// 전체정보
			equipTotalList();
			equipInit();
			btnRegit.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 설비 정보 수정
	public void handlerBtnEditAction(ActionEvent event) {
		RegitEquipDAO eDao = new RegitEquipDAO();
		if (txtEquipNum.getText().equals("") || txtEquipPrice.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("정보입력");
			alert2.setHeaderText("설비 정보 수정 실패!");
			alert2.setContentText("정보를 모두 입력하세요!");
			alert2.showAndWait();
		} else {
			if (eDao.getEquipAmount(txtEquipName.getText()) < eDao.getEquipRentAmount(txtEquipName.getText())) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("정보입력");
				alert2.setHeaderText("설비 정보 수정 실패!");
				alert2.setContentText("수량이 대여수량보다 적습니다. 수량을 다시 확인하세요.");
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
						alert.setTitle("정보수정");
						alert.setHeaderText(txtEquipName.getText() + " 정보가 성공적으로 수정되었습니다.");
						alert.setContentText("수정이 완료되었습니다.!");

						alert.showAndWait();

					}

				} catch (Exception e) {
					equipTotalList();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("정보수정");
					alert.setHeaderText("대여자 정보수정 실패!");
					alert.setContentText("정보를 모두 입력해 주세요!");
					alert.showAndWait();
					System.out.println("대여자 등록:" + e);
				}
			}
		}
	}

	// 설비전체 리스트
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
