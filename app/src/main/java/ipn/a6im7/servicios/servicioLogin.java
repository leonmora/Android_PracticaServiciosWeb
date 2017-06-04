package ipn.a6im7.servicios;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class servicioLogin extends Activity {

    EditText usuario;
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_login);

        usuario=(EditText)findViewById(R.id.usuario);
        clave=(EditText)findViewById(R.id.clave);
    }

    public void iniciarSesion(View vw){
        String us=usuario.getText().toString();
        String cl=clave.getText().toString();
        if(us.isEmpty() || cl.isEmpty()){
            Toast.makeText(getApplicationContext(),"Llena ambos campos",Toast.LENGTH_SHORT).show();
        }else
            new consumidor().execute(us,cl);
    }

    private class consumidor extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String respuesta="";
            try {
                String NAMESPACE = "http://soap/";
                String URL = "http://localhost:8080/servicios/login";
                String METHOD_NAME = "iniciarSesion";
                String SOAP_ACTION = "http://soap/login/iniciarSesion";// wsam:Action

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("nombre",strings[0]);
                request.addProperty("clave",strings[1]);
                SoapSerializationEnvelope envelope =
                        new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result= (SoapPrimitive) envelope.getResponse();
                respuesta = result.toString();
            }
            catch(Exception e){
                Log.e("Error",e.getMessage());
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String s) {
           Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }
}
