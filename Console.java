import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {
	
	static String login = null;
	static String password = null;
	static Connection con = null;
	static String sql;
	static Statement stmnt;
	static PreparedStatement pstmnt = null;
	static ResultSet rs = null;
	
	public static boolean connect() {
		con = null;
		login = "";
		password = "";
		sql = "jdbc:sqlite:Databaze.db";
		try {
			Scanner sc = new Scanner(new File("login.txt"));
			while (sc.hasNext()) {
				login = sc.next();
				password = sc.next();
				System.out.println(login + "\n" + password);
			}
			con = DriverManager.getConnection(sql, login, password);
			sc.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// odpojeni SQL
	public static void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	// vytvoreni tabulky
	public static Boolean createTable() {
		if (con == null)
			return false;

		String sql = "CREATE TABLE IF NOT EXISTS data (" + "id int PRIMARY KEY, " + "name varchar(20), "
				+ "director varchar(20), " + "release int NOT NULL, " + "ageCategory int" + ");";
		try {
			Statement stmnt = con.createStatement();
			stmnt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		Map<String, Film> filmDatabase = new HashMap<String, Film>();
	    Map<String, StaffMember> staffDatabase = new HashMap<String, StaffMember>();
		
		int volba;
		boolean type;
		String name;
		String oldName;
		String nameOfStaffMember;
		String director;
		String newLine = System.getProperty("line.separator");
		Film curObject;
		int year;
		int ageCat;
		
		boolean run=true;
		boolean run2=false;
		
		if(connect())
		{
			System.out.println("Databázi se podařilo připojit");
			
		}
		else
		{
			System.out.println("Databázi se nepodařilo připojit");
		}
		
		while(run)
		{
			System.out.println(newLine +"Vyberte pozadovanou cinnost:");
			System.out.println("1 .. vlozeni noveho filmu ");
			System.out.println("2 .. uprava filmu");
			System.out.println("3 .. odstraneni filmu ");
			System.out.println("4 .. pridani hodnoceni filmu ");
			System.out.println("5 .. vypis vsech filmu ");
			System.out.println("6 .. vyhledani filmu");
			System.out.println("7 .. vypis hercu/animatoru, kteri pracovali na vice filmech");
			System.out.println("8 .. vyhledani filmu podle herce/animatora");
			System.out.println("9 .. ulozeni filmu do souboru ");
			System.out.println("10 .. nacteni informaci ze souboru");
			System.out.println("0 .. ukonceni aplikace");
			
			volba=pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					sc.nextLine();
					System.out.println("Zadejte nazev");
					name=sc.nextLine();
					System.out.println("Zadejte rezisera");
					director=sc.nextLine();
					System.out.println("Zadejte rok vydani");
					year=Console.pouzeCelaCisla(sc);
					System.out.println("Zadejte typ filmu - hrany/animovany (true/false)");
					type=sc.nextBoolean();
					if(!type) {
						System.out.println("Zadejte doporuceny vek divaka");
						ageCat=Console.pouzeCelaCisla(sc);
						filmDatabase.put(name,curObject = new Animated(name, director, year, ageCat));
					} else filmDatabase.put(name,curObject = new LiveAction(name, director, year));
					System.out.println("Chcete doplnit film o seznam hercu/animatoru? (true/false)");
					type=sc.nextBoolean();
					if(type) {
						sc.nextLine();
						System.out.println("Zadejte jmeno herce/animatora, pro ukonceni zapisu zmacknete podruhe enter.");
						nameOfStaffMember = sc.nextLine();
						while(nameOfStaffMember!="") {
							curObject.addStaff(nameOfStaffMember);
							if (!staffDatabase.containsKey(nameOfStaffMember)) {
								staffDatabase.put(nameOfStaffMember, new StaffMember(nameOfStaffMember, name));
								} else staffDatabase.get(nameOfStaffMember).addFilm(name);
							nameOfStaffMember = sc.nextLine();
						}				
					}
					break;
				case 2:
					System.out.println("Zadejte nazev filmu, ktery chcete upravit");
					sc.nextLine();
					oldName=sc.nextLine();
					if (filmDatabase.get(oldName)!=null) {
					curObject = filmDatabase.get(oldName);
					
					run2=true;
					while(run2) {
					System.out.println("Zadejte atribut filmu, ktery chcete upravit");
					System.out.println("1 .. nazev filmu ");
					System.out.println("2 .. jmeno rezisera");
					System.out.println("3 .. rok vydani ");
					System.out.println("4 .. doporuceny vek(jen u animaku) ");
					System.out.println("5 .. pridat herce/animatora");
					System.out.println("6 .. odebrat herce/animatora");
					System.out.println("7 .. ukonceni upravy filmu");
					   switch(pouzeCelaCisla(sc))
					   {
					   case 1:
						   sc.nextLine();
						   curObject.setName(name=sc.nextLine());
						   filmDatabase.put(name, filmDatabase.remove(oldName));
						   oldName = name;
						   break;

					   case 2:
						   sc.nextLine();
						   curObject.setDirector(sc.nextLine());
						   break;

					   case 3:
						   sc.nextLine();
						   curObject.setRelease(pouzeCelaCisla(sc));
						   break;

					   case 4:
						   sc.nextLine();
						   if(curObject instanceof Animated) {
							   ((Animated) curObject).setAgeCat(pouzeCelaCisla(sc));
						   } else System.err.println("Tento film nema vekove doporuceni");
						   break;

					   case 5:
						   sc.nextLine();
							System.out.println("Zadejte jmeno herce/animatora, pro ukonceni zapisu zmacknete podruhe enter.");
							nameOfStaffMember = sc.nextLine();
							while(nameOfStaffMember!="") {
								curObject.addStaff(nameOfStaffMember);
								if (!staffDatabase.containsKey(nameOfStaffMember)) {
									staffDatabase.put(nameOfStaffMember, new StaffMember(nameOfStaffMember, oldName));
									} else staffDatabase.get(nameOfStaffMember).addFilm(oldName);
								nameOfStaffMember = sc.nextLine();
							}
						   break;
						   
					   case 6:
						   curObject.getList();
						   System.out.println("Na filmu: "+oldName+", "+curObject.getDirector()+" "+curObject.getRelease()+", pracovali:");
						   for(int i=0;i<curObject.getList().size();i++)
						   {
								String info = curObject.getList().get(i);
						    	System.out.println("Jmeno: "+info+", ");
						   }
				           System.out.println("Zadejte jmeno zamestnance, ktereho chcete odebrat.");
				           sc.nextLine();
				           nameOfStaffMember = sc.nextLine();
				           curObject.removeStaff(nameOfStaffMember);
				           staffDatabase.get(nameOfStaffMember).removeFilm(oldName);
						   break;
						  
					   case 7:
						   run2=false;
						   break;
					   
					   }
					}
					
					} else {
						System.err.println("Zadany film nebyl nalezen.");
					}
					break;
				case 3:
					System.out.println("Zadejte nazev filmu, ktery chcete smazat.");
					sc.nextLine();
					name=sc.nextLine();
					filmDatabase.remove(name);
					break;
				case 4:	
					System.out.println("Zadejte nazev filmu, kteremu chcete priradit hodnoceni.");
					sc.nextLine();
					name=sc.nextLine();
					curObject = filmDatabase.get(name);
					System.out.println("Zadejte hodnoceni - od 1-5 pro Live Action, 1-10 pro animaky.");
					int mark = pouzeCelaCisla(sc);
					System.out.println("Muzete zadat komentar k hodnoceni.");
					sc.nextLine();
					String comment = sc.nextLine();
					curObject.addRating(mark, comment);

					break;				
				case 5:
					System.out.println("List existujicich filmu:");
					filmDatabase.forEach((key, value) -> {
						System.out.println(newLine + key + ", " + value.getDirector() + ", " + value.getRelease() + ", " + value.getAgeCat());
						System.out.println("Na filmu pracovali:");
						for(int i=0;i<value.getList().size();i++)
					    {
							String info = value.getList().get(i);
					    	System.out.println("Jmeno: "+info);
					    }
				    });				
					break;
				case 6:
					System.out.println("Zadejte nazev pozadovaneho filmu.");
					sc.nextLine();
					name=sc.nextLine();
					curObject = filmDatabase.get(name);       //error kdyz film neexistuje
					System.out.println(newLine + curObject.getName() + ", " + curObject.getDirector() + ", " + curObject.getRelease() + ", " + curObject.getAgeCat());
					System.out.println("Na filmu pracovali:");
					for(int i=0;i<curObject.getList().size();i++)
				    {
						String info = curObject.getList().get(i);
				    	System.out.println("Jmeno: "+info);
				    }
					System.out.println("Hodnoceni filmu:");
					for(int j=0;j<curObject.getRatingsList().size();j++)
				    {
						Ratings info2 = curObject.getRatingsList().get(j);
				    	System.out.println("Hodnoceni: " + info2.getPoints() + " Komentar: " + info2.getComment());
				    };
					break;
				case 7:
					System.out.println("List hercu/animatoru a jejich filmu:");
					staffDatabase.forEach((key, value) -> {
						if(value.numFilm()>1) {
							System.out.println(key + ": ");
							for(int i=0;i<value.getFilms().size();i++)
						    {
								String info = value.getFilms().get(i);
						    	System.out.println("Film: "+info);
						    }
						}
					});							
					break;
				case 8:
					System.out.println("Zadejte jmeno herce/animatora, ktereho chcete najit:");
					sc.nextLine();
					name=sc.nextLine();
					StaffMember curStaff = staffDatabase.get(name);            //error kdyz herec/animator neexistuje
					for(int i=0;i<curStaff.getFilms().size();i++)
				    {
						String info = curStaff.getFilms().get(i);
				    	System.out.println("Film: "+info);
				    }
							
					break;
				case 9:
					System.out.println("Zadejte nazev filmu, ktery chcete ulozit.");
					sc.nextLine();
					name=sc.nextLine();
					curObject = filmDatabase.get(name);             //error kdyz film neexistuje
					try {
						File filmFile = new File(name + ".txt");
						if(filmFile.createNewFile()) {
							System.out.println("Soubor uspesne vytvoren.");
						}
						FileWriter filmWriter = new FileWriter(name + ".txt");
						BufferedWriter filmRewriter = new BufferedWriter(filmWriter);						
						filmRewriter.write(curObject.getName() + newLine + curObject.getDirector() + newLine + curObject.getRelease() + newLine + curObject.getAgeCat() + newLine);
						filmRewriter.write(newLine +"Jmeno animatoru/hercu:" + newLine);
						for(int i=0;i<curObject.getList().size();i++)
					    {
							String info = curObject.getList().get(i);
					    	filmRewriter.write(info + newLine);
					    }
						filmRewriter.write(newLine + "Hodnoceni: " + newLine);
						for(int j=0;j<curObject.getRatingsList().size();j++)
					    {
							Ratings info2 = curObject.getRatingsList().get(j);
							filmRewriter.write(info2.getPoints() + " Komentar: " + info2.getComment() + newLine);
					    };
						filmRewriter.close();
					} catch(IOException e) {
						System.err.println("Chyba pri praci se souborem.");
						e.printStackTrace();
					}
					break;
				case 10:
					System.out.println("Zadejte nazev filmu, ktery chcete nacist.");
					sc.nextLine();
					name=sc.nextLine();
					System.out.println("Nacetl se nasledujici film:");
					try {
						File filmFile = new File(name + ".txt");
						Scanner filmReader = new Scanner(filmFile);
						String name1 = filmReader.nextLine();
						String direc1 = filmReader.nextLine();
						int release1 = Integer.parseInt(filmReader.nextLine());
						int ageCat1 = Integer.parseInt(filmReader.nextLine());
						if(ageCat1!=0) {
							curObject = new Animated(name1, direc1, release1, ageCat1);
							} else {
							curObject = new LiveAction(name1, direc1, release1);
							}
						filmReader.nextLine();
						filmReader.nextLine();
						boolean run3 = true;
                        while(run3) {
							String filmData = filmReader.nextLine();
							curObject.addStaff(filmData);
							if(filmReader.nextLine()=="") run3 = false;
						}
                        filmReader.nextLine();
                        while(filmReader.hasNextLine()) {
							String filmData = filmReader.nextLine();
							String[] spRating = filmData.split(" ");
							curObject.addRating(Integer.parseInt(spRating[0]), spRating[2]);
						}
						filmReader.close();
						System.out.println(newLine + curObject.getName() + ", " + curObject.getDirector() + ", " + curObject.getRelease() + ", " + curObject.getAgeCat());
						System.out.println("Na filmu pracovali:");
						for(int i=0;i<curObject.getList().size();i++)
					    {
							String info = curObject.getList().get(i);
					    	System.out.println("Jmeno: "+info);
					    }
						System.out.println("Hodnoceni filmu:");
						for(int j=0;j<curObject.getRatingsList().size();j++)
					    {
							Ratings info2 = curObject.getRatingsList().get(j);
					    	System.out.println("Hodnoceni: " + info2.getPoints() + " Komentar: " + info2.getComment());
					    };
					} catch (FileNotFoundException e) {
						System.out.println("Chyba pri cteni souboru.");
						e.printStackTrace();
					}
					break;
				case 0:
					run=false;
					break;
			}
			
		}
	}

}