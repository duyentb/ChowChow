package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.realm.UserDAO;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

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

        input_user_name.setText(user_name);
        input_name.setText(name);
        input_password.setText(password);
        input_email.setText(email);
        input_phone.setText(phone);

        if (avatar != null) {
            Picasso.get().load(avatar).centerCrop().resize(150, 150).into(civ_avatar);
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDAO.updateLogin(user_name, false);

                Toast.makeText(getContext(), "Đăng xuất thành công !", Toast.LENGTH_SHORT).show();

                Fragment fragment = new LoginFragment();
                loadFragment(fragment);
            }
        });

        return view;
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
