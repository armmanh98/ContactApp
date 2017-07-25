package com.example.user.contactsapp.Fragments;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.DataBasa.DatabaseHandler;
import com.example.user.contactsapp.Dialogs.DialogFragmentImageUrl;
import com.example.user.contactsapp.Dialogs.MyDialogFragment;
import com.example.user.contactsapp.Image.Image;
import com.example.user.contactsapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private TextView countOfImagesTv;

    private DatabaseHandler db;

    public static final String FROM_WHERE_ADD_OR_EDT_FRAGMENT_KEY = "from  where  replace or add AddOrEditFr";
    public static final String NAME_OF_EDITABLE_ITEM = "name edit cont";
    public static final String NUMBER_OF_EDITABLE_ITEM = "number edit cont";
    public static final String AGE_OF_EDITABLE_ITEM = "age edit cont";
    public static final String GENDER_OF_EDITABLE_ITEM = "gender edit cont";
    public static final String ID_OF_EDITABLE_ITEM = "Id edit cont";
    public static final String TAG_FOR_MY_DIALOG_FRAGMENT = "my Dialog Fragment";
    static final int REQUEST_TAKE_PHOTO = 1;
    String imageDescription;
    MyDialogFragment fragmentPhoto;
    DialogFragmentImageUrl fragmentPhotoUrl;
    File imageFile;
    List<Image> listOfAddedImages;
    Uri mUri;

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
        inflater.inflate(R.menu.choice_photo, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.choice_photo, menu);


    }

    public Runnable downloadImageRunnable(final String url) {
        final String[] myUrl = {url};


        return new Runnable() {
            @Override
            public void run() {


                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myUrl[0]));
//                    request.setDescription("Some descrition");
//                    request.setTitle("Some title");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, imageFileName + ".jpg");
                DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + imageFileName + ".jpg");

//

            }
        };
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getActivity(), R.string.image_download_completed, Toast.LENGTH_SHORT).show();
            mUri = Uri.fromFile(imageFile);
            imgPhoto.setImageURI(mUri);
            listOfAddedImages.add(new Image(mUri.toString(), imageDescription));
            countOfImagesTv.setText(listOfAddedImages.size() + "/5");

        }
    };


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {

            case "Take photo":
                dispatchTakePictureIntent();
                break;
            case "Set Url":
                fragmentPhotoUrl = new DialogFragmentImageUrl.AlertFragmentSetImageDescriptionBuilder()
                        .clickListenerPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Thread thread = new Thread(downloadImageRunnable(fragmentPhotoUrl.getSpinner().getSelectedItem().toString()));
                                thread.start();
                                fragmentPhotoUrl.dismiss();


                            }

                        })
                        .clickListenerNegativeButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragmentPhotoUrl.dismiss();

                            }
                        })
                        .myArrayRes(R.array.images_url_array)
                        .build();
                fragmentPhotoUrl.show(getFragmentManager(), "TAG");
                break;
            case "Gallery":
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 5);
                break;


        }

        Toast.makeText(getActivity(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        return true;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            listOfAddedImages.add(new Image(mUri.toString(), imageDescription));
            countOfImagesTv.setText(listOfAddedImages.size() + "/5");

        }

        if (requestCode == 5 && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1) {
                return;
            }

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            if (columnIndex < 0)
                return;

            String picturePath = cursor.getString(columnIndex);

            cursor.close();


            listOfAddedImages.add(new Image(picturePath, imageDescription));
            countOfImagesTv.setText(listOfAddedImages.size() + "/5");
            imgPhoto.setImageURI(Uri.parse(picturePath));
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
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(onComplete);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listOfAddedImages = new ArrayList<>();

        db = new DatabaseHandler(getActivity());


        imgPhoto = view.findViewById(R.id.fragment_edit_add_photo_img);
        etName = view.findViewById(R.id.fragmentEdit_name);
        etNumber = view.findViewById(R.id.fragmentEdit_number);
        etAge = view.findViewById(R.id.fragmentEdit_age);
        btnSubmit = view.findViewById(R.id.fragmentEdit_btn_submit);
        spinner = view.findViewById(R.id.spinner_gender);
        countOfImagesTv = view.findViewById(R.id.fragmentEdit_count_of_images);

        registerForContextMenu(imgPhoto);

        imgPhoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {
                if (listOfAddedImages.size() <= 4) {
                    fragmentPhoto = new MyDialogFragment.AlertFragmentSetImageDescriptionBuilder()
                            .clickListenerPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View view1) {
                                    if (!fragmentPhoto.getAddDescriptionEdt().getText().toString().isEmpty()) {
                                        fragmentPhoto.dismiss();
                                        getActivity().openContextMenu(view);
                                        imageDescription = fragmentPhoto.getAddDescriptionEdt().getText().toString();
                                    } else
                                        Toast.makeText(getActivity(), R.string.image_description_error, Toast.LENGTH_SHORT).show();

                                    Log.i("TAG", "clicked positive button");
                                }
                            })
                            .clickListenerNegativeButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.i("TAG", "clicked negative button");
                                    fragmentPhoto.dismiss();
                                }
                            })
                            .title("Image Description")
                            .build();
                    fragmentPhoto.setTargetFragment(getParentFragment(), 5);
                    fragmentPhoto.show(getFragmentManager(), TAG_FOR_MY_DIALOG_FRAGMENT);
                }else {
                    Toast.makeText(getActivity(), R.string.max_size_image_error,Toast.LENGTH_SHORT).show();
                }
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

                if (!etNumber.getText().toString().matches(regEx) && !etNumber.getText().toString().matches(regEx2) && !etNumber.getText().toString().matches(regEx3)) {
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
                            long i = db.addContact(new Contact(
                                    etName.getText().toString(),
                                    etNumber.getText().toString(),
                                    etAge.getText().toString(),
                                    spinner.getSelectedItem().toString()
                            ));
                            for (Image a : listOfAddedImages) {
                                a.setOwnerId(i);
                                db.addImage(a);
                            }


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
