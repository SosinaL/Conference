import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDb {

    public static boolean register(User u) {
    	//register a new user
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("insert into users(name, lastname, fullname, username, password, email, mobile, role) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, u.getName());
            ps.setString(2, u.getLastname());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getUsername());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getMobile());
            ps.setString(8, "user");
            System.out.println("New user added!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDb->register() : " + ex.getMessage());
            return false;
        }
    }

    public static User login(String username, String password) {

        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from users where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                u.setRole(rs.getString("role"));
                return u;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("UserDb-> login() : " + ex.getMessage());
            return null;
        }
    }

    public static boolean changePassword(String username, String password, String newPassword) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update users set password = ? where username = ? and password = ?");
            ps.setString(1,newPassword);
            ps.setString(2,username);
            ps.setString(3,password);
            int count = ps.executeUpdate();
            System.out.println("Password changed successfully!");
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDb-> changePassword() : " + ex.getMessage());
            return false;
        }

    }

    public static User getUser(String username, String email) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from users where username = ? or email = ?");
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setName(rs.getString("name"));
                u.setLastname(rs.getString("lastname"));
                u.setUsername(username);
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                return u;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("UserDb-> getUser() : " + ex.getMessage());
            return null;
        }
    }
    
    public static User getProfile() {
    	try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from users where username = ?");
            ps.setString(1, Util.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setName(rs.getString("name"));
                u.setLastname(rs.getString("lastname"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                return u;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("UserDb-> getProfile() : " + ex.getMessage());
            return null;
        }
    }
    
    public static boolean update(User u) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update users set name=?, lastname=?, fullname=?, email=?, mobile=? where username=?");
            ps.setString(1, u.getName());
            ps.setString(2, u.getLastname());
            ps.setString(3, u.getFullname());;
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getMobile());
            ps.setString(6, Util.getUsername());
            System.out.println("Your profile updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDb->update() : " + ex.getMessage());
            return false;
        }
    }
    
 // Used to fetch all records
    public static ArrayList usersList(){
        try{
            Connection conn = Database.getConnection();
            Statement stmt= conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from users");  
            ArrayList usersList = new ArrayList();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setFullname(rs.getString("fullname"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMobile(rs.getString("mobile"));
                user.setRole(rs.getString("role"));
                usersList.add(user);
            }
            return usersList;
        }catch(Exception e){
        	System.out.println("UserDb->usersList() : " + e.getMessage());
        	return null;
        }
    }
    
    // Used to delete user record
    public static void delete(int id){
        try{
        	Connection conn = Database.getConnection();  
            PreparedStatement stmt = conn.prepareStatement("delete from users where id = "+id);  
            stmt.executeUpdate();  
            System.out.println("User deleted successfully");
        }catch(Exception e){
        	System.out.println("UserDb->delete() : " + e.getMessage());
        }
    }
    
    // Used to save user record
    public static void save(User u){
        int result = 0;
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into users(name,lastname,fullname, mobile, username, email,password,role) values(?,?,?,?,?,?,?,?)");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getLastname());
            stmt.setString(3, u.getFullname());
            stmt.setString(4, u.getMobile());
            stmt.setString(5, u.getUsername());
            stmt.setString(6, u.getEmail());
            stmt.setString(7, u.getPassword());
            stmt.setString(8, u.getRole());
            result = stmt.executeUpdate();
            System.out.println("User saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("UserDb->save() : " + e.getMessage());
        }
    }
    
    // Used to fetch record to update
    public static User edit(int id){
        User user = null;
        try{
        	Connection conn = Database.getConnection();
            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from users where id = "+(id));
            rs.next();
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setLastname(rs.getString("lastname"));
            user.setFullname(rs.getString("fullname"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setMobile(rs.getString("mobile"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            System.out.println("User data updated!");
            conn.close();
            return user;
        }catch(Exception e){
        	System.out.println("UserDb->edit() : " + e.getMessage());
        	return null;
        }       
    }

    public static boolean editUser(User u, int id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update users set name=?, lastname=?, fullname=?, username=?, email=?, password=?, mobile=?, role=? where id=?");
            ps.setString(1, u.getName());
            ps.setString(2, u.getLastname());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getUsername());;
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());
            ps.setString(7, u.getMobile());
            ps.setString(8, u.getRole());
            ps.setInt(9, id);
            System.out.println("Your profile updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDb->editUser() : " + ex.getMessage());
            return false;
        }
    }
}
