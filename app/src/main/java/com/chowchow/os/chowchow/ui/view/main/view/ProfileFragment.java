package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.constant.Constant;
import com.chowchow.os.chowchow.model.User;
import com.chowchow.os.chowchow.realm.UserDAO;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String user_name, name, email, phone, password, avatar;
    private String mParam2;
    private EditText input_name, input_user_name, input_email, input_phone, input_password;
    private CircleImageView civ_avatar;
    private Button btn_save, btn_logout;
    private OnFragmentInteractionListener mListener;
    private String selectedImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_name = getArguments().getString("USER_NAME");
            password = getArguments().getString("PASSWORD");
            name = getArguments().getString("NAME");
            email = getArguments().getString("EMAIL");
            phone = getArguments().getString("PHONE");
            avatar = getArguments().getString("AVATAR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Get init Realm instance
        final UserDAO userDAO = new UserDAO();

        btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        input_name = (EditText) view.findViewById(R.id.input_name);
        input_email = (EditText) view.findViewById(R.id.input_email);
        input_password = (EditText) view.findViewById(R.id.input_password);
        input_user_name = (EditText) view.findViewById(R.id.input_user_name);
        input_phone = (EditText) view.findViewById(R.id.input_phone);
        civ_avatar = (CircleImageView) view.findViewById(R.id.civ_avatar);

        Log.d("user_name",""+user_name);
        Log.d("password",""+password);
        Log.d("email",""+email);
        Log.d("phone",""+phone);
        User user = userDAO.getUserUpdate(user_name);
        Log.d("getUserName",""+user.getUserName());
        Log.d("getPassword",""+user.getPassword());
        Log.d("getEmail",""+user.getEmail());
        Log.d("getPhone",""+user.getPhone());
        Log.d("getAvatar",""+user.getAvatar());
        input_user_name.setText(user.getUserName());
        input_name.setText(user.getName());
        input_password.setText(user.getPassword());
        input_email.setText(user.getEmail());
        input_phone.setText(user.getPhone());
        selectedImage = user.getAvatar();
        if (selectedImage != null) {
            Uri mUri = Uri.parse(selectedImage);
            civ_avatar.setImageURI(mUri);
        }

        civ_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, Constant.RESULT_LOAD_IMAGE);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDAO.updateLogin(user_name, false);

                Toast.makeText(getContext(), "Đăng xuất thành công !", Toast.LENGTH_SHORT).show();

                Fragment fragment = new LoginFragment();
                loadFragment(fragment);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = input_user_name.getText().toString().trim();
                final String email = input_email.getText().toString().trim();
                final String name = input_name.getText().toString().trim();
                final String phone = input_phone.getText().toString().trim();
                final String password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Mật khẩu phải lớn hơn 6 ký tự !", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAvatar(selectedImage);
                user.setLogin(true);
                Log.d("setName",""+name);
                Log.d("setPassword",""+password);
                Log.d("setEmail",""+email);
                Log.d("setPhone",""+phone);
                userDAO.updateUser(user);
                User us = userDAO.getUserUpdate(userName);
                Log.d("userDAOName",""+us.getName());
                Log.d("userDAOPassword",""+us.getPassword());
                Log.d("userDAOEmail",""+us.getEmail());
                Log.d("userDAOPhone",""+us.getPhone());
                Log.d("userDAOAvatar",""+us.getAvatar());
                Toast.makeText(getContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData().toString();
            Uri mUri = data.getData();
            civ_avatar.setImageURI(mUri);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
