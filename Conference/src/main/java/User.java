import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@ManagedBean
@RequestScoped 
public class User {
    
	ArrayList usersList ;
	Connection connection;
	private String username, name, lastname, password, fullname, email,mobile, message, newPassword, role;
	private int id;
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
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

		public String getLastname() {
			return lastname;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getNewPassword() {
	        return newPassword;
	    }

	    public void setNewPassword(String newPassword) {
	        this.newPassword = newPassword;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
	  
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getFullname() {
	        return fullname;
	    }

	    public void setFullname(String fullname) {
	        this.fullname = fullname;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	        this.mobile = mobile;
	    }

    public String register() {
        boolean done = UserDb.register(this);
        if ( done )
             return "login?faces-redirect=true";
        else {
             message = "Sorry! Could not register. Please try again!";
             return "register";
        }
    }

     public String login() {
        User u = UserDb.login(username,password);
        
         if ( u != null ) {
            Util.addToSession("username", u.getUsername());
            Util.addToSession("fullname", u.getFullname());
            if(u.getRole().equals("user")) 
            {
            	 return "/user/home?faces-redirect=true";
            }
            else {
            	return "/admin/home?faces-redirect=true";			
            }
        }
        else {
        	message="Ju nuk mund te logoheni!";
            return "login";
        }
    }

     
    public void changePassword(ActionEvent evt) {
        boolean done = UserDb.changePassword(Util.getUsername(),password, newPassword);
        if (done) {
            message="Password has been changed successfully!";
        }
        else {
             message = "Sorry! Could not change password. Old passwod may be incorrect!";
        }
    }
     
    public String logout() {
        Util.terminateSession();
        return "/all/login?faces-redirect=true";
    }
   
    
    @Override
   public String toString() 
    { return "User {" + "uname=" + username + ", password=" + password + ", fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ", message=" + message + ", newPassword=" + newPassword + '}';
    	    }
    	    
    
    public String profile() {
    	User u = UserDb.getProfile();

    	this.name = u.getName();
		this.lastname = u.getLastname();
		this.email = u.getEmail();
		this.mobile = u.getMobile();
		this.fullname = u.getFullname();

		return "profile";
    }
    
    public String update() {
    	boolean done = UserDb.update(this);
        if ( done ) {
        	 message = "Success! Your profile updated!";
             return "profile";
        }else {
             message = "Sorry! Could not update your profile. Please try again!";
             return "profile";
        }
    }
    
    // Used to fetch all records
    public ArrayList usersList(){
        usersList = UserDb.usersList();
 
        return usersList;
    }

    public String adminList() {
    	return "/admin_panel/user_index?faces-redirect=true";
    }
    
    // Used to delete user record
    public void delete(int id){
        UserDb.delete(id);
    }
    
    // Used to create new user record
    public String saveUser() {
    	UserDb.save(this);
    	message = "Success! New user created!";
    	return "user_create";
    }
    
    // Used to edit user record
    public String edit(int user_id) {
    	User user = UserDb.edit(user_id);
    	this.id = user_id;
    	this.name = user.getName();
    	this.lastname=user.getLastname();
    	this.fullname = user.getFullname();
    	this.username = user.getUsername();
    	this.email = user.getEmail();
    	this.password = user.getPassword();
    	this.mobile = user.getMobile();
    	this.role = user.getRole();
    	return "user_edit";
    }
    
    public String editUser(int id) {
    	boolean done = UserDb.editUser(this, id);
        if ( done ) {
        	 message = "Success! User updated!";
             return "user_index";
        }else {
             message = "Sorry! Could not update user. Please try again!";
             return "user_edit";
        }
    }
    
}
