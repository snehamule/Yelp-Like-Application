
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Checkin {
	String checkinJsonFilePath="";
	//Constructor
	Checkin(String checkinJsonFilePath){
		this.checkinJsonFilePath= checkinJsonFilePath'
	}
	public void populateCheckinData() throws org.json.simple.parser.ParseException, FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException{
		
		ArrayList<JSONObject> json=new ArrayList<JSONObject>();
		    JSONObject obj;
		    String line = null;
		    Connection con= null;
		    FileReader fileReader=null;
		    BufferedReader bufferedReader=null;
		    PreparedStatement preparedStatement = null;
		    try {
		        fileReader = new FileReader(checkinJsonFilePath);
		        bufferedReader = new BufferedReader(fileReader);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				int i=0;
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				//con.prepareStatement("DELETE FROM checkin").executeUpdate();
			    String sql= "INSERT INTO checkin  (businessID,numberOfSundayCheckIn,numberOfMondayCheckIn,"
			    		+ "numberOfTuesdayCheckIn,numberOfWednesdayCheckIn,numberOfThursdayCheckIn,"
			    		+ "numberOfFriddayCheckIn,numberOfSaturdayCheckIn) VALUES(?,?,?,?,?,?,?,?)";
			    preparedStatement = con.prepareStatement(sql);

		        while((line = bufferedReader.readLine()) != null) {
		            obj = (JSONObject) new JSONParser().parse(line);
		            json.add(obj);
		            String businessID=((String)obj.get("business_id"));
		            JSONObject arr = (JSONObject)obj.get("checkin_info");
		            int[] dayArray=getChecInCountOnDay(arr.toString().replace("{","").replace("}","").trim());
		            preparedStatement.setString(1, businessID);
		            preparedStatement.setInt(2, dayArray[0]);
		            preparedStatement.setInt(3, dayArray[1]);
		            preparedStatement.setInt(4, dayArray[2]);
		            preparedStatement.setInt(5, dayArray[3]);
		            preparedStatement.setInt(6, dayArray[4]);
		            preparedStatement.setInt(7, dayArray[5]);
		            preparedStatement.setInt(8, dayArray[6]);

		            preparedStatement.executeUpdate();
		        }
		        bufferedReader.close(); 
		    }
		    
		    catch(Exception e){
		    System.out.println("Exception "+e);
		    }finally {
		    	preparedStatement.close();
		    	fileReader.close();
		    	bufferedReader.close();
		    	con.close();
			}
		    
	
}
public int[] getChecInCountOnDay(String checkinInfo){
	int sundayCounter=0;
	int mondayCounter=0;
	int tuesdayCounter=0;
	int wednesdayCounter=0;
	int thursdayCounter=0;
	int fridayCounter=0;
	int saturdayCounter=0;
	List<Integer> dayList = new ArrayList<>();
	

	String[] checkinData = checkinInfo.split("\\,");
	for(int i=0;i<checkinData.length;i++){
		String []checkinDataSplit= checkinData[i].split("\\:");
		String[] getDayFromSPlitData=checkinDataSplit[0].replace("\"", "").trim().split("\\-");
		int day=Integer.parseInt(getDayFromSPlitData[1]);
		switch(day){
			case 0:++sundayCounter;
					break;
			case 1:++mondayCounter;
					break;
			case 2:++tuesdayCounter;
					break;
			case 3:++wednesdayCounter;
					break;
			case 4:++thursdayCounter;
					break;
			case 5:++fridayCounter;
					break;
			case 6:++saturdayCounter;
					break;
		}
		
	}//For loop End
	dayList.add(sundayCounter);
	dayList.add(mondayCounter);
	dayList.add(tuesdayCounter);
	dayList.add(wednesdayCounter);
	dayList.add(thursdayCounter);
	dayList.add(fridayCounter);
	dayList.add(saturdayCounter);
	return dayList.stream().mapToInt(Integer::intValue).toArray(); 
	
}

}
