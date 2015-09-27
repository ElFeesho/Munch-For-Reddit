package com.example.naren.munch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.naren.munch.R;
import com.example.naren.munch.adapters.RecyclerViewAdapter;
import com.example.naren.munch.model.RedditPost;
import com.example.naren.munch.modeladaption.JSONPostAdapter;
import com.example.naren.munch.network.NetworkOperationQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RedditPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RedditPostFragment extends Fragment {

    private static final String ARG_SUBREDDIT = "param1";

    private String subredditName;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<RedditPost> redditPostArrayList = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressbar;

    //variables for scrolling down to more items
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    public RedditPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subreddit Parameter 1.
     * @return A new instance of fragment RedditPostFragment.
     */
    public static RedditPostFragment newInstance(String subreddit) {
        RedditPostFragment fragment = new RedditPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBREDDIT, subreddit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            subredditName = getArguments().getString(ARG_SUBREDDIT);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_redditpost, container, false);

        mProgressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);

        //setting up the recyclerview with the adapt
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_recyclerview);

        if (subredditName.contains("frontpage")) {
            adapter = new RecyclerViewAdapter(getActivity(), getRedditPost());
        } else {
            adapter = new RecyclerViewAdapter(getActivity(), getCustomRedditPost(subredditName));
        }

        mRecyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //swipe to refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (subredditName.contains("frontpage")) {
                    getRedditPost();
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    getCustomRedditPost(subredditName);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        //keep loading additions posts
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something

                    if (subredditName.contains("frontpage")) {
                        getMoreRedditPost(redditPostArrayList.get(redditPostArrayList.size() - 1).getAfter());
                    } else {
                        getMoreCustomRedditPost(subredditName, redditPostArrayList.get(redditPostArrayList.size() - 1).getAfter());
                    }

                    loading = true;
                }
            }
        });

        return rootView;
    }

    //send JSON request to URL and then parse the responses into relevant data
    private ArrayList<RedditPost> getRedditPost() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL + RedditPost.JSON_ENDPOINT, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    redditPostArrayList.addAll(new JSONPostAdapter().adapt(response));
                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());

                    //handle refresh actions
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressbar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        NetworkOperationQueue.getInstance(getActivity().getApplication()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }

    //send JSON request to URL and then parse the responses into relevant data
    private ArrayList<RedditPost> getCustomRedditPost(String custom) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.Test_URL + custom + RedditPost.JSON_ENDPOINT, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    redditPostArrayList.addAll(new JSONPostAdapter().adapt(response));
                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());

                    //handle refresh actions
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressbar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        NetworkOperationQueue.getInstance(getActivity().getApplication()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }

    //method to keep recieveing loading more posts
    private ArrayList<RedditPost> getMoreRedditPost(String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL + RedditPost.JSON_ENDPOINT + RedditPost.AFTER_ENDPOINT + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    redditPostArrayList.addAll(new JSONPostAdapter().adapt(response));
                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        NetworkOperationQueue.getInstance(getActivity().getApplication()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }

    //method to keep loading more posts
    private ArrayList<RedditPost> getMoreCustomRedditPost(String custom, String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.Test_URL + custom + RedditPost.JSON_ENDPOINT + RedditPost.AFTER_ENDPOINT + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    redditPostArrayList.addAll(new JSONPostAdapter().adapt(response));
                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        NetworkOperationQueue.getInstance(getActivity().getApplication()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }

    //Handle actions in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh:

                if (subredditName.contains("frontpage")) {
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressbar.setVisibility(View.VISIBLE);
                    getRedditPost();
                    mLayoutManager.scrollToPosition(0);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressbar.setVisibility(View.VISIBLE);
                    getCustomRedditPost(subredditName);
                    mLayoutManager.scrollToPosition(0);
                }
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
