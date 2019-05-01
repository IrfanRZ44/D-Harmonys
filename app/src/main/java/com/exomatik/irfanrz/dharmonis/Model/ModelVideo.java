package com.exomatik.irfanrz.dharmonis.Model;

/**
 * Created by IrfanRZ on 29/12/2018.
 */

public class ModelVideo {
    String videoUrl;
    String titelVideo;

    public ModelVideo() {
    }

    public ModelVideo(String videoUrl, String titelVideo) {
        this.videoUrl = videoUrl;
        this.titelVideo = titelVideo;
    }

    public String getTitelVideo() {
        return titelVideo;
    }

    public void setTitelVideo(String titelVideo) {
        this.titelVideo = titelVideo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
