package com.lftechnology.activitylogger.charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Creates a graphical representation of data in Pie Charts
 */
public class PieChart extends View {
    private int completeCircle;
    private List<EachAppDetails> eachAppDetailsList;
    private int numberOfPie;
    private Paint paint;

    public PieChart(Context context) {
        super(context);
    }

    /**
     * @param context Calling Activity
     * @param size    Number of desired PIEs
     */
    public PieChart(Context context, int size) {
        super(context);
        completeCircle = 0;
        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsList();
        numberOfPie = size;
        setBackgroundColor(getResources().getColor(R.color.transparent));
        paint = new Paint();

        if (numberOfPie > eachAppDetailsList.size())
            numberOfPie = eachAppDetailsList.size();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float totalValue = 0, startAngle = 0, makeAngle;
        int x = getWidth(); // The Width of the canvas created
        int y = getHeight();// The height of the canvas created

        int radiusReference = x; //If if the device is in portrait mode
        if (x > y) radiusReference = y;// If the device is in landscape mode

        int imageY = y / 15;
        int imageX = x - x / 9;
        float imageSize = y / 28;
        float textSize = x / 20;

        int radius = radiusReference / 3;
        RectF rectF = new RectF(x / 2 - x / 15 - radius, y / 2 - radius, x / 2 - x / 15 + radius, y / 2 + radius);// A rect created with the circle's dimension

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2 * radius / 5);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        int[] pieChartColors = getResources().getIntArray(R.array.chartsColors);

        float[] appValuesDuration = new float[numberOfPie];
        String[] valueNames = new String[numberOfPie];
        Bitmap[] bitmaps = new Bitmap[numberOfPie];
        for (int i = 0; i < appValuesDuration.length; i++) {
            EachAppDetails current = eachAppDetailsList.get(i);
            appValuesDuration[i] = (float) current.eachAppUsageDuration;
            valueNames[i] = current.eachAppName;
            bitmaps[i] = ((BitmapDrawable) current.eachAppIcon).getBitmap();
        }

        for (float value : appValuesDuration) {
            totalValue = totalValue + value;
        }

        for (int i = 0; i < appValuesDuration.length; i++) {
            makeAngle = appValuesDuration[i] * completeCircle / totalValue;
            paint.setColor((pieChartColors[i % pieChartColors.length]));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rectF, -startAngle, -makeAngle, false, paint);
            canvas.drawBitmap(resizeBitmap(bitmaps[i], imageSize), imageX, imageY, paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(imageX - imageSize / 2, imageY + imageSize / 2, 10, paint);
            imageY = imageY + (int) imageSize;
            startAngle = startAngle + makeAngle;
        }

//        paint.setColor(getResources().getColor(R.color.border));
//        canvas.drawCircle(x / 2 - x / 15, y / 2, 2 * radius / 3, paint);


        String theTime = String.format("%02dh %02dm %02ds", ((int) totalValue / 1000 / 3600)
                , ((((int) totalValue / 1000) % 3600) / 60),(((int) totalValue / 1000) % 60));

        paint.setColor(getResources().getColor(R.color.textColorPrimary));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Total Time", x / 2 - x / 15, y / 2 - textSize, paint);
        canvas.drawText(theTime, x / 2 - x / 15, y / 2 + textSize, paint);
        /**
         * animates the canvas
         */
        if (completeCircle < 360) {
            completeCircle += 5;
            invalidate();   //Redraw canvas
        }

    }

    /**
     * Returns a square bitmap with the desired width
     *
     * @param bitmap The bitmap image to be resized
     * @param width  The desired width that you want to resize with
     * @return
     */
    private Bitmap resizeBitmap(Bitmap bitmap, float width) {
        int prevWidth = bitmap.getWidth();
        int prevHeight = bitmap.getHeight();
        float scale = width / (float) prevWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, prevWidth, prevHeight, matrix, false);  //Returns the resized bitmap
    }

}
