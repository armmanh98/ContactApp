package com.example.user.contactsapp.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.DataBasa.DatabaseHandler;
import com.example.user.contactsapp.Image.Image;
import com.example.user.contactsapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

//import android.net.Uri;

/**
 * Created by User on 7/13/2017.
 */

public class AddOrEditContactFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btnSubmit;
    private EditText etName;
    private EditText etNumber;
    private EditText etAge;
    private Spinner spinner;
    private ImageView imgPhoto;

    private DatabaseHandler db;

    public static final String FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY = "from  where  replace or add AddOrEditFr";
    public static final String NAME_OF_EDITABLE_ITEM = "name edit cont";
    public static final String NUMBER_OF_EDITABLE_ITEM = "number edit cont";
    public static final String AGE_OF_EDITABLE_ITEM = "age edit cont";
    public static final String GENDER_OF_EDITABLE_ITEM = "gender edit cont";
    public static final String ID_OF_EDITABLE_ITEM = "Id edit cont";
    private static final int CAMERA_REQUEST = 1888;
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri mUri;


    private ArrayList imagesPaths = new ArrayList();
    String mCurrentPhotoPath;

    String regEx = "[+][0-9]{10,13}$";
    String regEx2 = "[0][0-9]{10,13}$";
    String regEx3 = "[00][0-9]{10,13}$";

    public static AddOrEditContactFragment newInstance(Bundle bundle) {

        AddOrEditContactFragment myFragment = new AddOrEditContactFragment();
        myFragment.setArguments(bundle);
        return myFragment;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.choice_photo,menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getActivity().getMenuInflater();
        inflater.inflate(R.menu.choice_photo, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Toast.makeText(getActivity(),item.getTitle().toString() ,Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        dispatchTakePictureIntent();
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            Log.i("TAG", mCurrentPhotoPath + " aaa:::");

            imgPhoto.setImageURI(mUri);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mUri = Uri.fromFile(photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());


        imgPhoto = view.findViewById(R.id.fragment_edit_add_photo_img);
        etName =  view.findViewById(R.id.fragmentEdit_name);
        etNumber =  view.findViewById(R.id.fragmentEdit_number);
        etAge =  view.findViewById(R.id.fragmentEdit_age);
        btnSubmit =  view.findViewById(R.id.fragmentEdit_btn_submit);
        spinner =  view.findViewById(R.id.spinner_gender);

        registerForContextMenu(imgPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().openContextMenu(view);
            }
        });

        registerForContextMenu(etName);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.genders_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etName.getText().toString().isEmpty())
                    etName.setError(getString(R.string.error_empty_field));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etAge.getText().toString().isEmpty())
                    etAge.setError(getString(R.string.error_empty_field));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etNumber.getText().toString().isEmpty())
                    etNumber.setError(getString(R.string.error_empty_field));

                if(!etNumber.getText().toString().matches(regEx) && !etNumber.getText().toString().matches(regEx2) && !etNumber.getText().toString().matches(regEx3)){
                    etNumber.setError(getString(R.string.wrong_number));
                }
//                if (!isValidMobile(etNumber.getText().toString()))
//                    etNumber.setError("AAA");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        switch (getArguments().getInt(FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY)) {

            case 1:
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!etName.getText().toString().isEmpty() &
                                !etNumber.getText().toString().isEmpty() &
                                !etAge.getText().toString().isEmpty()
                                ) {
//                            addNewContact();
                            db.addImage(new Image(mUri.toString(),"AAA",db.addContact(new Contact(
                                    etName.getText().toString(),
                                    etNumber.getText().toString(),
                                    etAge.getText().toString(),
                                    spinner.getSelectedItem().toString()
                            ))));



                            Fragment addContactFragment = ContactsListFragment.newInstance();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();

                            transaction.replace(R.id.mainActivity_list_place, addContactFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();
                        } else
                            Toast.makeText(getActivity(), R.string.wrong_statement, Toast.LENGTH_LONG).show();
                    }
                });
                break;

            case 2:
                etName.setText(getArguments().getString(NAME_OF_EDITABLE_ITEM));
                etNumber.setText(getArguments().getString(NUMBER_OF_EDITABLE_ITEM));
                etAge.setText(getArguments().getString(AGE_OF_EDITABLE_ITEM));
                spinner.setSelection("Male".equals(getArguments().getString(GENDER_OF_EDITABLE_ITEM)) ? 0 : 1);



                btnSubmit.setText(R.string.edit_button_text);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment addContactFragment = ContactsListFragment.newInstance();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        if (etName.getText().toString().isEmpty() ||
                                etNumber.getText().toString().isEmpty() ||
                                etAge.getText().toString().isEmpty()) {

                            Toast.makeText(getActivity(), R.string.wrong_statement, Toast.LENGTH_SHORT).show();
                        } else {
                            db.updateContact(new Contact(getArguments().getInt(ID_OF_EDITABLE_ITEM),
                                     etName.getText().toString(),
                                     etNumber.getText().toString(),
                                     etAge.getText().toString(),
                                    spinner.getSelectedItem().toString()));

                            transaction.replace(R.id.mainActivity_list_place, addContactFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();
                        }

                    }
                });

                break;

        }

    }

    void addNewContact() {

        db.addContact(new Contact(
                etName.getText().toString(),
                etNumber.getText().toString(),
                etAge.getText().toString(),
                spinner.getSelectedItem().toString()
        ));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
