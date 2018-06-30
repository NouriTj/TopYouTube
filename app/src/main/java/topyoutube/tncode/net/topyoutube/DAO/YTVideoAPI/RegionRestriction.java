package topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionRestriction {

    @SerializedName("blocked")
    @Expose
    private List<String> blocked = null;

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

}
