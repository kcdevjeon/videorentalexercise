import java.util.Date;

public class Rental {
	private Video video ;
	enum Status {RENTED, RETURNED};
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = Status.RENTED ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == Status.RETURNED ) {
			this.status = Status.RETURNED;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		if (getStatus() == Status.RETURNED) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = getDaysRented(diff);
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = getDaysRented(diff);
		}
		if ( daysRented <= 2) return limit ;

		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;
	}

	private int getDaysRented(long diff) {
		return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
	}
}
