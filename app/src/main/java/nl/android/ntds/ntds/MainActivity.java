package nl.android.ntds.ntds;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebSettings;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import android.os.AsyncTask;

import java.util.Calendar;
import java.net.Socket;
import java.net.InetSocketAddress;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import android.widget.LinearLayout;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.CookieManager;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Eigen code
    public String styling = "@media only screen {\n" +
            "  body              { font-family: \"Trebuchet MS\",Helvetica,sans-serif; padding-bottom: 10pt; }\n" +
            "  .main             { position:fixed; left:300px; right:100px; top:0px; padding-left:10px; padding-right:10px;\n" +
            "                      font-size: 16px; color: black; height:100%; \n" +
            "                      overflow-y:auto; \n" +
            "                      background-image: url(\"background.png\");\n" +
            "                      background-repeat: repeat; }\n" +
            "  .pubreg           { padding-left:10px; padding-right:10px;\n" +
            "                      font-size: 16px; color: black; height:100%; \n" +
            "                      overflow-y:auto; }\n" +
            "  .pubreg a         { color: blue; text-decoration: inherit; cursor: pointer; }\n" +
            "}\n" +
            "\n" +
            "@media only print {\n" +
            "  body              { font-size: 12pt; font-family: Arial,Helvetica,sans-serif; padding-top: 0px; }  \n" +
            "  .main             { left:0px; right:0px; top:0px; bottom:0px; }\n" +
            "  .menubar, .eventbar, input[type=button], input[type=submit] \n" +
            "                    { display: none; }\n" +
            "}\n" +
            "\n" +
            "@page               { size: 210mm 297mm; margin: 5mm 5mm 5mm 5mm; }\n" +
            "                  \n" +
            ".eventbar           { height:100%; width:300px; overflow-y:auto; position:fixed; left:0px; top:0px; \n" +
            "                      background-color: red; color: white; \n" +
            "                      font-size: 18px; font-style: normal; }\n" +
            "\n" +
            ".ev1                { background-color: white; color: red; padding-left: 0px; font-weight: bold; }\n" +
            ".ev2                { padding-left: 15px; font-weight: bold; }\n" +
            ".ev3                { padding-left: 30px;     }\n" +
            "\n" +
            ".menubar            { height:100%; width:100px; overflow-y:auto; position:fixed; right:0px; top:0px; \n" +
            "                      background-color: #009F00; text-align: center; color: white; \n" +
            "                      font-size: 14px; font-style: normal; }\n" +
            "\n" +
            "input, select       { font: inherit; } \n" +
            "input[type=button], input[type=submit] , input[type=reset] { padding: 6px; }\n" +
            "input[type=radio]   { padding: 6px; -moz-transform: scale(1.2); } \n" +
            "\n" +
            "\n" +
            ".digimain, .digibar, .digiform, .digimark0, .digimark1, .digimark\n" +
            "                    { font-family: Arial,Helvetica,sans-serif; }\n" +
            "\n" +
            ".digimain           { font-size: 14px; }\n" +
            "\n" +
            ".digibar            { position:fixed; top:0px; left:0px; width: 100%; height: 56px; \n" +
            "                      font-size: 14px; color: white; Background-color: red; overflow:hidden; vertical-align: top; }\n" +
            "\n" +
            ".digiform           { padding-top: 56px; padding-bottom: 128px; font-size: 16px; color: black;  }\n" +
            "\n" +
            ".digikeys           { background-color: lightgray; }\n" +
            "\n" +
            ".digimarkframe      { width: 64px; height: 106px; margin: 4px; float: left; }\n" +
            "\n" +
            ".digitbl            { border-style: none; }\n" +
            ".digirow            { }\n" +
            "\n" +
            ".digimark0, .digimark1, .digimark\n" +
            "                    { width: 40px; height: 40px;  border-style: solid; \n" +
            "                      vertical-align: middle; text-align: center; border-width: 2px; padding: 0px; }\n" +
            ".digimark0          { border-color: black; color: black; }\n" +
            ".digimark1          { border-color: red;   color: red;   }\n" +
            "\n" +
            ".digiplace          { width: 30px; height: 40px;  border-style: none; \n" +
            "                      vertical-align: middle; text-align: center; border-width: 2px; padding: 0px; margin: 0px; \n" +
            "                      color: black; background-color: LightGray; font-weight: bold; font-size: 24px; }\n" +
            "\n" +
            ".digibutton         { padding: 2px !important; width: 40px; height: 40px; margin: 6px; font-size: 20px; }\n" +
            "\n" +
            "                      \n" +
            "table               { border-style: none; }\n" +
            "\n" +
            ".form    td         { padding: 5px; vertical-align: baseline; border: none; }\n" +
            "\n" +
            ".showtable                                   { border: 2px solid #E0E0E0; }\n" +
            ".showtable td                                { padding: 3px; }\n" +
            ".showtable tr:nth-child(odd), .showtable tr  { background-color: #E0E0E0; }\n" +
            ".showtable tr:nth-child(even)                { background-color: white;   }\n" +
            "\n" +
            ".mmiOff             { color: red;       }\n" +
            ".mmiOn              { color: DarkGreen; }\n" +
            ".mmiCur             { text-decoration: underline;  } \n" +
            "\n" +
            ".adjrow             { margin: 0; padding: 0; }\n" +
            "td.adjrow           { width: 4ch; text-align: center; }\n" +
            ".adjrow table       { border: 2px solid gray; margin: -1px; }\n" +
            ".adjrow br          { clear: both; }\n" +
            ".adjrow input       { width: 1em; text-align: center; font-size: 18px; }\n" +
            ".adjblock           { float: left; }\n" +
            "td.adjfc1           {             width: 10ch; padding-right: 20px; text-align: right; }\n" +
            "td.adfsno           { padding: 0; width: 10ch; padding-right: 20px; text-align: right; }\n" +
            "\n" +
            ".assadj, .assadj td { border: 2px solid gray; border-collapse: collapse; vertical-align: top; }\n" +
            ".assbox             { text-align: center; }\n" +
            "\n" +
            "a                   { text-decoration: inherit; color: inherit; cursor: pointer; }\n" +
            "\n" +
            ".delbutton          { color: red; font-weight: bold; }\n" +
            "\n" +
            ".butbar input, .butbar h3\n" +
            "                    { margin-left: 15px; margin-right: 15px; }\n" +
            "input.donebut       { float: right; }\n" +
            "\n" +
            ".mmiswitch          { float: left; margin-left: 15px; font-weight: bold; font-size: larger; }\n" +
            "\n" +
            "@media only screen {\n" +
            "  .disqual          { color: DimGray; }\n" +
            "}\n" +
            "@media only print {\n" +
            "  .disqual          { color: rgba(0,0,0,0.6) !important; }\n" +
            "}\n" +
            "\n" +
            ".negative           { color: red; }\n" +
            "\n" +
            ".report             { page-break-after: always; width: 100%; border: none; border-collapse: collapse; }\n" +
            "\n" +
            ".report tr, .report td  { page-break-inside: never; }\n" +
            "\n" +
            ".pagehead           { font-size: 150%; font-weight: bold; border: 2pt solid black; text-align: left; }\n" +
            "\n" +
            ".listhead           { padding-top: 12pt; font-weight: bold; }\n" +
            "\n" +
            ".listbody           {  }\n" +
            "\n" +
            ".margintxt          { text-align: center; width: 100%; font-size: 12pt; font-weight: normal; }\n" +
            "\n" +
            ".helptext           { color: green; }\n" +
            "\n" +
            "#header             { padding-bottom: 10pt; }\n" +
            "#footer             { position: fixed; left: 0; bottom: 0mm; }\n" +
            "\n" +
            "\n";

//    public WebView webView = null;
    public Long endDate = 1520204400000L;

//     Test endDate
//    public Long endDate = 1519086633000L;
    public Boolean ntds2019 = false;
    public Long startDate = 1520024400000L;
    public TextView tv_countdown;
    public WebView wv_countdown;
    public TextView ntds2018countdown;


    public Boolean ntds2018 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


//        startDate =  111110L;
//        endDate = 1111111L;

        WebView webView = findViewById(R.id.webview);
        webView.clearCache(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.setWebViewClient(new myWebClient());

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(false);
        cookieManager.setAcceptThirdPartyCookies( webView, false );

        ntds2018countdown = (TextView) findViewById(R.id.countdown2018);

        Calendar calendar = Calendar.getInstance();
        Long timestamp = calendar.getTimeInMillis();

        if (timestamp > startDate) {ntds2018 = true;}
        if (timestamp > endDate) {ntds2019 = true;}

        if (ntds2019) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setTitle("NTDS 2019");
            ViewGroup parent = (ViewGroup) webView.getParent();
            int index = parent.indexOfChild(webView);
            parent.removeView(webView);
            View view = getLayoutInflater().inflate(R.layout.countdown_alt, parent, false);
            parent.addView(view, index);
            tv_countdown = (TextView) findViewById(R.id.countdown_clock);


            wv_countdown = (WebView) findViewById(R.id.countdown_webview);
            wv_countdown.loadUrl("file:///android_asset/loading_alt.html");


            Calendar start_calendar = Calendar.getInstance();
            Calendar end_calendar = Calendar.getInstance();

            end_calendar.set(2019, 1, 22, 21,0,0); // 10 = November, month start at 0 = January

            long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
            long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
            long total_millis = (end_millis - start_millis); //total time in milliseconds


            //1000 = 1 second interval
            CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

//                    tv_countdown.setText("Tot over\n" + days + " dagen\n" + hours + " uur\n" + minutes + " minuten\n" + seconds + " seconden\n\n");

                    tv_countdown.setText("Nog " + days + " dagen, " + hours + " uur,\n" + minutes + " min en " + seconds + " sec te gaan!");

                }

                @Override
                public void onFinish() {
                    tv_countdown.setText("");
                    // Get params:
                    LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) tv_countdown.getLayoutParams();
                    loparams.height = 0;
                    loparams.weight = 0;
                    tv_countdown.setLayoutParams(loparams);
                    wv_countdown.loadUrl("file:///android_asset/welkom2.html");
                }
            };
            cdt.start();
        } else {
            getSupportActionBar().setTitle("NTDS 2018");
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_welcome));
            navigationView.setCheckedItem(R.id.nav_welcome);
        }
        // Eigen code
//        webView = findViewById(R.id.webview);
//        webView = findViewById(R.id.webview);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setAppCacheEnabled(false);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.TEXT_AUTOSIZING);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_welcome:
                if (ntds2018) {
                    ntds2018countdown.setAlpha(0);
                } else {
                    ntds2018countdown.setAlpha(1);
                    Calendar start_calendar = Calendar.getInstance();
                    Calendar end_calendar = Calendar.getInstance();

                    end_calendar.set(2018, 2, 2, 21,0,0); // 10 = November, month start at 0 = January

                    long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
                    long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
                    long total_millis = (end_millis - start_millis); //total time in milliseconds


                    //1000 = 1 second interval
                    CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                            millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                            long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                            millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                            millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

//                    tv_countdown.setText("Tot over\n" + days + " dagen\n" + hours + " uur\n" + minutes + " minuten\n" + seconds + " seconden\n\n");

                            ntds2018countdown.setText("Nog " + days + " dagen, " + hours + " uur,\n" + minutes + " min en " + seconds + " sec te gaan!\n");

                        }

                        @Override
                        public void onFinish() {
                            ntds2018countdown.setText("");;
                        }
                    };
                    cdt.start();
                }
                break;
            default:
                ntds2018countdown.setAlpha(0);
                break;
        }

        switch (item.getItemId()) {
            case R.id.nav_welcome:
                new getUrl().execute("Welcome");
                break;
            case R.id.nav_program:
                new getUrl().execute("https://ntds2018.wordpress.com/programma/");
                break;
            case R.id.nav_timetable:
                new getUrl().execute("https://ntds2018.wordpress.com/app/timetable/");
                break;
            case R.id.nav_heats_beg:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/beginners/");
                break;
            case R.id.nav_heats_breiten:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/breitensport-kwalificatie/");
                break;
            case R.id.nav_heats_amateurs:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/amateurs/");
                break;
            case R.id.nav_heats_professionals:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/professionals/");
                break;
            case R.id.nav_heats_masters:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/masters/");
                break;
            case R.id.nav_heats_champions:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/champions/");
                break;
            case R.id.nav_heats_open_klasse:
                new getUrl().execute("https://ntds2018.wordpress.com/app/heat-indelingen/open-klasse/");
                break;
            case R.id.nav_loc:
                new getUrl().execute("https://ntds2018.wordpress.com/app/locatie/");
                break;
            case R.id.nav_fun_facts:
                new getUrl().execute("https://ntds2018.wordpress.com/app/fun-facts/");
                break;
            case R.id.nav_faq:
                new getUrl().execute("https://ntds2018.wordpress.com/faq/");
                break;
            default:
                new getUrl().execute("");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.clearCache(true);
//            CookieManager.getInstance().removeAllCookies(null);
//            CookieManager.getInstance().flush();
//            CookieManager.getInstance().removeSessionCookies(null);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return false;

        }
    }

    // Eigen code
    @Override
    protected void onResume(){
        super.onResume();
    }

    private class getUrl extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            WebView webView = findViewById(R.id.webview);
            webView.clearCache(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setAppCacheEnabled(false);
            webView.clearHistory();
//            webView.loadData("<center><b>Loading...</b></center>", "text/html", "utf-8");
            webView.loadUrl("file:///android_asset/loading.html");
        }

        @Override
        protected String doInBackground(String... params)
        {
            try{ Thread.sleep(700); }catch(InterruptedException e){ }
            try {
                if (params[0].equals("") || params[0].equals("Welcome")) {
                    return params[0];
                } else {
                    Document doc = Jsoup.connect(params[0]).get();
                    String cssQuery = "div[class=post-content clear]";
                    Element ele = doc.select(cssQuery).first();
                    return ele.toString();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            WebView webView = findViewById(R.id.webview);
            webView.clearCache(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setAppCacheEnabled(false);
            webView.clearHistory();

            switch (result) {
                case "":
                    new checkIfOnline().execute();
                    break;
                case "Welcome":
                    webView.loadUrl("file:///android_asset/welkom.html");
                    break;
                default:
                    if (result.startsWith("<div class=\"post-content clear\"> \n" +
                            " <p>&nbsp;</p> \n" +
                            " <p>")) {
                        result = Jsoup.parse(result).text();
                        result = result.replace("</STYLE>",styling+"\n</STYLE>");
                        result = result.replace("BAD r438 &copy; M. Musial 2018 &mdash; License no: 004 (NTDS 2018)","");
                    }
//                    if (result.startsWith("<div class=\"post-content clear\"> \n" +
//                            " <p>&lt;html xmlns:v=\"urn:schemas-microsoft-com:vml")) {
//                        result = Jsoup.parse(result).text();
//                    }


                    webView.loadData(result, "text/html", "utf-8");
                    break;
            }
        }

    }

    private class checkIfOnline extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try{ Thread.sleep(3000); }catch(InterruptedException e){ }
            return hostAvailable();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            WebView webView = findViewById(R.id.webview);
            webView.clearCache(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setAppCacheEnabled(false);
            webView.clearHistory();

            if (result){
                webView.loadUrl("file:///android_asset/data_not_available.html");
            } else {
                webView.loadUrl("file:///android_asset/no_internet.html");
            }
        }
    }

    public boolean hostAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("www.google.com", 80), 2000);
            return true;
        } catch (IOException e) {
            // Either we have a timeout or unreachable host or failed DNS lookup
            System.out.println(e);
            return false;
        }
    }
}
