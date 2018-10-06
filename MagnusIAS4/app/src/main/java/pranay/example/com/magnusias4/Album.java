package pranay.example.com.magnusias4;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Album {
    private String name,details,subject_id,topic_id,chapter_id,videoID,pdfDoc,email,mobile,hobbies,qualification,skills,post;
   private String chpater_content_id,sub_menu,main_menu_id,heading,access_type,type,test_type;
private int stage2;

    public int getStage2() {
        return stage2;
    }

    public String getTest_type() {
        return test_type;
    }

    public String getAccess_type() {
        return access_type;
    }

    public String getType() {
        return type;
    }

    public String getSub_menu() {
        return sub_menu;
    }

    public String getHeading() {
        return heading;
    }

    public String getMain_menu_id() {
        return main_menu_id;
    }

    public String getChpater_content_id() {
        return chpater_content_id;
    }

    private int numOfSongs;

    public String getVideoID() {
        return videoID;
    }

    public String getPost() {
        return post;
    }

    public String getEmail() {
        return email;
    }

    public Album(String name, String details, String email, String mobile, String hobbies, String qualification, String skills, String post, String img_path) {
        this.name = name;
        this.details = details;
        this.email = email;
        this.mobile = mobile;
        this.hobbies = hobbies;
        this.qualification = qualification;
        this.skills = skills;
        this.post = post;
        this.img_path = img_path;
        this.stage = 5000;
    }

    public String getMobile() {

        return mobile;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getQualification() {
        return qualification;
    }

    public String getSkills() {
        return skills;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;

    }

    public String getPdfDoc() {
        return pdfDoc;
    }

    public void setPdfDoc(String pdfDoc) {
        this.pdfDoc = pdfDoc;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    private int thumbnail;
    String img_path,url,id;

    int stage;

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Album( int stage, String chapter_id,String name,String details ) {
        this.chapter_id = chapter_id;
        this.stage = stage;
        this.name= name;
        this.details = details;
    }

    public Album(String name, String details, int thumbnail, int stage) {
        this.name = name;
        this.details = details;
        this.thumbnail = thumbnail;
        this.stage = stage;
    }
    public Album(String name,int stage, String topic_id, String subject_id, String img_path) {
        this.name = name;
        this.topic_id = topic_id;
        this.subject_id = subject_id;
        this.stage = stage;
        this.img_path = img_path;
    }

    public Album(int stage,String name, String subject_id, String topic_id, String chapter_id)
    {
        this.name = name;
        this.topic_id = topic_id;
        this.subject_id = subject_id;
        this.stage = stage;
        this.chapter_id = chapter_id;
        this.img_path = img_path;
    }

    public Album(String name,String chpater_content_id,int stage,String img_path, String chapter_id){
        this.name = name;
        this.stage = stage;
        this.img_path = img_path;
        this.chapter_id = chapter_id;
        this.chapter_id = chapter_id;
        this.chpater_content_id = chpater_content_id;

    }

    public Album(String name, String img_path, String url, int stage) {
        this.name = name;
        this.img_path = img_path;
        this.url = url;
        this.stage = stage;

    }
    public Album(String name, String img_path, int stage,String id) {
        this.name = name;
        this.img_path = img_path;
        this.stage = stage;
        this.id = id;
    }

    public Album(String name, String img_path) {
        this.name = name;
        this.img_path = img_path;
    }
    public Album(String id,String name, String main_menu_id, String sub_menu, String url,int stage) {
        this.name = name;
        this.id = id;
        this.main_menu_id = main_menu_id;
        this.sub_menu = sub_menu;
        this.url = url;
        this.stage = stage;
//TestSeriesFragement
        //TestSeriesFragement2
    }

    public Album(String id,String name,String heading,String url,String details ,String img_path,int stage, String test_type,int stage2)
    {
        this.name = name;
        this.id = id;
        this.heading = heading;
        this.img_path = img_path;
        this.details = details;
        this.url = url;
        this.stage = stage;
        this.test_type = test_type;
    }
    public Album(String id,String name,String heading,String url,String details ,String img_path,int stage)
    {
        this.name = name;
        this.id = id;
        this.heading = heading;
        this.img_path = img_path;
        this.details = details;
        this.url = url;
        this.stage = stage;

    }

    public Album(int stage,String id,String name,String access_type,String type ,String img_path,String url)
    {
        this.name = name;
        this.id = id;
        this.access_type = access_type;
        this.img_path = img_path;
        this.type = type;
        this.url = url;
        this.stage = stage;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public Album() {
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
