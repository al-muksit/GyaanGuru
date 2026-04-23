package com.gyaanguru.Account_details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.gyaanguru.Activities.MainActivity;
import com.gyaanguru.R;
import com.gyaanguru.databinding.ActivityProfileBinding;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    Uri imageUri;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.top_background));

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Toasty.error(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference();

        firebaseStorage = FirebaseStorage.getInstance("gs://gyaanguru-fd16e.appspot.com");
        storageReference = firebaseStorage.getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("email")){
            binding.userName.setText(sharedPreferences.getString("username", ""));
            binding.userEmail.setText(sharedPreferences.getString("email", ""));
        }
        // Try to load profile image from SharedPreferences first
        String storedProfileImageUrl = sharedPreferences.getString("profileImageUrl", null);
        if (storedProfileImageUrl != null) {
            loadProfileImage(storedProfileImageUrl);
        }

        // Read the userEmail from the database
        userRef = firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = sharedPreferences.edit(); // Initialize editor

                String userName = dataSnapshot.child("userName").getValue(String.class);
                binding.userName.setText(userName != null ? userName : "Name not found");
                editor.putString("username", userName); // Store username

                String userEmail = dataSnapshot.child("email").getValue(String.class);
                binding.userEmail.setText(userEmail != null ? userEmail : "Email not found");
                editor.putString("email", userEmail); // Store email

                String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                if (profileImageUrl != null) {
                    loadProfileImage(profileImageUrl);  // Load the image into the CircleImageView using Glide or Picasso
                    editor.putString("profileImageUrl", profileImageUrl);  // Update SharedPreferences with the latest profile image URL
                }
                editor.apply(); // Apply changes asynchronously
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseError", "Error reading user data: " + error.getMessage());
            }
        });

        binding.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {firebaseAuth.signOut();
                                Intent intent = new Intent(ProfileActivity.this, SigninActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select an image"),1);
            }
        });

    }

    private void loadProfileImage(String imageUrl) {
        // Using Glide:
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.profileImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                // MediaStore.Images.Media.getBitmap is deprecated, but we'll use it for now as it's already here
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.profileImage.setImageBitmap(imageBitmap);
                upload(imageUri);
            } catch (IOException e) {
                Log.e("ProfileActivity", "Error loading bitmap: " + e.getMessage());
                Toasty.error(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toasty.info(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void upload(Uri imageUri) {
        if (imageUri != null && firebaseAuth.getCurrentUser() != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Adding an extension and metadata can sometimes help with 412 errors
            String fileName = UUID.randomUUID().toString() + ".jpg";
            StorageReference ref = storageReference.child("Users/" + firebaseAuth.getCurrentUser().getUid() + "/Image/" + fileName);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpeg")
                    .build();

            ref.putFile(imageUri, metadata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String downmainImageuri = task.getResult().toString();
                                        progressDialog.dismiss();

                                        // Store the download URL in the database
                                        userRef.child("profileImageUrl").setValue(downmainImageuri)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void Void) {
                                                        Toasty.success(ProfileActivity.this, "Profile picture saved", Toast.LENGTH_SHORT).show();
                                                        loadProfileImage(downmainImageuri);
                                                        
                                                        // Also update SharedPreferences
                                                        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                                                        sharedPreferences.edit().putString("profileImageUrl", downmainImageuri).apply();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("ProfileActivity", "Database error: " + e.getMessage());
                                                        Toasty.error(ProfileActivity.this, "Failed to save image path: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    } else {
                                        progressDialog.dismiss();
                                        Log.e("ProfileActivity", "Failed to get download URL");
                                        Toasty.error(getApplicationContext(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.e("ProfileActivity", "Upload failed: " + e.getMessage(), e);
                            
                            String errorMessage = e.getMessage();
                            if (e instanceof StorageException) {
                                StorageException se = (StorageException) e;
                                int errorCode = se.getErrorCode();
                                int httpResultCode = se.getHttpResultCode();
                                Log.e("ProfileActivity", "Storage Error Code: " + errorCode);
                                Log.e("ProfileActivity", "HTTP Result Code: " + httpResultCode);
                                
                                if (errorCode == StorageException.ERROR_NOT_AUTHORIZED) {
                                    errorMessage = "Not authorized. Please check your Storage Rules.";
                                } else if (errorCode == StorageException.ERROR_QUOTA_EXCEEDED) {
                                    errorMessage = "Storage quota exceeded.";
                                } else if (httpResultCode == 412) {
                                    errorMessage = "Precondition failed (412). Check if App Check or Storage is enabled in Console.";
                                }
                            }
                            
                            Toasty.error(ProfileActivity.this, "Upload failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finish();
    }
}