import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private List<Customer> customers = new ArrayList<Customer>();
    private List<Video> videos = new ArrayList<Video>();

    public DataManager() {
    }

    Customer getCustomer(String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : getCustomers()) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    void setRental(Customer foundCustomer, Rental rental) {
        List<Rental> customerRentals = foundCustomer.getRentals();
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    void updateRentalAfterReturn(Customer foundCustomer, String videoTitle) {
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        getCustomers().add(james) ;
        getCustomers().add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        getVideos().add(v1) ;
        getVideos().add(v2) ;

        james.addRental(new Rental(v1));
        james.addRental(new Rental(v2));
    }

    void addCustomer(String name) {
        Customer customer = new Customer(name) ;
        getCustomers().add(customer) ;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Video> getVideos() {
        return videos;
    }

    Video getVideo(String videoTitle) {
        Video foundVideo = null ;
        for ( Video video: getVideos()) {
            if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
                foundVideo = video ;
                break ;
            }
        }
        return foundVideo;
    }
}