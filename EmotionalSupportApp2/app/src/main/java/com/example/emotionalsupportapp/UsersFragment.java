package com.example.emotionalsupportapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment implements FriendDialog.FriendDialogListener, DeleteFriendDialog.FriendDialogListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<Friend> friends;
    private String userID;
    private String user_name;

    private Button addFriend;
    private Button deleteFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userID = getArguments().getString("EXTRA_USER_ID");
        user_name = getArguments().getString("EXTRA_USER_NAME");
        friends = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeSql();

        addFriend = view.findViewById(R.id.add_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });
        deleteFriend = view.findViewById(R.id.delete_friend);
        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteDialog();
            }
        });
        return view;
    }

    private void initializeSql() {
        friends.clear();
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getFriends.php/?user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray re = response.getJSONArray("response");
                    for (int i = 0; i < re.length(); i++) {
                        JSONObject jsonobject = re.getJSONObject(i);
                        String userId = jsonobject.getString("userId");
                        String userName = jsonobject.getString("userName");
                        String friendId = jsonobject.getString("friendId");
                        String friendName = jsonobject.getString("friendName");
                        friends.add(new Friend(userId, userName, friendId, friendName));
                    }
                    userAdapter = new UserAdapter(friends, getContext());
                    recyclerView.setAdapter(userAdapter);
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


    public void openAddDialog(){
        FriendDialog friendDialog = new FriendDialog();
        friendDialog.setTargetFragment(UsersFragment.this, 1);
        friendDialog.show(getFragmentManager(), "Friend Dialog");
    }

    @Override
    public void applyTexts(String friendId) {
        if(friendId.equals("") || friendId.equals("0")){
            Toast.makeText(getActivity(), "Please enter an valid friend id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (friendId.equals(userID)){
            Toast.makeText(getActivity(), "You cannot add yourself as a friend", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i = 0; i < friends.size(); ++i){
            if(friends.get(i).getFriendId().equals(friendId)){
                Toast.makeText(getActivity(), "You have added the friend", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        getFriendName(friendId);
    }

    private void getFriendName(final String friendId){
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getUsername.php/?user_id=" + friendId;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject re = response.getJSONObject("response");
                    String friendName = re.getString("FirstName") + "%20" + re.getString("LastName");
                    addFriendToDatabase(friendId, friendName);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Please enter an valid friend id", Toast.LENGTH_SHORT).show();
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

    private void addFriendToDatabase(String friendId, String friendName) {
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/saveFriend.php/?"
                + "user_id=" + userID
                + "&user_name=" + user_name
                + "&friend_id=" + friendId
                + "&friend_name=" + friendName;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String re = response.getString("response");
                    if (re.equals("ok")){
                        Toast.makeText(getActivity(), "Add Successfully", Toast.LENGTH_SHORT).show();
                        initializeSql();
                    } else{
                        Toast.makeText(getActivity(), "Add Failed", Toast.LENGTH_SHORT).show();
                    }
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

    public void openDeleteDialog(){
        DeleteFriendDialog deleteFriendDialog = new DeleteFriendDialog();
        deleteFriendDialog.setTargetFragment(UsersFragment.this, 1);
        deleteFriendDialog.show(getFragmentManager(), "DeleteFriendDialog");
    }

    @Override
    public void deleteFriendId(String friendId) {
        if(friendId.equals("") || friendId.equals(userID)){
            Toast.makeText(getActivity(), "Please enter an valid friend id", Toast.LENGTH_SHORT).show();
        }
        boolean check = false;
        for(int i = 0; i < friends.size(); ++i){
            if(friends.get(i).getFriendId().equals(friendId)){
                check = true;
                break;
            }
        }
        if(check){
            Log.d("Test", friendId);
        }else{
            Toast.makeText(getActivity(), "Please enter an valid friend id", Toast.LENGTH_SHORT).show();
        }
        deleteFriendToDatabase(friendId);
    }

    private void deleteFriendToDatabase(String friendId) {
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/deleteFriend.php/?"
                + "user_id=" + userID
                + "&friend_id=" + friendId;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String re = response.getString("response");
                    if (re.equals("ok")){
                        Toast.makeText(getActivity(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                        initializeSql();
                    } else{
                        Toast.makeText(getActivity(), "Add Failed", Toast.LENGTH_SHORT).show();
                    }
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
