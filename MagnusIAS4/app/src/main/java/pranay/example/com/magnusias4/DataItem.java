package pranay.example.com.magnusias4;

import android.util.Log;

public class DataItem {
    private  String newsHeading,newsDesc,newsDescSmall,time,date,url,imageURL,chapterURL;
    private int imageId;

    public String getNewsHeading() {
        return newsHeading;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getChapterURL() {
        return chapterURL;
    }

    public String getNewsDesc() {
        return newsDesc;

    }

    public String getNewsDescSmall() {
        return newsDescSmall;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public int getImageId() {
        return imageId;
    }

    public DataItem(String newsHeading, String newsDesc, String time, String date, String imageURL,String chapterURL) {
        this.newsHeading = newsHeading;


       long size = newsDesc.length();
       String data;

       if (size <150)
           data = newsDesc;
       else
           data = (String) newsDesc.subSequence(0,150);

        this.newsDesc = data+"...[Tap to read more]";
        //this.newsDesc = this.newsDesc.substring(0,20);
        this.time = time;
        this.date = date;
        //this.url = url;
        this.imageURL = imageURL;
        this.newsDescSmall = this.newsDesc ;
        this.chapterURL = chapterURL;
         //this.newsDescSmall = newsDesc.substring(0, 50);
        Log.i("Small",newsDescSmall);
       // this.newsDesc = this.newsDescSmall+"...";





    }

    public DataItem(String newsHeading, String newsDesc, String time, String date, String url, int imageId, String chapterURL) {
        this.newsHeading = newsHeading;
        this.newsDesc = newsDesc+ "...";
        this.time = time;
        this.date = date;
        this.url = url;
        this.imageId = imageId;
        this.chapterURL = chapterURL;
        // this.newsDescSmall = this.newsDesc.substring(0, 50);

    }
}
