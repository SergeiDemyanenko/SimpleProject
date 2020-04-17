package com.example.toDoAndroidClient;

import android.widget.ArrayAdapter;

import com.example.myapplication102.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import okhttp3.ResponseBody;


class ToDOAdapter {


    List<String> taskList = new ArrayList<>();
    List<String> nativIdList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private OkHttpClient client = new OkHttpClient();
    private static final String SERVER_URL = "http://10.0.0.120:8080";
    private static final String URL_LIST_REQUEST = SERVER_URL + "/api/list-obj";
    private static final String URL_PUT_ITEM = SERVER_URL + "/api/add";
    private static final String URL_EDIT_ITEM = SERVER_URL + "/api/edit";
    private static final String URL_DELETE_ITEM = SERVER_URL + "/api/delete?id=";


    public ToDOAdapter(MainActivity mainActivity) {
        getTaskList();
        this.adapter = new ArrayAdapter<>(mainActivity, R.layout.single_item, taskList);
        System.out.println("Hi");

    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    private void onGetResponse(Response response) throws IOException {
        String result = null;
        ResponseBody body = response.body();
        result = body.string();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String index = String.valueOf(jsonObject.get("id"));
                String value = String.valueOf(jsonObject.get("text"));
                taskList.add(value);
                nativIdList.add(index);
            }
            System.out.println("Hi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onPostResponse(Response response) throws IOException {
        String result = null;
        ResponseBody body = response.body();
        result = body.string();
        try {
            JSONObject jsonObject = new JSONObject(result);
            String index = String.valueOf(jsonObject.get("id"));
            String value = String.valueOf(jsonObject.get("text"));
            taskList.add(value);
            nativIdList.add(index);
            System.out.println("Hi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getTaskList() {

        okhttp3.Request request = new okhttp3.Request.Builder()
            .url(URL_LIST_REQUEST)
            .build();

        client.newCall(request)
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    onGetResponse(response);
                }
            });
    }


    void putNewItem(String textOfNewItem) {

        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("text", textOfNewItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, postdata.toString());


        okhttp3.Request request = new okhttp3.Request.Builder()
            .url(URL_PUT_ITEM)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .post(body)
            .build();


        client.newCall(request)
            .enqueue
                (new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        onPostResponse(response);
                    }
                });


    }

    public void editItem(final int itemId, String textOfEditItem) {

        taskList.set(itemId, textOfEditItem);

        String nativItemId = nativIdList.get(itemId);
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        JSONObject patchdata = new JSONObject();
        try {
            patchdata.put("id", nativItemId);
            patchdata.put("text", textOfEditItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, patchdata.toString());


        okhttp3.Request request = new okhttp3.Request.Builder()
            .url(URL_EDIT_ITEM)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .patch(body)
            .build();


        client.newCall(request)
            .enqueue
                (new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {

                    }
                });
    }


    public void removeItem(int itemId) {

        taskList.remove(itemId);

        String nativItemId = nativIdList.get(itemId);

        okhttp3.Request request = new okhttp3.Request.Builder()
            .url(URL_DELETE_ITEM + nativItemId)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .delete()
            .build();


        client.newCall(request)
            .enqueue
                (new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {

                    }
                });
    }
}



