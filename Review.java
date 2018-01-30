

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Review {
	public void populateReviewData() throws org.json.simple.parser.ParseException, FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException{
		final String filePath = "yelp_review.json";
		ArrayList<JSONObject> json=new ArrayList<JSONObject>();
		    JSONObject obj;
		    String line = null;
		    Connection con= null;
		    FileReader fileReader=null;
		    BufferedReader bufferedReader=null;
		    PreparedStatement preparedStatement = null;
		    try {
		    	fileReader = new FileReader(filePath);
		    	bufferedReader = new BufferedReader(fileReader);
		        int i =0;
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				//con.prepareStatement("DELETE FROM review").executeUpdate();
				System.out.println("Review Count"+i++);
				String sql = "INSERT INTO review (reviewID,userID,businessID,reviewDate,stars,voteUseful,"
						+ "voteFunny,voteCool,text )VALUES(?,?,?,?,?,?,?,?,?)";
	            preparedStatement = con.prepareStatement(sql);
	            int k=0;
	            String reviewID=null;
	            String userID=null;
	            String businessID=null;
	            long stars=0;
	            String date=null;
	            String text=null;
	            JSONObject arr=null;
	            long funnyVote=0;
	            long usefulVote=0;
	            long coolVote=0;
		        while((line = bufferedReader.readLine()) != null) {
		            obj = (JSONObject) new JSONParser().parse(line);
		            json.add(obj);
		            reviewID=((String)obj.get("review_id"));
		            userID=((String)obj.get("user_id"));
		            businessID=((String)obj.get("business_id"));
		            stars=((long)obj.get("stars"));
		            date=((String)obj.get("date"));
		            text=((String)obj.get("text"));
		            arr = (JSONObject)obj.get("votes");
		            funnyVote=(long) arr.get("funny") ;
		            usefulVote=(long) arr.get("useful") ;
		            coolVote=(long) arr.get("cool") ;
		            

		            preparedStatement.setString(1, reviewID);
		            preparedStatement.setString(2, userID);
		            preparedStatement.setString(3, businessID);
		            preparedStatement.setDate(4,  java.sql.Date.valueOf(date));
		            preparedStatement.setDouble(5,(double)stars);
		            preparedStatement.setInt(6,(int)usefulVote);
		            preparedStatement.setInt(7,(int)funnyVote);
		            preparedStatement.setInt(8,(int)coolVote);
		            preparedStatement.setString(9, text);
		            preparedStatement.executeUpdate();
		        }
		        bufferedReader.close();         
		    }
		    catch(FileNotFoundException ex) {
		        System.out.println("Unable to open file '" + filePath + "'");                
		    }
		    catch(IOException ex) {
		        System.out.println("Error reading file '" + filePath + "'");                  
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }finally {
		    	preparedStatement.close();
		    	fileReader.close();
		    	bufferedReader.close();
		    	con.close();
			}
		    
	}
}
	

