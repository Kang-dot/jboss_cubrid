package cubrid.jdbc.jboss;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jboss.jca.adapters.jdbc.spi.ValidConnectionChecker;
import org.jboss.logging.Logger;

public class CUBRIDValidConnectionChecker implements ValidConnectionChecker {
	private static Logger log = Logger.getLogger(CUBRIDValidConnectionChecker.class);

	public SQLException isValidConnection(Connection conn) {
		
		log.info("CUBRID Valid Connection Checker");
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select 1");
			
		} catch (SQLException e) {
			return e;
			
		} catch (Exception e) {
			if (e instanceof SQLException) {
				return (SQLException) e;
			}
			return new SQLException("connection is invalid. reason : " + e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sQLException) {}
			
			try {
				if (st != null)
					st.close();
			} catch (SQLException sQLException) {}
			
		}
		return null;
	}
}
