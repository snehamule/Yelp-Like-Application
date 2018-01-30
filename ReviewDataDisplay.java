

public class ReviewDataDisplay {

	String reviewDate;
	String stars;
	String text;
	String userID;
	double voteUseful;
	public ReviewDataDisplay(String reviewDate, String stars, String text,
			String userID, double voteUseful) {
		this.reviewDate= reviewDate;
		this.stars= stars ;
		this.text=text;
		this.userID= userID;
		this. voteUseful= voteUseful;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public double getVoteUseful() {
		return voteUseful;
	}
	public void setVoteUseful(double voteUseful) {
		this.voteUseful = voteUseful;
	}

}
