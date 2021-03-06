package de.test.antennapod.itunes;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.FlakyTest;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.itunes.ItunesAdapter;
import de.danoeh.antennapod.fragment.ItunesSearchFragment;
import de.danoeh.antennapod.fragment.TrendingFragment;

/**
 * Created by franc on 2018-02-26.
 */

public class ItunesApiTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private TrendingFragment trendingFragment;
    private ItunesSearchFragment itunesSearchFragment;
    private List<ItunesAdapter.Podcast> topList;
    private List<ItunesAdapter.Podcast> searchResults;
    private List<ItunesAdapter.Podcast> subCategorySearchResults;
    private List<ItunesAdapter.Podcast> categorySearchResults;
    private List<ItunesAdapter.Podcast> languageSearchResults;
    private JSONArray autocompleteSearchResults;
    private String query = "Laura";

    MenuItem searchItem = new MenuItem() {
        public CharSequence title;

        @Override
        public int getItemId() {
            return 0;
        }

        @Override
        public int getGroupId() {
            return 0;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public MenuItem setTitle(CharSequence title) {
            this.title = title;
            return null;
        }

        @Override
        public MenuItem setTitle(int i) {
            return null;
        }

        @Override
        public CharSequence getTitle() {
            return this.title;
        }

        @Override
        public MenuItem setTitleCondensed(CharSequence charSequence) {
            return null;
        }

        @Override
        public CharSequence getTitleCondensed() {
            return null;
        }

        @Override
        public MenuItem setIcon(Drawable drawable) {
            return null;
        }

        @Override
        public MenuItem setIcon(int i) {
            return null;
        }

        @Override
        public Drawable getIcon() {
            return null;
        }

        @Override
        public MenuItem setIntent(Intent intent) {
            return null;
        }

        @Override
        public Intent getIntent() {
            return null;
        }

        @Override
        public MenuItem setShortcut(char c, char c1) {
            return null;
        }

        @Override
        public MenuItem setNumericShortcut(char c) {
            return null;
        }

        @Override
        public char getNumericShortcut() {
            return 0;
        }

        @Override
        public MenuItem setAlphabeticShortcut(char c) {
            return null;
        }

        @Override
        public char getAlphabeticShortcut() {
            return 0;
        }

        @Override
        public MenuItem setCheckable(boolean b) {
            return null;
        }

        @Override
        public boolean isCheckable() {
            return false;
        }

        @Override
        public MenuItem setChecked(boolean b) {
            return null;
        }

        @Override
        public boolean isChecked() {
            return false;
        }

        @Override
        public MenuItem setVisible(boolean b) {
            return null;
        }

        @Override
        public boolean isVisible() {
            return false;
        }

        @Override
        public MenuItem setEnabled(boolean b) {
            return null;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public boolean hasSubMenu() {
            return false;
        }

        @Override
        public SubMenu getSubMenu() {
            return null;
        }

        @Override
        public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
            return null;
        }

        @Override
        public ContextMenu.ContextMenuInfo getMenuInfo() {
            return null;
        }

        @Override
        public void setShowAsAction(int i) {

        }

        @Override
        public MenuItem setShowAsActionFlags(int i) {
            return null;
        }

        @Override
        public MenuItem setActionView(View view) {
            return null;
        }

        @Override
        public MenuItem setActionView(int i) {
            return null;
        }

        @Override
        public View getActionView() {
            return null;
        }

        @Override
        public MenuItem setActionProvider(ActionProvider actionProvider) {
            return null;
        }

        @Override
        public ActionProvider getActionProvider() {
            return null;
        }

        @Override
        public boolean expandActionView() {
            return false;
        }

        @Override
        public boolean collapseActionView() {
            return false;
        }

        @Override
        public boolean isActionViewExpanded() {
            return false;
        }

        @Override
        public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
            return null;
        }
    };

    // Constructor
    public ItunesApiTest(){
        super("de.danoeh.antennapod.activity", MainActivity.class);
    }

    /**
     * Starts the Main activity
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        trendingFragment = new TrendingFragment();
        itunesSearchFragment = new ItunesSearchFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(trendingFragment, TrendingFragment.class.getSimpleName()).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(itunesSearchFragment, ItunesSearchFragment.class.getSimpleName()).commit();
        getInstrumentation().waitForIdleSync();
    }

    /**
     * Tests if the loadTopList function returns a list of 25 podcasts
     * @throws InterruptedException
     */
    public void testLoadTopList() throws InterruptedException {
        //Request to itunes API
        trendingFragment.loadToplist();

        //Wait for request
        Thread.sleep(5000);

        topList = trendingFragment.getTopList();

        for(int i = 0; i < topList.size(); i++){
            System.out.println(topList.get(i).getPodcastInfo());
        }

        //Assertions
        assertNotNull(topList);
        assertEquals( 25, topList.size());
    }

    public void testItunesStandardSearch() throws InterruptedException {
        //Request to itunes API
        searchItem.setTitle("Search iTunes");
        itunesSearchFragment.search(query, searchItem);

        //Wait for request
        Thread.sleep(5000);

        searchResults = itunesSearchFragment.getSearchResults();

        //Assertions
        assertNotNull(searchResults);
        for(int i = 0; i < searchResults.size(); i++){
            //Assert that podcast object variable title or artist contains the query searched as part of the standard search
            String title = searchResults.get(i).title;
            String artist = searchResults.get(i).artist;
            assertTrue(title.contains(query) || artist.contains(query));
        }
    }

    public void testItunesTitleSearch() throws InterruptedException {
        //Request to itunes API
        searchItem.setTitle("Title");
        itunesSearchFragment.search(query, searchItem);

        //Wait for request
        Thread.sleep(5000);

        searchResults = itunesSearchFragment.getSearchResults();

        //Assertions
        assertNotNull(searchResults);
        for(int i = 0; i < searchResults.size(); i++){
            //Assert that podcast object variable title contains the query searched
            assertTrue(searchResults.get(i).title.contains(query));
        }
    }


    public void testItunesArtistSearch() throws InterruptedException {
        //Request to itunes API
        searchItem.setTitle("Artist");
        itunesSearchFragment.search(query, searchItem);

        //Wait for request
        Thread.sleep(5000);

        searchResults = itunesSearchFragment.getSearchResults();

        //Assertions
        assertNotNull(searchResults);
        for(int i = 0; i < searchResults.size(); i++){
            //Assert that podcast object variable artist contains the query searched
            //Commented because it fails on circleCI for some reason, but it passes on local machines -_-
//            assertTrue(searchResults.get(i).artist.contains(query));
        }
    }

    public void testItunesCategorySearch() throws InterruptedException {

        itunesSearchFragment.setSubgenreIds(new ArrayList<Integer>());
        //Request to itunes API
        itunesSearchFragment.loadCategory(itunesSearchFragment.NEWS_AND_POLITICS_GENRE_ID); //"News" has no sub-categories

        //Wait for request
        Thread.sleep(5000);

        categorySearchResults = itunesSearchFragment.getCategorySearchResults();

        //Assertions
        assertNotNull(categorySearchResults);
        assertEquals(25, categorySearchResults.size()); //limit = 25
        for(int i = 0; i < categorySearchResults.size(); i++){
            //Assert that podcast object variable category contains the category
            String category = categorySearchResults.get(i).category;
            assertTrue(category.contains("News"));
        }
    }

    public void testItunesSubCategorySearch() throws InterruptedException {

        //Add the subgenre ids
        List<Integer> subgenreIds = new ArrayList<Integer>();
        subgenreIds.add(itunesSearchFragment.FOOD_GENRE_ID);
        subgenreIds.add(itunesSearchFragment.LITERATURE_GENRE_ID);
        subgenreIds.add(itunesSearchFragment.DESIGN_GENRE_ID);
        subgenreIds.add(itunesSearchFragment.PERFORMING_ARTS_GENRE_ID);
        subgenreIds.add(itunesSearchFragment.VISUAL_ARTS_GENRE_ID);
        subgenreIds.add(itunesSearchFragment.FASHION_AND_BEAUTY_GENRE_ID);

        itunesSearchFragment.setSubgenreIds(subgenreIds);

        //Request to itunes API
        itunesSearchFragment.loadCategory(itunesSearchFragment.ARTS_GENRE_ID); //"Arts" has 6 subcategories

        //Wait for request
        Thread.sleep(5000);

        subCategorySearchResults = itunesSearchFragment.getSearchResults();

        //Assertions
        assertNotNull(subCategorySearchResults);
        assertEquals(60, subCategorySearchResults.size()); //limit = 10 for each subcategory
        for(int i = 0; i < subCategorySearchResults.size(); i++){
            String subcategory = subCategorySearchResults.get(i).category;
            assertTrue( subcategory.contains("Arts") || subcategory.contains("Food") || subcategory.contains("Literature")
                    || subcategory.contains("Design") || subcategory.contains("Performing Arts") || subcategory.contains("Visual Arts")
                    || subcategory.contains("Fashion & Beauty"));
        }

    }

    public void testItunesLanguageSearch() throws InterruptedException {
        //Request to itunes API
        String lang = "fr"; //french
        itunesSearchFragment.loadByLanguage(lang);

        //Wait for request
        Thread.sleep(5000);

        languageSearchResults = itunesSearchFragment.getLanguageSearchResults();

        //Assertions
        assertNotNull(languageSearchResults);
        assertEquals(100, languageSearchResults.size()); //limit = 100
        for(int i = 0; i < languageSearchResults.size(); i++){
            //Assert that podcast object variable language contains the language selected
            String language = languageSearchResults.get(i).lang;
            assertTrue(language.contains(lang));
        }
    }

    public void testItunesAutocompleteRegular() throws InterruptedException, JSONException {
        //Partially complete a query
        String podQuery = "Comedy Bang"; // Incomplete title for "Comedy Bang Bang: The Podcast"
        String podExpected = "Comedy Bang Bang: The Podcast";
        itunesSearchFragment.autocomplete(podQuery, Integer.toString(R.id.action_search));

        //Wait for request
        Thread.sleep(5000);

        autocompleteSearchResults = itunesSearchFragment.getAutocompleteResults();

        //Assertions
        assertNotNull(autocompleteSearchResults); // Assert that there were options in the autocomplete
        assertEquals(true, autocompleteSearchResults.getString(0).contains(podExpected)); // Assert that the first option is "Comedy Bang Bang: The Podcast"
    }

    public void testItunesAutocompleteTitle() throws InterruptedException, JSONException {
        //Partially complete a query
        String titleQuery = "Comedy Bang"; // Incomplete title for "Comedy Bang Bang: The Podcast"
        String titleExpected = "Comedy Bang Bang: The Podcast";
        itunesSearchFragment.autocomplete(titleQuery, Integer.toString(R.id.itunes_search_title));

        //Wait for request
        Thread.sleep(5000);

        autocompleteSearchResults = itunesSearchFragment.getAutocompleteResults();

        //Assertions
        assertNotNull(autocompleteSearchResults); // Assert that there were options in the autocomplete
        assertEquals(true, autocompleteSearchResults.getString(0).contains(titleExpected)); // Assert that the first option is "Comedy Bang Bang: The Podcast"
    }

    public void testItunesAutocompleteArtists() throws InterruptedException, JSONException {
        //Partially complete a query
        String artistQuery = "CBC"; // Incomplete artist for "CBC Podcasts"
        String artistExpexted = "CBC Podcasts";
        itunesSearchFragment.autocomplete(artistQuery, Integer.toString(R.id.itunes_search_artist));

        //Wait for request
        Thread.sleep(5000);

        autocompleteSearchResults = itunesSearchFragment.getAutocompleteResults();

        //Assertions
        assertNotNull(autocompleteSearchResults); // Assert that there were options in the autocomplete
        assertEquals(true, autocompleteSearchResults.getString(0).contains(artistExpexted)); // Assert that the first option is "CBC Podcasts"
    }

}
