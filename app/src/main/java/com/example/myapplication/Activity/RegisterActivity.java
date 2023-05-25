package com.example.myapplication.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.CircleTransform;
import com.example.myapplication.Post;
import com.example.myapplication.R;
import com.example.myapplication.RegisterModel;
import com.example.myapplication.UniversityModel;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Boolean isFemale = true;
    Uri selectedImageURI;
    String title;
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    String textUniversity;

    ArrayList<String> universityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init("beer");
//        callApi();
        university();
    }

    public void init(String name){
        binding.textFirstname.setText(name);
        binding.textEmail.setText("michael.lawson@reqres.in");
        binding.password.setText("1234");

        Intent intent = new Intent(RegisterActivity.this, ConfirmRegisterActivity.class);

//        binding.rdbFemale.setChecked(false);
//        binding.rdbMale.setChecked(true);
        isFemale = binding.rdbFemale.isChecked();
        binding.rdbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (R.id.rdb_female == checkedId){
                    isFemale = true;
                } else {
                    isFemale = false;
                }
            }
        });

        ArrayList<String> listPrefix = new ArrayList<>();
        listPrefix.add("Miss.");
        listPrefix.add("Mrs.");
        listPrefix.add("Mr.");
        listPrefix.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, listPrefix);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                title = listPrefix.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textUniversity = universityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        binding.rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.:
//                        isFemale = true; break;
//                    case R.id.rdb_m:
//                        isFemale = false; break;
//                }
//            }
//        });

        ArrayList<String> arrayList = new ArrayList<>();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    arrayList.add(checkBox.getText().toString());
                } else {
                    arrayList.remove(checkBox.getText().toString());
                }
            }
        };

        binding.cbFootball.setOnClickListener(onClickListener);
        binding.cbBasketball.setOnClickListener(onClickListener);
        binding.cbBadminton.setOnClickListener(onClickListener);

        binding.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
            }
        });

        binding.textBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                StringBuilder result = new StringBuilder();
                                result.append(dayOfMonth).append("/").append(month+1).append("/").append(year);

                                binding.textBirthday.setText(result);

                                int age = mYear - year;
                                if (age >= 0) {
                                    binding.textAge.setText(String.valueOf(age));
                                } else {
                                    binding.textAge.setText("0");
                                }

                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = binding.textFirstname.getText().toString();
                String last = binding.textLastname.getText().toString();
                String mail = binding.textEmail.getText().toString();
                String phoneNumber = binding.textPhone.getText().toString();
                String birthday = binding.textBirthday.getText().toString();
                String age = binding.textAge.getText().toString();
                String province = binding.textProvince.getText().toString();
                String postcode = binding.textPostcode.getText().toString();
                String address = binding.textAddress.getText().toString();
//                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();


                String img;
                if (selectedImageURI != null) {
                    img = selectedImageURI.toString();
                } else {
                    img = "";
                }

                RegisterModel model = new RegisterModel(first, last, birthday, age, mail, phoneNumber, address, province, postcode, "", password, img,isFemale,title,arrayList, textUniversity);

                intent.putExtra("registerModel", model);

                startActivity(intent);

                finish();

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageURI = data.getData();

//            if (selectedImageURI != null) {
                binding.imgProfile.setImageURI(selectedImageURI);
//            }
            Picasso.get().load(selectedImageURI).error(R.mipmap.ic_launcher_round).transform(new CircleTransform()).into(binding.imgProfile);
        }
    }

    public void callApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<Post> call = service.getPostById(1);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(RegisterActivity.this, "STATUS : " + response.code(), Toast.LENGTH_SHORT).show();

                Post body = response.body();
                if (body != null) {
                    String result = "GET LIST \n\nTitle : " + body.getTitle() + "\n" + "Body : " + "UserId : " + body.getUserId();

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void university(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://universities.hipolabs.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<List<UniversityModel>> call = service.getUniversity("Thailand");
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                List<UniversityModel> body = response.body();
                for (UniversityModel obj : body) {
                    if (obj != null) {
                        universityList.add(obj.getName());
                    }
                }

                ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>(RegisterActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, universityList);
                binding.spUniversity.setAdapter(adapterUniversity);

            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "STATUS : Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}