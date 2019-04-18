package com.example.mybeautybooking.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.mybeautybooking.R;
import com.example.mybeautybooking.SharedPrefManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 ;
    private String mParam2;

    public ArrayList<String> arrayList = new ArrayList<>();
    ProgressBar progressBar;

    EditText email;
    EditText firstnameText_Client;
    EditText nameText_Client;
    EditText passText_Client;
    EditText phoneIntClient;
    EditText streetText_Client;
    EditText zipInt_Client;
    EditText cityText_Client;

    Button doneButton;

    private RequestQueue requestQueue;
    private static final String URL = "http://10.192.129.78:80/beautybooking/user_control.php";
    private StringRequest request;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        nameText_Client = (EditText) view.findViewById(R.id.input_name);
        nameText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserName());

        firstnameText_Client = (EditText) view.findViewById(R.id.input_first_name);
        firstnameText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserFirstname());

        phoneIntClient= (EditText) view.findViewById(R.id.input_phone);
        phoneIntClient.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserPhone());

        streetText_Client = (EditText) view.findViewById(R.id.input_street);
        streetText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserStreet());

        cityText_Client = (EditText) view.findViewById(R.id.input_city);
        cityText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserCity());


        zipInt_Client = (EditText) view.findViewById(R.id.input_zip);
        cityText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserCity());

        email = (EditText) view.findViewById(R.id.input_email);
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());

        doneButton = (Button)view.findViewById(R.id.btn_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext (), R.string.done_msg, Toast.LENGTH_LONG).show();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
