//------------------------------------------------------------------------------
//This is the club member class.  This class holds membership info
//	that gets loaded from the database
//
//------------------------------------------------------------------------------
import java.math.BigDecimal;
import java.util.ArrayList;

public class ClubMember{

	//Private variables for member info
	//
	private String memberFirstName;
	private String memberLastName;
	private String memberID;
	private String memberStatus;
	private String memberPassword;
	private String spouse;
	private String email;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String memberSince;
	private ArrayList<String> statusList;
	private BigDecimal memberPrice;



	//Default constructor
	//
	public ClubMember(){
		memberFirstName = new String("First Name");
		memberLastName = new String("Last Name");
		memberID = new String("");
		memberStatus = new String("Member Type");
		memberPassword = new String("");
		spouse = new String("Spouse");
		email = new String("Email");
		address = new String("Address");
		city = new String("City");
		state = new String("State");
		zip = new String("Zip");
		phone = new String("Phone");
		memberSince = new String("");
		memberPrice = new BigDecimal(1);
		statusList = new ArrayList<String>();
		statusList.add(memberStatus);
	}

	//List constructor
	//  This constructor is used when only names/id numbers are loaded from the database.
	//  passwords will only be loaded in order to verify
	//
	public ClubMember(String firstName, String lastName, String ID){

		memberFirstName = new String(firstName);
		memberLastName = new String(lastName);
		memberID = new String(ID);
		memberStatus = new String("");
		memberPassword = new String("");
		spouse = new String("");
		email = new String("");
		address = new String("");
		city = new String("");
		state = new String("");
		zip = new String("");
		phone = new String("");
		memberSince = new String("");
		memberPrice = new BigDecimal(1);
		statusList = new ArrayList<String>();
		statusList.add(memberStatus);
	}


	//Primary constructor
	//
	public ClubMember(String firstName, String lastName, String ID, String status, String password,
			String setSpouse, String setEmail, String setAddress, String setCity, String setState,
			String setZip, String setPhone, String setMemberSince){

		memberFirstName = new String(firstName);
		memberLastName = new String(lastName);
		memberID = new String(ID);
		memberStatus = new String(status);
		memberPassword = new String(password);
		spouse = new String(setSpouse);
		email = new String(setEmail);
		address = new String(setAddress);
		city = new String(setCity);
		state = new String(setState);
		zip = new String(setZip);
		phone = new String(setPhone);
		memberSince = new String(setMemberSince);
		statusList = new ArrayList<String>();
		statusList.add(memberStatus);
	}


	//Getter methods
	//
	public String getFirstName(){
		return memberFirstName;
	}

	public String getLastName(){
		return memberLastName;
	}

	public String getFullName(){
		return new String(memberFirstName + " " + memberLastName);
	}

	public String getID(){
		return memberID;
	}

	public String getPassword(){
		return memberPassword;

	}

	public String getSpouse(){
		return spouse;

	}

	public String getAddress(){
		return address;
	}

	public String getCity(){
		return city;
	}

	public String getState(){
		return state;
	}

	public String getZip(){
		return zip;
	}

	public String getEmail(){
		return email;
	}

	public String getPhone(){
		return phone;
	}

	public String[] getStatusList(){
		return (String[])statusList.toArray(new String[0]);
	}

	public String getStatus(){
		//This method returns only the first value, to keep things
		//  working
		if(memberStatus.equals("Life Member"))
			return "Club";
		else
			return memberStatus;
	}

	public String getMemberSince(){
		return memberSince;
	}

	public BigDecimal getPrice(){
		return memberPrice;
	}

	public void setStatus(String newStatus){
		memberStatus = newStatus;
		statusList.set(0, memberStatus);
	}

	public void addStatus(String input){
		statusList.add(input);
	}

	public void setPassword(String newPassword){

			memberPassword = newPassword;

	}

	public void setSpouse(String input){
		if(input != null)
			spouse = new String(input);
	}

	public void setEmail(String input){
		if(input != null)
			email = new String(input);
	}

	public void setAddress(String input){
		if(input != null)
			address = new String(input);
	}

	public void setCity(String input){
		if(input != null)
			city = new String(input);
	}

	public void setState(String input){
		if(input != null)
			state = new String(input);
	}

	public void setZip(String input){
		if(input != null)
			zip = new String(input);
	}

	public void setPhone(String input){
		if(input != null)
			phone = new String(input);
	}

	public void setMemberSince(String input){
		if(input != null)
			memberSince = new String(input);
	}

	public void setPrice(BigDecimal newPrice){
		memberPrice = newPrice;
	}

	public String toString(){
		return(memberFirstName + ", " +
				memberLastName + ", " +
				memberID + ", " +
				memberStatus + ", " +
				memberPassword + ", " +
				spouse + ", " +
				email + ", " +
				address + ", " +
				city + ", " +
				state + ", " +
				zip + ", " +
				phone + ", " +
				memberSince);
	}


	//Check password method against password supplied
	//
	public boolean checkPass(String password){
		try{
			return PasswordHash.validatePassword(password, memberPassword);

		}

		catch(Exception e){
			System.out.println(e);
			return false;
		}
	}

}
