package club.iananderson.seasonhud.impl.seasons;

public enum SeasonList {
	SPRING(0,"spring","\uEA00"),
	SUMMER(1,"summer","\uEA01"),
	AUTUMN(2,"autumn","\uEA02"),
	FALL(3,"fall","\uEA03"),
	WINTER(4,"winter","\uEA04"),
	DRY(5,"dry","\uEA05"),
	WET(6,"wet","\uEA06");

	private final int idNum;
	private final String seasonFileName;
	private final String seasonIconChar;
	private SeasonList(int id,String fileName,String iconChar){
		this.idNum = id;
		this.seasonFileName = fileName;
		this.seasonIconChar = iconChar;
	}

	public int getId() {
		return this.idNum;
	}
	public String getFileName(){
		return this.seasonFileName;
	}
	public String getIconChar(){
		return this.seasonIconChar;
	}
}
