package pranay.example.com.magnusias4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context,"MagnusDatabase",null,1);
        db = this.getWritableDatabase();
        Log.i("Result","Database Created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


      db.execSQL("CREATE TABLE `subject_categ` (" +
                "  `id`  varchar(255) PRIMARY KEY ,\n" +
                "  `name` varchar(255) ,\n" +
                "  `heading` varchar(255) ,\n" +
                "  `sub_heading` varchar(255) ,\n" +
                "  `details` varchar(5000) ,\n" +
               "  `for_subject` varchar(500) ,\n" +
                "  `src` varchar(255) \n" +

                ")");

        db.execSQL("CREATE TABLE `subject` (\n" +
                "  `id`  varchar(255) PRIMARY KEY  ,\n" +
                "  `name` varchar(255)  ,\n" +
                "  `heading` varchar(255)  ,\n" +
                "  `details` varchar(255)  ,\n" +
                "  `categ` varchar(500)  ,\n" +
                "  `img_path` varchar(255)  \n" +
                ")");



        db.execSQL("CREATE TABLE `test_topics` (\n" +
                "  `id`  varchar(255) PRIMARY KEY  ,\n" +
                "  `name` varchar(255)  ,\n" +
                "  `subject_id` varchar(255) ,\n" +
                "  `details` varchar(5000)  ,\n" +
                "  `img_path` varchar(255)  \n" +
                ")");

        db.execSQL("CREATE TABLE `faq` (\n" +
                "  `id`  varchar(255) PRIMARY KEY  ,\n" +
                "  `question` varchar(255)  ,\n" +
                "  `answer` varchar(5000) \n" +
                ")");
        db.execSQL("CREATE TABLE `mission_vision` (\n" +
                "  `id`  varchar(255) PRIMARY KEY  ,\n" +
                "  `page_name` varchar(255)  ,\n" +
                "  `details` varchar(5000) \n" +
                ")");

       db.execSQL("CREATE TABLE `chapter_list` (\n" +
                "  `id` int(11) ,\n" +
                "  `subject_id` varchar(255),\n" +
                "  `topic_id` varchar(255),\n" +
                "  `heading` varchar(255) ,\n" +
                "  `details` varchar(5000) ,\n" +
                "  `chaper_no` varchar(255) \n" +
                ")");


        db.execSQL("CREATE TABLE `chapter_content` (\n" +
                "  `id` int(11) ,\n" +
                "  `access_type` varchar(255) ,\n" +
                "  `subject` varchar(255) ,\n" +
                "  `video_id` varchar(255),\n" +
                "  `pdf_document` varchar(255) ,\n" +
                "  `description` varchar(5000) ,\n" +
                "  `img_thumb` varchar(255) ,\n" +
                "  `heading` varchar(255) ,\n" +
                "  `ch_num` varchar(255) ,\n"  +
                "  `topic` varchar(255) ,\n" +
                "  `chapter` varchar(255) \n" +
                ")");
        db.execSQL("CREATE TABLE `our_team` (\n" +
                "  `id` int(11) ,\n" +
                "  `name` varchar(255) ,\n" +
                "  `post` varchar(255) ,\n" +
                "  `src` varchar(255),\n" +
                "  `details` varchar(255) ,\n" +
                "  `email` varchar(5000) ,\n" +
                "  `mobile` varchar(255) ,\n" +
                "  `hobbies` varchar(255) ,\n" +
                "  `qualification` varchar(255) ,\n" +
                "  `skills` varchar(255) ,\n"  +
                "  `test` varchar(255) \n" +
                ")");
        db.execSQL("CREATE TABLE `director_desk` (\n" +
                "  `id` int(11) ,\n" +
                "  `name` varchar(255) ,\n" +
                "  `post` varchar(255) ,\n" +
                "  `src` varchar(255),\n" +
                "  `details` varchar(255) ,\n" +
                "  `email` varchar(5000) ,\n" +
                "  `mobile` varchar(255) ,\n" +
                "  `hobbies` varchar(255) ,\n" +
                "  `qualification` varchar(255) ,\n" +
                "  `skills` varchar(255) ,\n"  +
                "  `test` varchar(255) \n" +
                ")");
        db.execSQL("CREATE TABLE `answer_table` (\n" +
                "  `id` int(11) ,\n" +
                "  `test_id` varchar(255) ,\n" +
                "  `subject_name` varchar(255) ,\n" +
                "  `Question_number` varchar(255),\n" +
                "  `answer` varchar(255) \n" +
                ")");


        Log.i("Database","Database created!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1, int i, int i1) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();

        db.execSQL("drop table if exists subject_categ");
        db.execSQL("drop table if exists subject");
        db.execSQL("drop table if exists test_topics");
        db.execSQL("drop table if exists chapter_list");
        db.execSQL("drop table if exists chapter_content");
        db.execSQL("drop table if exists our_team");
        db.execSQL("drop table if exists director_desk");
        db.execSQL("drop table if exists faq");
        db.execSQL("drop table if exists mission_vision");




        onCreate(db);
    }

    void  createAnswerTable()
    {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("drop table if exists answer_table");

        db.execSQL("CREATE TABLE `answer_table` (\n" +
                "  `id` varchar(255) ,\n" +
                "  `test_id` varchar(255) ,\n" +
                "  `subject_name` varchar(255) ,\n" +
                "  `question_number` varchar(255),\n" +
                "  `answer` varchar(255), \n" +
                "  `marked_answer` varchar(255) \n" +
                ")");



    }
    void createOfflineTestDataTable(){
        try{

            SQLiteDatabase db;
            db = this.getWritableDatabase();
              db.execSQL("drop table if exists offline_test_data");
            db.execSQL("CREATE TABLE `offline_test_data` (\n" +
                    "  `id` varchar(255) ,\n" +
                    "  `name` varchar(255) ,\n" +
                    "  `test_type` varchar(255) ,\n" +
                    "  `total_question` varchar(3), \n" +
                    "  `marks_correct_ans` varchar(255) ,\n" +
                    "  `negative_mark` varchar(255) ,\n" +
                    "  `full_marks` varchar(255) ,\n" +
                    "  `duration` varchar(255) ,\n" +
                    "  `unit` varchar(255) ,\n" +
                    "  `level` varchar(255) ,\n" +
                    "  `image` varchar(255) ,\n" +
                    "  `general_ins` varchar(255) ,\n" +
                    "  `question_ins` varchar(255) ,\n" +
                    "  `ques_pdf` varchar(255) ,\n" +
                    "  `pdf_offline_path` varchar(255) ,\n" +
                    "  `attr2` varchar(255) \n" +

                    ")");

        }catch (Exception e){Log.i("offlineTestDatatable","Exists");}
    }

    public void setOfflineTestData(String id, String name, String test_type, String total_question, String marks_correct_ans, String negative_mark, String full_marks,
                                   String duration, String unit, String level, String image, String general_ins, String question_ins, String ques_pdf, String attr2, String pdf_offline_path) {

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("test_type",test_type);
        contentValues.put("total_question",total_question);
        contentValues.put("marks_correct_ans",marks_correct_ans);
        contentValues.put("negative_mark",negative_mark);
        contentValues.put("full_marks",full_marks);
        contentValues.put("duration",duration);
        contentValues.put("unit",unit);
        contentValues.put("level",level);
        contentValues.put("image",image);
        contentValues.put("general_ins",general_ins);
        contentValues.put("question_ins",question_ins);
        contentValues.put("ques_pdf",ques_pdf);
        contentValues.put("attr2",attr2);
        contentValues.put("pdf_offline_path",pdf_offline_path);
       contentValues.put("id",id);

        long result = db.insert("offline_test_data",null,contentValues);
        if (result== -1) {
            Log.i("updateOVT Result", "Row updation failed!!!");
        }
        else{
            Log.i(" UpdateOVT Session","Offline Test Row inserted Successfully!");
        }
    }


    void createOfflineVideoTable(){
        try{
        SQLiteDatabase db;
        db = this.getWritableDatabase();
          //  db.execSQL("drop table if exists offline_video_list");
        db.execSQL("CREATE TABLE `offline_video_list` (\n" +
                "  `videoID` varchar(255) ,\n" +
                "  `offline_video_path` varchar(255) ,\n" +
                "  `offline_video_title` varchar(255) ,\n" +
                "  `offline_pdf_path` varchar(255) ,\n" +
                "  `offline_availability` varchar(3) \n" +
                ")");
        }catch(Exception e){Log.i("OVT","Exists");}
    }


    void insertOVT(String videoID,String offline_availability,String offline_video_path, String offline_video_title,String offline_pdf_path){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("videoID",videoID);
        contentValues.put("offline_availability",offline_availability);
        contentValues.put("offline_video_title",offline_video_title);
        contentValues.put("offline_video_path",offline_video_path);
        contentValues.put("offline_pdf_path",offline_pdf_path);

        long result = db.insert("offline_video_list",null,contentValues);
        if (result== -1) {
            Log.i("updateOVT Result", "Row updation failed!!!");
        }
        else{
            Log.i(" UpdateOVT Session","Video Row inserted Successfully!");
        }

    }
    void removeVideo(String videoID)
    { SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("delete from offline_video_list  where videoID="+videoID);



    }

    Cursor getOVTData()

    {

        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from offline_video_list " ,null);
        Log.i("Video row count",""+cursor.getCount());

        return cursor;

    }
    Cursor getOVTData(String videoID){

        Cursor cursor =null;
try{
        SQLiteDatabase db;

        db = this.getReadableDatabase();


             cursor  = db.rawQuery("select offline_video_path,offline_pdf_path from offline_video_list where videoID = "+videoID ,null);


        Log.i("Video row count",""+cursor.getCount());
    return cursor;
    }catch(Exception e){Log.i("OVT Exception",e.toString());}

    return cursor;
    }


    public void createLoginSession(){
        try{
        SQLiteDatabase db;

        db = this.getWritableDatabase();

   //     db.execSQL("drop table if exists login_session");
        db.execSQL("CREATE TABLE `login_session` (\n" +
                "  `id`  varchar(255)  ,\n" +
                "  `status` varchar(255)    \n" +
                ")");

        }catch (Exception e){
            Log.i("login_session","exists");
        }
        }
    public void setSession(String status){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("delete from login_session");
        ContentValues contentValues  = new ContentValues();
        contentValues.put("id","001");
        contentValues.put("status",status);
        long result = db.insert("login_session",null,contentValues);
        if (result== -1) {
            Log.i("Login Session", "Row updation failed!!!");
        }
        else{
            Log.i("Login Session","Session updated Successfully!");
        }
    }

    public Cursor getSessionData(){
        SQLiteDatabase db;

        db = this.getReadableDatabase();

        Cursor cursor =  db.rawQuery("select status from login_session",null);
        return cursor;

    }

    public void setAnswerTableData(String id,String test_id,String subject_name,String question_number,String answer ){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("test_id",test_id);
        contentValues.put("subject_name",subject_name);
        contentValues.put("question_number",question_number);
        contentValues.put("answer",answer);
     //   Log.i("subject_name  DB Table",subject_name);

        long result = db.insert("answer_table",null,contentValues);
        if (result== -1) {
            Log.i("answer_table Result", "Row updation failed!!!");
        }else{
          //  Log.i("answer_table Result","Row updated successfully!!!");
        }

    }

    public Cursor getSavedAnswer(String test_id){
        SQLiteDatabase db;

        db = this.getReadableDatabase();
        Log.i("Cursor Test_id",""+test_id);

        Cursor cursor  = db.rawQuery("select id,marked_answer from answer_table",null);//" where test_id = "+test_id + " and question_number ="+ question_no

        return  cursor;
    }
    public Cursor  getAnswer( String test_id,String question_no){
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select answer,marked_answer from answer_table where test_id = "+test_id,null);//" where test_id = "+test_id + " and question_number ="+ question_no

        return  cursor;
    }
    public void saveAnswer(String question_number,String test_id, String marked_answer){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
     /*   contentValues.put("id",id);
        contentValues.put("test_id",test_id);
     */   contentValues.put("marked_answer",marked_answer);
       // contentValues.put("test_id",test_id);
        int update_result = db.update("answer_table",contentValues,"question_number = "+question_number+" and test_id = "+test_id,null);



    }

    public void setMissionData(String id, String page_name, String details) {
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("page_name",page_name);
        contentValues.put("details",details);
        long result = db.insert("mission_vision",null,contentValues);
        if (result== -1) {
            Log.i("mission_vision Result", "Row updation failed!!!");
        }else{
            Log.i("mission_vision Result","Row updated successfully!!!");
        }
    }

    public Cursor getMissionData() {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from mission_vision ",null);
        Log.i("mission row count",""+cursor.getCount());

        return cursor;

    }


    public void setFAQData(String id, String question, String answer) {
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("question",question);
        contentValues.put("answer",answer);

        long result = db.insert("faq",null,contentValues);
        if (result== -1) {
            Log.i("faq Result", "Row updation failed!!!");
        }else{
            Log.i("faq Result","Row updated successfully!!!");
        }




    }




    public Cursor getFAQData() {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from faq ",null);
        Log.i("faq row count",""+cursor.getCount());

        return cursor;

    }

    public void setDirectorDeskData(String id,String name,String post,String src,String details,String email,String mobile,String hobbies,String qualification,String skills){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("post",post);
        contentValues.put("src",src);
        contentValues.put("details",details);
        contentValues.put("email",email);
        contentValues.put("mobile",mobile);
        contentValues.put("hobbies",hobbies);
        contentValues.put("qualification",qualification);
        contentValues.put("skills",skills);
        long result = db.insert("director_desk",null,contentValues);
        if (result== -1) {
            Log.i("director_desk Result", "Row updation failed!!!");
        }else{
            Log.i("director_desk Result","Row updated successfully!!!");
        }

    }

    public Cursor getDirectorDeskData( ) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from director_desk ",null);
        Log.i("faq row count",""+cursor.getCount());

        return cursor;

    }



    public void chapterContentData(String id,String access_type,String subject,String video_id,String pdf_document,String description,String img,String cc_heading,String chapter_num,String chapter,String topic){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("access_type",access_type);
        contentValues.put("subject",subject);
        contentValues.put("video_id",video_id);
        contentValues.put("pdf_document",pdf_document);
        contentValues.put("description",description);
        contentValues.put("img_thumb",img);
        contentValues.put("heading",cc_heading);
        contentValues.put("ch_num",chapter_num);
        contentValues.put("chapter",chapter);
        contentValues.put("topic",topic);


        long result = db.insert("chapter_content",null,contentValues);
        if (result== -1) {
            Log.i("chapter_content Result", "Row updation failed!!!");
        }else{
            Log.i("chapter_content Result","Row updated successfully!!!");
        }



    }

    public void ourTeamData(String id,String name,String post,String src,String details,String email,String mobile,String hobbies,String qualification,String skills){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("post",post);
        contentValues.put("details",details);
        contentValues.put("src",src);
        contentValues.put("email",email);
        contentValues.put("mobile",mobile);
        contentValues.put("hobbies",hobbies);
        contentValues.put("qualification",qualification);
        contentValues.put("skills",skills);


        long result = db.insert("our_team",null,contentValues);
        if (result== -1) {
            Log.i("our_team Result", "Row updation failed!!!");
        }else{
            Log.i("our_team Result","Row updated successfully!!!");
        }



    }

    public Cursor getOurTeamData() {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from our_team ",null);
        Log.i("faq row count",""+cursor.getCount());

        return cursor;

    }


    public void insertChapterList(String id,String subject_id,String topic_id, String heading,  String details, String chaper_no){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("subject_id",subject_id);
        contentValues.put("topic_id",topic_id);
        contentValues.put("heading",heading);
        contentValues.put("details",details);
        contentValues.put("chaper_no",chaper_no);

        long result = db.insert("chapter_list",null,contentValues);
        if (result== -1) {
            Log.i("chapter_list Result", "Row updation failed!!!");
        }else{
            Log.i("chapter_list Result","Row updated successfully!!!");
        }


    }
    public void insertTopicData(String id, String name, String subject_id,  String details, String img_path){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("subject_id",subject_id);
        contentValues.put("details",details);
        contentValues.put("img_path",img_path);

        long result = db.insert("test_topics",null,contentValues);
        if (result== -1) {
            Log.i("test_topics Result", "Row updation failed!!!");
        }else{
            Log.i("test_topics Result","Row updated successfully!!!");
        }


    }

    public void insertChapterData(String id, String name, String heading,  String details,String categ, String img_path){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("heading",heading);
        contentValues.put("categ",categ);
        contentValues.put("details",details);
        contentValues.put("img_path",img_path);

        long result = db.insert("subject",null,contentValues);
        if (result== -1) {
            Log.i("Subject Result", "Row updation failed!!!");
        }else{
            Log.i("Subject Result","Row updated successfully!!!");
        }


    }
    public void insertSubjectData(String id, String name, String heading, String sub_heading, String details, String src, String for_subject){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("heading",heading);
        contentValues.put("sub_heading",sub_heading);
        contentValues.put("details",details);
        contentValues.put("src",src);
        contentValues.put("for_subject",for_subject);
        long result = db.insert("subject_categ",null,contentValues);
        if (result== -1) {
            Log.i("subject_categ Result", "Row updation failed!!!");
        }else{
            Log.i("subject_categ Result","Row updated successfully!!!");
        }


    }
    public Cursor getSubjectData(){
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from subject_categ",null);
        Log.i("Result from DB Class",""+cursor.getCount());

        return cursor;
    }

    public Cursor getTopicData(String categ){
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from subject where categ = "+categ,null);
        Log.i("Result from DB Class",""+cursor.getCount());

        return cursor;
    }
    public Cursor getTestTopicData(String subject_id){
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from test_topics where subject_id = "+subject_id,null);
        Log.i("Result from DB Class",""+cursor.getCount());

        return cursor;
    }

    public Cursor getChapterListData(String topic_id, String subject_id) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from chapter_list where subject_id = "+subject_id+ " and topic_id = "+topic_id,null);
        Log.i(" chapter_list Tble data",""+cursor.getCount());

        return cursor;

    }
    public Cursor getChapterListContentData(String subject_id,String topic_id, String chapter_id ) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("select * from chapter_content where subject = "+subject_id + " and topic ="+topic_id + " and chapter = "+chapter_id,null);
        Log.i("chapter_content",""+cursor.getCount());

        return cursor;

    }


}
