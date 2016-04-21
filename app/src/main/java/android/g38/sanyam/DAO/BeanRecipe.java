package android.g38.sanyam.DAO;

/**
 * Created by SANYAM TYAGI on 4/21/2016.
 */
public class BeanRecipe {
    private String id ;
    private String If ;
    private String then ;
    private String name;
    private String data;
    private String time;
    private String status ;
    private String base ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIf() {
        return If;
    }

    public void setIf(String anIf) {
        If = anIf;
    }

    public String getThen() {
        return then;
    }

    public void setThen(String then) {
        this.then = then;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }


}
