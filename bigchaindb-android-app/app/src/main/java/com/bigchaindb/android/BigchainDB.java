package com.bigchaindb.android;

import android.util.Log;

import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.model.Transactions;
import com.bigchaindb.api.TransactionsApi;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import com.bigchaindb.api.AssetsApi;
import com.bigchaindb.constants.BigchainDbApi;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.BigChainDBGlobals;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.model.Transactions;
import com.bigchaindb.util.JsonUtils;
import com.bigchaindb.util.NetworkUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.KeyPair;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
public class BigchainDB {

    private static KeyPairGenerator edDsaKpg = new KeyPairGenerator();
    private static final String TAG = "BigchainDB";
    private static String userId = "";
    private static final KeyPair KEYS = edDsaKpg.generateKeyPair();
    private static final String bigchainDBNodeURL = "http://10.0.2.2:9984" ;
    private GenericCallback callback = null;

    public BigchainDB(GenericCallback callback){
        this.callback = callback;
    }

    /**
     * configures connection url and credentials
     */
    public void setConfig() {
        BigchainDbConfigBuilder
                .baseUrl(bigchainDBNodeURL) //or use http://testnet.bigchaindb.com
                .addToken("app_id", "")
                .addToken("app_key", "").setup();
    }

    public Transaction sendTransaction(String data) throws Exception {

        Log.d(TAG, "Setting configuration..");
        this.setConfig();
        Transaction transaction = null;

        //create asset data
        Map<String, String> assetData = new TreeMap<String, String>();
        assetData.put("data", data);

        //create asset metadata
        Map<String, String> metadata = new TreeMap<String, String>();
        metadata.put("lastModifiedOn", new Date().toString());

        //build and send CREATE transaction
        transaction = BigchainDbTransactionBuilder
                .init()
                .addAssets(assetData, TreeMap.class)
                .addMetaData(metadata)
                .operation(Operations.CREATE)
                .buildAndSign((EdDSAPublicKey) KEYS.getPublic(), (EdDSAPrivateKey) KEYS.getPrivate())
                .sendTransaction(this.callback);

        return transaction;
    }

    public Transaction recieveTransaction(String id) throws IOException
    {

        Transaction dat = TransactionsApi.getTransactionById(id);
        return dat;
    }

}