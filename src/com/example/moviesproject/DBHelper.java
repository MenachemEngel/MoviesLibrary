package com.example.moviesproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, "movies.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String cmd = "CREATE TABLE movies (_id INTEGER PRIMARY KEY AUTOINCREMENT ,title TEXT ,description TEXT ,imgeUrl	TEXT);";
		db.execSQL(cmd);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
