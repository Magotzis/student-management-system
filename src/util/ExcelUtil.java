package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddressList;

import po.Student;
import dao.Query;
import dao.Updater;

/**
 * excel���뵼��
 * 
 * @author Magotzis D
 * @since 2015��12��9��
 */
public class ExcelUtil {

	/**
	 * ����excel�����ݿ�
	 * 
	 * @param is
	 *            ������
	 * @return �Ƿ�ɹ�
	 */
	public static Boolean importExcel(InputStream is) {
		try {
			// �򿪱�
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			// �õ�������
			int rowNum = sheet.getLastRowNum();
			Boolean flag = new Boolean(true);
			List<Student> list = new ArrayList<Student>();
			// ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
			for (int i = 1; i <= rowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				Student student = new Student();
				/**
				 * ��ȡѧ�ţ����Ƴɼ��Ĵ��ı�
				 */
				HSSFCell cell = row.getCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String studentId = cell.getStringCellValue();
				cell = row.getCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String chineseGrade = cell.getStringCellValue();
				cell = row.getCell(5);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String mathGrade = cell.getStringCellValue();
				cell = row.getCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String englishGrade = cell.getStringCellValue();
				/**
				 * �������������ж��Ƿ�����
				 */
				if (row.getCell(0).toString() == null
						|| row.getCell(0).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "������������", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (studentId == null || studentId.equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "��������ѧ��", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (Query.getStudentId(studentId)) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "��ѧ���Ѿ�����,����", "��ܰ��ʾ",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (row.getCell(2).toString() == null
						|| row.getCell(2).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "���������Ա�", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (row.getCell(3).toString() == null
						|| row.getCell(3).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "��������༶", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (chineseGrade == null || chineseGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "�����������ĳɼ�", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (mathGrade == null || mathGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "����������ѧ�ɼ�", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (englishGrade == null || englishGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "��������Ӣ��ɼ�", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!studentId.matches("\\d{10}")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "����������ȷ��ѧ��", "��ܰ��ʾ", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!chineseGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "����������ȷ�����ĳɼ�", "��ܰ��ʾ",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!mathGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "����������ȷ����ѧ�ɼ�", "��ܰ��ʾ",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!englishGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "¼��ʧ��,��" + (i + 1)
							+ "����������ȷ��Ӣ��ɼ�", "��ܰ��ʾ",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				}
				/**
				 * �ӽ�������,�����������
				 */
				student.setName(row.getCell(0).toString());
				student.setStudentId(studentId);
				student.setSex(row.getCell(2).toString());
				student.setMajor(row.getCell(3).toString());
				student.setChineseGrade(chineseGrade);
				student.setMathGrade(mathGrade);
				student.setEnglishGrade(englishGrade);
				list.add(student);
			}
			if (flag) {
				Updater.batchInsertStudent(list);
				JOptionPane.showMessageDialog(null, "¼��ɹ�", "��ܰ��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
			}
			wb.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "¼��ʱ���ִ���", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;
	}

	public static Boolean exportExcel(String url, String[] head,
			String[][] column, int index) {
		try {
			int flag = 0;
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow(flag);
			// ���ñ�ͷ
			for (int i = 0; i < head.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(head[i]);
				sheet.setColumnWidth(i, 4000);
				HSSFCellStyle cellStyle = creatHeadCell(wb);
				cell.setCellStyle(cellStyle);
			}
			// index=0��Ϊ����ģ��,index=1��Ϊ��������
			if (index == 0) {
				// ��3���Ա�
				String[] sex = { "��", "Ů" };
				CellRangeAddressList regions = new CellRangeAddressList(1, 500,
						2, 2);
				DVConstraint constraint = DVConstraint
						.createExplicitListConstraint(sex);
				HSSFDataValidation data_validation = new HSSFDataValidation(
						regions, constraint);
				sheet.addValidationData(data_validation);
				url += "\\ѧ���ɼ�����ģ��.xls";
			} else if (index == 1) {
				flag = 1; // �������µ���1��ʼ¼����Ϣ
				// ����ÿһ����Ϣ
				for (int i = 0; i < column.length; i++) {
					row = sheet.createRow(flag++);
					String[] infos = column[i];
					for (int j = 0; j < infos.length; j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(infos[j]);
						HSSFCellStyle cellStyle = creatCellStyle(wb);
						cell.setCellStyle(cellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					}
				}
			}
			File file = new File(url);
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
			wb.close();
			JOptionPane.showMessageDialog(null, "�����ɹ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����ʱ���ִ���", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;
	}

	/**
	 * ������ͷ��ʽ
	 * 
	 * @param wb
	 * @return ��ʽ
	 */
	public static HSSFCellStyle creatHeadCell(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����
		HSSFFont font = wb.createFont();
		font.setFontName("����");
		font.setFontHeightInPoints((short) 12);// ���������С
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ������ʾ
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);// �����Զ�����
		return cellStyle;
	}

	/**
	 * ���ñ�����ʽ
	 * 
	 * @param wb
	 * @return ��ʽ
	 */
	public static HSSFCellStyle creatCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����
		HSSFFont font = wb.createFont();
		font.setFontName("����");
		font.setFontHeightInPoints((short) 10);// ���������С
		cellStyle.setFont(font);
		HSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		cellStyle.setWrapText(true);// �����Զ�����
		return cellStyle;
	}
}
