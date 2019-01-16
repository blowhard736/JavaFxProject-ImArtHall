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
		// 워크북 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 워크시트 생성
		XSSFSheet sheet = workbook.createSheet();
		// 행 생성
		XSSFRow row = sheet.createRow(0);
		// 셀 생성
		XSSFCell cell;
		// 헤더 정보 구성
		cell = row.createCell(0);
		cell.setCellValue("고객타입");
		cell = row.createCell(1);
		cell.setCellValue("단체/회사명");
		cell = row.createCell(2);
		cell.setCellValue("사업자 번호");
		cell = row.createCell(3);
		cell.setCellValue("대표자명");
		cell = row.createCell(4);
		cell.setCellValue("담당자명");
		cell = row.createCell(5);
		cell.setCellValue("주소");
		cell = row.createCell(6);
		cell.setCellValue("연락처");
		cell = row.createCell(7);
		cell.setCellValue("이메일");
		cell = row.createCell(8);
		cell.setCellValue("장르");
		cell = row.createCell(9);
		cell.setCellValue("공연제목");
		cell = row.createCell(10);
		cell.setCellValue("입장권 가격");
		cell = row.createCell(11);
		cell.setCellValue("시작시간");
		cell = row.createCell(12);
		cell.setCellValue("종료시간");
		cell = row.createCell(13);
		cell.setCellValue("일련번호");

		// 리스트의 size만큼 row를 생성
		CustomerVO vo;
		for (int rowIdx = 0; rowIdx < list.size(); rowIdx++) {
			vo = list.get(rowIdx);

			// 행 생성
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

		// 입력된 내용 파일로 쓰기
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
