package com.example.telpic;


import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
//import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class Data
{
    float x, y;
    Data (float xp, float yp)
    {
        x = xp;
        y = yp;
    }
}

public class Sketch extends View implements OnTouchListener {

    private float width;    // width of one tile
    private float height;   // height of one tile
    Paint background, translucentRedPen, bluePen;

    ArrayList<Data> pointList = new ArrayList<Data>();   

    private void init()
    {
        background = new Paint();
        //background.setStyle(Paint.Style.FILL_AND_STROKE);
        background.setColor(0xffcfffff);

        translucentRedPen = new Paint();
        translucentRedPen.setColor(getResources().getColor(R.color.translucentRedPen));

        bluePen = new Paint();
        bluePen.setColor(getResources().getColor(R.color.bluePen));
        bluePen.setStyle(Paint.Style.STROKE);
        bluePen.setTextSize(24);
        bluePen.setTextScaleX(.75f);
        bluePen.setTextAlign(Paint.Align.CENTER); // Try LEFT and RIGHT
        //FontMetrics fm = bluePen.getFontMetrics();
        //float textHeight = fm.descent + fm.ascent;       

        //setFocusable(true);
        //setFocusableInTouchMode(true);
    }
    
    public Sketch(Context context) {
        super(context);
        init();
    }

    public Sketch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Sketch(Context context, 
            AttributeSet ats, 
            int defaultStyle) {
        super(context, ats, defaultStyle);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the background...
        width = getWidth();
        height = getHeight();
        float centerX = width/2;
        float centerY = height/2;
        float radius = Math.min(width, height)/2;
        float delta = 10;

        canvas.drawRect(0, 0, width, height, background);

//        canvas.drawCircle(centerX, centerY, radius,  bluePen);
        float radius2 = radius-delta;
//        canvas.drawCircle(centerX, centerY, radius2,  bluePen);
        canvas.drawLine(centerX-radius2, centerY, centerX+radius2 ,centerY , bluePen);
        canvas.drawLine(centerX, centerY-radius2, centerX ,centerY+radius2 , bluePen);

//        canvas.drawText("abcdefghijklmnopqrstuvwxyz", centerX,centerY, bluePen);

        canvas.save();
//        canvas.rotate(-90, centerX, centerY);
//        canvas.drawText("abcdefghijklmnopqrstuvwxyz", centerX,centerY, bluePen);
        canvas.restore();        

//        canvas.drawCircle(centerX, centerY, radius,  translucentRedPen);
        //For Drawing on screen with your finger
        for (int i=1; i < pointList.size(); i++)
        {
            Data d1 = pointList.get(i-1);
            Data d2 = pointList.get(i);
            canvas.drawLine(d1.x, d1.y, d2.x, d2.y, bluePen);
        }

    }

    public boolean onTouch(View v, MotionEvent event) {     

        // Handle touch events here...
        float x = event.getX();
        float y = event.getY();
        String type="unknown";
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        //Finger is down
        case MotionEvent.ACTION_DOWN:            
            type="DOWN ";
//            pointList.clear();
            pointList.add(new Data(x,y));
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            type="ACTION_POINTER_DOWN ";        
            break;
        case MotionEvent.ACTION_UP:
        	//finger is picked up off screen
        case MotionEvent.ACTION_POINTER_UP:
            pointList.add(new Data(x,y));                 
            type="UP";
            break;
            //finger is moving
        case MotionEvent.ACTION_MOVE:          
            type= "ACTION_MOVE";
            pointList.add(new Data(x,y));              
            break;
        }
        invalidate();
        return true; // indicate event was handled
    }
}