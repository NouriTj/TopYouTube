package topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class High {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
