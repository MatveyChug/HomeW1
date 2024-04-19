package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ArtView extends View {

    //поля
    private Canvas canvas; //холст
    private Paint paint; //стиль рисования
    private Path path; //конутр рисования
    private Bitmap bitmap; //контенйер для рисунков
    private final int PAINT_COLOR_DEF = 0xFF8E5DD5; //цвет по умолчанию

    //конструктор для кастомизиции
    public ArtView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Настройка отрисовки
        path = new Path(); //обьект контура

        paint = new Paint(); // стиль рисования
        paint.setAntiAlias(true); //сглаживание краёв
        paint.setStyle(Paint.Style.STROKE);//стиль кисти
        paint.setStrokeCap(Paint.Cap.ROUND);//вид концов линий
        paint.setStrokeJoin(Paint.Join.ROUND);//стиль обьединения рисуемых линий
        paint.setColor(PAINT_COLOR_DEF);//цвет линии
        paint.setStrokeWidth(30);//толщина линий


    }
    @Override
    protected  void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //создание пустого изображения с шириной длиной и высотой и цветом
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //создание холста для создания изображения
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //опредение рисунка на холсте
        canvas.drawBitmap(bitmap, 0,0,paint); //помещение на холст отрисованных линий
        canvas.drawPath(path, paint); //показ отрисованных линий
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //извлечение кординат касания View
        float x = event.getX();
        float y = event.getY();

        //обраюотка событий касания View
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: //прикосновение к view
                path.moveTo(x,y); //определение начала
                path.lineTo(x,y);//задание линии контура
                break;
            case MotionEvent.ACTION_MOVE://движение по экрну
                path.lineTo(x,y);//задание линии контура
                break;
            case MotionEvent.ACTION_UP: //отпускание экрана
                canvas.drawPath(path, paint); //отрисовка на холсте
                path.reset();// прерывание линии
                break;
    }
    invalidate(); //обновление view
    return true;
    }
    public void erase(){
        //очистка экрана и заполнение его белым цветом
        canvas.drawColor(0xFFFFFFFF, PorterDuff.Mode.CLEAR);
        //обновление представление view
        invalidate();
    }
}
