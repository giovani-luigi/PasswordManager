package myid.storage;

import java.io.File;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myid.cryptography.Cypher;
import myid.model.BadValueException;
import myid.model.DatabaseException;
import myid.model.Profile;
import myid.view.MainView;

public final class SQLiteStorage implements IStoreProfiles {

    // DB objects
    private static final String TABLE_PROFILES = "Profiles";
    private static final String TABLE_SETTINGS = "Settings";
    private final String connectionString;
    private boolean initialized;
    
    // Cryptography engine
    private final Cypher cypher;
    protected final String filePath;
    
    public static File getDefaultFile(){
        return FileSystems.getDefault().getPath("myid.db").toFile();
    }
    
    /**
     * Creates a new instance of this class, using the specified cypher, and using the default database file path
     * @param cypher The cypher to use when reading/writing the password to the database
     */
    public SQLiteStorage(Cypher cypher) {
        this(getDefaultFile(), cypher); // use default file path
    }
    
    /**
     * Creates a new instance of this class, using the specified cypher, and using the specified database file path
     * @param cypher The cypher to use when reading/writing the password to the database
     * @param file The file to the database
     */
    public SQLiteStorage(File file, Cypher cypher) {
        this.filePath = file.getAbsolutePath();
        this.initialized = false;
        this.cypher = cypher;
        connectionString = "jdbc:sqlite:" + filePath;
    }

    /**
     * Creates database file and tables
     * @throws SQLException 
     */
    private void initialize() throws DatabaseException {
        //Table to store all profiles (login+password pairs)
        String sqlTabProfiles = "CREATE TABLE IF NOT EXISTS " + TABLE_PROFILES + 
                         "(Id integer PRIMARY KEY," +   // primary key
                         "alias TEXT," +   // alias (e.g. 'hotmail')
                         "pwd TEXT," +     // crypted password
                         "user TEXT," +    // user name
                         "url TEXT)";      // url to the service when applicable
        
        //Table to store all settings (the master password hash)
        String sqlTabSettings = "CREATE TABLE IF NOT EXISTS " + TABLE_SETTINGS + 
                         "(name TEXT, value TEXT)";
        try(Connection c = DriverManager.getConnection(connectionString)){
            Statement s = c.createStatement();
            s.execute(sqlTabProfiles);
            s.execute(sqlTabSettings);
            initialized = true;
        } catch (SQLException ex) {
            initialized = false;
            throw new DatabaseException("Erro ao inicializar um novo banco de dados", ex);
        }
    }
    
    @Override
    public void add(Profile profile) throws DatabaseException {
        if (!initialized) initialize();
        String sql = "INSERT INTO " + TABLE_PROFILES + 
                " (alias,pwd,user,url) VALUES (?,?,?,?)";
        try(
            Connection c = DriverManager.getConnection(connectionString);
            PreparedStatement ps = c.prepareStatement(sql)
          ){
            ps.setString(1, profile.getAlias());
            ps.setString(2, cypher.Encrypt(profile.getPwd()));
            ps.setString(3, cypher.Encrypt(profile.getUser()));
            ps.setString(4, profile.getUrl());
            ps.execute();
            
            // get the primary key assigned to the profile
            Statement getCount = c.createStatement();
            ResultSet rs = getCount.executeQuery("SELECT last_insert_rowid()");
            profile.setPrimaryKey(rs.getInt(1));
        } catch (SQLException ex) {
            throw new DatabaseException("Erro ao inserir no banco de dados.", ex);
        }
    }

    @Override
    public void remove(Profile profile) throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready
        if (!profile.hasPrimaryKey()) return; // profile is not in the database yet, so there is nothing to erase
        String sql = "DELETE FROM " + TABLE_PROFILES + " WHERE Id=?";
        try(Connection c = DriverManager.getConnection(connectionString);
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, profile.getPrimaryKey());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("Erro ao apagar no banco de dados.", ex);
        }
    }

    @Override
    public Profile read(int primaryKey) throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready
        String sql = "SELECT * FROM " + TABLE_PROFILES + " WHERE Id=?";
        try(
            Connection c = DriverManager.getConnection(connectionString);
            PreparedStatement ps = c.prepareStatement(sql)
          ){
            ps.setInt(1, primaryKey); // set the Id value we are looking for
            ResultSet rs = ps.executeQuery(); // run query

            // read first item found with the specified primary key
            if (rs.next()){ 
                Profile p = new Profile(rs.getInt("Id"));
                p.setAlias(rs.getString("alias"));
                p.setPwd(cypher.Decrypt(rs.getString("pwd")));
                p.setUser(rs.getString("user"));
                p.setUrl(rs.getString("url"));
                return p;
            }
        } catch (BadValueException ex) {
            // If we got an exception, then data from DB is not valid.
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DatabaseException("Erro ao ler banco de dados.", ex);
        }
        return null;
    }

    @Override
    public List<Profile> readAll() throws DatabaseException{
        if (!initialized) initialize(); // ensures DB is ready
        
        ArrayList<Profile> result = new ArrayList<>();
        
        String sql = "SELECT * FROM " + TABLE_PROFILES;
        try(
            Connection c = DriverManager.getConnection(connectionString);
            PreparedStatement ps = c.prepareStatement(sql)
          ){
            ResultSet rs = ps.executeQuery(); // run query

            // read first item found with the specified primary key
            if (rs.next()){ 
                Profile p = new Profile(rs.getInt("Id"));
                p.setAlias(rs.getString("alias"));
                p.setPwd(cypher.Decrypt(rs.getString("pwd")));
                p.setUser(rs.getString("user"));
                p.setUrl(rs.getString("url"));
                result.add(p);
            }
        } catch (BadValueException ex) {
            // If we got an exception, then data from DB is not valid.
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DatabaseException("Erro ao ler banco de dados.", ex);
        }
        return result;
    }
    
    @Override
    public void update(Profile profile) throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready
        
    }

    @Override
    public String getPasswordHash() throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void setPasswordHash(String password) throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /** 
     * Check if DB has a master password defined
     * @return TRUE if the password is defined
     * @throws DatabaseException 
     */
    @Override
    public boolean hasPassword() throws DatabaseException {
        if (!initialized) initialize(); // ensures DB is ready        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}