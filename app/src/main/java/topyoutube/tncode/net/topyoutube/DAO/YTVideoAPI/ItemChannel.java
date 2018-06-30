
package topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemChannel extends Item {

    @SerializedName("statistics")
    @Expose
    private StatisticsChannel statistics;

    public StatisticsChannel getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsChannel statistics) {
        this.statistics = statistics;
    }

}
