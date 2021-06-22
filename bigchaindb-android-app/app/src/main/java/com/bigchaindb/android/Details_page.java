package com.bigchaindb.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sourcey.android.R;

import java.io.IOException;
import java.net.ConnectException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class Details_page extends AppCompatActivity {
    private static final String TAG = "Details_page";
    BigchainDB bigchainDBApi = new BigchainDB(handleServerResponse());
    int SUCCESS_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

    }
        private GenericCallback handleServerResponse () {
            //define callback methods to verify response from BigchainDBServer
            GenericCallback callback = new GenericCallback() {

                @Override
                public void transactionMalformed(Response response) {
                    Log.d(TAG, "malformed " + response.message());
                    onFailure();
                }

                @Override
                public void pushedSuccessfully(Response response) {
                    Log.d(TAG, "pushedSuccessfully");
                    onSuccess(response);
                }

                @Override
                public void otherError(Response response) {
                    Log.d(TAG, "otherError" + response.message());
                    onFailure();
                }
            };

            return callback;
        }

        public  void getdisplaydata() throws IOException
        {
            Transaction data = bigchainDBApi.recieveTransaction("043b3ed2e0c63f55324f017e0e9bde3d66ddae8ea094287124cd6394187085e7");
            data.getAsset().getData();
            Log.d(TAG ,"Hopefully this is the data" +data.toString());

        }


    private void onSuccess(Response response) {
        SUCCESS_CODE = 0;
        Log.d(TAG, "(*) Transaction successfully committed..");
        Log.d(TAG, response.toString());
    }

    private void onFailure() {
        SUCCESS_CODE = -1;
        Log.d(TAG, "Transaction failed");
        Log.d(TAG, "Transaction failed");
    }
}