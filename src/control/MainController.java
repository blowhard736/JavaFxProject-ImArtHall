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
	ObservableList<CustomerVO> selectCustomer;// ���̺��� ������ ���� ����
	int selectedIndex;// ���̺��� ������ �뿩������ �ε��� ����
	int no;// ���̺��� ���õ� �뿩���� ��ȣ ����

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
	static String rentnum;// �����ȣ ���
	static String cusEmail;//�� �̸��� �ּ�
	static String cusName;//�� �̸�

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rent = rDao.getCusComName();
		rentDefault = rDao.getDefaultCusComName();
		for (int i = 0; i < rent.size(); i++) {
			try {
				rDao.getRentalCancel(rDao.getDefaultRentNo().get(i));
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("������");
				alert2.setHeaderText(rentDefault.get(i) + " ��� ���!");
				alert2.setContentText(rentDefault.get(i) + " ��ü(�Ǵ� ȸ��)�� �����ϱ��� �ݾ��� ���������ʾ� �����̳�ó���˴ϴ�.");
				alert2.showAndWait();
			} catch (Exception e) {

			}
		}
		for (int i = 0; i < rent.size(); i++) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("����� �ӹ�");
			alert2.setHeaderText(rent.get(i) + " ��� ��� �ӹ�!");
			alert2.setContentText(rent.get(i) + " ��ü(�Ǵ� ȸ��)�� ������� �������� ������ �����̳�ó���˴ϴ�.");
			alert2.showAndWait();

		}

		rentDate.setValue(LocalDate.now()); // �����->���糯¥
		regitDate.setValue(LocalDate.now());
		finalDate.setValue(LocalDate.now());
		txtCeoName.setEditable(false); // ��ǥ���̸� ���� ���Ƴ���
		txtNum.setEditable(false); // ����ڹ�ȣ ���� ����
		btnCusEdit.setDisable(true); // ������ư����
		btnRentCancel.setDisable(true);
		btnCalculated.setDisable(true);
		btnEmail.setDisable(true);
		btnDelete.setDisable(true);
		rbGeneral.setSelected(true); // �Ϲ����� üũ�Ȼ��¿��� ����
		rentalInitOne();
		rentalInitTwo();
		rentalBlockOne(); // ���ù��° ���
		rentalBlockTwo(); // ������� �ι�° ���

		cbHallType.setItems(FXCollections.observableArrayList(hDao.getHallName()));
		cbRentType.setItems(FXCollections.observableArrayList("����", "�غ�", "ö��", "���㼳"));
		cbGenre.setItems(FXCollections.observableArrayList("��������/����/Ŭ����/����", "°��/ǻ������", "������/�����", "��������/���/���"));
		cbDefault.setItems(FXCollections.observableArrayList("�ϳ�", "�̳�", "�����̳�"));
		cbYear.setItems(FXCollections.observableArrayList(rDao.getRentDate()));
		if (cbYear.getSelectionModel().getSelectedItem() == null) {
			cbYear.getSelectionModel().select("2018");
		}
		// customer table
		customerTable.setEditable(false);

		TableColumn colType = new TableColumn("��Ÿ��");
		colType.setPrefWidth(100);
		colType.setMaxWidth(500);
		colType.setCellValueFactory(new PropertyValueFactory<>("cusType"));

		TableColumn colComName = new TableColumn("��ü/ȸ���");
		colComName.setPrefWidth(100);
		colComName.setMaxWidth(500);
		colComName.setCellValueFactory(new PropertyValueFactory<>("cusComname"));

		TableColumn colNum = new TableColumn("����� ��ȣ");
		colNum.setPrefWidth(100);
		colNum.setMaxWidth(500);
		colNum.setCellValueFactory(new PropertyValueFactory<>("cusNum"));

		TableColumn colCeoName = new TableColumn("��ǥ�ڸ�");
		colCeoName.setPrefWidth(100);
		colCeoName.setMaxWidth(500);
		colCeoName.setCellValueFactory(new PropertyValueFactory<>("cusCeoname"));

		TableColumn colName = new TableColumn("����ڸ�");
		colName.setPrefWidth(100);
		colName.setMaxWidth(500);
		colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));

		TableColumn colAddr = new TableColumn("�ּ�");
		colAddr.setPrefWidth(100);
		colAddr.setMaxWidth(500);
		colAddr.setCellValueFactory(new PropertyValueFactory<>("cusAddr"));

		TableColumn colPhone = new TableColumn("����ó");
		colPhone.setPrefWidth(100);
		colPhone.setMaxWidth(500);
		colPhone.setCellValueFactory(new PropertyValueFactory<>("cusPhone"));

		TableColumn colEmail = new TableColumn("�̸���");
		colEmail.setPrefWidth(100);
		colEmail.setMaxWidth(500);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("cusEmail"));

		TableColumn colGenre = new TableColumn("�帣");
		colGenre.setPrefWidth(100);
		colGenre.setMaxWidth(500);
		colGenre.setCellValueFactory(new PropertyValueFactory<>("cusGenre"));

		TableColumn colTitle = new TableColumn("��������");
		colTitle.setPrefWidth(100);
		colTitle.setMaxWidth(500);
		colTitle.setCellValueFactory(new PropertyValueFactory<>("cusTitle"));

		TableColumn colPrice = new TableColumn("����� ����");
		colPrice.setPrefWidth(100);
		colPrice.setMaxWidth(500);
		colPrice.setCellValueFactory(new PropertyValueFactory<>("cusPrice"));

		TableColumn colStartTime = new TableColumn("���۽ð�");
		colStartTime.setPrefWidth(100);
		colStartTime.setMaxWidth(500);
		colStartTime.setCellValueFactory(new PropertyValueFactory<>("cusStartTime"));

		TableColumn colEndTime = new TableColumn("����ð�");
		colEndTime.setPrefWidth(100);
		colEndTime.setMaxWidth(500);
		colEndTime.setCellValueFactory(new PropertyValueFactory<>("cusEndTime"));

		TableColumn colNo = new TableColumn("No.");
		colNo.setPrefWidth(100);
		colNo.setCellValueFactory(new PropertyValueFactory<>("no"));

		customerTable.getColumns().addAll(colType, colComName, colNum, colCeoName, colName, colAddr, colPhone, colEmail,
				colGenre, colTitle, colPrice, colStartTime, colEndTime, colNo);
		customerTable.setItems(data);

		// �뿩�� ����
		cusTotalList();

		// customer table
		rentTable.setEditable(false);

		TableColumn colRNo = new TableColumn("�����ȣ");
		colRNo.setPrefWidth(100);
		colRNo.setMaxWidth(500);
		colRNo.setCellValueFactory(new PropertyValueFactory<>("rentalNo"));

		TableColumn colHiNo = new TableColumn("�����ȣ");
		colHiNo.setPrefWidth(100);
		colHiNo.setMaxWidth(500);
		colHiNo.setCellValueFactory(new PropertyValueFactory<>("hiNo"));

		TableColumn colCName = new TableColumn("��ü��");
		colCName.setPrefWidth(100);
		colCName.setMaxWidth(500);
		colCName.setCellValueFactory(new PropertyValueFactory<>("cusComName"));

		TableColumn colRDate = new TableColumn("�����¥");
		colRDate.setPrefWidth(100);
		colRDate.setMaxWidth(500);
		colRDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

		TableColumn colRType = new TableColumn("���Ÿ��");
		colRType.setPrefWidth(100);
		colRType.setMaxWidth(500);
		colRType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));

		TableColumn colRGenre = new TableColumn("�帣");
		colRGenre.setPrefWidth(100);
		colRGenre.setMaxWidth(500);
		colRGenre.setCellValueFactory(new PropertyValueFactory<>("rentalGenre"));

		TableColumn colMorning = new TableColumn("�������");
		colMorning.setPrefWidth(100);
		colMorning.setMaxWidth(500);
		colMorning.setCellValueFactory(new PropertyValueFactory<>("rentalMorning"));

		TableColumn colNoon = new TableColumn("���Ĵ��");
		colNoon.setPrefWidth(100);
		colNoon.setMaxWidth(500);
		colNoon.setCellValueFactory(new PropertyValueFactory<>("rentalNoon"));

		TableColumn colEvening = new TableColumn("������");
		colEvening.setPrefWidth(100);
		colEvening.setMaxWidth(500);
		colEvening.setCellValueFactory(new PropertyValueFactory<>("rentalEvening"));

		TableColumn colRCount = new TableColumn("���ȸ��");
		colRCount.setPrefWidth(100);
		colRCount.setMaxWidth(500);
		colRCount.setCellValueFactory(new PropertyValueFactory<>("rentalCount"));

		TableColumn colEPrice = new TableColumn("����ݾ�");
		colEPrice.setPrefWidth(100);
		colEPrice.setMaxWidth(500);
		colEPrice.setCellValueFactory(new PropertyValueFactory<>("rentalEquipPrice"));

		TableColumn colRPrice = new TableColumn("����ݾ�");
		colRPrice.setPrefWidth(100);
		colRPrice.setMaxWidth(500);
		colRPrice.setCellValueFactory(new PropertyValueFactory<>("rentalRentalPrice"));

		TableColumn colContractP = new TableColumn("����");
		colContractP.setPrefWidth(100);
		colContractP.setMaxWidth(500);
		colContractP.setCellValueFactory(new PropertyValueFactory<>("rentalContractP"));

		TableColumn colDefault = new TableColumn("�̳�����");
		colDefault.setPrefWidth(100);
		colDefault.setCellValueFactory(new PropertyValueFactory<>("rentalDefault"));

		TableColumn colExTime = new TableColumn("�ʰ��ð�");
		colExTime.setPrefWidth(100);
		colExTime.setCellValueFactory(new PropertyValueFactory<>("rentalExtraTime"));

		TableColumn colTPrice = new TableColumn("�� �ݾ�");
		colTPrice.setPrefWidth(100);
		colTPrice.setCellValueFactory(new PropertyValueFactory<>("rentalTotalPrice"));

		TableColumn colRegitDay = new TableColumn("�����");
		colRegitDay.setPrefWidth(100);
		colRegitDay.setCellValueFactory(new PropertyValueFactory<>("regitDate"));

		TableColumn colEditDay = new TableColumn("������");
		colEditDay.setPrefWidth(100);
		colEditDay.setCellValueFactory(new PropertyValueFactory<>("editDate"));

		rentTable.getColumns().addAll(colRNo, colHiNo, colCName, colRDate, colRType, colRGenre, colMorning, colNoon,
				colEvening, colRCount, colEPrice, colRPrice, colContractP, colDefault, colExTime, colTPrice,
				colRegitDay, colEditDay);
		rentTable.setItems(dataR);

		// ��� ����
		rentTotalList();

		//////////////////////////////////////////////////////////////////////////////////////////
		// �뿩������ ���� ��ư
		rbGeneral.setOnMouseClicked(event -> handlerbGeneralAction(event));
		rbCompany.setOnMouseClicked(event -> handlerbCompanyAction(event));
		btnCusTotal.setOnAction(event -> handlerBtnCusTotalAction(event));
		btnRegit.setOnAction(event -> handlerBtnRegitAction(event));
		btnCusSearch.setOnAction(event -> handlerBtnCusSearchAction(event));
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// ������ ���� ���̺� ��Ʈ��
		customerTable.setOnMouseClicked(event -> handlerCusClickAction(event));
		btnCusEdit.setOnAction(event -> handlerBtnCusEditAction(event));

		// ����� ��ư
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
		
		// ����,pdf�����ư
		btnExcel.setOnAction(event -> handlerBtnExcelAction(event));
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	//�̸�������
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
			emailStage.setTitle("�� ���� ����");
			emailStage.setScene(scene);

			emailStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��������� ��μ��� ��ư
	public void handlerBtnSaveFileDirAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());
			btnExcel.setDisable(false);
			btnPDF.setDisable(false);
		}
	}

	// �������ϻ���
	public void handlerBtnExcelAction(ActionEvent event) {
		CustomerDAO cdao = new CustomerDAO();
		boolean saveSuccess;

		ArrayList<CustomerVO> list;
		list = cdao.getCustomerTotal();
		CustomerExcel excelWriter = new CustomerExcel();

		// xlsx���Ͼ���
		saveSuccess = excelWriter.xlsxWriter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("���� ���� ����");
			alert.setHeaderText("�� ��� �������� ���� ����!");
			alert.setContentText("����� ���� ����!");
			alert.showAndWait();
		}
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		btnPDF.setDisable(true);
	}

	// pdf���� ����
	public void handlerBtnPDFAction(ActionEvent event) {
		try {
			// pdf document ����
			// (Rectangle pageSize,float marginLeft.float marginRight,top,bottom
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			// pdf ���� ������� ���� .pdf������ ����, ���� ��Ʈ������ ����
			String strReportPDFName = "customer_" + System.currentTimeMillis() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
			// document�� ���� pdf������ �����ֵ���
			document.open();
			// �ѱ�������Ʈ ����
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 8, Font.NORMAL);
			Font font2 = new Font(bf, 14, Font.BOLD);
			// Ÿ��Ʋ
			Paragraph title = new Paragraph("�� ����", font2);
			// �߰�����
			title.setAlignment(Element.ALIGN_CENTER);
			// ������ �߰�
			document.add(title);
			document.add(new Paragraph("\r\n"));
			// ������¥
			LocalDate date1 = LocalDate.now();
			Paragraph writeDay = new Paragraph(date1.toString(), font);
			// ������ ����
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			// ������ �߰�
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));

			// �����ڿ� �÷����� �ش�.
			PdfPTable table = new PdfPTable(9);
			table.setWidths(new int[] { 100, 120, 100, 100, 100, 200, 150, 220, 50 });
			// �÷�Ÿ��Ʋ
			PdfPCell header1 = new PdfPCell(new Paragraph("��Ÿ��", font));
			PdfPCell header2 = new PdfPCell(new Paragraph("��ü/ȸ���", font));
			PdfPCell header3 = new PdfPCell(new Paragraph("����� ��ȣ", font));
			PdfPCell header4 = new PdfPCell(new Paragraph("��ǥ�ڸ�", font));
			PdfPCell header5 = new PdfPCell(new Paragraph("����ڸ�", font));
			PdfPCell header6 = new PdfPCell(new Paragraph("�ּ�", font));
			PdfPCell header7 = new PdfPCell(new Paragraph("����ó", font));
			PdfPCell header8 = new PdfPCell(new Paragraph("�̸���", font));
			PdfPCell header9 = new PdfPCell(new Paragraph("�Ϸù�ȣ", font));
			// ��������
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header4.setHorizontalAlignment(Element.ALIGN_CENTER);
			header5.setHorizontalAlignment(Element.ALIGN_CENTER);
			header6.setHorizontalAlignment(Element.ALIGN_CENTER);
			header7.setHorizontalAlignment(Element.ALIGN_CENTER);
			header8.setHorizontalAlignment(Element.ALIGN_CENTER);
			header9.setHorizontalAlignment(Element.ALIGN_CENTER);
			// ��������
			header1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header3.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header4.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header5.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header7.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header8.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			header9.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			// ���̺� �߰�
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			table.addCell(header4);
			table.addCell(header5);
			table.addCell(header6);
			table.addCell(header7);
			table.addCell(header8);
			table.addCell(header9);
			// DB ���� �� ����Ʈ ����
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

				// ��������
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

				// ��������
				cell1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell3.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell4.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell5.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell7.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell8.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				cell9.setHorizontalAlignment(Element.ALIGN_MIDDLE);

				// ���̺� �� �߰�
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
			// ������ ���̺� �߰�
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			// ���� �ݱ� ���� ����
			document.close();

			txtSaveFileDir.clear();
		
			btnPDF.setDisable(true);
			btnExcel.setDisable(true);

			alert.setTitle("PDF ����");
			alert.setHeaderText("�뿩�� ��� PDF���� ���� ����!");
			alert.setContentText("�뿩�ڸ�� PDF ����!");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ϳ���ư
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

	// ������
	public void handlerBtnMOnthlSalesAction(ActionEvent event) {

		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnMonthlySales.getScene().getWindow());
			dialog.setTitle("���� �׷���");

			Parent parent = FXMLLoader.load(getClass().getResource("/view/monthlysales.fxml"));

			BarChart barChart = (BarChart) parent.lookup("#barChart");

			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());

			// 1��
			XYChart.Series seriesJan = new XYChart.Series();
			seriesJan.setName("1��");
			double sales1 = 0;
			ObservableList JanList = FXCollections.observableArrayList();
			ObservableList<Double> sale1 = FXCollections.observableArrayList();
			sale1 = sDao.getJanSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale1.size(); i++) {
				sales1 = sales1 + sale1.get(i);
			}
			JanList.add(new XYChart.Data("1��", sales1));
			seriesJan.setData(JanList);
			barChart.getData().add(seriesJan);

			// 2��
			XYChart.Series seriesFeb = new XYChart.Series();
			seriesFeb.setName("2��");
			double sales2 = 0;
			ObservableList FebList = FXCollections.observableArrayList();
			ObservableList<Double> sale2 = FXCollections.observableArrayList();

			sale2 = sDao.getFebSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale2.size(); i++) {
				sales2 = sales2 + sale2.get(i);
			}
			FebList.add(new XYChart.Data("2��", sales2));
			seriesFeb.setData(FebList);
			barChart.getData().add(seriesFeb);

			// 3��
			XYChart.Series seriesMar = new XYChart.Series();
			seriesMar.setName("3��");
			double sales3 = 0;
			ObservableList MarList = FXCollections.observableArrayList();
			ObservableList<Double> sale3 = FXCollections.observableArrayList();
			sale3 = sDao.getMarSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale3.size(); i++) {
				sales3 = sales3 + sale3.get(i);
			}
			MarList.add(new XYChart.Data("3��", sales3));
			seriesMar.setData(MarList);
			barChart.getData().add(seriesMar);

			// 4��
			XYChart.Series seriesApr = new XYChart.Series();
			seriesApr.setName("4��");
			double sales4 = 0;
			ObservableList AprList = FXCollections.observableArrayList();
			ObservableList<Double> sale4 = FXCollections.observableArrayList();
			sale4 = sDao.getAprSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale4.size(); i++) {
				sales4 = sales4 + sale4.get(i);
			}
			AprList.add(new XYChart.Data("4��", sales4));
			seriesApr.setData(AprList);
			barChart.getData().add(seriesApr);

			// 5��
			XYChart.Series seriesMay = new XYChart.Series();
			seriesMay.setName("5��");
			double sales5 = 0;
			ObservableList MayList = FXCollections.observableArrayList();
			ObservableList<Double> sale5 = FXCollections.observableArrayList();
			sale5 = sDao.getMaySales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale5.size(); i++) {
				sales5 = sales5 + sale5.get(i);
			}
			MayList.add(new XYChart.Data("1��", sales5));
			seriesMay.setData(MayList);
			barChart.getData().add(seriesMay);

			// 6��
			XYChart.Series seriesJun = new XYChart.Series();
			seriesJun.setName("6��");
			double sales6 = 0;
			ObservableList JunList = FXCollections.observableArrayList();
			ObservableList<Double> sale6 = FXCollections.observableArrayList();
			sale6 = sDao.getJunSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale6.size(); i++) {
				sales6 = sales6 + sale6.get(i);
			}
			JunList.add(new XYChart.Data("6��", sales6));
			seriesJun.setData(JunList);
			barChart.getData().add(seriesJun);

			// 7��
			XYChart.Series seriesJul = new XYChart.Series();
			seriesJul.setName("7��");
			double sales7 = 0;
			ObservableList JulList = FXCollections.observableArrayList();
			ObservableList<Double> sale7 = FXCollections.observableArrayList();
			sale7 = sDao.getJulSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale7.size(); i++) {
				sales7 = sales7 + sale7.get(i);
			}
			JulList.add(new XYChart.Data("7��", sales7));
			seriesJul.setData(JulList);
			barChart.getData().add(seriesJul);

			// 8��
			XYChart.Series seriesAug = new XYChart.Series();
			seriesAug.setName("8��");
			double sales8 = 0;
			ObservableList AugList = FXCollections.observableArrayList();
			ObservableList<Double> sale8 = FXCollections.observableArrayList();
			sale8 = sDao.getAugSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale8.size(); i++) {
				sales8 = sales8 + sale8.get(i);
			}
			AugList.add(new XYChart.Data("8��", sales8));
			seriesAug.setData(AugList);
			barChart.getData().add(seriesAug);

			// 9��
			XYChart.Series seriesSep = new XYChart.Series();
			seriesSep.setName("9��");
			double sales9 = 0;
			ObservableList SepList = FXCollections.observableArrayList();
			ObservableList<Double> sale9 = FXCollections.observableArrayList();
			sale9 = sDao.getSepSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale9.size(); i++) {
				sales9 = sales9 + sale9.get(i);
			}
			SepList.add(new XYChart.Data("9��", sales9));
			seriesSep.setData(SepList);
			barChart.getData().add(seriesSep);

			// 10��
			XYChart.Series seriesOct = new XYChart.Series();
			seriesOct.setName("10��");
			double sales10 = 0;
			ObservableList OctList = FXCollections.observableArrayList();
			ObservableList<Double> sale10 = FXCollections.observableArrayList();
			sale10 = sDao.getOctSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale10.size(); i++) {
				sales10 = sales10 + sale10.get(i);
			}
			OctList.add(new XYChart.Data("10��", sales10));
			seriesOct.setData(OctList);
			barChart.getData().add(seriesOct);

			// 11��
			XYChart.Series seriesNov = new XYChart.Series();
			seriesNov.setName("11��");
			double sales11 = 0;
			ObservableList NovList = FXCollections.observableArrayList();
			ObservableList<Double> sale11 = FXCollections.observableArrayList();
			sale11 = sDao.getNovSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale11.size(); i++) {
				sales11 = sales11 + sale11.get(i);
			}
			NovList.add(new XYChart.Data("11��", sales11));
			seriesNov.setData(NovList);
			barChart.getData().add(seriesNov);

			// 12��
			XYChart.Series seriesDec = new XYChart.Series();
			seriesDec.setName("12��");
			double sales12 = 0;
			ObservableList DecList = FXCollections.observableArrayList();
			ObservableList<Double> sale12 = FXCollections.observableArrayList();
			sale12 = sDao.getDecSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < sale12.size(); i++) {
				sales12 = sales12 + sale12.get(i);
			}
			DecList.add(new XYChart.Data("12��", sales12));
			seriesDec.setData(DecList);
			barChart.getData().add(seriesDec);

			// ������
			XYChart.Series seriesYear = new XYChart.Series();
			seriesYear.setName("������");
			double salesY = 0;
			ObservableList YearList = FXCollections.observableArrayList();
			ObservableList<Double> saleY = FXCollections.observableArrayList();
			saleY = sDao.getTotalSales(cbYear.getSelectionModel().getSelectedItem().toString());
			for (int i = 0; i < saleY.size(); i++) {
				salesY = salesY + saleY.get(i);
			}
			YearList.add(new XYChart.Data("������", salesY));
			seriesYear.setData(YearList);
			barChart.getData().add(seriesYear);

			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();

		} catch (IOException e) {

		}
	}

	// ������ -> �����̳�ó�� �Ѿ��� �������� ��ü
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

	// ����˻�
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
				alert.setTitle("��� ���� �˻�");
				alert.setHeaderText("��ü/ȸ����� �Է��ϼ���");
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
				alert.setTitle("��� ���� �˻�");
				alert.setHeaderText("�ش� ��� ������ �����ϴ�");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			txtCusSearch.clear();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("��� ���� �˻�");
			alert.setHeaderText("�˻��� �����߻�");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
		}
	}

	////////////////////////////////////// ���
	////////////////////////////////////// ���///////////////////////////////////////////

	// �����ҹ�ư
	public void handlerBtnCancelAction(ActionEvent event) {
		customerInit();
		rentalInitOne();
		rentalBlockOne();
		rentalInitTwo();
		rentalBlockTwo();
		customerTable.setDisable(false);
		rentTable.setDisable(false);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("�����Է�");
		alert.setHeaderText("��� ��û���!");
		alert.setContentText("���� �뿩�������� �����ϰ� �ٽ� ������ּ���!");
		alert.showAndWait();
	}

	// ������̺� ���콺 Ŭ���̺�Ʈ
	public void handlerRentClickAction(MouseEvent event) {

		if (event.getClickCount() == 2) {
			selectRental = rentTable.getSelectionModel().getSelectedItems();
			selectedRentalIndex = rentTable.getSelectionModel().getSelectedIndex();
			rentNo = selectRental.get(0).getRentalNo();
			if (selectRental.get(0).getRentalDefault().equals("�����̳�")
					|| selectRental.get(0).getRentalDefault().equals("�ϳ�")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("������ �ҷ��� �� �����ϴ�!");
				alert.setContentText("�ϳ��̳� �����̳�ó���� �����ʹ� ������ �� �����ϴ�.");
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
					alert.setTitle("�����Է�");
					System.out.println(e);
					alert.setHeaderText("��� ������ ���� �Է��ϼ���!");
					alert.setContentText("Careful with the next step!");
					alert.showAndWait();
					btnRentEdit.setDisable(true);
					btnRentCancel.setDisable(true);
					btnCalculated.setDisable(true);
				}
			}
		}
	}

	// ��� ������ư
	public void handlerBtnRentEditAction(ActionEvent event) {
		RentalDAO rdao = new RentalDAO();
		RegitHallDAO hdao = new RegitHallDAO();
		int check1 = 0;// �����,������ �ߺ��˻�
		int check2 = 0;// �����,������ �ߺ��˻�
		int check3 = 0;// �����,������ �ߺ��˻�
		int rentYear;
		int rentDay;
		int finalYear;
		int finalDay;
		int diff;// �����¥�� ��������� ����
		String rMorning = "x";
		String rNoon = "x";
		String rEvening = "x";
		boolean check = false;// ���Ƚ�� �ߺ��˻�

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
				alert2.setTitle("�����¥");
				alert2.setHeaderText("��� �����Է� ����!");
				alert2.setContentText("�����7�������� �����Ҽ� �����ϴ�!");
				alert2.showAndWait();
			} else if (check1 == 1 || check2 == 1 || check3 == 1) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�����¥,���");
				alert2.setHeaderText("��� �����Է� ����!");
				alert2.setContentText("������ �Ǵ� �ð��� �ߺ��Ǿ����ϴ�.");
				alert2.showAndWait();
			} else {
				goOnEdit();
			}
		}
	}

	// ���Ǹ����� ����
	public void goOnEdit() {
		double rentPrice;
		int equipPrice;
		double contractPrice;
		int exTime;
		double total;
		double typePrice = 1; // ���Ÿ�Կ� ���� ���
		int genrePrice; // �帣�� ��� �ݾ�
		try {
			dataR.removeAll(dataR);
			RentalVO rvo = null;
			RentalDAO rdao = new RentalDAO();
			RegitHallDAO hdao = new RegitHallDAO();

			// ����ݾ� �����Ʈ

			int morning = 0; // ���� üũ�ڽ�
			int noon = 0; // ���� üũ�ڽ�
			int evening = 0; // ���� üũ�ڽ�
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
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/����/Ŭ����/����")) {
				genre = "hi_genre1";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("°��/ǻ������")) {
				genre = "hi_genre2";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("������/�����")) {
				genre = "hi_genre3";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/���/���")) {
				genre = "hi_genre4";
			}
			if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("����")) {
				typePrice = 1;
			}
			if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("���㼳")
					|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("ö��")
					|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("�غ�")) {
				typePrice = 0.5;
			}

			genrePrice = hDao.getHallPrice(cbHallType.getSelectionModel().getSelectedItem().toString(), genre);

			rentPrice = (morning + noon + evening) * typePrice * genrePrice; // ����ݾ�
			equipPrice = aDao.getTotalEquipPrice(txtRentNum.getText()); // ����ݾ�
			contractPrice = (rentPrice + equipPrice) / 10; // ��ױ�
			exTime = Integer.parseInt(txtExtraTime.getText()); // �ʰ��ð�
			total = (rentPrice + equipPrice) + rentPrice * exTime * 0.3; // �Ѿ�

			txtRentalPrice.setText(rentPrice + "");
			txtEquipPrice.setText(equipPrice + "");
			txtContractP.setText(contractPrice + "");
			txtTotalPrice.setText(total + "");

			// �����Ʈ

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
				alert3.setTitle("�����Է�");
				alert3.setHeaderText(txtCName.getText() + " ������ ���������� �����Ǿ����ϴ�.");
				alert3.setContentText("��������� �Ϸ�Ǿ����ϴ�!");
				alert3.showAndWait();
				rentalInitOne();
				rentalInitTwo();
				rentalBlockOne(); // ���ù��° ���
				rentalBlockTwo(); // ������� �ι�° ���
				btnRegit.setDisable(false);
				btnRentCancel.setDisable(true);
				btnCalculated.setDisable(true);

			}
		} catch (Exception e) {
			rentTotalList();
			Alert alert4 = new Alert(AlertType.WARNING);
			alert4.setTitle("�����Է�");
			alert4.setHeaderText("��� ���� ����!");
			alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
			alert4.showAndWait();
			System.out.println("��� ���:" + e);
		}
	}

	// ��� ��� ��ư
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
			// ����ݾ� �����Ʈ

			int morning = 0; // ���� üũ�ڽ�
			int noon = 0; // ���� üũ�ڽ�
			int evening = 0; // ���� üũ�ڽ�
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
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/����/Ŭ����/����")) {
				genre = "hi_genre1";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("°��/ǻ������")) {
				genre = "hi_genre2";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("������/�����")) {
				genre = "hi_genre3";
			}
			if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/���/���")) {
				genre = "hi_genre4";
			}

			// �����Ʈ

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
				alert3.setTitle("�����Է�");
				alert3.setHeaderText(txtCName.getText() + " ������ ���������� �߰��Ǿ����ϴ�.");
				alert3.setContentText("�������� �Ϸ�Ǿ����ϴ�!");

				alert3.showAndWait();

				// ����� �ʱ�ȭ
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
			alert4.setTitle("�����Է�");
			alert4.setHeaderText("��� �����Է� ����!");
			alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
			alert4.showAndWait();
			System.out.println("��� ���:" + e);
		}

	}

	// �����߰� ��ư
	public void handlerBtnAddAction(ActionEvent event) {
		try {
			rentnum = txtRentNum.getText();
			Parent root = FXMLLoader.load(getClass().getResource("/view/addequip.fxml"));
			Scene scene = new Scene(root);
			Stage subjectStage = new Stage();
			subjectStage.setTitle("���� �߰�");
			subjectStage.setScene(scene);

			subjectStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������� ��ü ����Ʈ
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

	// ���� ��ư
	public void handlerBtnNextAction(ActionEvent event) {
		RentalVO rvo = null;
		RentalDAO rdao = new RentalDAO();
		RegitHallDAO hdao = new RegitHallDAO();
		int check1 = 0;	// �����,������ �ߺ��˻�
		int check2 = 0;	// �����,������ �ߺ��˻�
		int check3 = 0;	// �����,������ �ߺ��˻�
		int rentYear; 	//����� ����
		int rentDay;	//����� ��¥�� �ϼ���
		int sysYear;	//����� ����
		int sysDay;		//����� ��¥�� �ϼ���
		int diff;// �����¥�� ��������� ����
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

		// ��� ���� ��� ��Ʈ
		if (cbHallType.getSelectionModel().getSelectedItem() == null || txtCName.getText().equals("")
				|| cbGenre.getSelectionModel().getSelectedItem() == null
				|| cbRentType.getSelectionModel().getSelectedItem() == null) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("��� �����Է� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
			alert2.showAndWait();
		} else if (!cbMorning.isSelected() && !cbNoon.isSelected() && !cbEvening.isSelected()) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("��� �����Է� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
			alert2.showAndWait();
		} else {

			if (diff < 30) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�����¥");
				alert2.setHeaderText("��� �����Է� ����!");
				alert2.setContentText("������� ����� ���� 30���̻��̾�� �մϴ�!");
				alert2.showAndWait();
			} else {

				if (check1 == 1 || check2 == 1 || check3 == 1) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("�����¥,���");
					alert2.setHeaderText("��� �����Է� ����!");
					alert2.setContentText("������ �Ǵ� �ð��� �ߺ��Ǿ����ϴ�.");
					alert2.showAndWait();
				} else {
					try {
						dataR.removeAll(dataR);

						// ����ݾ� �����Ʈ
						double rPrice; // ����ݾ�
						int morning = 0; // ���� üũ�ڽ�
						int noon = 0; // ���� üũ�ڽ�
						int evening = 0; // ���� üũ�ڽ�
						double typePrice = 1; // ���Ÿ�Կ� ���� ���
						int genrePrice; // �帣�� ��� �ݾ�
						String genre = "";
						String rMorning = "x";
						String rNoon = "x";
						String rEvening = "x";
						int equipPrice = 0; // ����뿩 �Ѿ�
						double contractP = 0; // ��� �ݾ�
						int extraTime = 0; // �ʰ��ð�
						double totalPrice = 0; // �Ѿ�

						if (cbDefault.getSelectionModel().getSelectedItem() == null) {
							cbDefault.getSelectionModel().select("�̳�"); // �̳����� �ʱⰪ
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
						if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("����")) {
							typePrice = 1;
						}
						if (cbRentType.getSelectionModel().getSelectedItem().toString().equals("���㼳")
								|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("ö��")
								|| cbRentType.getSelectionModel().getSelectedItem().toString().equals("�غ�")) {
							typePrice = 0.5;
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/����/Ŭ����/����")) {
							genre = "hi_genre1";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("°��/ǻ������")) {
							genre = "hi_genre2";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("������/�����")) {
							genre = "hi_genre3";
						}
						if (cbGenre.getSelectionModel().getSelectedItem().toString().equals("��������/���/���")) {
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

						// �����Ʈ

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
							alert3.setTitle("�����Է�");
							alert3.setHeaderText(txtCName.getText() + " ������ ���������� �߰��Ǿ����ϴ�.");
							alert3.setContentText("���� ��ϻ����� �Է��� �ּ���!");

							alert3.showAndWait();
							rentalBlockOne();
							rentalUnBlockTwo();
							regitDate.setDisable(true);
						}

					} catch (Exception e) {
						rentTotalList();
						Alert alert4 = new Alert(AlertType.WARNING);
						alert4.setTitle("�����Է�");
						alert4.setHeaderText("��� �����Է� ����!");
						alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
						alert4.showAndWait();
						System.out.println("��� ���:" + e);
					}

				}
			}
		}
	}

	////////////////////////////////////// ���
	////////////////////////////////////// ���////////////////////////////////////////

	///////////////////////////////////////////////// �����
	///////////////////////////////////////////////// �ʱ�ȭ////////////////////////////////////////
	// �뿩�� ���� �ʱ�ȭ
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

	// �뿩�� ���� ���
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

	// ù��° ������� ���
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

	// ù��° ������� �ʱ�ȭ
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

	// �ι�° ������� �ʱ�ȭ
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
		cbDefault.setValue("�̳�");
		cbDefault.setDisable(false);
		txtExtraTime.setEditable(true);
		txtTotalPrice.setEditable(false);
		// regitDate.setDisable(false); ������� ������ �Ұ��մϴ�.
		finalDate.setDisable(false);

	}

	// �ι�° ������� ���
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

	// �ι�° ������� ����
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

	// ���� ��� ��ư -> ȭ�� �̵�
	public void handlerBtnHallRegitAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/hall.fxml"));
			Scene scene = new Scene(root);
			Stage subjectStage = new Stage();
			subjectStage.setTitle("���� ���");
			subjectStage.setScene(scene);
			Stage mainStage = (Stage) btnHallRegit.getScene().getWindow();
			mainStage.close();
			subjectStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������ ��ư -> ȭ�� �̵�
	public void handlerBtnEquipRegitAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/equip.fxml"));
			Scene scene = new Scene(root);
			Stage equipStage = new Stage();
			equipStage.setTitle("���� ���");
			equipStage.setScene(scene);

			Stage mainStage = (Stage) btnEquipRegit.getScene().getWindow();
			mainStage.close();
			equipStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////// �뿩�� ��� ���� �˻� ��ü//////////////////////
	// �뿩������ ����
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
			alert.setTitle("��������");
			alert.setHeaderText("�̹� ��ϵ� ��ü!");
			alert.setContentText("�����û�� �Ϸ�� ������ ������ �� �����ϴ�.!");
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

	// ���콺����Ŭ�� �׼�
	public void handlerCusClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			try {

				selectCustomer = customerTable.getSelectionModel().getSelectedItems();
				selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
				no = selectCustomer.get(0).getNo();

				if (selectCustomer.get(0).getCusType().equals("�Ϲ�")) {
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
				alert.setTitle("�����Է�");
				alert.setHeaderText("�뿩�� ������ ���� �Է��ϼ���!");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
				btnCusEdit.setDisable(true);
				btnDelete.setDisable(true);
				btnRegit.setDisable(true);
				btnEmail.setDisable(true);
			}
		}
	}

	// �뿩�� �˻�
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
				alert.setTitle("�뿩�� ���� �˻�");
				alert.setHeaderText("��ü/ȸ����� �Է��ϼ���");
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
				alert.setTitle("�뿩�� ���� �˻�");
				alert.setHeaderText("�ش� �뿩�� ������ �����ϴ�");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			txtCusSearch.clear();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�뿩�� ���� �˻�");
			alert.setHeaderText("�˻��� �����߻�");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
		}
	}

	// �뿩�� ���� ��ư
	public void handlerBtnCusEditAction(ActionEvent event) {
		if (txtComName.getText().equals("") || txtName.getText().equals("") || txtAddr.getText().equals("")
				|| txtPhone.getText().equals("") || txtEmail.getText().equals("") || txtGenre.getText().equals("")
				|| txtTitle.getText().equals("") || txtPrice.getText().equals("") || txtStartTime.getText().equals("")
				|| txtEndTime.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("�����Է�");
			alert2.setHeaderText("�뿩�� �����Է� ����!");
			alert2.setContentText("������ ��� �Է��ϼ���!");
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
					alert.setTitle("��������");
					alert.setHeaderText(txtComName.getText() + " ������ ���������� �����Ǿ����ϴ�.");
					alert.setContentText("������ �Ϸ�Ǿ����ϴ�.!");

					alert.showAndWait();

				}

			} catch (Exception e) {
				cusTotalList();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("��������");
				alert.setHeaderText("�뿩�� �������� ����!");
				alert.setContentText("������ ��� �Է��� �ּ���!");
				alert.showAndWait();
				System.out.println("�뿩�� ���:" + e);
			}
		}
	}

	// �뿩�� ��� ��ư
	public void handlerBtnRegitAction(ActionEvent event) {
		String check = typeGroup.getSelectedToggle().getUserData().toString();

		switch (check) {
		case "ȸ��":
			if (txtComName.getText().equals("") || txtNum.getText().equals("") || txtCeoName.getText().equals("")
					|| txtName.getText().equals("") || txtAddr.getText().equals("") || txtPhone.getText().equals("")
					|| txtEmail.getText().equals("") || txtGenre.getText().equals("") || txtTitle.getText().equals("")
					|| txtPrice.getText().equals("") || txtStartTime.getText().equals("")
					|| txtEndTime.getText().equals("")) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�����Է�");
				alert2.setHeaderText("�뿩�� �����Է� ����!");
				alert2.setContentText("������ ��� �Է��ϼ���!");
				alert2.showAndWait();
			} else {
				customerRegit();
			}
			return;
		case "�Ϲ�":
			if (txtComName.getText().equals("") || txtName.getText().equals("") || txtAddr.getText().equals("")
					|| txtPhone.getText().equals("") || txtEmail.getText().equals("") || txtGenre.getText().equals("")
					|| txtTitle.getText().equals("") || txtPrice.getText().equals("")
					|| txtStartTime.getText().equals("") || txtEndTime.getText().equals("")) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�����Է�");
				alert2.setHeaderText("�뿩�� �����Է� ����!");
				alert2.setContentText("������ ��� �Է��ϼ���!");
				alert2.showAndWait();
			} else {
				customerRegit();
			}
			return;
		}
	}

	// ���Ǹ����� �뿩�� ��� ����
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
				alert3.setTitle("�����Է�");
				alert3.setHeaderText(txtComName.getText() + " ������ ���������� �߰��Ǿ����ϴ�.");
				alert3.setContentText("���� ��ϻ����� �Է��� �ּ���!");

				alert3.showAndWait();

				// ����� �ʱ�ȭ
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
			alert4.setTitle("�����Է�");
			alert4.setHeaderText("�뿩�� �����Է� ����!");
			alert4.setContentText("�����Է¿� �����Ͽ����ϴ�.!");
			alert4.showAndWait();
			System.out.println("�뿩�� ���:" + e);
		}

	}

	// �뿩�� ��ü ��ư
	public void handlerBtnCusTotalAction(ActionEvent event) {
		try {
			data.removeAll(data);
			cusTotalList();
		} catch (Exception e) {
		}
	}

	// �뿩�� ��ü����Ʈ
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

	// ȸ����۹�ư
	public void handlerbCompanyAction(MouseEvent event) {
		txtCeoName.setEditable(true);
		txtNum.setEditable(true);
	}

	// �Ϲ��� ��۹�ư
	public void handlerbGeneralAction(MouseEvent event) {
		txtCeoName.setEditable(false);
		txtNum.setEditable(false);
		txtCeoName.clear();
		txtNum.clear();
	}

	/////////////////////////////////// �뿩�� ���///////////////////////////////////
	// �����ư
	public void handlerBtnExitAction(ActionEvent event) {
		Platform.exit();
	}

}
