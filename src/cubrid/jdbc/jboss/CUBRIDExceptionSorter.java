package cubrid.jdbc.jboss;

import java.sql.SQLException;

import org.jboss.jca.adapters.jdbc.spi.ExceptionSorter;
import org.jboss.logging.Logger;


//ExceptionSorter
public class CUBRIDExceptionSorter implements ExceptionSorter {
	public static Logger log = Logger.getLogger(CUBRIDExceptionSorter.class);

	public boolean isExceptionFatal(SQLException e) {
		log.info("cubrid exception sorter");

		if (e.getSQLState() != null && e.getSQLState().startsWith("08")) {
			log.info("match: SQLState. return true");
			return true;
		}
		
		int errorCode = Math.abs(e.getErrorCode());
		
		switch (errorCode) {
		
			//server error
			case 1:			//NO_ERROR
			case 2:			//ER_FAILED
			case 3:			//ER_OUT_OF_VIRTUAL_MEMORY
			case 86:		//ER_LOG_INCOMPATIBLE_DATABASE
			case 130:		//ER_REGU_NO_SPACE
			case 191:		//ER_NET_CANT_CONNECT_SERVER
			case 195:		//ER_NET_SERVER_COMM_ERROR
			case 197:		//ER_NET_NO_MASTER
			case 331:		//ER_AREA_NOSPACE
			case 332:		//ER_AREA_EXTENDING
			case 962:		//ER_INTERFACE_NO_MORE_MEMORY
				
			//cas error
			case 10000:		// CAS_ER_DBMS
			case 10001:		// CAS_ER_INTERNAL
			case 10002:		// CAS_ER_NO_MORE_MEMORY
			case 10003:		// CAS_ER_COMMUNICATION
			case 10006:		// CAS_ER_SRV_HANDLE
			case 10103:		// CAS_ER_SSL_TYPE_NOT_ALLOWED
			
			//jdbc error
			case 21003:		// ER_CUMMICATION
			case 21013:		// ER_CONNECTION
				return true;
			
			default :
				return false;
		}
	}
}
