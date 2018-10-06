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
    public void createLoginSession(){
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        db.execSQL("drop table if exists login_session");
        db.execSQL("CREATE TABLE `login_session` (\n" +
                "  `id`  varchar(255) PRIMARY KEY  ,\n" +
                "  `status` varchar(255)    \n" +
                ")");

    }
    public void setSession(String id,String status){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("status",status);
        long result = db.insert("login_session",null,contentValues);
        if (result== -1) {
            Log.i("login_session Result", "Row updation failed!!!");
        }
        else{
            Log.i(" Login Session","Session updated Successfully!");
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
