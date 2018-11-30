package com.chowchow.os.chowchow.ui.view.main.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.callback.ItemClickListener;
import com.chowchow.os.chowchow.constant.Constant;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.weather.WeatherModel;
import com.chowchow.os.chowchow.ui.adapter.AttractionsAdapter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private ImageView iv_back, imgAppName;
    private APIService mService;
    private ProgressDialog progressDialog;

    private NumberFormat format = new DecimalFormat("#0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mService = ApiUtils.getWeatherService();

        imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fetchWeatherDetails();
    }

    private void fetchWeatherDetails() {
        progressDialog = ProgressDialog.show(this, "Vui lòng đợi...",
                "Đang tải..!", true);
        /*
        Invoke the method corresponding to the HTTP request which will return a Call object. This Call object will used to send the actual network request with the specified parameters
        */
        Call<WeatherModel> call = mService.getWeatherByCity(Constant.WEATHER_LOCATION, Constant.OPEN_WEATHER_MAP_API_KEY);
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                if (response.isSuccessful()) {
                    WeatherModel jsonResponse = response.body();
                    TextView txtTemperature=(TextView) findViewById(R.id.txtTemperature);
                    TextView txtCurrentAddressName=(TextView) findViewById(R.id.txtCurrentAddressName);
                    ImageView imageView=(ImageView) findViewById(R.id.imgBauTroi);
                    TextView txtMaxtemp=(TextView) findViewById(R.id.txtMaxTemp);
                    TextView txtMinTemp=(TextView) findViewById(R.id.txtMinTemp);
                    TextView txtWind=(TextView) findViewById(R.id.txtWind);
                    TextView txtCloudliness= (TextView) findViewById(R.id.txtCloudliness);
                    TextView txtPressure= (TextView) findViewById(R.id.txtPressure);
                    TextView txtHumidty= (TextView) findViewById(R.id.txtHumidty);
                    TextView txtSunrise= (TextView) findViewById(R.id.txtSunrise);
                    TextView txtSunset= (TextView) findViewById(R.id.txtSunset);
                    txtCurrentAddressName.setText("Đà Nẵng");
                    double temperature= jsonResponse.getMain().getTemp()-273.15;
                    String maxtemp= format.format(jsonResponse.getMain().getTempMax()-273.15)+"°C";
                    String mintemp= format.format(jsonResponse.getMain().getTempMin()-273.15)+"°C";
                    String wind= jsonResponse.getWind().getSpeed()+" m/s";
                    String mesg = jsonResponse.getWeather().get(0).getMain();
                    //  Translator translate = Translator.getInstance();
                    // String cloudiness=mesg+" ("+translate.translate(mesg, Language.ENGLISH, Language.VIETNAMESE)+")";
                    String cloudiness=mesg;
                    String pressure= jsonResponse.getMain().getPressure()+" hpa";
                    String humidity=jsonResponse.getMain().getHumidity()+" %";
                    Calendar calendar = Calendar.getInstance();
                    Date timeSunrise = new Date(jsonResponse.getSys().getSunrise()*1000);
                    calendar.setTime(timeSunrise);
                    String Sunrise= calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " AM";
                    Date timeSunSet = new Date(jsonResponse.getSys().getSunset()*1000);
                    calendar.setTime(timeSunSet);
                    String sunset= calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                    txtTemperature.setText(format.format(temperature)+"°C");
                    String imgURL = Constant.OPEN_WEATHER_MAP_API_BASE_URL + "img/w/" + jsonResponse.getWeather().get(0).getIcon() + ".png";
                    Picasso.get().load(imgURL).centerCrop().resize(72, 72).into(imageView);
                    txtMaxtemp.setText(maxtemp);
                    txtMinTemp.setText(mintemp);
                    txtWind.setText(wind);
                    txtCloudliness.setText(cloudiness);
                    txtPressure.setText(pressure);
                    txtHumidty.setText(humidity);
                    txtSunrise.setText(Sunrise);
                    txtSunset.setText(sunset);
                    Log.d("WeatherActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("WeatherActivity", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                // Hide loading indicator
                progressDialog.dismiss();
                Log.d("Error", t.getMessage());
                Log.d("WeatherActivity", "error loading from API");
            }
        });
    }
}
