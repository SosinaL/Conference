
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class Artikull {
	ArrayList artikullList, artikullAutorsList, artikullShqyrtuesList;
	private String titulli, abstrakti, emri_dokelektronik, roli, message;
	private int artikull_id;
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public String getTitulli() {
		return titulli;
	}
	public void setTitulli(String titulli) {
		this.titulli=titulli;
	}
	public String getAbstrakti() {
		return abstrakti;
	}
	public void setAbstrakti(String abstrakti) {
		this.abstrakti=abstrakti;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRoli() {
		return roli;
	}

	public void setRoli(String roli) {
		this.roli = roli;
	}
	public int getArtikull_id() {
		return artikull_id;
	}

	public void setArtikull_id(int artikull_id) {
		this.artikull_id = artikull_id;
	}

	public void setEmri_DokElekt(String emri_dokelektronik) {
		this.emri_dokelektronik = emri_dokelektronik;
	}
	public String getEmri_DokElekt() {
		return emri_dokelektronik;
	}

	
	public ArrayList artikullList(){
		artikullList = ArtikullDb.artikullList();
		//ruan ne nje liste te gjithe elementet e tabeles artikull
		return artikullList;
	}
	
	public String list() {
		return "/user_panel/movie?faces-redirect=true";
	}
	
	public ArrayList artikullAutorsList(int id){
		artikullAutorsList = ArtikullDb.artikullAutorsList(id);
		return artikullAutorsList;
	}
	
	public String actors(int film_id) {
		String title = ArtikullDb.ArtikullTitull(artikull_id);
		sessionMap.put("artikull_id", artikull_id);
		sessionMap.put("titulli", titulli);

		return "/user/artikull_autors?faces-redirect=true";
	}
	
	public ArrayList artikullShqyrtuesList(int id){
		artikullShqyrtuesList = ArtikullDb.artikullShqyrtuesList(id);
		return artikullShqyrtuesList;
	}
	
	public String shqyrtues(int film_id) {
		String title = ArtikullDb.ArtikullTitull(film_id);
		sessionMap.put("artikull_id", artikull_id);
		sessionMap.put("titulli",titulli);

		return "/user_panel/movie_directors?faces-redirect=true";
	}
    
    public String adminList() {
    	return "/admin_panel/movie_index?faces-redirect=true";
    }
    
 // Used to delete user record
    public void delete(int id){
        ArtikullDb.delete(id);
    }
    
    // Used to create new movie record
    public String saveMovie() {
    	ArtikullDb.save(this);
    	message = "Success! New artikull created!";
    	return "artikull_create";
    }
    
    // Used to edit artikull data 
    public String edit(int artikull_id) {
    	Artikull artikull = ArtikullDb.edit(artikull_id);
    	this.artikull_id = artikull_id;
    	this.titulli = artikull.getTitulli();
    	this.abstrakti = artikull.getAbstrakti();
    	this.emri_dokelektronik = artikull.getEmri_DokElekt();
    	
    	return "artikull_edit";
    	//funx i cili ben editimin e artikullit
    }
    
    
    public String editArtikull(int id) {
    	boolean done = ArtikullDb.editArtikull(this, id);
        if ( done ) {
        	 message = " Article updated!";
             return "article_edited";
        }else {
             message = "Sorry! Could not update artikull. Please try again!";
             return "article_edit";
        }
    }
    
   
  
}