package com.example.emotionalsupportapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ConnectActivity extends AppCompatActivity{

    private String userID;

    public interface VolleyCallback {
        void onSuccessResponse(String username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

//        String userID = getIntent().getStringExtra("EXTRA_USER_ID");
        final String userID = "1";
        getUsername((new VolleyCallback() {
            @Override
            public void onSuccessResponse(String username) {
                TabLayout tabLayout = findViewById(R.id.tab_layout);
                ViewPager viewPager = findViewById(R.id.view_pager);
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_USER_ID", userID);
                bundle.putString("EXTRA_USER_NAME", username);

                ChatsFragment chatsFragment = new ChatsFragment();
                chatsFragment.setArguments(bundle);
                UsersFragment usersFragment = new UsersFragment();
                usersFragment.setArguments(bundle);

                viewPagerAdapter.addFragment(chatsFragment,"Chats");
                viewPagerAdapter.addFragment(usersFragment,"Users");

                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        }));
    }


    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }
    }

    private void getUsername(final VolleyCallback callback){
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getUsername.php/?user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject re = response.getJSONObject("response");
                    String username = re.getString("FirstName") + " " + re.getString("LastName");
                    callback.onSuccessResponse(username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequest);
    }

}