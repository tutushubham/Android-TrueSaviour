package com.vaidik.truesaviour.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vaidik.truesaviour.BuildConfig;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.models.Wallpaper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.WallpaperViewHolder> {
    private Context mCtx;
    private List<Wallpaper> wallpaperList;


    public WallpapersAdapter(Context mCtx, List<Wallpaper> wallpaperList) {
        this.mCtx = mCtx;
        this.wallpaperList = wallpaperList;

    }

    @Override
    public WallpaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_wallpapers, parent, false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallpaperViewHolder holder, int position) {
        Wallpaper w = wallpaperList.get(position);
        holder.textView.setText(w.title);
        Glide.with(mCtx)
                .load(w.url)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    class WallpaperViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        TextView textView;
        ImageView imageView;

        CheckBox checkBoxFav;
        ImageButton buttonShare, buttonDownload;


        public WallpaperViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_title);
            imageView = itemView.findViewById(R.id.image_view);

            checkBoxFav = itemView.findViewById(R.id.checkbox_favourite);
            buttonShare = itemView.findViewById(R.id.button_share);
            buttonDownload = itemView.findViewById(R.id.button_download);

            checkBoxFav.setOnCheckedChangeListener(this);
            buttonShare.setOnClickListener(this);
            buttonDownload.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button_share:

                    shareWallpaper(wallpaperList.get(getAdapterPosition()));

                    break;
                case R.id.button_download:

                    downloadWallpaper(wallpaperList.get(getAdapterPosition()));


                    break;

            }

        }

        private void shareWallpaper(Wallpaper w) {

            Glide.with(mCtx)
                    .asBitmap()
                    .load(w.url)
                    .into(new SimpleTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {


                                  Intent intent = new Intent(Intent.ACTION_SEND);
                                  intent.setType("image/*");
                                  intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));

                                  mCtx.startActivity(Intent.createChooser(intent, "Wallpapers Hub"));
                              }
                          }
                    );
        }

        private Uri getLocalBitmapUri(Bitmap bmp) {
            Uri bmpUri = null;
            try {
                File file = new File(mCtx.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "wallpaper_hub_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = FileProvider.getUriForFile(mCtx, BuildConfig.APPLICATION_ID + ".provider", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }


        private void downloadWallpaper(final Wallpaper wallpaper) {


            Glide.with(mCtx)
                    .asBitmap()
                    .load(wallpaper.url)
                    .into(new SimpleTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {


                                  Intent intent = new Intent(Intent.ACTION_VIEW);
                                  //Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                                  Uri uri = saveWallpaperAndGetUri(resource, wallpaper.id);

                                  if (uri != null) {
                                      intent.setDataAndType(uri, "image/*");
                                      mCtx.startActivity(Intent.createChooser(intent, "Wallpapers Hub"));

                                      /*intent.addCategory(Intent.CATEGORY_DEFAULT);
                                      intent.setDataAndType(uri, "image/jpeg");
                                      intent.putExtra("mimeType", "image/jpeg");
                                      mCtx.startActivity(Intent.createChooser(intent, "Set as:"));*/
                                  }
                              }
                          }
                    );
        }


        private Uri saveWallpaperAndGetUri(Bitmap bitmap, String id) {
            if (ContextCompat.checkSelfPermission(mCtx, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat
                        .shouldShowRequestPermissionRationale((Activity) mCtx, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                    Uri uri = Uri.fromParts("package", mCtx.getPackageName(), null);
                    intent.setData(uri);

                    mCtx.startActivity(intent);

                } else {
                    ActivityCompat.requestPermissions((Activity) mCtx, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
                return null;
            }

            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/wallpapers_hubs");
            folder.mkdirs();

            File file = new File(folder, id + ".jpg");
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                return FileProvider.getUriForFile(mCtx, BuildConfig.APPLICATION_ID + ".provider", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(mCtx, "Please login first...", Toast.LENGTH_LONG).show();
                compoundButton.setChecked(false);
                return;
            }


            int position = getAdapterPosition();
            Wallpaper w = wallpaperList.get(position);


            DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourites")
                    .child(w.category);

            if (b) {
                dbFavs.child(w.id).setValue(w);
            } else {
                dbFavs.child(w.id).setValue(null);
            }
        }
    }
}
