package ipn.a6im7.servicios;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class servicioConversor extends Activity {

    private int divisa=0;
    private String monto="";
    EditText MXN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_conversor);
        MXN=(EditText)findViewById(R.id.monto);
    }

    public void divisa(View vw){

        switch(vw.getId()){
            case R.id.dolar:
                divisa=1;
                break;
            case R.id.Marco:
                divisa=2;
                break;
            case R.id.Euro:
                divisa=3;
                break;
            case R.id.Yen:
                divisa=4;
                break;
            case R.id.Yuan:
                divisa=5;
                break;
        }
    }

    public void convertir(View vw){
        monto=MXN.getText().toString();
        if(monto.isEmpty()){
            Toast.makeText(getApplicationContext(),"Introduce una cantidad",Toast.LENGTH_SHORT).show();
        }else if(divisa!=0){
            new consumidor().execute(monto,""+divisa);
        }else
            Toast.makeText(getApplicationContext(),"Selecciona una divisa",Toast.LENGTH_SHORT).show();
    }

    private class consumidor extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            HttpClient cliente=new DefaultHttpClient();
            HttpGet peticion=new HttpGet("http://localhost:8080/servicios/webresources/Convertidor/" +
                    "MXN/cantidad="+strings[0]+"&tipo="+strings[1]);
            peticion.setHeader("content-type","text/plain");
            try {
                HttpResponse res=cliente.execute(peticion);
                result= EntityUtils.toString(res.getEntity());
            } catch (IOException e) {
                Log.e("Error:",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),"Resultado: "+s,Toast.LENGTH_LONG).show();
        }
    }
}

