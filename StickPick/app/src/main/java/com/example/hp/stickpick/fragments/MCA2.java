package com.example.hp.stickpick.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.stickpick.Conversion;
import com.example.hp.stickpick.ParameterConstants;
import com.example.hp.stickpick.R;
import com.example.hp.stickpick.bean.ListBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MCA2 extends Fragment {


    public MCA2() {
        // Required empty public constructor
    }

    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mca2, container, false);
        getAllRecord();
        listView=view.findViewById(R.id.AttendanceMca2);
        return view;
    }


    void getAllRecord() {
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


    private void collectPhoneNumbers(Map<String, Object> users){

        List<String> listName = new ArrayList<>();
        List<String> listMobile = new ArrayList<>();
        List listEmail = new ArrayList<>();
        List listPassword = new ArrayList<>();
        List listImage = new ArrayList<>();
        List listCourse = new ArrayList<>();
        List listSemester = new ArrayList<>();
        List listCourseSemester = new ArrayList<>();

        //iterate through each user, ignoring their UID
        if (users == null) {
            Toast.makeText(getContext(), "No record found", Toast.LENGTH_SHORT).show();

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
        }

        List<ListBean> listBean = new ArrayList();
        for (int i = 0; i < listName.size(); i++) {
            ListBean bean = new ListBean();
            bean.setName(listName.get(i).toString());
            bean.setEmail(listEmail.get(i).toString());
            bean.setMobile(listMobile.get(i).toString());
            bean.setPassword(listPassword.get(i).toString());
            bean.setImage(listImage.get(i).toString());
            bean.setCourse(listCourse.get(i).toString());
            bean.setSemester(listSemester.get(i).toString());
            listBean.add(bean);
        }
        Collections.sort(listBean);
        listCourseSemester.clear();
        for (int i = 0; i < listBean.size(); i++) {
            if (listBean.get(i).getCourse().equals("MCA") && listBean.get(i).getSemester().equals("2nd")) {
                listCourseSemester.add(listBean.get(i));
            }
        }
        MyAdapter myAdapter = new MyAdapter(getActivity(), listCourseSemester);
        listView.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter {

        List<ListBean> listBean;
        Context context;
        TextView name, mobile, email, password, courseSem,textViewImageDetail;
        ImageView image;
        MyAdapter(Context context, List listBean) {
            this.context = context;
            this.listBean = listBean;
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

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_fragment, null);

            name = (TextView) convertView.findViewById(R.id.nameUserList);
            mobile = (TextView) convertView.findViewById(R.id.mobileUserList);
            email = (TextView) convertView.findViewById(R.id.emailUserList);
            password = (TextView) convertView.findViewById(R.id.passwordUserList);
            courseSem = (TextView) convertView.findViewById(R.id.courseSemUserList);

            image = (ImageView) convertView.findViewById(R.id.imageUserList);
            textViewImageDetail = (TextView) convertView.findViewById(R.id.textViewimageDetail);

            name.setText(listBean.get(position).getName());
            mobile.setText(listBean.get(position).getMobile());
            email.setText(listBean.get(position).getEmail());
            password.setText(listBean.get(position).getPassword());
            courseSem.setText(listBean.get(position).getCourse() + " " + listBean.get(position).getSemester());

           /* if (listBean.get(position).getImage().length() > 0) {
                image.setImageBitmap(Conversion.BitMapfromString(listBean.get(position).getImage().toString()));
            } else {*/
                image.setVisibility(View.GONE);
                textViewImageDetail.setText("" + (position+1));
                textViewImageDetail.setVisibility(View.VISIBLE);
            /*}*/

            return convertView;
        }
    }


}
