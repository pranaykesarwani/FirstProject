package pranay.example.com.magnusias4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SubjectDatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    public SubjectDatabaseHelper(Context context) {
        super(context,"SampleDatabase",null,1);
         sqLiteDatabase = this.getWritableDatabase();
      //  Toast.makeText(context, "Database Created Successfully!!!!!", Toast.LENGTH_SHORT).show();
        Log.i("Result","SampleDatabase created successfully!!!");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table data (id integer primary key autoincrement , name TEXT, heading TEXT ,categ TEXT, details TEXT ,  img_path TEXT, url TEXT  )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabas, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists data");
        onCreate(sqLiteDatabase);

    }
    public void insertData(String name, String heading, String details, String categ, String img_path, String url){
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("heading",heading);
        contentValues.put("details",details);
        contentValues.put("categ",categ);
        contentValues.put("img_path",img_path);
        contentValues.put("url",url);

        long result = sqLiteDatabase.insert("data",null,contentValues);
        if (result== -1) {
            Log.i("Result", "Row updation failed!!!");
        }else{
            //Log.i("Result","Row updated successfully!!!");
        }


    }

    public Cursor getAllData(){
        sqLiteDatabase = this.getReadableDatabase();


        Cursor cursor  = sqLiteDatabase.rawQuery("select * from data",null);
        Log.i("Result from DB Class",""+cursor.getCount());

        return cursor;
    }

}
