

public class BusinessDataDisplay {
	String businessID;
	String businessName;
	String city;
	String state;
	double stars;
	double reviewCount;
	
	
	public BusinessDataDisplay(String businessID, String businessName, String city, String state, double stars,
			double reviewCount) {
		
		this.businessID = businessID;
		this.businessName = businessName;
		this.city =city;
		this.state = state;
		this.stars = stars;
		this.reviewCount = reviewCount;
		
			
	}


	public String getBusinessID() {
		return businessID;
	}


	public String getBusinessName() {
		return businessName;
	}



	public String getCity() {
		return city;
	}


	public String getState() {
		return state;
	}


	public double getStars() {
		return stars;
	}


	public double getReviewCount() {
		return reviewCount;
	}


	
	
	
}
