import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Core {
	private static String path = System.getProperty("user.home") + File.separator + "FBLA" + File.separator +"data.xlsx";

	private static ArrayList<Attendee> as = new ArrayList<Attendee>();

	public static void loadData() throws IOException {
		FileInputStream fis = new FileInputStream(new File(path));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
					String fullName = row.getCell(0).getStringCellValue();
					String[] names = fullName.split(" ");
					as.add(new Attendee(names[0], names[1], row.getRowNum()));	
			}
		wb.close();
		fis.close();

	}
	
	 static boolean checkName(String[] names) {
		
		for (int i = 0; i < as.size(); i++) {
			if (names[0].equalsIgnoreCase(as.get(i).getFirstName()) 
					&& names[1].equalsIgnoreCase(as.get(i).getLastName())){
				return true;
				
			}
		
		}
		return false;
		
		
	}
	
	public static boolean handleEntry(String name) throws IOException, InterruptedException {
		String[] names = name.split(" ");
		if (names.length != 2) {
			Server.sendResult("Error", "Please enter only your first and last name.");
			return false;
		}
		System.out.println("" + names[0] + " " +names[1]);
		if (checkName(names)) {
			addInDate(names);
			Server.sendResult("Success", "Thank you for signing in.");
			return true;
		} else {
			Server.sendResult("Error", "The database could not find your name.");
			return false;

		}
	}
	
	private static int getRowNumberOfAttendee(String[] names) {
		for (int i = 0; i < as.size(); i++) {
			if (as.get(i).getFirstName().equalsIgnoreCase(names[0]) && as.get(i).getLastName().equalsIgnoreCase(names[1])) {
				return as.get(i).getRowNumber();
			}
		}
			
			return -1;
	}
	
	private static void addInDate(String[] names) throws IOException {//make PRIVATE
		FileInputStream fis = new FileInputStream( new File(path));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet xs = wb.getSheetAt(0);
		XSSFRow row = xs.getRow(getRowNumberOfAttendee(names));
		Cell cell = null;
		for (int i = 2; i < 100;i++) {
			if (row.getCell(i) == null) {
				row.createCell(i);
				cell = row.getCell(i);
				break;
			}
		}
		cell.setCellValue(getDate());
		fis.close();
		FileOutputStream fos = new FileOutputStream(new File(path));
		wb.write(fos);
		fos.close();
		wb.close();
		
	}
	
	private static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		   //get current date time with Date()
		   Date date = new Date();
		   return dateFormat.format(date);
	}
	
}
