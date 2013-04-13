package com.example.com.rallat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper{

	  private static final int DB_VERSION = 1;
	  private static final String DB_NAME = "test";

	  public static final String TABLE_NAME = "taxi";
	  public static final String NAME = "login";
	  public static final String ADDRESS = "passw";
	  private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( _id integer primary key autoincrement, "
	      + NAME + " TEXT, " + ADDRESS + " TEXT)";

	  public DBAdapter(Context context) {
	    super(context, DB_NAME, null,DB_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase sqLiteDatabase) {
	    sqLiteDatabase.execSQL(CREATE_TABLE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
	  }
	}
