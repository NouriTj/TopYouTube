package topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsChannel extends Statistics {

    @SerializedName("subscriberCount")
    @Expose
    private String subscriberCount;
    @SerializedName("hiddenSubscriberCount")
    @Expose
    private Boolean hiddenSubscriberCount;
    @SerializedName("videoCount")
    @Expose
    private String videoCount;

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public Boolean getHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount(Boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }

}
