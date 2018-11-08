package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery query = new ForecastQuery();
        query.execute();


    }

    private class ForecastQuery extends AsyncTask<String,Integer,String> {
        private String windSpeed, minTemp, maxTemp, currentTemp, iconName;
        private Bitmap bitmap;

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public String doInBackground(String ... args)
        {
            try {
                //connect to Server:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Read the XML:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    switch(xpp.getEventType())
                    {

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            if(name.equals("temperature")) {
                                minTemp = xpp.getAttributeValue(null, "min");
                                publishProgress(25);
                                maxTemp = xpp.getAttributeValue(null, "max");
                                publishProgress(50);
                                currentTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(75);
                            }
                            else if(name.equals("speed"))
                            {
                                windSpeed = xpp.getAttributeValue(null, "value");
                            }
                            else if(name.equals("weather"))
                            {
                                iconName = xpp.getAttributeValue(null,"icon");

                                if (!fileExistance(iconName + ".png")){
                                    Log.i("Downloading image: ", iconName + ".png");
                                    bitmap = HttpUtils.getImage("http://openweathermap.org/img/w/" + iconName + ".png");
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png",Context.MODE_PRIVATE);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                    FileInputStream fis = null;
                                } else {
                                    Log.i("Found image locally:  ", iconName + ".png");
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(iconName + ".png");
                                    } catch (FileNotFoundException e){
                                        e.printStackTrace();
                                    }
                                    bitmap = BitmapFactory.decodeStream(fis);
                                }
                                publishProgress(100);
                            }
                            Log.i("read XML tag:" , name);
                            break;

                        case XmlPullParser.TEXT:
                            break;
                    }
                    xpp.next();//look at next XML tag
                }
            }
            catch(Exception e)
            {
                Log.i("Exception", e.getMessage());
            }

            return "";
        }

        public void onProgressUpdate(Integer ...value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        public void onPostExecute(String result){
            TextView currentV = findViewById(R.id.currentTemp);
            TextView minV = findViewById(R.id.minTemp);
            TextView maxV = findViewById(R.id.maxTemp);
            TextView windV = findViewById(R.id.windSpeed);
            ImageView imageV = findViewById(R.id.imageView3);

            currentV.setText("Current: " + currentTemp + "°C");
            minV.setText("Min: " + minTemp + "°C");
            maxV.setText("Max: " + maxTemp + "°C");
            windV.setText("Wind: " + windSpeed + " km/h");
            imageV.setImageBitmap(bitmap);

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
