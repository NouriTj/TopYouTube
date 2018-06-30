package topyoutube.tncode.net.topyoutube.DAO;

import io.realm.RealmObject;

/**
 * Created by noure on 19/05/2017.
 */

public class Country extends RealmObject {
    private  String code;
    private  String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
