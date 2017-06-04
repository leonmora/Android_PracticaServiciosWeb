package ipn.a6im7.servicios;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class servicioCalc extends Activity {

    EditText numA;
    EditText numB;
    private String A;
    private String B;
    private int operador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_calc);

        numA=(EditText)findViewById(R.id.numeroA);
        numB=(EditText)findViewById(R.id.numeroB);
    }

    public void selectOp(View vw){
        switch (vw.getId()){
            case R.id.suma:
                operador=1;
                break;
            case R.id.resta:
                operador=2;
                break;
            case R.id.Division:
                operador=3;
                break;
            case R.id.Producto:
                operador=4;
                break;
        }
    }

    public void operar(View vw){
        A=numA.getText().toString();
        B=numB.getText().toString();
        if(A.isEmpty() || B.isEmpty()){
            Toast.makeText(getApplicationContext(),"Necesitas 2 números",Toast.LENGTH_SHORT).show();
        }else if(operador==0){
            Toast.makeText(getApplicationContext(),"Selecciona una operación",Toast.LENGTH_SHORT).show();
        }else{
            new consumidor().execute(A,B,""+operador);
        }

    }

    private class consumidor extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String respuesta="";
            try {
                String NAMESPACE = "http://soap/";
                String URL = "http://localhost:8080/servicios/calculadora";
                String METHOD_NAME = "getResultado";
                String SOAP_ACTION = "http://soap/calculadora/getResultado";// wsam:Action

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("numA",strings[0]);
                request.addProperty("numB",strings[1]);
                request.addProperty("op",strings[2]);
                SoapSerializationEnvelope envelope =
                        new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result= (SoapPrimitive) envelope.getResponse();
                respuesta = result.toString();
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }
}
