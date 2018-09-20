package pranay.example.com.magnusias4;

public class TopicData {
    private String topicName,details,imageUrl;

    public TopicData() {
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TopicData(String topicName, String details, String imageUrl) {
        this.topicName = topicName;
        this.details = details;

        this.imageUrl = imageUrl;
    }
}
