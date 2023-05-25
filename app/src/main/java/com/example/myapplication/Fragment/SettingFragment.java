package com.example.myapplication.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.LocaleList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Activity.EditProfileActivity;
import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.FragmentSettingBinding;

import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "mTitle";

    // TODO: Rename and change types of parameters
    private String mTitle;

    public SettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String mTitle) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, mTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        init();

        return view;

    }

    public void init() {
        getCurrentLocale();

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);


            }
        });

        binding.toggleLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.toggleLang.isChecked()) {
                    setLocale("th");
                } else {
                    setLocale("en");
                }
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear token
                SharedPreferences mPrefs = requireContext().getSharedPreferences("MySharedPreference", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putString("token", "");
                prefsEditor.apply();

                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    private void getCurrentLocale() {
        LocaleList current = getResources().getConfiguration().getLocales();
        Locale locale = current.get(0);
        String lang = locale.getCountry();
        if (lang.equalsIgnoreCase("th")) {
            binding.toggleLang.setChecked(true);
        } else {
            binding.toggleLang.setChecked(false);
        }
    }
    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        getResources().getConfiguration().setLocale(myLocale);

        Resources res = requireActivity().getResources();
        Configuration config = new Configuration(res.getConfiguration());
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());

        binding.setting.setText(R.string.setting);
        binding.editProfile.setText(R.string.edit_profile);
        binding.language.setText(R.string.language);
        binding.btnLogout.setText(R.string.logout);
    }
}