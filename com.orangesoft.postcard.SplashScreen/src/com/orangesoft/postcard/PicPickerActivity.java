package com.orangesoft.postcard;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PicPickerActivity extends Activity
{
  int column_index;
  Button f;
  Button g;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.picpickerscreen);
    g = ((Button)findViewById(R.id.gallerybutton));
    f = ((Button)findViewById(R.id.facebookbutton));
    g.setOnClickListener(l);
    f.setOnClickListener(m);
  }
  
  View.OnClickListener l = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      Intent localIntent = new Intent();
      localIntent.setType("image/*");
      localIntent.setAction("android.intent.action.GET_CONTENT");
      PicPickerActivity.this.startActivityForResult(Intent.createChooser(localIntent, "Select Picture"), 1);
    }
  };
  View.OnClickListener m = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
    }
  };
  String selectedImagePath;
  Uri selectedImageUri;

  public String getPath(Uri paramUri)
  {
    Cursor localCursor = managedQuery(paramUri, new String[] { "_data" }, null, null, null);
    this.column_index = localCursor.getColumnIndexOrThrow("_data");
    localCursor.moveToNext();
    return localCursor.getString(this.column_index);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramInt2 == -1))
    {
      this.selectedImageUri = paramIntent.getData();
      Intent localIntent = new Intent(getApplicationContext(), CustomizeActivity.class);
      this.selectedImagePath = getPath(this.selectedImageUri);
      localIntent.putExtra("IMAGE_PATH", this.selectedImagePath);
      startActivity(localIntent);
    }
  }
}
