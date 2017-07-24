package com.example.user.contactsapp.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.contactsapp.Image.Image;
import com.example.user.contactsapp.R;

import java.io.IOException;

/**
 * Created by User on 7/24/2017.
 */

public class ImageFragment extends Fragment {

    public static final String PATH_OF_CONTACT_IMAGE = "path";
    public static final String DESCRIPTION_OF_CONTACT_IMAGE = "description";


    public static ImageFragment newInstance(Image image) {
        ImageFragment fragmentFirst = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(PATH_OF_CONTACT_IMAGE, image.getPath());
        args.putString(DESCRIPTION_OF_CONTACT_IMAGE, image.getDescription());
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView contactImage =  view.findViewById(R.id.img_contact_image);
        TextView imageDescription =  view.findViewById(R.id.tv_contact_description);
        imageDescription.setText(getArguments().getString(DESCRIPTION_OF_CONTACT_IMAGE));


        Bitmap takenImage = BitmapFactory.decodeFile(Uri.parse(getArguments().getString(PATH_OF_CONTACT_IMAGE)).getPath());


        ExifInterface ei = null;
        try {
            ei = new ExifInterface(Uri.parse(getArguments().getString(PATH_OF_CONTACT_IMAGE)).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(takenImage, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(takenImage, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(takenImage, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:

            default:
                break;
        }

        contactImage.setImageBitmap(rotatedBitmap);


        return view;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
