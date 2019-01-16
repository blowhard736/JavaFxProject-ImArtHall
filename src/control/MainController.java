package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CustomerVO;
import model.RentalVO;

public class MainController implements Initializable {
	@FXML
	private Button btnExit;
	@FXML
	private TableView<CustomerVO> customerTable = new TableView<>();
	@FXML
	private ToggleGroup typeGroup;
	@FXML
	private RadioButton rbGeneral;
	@FXML
	private RadioButton rbCompany;
	@FXML
	private TextField txtComName;
	@FXML
	private TextField txtNum;
	@FXML
	private TextField txtCeoName;
	@FXML
	private TextField txtAddr;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtGenre;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtStartTime;
	@FXML
	private TextField txtEndTime;
	@FXML
	private Button btnCusSearch;
	@FXML
	private Button btnCusTotal;
	@FXML
	private Button btnRegit;
	@FXML
	private Button btnCusEdit;
	@FXML
	private TextField txtCusSearch;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnEmail;

	@FXML
	private Button btnNext;
	@FXML
	private Button btnCancel;
	@FXML
	private TableView<RentalVO> rentTable = new TableView<>();
	@FXML
	private ComboBox<String> cbHallType;
	@FXML
	private TextField txtCName;
	@FXML
	private DatePicker rentDate;
	@FXML
	private ComboBox<String> cbRentType;
	@FXML
	private ComboBox<String> cbGenre;
	@FXML
	private CheckBox cbMorning;
	@FXML
	private CheckBox cbNoon;
	@FXML
	private CheckBox cbEvening;

	@FXML
	private TextField txtRentalPrice;
	@FXML
	private TextField txtHallNum;
	@FXML
	private TextField txtEquipPrice;
	@FXML
	private TextField txtContractP;
	@FXML
	private ComboBox<String> cbDefault;
	@FXML
	private TextField txtExtraTime;
	@FXML
	private TextField txtTotalPrice;
	@FXML
	private DatePicker regitDate;
	@FXML
	private DatePicker finalDate;
	@FXML
	private TextField txtRentNum;
	@FXML
	private TextField txtHallSearch;
	@FXML
	private Button btnHallSearch;
	@FXML
	private Button btnHallTotal;
	@FXML
	private TextField txtCNo;
	@FXML
	private Button btnRentCancel;
	@FXML
	private Button btnCalculated;

	@FXML
	private Button btnAddEquip;
	@FXML
	private Button btnRentRegit;
	@FXML
	private Button btnRentEdit;
	@FXML
	private Button btnMonthlySales;
	@FXML
	ComboBox<String> cbYear;

	@FXML
	private Button btnHallRegit;
	@FXML
	private Button btnEquipRegit;

	@FXML
	private Button btnSaveFileDir;
	@FXML
	private Button btnExcel;
	@FXML
	private Button btnPDF;
	@FXML
	private TextField txtSaveFileDir;

	static String sumPrice;
	private Stage primaryStage;

	CustomerVO customer = new CustomerVO();
	ObservableList<CustomerVO> data = FXCollections.observableArrayList();
	ObservableList<CustomerVO> selectCustomer;// 테이블에서 선택한 정보 저장
	int selectedIndex;// 테이블에서 선택한 대여자정보 인덱스 저장
	int no;// 테이블에서 선택된 대여자의 번호 저장

	RentalVO rental = new RentalVO();
	ObservableList<RentalVO> dataR = FXCollections.observableArrayList();
	ObservableList<RentalVO> selectRental;
	ObservableList<String> rent = FXCollections.observableArrayList();
	ObservableList<String> rentDefault = FXCollections.observableArrayList();
	int selectedRentalIndex;
	int rentNo;

	RegitHallDAO hDao = new RegitHallDAO();
	RentalDAO rDao = new RentalDAO();
	AddEquipDAO aDao = new AddEquipDAO();
	RegitHallDAO hdao = new RegitHallDAO();
	MonthlySalesDAO sDao = new MonthlySalesDAO();
	static String rentnum;// 대관번호 기억
	static String cusEmail;//고객 이메일 주소
	static String cusName;//고객 이름

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rent = rDao.getCusComName();
		rentDefault = rDao.getDefaultCusComName();
		for (int i = 0; i < rent.size(); i++) {
			try {
				rDao.getRentalCancel(rDao.getDefaultRentNo().get(i));
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("대관취소");
				alert2.setHeaderText(rentDefault.get(i) + " 대관 취소!");
				alert2.setContentText(rentDefault.get(i) + " 단체(또는 회사)가 기한일까지 금액을 지불하지않아 최종미납처리됩니다.");
				alert2.showAndWait();
			} catch (Exception e) {

			}
		}
		for (int i = 0; i < rent.size(); i++) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("대관일 임박");
			alert2.setHeaderText(rent.get(i) + " 대관 취소 임박!");
			alert2.setContentText(rent.get(i) + " 단체(또는 회사)의 대관일을 수정하지 않으면 최종미납처리됩니다.");
			alert2.showAndWait();

		}

		rentDate.setValue(LocalDate.now()); // 대관일->현재날짜
		regitDate.setValue(LocalDate.now());
		finalDate.setValue(LocalDate.now());
		txtCeoName.setEditable(false); // 대표자이름 쓰기 막아놓기
		txtNum.setEditable(false); // 사업자번호 막아 놓기
		btnCusEdit.setDisable(true); // 수정버튼막기
		btnRentCancel.setDisable(true);
		btnCalculated.setDisable(true);
		btnEmail.setDisable(true);
		btnDelete.setDisable(true);
		rbGeneral.setSelected(true); // 일반으로 체크된상태에서 시작
		rentalInitOne();
		rentalInitTwo();
		rentalBlockOne(); // 대관첫번째 블락
		rentalBlockTwo(); // 대관정보 두번째 블락

		cbHallType.setItems(FXCollections.observableArrayList(hDao.getHallName()));
		cbRentType.setItems(FXCollections.observableArrayList("공연", "준비", "철수", "리허설"));
		cbGenre.setItems(FXCollections.observableArrayList("순수음악/연극/클래식/무용", "째즈/퓨전음악", "뮤지컬/오페라", "대중음악/행사/방송"));
		cbDefault.setItems(FXCollections.observableArrayList("완납", "미납", "최종미납"));
		cbYear.setItems(FXCollections.observableArrayList(rDao.getRentDate()));
		if (cbYear.getSelectionModel().getSelectedItem() == null) {
			cbYear.getSelectionModel().select("2018");
		}
		// customer table
		customerTable.setEditable(false);

		TableColumn colType = new TableColumn("고객타입");
		colType.setPrefWidth(100);
		colType.setMaxWidth(500);
		colType.setCellValueFactory(new PropertyValueFactory<>("cusType"));

		TableColumn colComName = new TableColumn("단체/회사명");
		colComName.setPrefWidth(100);
		colComName.setMaxWidth(500);
		colComName.setCellValueFactory(new PropertyValueFactory<>("cusComname"));

		TableColumn colNum = new TableColumn("사업자 번호");
		colNum.setPrefWidth(100);
		colNum.setMaxWidth(500);
		colNum.setCellValueFactory(new PropertyValueFactory<>("cusNum"));

		TableColumn colCeoName = new TableColumn("대표자명");
		colCeoName.setPrefWidth(100);
		colCeoName.setMaxWidth(500);
		colCeoName.setCellValueFactory(new PropertyValueFactory<>("cusCeoname"));

		TableColumn colName = new TableColumn("담당자명");
		colName.setPrefWidth(100);
		colName.setMaxWidth(500);
		colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));

		TableColumn colAddr = new TableColumn("주소");
		colAddr.setPrefWidth(100);
		colAddr.setMaxWidth(500);
		colAddr.setCellValueFactory(new PropertyValueFactory<>("cusAddr"));

		TableColumn colPhone = new TableColumn("연락처");
		colPhone.setPrefWidth(100);
		colPhone.setMaxWidth(500);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("cusPhone"));

		TableColumn colEmail = new TableColumn("이메일");
		colEmail.setPrefWidth(100);
		colEmail.setMaxWidth(500);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("cusEmail"));

		TableColumn colGenre = new TableColumn("장르");
		colGenre.setPrefWidth(100);
		colGenre.setMaxWidth(500);
		colGenre.setCellValueFactory(new PropertyValueFactory<>("cusGenre"));

		TableColumn colTitle = new TableColumn("공연제목");
		colTitle.setPrefWidth(100);
		colTitle.setMaxWidth(500);
		colTitle.setCellValueFactory(new PropertyValueFactory<>("cusTitle"));

		TableColumn colPrice = new TableColumn("입장권 가격");
		colPrice.setPrefWidth(100);
		colPrice.setMaxWidth(500);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("cusPrice"));

		TableColumn colStartTime = new TableColumn("시작시간");
		colStartTime.setPrefWidth(100);
		colStartTime.setMaxWidth(500);
		colStartTime.setCellValueFactory(new PropertyValueFactory<>("cusStartTime"));

		TableColumn colEndTime = new TableColumn("종료시간");
		colEndTime.setPrefWidth(100);
		colEndTime.setMaxWidth(500);
		colEndTime.setCellValueFactory(new PropertyValueFactory<>("cusEndTime"));

		TableColumn colNo = new TableColumn("No.");
		colNo.setPrefWidth(100);
		colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

		customerTable.getColumns().addAll(colType, colComName, colNum, colCeoName, colName, colAddr, colPhone, colEmail,
				colGenre, colTitle, colPrice, colStartTime, colEndTime, colNo);
		customerTable.setItems(data);

		// 대여자 정보
		cusTotalList();

		// customer table
		rentTable.setEditable(false);

		TableColumn colRNo = new TableColumn("대관번호");
		colRNo.setPrefWidth(100);
		colRNo.setMaxWidth(500);
		colRNo.setCellValueFactory(new PropertyValueFactory<>("rentalNo"));

		TableColumn colHiNo = new TableColumn("극장번호");
		colHiNo.setPrefWidth(100);
		colHiNo.setMaxWidth(500);
		colHiNo.setCellValueFactory(new PropertyValueFactory<>("hiNo"));

		TableColumn colCName = new TableColumn("단체명");
		colCName.setPrefWidth(100);
		colCName.setMaxWidth(500);
		colCName.setCellValueFactory(new PropertyValueFactory<>("cusComName"));

		TableColumn colRDate = new TableColumn("대관날짜");
		colRDate.setPrefWidth(100);
		colRDate.setMaxWidth(500);
		colRDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

		TableColumn colRType = new TableColumn("대관타입");
		colRType.setPrefWidth(100);
		colRType.setMaxWidth(500);
		colRType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));

		TableColumn colRGenre = new TableColumn("장르");
		colRGenre.setPrefWidth(100);
		colRGenre.setMaxWidth(500);
		colRGenre.setCellValueFactory(new PropertyValueFactory<>("rentalGenre"));

		TableColumn colMorning = new TableColumn("오전대관");
		colMorning.setPrefWidth(100);
		colMorning.setMaxWidth(500);
		colMorning.setCellValueFactory(new PropertyValueFactory<>("rentalMorning"));

		TableColumn colNoon = new TableColumn("오후대관");
		colNoon.setPrefWidth(100);
		colNoon.setMaxWidth(500);
		colNoon.setCellValueFactory(new PropertyValueFactory<>("rentalNoon"));

		TableColumn colEvening = new TableColumn("저녁대관");
		colEvening.setPrefWidth(100);
		colEvening.setMaxWidth(500);
		colEvening.setCellValueFactory(new PropertyValueFactory<>("rentalEvening"));

		TableColumn colRCount = new TableColumn("대관회수");
		colRCount.setPrefWidth(100);
		colRCount.setMaxWidth(500);
		colRCount.setCellValueFactory(new PropertyValueFactory<>("rentalCount"));

		TableColumn colEPrice = new TableColumn("설비금액");
		colEPrice.setPrefWidth(100);
		colEPrice.setMaxWidth(500);
		colEPrice.setCellValueFactory(new PropertyValueFactory<>("rentalEquipPrice"));

		TableColumn colRPrice = new TableColumn("대관금액");
		colRPrice.setPrefWidth(100);
		colRPrice.setMaxWidth(500);
		colRPrice.setCellValueFactory(new PropertyValueFactory<>("rentalRentalPrice"));

		TableColumn colContractP = new TableColumn("계약금");
		colContractP.setPrefWidth(100);
		colContractP.setMaxWidth(500);
		colContractP.setCellValueFactory(new PropertyValueFactory<>("rentalContractP"));

		TableColumn colDefault = new TableColumn("미납여부");
		colDefault.setPrefWidth(100);
		colDefault.setCellValueFactory(new PropertyValueFactory<>("rentalDefault"));

		TableColumn colExTime = new TableColumn("초과시간");
		colExTime.setPrefWidth(100);
		colExTime.setCellValueFactory(new PropertyValueFactory<>("rentalExtraTime"));

		TableColumn colTPrice = new TableColumn("총 금액");
		colTPrice.setPrefWidth(100);
		colTPrice.setCellValueFactory(new PropertyValueFactory<>("rentalTotalPrice"));

		TableColumn colRegitDay = new TableColumn("등록일");
		colRegitDay.setPrefWidth(100);
		colRegitDay.setCellValueFactory(new PropertyValueFactory<>("regitDate"));

		TableColumn colEditDay = new TableColumn("수정일");
		colEditDay.setPrefWidth(100);
		colEditDay.setCellValueFactory(new PropertyValueFactory<>("editDate"));

		rentTable.getColumns().addAll(colRNo, colHiNo, colCName, colRDate, colRType, colRGenre, colMorning, colNoon,
				colEvening, colRCount, colEPrice, colRPrice, colContractP, colDefault, colExTime, colTPrice,
				colRegitDay, colEditDay);
		rentTable.setItems(dataR);

		// 대관 정보
		rentTotalList();

		//////////////////////////////////////////////////////////////////////////////////////////
		// 대여자정보 관련 버튼
		rbGeneral.setOnMouseClicked(event -> handlerbGeneralAction(event));
		rbCompany.setOnMouseClicked(event -> handlerbCompanyAction(event));
		btnCusTotal.setOnAction(event -> handlerBtnCusTotalAction(event));
		btnRegit.setOnAction(event -> handlerBtnRegitAction(event));
		btnCusSearch.setOnAction(event -> handlerBtnCusSearchAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 수정을 위한 테이블 컨트롤
		customerTable.setOnMouseClicked(event -> handlerCusClickAction(event));
		btnCusEdit.setOnAction(event -> handlerBtnCusEditAction(event));

		// 대관쪽 버튼
		btnNext.setOnAction(event -> handlerBtnNextAction(event));
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
		btnAddEquip.setOnAction(event -> handlerBtnAddAction(event));
		btnRentRegit.setOnAction(event -> handlerBtnRentRegitAction(event));
		rentTable.setOnMouseClicked(event -> handlerRentClickAction(event));
		btnRentEdit.setOnAction(event -> handlerBtnRentEditAction(event));
		btnHallSearch.setOnAction(event -> handlerBtnHallSearchAction(event));
		btnHallTotal.setOnAction(event -> handlerBtnHallTotalAction(event));
		btnRentCancel.setOnAction(event -> handlerBtnRegitCancelAction(event));
		btnCalculated.setOnAction(event -> handlerBtnCalculatedAction(event));

		btnMonthlySales.setOnAction(event -> handlerBtnMOnthlSalesAction(event));
		btnExit.setOnAction(event -> handlerBtnExitAction(event));
		btnEquipRegit.setOnAction(event -> handlerBtnEquipRegitAction(event));
		btnHallRegit.setOnAction(event -> handlerBtnHallRegitAction(event));
		btnEmail.setOnAction(event -> handlerBtnEmailAction(event));
		
		// 엑셀,pdf저장버튼
		btnExcel.setOnAction(event -> handlerBtnExcelAction(event));
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	//이메일전송
	public void handlerBtnEmailAction(ActionEvent event) {
		try {
			cusEmail = txtEmail.getText();
			cusName = txtName.getText();
			btnEmail.setDisable(true);
			btnCusEdit.setDisable(true);
			btnDelete.setDisable(true);
			customerInit();
			Parent root = FXMLLoader.load(getClass().getResource("/view/mail.fxml"));
			Scene scene = new Scene(root);
			Stage emailStage = new Stage();
			emailStage.setTitle("고객 메일 전송");
			emailStage.setScene(scene);

			emailStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 파일저장소 경로설정 버튼
	public void handlerBtnSaveFileDirAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());
			btnExcel.setDisable(false);
			btnPDF.setDisable(false);
		}
	}

	// 엑셀파일생성
	public void handlerBtnExcelAction(ActionEvent event) {
		CustomerDAO cdao = new CustomerDAO();
		boolean saveSuccess;

		ArrayList<CustomerVO> list;
		list = cdao.getCustomerTotal();
		CustomerExcel excelWriter = new CustomerExcel();

		// xlsx파일쓰기
		saveSuccess = excelWriter.xlsxWriter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("엑셀 파일 생성");
			alert.setHeaderText("고객 목록 엑셀파일 생성 성공!");
			alert.setContentText("고객목록 엑셀 파일!");
			alert.showAndWait();
		}
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	// pdf파일 생성
	public void handlerBtnPDFAction(ActionEvent event) {
		try {
			// pdf document 선언
			// (Rectangle pageSize,float marginLeft.float marginRight,top,bottom
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			// pdf 파일 저장공간 선언 .pdf파일이 생성, 그후 스트림으로 저장
			String strReportPDFName = "customer_" + System.currentTimeMillis() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
			// document를 열어 pdf문서를 쓸수있도록
			document.open();
			// 한글지원폰트 설정
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 8, Font.NORMAL);
			Font font2 = new Font(bf, 14, Font.BOLD);
			// 타이틀
			Paragraph title = new Paragraph("고객 정보", font2);
			// 중간정렬
			title.setAlignment(Element.ALIGN_CENTER);
			// 문서에 추가
			document.add(title);
			document.add(new Paragraph("\r\n"));
			// 생성날짜
			LocalDate date1 = LocalDate.now();
			Paragraph writeDay = new Paragraph(date1.toString(), font);
			// 오른쪽 정렬
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			// 문서에 추가
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));

			// 생성자에 컬럼수를 준다.
			PdfPTable table = new PdfPTable(9);
			table.setWidths(new int[] { 100, 120, 100, 100, 100, 200, 150, 220, 50 });
			// 컬럼타이틀
			PdfPCell header1 = new PdfPCell(new Paragraph("고객타입", font));
			PdfPCell header2 = new PdfPCell(new Paragraph("단체/회사명", font));
			PdfPCell header3 = new PdfPCell(new Paragraph("사업자 번호", font));
			PdfPCell header4 = new PdfPCell(new Paragraph("대표자명", font));
			PdfPCell header5 = new PdfPCell(new Paragraph("담당자명", font));
			PdfPCell header6 = new PdfPCell(new Paragraph("주소", font));
			PdfPCell header7 = new PdfPCell(new Paragraph("연락처", font));
			PdfPCell header8 = new PdfPCell(new Paragraph("이메일", font));
			PdfPCell header9 = new PdfPCell(new Paragraph("일련번호", font));
			// 가로정렬
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header4.setHorizontalAlignment(Element.ALIGN_CENTER);
			header5.setHorizontalAlignment(Element.ALIGN_CENTER);
			header6.setHorizontalAlignment(Element.ALIGN_CENTER);
			header7.setHorizontalAlignment(Element.ALIGN_CENTER);
			header8.setHorizontalAlignment(Element.ALIGN_CENTER);
			header9.setHorizontalAlignment(Element.ALIGN_CENTER);
			// 세로정렬
			header1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header3.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header4.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header5.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header7.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header8.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header9.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			// 테이블에 추가
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			table.addCell(header4);
			table.addCell(header5);
			table.addCell(header6);
			table.addCell(header7);
			table.addCell(header8);
			table.addCell(header9);
			// DB 연결 및 리스트 선택
			CustomerDAO cDao = new CustomerDAO();
			CustomerVO cVo = new CustomerVO();
			ArrayList<CustomerVO> list;
			list = cDao.getCustomerTotal2();
			int rowCount = list.size();
			PdfPCell cell1 = null;
			PdfPCell cell2 = null;
			PdfPCell cell3 = null;
			PdfPCell cell4 = null;
			PdfPCell cell5 = null;
			PdfPCell cell6 = null;
			PdfPCell cell7 = null;
			PdfPCell cell8 = null;
			PdfPCell cell9 = null;

			for (int index = 0; index < rowCount; index++) {

				cVo = list.get(index);

				cell1 = new PdfPCell(new Paragraph(cVo.getCusType(), font));
				cell2 = new PdfPCell(new Paragraph(cVo.getCusComname(), font));
				cell3 = new PdfPCell(new Paragraph(cVo.getCusNum(), font));
				cell4 = new PdfPCell(new Paragraph(cVo.getCusCeoname(), font));
				cell5 = new PdfPCell(new Paragraph(cVo.getCusName(), font));
				cell6 = new PdfPCell(new Paragraph(cVo.getCusAddr(), font));
				cell7 = new PdfPCell(new Paragraph(cVo.getCusPhone(), font));
				cell8 = new PdfPCell(new Paragraph(cVo.getCusEmail(), font));
				cell9 = new PdfPCell(new Paragraph(cVo.getNo() + "", font));

				// 가로정렬
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

				// 세로정렬
				cell1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell3.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell4.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell5.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell7.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell8.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell9.setHorizontalAlignment(Element.ALIGN_MIDDLE);

				// 테이블에 셀 추가
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				table.addCell(cell7);
				table.addCell(cell8);
				table.addCell(cell9);

			}
			// 문서에 테이블 추가
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			// 문서 닫기 쓰기 종료
			document.close();

			txtSaveFileDir.clear();
		
			btnPDF.setDisable(true);
			btnExcel.setDisable(true);

			alert.setTitle("PDF 생성");
			alert.setHeaderText("대여자 목록 PDF파일 생성 성공!");
			alert.setContentText("대여자목록 PDF 파일!");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 완납버튼
	public void handlerBtnCalculatedAction(ActionEvent event) {
		RentalDAO rDao = new RentalDAO();
		try {
			rDao.getCalculated(Integer.parseInt(txtRentNum.getText()));
			dataR.removeAll(dataR);
			customerInit();
			rentalInitOne();
			rentalBlockOne();
			rentalInitTwo();
			rentalBlockTwo();
			btnRentCancel.setDisable(true);
			btnCalculated.setDisable(true);
			rentTotalList();
			cbYear.setItems(FXCollections.observableArrayList(rDao.getRentDate()));
		} catch (Exception e) {

		}
	}

	// 월매출
	public void handlerBtnMOnthlSalesAction(ActionEvent event) {

		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnMonthlySales.getScene().getWindow());
			dialog.setTitle("막대 그래프");

			Parent parent = FXMLLoader.load(getClass().getResource("/view/monthlysales.fxml"));

			BarChart barChart = (BarChart) parent.lookup("#barChart");

			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());

			// 1월
			XYChart.Series seriesJan = new XYChart.Series();
			seriesJan.setName("1월");
			double sales1 = 0;
			ObservableList JanList = FXCollections.observableArrayList();
			ObservableList<Double> sale1 = FXCollections.observableArrayList();
			sale1 = sDao.getJanSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale1.size(); i++) {
				sales1 = sales1 + sale1.get(i);
			}
			JanList.add(new XYChart.Data("1월", sales1));
			seriesJan.setData(JanList);
			barChart.getData().add(seriesJan);

			// 2월
			XYChart.Series seriesFeb = new XYChart.Series();
			seriesFeb.setName("2월");
			double sales2 = 0;
			ObservableList FebList = FXCollections.observableArrayList();
			ObservableList<Double> sale2 = FXCollections.observableArrayList();

			sale2 = sDao.getFebSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale2.size(); i++) {
				sales2 = sales2 + sale2.get(i);
			}
			FebList.add(new XYChart.Data("2월", sales2));
			seriesFeb.setData(FebList);
			barChart.getData().add(seriesFeb);

			// 3월
			XYChart.Series seriesMar = new XYChart.Series();
			seriesMar.setName("3월");
			double sales3 = 0;
			ObservableList MarList = FXCollections.observableArrayList();
			ObservableList<Double> sale3 = FXCollections.observableArrayList();
			sale3 = sDao.getMarSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale3.size(); i++) {
				sales3 = sales3 + sale3.get(i);
			}
			MarList.add(new XYChart.Data("3월", sales3));
			seriesMar.setData(MarList);
			barChart.getData().add(seriesMar);

			// 4월
			XYChart.Series seriesApr = new XYChart.Series();
			seriesApr.setName("4월");
			double sales4 = 0;
			ObservableList AprList = FXCollections.observableArrayList();
			ObservableList<Double> sale4 = FXCollections.observableArrayList();
			sale4 = sDao.getAprSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale4.size(); i++) {
				sales4 = sales4 + sale4.get(i);
			}
			AprList.add(new XYChart.Data("4월", sales4));
			seriesApr.setData(AprList);
			barChart.getData().add(seriesApr);

			// 5월
			XYChart.Series seriesMay = new XYChart.Series();
			seriesMay.setName("5월");
			double sales5 = 0;
			ObservableList MayList = FXCollections.observableArrayList();
			ObservableList<Double> sale5 = FXCollections.observableArrayList();
			sale5 = sDao.getMaySales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale5.size(); i++) {
				sales5 = sales5 + sale5.get(i);
			}
			MayList.add(new XYChart.Data("1월", sales5));
			seriesMay.setData(MayList);
			barChart.getData().add(seriesMay);

			// 6월
			XYChart.Series seriesJun = new XYChart.Series();
			seriesJun.setName("6월");
			double sales6 = 0;
			ObservableList JunList = FXCollections.observableArrayList();
			ObservableList<Double> sale6 = FXCollections.observableArrayList();
			sale6 = sDao.getJunSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale6.size(); i++) {
				sales6 = sales6 + sale6.get(i);
			}
			JunList.add(new XYChart.Data("6월", sales6));
			seriesJun.setData(JunList);
			barChart.getData().add(seriesJun);

			// 7월
			XYChart.Series seriesJul = new XYChart.Series();
			seriesJul.setName("7월");
			double sales7 = 0;
			ObservableList JulList = FXCollections.observableArrayList();
			ObservableList<Double> sale7 = FXCollections.observableArrayList();
			sale7 = sDao.getJulSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale7.size(); i++) {
				sales7 = sales7 + sale7.get(i);
			}
			JulList.add(new XYChart.Data("7월", sales7));
			seriesJul.setData(JulList);
			barChart.getData().add(seriesJul);

			// 8월
			XYChart.Series seriesAug = new XYChart.Series();
			seriesAug.setName("8월");
			double sales8 = 0;
			ObservableList AugList = FXCollections.observableArrayList();
			ObservableList<Double> sale8 = FXCollections.observableArrayList();
			sale8 = sDao.getAugSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale8.size(); i++) {
				sales8 = sales8 + sale8.get(i);
			}
			AugList.add(new XYChart.Data("8월", sales8));
			seriesAug.setData(AugList);
			barChart.getData().add(seriesAug);

			// 9월
			XYChart.Series seriesSep = new XYChart.Series();
			seriesSep.setName("9월");
			double sales9 = 0;
			ObservableList SepList = FXCollections.observableArrayList();
			ObservableList<Double> sale9 = FXCollections.observableArrayList();
			sale9 = sDao.getSepSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale9.size(); i++) {
				sales9 = sales9 + sale9.get(i);
			}
			SepList.add(new XYChart.Data("9월", sales9));
			seriesSep.setData(SepList);
			barChart.getData().add(seriesSep);

			// 10월
			XYChart.Series seriesOct = new XYChart.Series();
			seriesOct.setName("10월");
			double sales10 = 0;
			ObservableList OctList = FXCollections.observableArrayList();
			ObservableList<Double> sale10 = FXCollections.observableArrayList();
			sale10 = sDao.getOctSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale10.size(); i++) {
				sales10 = sales10 + sale10.get(i);
			}
			OctList.add(new XYChart.Data("10월", sales10));
			seriesOct.setData(OctList);
			barChart.getData().add(seriesOct);

			// 11월
			XYChart.Series seriesNov = new XYChart.Series();
			seriesNov.setName("11월");
			double sales11 = 0;
			ObservableList NovList = FXCollections.observableArrayList();
			ObservableList<Double> sale11 = FXCollections.observableArrayList();
			sale11 = sDao.getNovSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale11.size(); i++) {
				sales11 = sales11 + sale11.get(i);
			}
			NovList.add(new XYChart.Data("11월", sales11));
			seriesNov.setData(NovList);
			barChart.getData().add(seriesNov);

			// 12월
			XYChart.Series seriesDec = new XYChart.Series();
			seriesDec.setName("12월");
			double sales12 = 0;
			ObservableList DecList = FXCollections.observableArrayList();
			ObservableList<Double> sale12 = FXCollections.observableArrayList();
			sale12 = sDao.getDecSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale12.size(); i++) {
				sales12 = sales12 + sale12.get(i);
			}
			DecList.add(new XYChart.Data("12월", sales12));
			seriesDec.setData(DecList);
			barChart.getData().add(seriesDec);

			// 연매출
			XYChart.Series seriesYear = new XYChart.Series();
			seriesYear.setName("연매출");
			double salesY = 0;
			ObservableList YearList = FXCollections.observableArrayList();
			ObservableList<Double> saleY = FXCollections.observableArrayList();
			saleY = sDao.getTotalSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < saleY.size(); i++) {
				salesY = salesY + saleY.get(i);
			}
			YearList.add(new XYChart.Data("연매출", salesY));
			seriesYear.setData(YearList);
			barChart.getData().add(seriesYear);

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();

		} catch (IOException e) {

		}
	}

	// 대관취소 -> 최종미납처리 총액은 계약금으로 대체
	public void handlerBtnRegitCancelAction(ActionEvent event) {
		RentalDAO rDao = new RentalDAO();
		try {
			rDao.getRentalCancel(Integer.parseInt(txtRentNum.getText()));
			dataR.removeAll(dataR);
			customerInit();
			rentalInitOne();
			rentalBlockOne();
			rentalInitTwo();
			rentalBlockTwo();
			btnRentCancel.setDisable(true);
			btnCalculated.setDisable(true);
			rentTotalList();
		} catch (Exception e) {

		}
	}

	public void handlerBtnHallTotalAction(ActionEvent event) {
		try {
			dataR.removeAll(dataR);
			rentTotalList();
		} catch (Exception e) {
		}
	}

	// 대관검색
	public void handlerBtnHallSearchAction(ActionEvent event) {
		RentalVO rVo = new RentalVO();
		RentalDAO rDao = null;

		Object[][] totalData = null;

		String searchComName = "";
		boolean searchResult = false;

		try {
			searchComName = txtHallSearch.getText().trim();
			rDao = new RentalDAO();
			rVo = rDao.getRentalCheck(searchComName);

			if (searchComName.equals("")) {
				searchResult = true;

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("대관 정보 검색");
				alert.setHeaderText("단체/회사명을 입력하세요");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}

			if (!searchComName.equals("") && (rVo != null)) {
				ArrayList<String> title;
				ArrayList<RentalVO> list;

				title = rDao.getRentColumnName();
				int columnCount = title.size();

				list = rDao.getRentalTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];

				if (rVo.getCusComName().equals(searchComName)) {
					txtHallSearch.clear();
					dataR.removeAll(dataR);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						rVo = list.get(index);
						if (rVo.getCusComName().equals(searchComName)) {
							dataR.add(rVo);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) {
				txtHallSearch.clear();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("대관 정보 검색");
				alert.setHeaderText("해당 대관 정보가 없습니다");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			txtCusSearch.clear();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("대관 정보 검색");
			alert.setHeaderText("검색에 오류발생");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
		}
	}

	////////////////////////////////////// 대관
	////////////////////////////////////// 등록///////////////////////////////////////////

	// 대관취소버튼
	public void handlerBtnCancelAction(ActionEvent event) {
		customerInit();
		rentalInitOne();
		rentalBlockOne();
		rentalInitTwo();
		rentalBlockTwo();
		customerTable.setDisable(false);
		rentTable.setDisable(false);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("정보입력");
		alert.setHeaderText("대관 신청취소!");
		alert.setContentText("기존 대여자정보를 삭제하고 다시 등록해주세요!");
		alert.showAndWait();
	}

	// 대관테이블 마우스 클릭이벤트
	public void handlerRentClickAction(MouseEvent event) {

		if (event.getClickCount() == 2) {
			selectRental = rentTable.getSelectionModel().getSelectedItems();
			selectedRentalIndex = rentTable.getSelectionModel().getSelectedIndex();
			rentNo = selectRental.get(0).getRentalNo();
			if (selectRental.get(0).getRentalDefault().equals("최종미납")
					|| selectRental.get(0).getRentalDefault().equals("완납")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("정보를 불러올 수 없습니다!");
				alert.setContentText("완납이나 최종미납처리된 데이터는 수정할 수 없습니다.");
				alert.showAndWait();
			} else {
				try {

					rentalInitOne();
					rentalInitTwo();
					if (selectRental.get(0).getRentalMorning().equals("o")) {
						cbMorning.setSelected(true);
					} else {
						cbMorning.setSelected(false);
					}
					if (selectRental.get(0).getRentalNoon().equals("o")) {
						cbNoon.setSelected(true);
					} else {
						cbNoon.setSelected(false);
					}
					if (selectRental.get(0).getRentalEvening().equals("o")) {
						cbEvening.setSelected(true);
					} else {
						cbEvening.setSelected(false);
					}
					regitDate.setDisable(true);

					cbHallType.setValue(hDao.getHallName(selectRental.get(0).getHiNo()));
					txtCNo.setText(selectRental.get(0).getCusNo() + "");
					txtCName.setText(selectRental.get(0).getCusComName());
					rentDate.setValue(LocalDate.parse(selectRental.get(0).getDate().substring(0, 10)));
					cbRentType.setValue(selectRental.get(0).getRentalType());
					txtRentalPrice.setText(selectRental.get(0).getRentalRentalPrice() + "");
					cbGenre.setValue(selectRental.get(0).getRentalGenre());
					txtRentNum.setText(selectRental.get(0).getRentalNo() + "");
					txtHallNum.setText(selectRental.get(0).getHiNo() + "");
					txtEquipPrice.setText(selectRental.get(0).getRentalEquipPrice() + "");
					txtContractP.setText(selectRental.get(0).getRentalContractP() + "");
					cbDefault.setValue(selectRental.get(0).getRentalDefault());
					txtExtraTime.setText(selectRental.get(0).getRentalExtraTime() + "");
					txtTotalPrice.setText(selectRental.get(0).getRentalTotalPrice() + "");
					regitDate.setValue(LocalDate.parse(selectRental.get(0).getRegitDate().substring(0, 10)));
					finalDate.setValue(LocalDate.parse(selectRental.get(0).getEditDate().substring(0, 10)));

					btnRegit.setDisable(true);
					btnRentRegit.setDisable(true);
					btnNext.setDisable(true);
					btnRentEdit.setDisable(false);
					btnRentCancel.setDisable(false);
					btnCancel.setDisable(true);
					btnCalculated.setDisable(false);

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("정보입력");
					System.out.println(e);
					alert.setHeaderText("대관 정보를 먼저 입력하세요!");
					alert.setContentText("Careful with the next step!");
					alert.showAndWait();
					btnRentEdit.setDisable(true);
					btnRentCancel.setDisable(true);
					btnCalculated.setDisable(true);
				}
			}
		}
	}

	// 대관 수정버튼
	public void handlerBtnRentEditAction(ActionEvent event) {
		RentalDAO rdao = new RentalDAO();
		RegitHallDAO hdao = new RegitHallDAO();
		int check1 = 0;// 대관일,대관장소 중복검사
		int check2 = 0;// 대관일,대관장소 중복검사
		int check3 = 0;// 대관일,대관장소 중복검사
		int rentYear;
		int rentDay;
		int finalYear;
		int finalDay;
		int diff;// 대관날짜와 등록일자의 차이
		String rMorning = "x";
		String rNoon = "x";
		String rEvening = "x";
		boolean check = false;// 대관횟수 중복검사

		rentYear = rentDate.getValue().getYear();
		rentDay = rentDate.getValue().getDayOfYear();
		finalYear = finalDate.getValue().getYear();
		finalDay = finalDate.getValue().getDayOfYear();
		if (rentYear > finalYear) {
			diff = 365 + rentDay - finalDay;
		} else {
			diff = rentDay - finalDay;
		}
		if (cbMorning.isSelected()) {
			check1 = rdao.getDayLoCheckMorning(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
			rMorning = "o";
		}
		if (cbNoon.isSelected()) {
			check2 = rdao.getDayLoCheckNoon(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
			rNoon = "o";
		}
		if (cbEvening.isSelected()) {
			check3 = rdao.getDayLoCheckEvening(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
			rEvening = "o";
		}

		check = rDao.getRentCount(rMorning, rNoon, rEvening, Integer.parseInt(txtRentNum.getText()));
		if (check == true
				&& rentDate.getValue().toString().equals(rDao.getRentDate(Integer.parseInt(txtRentNum.getText())))) {
			goOnEdit();
		} else {
			if (diff < 7) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("대관날짜");
				alert2.setHeaderText("대관 정보입력 실패!");
				alert2.setContentText("대관일7일전에는 수정할수 없습니다!");
				alert2.showAndWait();
			} else if (check1 == 1 || check2 == 1 || check3 == 1) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("대관날짜,장소");
				alert2.setHeaderText("대관 정보입력 실패!");
				alert2.setContentText("대관장소 또는 시간이 중복되었습니다.");
				alert2.showAndWait();
			} else {
				goOnEdit();
			}
		}
	}

	// 조건만족시 수정
	public void goOnEdit() {
		double rentPrice;
		int equipPrice;
		double contractPrice;
		int exTime;
		double total;
		double typePrice = 1; // 대관타입에 따른 배수
		int genrePrice; // 장르별 대관 금액
		try {
			dataR.removeAll(dataR);
			RentalVO rvo = null;
			RentalDAO rdao = new RentalDAO();
			RegitHallDAO hdao = new RegitHallDAO();

			// 대관금액 계산파트

			int morning = 0; // 오전 체크박스
			int noon = 0; // 오후 체크박스
			int evening = 0; // 저녁 체크박스
			String genre = "";
			String rMorning = "x";
			String rNoon = "x";
			String rEvening = "x";

			if (cbMorning.isSelected()) {
				morning = 1;
				rMorning = "o";
			}
			if (cbNoon.isSelected()) {
				noon = 1;
				rNoon = "o";
			}
			if (cbEvening.isSelected()) {
				evening = 1;
				rEvening = "o";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("순수음악/연극/클래식/무용")) {
				genre = "hi_genre1";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("째즈/퓨전음악")) {
				genre = "hi_genre2";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("뮤지컬/오페라")) {
				genre = "hi_genre3";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("대중음악/행사/방송")) {
				genre = "hi_genre4";
			}
			if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("공연")) {
				typePrice = 1;
			}
			if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("리허설")
					|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("철수")
					|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("준비")) {
				typePrice = 0.5;
			}

			genrePrice = hDao.getHallPrice(cbHallType.getSelectionModel().getSelectedItem().toString(), genre);

			rentPrice = (morning + noon + evening) * typePrice * genrePrice; // 대관금액
			equipPrice = aDao.getTotalEquipPrice(txtRentNum.getText()); // 설비금액
			contractPrice = (rentPrice + equipPrice) / 10; // 계액금
			exTime = Integer.parseInt(txtExtraTime.getText()); // 초과시간
			total = (rentPrice + equipPrice) + rentPrice * exTime * 0.3; // 총액

			txtRentalPrice.setText(rentPrice + "");
			txtEquipPrice.setText(equipPrice + "");
			txtContractP.setText(contractPrice + "");
			txtTotalPrice.setText(total + "");

			// 등록파트

			rvo = new RentalVO(Integer.parseInt(txtHallNum.getText().trim()), Integer.parseInt(txtCNo.getText().trim()),
					txtCName.getText(), rentDate.getValue().toString(),
					cbRentType.getSelectionModel().getSelectedItem().toString(),
					cbGenre.getSelectionModel().getSelectedItem().toString(), rMorning, rNoon, rEvening,
					morning + noon + evening, Integer.parseInt(txtEquipPrice.getText().trim()),
					Double.parseDouble(txtRentalPrice.getText().trim()),
					Double.parseDouble(txtContractP.getText().trim()),
					cbDefault.getSelectionModel().getSelectedItem().toString(),
					Integer.parseInt(txtExtraTime.getText().trim()), Double.parseDouble(txtTotalPrice.getText().trim()),
					regitDate.getValue().toString(), finalDate.getValue().toString());

			rdao = new RentalDAO();
			rdao.getRentalUpdate(rvo, Integer.parseInt(txtRentNum.getText()));

			if (rdao != null) {

				rentTotalList();

				Alert alert3 = new Alert(AlertType.WARNING);
				alert3.setTitle("정보입력");
				alert3.setHeaderText(txtCName.getText() + " 정보가 성공적으로 수정되었습니다.");
				alert3.setContentText("대관수정이 완료되었습니다!");
				alert3.showAndWait();
				rentalInitOne();
				rentalInitTwo();
				rentalBlockOne(); // 대관첫번째 블락
				rentalBlockTwo(); // 대관정보 두번째 블락
				btnRegit.setDisable(false);
				btnRentCancel.setDisable(true);
				btnCalculated.setDisable(true);

			}
		} catch (Exception e) {
			rentTotalList();
			Alert alert4 = new Alert(AlertType.WARNING);
			alert4.setTitle("정보입력");
			alert4.setHeaderText("대관 수정 실패!");
			alert4.setContentText("정보입력에 실패하였습니다.!");
			alert4.showAndWait();
			System.out.println("대관 등록:" + e);
		}
	}

	// 대관 등록 버튼
	public void handlerBtnRentRegitAction(ActionEvent event) {
		double rentPrice;
		int equipPrice;
		double contractPrice;
		int exTime;
		double total;

		rentPrice = Double.parseDouble(txtRentalPrice.getText());
		equipPrice = aDao.getTotalEquipPrice(txtRentNum.getText());
		contractPrice = (rentPrice + equipPrice) / 10;
		exTime = Integer.parseInt(txtExtraTime.getText());
		total = (rentPrice + equipPrice) + rentPrice * exTime * 0.3;

		txtEquipPrice.setText(equipPrice + "");
		txtContractP.setText(contractPrice + "");
		txtTotalPrice.setText(total + "");

		try {
			dataR.removeAll(dataR);
			RentalVO rvo = null;
			RentalDAO rdao = new RentalDAO();
			// 대관금액 계산파트

			int morning = 0; // 오전 체크박스
			int noon = 0; // 오후 체크박스
			int evening = 0; // 저녁 체크박스
			String genre = "";
			String rMorning = "x";
			String rNoon = "x";
			String rEvening = "x";

			if (cbMorning.isSelected()) {
				morning = 1;
				rMorning = "o";
			}
			if (cbNoon.isSelected()) {
				noon = 1;
				rNoon = "o";
			}
			if (cbEvening.isSelected()) {
				evening = 1;
				rEvening = "o";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("순수음악/연극/클래식/무용")) {
				genre = "hi_genre1";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("째즈/퓨전음악")) {
				genre = "hi_genre2";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("뮤지컬/오페라")) {
				genre = "hi_genre3";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("대중음악/행사/방송")) {
				genre = "hi_genre4";
			}

			// 등록파트

			rvo = new RentalVO(Integer.parseInt(txtHallNum.getText().trim()), Integer.parseInt(txtCNo.getText().trim()),
					txtCName.getText(), rentDate.getValue().toString(),
					cbRentType.getSelectionModel().getSelectedItem().toString(),
					cbGenre.getSelectionModel().getSelectedItem().toString(), rMorning, rNoon, rEvening,
					morning + noon + evening, Integer.parseInt(txtEquipPrice.getText().trim()),
					Double.parseDouble(txtRentalPrice.getText().trim()),
					Double.parseDouble(txtContractP.getText().trim()),
					cbDefault.getSelectionModel().getSelectedItem().toString(),
					Integer.parseInt(txtExtraTime.getText().trim()), Double.parseDouble(txtTotalPrice.getText().trim()),
					regitDate.getValue().toString(), finalDate.getValue().toString());

			rdao = new RentalDAO();
			rdao.getRentalUpdate(rvo, rdao.getRentNumber(Integer.parseInt(txtCNo.getText())));

			if (rdao != null) {

				rentTotalList();

				Alert alert3 = new Alert(AlertType.WARNING);
				alert3.setTitle("정보입력");
				alert3.setHeaderText(txtCName.getText() + " 정보가 성공적으로 추가되었습니다.");
				alert3.setContentText("대관등록이 완료되었습니다!");

				alert3.showAndWait();

				// 블락과 초기화
				customerInit();
				rentalInitOne();
				rentalBlockOne();
				rentalInitTwo();
				rentalBlockTwo();
				customerTable.setDisable(false);
				rentTable.setDisable(false);
			}

		} catch (Exception e) {
			rentTotalList();
			Alert alert4 = new Alert(AlertType.WARNING);
			alert4.setTitle("정보입력");
			alert4.setHeaderText("대관 정보입력 실패!");
			alert4.setContentText("정보입력에 실패하였습니다.!");
			alert4.showAndWait();
			System.out.println("대관 등록:" + e);
		}

	}

	// 설비추가 버튼
	public void handlerBtnAddAction(ActionEvent event) {
		try {
			rentnum = txtRentNum.getText();
			Parent root = FXMLLoader.load(getClass().getResource("/view/addequip.fxml"));
			Scene scene = new Scene(root);
			Stage subjectStage = new Stage();
			subjectStage.setTitle("설비 추가");
			subjectStage.setScene(scene);

			subjectStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 대관정보 전체 리스트
	public void rentTotalList() {
		Object[][] rentTotalData;

		RentalDAO rDao = new RentalDAO();
		RentalVO rVo = null;
		ArrayList<String> title1;
		ArrayList<RentalVO> list1;

		title1 = rDao.getRentColumnName();
		int columnCount1 = title1.size();
		System.out.println(columnCount1);
		list1 = rDao.getRentalTotal();
		int rowCount1 = list1.size();
		System.out.println(rowCount1);

		rentTotalData = new Object[rowCount1][columnCount1];

		for (int index1 = 0; index1 < rowCount1; index1++) {
			rVo = list1.get(index1);
			dataR.add(rVo);
		}

	}

	// 다음 버튼
	public void handlerBtnNextAction(ActionEvent event) {
		RentalVO rvo = null;
		RentalDAO rdao = new RentalDAO();
		RegitHallDAO hdao = new RegitHallDAO();
		int check1 = 0;	// 대관일,대관장소 중복검사
		int check2 = 0;	// 대관일,대관장소 중복검사
		int check3 = 0;	// 대관일,대관장소 중복검사
		int rentYear; 	//대관일 연도
		int rentDay;	//대관일 날짜를 일수로
		int sysYear;	//등록일 연도
		int sysDay;		//등록일 날짜를 일수로
		int diff;// 대관날짜와 등록일자의 차이
		rentYear = rentDate.getValue().getYear();
		rentDay = rentDate.getValue().getDayOfYear();
		sysYear = LocalDate.now().getYear();
		sysDay = LocalDate.now().getDayOfYear();
		if (rentYear > sysYear) {
			diff = 365 + rentDay - sysDay;
		} else {
			diff = rentDay - sysDay;
		}
		if (cbMorning.isSelected()) {
			check1 = rdao.getDayLoCheckMorning(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
		}
		if (cbNoon.isSelected()) {
			check2 = rdao.getDayLoCheckNoon(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
		}
		if (cbEvening.isSelected()) {
			check3 = rdao.getDayLoCheckEvening(rentDate.getValue().toString(),
					hdao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()));
		}

		// 대관 정보 등록 파트
		if (cbHallType.getSelectionModel().getSelectedItem() == null || txtCName.getText().equals("")
				|| cbGenre.getSelectionModel().getSelectedItem() == null
				|| cbRentType.getSelectionModel().getSelectedItem() == null) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("정보입력");
			alert2.setHeaderText("대관 정보입력 실패!");
			alert2.setContentText("정보를 모두 입력하세요!");
			alert2.showAndWait();
		} else if (!cbMorning.isSelected() && !cbNoon.isSelected() && !cbEvening.isSelected()) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("정보입력");
			alert2.setHeaderText("대관 정보입력 실패!");
			alert2.setContentText("정보를 모두 입력하세요!");
			alert2.showAndWait();
		} else {

			if (diff < 30) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("대관날짜");
				alert2.setHeaderText("대관 정보입력 실패!");
				alert2.setContentText("대관일은 등록일 기준 30일이상이어야 합니다!");
				alert2.showAndWait();
			} else {

				if (check1 == 1 || check2 == 1 || check3 == 1) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("대관날짜,장소");
					alert2.setHeaderText("대관 정보입력 실패!");
					alert2.setContentText("대관장소 또는 시간이 중복되었습니다.");
					alert2.showAndWait();
				} else {
					try {
						dataR.removeAll(dataR);

						// 대관금액 계산파트
						double rPrice; // 대관금액
						int morning = 0; // 오전 체크박스
						int noon = 0; // 오후 체크박스
						int evening = 0; // 저녁 체크박스
						double typePrice = 1; // 대관타입에 따른 배수
						int genrePrice; // 장르별 대관 금액
						String genre = "";
						String rMorning = "x";
						String rNoon = "x";
						String rEvening = "x";
						int equipPrice = 0; // 설비대여 총액
						double contractP = 0; // 계약 금액
						int extraTime = 0; // 초과시간
						double totalPrice = 0; // 총액

						if (cbDefault.getSelectionModel().getSelectedItem() == null) {
							cbDefault.getSelectionModel().select("미납"); // 미납여부 초기값
						}
						if (cbMorning.isSelected()) {
							morning = 1;
							rMorning = "o";
						}
						if (cbNoon.isSelected()) {
							noon = 1;
							rNoon = "o";
						}
						if (cbEvening.isSelected()) {
							evening = 1;
							rEvening = "o";
						}
						if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("공연")) {
							typePrice = 1;
						}
						if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("리허설")
								|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("철수")
								|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("준비")) {
							typePrice = 0.5;
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("순수음악/연극/클래식/무용")) {
							genre = "hi_genre1";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("째즈/퓨전음악")) {
							genre = "hi_genre2";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("뮤지컬/오페라")) {
							genre = "hi_genre3";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("대중음악/행사/방송")) {
							genre = "hi_genre4";
						}

						genrePrice = hDao.getHallPrice(cbHallType.getSelectionModel().getSelectedItem().toString(),
								genre);
						rPrice = (morning + noon + evening) * typePrice * genrePrice;

						txtHallNum.setText(
								hDao.getHallNum(cbHallType.getSelectionModel().getSelectedItem().toString()) + "");
						txtRentalPrice.setText(rPrice + "");
						txtEquipPrice.setText(aDao.getTotalEquipPrice(txtRentNum.getText()) + "");
						equipPrice = aDao.getTotalEquipPrice(txtRentNum.getText());
						txtContractP.setText(((rPrice + equipPrice) / 10) + "");
						txtExtraTime.setText(extraTime + "");
						txtTotalPrice.setText((rPrice + equipPrice) + "");

						// 등록파트

						rvo = new RentalVO(Integer.parseInt(txtHallNum.getText().trim()),
								Integer.parseInt(txtCNo.getText().trim()), txtCName.getText(),
								rentDate.getValue().toString(),
								cbRentType.getSelectionModel().getSelectedItem().toString(),
								cbGenre.getSelectionModel().getSelectedItem().toString(), rMorning, rNoon, rEvening,
								morning + noon + evening, Integer.parseInt(txtEquipPrice.getText().trim()),
								Double.parseDouble(txtRentalPrice.getText().trim()),
								Double.parseDouble(txtContractP.getText().trim()),
								cbDefault.getSelectionModel().getSelectedItem().toString(),
								Integer.parseInt(txtExtraTime.getText().trim()),
								Double.parseDouble(txtTotalPrice.getText().trim()), regitDate.getValue().toString(),
								finalDate.getValue().toString());

						rdao = new RentalDAO();
						rdao.getRentalRegiste(rvo);

						int rNum = rDao.getRentNumber(Integer.parseInt(txtCNo.getText()));
						txtRentNum.setText(rNum + "");

						if (rdao != null) {
							cbYear.setItems(FXCollections.observableArrayList(rDao.getRentDate()));
							rentTotalList();

							Alert alert3 = new Alert(AlertType.WARNING);
							alert3.setTitle("정보입력");
							alert3.setHeaderText(txtCName.getText() + " 정보가 성공적으로 추가되었습니다.");
							alert3.setContentText("다음 등록사항을 입력해 주세요!");

							alert3.showAndWait();
							rentalBlockOne();
							rentalUnBlockTwo();
							regitDate.setDisable(true);
						}

					} catch (Exception e) {
						rentTotalList();
						Alert alert4 = new Alert(AlertType.WARNING);
						alert4.setTitle("정보입력");
						alert4.setHeaderText("대관 정보입력 실패!");
						alert4.setContentText("정보입력에 실패하였습니다.!");
						alert4.showAndWait();
						System.out.println("대관 등록:" + e);
					}

				}
			}
		}
	}

	////////////////////////////////////// 대관
	////////////////////////////////////// 등록////////////////////////////////////////

	///////////////////////////////////////////////// 블락과
	///////////////////////////////////////////////// 초기화////////////////////////////////////////
	// 대여자 정보 초기화
	public void customerInit() {
		rbGeneral.setSelected(true);
		rbGeneral.setDisable(false);
		rbCompany.setDisable(false);
		txtComName.setEditable(true);
		txtCeoName.setEditable(false);
		txtNum.setEditable(false);

		txtComName.clear();
		txtNum.clear();
		txtCeoName.clear();
		txtAddr.clear();
		txtName.clear();
		txtPhone.clear();
		txtEmail.clear();
		txtTitle.clear();
		txtGenre.clear();
		txtPrice.clear();
		txtStartTime.clear();
		txtEndTime.clear();

		txtAddr.setEditable(true);
		txtName.setEditable(true);
		txtPhone.setEditable(true);
		txtEmail.setEditable(true);
		txtTitle.setEditable(true);
		txtGenre.setEditable(true);
		txtPrice.setEditable(true);
		txtStartTime.setEditable(true);
		txtEndTime.setEditable(true);

		btnRegit.setDisable(false);
	}

	// 대여자 정보 블락
	public void customerBlock() {
		btnRegit.setDisable(true);

		customerTable.setDisable(true);
		rentTable.setDisable(true);

		rbGeneral.setDisable(true);
		rbCompany.setDisable(true);
		txtComName.setEditable(false);
		txtNum.setEditable(false);
		txtCeoName.setEditable(false);
		txtAddr.setEditable(false);
		txtName.setEditable(false);
		txtPhone.setEditable(false);
		txtEmail.setEditable(false);
		txtTitle.setEditable(false);
		txtGenre.setEditable(false);
		txtPrice.setEditable(false);
		txtStartTime.setEditable(false);
		txtEndTime.setEditable(false);
	}

	// 첫번째 대관정보 블락
	public void rentalBlockOne() {
		btnNext.setDisable(true);
		btnCancel.setDisable(true);

		cbHallType.setDisable(true);
		txtCName.setEditable(false);
		txtCNo.setEditable(false);
		rentDate.setDisable(true);
		cbRentType.setDisable(true);
		cbGenre.setDisable(true);
		cbMorning.setDisable(true);
		cbNoon.setDisable(true);
		cbEvening.setDisable(true);

	}

	// 첫번째 대관정보 초기화
	public void rentalInitOne() {
		btnNext.setDisable(false);
		btnCancel.setDisable(false);

		cbHallType.getSelectionModel().select(null);
		cbRentType.getSelectionModel().select(null);
		cbGenre.getSelectionModel().select(null);
		cbMorning.setSelected(false);
		cbNoon.setSelected(false);
		cbEvening.setSelected(false);
		txtRentalPrice.clear();
		txtCName.clear();
		txtCNo.clear();
		rentDate.setValue(LocalDate.now());

		txtCName.setEditable(false);
		txtRentalPrice.setEditable(false);
		cbHallType.setDisable(false);
		rentDate.setDisable(false);
		cbRentType.setDisable(false);
		cbGenre.setDisable(false);
		cbMorning.setDisable(false);
		cbNoon.setDisable(false);
		cbEvening.setDisable(false);
	}

	// 두번째 대관정보 초기화
	public void rentalInitTwo() {

		txtRentNum.clear();
		txtHallNum.clear();
		txtEquipPrice.clear();
		txtContractP.clear();
		txtExtraTime.clear();
		txtTotalPrice.clear();
		regitDate.setValue(LocalDate.now());
		finalDate.setValue(LocalDate.now());

		btnAddEquip.setDisable(false);
		btnRentRegit.setDisable(false);
		btnRentEdit.setDisable(true);

		btnAddEquip.setDisable(false);
		txtRentNum.setEditable(false);
		txtHallNum.setEditable(false);
		txtEquipPrice.setEditable(false);
		txtContractP.setEditable(false);
		cbDefault.setValue("미납");
		cbDefault.setDisable(false);
		txtExtraTime.setEditable(true);
		txtTotalPrice.setEditable(false);
		// regitDate.setDisable(false); 등록일은 수정이 불가합니다.
		finalDate.setDisable(false);

	}

	// 두번째 대관정보 블락
	private void rentalBlockTwo() {
		btnRentRegit.setDisable(true);
		btnAddEquip.setDisable(true);

		txtHallNum.setEditable(false);
		txtEquipPrice.setEditable(false);
		txtRentalPrice.setEditable(false);
		txtContractP.setEditable(false);
		cbDefault.setDisable(true);
		txtExtraTime.setEditable(false);
		txtTotalPrice.setEditable(false);
		regitDate.setDisable(true);
		finalDate.setDisable(true);

	}

	// 두번째 대관정보 언블락
	private void rentalUnBlockTwo() {
		btnRentRegit.setDisable(false);
		btnAddEquip.setDisable(false);
		cbDefault.setDisable(false);
		txtExtraTime.setEditable(true);
		regitDate.setDisable(false);
		finalDate.setDisable(false);
		regitDate.setValue(LocalDate.now());
		finalDate.setValue(LocalDate.now());

	}

	/////////////////////////////////////////////// block &
	/////////////////////////////////////////////// init////////////////////////////////////////////

	// 극장 등록 버튼 -> 화면 이동
	public void handlerBtnHallRegitAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/hall.fxml"));
			Scene scene = new Scene(root);
			Stage subjectStage = new Stage();
			subjectStage.setTitle("극장 등록");
			subjectStage.setScene(scene);
			Stage mainStage = (Stage) btnHallRegit.getScene().getWindow();
			mainStage.close();
			subjectStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 설비등록 버튼 -> 화면 이동
	public void handlerBtnEquipRegitAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/equip.fxml"));
			Scene scene = new Scene(root);
			Stage equipStage = new Stage();
			equipStage.setTitle("설비 등록");
			equipStage.setScene(scene);

			Stage mainStage = (Stage) btnEquipRegit.getScene().getWindow();
			mainStage.close();
			equipStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////// 대여자 등록 수정 검색 전체//////////////////////
	// 대여자정보 삭제
	public void handlerBtnDeleteAction(ActionEvent event) {
		RentalDAO rDao = null;
		rDao = new RentalDAO();
		CustomerDAO cDao = null;
		cDao = new CustomerDAO();
		boolean check = false;
		check = rDao.getCNo(customerTable.getSelectionModel().getSelectedItem().getNo());
		System.out.println(check);
		if (check == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("정보삭제");
			alert.setHeaderText("이미 등록된 단체!");
			alert.setContentText("대관신청이 완료된 정보는 삭제할 수 없습니다.!");
			alert.showAndWait();
			customerInit();
			btnCusEdit.setDisable(true);
			btnDelete.setDisable(true);
			return;
		} else {
			try {
				cDao.getCusDelete(customerTable.getSelectionModel().getSelectedItem().getNo());
				data.removeAll(data);
				customerInit();
				cusTotalList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 마우스더블클릭 액션
	public void handlerCusClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {

				selectCustomer = customerTable.getSelectionModel().getSelectedItems();
				selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
				no = selectCustomer.get(0).getNo();

				if (selectCustomer.get(0).getCusType().equals("일반")) {
					rbGeneral.setSelected(true);
					rbGeneral.setDisable(true);
					rbCompany.setDisable(true);
				} else {
					rbCompany.setSelected(true);
					rbCompany.setDisable(true);
					rbGeneral.setDisable(true);
				}
				txtComName.setEditable(false);
				txtNum.setEditable(false);
				txtCeoName.setEditable(false);

				txtComName.setText(selectCustomer.get(0).getCusComname());
				txtNum.setText(selectCustomer.get(0).getCusNum());
				txtCeoName.setText(selectCustomer.get(0).getCusCeoname());
				txtAddr.setText(selectCustomer.get(0).getCusAddr());
				txtName.setText(selectCustomer.get(0).getCusName());
				txtPhone.setText(selectCustomer.get(0).getCusPhone());
				txtEmail.setText(selectCustomer.get(0).getCusEmail());
				txtTitle.setText(selectCustomer.get(0).getCusTitle());
				txtGenre.setText(selectCustomer.get(0).getCusGenre());
				txtPrice.setText(selectCustomer.get(0).getCusPrice() + "");
				txtStartTime.setText(selectCustomer.get(0).getCusStartTime());
				txtEndTime.setText(selectCustomer.get(0).getCusEndTime());
				btnRegit.setDisable(true);
				btnCusEdit.setDisable(false);
				btnDelete.setDisable(false);
				btnRegit.setDisable(false);
				btnEmail.setDisable(false);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대여자 정보를 먼저 입력하세요!");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
				btnCusEdit.setDisable(true);
				btnDelete.setDisable(true);
				btnRegit.setDisable(true);
				btnEmail.setDisable(true);
			}
		}
	}

	// 대여자 검색
	public void handlerBtnCusSearchAction(ActionEvent event) {
		CustomerVO cVo = new CustomerVO();
		CustomerDAO cDao = null;

		Object[][] totalData = null;

		String searchComName = "";
		boolean searchResult = false;

		try {
			searchComName = txtCusSearch.getText().trim();
			cDao = new CustomerDAO();
			cVo = cDao.getCustomerCheck(searchComName);

			if (searchComName.equals("")) {
				searchResult = true;

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("대여자 정보 검색");
				alert.setHeaderText("단체/회사명을 입력하세요");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}

			if (!searchComName.equals("") && (cVo != null)) {
				ArrayList<String> title;
				ArrayList<CustomerVO> list;

				title = cDao.getCusColumnName();
				int columnCount = title.size();

				list = cDao.getCustomerTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];

				if (cVo.getCusComname().equals(searchComName)) {
					txtCusSearch.clear();
					data.removeAll(data);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						cVo = list.get(index);
						if (cVo.getCusComname().equals(searchComName)) {
							data.add(cVo);
							searchResult = true;
						}
					}
				}
			}
			if (!searchResult) {
				txtCusSearch.clear();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("대여자 정보 검색");
				alert.setHeaderText("해당 대여자 정보가 없습니다");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			txtCusSearch.clear();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("대여자 정보 검색");
			alert.setHeaderText("검색에 오류발생");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
		}
	}

	// 대여자 수정 버튼
	public void handlerBtnCusEditAction(ActionEvent event) {
		if (txtComName.getText().equals("") || txtName.getText().equals("") || txtAddr.getText().equals("")
				|| txtPhone.getText().equals("") || txtEmail.getText().equals("") || txtGenre.getText().equals("")
				|| txtTitle.getText().equals("") || txtPrice.getText().equals("") || txtStartTime.getText().equals("")
				|| txtEndTime.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("정보입력");
			alert2.setHeaderText("대여자 정보입력 실패!");
			alert2.setContentText("정보를 모두 입력하세요!");
			alert2.showAndWait();
		} else {
			btnRegit.setDisable(false);
			selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
			no = selectCustomer.get(0).getNo();

			CustomerVO cvo = customerTable.getSelectionModel().getSelectedItem();
			CustomerDAO cdao = null;
			try {

				data.remove(selectedIndex);
				cvo = new CustomerVO(txtName.getText(), txtAddr.getText(), txtPhone.getText(), txtEmail.getText(),
						txtGenre.getText(), txtTitle.getText(), Integer.parseInt(txtPrice.getText().trim()),
						txtStartTime.getText(), txtEndTime.getText(), no);

				cdao = new CustomerDAO();
				cdao.getCustomerUpdate(cvo, cvo.getNo());

				data.removeAll(data);
				cusTotalList();
				btnCusEdit.setDisable(true);
				btnDelete.setDisable(true);
				if (cdao != null) {
					customerInit();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("정보수정");
					alert.setHeaderText(txtComName.getText() + " 정보가 성공적으로 수정되었습니다.");
					alert.setContentText("수정이 완료되었습니다.!");

					alert.showAndWait();

				}

			} catch (Exception e) {
				cusTotalList();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보수정");
				alert.setHeaderText("대여자 정보수정 실패!");
				alert.setContentText("정보를 모두 입력해 주세요!");
				alert.showAndWait();
				System.out.println("대여자 등록:" + e);
			}
		}
	}

	// 대여자 등록 버튼
	public void handlerBtnRegitAction(ActionEvent event) {
		String check = typeGroup.getSelectedToggle().getUserData().toString();

		switch (check) {
		case "회사":
			if (txtComName.getText().equals("") || txtNum.getText().equals("") || txtCeoName.getText().equals("")
					|| txtName.getText().equals("") || txtAddr.getText().equals("") || txtPhone.getText().equals("")
					|| txtEmail.getText().equals("") || txtGenre.getText().equals("") || txtTitle.getText().equals("")
					|| txtPrice.getText().equals("") || txtStartTime.getText().equals("")
					|| txtEndTime.getText().equals("")) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("정보입력");
				alert2.setHeaderText("대여자 정보입력 실패!");
				alert2.setContentText("정보를 모두 입력하세요!");
				alert2.showAndWait();
			} else {
				customerRegit();
			}
			return;
		case "일반":
			if (txtComName.getText().equals("") || txtName.getText().equals("") || txtAddr.getText().equals("")
					|| txtPhone.getText().equals("") || txtEmail.getText().equals("") || txtGenre.getText().equals("")
					|| txtTitle.getText().equals("") || txtPrice.getText().equals("")
					|| txtStartTime.getText().equals("") || txtEndTime.getText().equals("")) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("정보입력");
				alert2.setHeaderText("대여자 정보입력 실패!");
				alert2.setContentText("정보를 모두 입력하세요!");
				alert2.showAndWait();
			} else {
				customerRegit();
			}
			return;
		}
	}

	// 조건만족시 대여자 등록 진행
	public void customerRegit() {
		try {
			data.removeAll(data);

			CustomerVO cvo = null;
			CustomerDAO cdao = new CustomerDAO();

			cvo = new CustomerVO(typeGroup.getSelectedToggle().getUserData().toString(), txtComName.getText(),
					txtNum.getText(), txtCeoName.getText(), txtName.getText(), txtAddr.getText(), txtPhone.getText(),
					txtEmail.getText(), txtGenre.getText(), txtTitle.getText(),
					Integer.parseInt(txtPrice.getText().trim()), txtStartTime.getText(), txtEndTime.getText());

			cdao = new CustomerDAO();
			cdao.getCustomerRegiste(cvo);

			if (cdao != null) {

				cusTotalList();

				Alert alert3 = new Alert(AlertType.WARNING);
				alert3.setTitle("정보입력");
				alert3.setHeaderText(txtComName.getText() + " 정보가 성공적으로 추가되었습니다.");
				alert3.setContentText("다음 등록사항을 입력해 주세요!");

				alert3.showAndWait();

				// 블락과 초기화
				customerBlock();
				rentalInitOne();
				customerTable.setDisable(true);
				rentTable.setDisable(true);
				btnCusEdit.setDisable(true);
				btnDelete.setDisable(true);
				txtCName.setText(txtComName.getText());
				txtCNo.setText(cdao.getCusNo() + "");

			}

		} catch (Exception e) {
			cusTotalList();
			Alert alert4 = new Alert(AlertType.WARNING);
			alert4.setTitle("정보입력");
			alert4.setHeaderText("대여자 정보입력 실패!");
			alert4.setContentText("정보입력에 실패하였습니다.!");
			alert4.showAndWait();
			System.out.println("대여자 등록:" + e);
		}

	}

	// 대여자 전체 버튼
	public void handlerBtnCusTotalAction(ActionEvent event) {
		try {
			data.removeAll(data);
			cusTotalList();
		} catch (Exception e) {
		}
	}

	// 대여자 전체리스트
	public void cusTotalList() {
		Object[][] cusTotalData;

		CustomerDAO cDao = new CustomerDAO();
		CustomerVO cVo = null;
		ArrayList<String> title;
		ArrayList<CustomerVO> list;

		title = cDao.getCusColumnName();
		int columnCount = title.size();

		list = cDao.getCustomerTotal();
		int rowCount = list.size();

		cusTotalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			cVo = list.get(index);
			data.add(cVo);
		}
	}

	// 회사토글버튼
	public void handlerbCompanyAction(MouseEvent event) {
		txtCeoName.setEditable(true);
		txtNum.setEditable(true);
	}

	// 일반인 토글버튼
	public void handlerbGeneralAction(MouseEvent event) {
		txtCeoName.setEditable(false);
		txtNum.setEditable(false);
		txtCeoName.clear();
		txtNum.clear();
	}

	/////////////////////////////////// 대여자 등록///////////////////////////////////
	// 종료버튼
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

}
