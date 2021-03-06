import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = 0;
			int eachPoint = 0 ;
			int daysRented = 0;
			Video video = each.getVideo();

			daysRented = getDaysRented(each, daysRented);
			eachCharge = getEachCharge(video, daysRented, eachCharge);
			eachPoint++;

			eachPoint = getEachPoint(video, each, daysRented, eachPoint);

			result += "\t" + video.getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		showFreeCoupon(totalPoint);
		
		return result ;
	}
	
	private double getEachCharge(Video video, int daysRented, double eachCharge) {		
		
		switch (video.getPriceCode()) {
		case Video.REGULAR:
			eachCharge += 2;
			if (daysRented > 2)
				eachCharge += (daysRented - 2) * 1.5;
			break;
		case Video.NEW_RELEASE:
			eachCharge = daysRented * 3;
			break;
		}
		
		return eachCharge;
	}
	
	private int getDaysRented(Rental each, int daysRented) {
		if (each.getStatus() == Rental.Status.RETURNED) { // returned Video
			long diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - each.getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		
		return daysRented;
	}
	
	private int getEachPoint(Video video, Rental each, int daysRented, int eachPoint) {
		if ((video.getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > each.getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, video.getLateReturnPointPenalty()) ;
		
		return eachPoint;
	}
	
	private void showFreeCoupon(int totalPoint) {
		if ( totalPoint >= 10 ) {
			printFreeCoupon("one");
		}
		if ( totalPoint >= 30 ) {
			printFreeCoupon("two");
		}		
	}
	
	private void printFreeCoupon(String couponNum) {
		System.out.println("Congrat! You earned "+couponNum+" free coupon");
	}
}
