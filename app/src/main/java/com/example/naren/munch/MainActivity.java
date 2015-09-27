package com.example.naren.munch;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.naren.munch.fragments.RedditPostFragment;

public class MainActivity extends AppCompatActivity {

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final static int[] subredditIds = {
                R.id.item_frontpage, R.id.item_all, R.id.item_aww,
                R.id.item_art, R.id.item_askreddit, R.id.item_askscience, R.id.item_announcements,
                R.id.item_blog, R.id.item_books, R.id.item_creepy, R.id.item_dataisbeautiful,
                R.id.item_diy, R.id.item_documentaries, R.id.item_earthporn, R.id.item_eli5,
                R.id.item_fitness, R.id.item_food, R.id.item_funny, R.id.item_futurology,
                R.id.item_gadgets, R.id.item_gaming, R.id.item_getmotivated, R.id.item_gifs,
                R.id.item_history, R.id.item_iama, R.id.item_internetisbeautiful, R.id.item_jokes, R.id.item_lpt,
                R.id.item_listentothis, R.id.item_mildlyinteresting, R.id.item_movies, R.id.item_music,
                R.id.item_news, R.id.item_nosleep, R.id.item_nottheonion, R.id.item_oldschoolcool,
                R.id.item_personalfinance, R.id.item_philosophy, R.id.item_photoshopbattles, R.id.item_pics,
                R.id.item_science, R.id.item_showerthoughts, R.id.item_space, R.id.item_sports,
                R.id.item_television, R.id.item_tifu, R.id.item_til, R.id.item_twox,
                R.id.item_upnews, R.id.item_videos, R.id.item_worldnews, R.id.item_writingprompts
        };

        private final static String[] FEED_TITLES =

                {"frontpage", "all", "aww", "art", "askreddit", "askscience",
                        "announcements", "blogs", "books", "creepy", "dataisbeautiful", "DIY", "Documentaries",
                        "earthporn", "explainlimeimfive", "fitness", "food", "funny", "futurology", "gadgets", "gaming", "getmotivated",
                        "gifs", "history", "iama", "internetisbeautiful", "jokes", "lifeprotips",
                        "listentothis", "mildlyinteresting", "movies", "Music", "news", "nosleep",
                        "nottheonion", "oldschoolcool", "personalfinance", "philosophy",
                        "photoshopbattles", "pics", "science", "showerthoughts", "space", "sports",
                        "television", "tifu", "todayilearned", "twoxchromosomes", "upliftingnews", "videos", "worldnews", "writingprompts"
                };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RedditPostFragment.newInstance(FEED_TITLES[position], "");
        }

        @Override
        public int getCount() {
            return FEED_TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FEED_TITLES[position];
        }

        private int lookupSubredditIdPosition(int itemId) {
            int position = 0;
            for (int i = 0; i < subredditIds.length; i++)
            {
                if (subredditIds[i] == itemId)
                {
                    position = i;
                    break;
                }
            }
            return position;
        }

        public int positionForSubreddit(int itemId) {
            return lookupSubredditIdPosition(itemId);
        }
    }

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Munch");

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                mViewPager.setCurrentItem(mAdapter.positionForSubreddit(menuItem.getItemId()), true);
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
