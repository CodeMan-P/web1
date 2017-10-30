import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
public class ExcelUtils {
	private static SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void BuildForExcelUtil(HttpServletResponse response,
			List<Object> list, String typeName, String fileName) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtils.createWorkBook(list, typeName).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String filename = fileName + sdf.format(new Date()) + ".xls";
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	/**
	 * 
	 * 组织excel
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static HSSFWorkbook createWorkBook(List<Object> list, String typeName) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建两种单元格格式
		HSSFCellStyle cs = wb.createCellStyle();
		HSSFCellStyle cs2 = wb.createCellStyle();
		// 创建两种字体
		HSSFFont f = wb.createFont();
		HSSFFont f2 = wb.createFont();
		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		// f.setBoldweight(Font.DEFAULT_CHARSET);
		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());
		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		// cs.setBorderLeft(CellStyle.BORDER_THIN);
		//
		// cs.setBorderRight(CellStyle.BORDER_THIN);
		//
		// cs.setBorderTop(CellStyle.BORDER_THIN);
		//
		// cs.setBorderBottom(CellStyle.BORDER_THIN);
		// cs.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		// cs2.setBorderLeft(CellStyle.BORDER_THIN);
		//
		// cs2.setBorderRight(CellStyle.BORDER_THIN);
		//
		// cs2.setBorderTop(CellStyle.BORDER_THIN);
		//
		// cs2.setBorderBottom(CellStyle.BORDER_THIN);
		//
		// cs2.setAlignment(CellStyle.ALIGN_CENTER);
		if ("exportChargeListExcel".equalsIgnoreCase(typeName)) {
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("用户账户流水记录");
			// 设置列宽
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 6000);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("流水号");
			cell.setCellStyle(cs);
			cell = row.createCell(1);
			cell.setCellValue("手机号");
			cell.setCellStyle(cs);
			cell = row.createCell(2);
			cell.setCellValue("金额");
			cell.setCellStyle(cs);
			cell = row.createCell(3);
			cell.setCellValue("进出帐");
			cell.setCellStyle(cs);
			cell = row.createCell(4);
			cell.setCellValue("变动日期");
			cell.setCellStyle(cs);
			cell = row.createCell(5);
			cell.setCellValue("交易类型");
			cell.setCellStyle(cs);
			cell = row.createCell(6);
			cell.setCellValue("变化前金额");
			cell.setCellStyle(cs);
			cell = row.createCell(7);
			cell.setCellValue("变化后金额");
			cell.setCellStyle(cs);
			cell = row.createCell(8);
			cell.setCellValue("备注");
			cell.setCellStyle(cs);
			for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow(i + 1);
				Map<String, Object> userCharge = (Map<String, Object>) list.get(i);
				// 第四步，创建单元格，并设置值
				HSSFCell hcell0 = row.createCell(0);
				hcell0.setCellValue(userCharge.get("ICHARGEID").toString());
				hcell0.setCellStyle(cs2);
				HSSFCell hcell1 = row.createCell(1);
				hcell1.setCellValue(userCharge.get("CMOBILENO").toString());
				hcell1.setCellStyle(cs2);
				HSSFCell hcell2 = row.createCell(2);
				hcell2.setCellValue(userCharge.get("IMONEY").toString());
				hcell2.setCellStyle(cs2);
				HSSFCell hcell3 = row.createCell(3);
				// hcell3.setCellValue(MathUtil.toInt(userCharge.get("ITYPE").toString())==0?"进账":"出账");
				hcell3.setCellStyle(cs2);
				HSSFCell hcell4 = row.createCell(4);
				hcell4.setCellValue(userCharge.get("CADDDATE").toString());
				hcell4.setCellStyle(cs2);
				HSSFCell hcell5 = row.createCell(5);
				// hcell5.setCellValue(DicUtil.getUserChargeMap().get(userCharge.get("IBIZTYPE").toString()));
				hcell5.setCellStyle(cs2);
				HSSFCell hcell6 = row.createCell(6);
				hcell6.setCellValue(userCharge.get("IOLDMONEY").toString());
				hcell6.setCellStyle(cs2);
				HSSFCell hcell7 = row.createCell(7);
				hcell7.setCellValue(userCharge.get("IBALANCE").toString());
				hcell7.setCellStyle(cs2);
				HSSFCell hcell8 = row.createCell(8);
				hcell8.setCellValue(userCharge.get("CMEMO").toString());
				hcell8.setCellStyle(cs2);
			}
		}
		if ("userFillForm".equalsIgnoreCase(typeName)) {
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("用户充值记录");
			// 设置列宽
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("订单号");
			cell.setCellStyle(cs);
			cell = row.createCell(1);
			cell.setCellValue("手机号");
			cell.setCellStyle(cs);
			cell = row.createCell(2);
			cell.setCellValue("充值金额");
			cell.setCellStyle(cs);
			cell = row.createCell(3);
			cell.setCellValue("手续费");
			cell.setCellStyle(cs);
			cell = row.createCell(4);
			cell.setCellValue("充值时间");
			cell.setCellStyle(cs);
			cell = row.createCell(5);
			cell.setCellValue("支付网关");
			cell.setCellStyle(cs);
			cell = row.createCell(6);
			cell.setCellValue("是否成功");
			cell.setCellStyle(cs);
			for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow(i + 1);
				Map<String, Object> userFill = (Map<String, Object>) list.get(i);
				// 第四步，创建单元格，并设置值
				HSSFCell hcell0 = row.createCell(0);
				hcell0.setCellValue(userFill.get("CAPPLYID").toString());
				hcell0.setCellStyle(cs2);
				HSSFCell hcell1 = row.createCell(1);
				hcell1.setCellValue(
						userFill.get("CMOBILENO").toString() == null ? "" : userFill.get("CMOBILENO").toString());
				hcell1.setCellStyle(cs2);
				HSSFCell hcell2 = row.createCell(2);
				hcell2.setCellValue(userFill.get("IMONEY").toString());
				hcell2.setCellStyle(cs2);
				HSSFCell hcell3 = row.createCell(3);
				hcell3.setCellValue(userFill.get("IRATE").toString());
				hcell3.setCellStyle(cs2);
				HSSFCell hcell4 = row.createCell(4);
				hcell4.setCellValue(userFill.get("CAPPLYDATE").toString());
				hcell4.setCellStyle(cs2);
				HSSFCell hcell5 = row.createCell(5);
				// hcell5.setCellValue(DicUtil.getBankMap().get(userFill.get("CBANKID").toString()));
				hcell5.setCellStyle(cs2);
				HSSFCell hcell6 = row.createCell(6);
				hcell6.setCellValue(userFill.get("ISUCCESS").toString() == "1" ? "充值成功"
						: userFill.get("ISUCCESS").toString() == "0" ? "未支付" : "支付失败");
				hcell6.setCellStyle(cs2);
			}
		}
		if ("exportCashExcel".equalsIgnoreCase(typeName)) {
			// 在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("用户提款记录");
			// 设置列宽
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 6000);
			sheet.setColumnWidth(9, 6000);
			sheet.setColumnWidth(10, 4000);
			sheet.setColumnWidth(11, 4000);
			sheet.setColumnWidth(12, 4000);
			sheet.setColumnWidth(13, 4000);
			sheet.setColumnWidth(14, 6000);
			sheet.setColumnWidth(15, 6000);
			sheet.setColumnWidth(16, 4000);
			sheet.setColumnWidth(17, 4000);
			// 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("订单编号");
			cell.setCellStyle(cs);
			cell = row.createCell(1);
			cell.setCellValue("电话号码");
			cell.setCellStyle(cs);
			cell = row.createCell(2);
			cell.setCellValue("真实姓名");
			cell.setCellStyle(cs);
			cell = row.createCell(3);
			cell.setCellValue("省份");
			cell.setCellStyle(cs);
			cell = row.createCell(4);
			cell.setCellValue("城市");
			cell.setCellStyle(cs);
			cell = row.createCell(5);
			cell.setCellValue("银行名称");
			cell.setCellStyle(cs);
			cell = row.createCell(6);
			cell.setCellValue("银行卡号");
			cell.setCellStyle(cs);
			cell = row.createCell(7);
			cell.setCellValue("提款金额");
			cell.setCellStyle(cs);
			cell = row.createCell(8);
			cell.setCellValue("手续费用");
			cell.setCellStyle(cs);
			cell = row.createCell(9);
			cell.setCellValue("提款时间");
			cell.setCellStyle(cs);
			cell = row.createCell(10);
			cell.setCellValue("处理人");
			cell.setCellStyle(cs);
			cell = row.createCell(11);
			cell.setCellValue("处理方式");
			cell.setCellStyle(cs);
			cell = row.createCell(12);
			cell.setCellValue("处理时间");
			cell.setCellStyle(cs);
			cell = row.createCell(13);
			cell.setCellValue("处理状态");
			cell.setCellStyle(cs);
			cell = row.createCell(14);
			cell.setCellValue("付款完成时间");
			cell.setCellStyle(cs);
			cell = row.createCell(15);
			cell.setCellValue("付款结果");
			cell.setCellStyle(cs);
			cell = row.createCell(16);
			cell.setCellValue("付款结果描述");
			cell.setCellStyle(cs);
			for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow(i + 1);
				// UserCash userCash = (UserCash) list.get(i);
				// 第四步，创建单元格，并设置值
				HSSFCell hcell0 = row.createCell(0);
				// hcell0.setCellValue(userCash.getIcashid());
				hcell0.setCellStyle(cs2);
				HSSFCell hcell1 = row.createCell(1);
				// hcell1.setCellValue(userCash.getCmobileNo());
				hcell1.setCellStyle(cs2);
//				HSSFCell hcell2 = row.createCell(2);
//				 hcell2.setCellValue(userCash.getCrealName());
//				 hcell2.setCellStyle(cs2);
//				 HSSFCell hcell3 = row.createCell(3);
//				 hcell3.setCellValue(userCash.getCbankPro()==null?"":userCash.getCbankPro());
//				 hcell3.setCellStyle(cs2);
//				 HSSFCell hcell4 = row.createCell(4);
//				 hcell4.setCellValue(userCash.getCbankCity()==null?"":userCash.getCbankCity());
//				 hcell4.setCellStyle(cs2);
//				 HSSFCell hcell5 = row.createCell(5);
//				 hcell5.setCellValue(userCash.getCbankName()==null?"":userCash.getCbankName());
//				 hcell5.setCellStyle(cs2);
//				 HSSFCell hcell6 = row.createCell(6);
//				 hcell6.setCellValue(userCash.getCbankCard()==null?"":userCash.getCbankCard());
//				 hcell6.setCellStyle(cs2);
//				 HSSFCell hcell7 = row.createCell(7);
//				 hcell7.setCellValue(userCash.getImoney());
//				 hcell7.setCellStyle(cs2);
//				 HSSFCell hcell8 = row.createCell(8);
//				 hcell8.setCellValue(userCash.getIrate());
//				 hcell8.setCellStyle(cs2);
//				 HSSFCell hcell9 = row.createCell(9);
//				 hcell9.setCellValue(sdf_detail.format(userCash.getCaddDate()));
//				 hcell9.setCellStyle(cs2);
//				 HSSFCell hcell10 = row.createCell(10);
//				 hcell10.setCellValue(userCash.getCoperator()==null?"":userCash.getCoperator());
//				 hcell10.setCellStyle(cs2);
//				 HSSFCell hcell11 = row.createCell(11);
//				 hcell11.setCellValue(userCash.getIpayType()==null?"尚未处理":"1".equalsIgnoreCase(userCash.getIpayType())?"连连代付":"2".equalsIgnoreCase(userCash.getIpayType())?"手动批复":"单条处理");
//				 hcell11.setCellStyle(cs2);
//				 HSSFCell hcell12 = row.createCell(12);
//				 hcell12.setCellValue(userCash.getChandleDate()==null?"":sdf_detail.format(userCash.getChandleDate()));
//				 hcell12.setCellStyle(cs2);
//				 HSSFCell hcell13 = row.createCell(13);
//				 hcell13.setCellValue("0".equalsIgnoreCase(userCash.getIstate())?"未处理":"1".equalsIgnoreCase(userCash.getIstate())?"处理中":"已处理");
//				 hcell13.setCellStyle(cs2);
//				 HSSFCell hcell14 = row.createCell(14);
//				 hcell14.setCellValue(userCash.getCfinshDate()==null?"":sdf_detail.format(userCash.getCfinshDate()));
//				 hcell14.setCellStyle(cs2);
//				 HSSFCell hcell15 = row.createCell(15);
//				 hcell15.setCellValue("0".equalsIgnoreCase(userCash.getIresult())?"未付款":"1".equalsIgnoreCase(userCash.getIresult())?"付款中":"2".equalsIgnoreCase(userCash.getIresult())?"提款成功":"3".equalsIgnoreCase(userCash.getIresult())?"提款失败":"提款失败(拒绝提款)");
//				 hcell15.setCellStyle(cs2);
//				 HSSFCell hcell16 = row.createCell(16);
//				 hcell16.setCellValue(userCash.getCresultDesc()==null?"":userCash.getCresultDesc());
//				 hcell16.setCellStyle(cs2);
			}
		}
		return wb;
	}
}