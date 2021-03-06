package com.example.hp.stickpick;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.stickpick.bean.ListBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShowAllRecordsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listView;
    int i;
    int position;
    ArrayList<String> listName;
    ArrayList<String> listMobile;
    ArrayList<String> listEmail;
    ArrayList<String> listPassword;
    ArrayList<String> listImage;
    ArrayList<String> listCourse;
    ArrayList<String> listSemester;
    List<ListBean> listBean;
    private ProgressDialog progressDialog;
    String cs;

    boolean expendListViewBool = true;
    List<ListBean> listCourseSemester;
    List<ListBean> listTeacher;

    public List<ListBean> getList() {
        return listBean;
    }

    public List<ListBean> getListCourseSem() {
        return listCourseSemester;
    }

    public int getPosition() {
        return position;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_record);

        Intent i = getIntent();

        if (HomeActivity.intShowRecordTitle == 0) {
            getSupportActionBar().setTitle("List of all Student");
        } else if (HomeActivity.intShowRecordTitle == 1) {
            getSupportActionBar().setTitle("M.C.A 1st");
        } else if (HomeActivity.intShowRecordTitle == 2) {
            getSupportActionBar().setTitle("M.C.A 2st");
        } else if (HomeActivity.intShowRecordTitle == 3) {
            getSupportActionBar().setTitle("M.C.A 3rd");
        } else if (HomeActivity.intShowRecordTitle == 4) {
            getSupportActionBar().setTitle("M.C.A 4th");
        } else if (HomeActivity.intShowRecordTitle == 5) {
            getSupportActionBar().setTitle("M.C.A 5th");
        } else if (HomeActivity.intShowRecordTitle == 6) {
            getSupportActionBar().setTitle("M.C.A 6th");
        } else if (HomeActivity.intShowRecordTitle == 7) {
            getSupportActionBar().setTitle("B.C.A 1st");
        } else if (HomeActivity.intShowRecordTitle == 8) {
            getSupportActionBar().setTitle("B.C.A 2st");
        } else if (HomeActivity.intShowRecordTitle == 9) {
            getSupportActionBar().setTitle("B.C.A 3rd");
        } else if (HomeActivity.intShowRecordTitle == 10) {
            getSupportActionBar().setTitle("B.C.A 4th");
        } else if (HomeActivity.intShowRecordTitle == 11) {
            getSupportActionBar().setTitle("B.C.A 5th");
        } else if (HomeActivity.intShowRecordTitle == 12) {
            getSupportActionBar().setTitle("B.C.A 6th");
        } else if (HomeActivity.intShowRecordTitle == 13) {
            getSupportActionBar().setTitle("List Of Teachers");
        }
       /* if (Networking.isConnected(ShowAllRecordsActivity.this)) {*/
            listView = (ListView) findViewById(R.id.listView);
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Shaking hands with server");
            progressDialog.show();
            getAllRecord();

        /*} else {
            Toast.makeText(ShowAllRecordsActivity.this, "Please check you internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Networking.isConnected(ShowAllRecordsActivity.this)) {
            progressDialog.dismiss();
        }
    }

    void getAllRecord() {
//use addListenerForSingleValueEvent for every hit

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(ParameterConstants.USERS).child(ParameterConstants.PROFILE);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        listName = new ArrayList<>();
        listMobile = new ArrayList<>();
        listEmail = new ArrayList<>();
        listPassword = new ArrayList<>();
        listImage = new ArrayList<>();
        listCourse = new ArrayList<>();
        listSemester = new ArrayList<>();

        listCourseSemester = new ArrayList<>();
        listTeacher = new ArrayList<>();


        //iterate through each user, ignoring their UID
        if (users == null) {
            Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            finish();
        } else {
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                //Get user map
                Map map = (Map) entry.getValue();
                Set<String> keyset = map.keySet();
                for (String key : keyset) {

                    if (key.equals("name")) {
                        listName.add(map.get(key).toString().toUpperCase());
                        //showAllText.append("" + userName + "\n");
                    }
                    if (key.equals("mobile")) {

                        listMobile.add(map.get(key).toString());

                    }
                    if (key.equals("email")) {

                        listEmail.add(map.get(key).toString());

                    }
                    if (key.equals("password")) {

                        listPassword.add(map.get(key).toString());

                    }
                    if (key.equals("myImage")) {
                        listImage.add(map.get(key).toString());

                    }
                    if (key.equals("course")) {
                        listCourse.add(map.get(key).toString());

                    }
                    if (key.equals("semester")) {
                        listSemester.add(map.get(key).toString());

                    }

                }
            }
            progressDialog.dismiss();
        }

        listBean = new ArrayList();
        for (int i = 0; i < listName.size(); i++) {
                ListBean bean = new ListBean();
                bean.setName(listName.get(i));
                bean.setEmail(listEmail.get(i));
                bean.setMobile(listMobile.get(i));
                bean.setPassword(listPassword.get(i));
                bean.setImage(listImage.get(i));
                bean.setCourse(listCourse.get(i));
                bean.setSemester(listSemester.get(i));
                listBean.add(bean);
        }
        Collections.sort(listBean);

        if (HomeActivity.boolCourseSem) {
            listCourseSemester.clear();
            cs = getIntent().getStringExtra("cs");
            String[] strCs = cs.split(" ");
            for (int a = 0; a < listBean.size(); a++) {
                if (listBean.get(a).getCourse().equals(strCs[0]) && listBean.get(a).getSemester().equals(strCs[1])) {
                    listCourseSemester.add(listBean.get(a));
                }
            }

            MyAdapter myAdapter = new MyAdapter(ShowAllRecordsActivity.this, listCourseSemester);
            listView.setAdapter(myAdapter);
        } else {
            for (int a = 0; a < listBean.size(); a++) {
                if (listBean.get(a).getCourse().equals("TEACHER")) {
                    listTeacher.add(listBean.get(a));
                }
            }
            listBean.removeAll(listTeacher);
            MyAdapter myAdapter = new MyAdapter(ShowAllRecordsActivity.this, listBean);
            listView.setAdapter(myAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!HomeActivity.boolCourseSem) {
                    ShowAllRecordsActivity.this.i = i;
                    final CharSequence[] items = {"Call", "Sms", "Email", "Remove"};
                    ShowAllRecordsActivity.this.i = i;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowAllRecordsActivity.this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listBean.get(ShowAllRecordsActivity.this.i).getMobile().toString()));
                                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (ActivityCompat.checkSelfPermission(ShowAllRecordsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                ShowAllRecordsActivity.this.startActivity(callIntent);

                            } else if (item == 1) {

                                final Dialog openDialog = new Dialog(ShowAllRecordsActivity.this);
                                openDialog.setContentView(R.layout.message_dailog);
                                openDialog.setTitle("Enter message");
                                final EditText msgEdt = (EditText) openDialog.findViewById(R.id.msgEdt);
                                Button msgBtn = (Button) openDialog.findViewById(R.id.msgBtn);
                                final String mobileNumber = listBean.get(ShowAllRecordsActivity.this.i).getMobile().toString();
                                msgBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(mobileNumber, null, "B.U.Jhansi=" + msgEdt.getText().toString(), null, null);
                                        openDialog.dismiss();
                                    }
                                });
                                openDialog.show();


                            } else if (item == 1) {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", listBean.get(position).getEmail().toString(), null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion/Query");
                                startActivity(Intent.createChooser(emailIntent, null));
                            } else if (item == 2) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ShowAllRecordsActivity.this);
                                alertDialog.setMessage("Are you sure?");
                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        UserBean userBean = ReferenceWrapper.getRefrenceWrapper(ShowAllRecordsActivity.this).getUserBean();
                                        if (userBean.getCourse().equals("TEACHER")){
                                            ProgressDialog progressDialog2 = new ProgressDialog(ShowAllRecordsActivity.this);
                                            progressDialog2.setMessage("Removing...");
                                            progressDialog2.show();
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            databaseReference = database.getReference(ParameterConstants.USERS);
                                            databaseReference.child(ParameterConstants.PROFILE).child(listBean.get(ShowAllRecordsActivity.this.i).getMobile()).removeValue();
                                            Intent intent = new Intent(ShowAllRecordsActivity.this, ShowAllRecordsActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);
                                            finish();
                                            progressDialog2.dismiss();
                                        }
                                       else {
                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShowAllRecordsActivity.this);
                                            dialogBuilder.setMessage("Only teacher can delete the record");
                                            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            AlertDialog b = dialogBuilder.create();
                                            b.show();
                                        }

                                    }
                                });
                                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }
                        }

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    {
                        ShowAllRecordsActivity.this.i = i;
                        final CharSequence[] items = {"Call", "Email", "Remove"};
                        ShowAllRecordsActivity.this.i = i;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowAllRecordsActivity.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listCourseSemester.get(ShowAllRecordsActivity.this.i).getMobile().toString()));
                                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    if (ActivityCompat.checkSelfPermission(ShowAllRecordsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    ShowAllRecordsActivity.this.startActivity(callIntent);

                                } /*else if (item == 1) {

                                    final Dialog openDialog = new Dialog(ShowAllRecordsActivity.this);
                                    openDialog.setContentView(R.layout.message_dailog);
                                    openDialog.setTitle("Enter message");
                                    final EditText msgEdt = (EditText) openDialog.findViewById(R.id.msgEdt);
                                    Button msgBtn = (Button) openDialog.findViewById(R.id.msgBtn);
                                    final String mobileNumber = listCourseSemester.get(ShowAllRecordsActivity.this.i).getMobile().toString();
                                    msgBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(mobileNumber, null, "B.U.Jhnsi=" + msgEdt.getText().toString(), null, null);
                                            openDialog.dismiss();
                                        }
                                    });
                                    openDialog.show();


                                } */else if (item == 1) {
                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                            "mailto", listCourseSemester.get(ShowAllRecordsActivity.this.i).getEmail().toString(), null));
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion/Query");
                                    startActivity(Intent.createChooser(emailIntent, null));
                                } else if (item == 2) {
                                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ShowAllRecordsActivity.this);
                                    alertDialog.setMessage("Are you sure?");
                                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            UserBean userBean = ReferenceWrapper.getRefrenceWrapper(ShowAllRecordsActivity.this).getUserBean();
                                            if (userBean.getCourse().equals("TEACHER")){
                                                ProgressDialog progressDialog2 = new ProgressDialog(ShowAllRecordsActivity.this);
                                                progressDialog2.setMessage("Removing...");
                                                progressDialog2.show();
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                databaseReference = database.getReference(ParameterConstants.USERS);

                                                databaseReference.child(ParameterConstants.PROFILE).child(listCourseSemester.get(ShowAllRecordsActivity.this.i).getMobile()).removeValue();
                                                Intent intent = new Intent(ShowAllRecordsActivity.this, ShowAllRecordsActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                intent.putExtra("cs", cs);
                                                startActivity(intent);
                                                finish();
                                                progressDialog2.dismiss();
                                            }
                                            else {
                                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShowAllRecordsActivity.this);
                                                dialogBuilder.setMessage("Only teacher can delete the record");
                                                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                                AlertDialog b = dialogBuilder.create();
                                                b.show();
                                            }

                                        }
                                    });
                                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                }
                            }

                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity input AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_deleteAllRecord) {

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
            alertDialog.setTitle("Are you sure to clear whole record?");
            alertDialog.setMessage("Please think before doing...");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference(ParameterConstants.USERS);
                    databaseReference.child(ParameterConstants.PROFILE).setValue(null);
                    //NoticeBoardActivity.clearNoticeBoardContent();
                    databaseReference = database.getReference(ParameterConstants.NOTICE);
                    databaseReference.setValue(null);
                    finish();

                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


    class MyAdapter extends BaseAdapter {

        Context context;
        TextView name, mobile, email, password, courseSem;
        ImageView image;
        TextView textViewimageDetail;
        LinearLayout linearEmail, linearPass;

        List<ListBean> listBean;


        MyAdapter(Context context, List listBean) {
            this.context = context;

            this.listBean = listBean;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {


            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.userlistitem, null);
            // well set up the ViewHolder

            name = (TextView) convertView.findViewById(R.id.nameUserList);
            mobile = (TextView) convertView.findViewById(R.id.mobileUserList);
            email = (TextView) convertView.findViewById(R.id.emailUserList);
            password = (TextView) convertView.findViewById(R.id.passwordUserList);
            courseSem = (TextView) convertView.findViewById(R.id.courseSemUserList);

            linearEmail = (LinearLayout) convertView.findViewById(R.id.linearEmail);
            linearPass = (LinearLayout) convertView.findViewById(R.id.linearPass);

            image = (ImageView) convertView.findViewById(R.id.imageUserList);
            textViewimageDetail = (TextView) convertView.findViewById(R.id.textViewimageDetail);

            name.setText(listBean.get(position).getName());
            mobile.setText(listBean.get(position).getMobile());
            email.setText(listBean.get(position).getEmail());
            password.setText(listBean.get(position).getPassword());
            courseSem.setText(listBean.get(position).getCourse() + " " + listBean.get(position).getSemester());

            if (expendListViewBool) {
                linearEmail.setVisibility(View.VISIBLE);
                //linearPass.setVisibility(View.VISIBLE);
            } else {
                linearEmail.setVisibility(View.GONE);
                linearPass.setVisibility(View.GONE);
            }

            if (listBean.get(position).getImage().length() > 0) {
                image.setImageBitmap(Conversion.BitMapfromString(listBean.get(position).getImage().toString()));
            } else {
                image.setVisibility(View.GONE);
                textViewimageDetail.setText("" + listBean.get(position).getName().charAt(0));
                textViewimageDetail.setVisibility(View.VISIBLE);
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowAllRecordsActivity.this.position = position;
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    android.support.v4.app.DialogFragment dFragment = new UserPicFragment();
                    dFragment.show(fm, "Dialog Fragment");
                }
            });
            textViewimageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowAllRecordsActivity.this.position = position;
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    android.support.v4.app.DialogFragment dFragment = new UserPicFragment();
                    dFragment.show(fm, "Dialog Fragment");
                }
            });

            return convertView;
        }

        @Override
        public int getCount() {
            return listBean.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

    }

    private void sendMessageToWhatsAppContact(String number) {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode("hi", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                getApplicationContext().startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
