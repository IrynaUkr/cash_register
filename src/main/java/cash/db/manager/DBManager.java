package cash.db.manager;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.exceptions.DBException;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(PaymentDaoImpl.class);
    private static DBManager instance;
    private DataSource ds;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    public DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/cashier");
            System.out.println("ds====>" + ds);
               logger.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
           logger.error("Cannot obtain data source", ex);
            throw new DBException("Cannot obtain data source", ex);
        }

    }

    /**
     * Returns a DB connection from the Pool Connections.
     *
     * @return DB connection.
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            logger.error("Error cannot obtain connection", ex);
            throw new DBException("Error cannot obtain connection", ex);
        }
        return con;
    }
}
