package com.meijia.utils;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/*
	 * //************************************XSSF********************************
	 * ************
	 */

	public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		org.apache.poi.ss.usermodel.Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		org.apache.poi.ss.usermodel.Font f = wb.createFont();
		org.apache.poi.ss.usermodel.Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight((short) Font.BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			org.apache.poi.ss.usermodel.Row row1 = sheet.createRow(i);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				org.apache.poi.ss.usermodel.Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	/**
	 * Excel文件到List
	 * 
	 * @param fileName
	 * @param sheetIndex
	 *            // 工作表索引
	 * @param skipRows
	 *            // 跳过的表头
	 * @return
	 * @throws Exception
	 */
	public static List<Object> readToList(String fileName, InputStream in, int sheetIndex, int skipRows) throws Exception {
		List<Object> ls = new ArrayList<Object>();
		Workbook wb = loadWorkbook(fileName, in);
		if (null != wb) {
			Sheet sh = wb.getSheetAt(sheetIndex);
			int rows = sh.getPhysicalNumberOfRows();
			for (int i = skipRows; i < rows; i++) {
				Row row = sh.getRow(i);
				if (null == row) {
					break;
				}
				int cells = row.getPhysicalNumberOfCells();
				if (cells == 0) {
					continue;
				}
				List<String> r = new ArrayList<String>(cells);
				for (int c = 0; c < cells; c++) {
					row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
					r.add(row.getCell(c).getStringCellValue());
				}
				ls.add(r);
			}
		}

		return ls;
	}

	/**
	 * 读取Excel文件，支持2000与2007格式
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Workbook loadWorkbook(String fileName, InputStream in) throws Exception {
		if (null == fileName)
			return null;

		Workbook wb = null;
		if (fileName.toLowerCase().endsWith(".xls")) {
			try {
				POIFSFileSystem fs = new POIFSFileSystem(in);
				wb = new HSSFWorkbook(fs);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (fileName.toLowerCase().endsWith(".xlsx")) {
			try {
				wb = new XSSFWorkbook(in);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new Exception("不是一个有效的Excel文件");
		}
		return wb;
	}

	public static void writeToExcel(Workbook wb, OutputStream out) {
		try {
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static enum ExcelType {
		xls, xlsx;
	}

	public static Workbook listToWorkbook(List<?> rows, ExcelType type) {
		Workbook wb = null;
		if (ExcelType.xls.equals(type)) {
			wb = new HSSFWorkbook();
		} else if (ExcelType.xlsx.equals(type)) {
			wb = new XSSFWorkbook();
		} else {
			return null;
		}

		Sheet sh = wb.createSheet();
		if (null != rows) {
			for (int i = 0; i < rows.size(); i++) {
				Object obj = rows.get(i);
				Row row = sh.createRow(i);

				if (obj instanceof Collection) {
					Collection<?> r = (Collection<?>) obj;
					Iterator<?> it = r.iterator();
					int j = 0;
					while (it.hasNext()) {
						Cell cell = row.createCell(j++);
						cell.setCellValue(String.valueOf(it.next()));
					}
				} else if (obj instanceof Object[]) {
					Object[] r = (Object[]) obj;
					for (int j = 0; j < r.length; j++) {
						Cell cell = row.createCell(j);
						cell.setCellValue(String.valueOf(r[j]));
					}
				} else {
					Cell cell = row.createCell(0);
					cell.setCellValue(String.valueOf(obj));
				}
			}
		}

		return wb;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		// List<Object> rows = new ArrayList<Object>();
		//
		// List<Object> row = new ArrayList<Object>();
		// row.add("字符串");
		// row.add(11);
		// row.add(new Date());
		// row.add(1.0);
		// rows.add(((Object) row));
		//
		// rows.add("中文");
		// rows.add(new Date());
		//
		// listToWorkbook(rows, ExcelType.xls);
		// listToWorkbook(rows, ExcelType.xlsx);
		String path = "/Users/lnczx/Downloads/";
		String fileName = "批量导入员工模板文件(3).xlsx";

		InputStream in = new FileInputStream(path + fileName);
		List<Object> list = ExcelUtil.readToList(fileName, in, 0, 0);
		
		for (int i = 0; i < list.size(); i++) {
			List<String> item = (List<String>) list.get(i);
			System.out.println(item.get(0));
		}

	}

}
