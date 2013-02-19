package com.orangesoft.postcard;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomizeActivity extends Activity
{
  public static final int DRAG = 1;
  public static final int NONE = 0;
  private static final String TAG = "Touch";
  public static final int ZOOM = 2;
  public static PointF mid = new PointF();
  public static int mode = 0;
  float d = 0.0F;
  Uri f;
  FrameLayout fm;
  String imagePath;
  ImageView iv;
  float[] lastEvent = null;
  Matrix matrix = new Matrix();
  float newRot = 0.0F;
  float oldDist;

  Matrix savedMatrix = new Matrix();
PointF start = new PointF();
  
  @Override
public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.customizescreen);
    fm = ((FrameLayout)findViewById(R.id.editpiclayout));
    imagePath = getIntent().getStringExtra("IMAGE_PATH");
    iv = new ImageView(this);
    iv.setPadding(10, 10, 25, 25);
    tv = new EditText(this);
    tv.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
    tv.setBackgroundColor(Color.TRANSPARENT);
//    tv.setInputType(1);
//    tv.setImeOptions(6);
    tv.setSingleLine();
    iv.setOnTouchListener(t);
    addImage(imagePath);
  }

  
  View.OnTouchListener t = new View.OnTouchListener()
  {
	  public boolean onTouch(View paramView, MotionEvent event)
	    {
	      ImageView view = (ImageView)paramView;
	      switch (event.getAction() & MotionEvent.ACTION_MASK)
	      {
	      case MotionEvent.ACTION_DOWN:

	          savedMatrix.set(matrix);
	          start.set(event.getX(), event.getY());
	          Log.d(TAG, "mode=DRAG" );
	          mode = DRAG;
	          break;
	      case MotionEvent.ACTION_POINTER_DOWN:

	          oldDist = spacing(event);
	          Log.d(TAG, "oldDist=" + oldDist);
	          if (oldDist > 10f) {

	              savedMatrix.set(matrix);
	              midPoint(mid, event);
	              mode = ZOOM;
	              Log.d(TAG, "mode=ZOOM" );
	          }
	          break;

	      case MotionEvent.ACTION_MOVE:

	          if (mode == DRAG) {

	              matrix.set(savedMatrix);
	              matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
	          }
	          else if (mode == ZOOM) {

	              float newDist = spacing(event);
	              Log.d(TAG, "newDist=" + newDist);
	              if (newDist > 10f) {

	                  matrix.set(savedMatrix);
	                  float scale = newDist / oldDist;
	                  matrix.postScale(scale, scale, mid.x, mid.y);
	              }
	          }
	          break;

	      case MotionEvent.ACTION_UP:
	      case MotionEvent.ACTION_POINTER_UP:

	          mode = NONE;
	          Log.d(TAG, "mode=NONE" );
	          break;
	      }  
	      view.setImageMatrix(matrix);
	      return true;
	     
	    }
	  
	  private void midPoint(PointF point, MotionEvent event) {

		    float x = event.getX(0) + event.getX(1);
		    float y = event.getY(0) + event.getY(1);
		    point.set(x / 2, y / 2);
		}

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }
  };
  EditText tv;

  public void addImage(String paramString)
  {
    f = Uri.parse(paramString);
    iv.setImageURI(f);
    iv.setScaleType(ImageView.ScaleType.MATRIX);
    fm.addView(iv);
  }

  public void newText()
  {
    tv.setHint("Add Greeting Text");
    fm.addView(tv);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.cutomizemenu, paramMenu);
    return true;
  }

  @Override
public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    case R.id.edit:
    	newText();
    	break;
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    }
    return true;
  }
}