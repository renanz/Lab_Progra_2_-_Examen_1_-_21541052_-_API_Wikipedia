/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API_Wikipedia;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import org.json.JSONArray;

import org.json.JSONObject;

/**
 *
 * @author Renan
 */
public class API_Wikipedia { 
    //public static void main(String[] args) {
    static String equipo;

    public static String getEquipo() {
        return equipo;
    }

    public static void setEquipo(String equipo) {
        API_Wikipedia.equipo = equipo;
    }

    public static String hacer( int choose){
        boolean continuar = true;
       // do{
            Scanner lea = new Scanner (System.in);
           // System.out.print("Ingrese el nombre de la ciudad: ");
            //String equipo = lea.nextLine();
            String noSpace = equipo.replaceAll("\\s","_");
            try {
                String respuesta = getHTML("https://en.wikipedia.org/w/api.php?action=query&titles="+noSpace+"&prop=revisions&rvprop=content&format=json");
                JSONObject obj = new JSONObject(respuesta);
                
                //Titulo equipo
                String title = obj.getJSONObject("query").getJSONObject("pages").getJSONObject(getPageId(obj)).getString("title");
                
                
                //Informacion completa
                String arr = obj.getJSONObject("query").getJSONObject("pages").getJSONObject(getPageId(obj)).getJSONArray("revisions").getJSONObject(0).getString("*");
               // System.out.println (arr);
                     
               switch (choose){
                    case 1:
                        return establecido(arr,title);
                        
                    case 2:
                        return league(arr, title);
                       
                    case 3:
                        return colors(arr, title);
                        
                    case 4:
                        return manager(arr, title);
                        
                    case 5:
                        return generalManager(arr, title);
                        
                    default:
                        return (establecido(arr,title)+league(arr, title)+colors(arr, title)+manager(arr, title)+generalManager(arr, title));
                }
              //  nicknames(arr, title);
/*
                System.out.println ("Desea consultar otro equipo");
                char seguir = lea.next().charAt(0);
                if (seguir=='n')
                    continuar= false;
            */} catch (Exception e) {
                e.printStackTrace();
            }return "hola";/*
        }while (continuar);
        System.out.println("Nos vemos...");*/
    }
    
    public static String establecido(String arr, String title){
        String e = "established      = ";
        int establish = arr.indexOf(e);
        String established = "";
        for(int i=(establish+e.length());arr.charAt(i)!='\n';i++){
            established+=arr.charAt(i);
        }
        //System.out.println(title+" fue establecido en "+established+"\n");
        return established;
    }
    
    public static String league(String arr, String title){
        String cl = "current league   = ";
        int league = arr.indexOf(cl);
        String currentLeague = "";
        for(int i=(league+cl.length());arr.charAt(i)!='\n';i++){
            currentLeague+=arr.charAt(i);
        }
        //System.out.println(title+" juega en la "+currentLeague+"\n");
        return currentLeague;
    }
    
    public static String manager(String arr, String title){
        String man = "manager          = [[";
        int manag = arr.indexOf(man);
        String manager = "";
        for(int i=(manag+man.length());arr.charAt(i)!='\n';i++){
            manager+=arr.charAt(i);
        }
        String m = "";
        for (int i = 0; i < (manager.length()-2); i++) {
            m+=manager.charAt(i);
        }
        //System.out.println("El entrenador de los "+title+" es: "+m+"\n");
        return m;
    }
    
    public static String generalManager(String arr, String title){
        String gm = "gm               = [[";
        int genM = arr.indexOf(gm);
        String generalManager = "";
        for(int i=(genM+gm.length());arr.charAt(i)!='\n';i++){
            generalManager+=arr.charAt(i);
        }
        String gmm = "";
        for (int i = 0; i < (generalManager.length()-2); i++) {
            gmm+=generalManager.charAt(i);
        }
        //System.out.println("El gerente general de los "+title+" es "+gmm+"\n");
        return gmm;
    }
    
    public static String nicknames(String arr, String title){
        String nick = "nicknames = ";
        int nm = arr.indexOf(nick);
        String nicknames = "";
        for(int i=(nm+nick.length());arr.charAt(i)!='\n';i++){
            nicknames+=arr.charAt(i);
        }
        //System.out.println("Los apodos de "+title+" son: "+nicknames+"\n");
        return nicknames;
    }
    
    public static String colors(String arr, String title){
        String col = "colors           = ";
        int cols = arr.indexOf(col);
        String colors = "";
        for(int i=(cols+col.length());arr.charAt(i)!='\n';i++){
            colors+=arr.charAt(i);
        }
        System.out.println("Los colores de "+title+" son: "+colors+"\n");
        return colors;
    }
    
    private static String getPageId(JSONObject obj){
        String pageid = obj.getJSONObject("query").getJSONObject("pages").names().toString();
        String g="";
        for (int i = 2; i < (pageid.length()-2); i++) {
                    g+=pageid.charAt(i);
                }
        return g;
    }
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
           result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
