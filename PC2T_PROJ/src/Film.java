import java.util.ArrayList;

public abstract class Film {

    private String name;
    private String director;
    private int release;
    protected int ageCat;

    public Film (String name, String director, int release) {
        this.name = name;
        this.director = director;
        this.release = release;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getRelease() {
		return release;
	}

	public void setRelease(int release) {
		this.release = release;
	}
    
    abstract void addStaff(String name2);
    abstract void removeStaff(String name3);
    abstract void addRating(int mark, String comment);
    abstract int getAgeCat();
	abstract void setAgeCat(int ageCat);
    protected abstract ArrayList<String> getList();
    protected abstract ArrayList<Ratings> getRatingsList();

}