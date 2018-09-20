package pranay.example.com.magnusias4;

public class VideoListDataItem {
    private  String id,videoThumbnail,videoURL,cc_heading,video_id,access_type;
    private int imageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getCc_heading() {
        return cc_heading;
    }

    public void setCc_heading(String cc_heading) {
        this.cc_heading = cc_heading;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public VideoListDataItem(String id) {
        this.id = id;
    }

    public VideoListDataItem(String id, String videoThumbnail, String videoURL, String cc_heading, String video_id, String access_type) {
        this.id = id;
        this.videoThumbnail = videoThumbnail;
        this.videoURL = videoURL;
        this.cc_heading = cc_heading;
        this.video_id = video_id;
        this.access_type = access_type;
    }

    public VideoListDataItem(String id, String videoThumbnail, String videoURL, String cc_heading, String video_id, String access_type, int imageId) {
        this.id = id;
        this.videoThumbnail = videoThumbnail;
        this.videoURL = videoURL;
        this.cc_heading = cc_heading;
        this.video_id = video_id;
        this.access_type = access_type;
        this.imageId = imageId;

    }
}

