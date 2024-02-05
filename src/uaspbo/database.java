package uaspbo;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * author DametraOwO
 */
public class database {
   public static Connection connectDb() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/akun", "root", "");
        return connect;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}