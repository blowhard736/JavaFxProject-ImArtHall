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
	ObservableList<RegitHallVO> selectHall;// 테이블에서 선택한 정보 저장
	int selectedIndex;// 테이블에서 선택한 대여자정보 인덱스 저장
	int no;// 테이블에서 선택된 대여자의 번호 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnEdit.setDisable(true);
		btnDelete.setDisable(true);

		// customer table
		hallTable.setEditable(false);

		TableColumn colNo = new TableColumn("극장번호");
		colNo.setPrefWidth(70);
		colNo.setCellValueFactory(new PropertyValueFactory<>("hiNo"));

		TableColumn colName = new TableColumn("극장이름");
		colName.setPrefWidth(100);
		colName.setCellValueFactory(new PropertyValueFactory<>("hiName"));

		TableColumn colArea = new TableColumn("면적");
		colArea.setPrefWidth(70);
		colArea.setCellValueFactory(new PropertyValueFactory<>("hiArea"));

		TableColumn colNum = new TableColumn("수용인원");
		colNum.setPrefWidth(80);
		colNum.setCellValueFactory(new PropertyValueFactory<>("hiNum"));

		TableColumn colOne = new TableColumn("타입1");
		colOne.setPrefWidth(80);
		colOne.setCellValueFactory(new PropertyValueFactory<>("genreOne"));

		TableColumn colTwo = new TableColumn("타입2");
		colTwo.setPrefWidth(80);
		colTwo.setCellValueFactory(new PropertyValueFactory<>("genreTwo"));

		TableColumn colThree = new TableColumn("타입3");
		colThree.setPrefWidth(80);
		colThree.setCellValueFactory(new PropertyValueFactory<>("genreThree"));

		TableColumn colFour = new TableColumn("타입4");
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

	// 극장정보 등록
	public void handlerBtnRegitAction(ActionEvent event) {
		if (txtHallName.getText().equals("") || txtHallArea.getText().equals("")
				|| txtHallPeopleNum.getText().equals("") || txtTypeOne.getText().equals("")
				|| txtTypeTwo.getText().equals("") || txtTypeThree.getText().equals("")
				|| txtTypeFour.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("정보 등록");
			alert.setHeaderText("극장 등록 실패!!");
			alert.setContentText("모든 항목을 입력해 주세요!");
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
					alert3.setTitle("정보입력");
					alert3.setHeaderText(txtHallName.getText() + " 정보가 성공적으로 추가되었습니다.");
					alert3.setContentText("추가등록 또는 취소를 해주세요!");

					alert3.showAndWait();

					hallInit();
				}

			} catch (Exception e) {
				hallTotalList();
				Alert alert4 = new Alert(AlertType.WARNING);
				alert4.setTitle("정보입력");
				alert4.setHeaderText("극장 정보입력 실패!");
				alert4.setContentText("정보입력에 실패하였습니다.!");
				alert4.showAndWait();
				System.out.println("극장 등록:" + e);
			}

		}
	}

	// 극장 입력 초기화
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

	// 마우스 클릭 이벤트
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
				alert.setTitle("정보입력");
				alert.setHeaderText("극장 정보를 먼저 입력하세요!");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();

			}
		}
	}

	// 삭제버튼
	public void handlerBtnDeleteAction(ActionEvent event) {
		RegitHallDAO hDao = null;
		hDao = new RegitHallDAO();

		try {
			hDao.getHallDelete(hallTable.getSelectionModel().getSelectedItem().getHiNo());
			data.removeAll(data);
			// 전체정보
			hallTotalList();
			hallInit();
			btnRegit.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 수정버튼
	public void handlerBtnEditAction(ActionEvent event) {
		if (txtHallName.getText().equals("") || txtHallArea.getText().equals("")
				|| txtHallPeopleNum.getText().equals("") || txtTypeOne.getText().equals("")
				|| txtTypeTwo.getText().equals("") || txtTypeThree.getText().equals("")
				|| txtTypeFour.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("정보입력");
			alert2.setHeaderText("극장 정보 수정 실패!");
			alert2.setContentText("정보를 모두 입력하세요!");
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
					alert.setTitle("정보수정");
					alert.setHeaderText(txtHallName.getText() + " 정보가 성공적으로 수정되었습니다.");
					alert.setContentText("수정이 완료되었습니다.!");

					alert.showAndWait();

				}

			} catch (Exception e) {
				hallTotalList();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보수정");
				alert.setHeaderText("대여자 정보수정 실패!");
				alert.setContentText("정보를 모두 입력해 주세요!");
				alert.showAndWait();
				System.out.println("대여자 등록:" + e);
			}
		}
	}

	// 극장 전체 리스트
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
