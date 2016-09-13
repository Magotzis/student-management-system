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
 * excel导入导出
 * 
 * @author Magotzis D
 * @since 2015年12月9日
 */
public class ExcelUtil {

	/**
	 * 导入excel进数据库
	 * 
	 * @param is
	 *            输入流
	 * @return 是否成功
	 */
	public static Boolean importExcel(InputStream is) {
		try {
			// 打开表
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			Boolean flag = new Boolean(true);
			List<Student> list = new ArrayList<Student>();
			// 正文内容应该从第二行开始,第一行为表头的标题
			for (int i = 1; i <= rowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				Student student = new Student();
				/**
				 * 获取学号，各科成绩的纯文本
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
				 * 检查输入情况，判断是否有误
				 */
				if (row.getCell(0).toString() == null
						|| row.getCell(0).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入姓名", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (studentId == null || studentId.equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入学号", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (Query.getStudentId(studentId)) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行学号已经存在,请检查", "温馨提示",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (row.getCell(2).toString() == null
						|| row.getCell(2).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入性别", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (row.getCell(3).toString() == null
						|| row.getCell(3).toString().equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入班级", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (chineseGrade == null || chineseGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入语文成绩", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (mathGrade == null || mathGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入数学成绩", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (englishGrade == null || englishGrade.equals("")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入英语成绩", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!studentId.matches("\\d{10}")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入正确的学号", "温馨提示", JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!chineseGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入正确的语文成绩", "温馨提示",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!mathGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入正确的数学成绩", "温馨提示",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				} else if (!englishGrade
						.matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
					JOptionPane.showMessageDialog(null, "录入失败,第" + (i + 1)
							+ "行请输入正确的英语成绩", "温馨提示",
							JOptionPane.WARNING_MESSAGE);
					flag = false;
				}
				/**
				 * 加进集合里,最后批量插入
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
				JOptionPane.showMessageDialog(null, "录入成功", "温馨提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			wb.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "录入时出现错误", "温馨提示",
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
			// 设置表头
			for (int i = 0; i < head.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(head[i]);
				sheet.setColumnWidth(i, 4000);
				HSSFCellStyle cellStyle = creatHeadCell(wb);
				cell.setCellStyle(cellStyle);
			}
			// index=0则为下载模板,index=1则为导出数据
			if (index == 0) {
				// 第3列性别
				String[] sex = { "男", "女" };
				CellRangeAddressList regions = new CellRangeAddressList(1, 500,
						2, 2);
				DVConstraint constraint = DVConstraint
						.createExplicitListConstraint(sex);
				HSSFDataValidation data_validation = new HSSFDataValidation(
						regions, constraint);
				sheet.addValidationData(data_validation);
				url += "\\学生成绩导入模板.xls";
			} else if (index == 1) {
				flag = 1; // 行数重新调回1开始录入信息
				// 导出每一行信息
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
			JOptionPane.showMessageDialog(null, "导出成功", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "导出时出现错误", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;
	}

	/**
	 * 创建表头样式
	 * 
	 * @param wb
	 * @return 样式
	 */
	public static HSSFCellStyle creatHeadCell(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);// 设置自动换行
		return cellStyle;
	}

	/**
	 * 设置表中样式
	 * 
	 * @param wb
	 * @return 样式
	 */
	public static HSSFCellStyle creatCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
		HSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		cellStyle.setWrapText(true);// 设置自动换行
		return cellStyle;
	}
}
