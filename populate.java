

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;


public class populate {
	String yelpUSerJson ;
    String businessJson;
    String reviewJson;
    String checkinJson;
	
	 populate(String businessJson,String reviewJson, String checkinJson,String yelpUSerJson){
		 this. businessJson= businessJson;
		 this.reviewJson=reviewJson;
		 this.checkinJson=checkinJson;
		 this.yelpUSerJson=yelpUSerJson;
     }
	 

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException{
   	String yelp_business="yelp_business.json";
    	String yelp_review="yelp_review.json";
    	String yelp_checkin="yelp_checkin.json";
    	String yelp_user="yelp_user.json";
       
    	//populate p = new populate (yelp_business, yelp_review,yelp_checkin, yelp_user);
    	
    	Business b= new Business();
    	Review r = new Review();
    	Checkin c = new Checkin();
    	YelpUser y= new YelpUser();

    	
    	
    	//y.populateData();
    	//b.getBusinessData();
    	//r.populateReviewData();
    	c.populateCheckinData();

        
       
    }
    
}
