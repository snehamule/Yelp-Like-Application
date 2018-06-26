

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Yelp_User {
	String userJsonFilePath="";
	//Constructor
	Review(String userJsonFilePath){
		this.userJsonFilePath= userJsonFilePath'
	}
	public void populateData() throws SQLException, IOException{
		
		ArrayList<JSONObject> json=new ArrayList<JSONObject>();
		    JSONObject obj;
		    String line = null;
		    Connection con= null;
		    FileReader fileReader=null;
		    BufferedReader bufferedReader=null;
		    PreparedStatement preparedStatement = null;
		    PreparedStatement preparedFriend =null;
		    PreparedStatement pre=null;
		    try {
		        fileReader = new FileReader(userJsonFilePath);
		        bufferedReader = new BufferedReader(fileReader);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				//con.prepareStatement("DELETE FROM friends").executeUpdate();
				//con.prepareStatement("DELETE FROM yelp_user").executeUpdate();
	            String sql1="INSERT INTO yelp_user(userID,username ,yelpingSince,reviewCount,fans,"
	            		+ "average_stars,elite,funnyVote,usefulVote,coolVote,compliment_hot,"
	            		+ "compliment_more,compliment_profile,compliment_cute,compliment_list,compliment_note,compliment_plain,"
	            		+ "compliment_cool,coimpliment_funny,compliment_writer,compliment_photos) VALUES(?,?,?,"
	            		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	            
	            String insertFriendTable= "INSERT INTO friends (userID,friendID) VALUES(?,?)";
	            preparedStatement = con.prepareStatement(sql1);
	            int i=0;
	            preparedFriend = con.prepareStatement(insertFriendTable);
		        while((line = bufferedReader.readLine()) != null) {
		        	System.out.println("I is "+i++);
		            obj = (JSONObject) new JSONParser().parse(line);
		            json.add(obj);
		            String username=((String)obj.get("name"));
		            if (username.endsWith("'")){
		            	username= username.substring(0, username.length()-1);
		            }
		            if(username.contains("'")){
	            		String[] splitUserName = username.split("\\'");
	            		username=(splitUserName[0]+"''"+splitUserName[1]).toString();
	            	}
		            String userID=((String)obj.get("user_id"));
		            String yelpingSince=((String)obj.get("yelping_since"));
		            long reviewCount=((long)obj.get("review_count"));
		            JSONArray data = null;
		            data=	(JSONArray)obj.get("friends");
		            String friends=data.toString().replace("[", "").replace("]", "").trim();
		            String[] friendData=null;
		            if(friends.length()>0){
		            	friendData = friends.split("\\,");
		            }
		           int fans= (int)((long)obj.get("fans"));
		           double average_stars= ((double)obj.get("average_stars"));
		            JSONObject arr = (JSONObject)obj.get("compliments");
		            int complimentHot=(arr.get("hot")==null) ? 0 :(int)(long) arr.get("hot") ;
		            int complimentMore=(arr.get("more")==null) ? 0 :(int)(long) arr.get("more") ;
		            int complimentProfile=(arr.get("profile")==null) ? 0 :(int)(long) arr.get("profile") ;
		            int complimentCute=(arr.get("cute")==null) ? 0 :(int)(long) arr.get("cute") ;
		            int complimentList=(arr.get("photos")==null) ? 0 :(int)(long) arr.get("photos") ;
		            int complimentNote=(arr.get("funny")==null) ? 0 :(int)(long) arr.get("funny") ;
		            int complimentPlain=(arr.get("plain")==null) ? 0 :(int)(long) arr.get("plain") ;
		            int complimentCool=(arr.get("cool")==null) ? 0 :(int)(long) arr.get("cool") ;
		            int complimentFunny=(arr.get("funny")==null) ? 0 :(int)(long) arr.get("funny") ;
		            int complimentWriter =(arr.get("write")==null) ? 0 :(int)(long) arr.get("write") ;
		            int complimentPhotos=(arr.get("photos")==null) ? 0 :(int)(long) arr.get("photos") ;
		            
		            data= (JSONArray)obj.get("elite");
		            String elite= data.toString().replace("[", "").replace("]", "").trim();
		            
		            JSONObject arrVote = (JSONObject)obj.get("votes");
		            int funnyVote=(arrVote.get("funny")==null) ? 0 :(int)(long) arrVote.get("funny") ;
		            int usefulVote=(arrVote.get("useful")==null) ? 0 :(int)(long) arrVote.get("useful") ;
		            int coolVote=(arrVote.get("cool")==null) ? 0 :(int)(long) arrVote.get("cool") ;
		            
		            

					preparedStatement.setString(1, userID);
					preparedStatement.setString(2,username);
					preparedStatement.setString(3, yelpingSince);
					preparedStatement.setInt(4, (int)reviewCount);
					preparedStatement.setInt(5, fans);
					preparedStatement.setDouble(6,average_stars);
					preparedStatement.setString(7, elite);
					preparedStatement.setInt(8, funnyVote);
					preparedStatement.setInt(9, usefulVote);
					preparedStatement.setInt(10, coolVote);
					preparedStatement.setInt(11, complimentHot);
					preparedStatement.setInt(12, complimentMore);
					preparedStatement.setInt(13, complimentProfile);
					preparedStatement.setInt(14, complimentCute);
					preparedStatement.setInt(15, complimentList);
					preparedStatement.setInt(16, complimentNote);
					preparedStatement.setInt(17, complimentPlain);
					preparedStatement.setInt(18, complimentCool);
					preparedStatement.setInt(19, complimentFunny);
					preparedStatement.setInt(20, complimentWriter);
					preparedStatement.setInt(21, complimentPhotos);
					preparedStatement.executeUpdate();
					
					
					if(friends.length()>0){
					for(int j=0 ; j<friendData.length;j++){
						preparedFriend.setString(1,userID);
						preparedFriend.setString(2,friendData[j]);
						preparedFriend.executeUpdate();
					}
					}
		        }
		    }
		    catch(Exception ex) {
		        System.out.println("Excetion"+ex);                
			}finally {
					preparedStatement.close();
					bufferedReader.close();
					fileReader.close();
					con.close();
			}
		    
		}
	
}
