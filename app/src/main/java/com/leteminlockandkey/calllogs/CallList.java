package com.leteminlockandkey.calllogs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CallList extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> JobItems = new ArrayList<HashMap<String, String>>();
    ArrayList<JobItemDetails> thisJobDetail = new ArrayList<JobItemDetails>();
    ArrayList<String> lister = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getTitle());
  //      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  //      fab.setOnClickListener(new View.OnClickListener() {
  //              @Override
  //              public void onClick(View view) {
  //              Snackbar.make(view, "More to do.", Snackbar.LENGTH_LONG)
  //                      .setAction("Action", null).show();
  //          }
  //          });
        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>()));
        PullXMLData theData = new PullXMLData();
        theData.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                String TempListViewClickedValue = thisJobDetail.get((int)id).getName();
                Intent intent = new Intent(CallList.this, JobDetailActivity.class);
                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                intent.putExtra("sentJobDetail",thisJobDetail.get((int)id));
                startActivity(intent);
            }
        });
    }
    class PullXMLData extends AsyncTask<String, Void, String> {
        ArrayAdapter<String> adapter;
        @Override
        protected void onPreExecute(){
            adapter = (ArrayAdapter<String>)listView.getAdapter();
        }
        @Override
        protected String doInBackground(String... params){
             String xml = null;
             try {
                URL url = new URL("http://www.leteminlockandkey.com/AppsFolder/mobilesearch.php");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                int statusCode = con.getResponseCode();
                if (statusCode ==  200) {
                    InputStream inputStream = new BufferedInputStream(con.getInputStream());
                    xml = readStream(inputStream);
                }
            }catch (Exception e){
                Log.d("Connection Exception","Something happon");
            }
            return xml;
        }
        @Override
        protected void onPostExecute(String results){
            Document doc = getDomElement(results);
            NodeList nl = doc.getElementsByTagName("markers");
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value
                map.put("ID", getValue(e,"ID"));
                map.put("Name", getValue(e,"Name"));
                map.put("Phonenumber", getValue(e,"Phonenumber"));
                JobItemDetails newJob = new JobItemDetails();
                newJob.setID(getValue(e,"ID"));
                newJob.setName(getValue(e,"Name"));
                newJob.setJob(getValue(e,"Job"));
                newJob.setYear(getValue(e,"Year"));
                newJob.setMake(getValue(e, "Make"));
                newJob.setModel(getValue(e,"Model"));
                newJob.setComments(getValue(e,"Comments"));
                newJob.setPhonenumber(getValue(e,"Phonenumber"));
                newJob.setAddress(getValue(e,"Address"));
                newJob.setRequirements(getValue(e,"Requirements"));
                newJob.setETAStart(getValue(e,"ETAStart"));
                newJob.setETAEnd(getValue(e,"ETAEnd"));
                newJob.setStatus(getValue(e,"Status"));
                newJob.setQuote(getValue(e,"Quote"));
                newJob.setReferred(getValue(e,"Referred"));
                thisJobDetail.add(0, newJob);
                // adding HashList to ArrayList
                JobItems.add(map);
                adapter.notifyDataSetChanged();
                adapter.add(map.get("Name")+" "+map.get("Phonenumber"));
                lister.add(adapter.getItem(i));
            }
            Collections.reverse(lister);
            listView.setAdapter(new ArrayAdapter<String>(CallList.this,android.R.layout.simple_list_item_1,lister));
        }
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
    public Document getDomElement(String xml){
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}