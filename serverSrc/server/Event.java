package server;

public class Event {
	private int id;
	private String name;
	private String place;
	private String date;//another type
	private String linkEvent;
	private String linkPhoto;
	private int numGoing;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLinkEvent() {
		return linkEvent;
	}
	public void setLinkEvent(String linkEvent) {
		this.linkEvent = linkEvent;
	}
	public String getLinkPhoto() {
		return linkPhoto;
	}
	public void setLinkPhoto(String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}
	public int getNumGoing() {
		return numGoing;
	}
	public void setNumGoing(int numGoing) {
		this.numGoing = numGoing;
	}
	
	public Event() {
		
	}
	
	public Event(int id, String name, String place, String date, String linkEvent, String linkPhoto, int numGoing) {
		this.id = id;
		this.name = name;
		this.place = place;
		this.date = date;
		this.linkEvent = linkEvent;
		this.linkPhoto = linkPhoto;
		this.numGoing = numGoing;
	}
	

}
