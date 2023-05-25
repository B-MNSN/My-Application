package com.example.myapplication.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.myapplication.R;
import com.example.myapplication.RegisterModel;
import com.example.myapplication.UniversityModel;
import com.example.myapplication.databinding.ActivityEditProfileBinding;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    ActivityRegisterBinding includeBinding;

    ArrayList<String> universityList = new ArrayList<>();
    RegisterModel model;
    Uri selectedImageURI;

    Boolean isFemale = true;

    String title;
    String textUniversity;

    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        includeBinding = binding.layout;

        model = getSharedPreference();
        university();

        ArrayList<String> listPrefix = new ArrayList<>();
        listPrefix.add("Miss.");
        listPrefix.add("Mrs.");
        listPrefix.add("Mr.");
        listPrefix.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, listPrefix);
        includeBinding.spinner.setAdapter(adapter);

        for (int i = 0; i < listPrefix.size(); i++){
            if (model.getTitle().equalsIgnoreCase(listPrefix.get(i))){
                includeBinding.spinner.setSelection(i);
            }
        }

        includeBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                title = listPrefix.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        includeBinding.spUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                textUniversity = universityList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        includeBinding.cbFootball.setOnClickListener(onClickListener);
        includeBinding.cbBasketball.setOnClickListener(onClickListener);
        includeBinding.cbBadminton.setOnClickListener(onClickListener);

        includeBinding.textFirstname.setText(model.getFirstname());
        includeBinding.textLastname.setText(model.getLastname());
        includeBinding.textBirthday.setText(model.getBirthday());
        includeBinding.textAge.setText(model.getAge());

        isFemale = model.getGender();
        if (isFemale) {
            includeBinding.rdbFemale.setChecked(true);
        } else {
            includeBinding.rdbMale.setChecked(true);
        }
        includeBinding.textAddress.setText(model.getAddress());
        includeBinding.textProvince.setText(model.getProvince());
        includeBinding.textPostcode.setText(model.getPostcode());
        includeBinding.textPhone.setText(model.getPhone());
        includeBinding.textEmail.setText(model.getEmail());
        includeBinding.password.setText(model.getPassword());

        for (int i = 0; i < model.getHobbyList().size(); i++) {
            if (includeBinding.cbFootball.getText().toString().equalsIgnoreCase(model.getHobbyList().get(i))) {
                includeBinding.cbFootball.setChecked(true);
            }
            if (includeBinding.cbBadminton.getText().toString().equalsIgnoreCase(model.getHobbyList().get(i))) {
                includeBinding.cbBadminton.setChecked(true);
            }
            if (includeBinding.cbBasketball.getText().toString().equalsIgnoreCase(model.getHobbyList().get(i))) {
                includeBinding.cbBasketball.setChecked(true);
            }
        }

        if (!model.getImageURI().isEmpty()) {
            Picasso.get().load(model.getImageURI()).error(R.mipmap.ic_launcher_round).transform(new CircleTransform()).into(includeBinding.imgProfile);
        }

        includeBinding.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
            }
        });

        isFemale = includeBinding.rdbFemale.isChecked();
        includeBinding.rdbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (R.id.rdb_female == checkedId){
                    isFemale = true;
                } else {
                    isFemale = false;
                }
            }
        });

        includeBinding.buttonRegister.setText(R.string.buttonUpdate);

        includeBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = includeBinding.textFirstname.getText().toString();
                String last = includeBinding.textLastname.getText().toString();
                String mail = includeBinding.textEmail.getText().toString();
                String phoneNumber = includeBinding.textPhone.getText().toString();
                String birthday = includeBinding.textBirthday.getText().toString();
                String age = includeBinding.textAge.getText().toString();
                String province = includeBinding.textProvince.getText().toString();
                String postcode = includeBinding.textPostcode.getText().toString();
                String address = includeBinding.textAddress.getText().toString();
                String password = includeBinding.password.getText().toString();
                String img;
                if (selectedImageURI != null) {
                    img = selectedImageURI.toString();
                } else {
                    img = "";
                }
                RegisterModel newModel = new RegisterModel(first, last, birthday, age, mail, phoneNumber, address, province, postcode, "", password, img,isFemale,title,arrayList, textUniversity);

                SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(newModel);
                prefsEditor.putString("MyObject", json);
                prefsEditor.apply();

            }
        });

        String[] currentBirthday = model.getBirthday().split("/");
        if (currentBirthday.length == 3) {
            mYear = Integer.parseInt(currentBirthday[2]);
            mMonth = Integer.parseInt(currentBirthday[1]);
            mDay = Integer.parseInt(currentBirthday[0]);
        }
        includeBinding.textBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                StringBuilder result = new StringBuilder();
                                result.append(dayOfMonth).append("/").append(month+1).append("/").append(year);

                                includeBinding.textBirthday.setText(result);

                                int age = mYear - year;
                                if (age >= 0) {
                                    includeBinding.textAge.setText(String.valueOf(age));
                                } else {
                                    includeBinding.textAge.setText("0");
                                }

                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

        includeBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                title = listPrefix.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageURI = data.getData();

            includeBinding.imgProfile.setImageURI(selectedImageURI);
            Picasso.get().load(selectedImageURI).error(R.mipmap.ic_launcher_round).transform(new CircleTransform()).into(includeBinding.imgProfile);
        }
    }

    private RegisterModel getSharedPreference() {
        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        RegisterModel obj = gson.fromJson(json, RegisterModel.class);

        return obj;
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

                ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>(EditProfileActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, universityList);
                includeBinding.spUniversity.setAdapter(adapterUniversity);

                for (int i = 0; i < universityList.size(); i++){
                    if (model.getUniversity().equalsIgnoreCase(universityList.get(i))){
                        includeBinding.spUniversity.setSelection(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "STATUS : Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}