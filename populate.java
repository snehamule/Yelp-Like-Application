

import java.io.IOException;
import java.sql.SQLException;

public class populate {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException{
    	if (args.length !=4) {
    		System.out.println("Argument must contain 4 files:  business.json, review.json, checki.json, user.json");
    	}
    	if (args.length == 4){
		Business b= new Business(args[0]);
		Review r = new Review(args[1]);
		Checkin c = new Checkin(args[2]);
		Yelp_User y= new Yelp_User(args[3]);

		y.populateData();
		b.getBusinessData();
		r.populateReviewData();
		c.populateCheckinData();
       } 
 }
    
}
