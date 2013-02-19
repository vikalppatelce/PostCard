package com.orangesoft.postcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity
{
  private final int SPLASH_DISPLAY_LENGTH = 3000;

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.main);
   
    new Thread()
    {
      public void run()
      {
        try
        {
          sleep(SPLASH_DISPLAY_LENGTH);
          Intent localIntent = new Intent(SplashScreen.this, InfoActivity.class);
          SplashScreen.this.startActivity(localIntent);
          SplashScreen.this.finish();
          return;
        }
        catch (Exception localException)
        {
        }
      }
    }
    .start();
  }
}
