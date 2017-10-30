package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 2017年10月30日19:38:58
 * @author lenovo
 *
 */
public class PoiForExcel {

	static String PATH = System.getProperty("user.dir") + File.separatorChar + "data" + File.separatorChar
			+ "book2.xlsx";

	public static void main(String[] args) {
		showXsheet();
	}
	public static void setPath(String path){
		PATH = path;
	}
	public static void showXsheet() {
		File file = new File(PATH);
		XSSFWorkbook hwb = null;
		XSSFSheet hst;
		Row row = null;
		try {
			if(!file.exists()){
				System.out.println(file.getAbsolutePath()+"\n文件不存在");				
			}
			
			hwb = new XSSFWorkbook(file);
			int num = hwb.getNumberOfSheets();
			int i = 0;
			Iterator<Row> itr;
			Iterator<Cell> itc;
			while(i<=num-1){
				hst = hwb.getSheetAt(i);
				System.out.println("------------"+hst.getSheetName()+"------------");
				itr =hst.iterator();
				while(itr.hasNext()){
					row = itr.next();
					itc = row.iterator();
					while(itc.hasNext()){
						System.out.print(itc.next()+"\t");
					}
					System.out.println();
				}
				i++;
				System.out.println("----------------------------------------------");
			}
			System.out.println("down!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hwb != null){
					hwb.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public static void editXsheet() {
		File file = new File(PATH);
		XSSFWorkbook hwb = null;
		XSSFSheet hst;
		XSSFRow hsr = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if(!file.exists()){
				file.createNewFile();				
			}
			fis = new FileInputStream(file);
			hwb = new XSSFWorkbook(fis);
			fis.close();
			hst = hwb.createSheet("newSheet5");
			hsr = hst.createRow(5);
			hsr.createCell(6).setCellValue("hello");
			fos = new FileOutputStream(file);
			hwb.write(fos);
			fos.flush();
			System.out.println("down!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hwb != null){
					hwb.close();
				}
				if(fis != null){
					fis.close();
				}
				if(fos != null){
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 */
	public static synchronized LinkedList<Points> getXcol(int col,int col2,int sheet) {
		File file = new File(PATH);
		XSSFWorkbook hwb = null;
		XSSFSheet hst;
		LinkedList<Points> list = new LinkedList<Points>();
		try {
			hwb = new XSSFWorkbook(file);
			hst = hwb.getSheetAt(sheet);
			Iterator<Row> it = hst.iterator();
			Row row = null;
			double x,y;
			while(it.hasNext()){
				row = it.next();
				try{
					x = row.getCell(col).getNumericCellValue();
					y = row.getCell(col2).getNumericCellValue();
					list.add(new Points(x,y));	
				}catch(Exception e){
					System.out.println("(c1,c2,sheet):"+col+col2+sheet+"超出范围");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hwb != null) {
					hwb.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 
	 */
	public static XSSFRow getXrow(int sheet,int row) {
		File file = new File(PATH);
		XSSFWorkbook hwb = null;
		XSSFSheet hst;
		XSSFRow hsr = null;
		try {
			hwb = new XSSFWorkbook(file);
			hst = hwb.getSheetAt(sheet);
			hsr = hst.getRow(row);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hwb != null) {
					hwb.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hsr;
	}

	/**
	 * 
	 */
	public static HSSFRow getHrow(int sheet,int row) {
		File file = new File(PATH);
		POIFSFileSystem pf = null;
		HSSFWorkbook hwb = null;
		HSSFSheet hst;
		HSSFRow hsr = null;
		try {
			pf = new POIFSFileSystem(file);
			hwb = new HSSFWorkbook(pf);
			hst = hwb.getSheetAt(sheet);
			hsr = hst.getRow(row);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (hwb != null) {
					hwb.close();
				}
				if (pf != null) {
					pf.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hsr;
	}
}
