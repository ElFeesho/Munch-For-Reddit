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


                switch (menuItem.getItemId()) {

//                    case R.id.nav_profile:
//                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_messages:
//                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_user:
//                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_Subreddit:
//                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_settings:
//                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                        break;

                    case R.id.item_frontpage:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.item_all:
                        mViewPager.setCurrentItem(1);
                        break;

                    case R.id.item_aww:
                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.item_art:

                        mViewPager.setCurrentItem(3);
                        break;

                    case R.id.item_askreddit:

                        mViewPager.setCurrentItem(4);
                        break;

                    case R.id.item_askscience:

                        mViewPager.setCurrentItem(5);
                        break;

                    case R.id.item_announcements:

                        mViewPager.setCurrentItem(6);
                        break;

                    case R.id.item_blog:

                        mViewPager.setCurrentItem(7);
                        break;

                    case R.id.item_books:

                        mViewPager.setCurrentItem(8);
                        break;

                    case R.id.item_creepy:

                        mViewPager.setCurrentItem(9);
                        break;

                    case R.id.item_dataisbeautiful:

                        mViewPager.setCurrentItem(10);
                        break;

                    case R.id.item_diy:

                        mViewPager.setCurrentItem(11);
                        break;

                    case R.id.item_documentaries:

                        mViewPager.setCurrentItem(12);
                        break;

                    case R.id.item_earthporn:

                        mViewPager.setCurrentItem(13);
                        break;


                    case R.id.item_eli5:

                        mViewPager.setCurrentItem(14);
                        break;

                    case R.id.item_fitness:

                        mViewPager.setCurrentItem(15);
                        break;

                    case R.id.item_food:

                        mViewPager.setCurrentItem(16);
                        break;

                    case R.id.item_funny:

                        mViewPager.setCurrentItem(17);
                        break;

                    case R.id.item_futurology:

                        mViewPager.setCurrentItem(18);
                        break;

                    case R.id.item_gadgets:

                        mViewPager.setCurrentItem(19);
                        break;

                    case R.id.item_gaming:

                        mViewPager.setCurrentItem(20);
                        break;

                    case R.id.item_getmotivated:

                        mViewPager.setCurrentItem(21);
                        break;

                    case R.id.item_gifs:

                        mViewPager.setCurrentItem(22);
                        break;

                    case R.id.item_history:

                        mViewPager.setCurrentItem(23);
                        break;

                    case R.id.item_iama:

                        mViewPager.setCurrentItem(24);
                        break;

                    case R.id.item_internetisbeautiful:

                        mViewPager.setCurrentItem(25);
                        break;

                    case R.id.item_jokes:

                        mViewPager.setCurrentItem(26);
                        break;

                    case R.id.item_lpt:

                        mViewPager.setCurrentItem(27);
                        break;

                    case R.id.item_listentothis:

                        mViewPager.setCurrentItem(28);
                        break;


                    case R.id.item_mildlyinteresting:

                        mViewPager.setCurrentItem(29);
                        break;

                    case R.id.item_movies:

                        mViewPager.setCurrentItem(30);
                        break;

                    case R.id.item_music:

                        mViewPager.setCurrentItem(31);
                        break;

                    case R.id.item_news:

                        mViewPager.setCurrentItem(32);
                        break;

                    case R.id.item_nosleep:

                        mViewPager.setCurrentItem(33);
                        break;


                    case R.id.item_nottheonion:

                        mViewPager.setCurrentItem(34);
                        break;

                    case R.id.item_oldschoolcool:

                        mViewPager.setCurrentItem(35);
                        break;


                    case R.id.item_personalfinance:

                        mViewPager.setCurrentItem(36);
                        break;

                    case R.id.item_philosophy:

                        mViewPager.setCurrentItem(37);
                        break;

                    case R.id.item_photoshopbattles:

                        mViewPager.setCurrentItem(38);
                        break;

                    case R.id.item_pics:

                        mViewPager.setCurrentItem(39);
                        break;

                    case R.id.item_science:

                        mViewPager.setCurrentItem(40);
                        break;

                    case R.id.item_showerthoughts:

                        mViewPager.setCurrentItem(41);
                        break;

                    case R.id.item_space:

                        mViewPager.setCurrentItem(42);
                        break;

                    case R.id.item_sports:

                        mViewPager.setCurrentItem(43);
                        break;

                    case R.id.item_television:

                        mViewPager.setCurrentItem(44);
                        break;

                    case R.id.item_tifu:

                        mViewPager.setCurrentItem(45);
                        break;

                    case R.id.item_til:

                        mViewPager.setCurrentItem(46);
                        break;

                    case R.id.item_twox:

                        mViewPager.setCurrentItem(47);
                        break;

                    case R.id.item_upnews:

                        mViewPager.setCurrentItem(48);
                        break;

                    case R.id.item_videos:

                        mViewPager.setCurrentItem(49);
                        break;

                    case R.id.item_worldnews:

                        mViewPager.setCurrentItem(50);
                        break;


                    case R.id.item_writingprompts:

                        mViewPager.setCurrentItem(51);
                        break;


                    default:

                        mViewPager.setCurrentItem(0);

                }

//                mDrawerLayout.closeDrawers();

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
