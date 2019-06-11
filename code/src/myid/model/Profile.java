package myid.model;

public class Profile {

    private int primaryKey; // DB primary key
    private String alias;   // alias to this profile
    private String url;     // (optional) url related to this profile
    private String user;    // user name/ login ID
    private String pwd;     // password

    public Profile(){
        this.primaryKey = -1;
    }
    
    public Profile(int primaryKey){
        this.primaryKey = primaryKey;
    }
            
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) throws BadValueException {
        if (user.length() < 1) throw new BadValueException("Usuário deve conter ao menos 1 caracter.");
        if (user.length() > 50) throw new BadValueException("Usuário deve ter no máximo 50 caracteres.");
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) throws BadValueException {
        if (pwd.length() < 6) throw new BadValueException("Senha deve conter ao menos 6 caracteres.");
        if (pwd.length() > 50) throw new BadValueException("Senha deve ter no máximo 50 caracteres.");
        this.pwd = pwd;
    }
        
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) throws BadValueException {
        if (alias.length() < 1) throw new BadValueException("Alias deve conter ao menos 1 caracter.");
        if (alias.length() > 50) throw new BadValueException("Alias deve ter no máximo 50 caracteres.");
        this.alias = alias;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public boolean hasPrimaryKey(){ 
        return (this.primaryKey > -1);
    }
}
