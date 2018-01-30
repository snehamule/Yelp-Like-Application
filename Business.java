

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Business {
final String filePath = "yelp_business.json";
ArrayList<JSONObject> json=new ArrayList<JSONObject>();
JSONObject obj;
String line = null;
Connection con= null;
FileReader fileReader=null;
BufferedReader bufferedReader=null;
PreparedStatement preparedStatement = null;
PreparedStatement preparedStatementBusinessTiming =null;
PreparedStatement preparedStatementBusinessCategory =null;
PreparedStatement preparedStatementBusinessAttributes= null;
public void getBusinessData() throws ClassNotFoundException, SQLException, IOException{
		try {
		    fileReader = new FileReader(filePath);
		    bufferedReader = new BufferedReader(fileReader);
		    int i =0;
		    RetriveData r = new RetriveData();
		    List mainCategoryList= new ArrayList();
		    mainCategoryList=r.getBusinessCategory();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
            Set<String> hs = new HashSet<>();

			//con.prepareStatement("DELETE FROM businessTiming").executeUpdate();
			//con.prepareStatement("DELETE FROM businessCategory").executeUpdate();
			//con.prepareStatement("DELETE FROM businessAttribute").executeUpdate();
			//con.prepareStatement("DELETE FROM business").executeUpdate();
			
			 String sql = "INSERT INTO business (businessID,businessName,fullAddress,buiness_open,city,state,reviewCount,"
		        		+ "stars) VALUES (?,?,?,?,?,?,?,?)";
			preparedStatement=con.prepareStatement(sql);
			
		    String insertBusinessTimingTableSQL= "INSERT INTO businessTiming (businessID,businessDay,openTiming,closeTiming) VALUES(?,?,?,?)";
		    preparedStatementBusinessTiming = con.prepareStatement(insertBusinessTimingTableSQL);
            
            String insertBusinessCategoryTableSQL= "INSERT INTO businessSubCategory (businessID,businessMainCategory,businessSubCategory) VALUES(?,?,?)";
		    preparedStatementBusinessCategory = con.prepareStatement(insertBusinessCategoryTableSQL);
            
            
		    int x=0;
		    ArrayList subCategoryData;
		    while((line = bufferedReader.readLine()) != null) {
		        obj = (JSONObject) new JSONParser().parse(line);
		        json.add(obj);
		        System.out.println("X is"+x++);
		        String businessID =((String)obj.get("business_id"));
		        String businessName=((String)obj.get("name"));
		        String fullAddress=((String)obj.get("full_address"));
		        String open=(obj.get("open")==null) ? "false" :((String)obj.get("open").toString());
		        JSONArray data = null;
		        String city=((String)obj.get("city"));
		        String state=((String)obj.get("state"));
		        long reviewCount=((long)obj.get("review_count"));
		        double stars=((Double)obj.get("stars"));

		        preparedStatement.setString(1, businessID);
		        preparedStatement.setString(2, businessName);
		        preparedStatement.setString(3, fullAddress);
		        preparedStatement.setString(4, open);
		        preparedStatement.setString(5, city);
		        preparedStatement.setString(6, state);
		        preparedStatement.setDouble(7, (double)reviewCount);
		        preparedStatement.setDouble(8, stars);
		        preparedStatement.executeUpdate();
		        
		        JSONObject arrHour = (JSONObject) ((JSONObject)obj.get("hours"));
		        if(arrHour.size()>0){
		        	if(arrHour.get("Monday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Monday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble(((JSONObject)arrHour.get("Monday")).get("open").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble(((JSONObject)arrHour.get("Monday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Tuesday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Tuesday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble( ((JSONObject)arrHour.get("Tuesday")).get("open") .toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Tuesday")).get("close") .toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Wednesday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Wednesday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble( ((JSONObject)arrHour.get("Wednesday")).get("open") .toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Wednesday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Thursday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Thursday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble( ((JSONObject)arrHour.get("Thursday")).get("open").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Thursday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Friday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Friday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble( ((JSONObject)arrHour.get("Friday")).get("open").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Friday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Saturday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Saturday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble(((JSONObject)arrHour.get("Saturday")).get("open").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Saturday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}else if(arrHour.get("Sunday")!=null){
		        		preparedStatementBusinessTiming.setString(1, businessID);
		        		preparedStatementBusinessTiming.setString(2, "Sunday");
		        		preparedStatementBusinessTiming.setDouble(3,Double.parseDouble(((JSONObject)arrHour.get("Sunday")).get("open").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.setDouble(4,Double.parseDouble( ((JSONObject)arrHour.get("Sunday")).get("close").toString().replace(":", ".")) );
		        		preparedStatementBusinessTiming.executeUpdate();
		        	}
		        		

		                obj = (JSONObject) new JSONParser().parse(line);
		        			JSONArray categoryData= (JSONArray)obj.get("categories");
		        			subCategoryData= new ArrayList(categoryData);
		        	        List final_MainCategoryList = new ArrayList (mainCategoryList);
		        	        if(subCategoryData!=null){
		        	        	final_MainCategoryList.retainAll(subCategoryData);
		        	        }
		        	        hs.clear();
		        	        hs.addAll(final_MainCategoryList);
		        	        final_MainCategoryList.clear();
		        	        final_MainCategoryList.addAll(hs);
		        	        
		        	    	subCategoryData.removeAll(final_MainCategoryList);
		        	        businessID =((String)obj.get("business_id"));
		        	
		        	       if (final_MainCategoryList.size()>0 && subCategoryData.size()<=0){
		        	        	for(int m=0 ; m<final_MainCategoryList.size();m++){
		        					preparedStatementBusinessCategory.setString(1,businessID);
		        					preparedStatementBusinessCategory.setString(2, final_MainCategoryList.get(m).toString().trim());
		        					preparedStatementBusinessCategory.setString(3,null);
		        					preparedStatementBusinessCategory.executeUpdate();
		        			     }
		        			}else{
		        				for (int j=0 ;j< final_MainCategoryList.size();j++){
		        					for(int k=0 ; k<subCategoryData.size();k++){
		        						preparedStatementBusinessCategory.setString(1,businessID);
		        						preparedStatementBusinessCategory.setString(2,final_MainCategoryList.get(j).toString().trim());
		        						preparedStatementBusinessCategory.setString(3,subCategoryData.get(k).toString().trim());
		        						preparedStatementBusinessCategory.executeUpdate();
		        				     }
		        				}
		        			}
		        	
			           
				       

				        
				     
     
		        }				
				
		    }
		    bufferedReader.close();         
		}
		catch(Exception e){
			System.out.println("Excetion"+e);
			e.printStackTrace();
		}finally {
			preparedStatement.close();
		    preparedStatementBusinessTiming.close();
		    preparedStatementBusinessCategory.close();
		    preparedStatementBusinessAttributes.close();
		    fileReader.close();
	    	bufferedReader.close();	
			con.close();
	   }


}

}
