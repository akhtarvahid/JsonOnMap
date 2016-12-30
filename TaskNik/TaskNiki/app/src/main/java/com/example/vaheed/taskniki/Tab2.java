package com.example.vaheed.taskniki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Belal on 2/3/2016.
 */

public class Tab2 extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    Button mc;
    // URL to get contacts JSON
    // private static String url = "http://api.androidhive.info/contacts/";
    private static String url="http://akhtarvahid543.16mb.com/contacts.json";
    ArrayList<HashMap<String, String>> contactList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab2,container,false);


        contactList = new ArrayList<>();

        lv = (ListView)v.findViewById(R.id.list);
        new GetContacts().execute();

       return v;
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                      /*  String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");*/

                        String name = c.getString("name");
                        String email = c.getString("email");
                        String phone=c.getString("phone");
                        String latitude=c.getString("latitude");
                        String longitude=c.getString("longitude");


                        // Phone node is JSON Object
                        /*JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/



                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        //contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("phone", phone);
                        contact.put("latitude",latitude);
                        contact.put("longitude",longitude);
                        // adding contact to contact list
                        contactList.add(contact);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            final ListAdapter adapter = new SimpleAdapter(
                    getContext(), contactList,
                    R.layout.list_item, new String[]{"name", "email",
                    "phone","latitude","longitude"}, new int[]{R.id.name,
                    R.id.email, R.id.phone,R.id.lat,R.id.longi});

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//final String item = (String) arg0.getItemAtPosition(arg2);
                    HashMap<String, String> key=contactList.get(position);
                    String name=key.get("name");
                    String email=key.get("email");
                    String phone=key.get("phone");
                    String latitude=key.get("latitude");
                    String longitude=key.get("longitude");
                    // String value=String.valueOf(key);
                    Toast.makeText(getContext(), "arg2 : "+position, Toast.LENGTH_SHORT).show();
                    Intent view_det = new Intent(getContext(),MapActivity.class);
                    //view_det.putExtra("position",value);
                    view_det.putExtra("name",name);
                    view_det.putExtra("email",email);
                    view_det.putExtra("phone",phone);
                    view_det.putExtra("latitude",latitude);
                    view_det.putExtra("longitude",longitude);
                    startActivity(view_det);

                }
            });
        }

    }


}
