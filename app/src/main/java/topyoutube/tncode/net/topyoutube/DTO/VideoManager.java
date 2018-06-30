package topyoutube.tncode.net.topyoutube.DTO;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import topyoutube.tncode.net.topyoutube.DAO.Channel;
import topyoutube.tncode.net.topyoutube.DAO.Video;
import topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.ListChannelResult;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.Item;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.ListVideoResult;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.Localized;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.Thumbnails;
import topyoutube.tncode.net.topyoutube.YTAPI.ApiUtils;
import topyoutube.tncode.net.topyoutube.YTAPI.YTService;

/**
 * Created by noure on 04/06/2017.
 */

public class VideoManager {
    //YT API call
    public static final YTService mService = ApiUtils.getYTIService();

    private static void getChannelFromJson(String idChannel, final Video video) {
        final Call<ListChannelResult> call = VideoManager.mService.listChannels(idChannel);
        call.enqueue(new Callback<ListChannelResult>() {
            @Override
            public void onResponse(Call<ListChannelResult> call, Response<ListChannelResult> response) {

                if (response.isSuccessful()) {
                    video.setChannel(deserializeChannel(response.body()));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ListChannelResult> call, Throwable t) {
                Log.d("MainActivity", "error loading from API" + t.getMessage() + "\n" + t.getStackTrace().toString());

            }
        });
    }

    public static Channel deserializeChannel(ListChannelResult listChannelResult) {
        if (listChannelResult.getItems().size() <= 0)
            return null;

        topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.Item item = listChannelResult.getItems().get(0);
        if (item == null)
            return null;

        Channel channel = new Channel();

        channel.setId(item.getId());
        channel.setTitle(item.getSnippet().getTitle());
        topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.Localized LocalizedTitle = item.getSnippet().getLocalized();
        if (LocalizedTitle != null) {
            channel.setTitle(LocalizedTitle.getTitle());
        } else {
            channel.setTitle(item.getSnippet().getTitle());
        }
        topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.Localized LocalizedDescription = item.getSnippet().getLocalized();
        if (LocalizedTitle != null) {
            channel.setDescription(LocalizedDescription.getDescription());
        } else {
            channel.setDescription(item.getSnippet().getDescription());
        }
        channel.setPublishedAt(item.getSnippet().getPublishedAt());//todo convert to datetime
        topyoutube.tncode.net.topyoutube.DAO.YTChannelAPI.Thumbnails thumbnails = item.getSnippet().getThumbnails();
        if (thumbnails != null) {
            if (thumbnails.getHigh() != null) {
                channel.setImage(thumbnails.getHigh().getUrl());//todo convert to url
            } else if (thumbnails.getMedium() != null) {
                channel.setImage(thumbnails.getMedium().getUrl());//todo convert to url
            } else {
                channel.setImage(thumbnails.getDefault().getUrl());//todo convert to url
            }
        }
        if (item.getStatistics() != null) {
            channel.setViewCount(item.getStatistics().getViewCount());
            channel.setCommentCount(item.getStatistics().getCommentCount());
            channel.setSubscriberCount(item.getStatistics().getSubscriberCount());
            channel.setVideoCount(item.getStatistics().getViewCount());

        }

        return channel;
    }

    public static List<Video> getVideoList(ListVideoResult listVideoResult) {
        List<Video> listVideo = new ArrayList<>();
        for (Item item : listVideoResult.getItems()) {

            Video video = new Video();

            video.setId(item.getId());
            video.setTitle(item.getSnippet().getTitle());
            Localized LocalizedTitle = item.getSnippet().getLocalized();
            if (LocalizedTitle != null) {
                video.setTitle(LocalizedTitle.getTitle());
            } else {
                video.setTitle(item.getSnippet().getTitle());
            }
            Localized LocalizedDescription = item.getSnippet().getLocalized();
            if (LocalizedDescription != null) {
                video.setDescription(LocalizedDescription.getDescription());
            } else {
                video.setDescription(item.getSnippet().getDescription());
            }
            getChannelFromJson(item.getSnippet().getChannelId(), video);
            if (item.getContentDetails() != null) {
                video.setDuration(item.getContentDetails().getDuration());//todo convert to datetime
            }
            video.setPublishedAt(item.getSnippet().getPublishedAt());//todo convert to datetime
            Thumbnails thumbnails = item.getSnippet().getThumbnails();
            if (thumbnails != null) {
                if (thumbnails.getMaxres() != null) {
                    video.setImage(thumbnails.getMaxres().getUrl());//todo convert to url
                } else if (thumbnails.getHigh() != null) {
                    video.setImage(thumbnails.getHigh().getUrl());//todo convert to url
                } else if (thumbnails.getMedium() != null) {
                    video.setImage(thumbnails.getMedium().getUrl());//todo convert to url
                } else if (thumbnails.getStandard() != null) {
                    video.setImage(thumbnails.getStandard().getUrl());//todo convert to url
                } else {
                    video.setImage(thumbnails.getDefault().getUrl());//todo convert to url
                }
            }
            if (item.getStatistics() != null) {
                video.setViewCount(item.getStatistics().getViewCount());
                video.setLikeCount(item.getStatistics().getLikeCount());
                video.setDisLikesCount(item.getStatistics().getDislikeCount());

            }
            video.setCategoryId(item.getSnippet().getCategoryId());//todo get catecory name

            listVideo.add(video);
        }
        return listVideo;
    }
}
