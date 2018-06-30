package topyoutube.tncode.net.topyoutube.YTAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.ListChannelResult;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.ListVideoResult;

/**
 * Created by noure on 01/06/2017.
 */

public interface YTService {
    static final String KEY = "AIzaSyD3BO6T-xEeQA0-IGzegiAcGGBEKeWbd-U";

    @GET("videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&key="+KEY)
    Call<ListVideoResult> listVideos(@Query("regionCode") String regionCode, @Query("maxResults") Integer maxResults);

    @GET("channels?part=snippet%2CcontentDetails%2Cstatistics&key=" + KEY)
    Call<ListChannelResult> listChannels(@Query("id") String idChannel);
}
