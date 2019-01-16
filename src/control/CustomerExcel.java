package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CustomerVO;

public class CustomerExcel {
	public boolean xlsxWriter(ArrayList<CustomerVO> list, String saveDir) {
		// ��ũ�� ����
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ��ũ��Ʈ ����
		XSSFSheet sheet = workbook.createSheet();
		// �� ����
		XSSFRow row = sheet.createRow(0);
		// �� ����
		XSSFCell cell;
		// ��� ���� ����
		cell = row.createCell(0);
		cell.setCellValue("��Ÿ��");
		cell = row.createCell(1);
		cell.setCellValue("��ü/ȸ���");
		cell = row.createCell(2);
		cell.setCellValue("����� ��ȣ");
		cell = row.createCell(3);
		cell.setCellValue("��ǥ�ڸ�");
		cell = row.createCell(4);
		cell.setCellValue("����ڸ�");
		cell = row.createCell(5);
		cell.setCellValue("�ּ�");
		cell = row.createCell(6);
		cell.setCellValue("����ó");
		cell = row.createCell(7);
		cell.setCellValue("�̸���");
		cell = row.createCell(8);
		cell.setCellValue("�帣");
		cell = row.createCell(9);
		cell.setCellValue("��������");
		cell = row.createCell(10);
		cell.setCellValue("����� ����");
		cell = row.createCell(11);
		cell.setCellValue("���۽ð�");
		cell = row.createCell(12);
		cell.setCellValue("����ð�");
		cell = row.createCell(13);
		cell.setCellValue("�Ϸù�ȣ");

		// ����Ʈ�� size��ŭ row�� ����
		CustomerVO vo;
		for (int rowIdx = 0; rowIdx < list.size(); rowIdx++) {
			vo = list.get(rowIdx);

			// �� ����
			row = sheet.createRow(rowIdx + 1);

			cell = row.createCell(0);
			cell.setCellValue(vo.getCusType());
			cell = row.createCell(1);
			cell.setCellValue(vo.getCusComname());
			cell = row.createCell(2);
			cell.setCellValue(vo.getCusNum());
			cell = row.createCell(3);
			cell.setCellValue(vo.getCusCeoname());
			cell = row.createCell(4);
			cell.setCellValue(vo.getCusName());
			cell = row.createCell(5);
			cell.setCellValue(vo.getCusAddr());
			cell = row.createCell(6);
			cell.setCellValue(vo.getCusPhone());
			cell = row.createCell(7);
			cell.setCellValue(vo.getCusEmail());
			cell = row.createCell(8);
			cell.setCellValue(vo.getCusGenre());
			cell = row.createCell(9);
			cell.setCellValue(vo.getCusTitle());
			cell = row.createCell(10);
			cell.setCellValue(vo.getCusPrice());
			cell = row.createCell(11);
			cell.setCellValue(vo.getCusStartTime());
			cell = row.createCell(12);
			cell.setCellValue(vo.getCusEndTime());
			cell = row.createCell(13);
			cell.setCellValue(vo.getNo());
			
		}

		// �Էµ� ���� ���Ϸ� ����
		String strReportPDFName = "customer_" + System.currentTimeMillis() + ".xlsx";
		File file = new File(saveDir + "\\" + strReportPDFName);
		FileOutputStream fos = null;

		boolean saveSuccess;
		saveSuccess = false;
		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				workbook.write(fos);
				saveSuccess = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return saveSuccess;
	}

}
