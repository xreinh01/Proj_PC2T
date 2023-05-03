import java.util.ArrayList;

public class StaffMember {
	private String name;
	ArrayList<String> films;
	
	StaffMember(String name, String filmName){
		this.name = name;
		this.films = new ArrayList<String>();
		this.films.add(filmName);
	}
	
	public String getName() {
		return name;
	}
	
	public int numFilm() {
		return films.size();
	}

	public ArrayList<String> getFilms() {
		return films;
	}
	
	public void addFilm(String filmName) {
		films.add(filmName);
	}
	
	public void removeFilm(String filmName) {
		films.remove(filmName);
	}
}

