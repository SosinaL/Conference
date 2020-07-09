import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@ManagedBean
@RequestScoped 
public class Shqyrtues {
	
	ArrayList shqyrtuesList, shqyrtuesartikull;
	
	private String emri, mbiemri, message, role,temat, institucioni,email;
	private int shqyrtues_id, nrtel;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public String getEmri() {
		return emri;
	}
	
	public void setEmri(String emri) {
		this.emri = emri;
	}
	
	public String getMbiemri() {
		return mbiemri;
	}
	
	public void setMbiemri(String mbiemri) {
		this.mbiemri = mbiemri;
	}
	
	public int getShqyrtues_id() {
		return shqyrtues_id;
	}
	public void setTemat(String temat) {
		this.temat = temat;
	}
	
	public String getTemat() {
		return temat;
	}
	public void setNrTel(int nrtel) {
		this.nrtel = nrtel;
	}
	
	public int getNrTel() {
		return nrtel;
	}
	public void setInsitucioni(String institucioni) {
		this.institucioni = institucioni;
	}
	
	public String getInstitucioni() {
		return institucioni;
	}
	
	
	public void setShqyrtues_id(int shqyrtues_id) {
		this.shqyrtues_id = shqyrtues_id;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getEmail()
	{
		return email;
	}
	
	public ArrayList shqyrtuesList(){
		shqyrtuesList =ShqyrtuesDb.shqyrtuesList();
		
		return shqyrtuesList;
	}
	
	public String list() {
		return "/user_panel/director?faces-redirect=true";
	}
	

	
	public String artikull(int shqyrtues_id) {
		String name =ShqyrtuesDb.shqyrtuesName(shqyrtues_id);
		sessionMap.put("shqyrtues_id", shqyrtues_id);
		sessionMap.put("emer", name);

		return "/user_panel/director_movies?faces-redirect=true";
	}

	public String adminList() {
    	return "/admin_panel/director_index?faces-redirect=true";
    }
    
    // Used to delete shqyrtues record
    public static void delete(int id){
        Shqyrtues.delete(id);
    }
    
    //krijimi i nje shqyrtuesi te ri shtimi i nje recordi
    public String saveDirector() {
    	ShqyrtuesDb.save(this);
    	message = "Success! New director created!";
    	return "director_create";
    }
    
    // editimi i te dhenave te shqyrtuesit
    public String edit(int director_id) {
    	Shqyrtues d = ShqyrtuesDb.edit(shqyrtues_id);
    	this.shqyrtues_id = director_id;
    	this.emri = d.getEmri();
    	this.mbiemri = d.getMbiemri();
    	this.email = d.getEmail();
    	this.temat = d.getTemat();
    	this.institucioni = d.getInstitucioni();
    	return "shqyrtues_edit";
    }
    
    
    
   
//editimi i te dhenatve te shqyrtuesit
	public String editShqyrtues(int id) {
    	boolean done = ShqyrtuesDb.editShqyrtues(this, id);
        if ( done ) {
        	 message = "Success! Shqyrtues updated!";
             return "director_index";
        }else {
             message = "Sorry! Could not update shqyrtues. Please try again!";
             return "director_edit";
        }
    }

    public String saveAssignDirector(int art_id) {
    	ShqyrtuesDb.saveShqyrtues(this.shqyrtues_id, this.role, art_id);
    	message = "Success! New assign director created!";
    	return "movie_assign_director";
    }

	
}