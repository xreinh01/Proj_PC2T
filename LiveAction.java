import java.util.ArrayList;
import java.util.Collections;

public class LiveAction extends Film{

	ArrayList<String> staffList;
	ArrayList<Ratings> ratings;

    public LiveAction(String name, String director, int release) {
        super(name, director, release);
		this.staffList = new ArrayList<String>();
		this.ratings = new ArrayList<Ratings>();
    }
    
	@Override
    public void addStaff(String name) {
		if(this.staffList.contains(name)) 	//hlida aby nebyl zadan samy herec/animator 2x
		{
			
		}
		else
		{
			this.staffList.add(name);
		}	
	}
	
	@Override
	public void removeStaff(String name) {
		{
			this.staffList.remove(name);
		}
	}
	
	@Override
	public ArrayList<String> getList() {
		return this.staffList;
	}
	
	@Override
    public void addRating(int mark, String comment) {
		if(1<= mark && mark <= 5)
		{
			this.ratings.add(new Ratings(mark, comment));
			Collections.sort((ratings));
		}
		else
		{
			System.err.println("Rozsah hodnoceni je 1-5.");
		}	
    }
	
	@Override
	public ArrayList<Ratings> getRatingsList() {
		return this.ratings;
	}
	
	@Override
    public int getAgeCat() {
    	return 0;
	}

	@Override
	public void setAgeCat(int ageCat) {
	}
}
