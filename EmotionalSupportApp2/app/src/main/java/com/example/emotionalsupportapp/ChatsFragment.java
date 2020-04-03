package com.example.emotionalsupportapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private String userID;
    private ArrayList<Friend> friends;
    private ArrayList<String> hasChatted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userID = getArguments().getString("EXTRA_USER_ID");
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        friends = new ArrayList<>();
        hasChatted = new ArrayList<>();

        getFriendsHasChatted();

        return view;
    }

    private void getFriendsHasChatted(){
        friends.clear();
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getMessages.php/?user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray re = response.getJSONArray("response");
                    for (int i = 0; i < re.length(); i++) {
                        JSONObject jsonobject = re.getJSONObject(i);
                        String senderId = jsonobject.getString("senderId");
                        String senderName = jsonobject.getString("senderName");
                        String receiverId = jsonobject.getString("receiverId");
                        String receiverName = jsonobject.getString("receiverName");
                        Log.d("test", senderId);
                        Log.d("test", receiverId);
                        if (userID.equals(senderId)){
                            if (hasChatted.contains(receiverId)){
                                continue;
                            }
                            hasChatted.add(receiverId);
                            friends.add(new Friend(senderId, senderName, receiverId, receiverName));
                        }else if (userID.equals(receiverId)){
                            if (hasChatted.contains(senderId)){
                                continue;
                            }
                            hasChatted.add(senderId);
                            friends.add(new Friend(receiverId, receiverName, senderId, senderName));
                        }
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
