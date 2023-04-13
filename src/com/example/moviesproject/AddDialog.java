package com.example.moviesproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class AddDialog extends Dialog implements OnClickListener ,Constants {

	private Activity dialogActivity;


	public AddDialog(Activity dialogActivity) {
		super(dialogActivity);
//		setTitle("Message:");
		this.dialogActivity = dialogActivity;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);

		findViewById(R.id.dialog_myself_button).setOnClickListener(this);
		findViewById(R.id.dialog_internet_button).setOnClickListener(this);

		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_myself_button:
			Intent intent = new Intent(dialogActivity, AddMyselfMovieActivity.class);
			intent.putExtra("mode", ADD_MYSELF_MOVIE_ACTIVITY);
			dialogActivity.startActivityForResult(intent, ADD_MYSELF_MOVIE_ACTIVITY);
			break;
		case R.id.dialog_internet_button:
			dialogActivity.startActivityForResult(new Intent(dialogActivity ,InternetActivity.class), Constants.INTERNET_ACTIVITY);
			break;
			
		}
		dismiss();
	}

}
