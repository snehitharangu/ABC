package com.example.abc;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class galleryfragment extends Fragment implements View.OnClickListener{
    public View view;
    public Bitmap bitmap;
    public void getphoto(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults.length>0){
                getphoto();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);}
        else{
            getphoto();
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_gallery,container,false);
        Button btn1=(Button)view.findViewById(R.id.back);
        Button btn2=(Button)view.findViewById(R.id.save);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                if(bitmap!=null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                }
                byte[] byteArray=byteArrayOutputStream.toByteArray();
                ParseFile file=new ParseFile("image",byteArray);
                ParseObject parseObject=new ParseObject("image");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("image",file);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"not saved",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;
        if (data !=null){
            selectedImage = data.getData();
        }
        else{
            selectedImage=null;
        }
        if(selectedImage!=null){
            if(requestCode==1&& resultCode==RESULT_OK&&data!=null)
            {
                try{
                    bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);
                    ImageView imageView=(ImageView)view.findViewById(R.id.imageView3);
                    imageView.setImageBitmap(bitmap);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }}
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.save:
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] byteArray=byteArrayOutputStream.toByteArray();
                ParseFile file=new ParseFile("image",byteArray);
                ParseObject parseObject=new ParseObject("image");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("image",file);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"not saved",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }}









}
