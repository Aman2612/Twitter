package ecorp.twitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Aman on 01/04/17.
 */

public class OAuthRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        OAuthClass oAuthClass= new OAuthClass();

        try {

            HashMap<String,String> body=new HashMap<>();
            body.put("status","Hello Ladies + Gentlemen, a signed OAuth request!");//parameters that go in a body of request

            HashMap<String,String> query=new HashMap<>();
            query.put("include_entities","true");

            String header=oAuthClass.setMethod("GET")

                    .setConsumersecret("XNNdlf1bduK5gf03wybQLoFxv9EtNfDIjFDLSxlDixzTVoCZOx") // same always
                    .setTokensecret("D7ziqZdXGpgTtse1zEtyJkkPClAmyHCSnvLTCeOCaIR8q") //runtime
                    .setOauth_consumer_key("VLaMbUjondPMFyibiXqIjTKY8") //same always
                    .setOauth_token("845296363801735168-CXoszJWJEPLnmVOg10CbsjRX4CzpbzR") //runtime

                    .setOauth_signature_method("HMAC-SHA1")
                    .setOauth_version("1.0")

                    .setOauth_nonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")//should be a random string everytime
                    .setOauth_timestamp("1318622958") //current epoch time
                    .setBody(null) //set to null if there is no request body
                    .setBaseurl("https://api.twitter.com/1.1/account/settings.json")//Complete API endpoint which is being hit
                    .setQuery(null)//Query Paremeters for the request-set to null if not any
                    .getAuthheader();


            Log.d("lala",header);

            /*Now this header string will be added as a header as {Authorization: header string} to each request
            Set the 4 keys ,request & signature method , version only once while making the oAuthObject or making the first request
            This whole header calculation can be done in the API interface of retrofit while using one oAuthClass object
            Refer to the link https://dev.twitter.com/oauth/overview/authorizing-requests for more details
            */

            Log.d("lala",oAuthClass.displaySignature());
            /*This function displays the signature that is generated internally using the oAuth header and other details
            Refer to https://dev.twitter.com/oauth/overview/creating-signatures for more details
            */

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
