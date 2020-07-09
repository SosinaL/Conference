import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;



@ManagedBean
@RequestScoped 
public class Autor {
	
	ArrayList autorsList, autorArtikullList;
	
	private String emri, mbiemri, email, artikull,message; 
	private int autor_id;
	
	public String getEmri() {
		return emri;
	}
	public void setEmri(String emri) {
		this.emri = emri;
	}
	public void setMbiemri(String mbiemri) {
		this.mbiemri= mbiemri;
	}
	public String getMbiemri() {
		return mbiemri;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public String getArtikull() {
		return artikull;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage()
	{
		this.message=message;
	}
	public void setAutor_id(int autor_id) {
		this.autor_id = autor_id;
	}
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public ArrayList actorMoviesList(int id){
		autorArtikullList = AutorDb.autorArtikullList(id);
		return autorArtikullList;
	}
	
	public String artikull(int autor_id) {
		String emri = AutorDb.autorName(autor_id);
		sessionMap.put("autor_id", autor_id);
		sessionMap.put("emri", emri);
		sessionMap.put("mbiemri", mbiemri);

		return "/user_panel/actor_movies?faces-redirect=true";
	}

	public String adminList() {
    	return "/admin_panel/actor_index?faces-redirect=true";
    }
    
    public void delete(int id){
        AutorDb.delete(id);
    }
    
    // Used to create new autor record
    public String saveAutor() {
    	AutorDb.save(this);
    	message = "Success! New autor created!";
    	return "autor_create";
    }
    
    // Used to edit autor record
    public String edit(int actor_id) {
    	Autor a = AutorDb.edit(actor_id);
    	this.autor_id = autor_id;
    	this.emri = a.getEmri();
    	this.email = a.getEmail();
    	this.mbiemri = a.getMbiemri();
    	return "autor_edit";
    }
    
    public String editAutor(int id) {
    	boolean done = AutorDb.editAutor(this, id);
        if ( done ) {
        	   message = "Autor updated!";
             return "autor_index";
        }else {
            message = "Could not update autor. Please try again!";
             return "autor_edit";
        }
    }
    
    public String saveAssignActor(int autor_id) {
    	AutorDb.saveAssignAutor(this.autor_id, this.emri, mbiemri,artikull);
    	 message = "Success! New assign autor created!";
    	return "movie_assign_actors";
    }

}