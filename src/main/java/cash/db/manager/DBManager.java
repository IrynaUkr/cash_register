package cash.db.manager;

import cash.exceptions.DBException;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
  //  private static final Logger LOG = Logger.getLogger(DBManager.class);
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
            //   LOG.trace("Data source ==> " + ds);
//        } catch (NamingException ex) {
//         //   LOG.error("Cannot obtain data source", ex);
//            throw new DBException("Cannot obtain data source", ex);
//        }
        } catch (NamingException e) {
            e.printStackTrace();
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
          //  LOG.error("Error cannot obtain connection", ex);
            throw new DBException("Error cannot obtain connection", ex);
        }
        return con;
    }
    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and close the given connection.
     *
     * @param con
     *            Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con
     *            Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
