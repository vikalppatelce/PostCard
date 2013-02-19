package com.orangesoft.postcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TipActivity extends Activity
{
  Button lstart;
  TextView wTitle;
  ImageView wTitleImage;

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.tipscreen);
    wTitle = ((TextView)findViewById(R.id.title));
    wTitleImage = ((ImageView)findViewById(R.id.titleimage));
    lstart = ((Button)findViewById(R.id.start));
    lstart.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        startActivity(new Intent(getApplicationContext(), PicPickerActivity.class));
      }
    });
  }
}