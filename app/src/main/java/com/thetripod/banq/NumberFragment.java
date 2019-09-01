package com.thetripod.banq;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumberFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {


    private Spinner spin_city,spin_branch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText phoneNumber;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText name;
    private OnFragmentInteractionListener mListener;

    public NumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumberFragment newInstance(String param1, String param2) {
        NumberFragment fragment = new NumberFragment();
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
                final View rootView = inflater.inflate(R.layout.fragment_number, container, false);

                spin_city = (Spinner)rootView.findViewById(R.id.spin_city);
                spin_branch= (Spinner)rootView.findViewById(R.id.spin_branch);
                spin_city.setOnItemSelectedListener(this);
                final String city =  String.valueOf(spin_city.getSelectedItem());
                final String branch= String.valueOf(spin_branch.getSelectedItem());


                Button confirm_number = rootView.findViewById(R.id.button_confirm_number);
                phoneNumber=rootView.findViewById(R.id.enter_number);
                name = rootView.findViewById(R.id.enter_name);
                confirm_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryCodePicker ccp;
                ccp = (CountryCodePicker)rootView.findViewById(R.id.ccp);
                final String city =  String.valueOf(spin_city.getSelectedItem());
                final String branch= String.valueOf(spin_branch.getSelectedItem());
                String numWithCode=ccp.getSelectedCountryCodeWithPlus() + phoneNumber.getText().toString().trim();
                String number = NumberToFirebase(numWithCode);
                Log.i("NumCheck",number);
                Bundle args = new Bundle();
                args.putString("NUMBER_TRANSFER",number);
                args.putString("NAME_BANKER",name.getText().toString().trim());
                args.putString("CITY",city);
                args.putString("BRANCH",branch);
                OTPFragment fragment = new OTPFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_login, fragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rootView;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1= String.valueOf(spin_city.getSelectedItem());
        //Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("Kolkata")) {
            List<String> list = new ArrayList<String>();
            list.add("FGMO, KOLKATA");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spin_branch.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Delhi")) {
            List<String> list = new ArrayList<String>();
            list.add("LAVI PUB SCHOOL GHEVRA");
            list.add("AF SCHOOL DELHI");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spin_branch.setAdapter(dataAdapter2);
        }
        if(sp1.contentEquals("Bangalore")) {
            List<String> list = new ArrayList<String>();
            list.add("CARD CENTRE");
            list.add("CO GAD BANGALORE");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spin_branch.setAdapter(dataAdapter2);
        }
        if(sp1.contentEquals("Mumbai")) {
            List<String> list = new ArrayList<String>();
            list.add("MUMBAI FORT");
            list.add("MUMBAI KHAR");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spin_branch.setAdapter(dataAdapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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


    String NumberToFirebase(String number)
    {
        if(number.isEmpty()||number.length()<12) {
            phoneNumber.setError("Enter number");
            phoneNumber.requestFocus();
            return null;
        }
        else {
            //String phonenumberfull = "+" + number;
            String phonenumberfull =  number;
            return phonenumberfull;
        }
    }
}
