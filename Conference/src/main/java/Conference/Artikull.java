package Conference;
import java.sql.*;
import java.util.*;
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
	private String titulli, abstrakt, emdokelek,roli, message;
	private int art_id;
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public String getTitulli() {
		return titulli;
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

	public void setTitulli(String titulli) {
		this.titulli = titulli;
	}

	public int getArtikull_id() {
		return art_id;
	}

	public void setFilm_id(int film_id) {
		this.art_id = film_id;
	}

	
	
	public ArrayList artikullList(){
		artikullList = ArtikullDb.artikullList();
		
		return moviesList;
	}
	
	public String list() {
		return "/user_panel/movie?faces-redirect=true";
	}
	
	public ArrayList movieActorsList(int id){
		movieActorsList = MovieDAO.movieActorsList(id);
		return movieActorsList;
	}
	
	public String actors(int film_id) {
		String title = MovieDAO.movieTitle(film_id);
		sessionMap.put("film_id", film_id);
		sessionMap.put("title", title);

		return "/user_panel/movie_actors?faces-redirect=true";
	}
	
	public ArrayList movieDirectorsList(int id){
		movieDirectorsList = MovieDAO.movieDirectorsList(id);
		return movieDirectorsList;
	}
	
	public String directors(int film_id) {
		String title = MovieDAO.movieTitle(film_id);
		sessionMap.put("film_id", film_id);
		sessionMap.put("title", title);

		return "/user_panel/movie_directors?faces-redirect=true";
	}
    
    public String adminList() {
    	return "/admin_panel/movie_index?faces-redirect=true";
    }
    
 // Used to delete user record
    public void delete(int id){
        MovieDAO.delete(id);
    }
    
    // Used to create new movie record
    public String saveMovie() {
    	MovieDAO.save(this);
    	message = "Success! New movie created!";
    	return "movie_create";
    }
    
    // Used to edit movie record
    public String edit(int movie_id) {
    	Movie movie = MovieDAO.edit(movie_id);
    	this.film_id = movie_id;
    	this.titulli = movie.getTitulli();
    	this.viti = movie.getViti();
    	this.gjatesia = movie.getGjatesia();
    	this.skenari = movie.getSkenari();
    	this.kompania = movie.getKompania();
    	return "movie_edit";
    }
    
    public String editMovie(int id) {
    	boolean done = MovieDAO.editMovie(this, id);
        if ( done ) {
        	 message = "Success! Movie updated!";
             return "movie_index";
        }else {
             message = "Sorry! Could not update movie. Please try again!";
             return "movie_edit";
        }
    }
    
    public String assignActor(int movie_id) {
		String title = MovieDAO.movieTitle(movie_id);
		sessionMap.put("film_id", movie_id);
		sessionMap.put("title", title);

		return "/user_panel/movie_assign_actor?faces-redirect=true";
    }

    public String assignDirector(int movie_id) {
		String title = MovieDAO.movieTitle(movie_id);
		sessionMap.put("film_id", movie_id);
		sessionMap.put("title", title);

		return "/user_panel/movie_assign_director?faces-redirect=true";
    }
