import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import javax.swing.JOptionPane;
import org.json.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DropboxTest {

    public static void main(String args[]){

    }

    public JSONObject UploadFile(){
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response1 = Unirest.post("https://content.dropboxapi.com/2/files/upload")
                    .header("Dropbox-API-Arg", "{\"path\": \"/NewFile.txt\",\"mode\": \"add\",\"autorename\": true,\"mute\": false,\"strict_conflict\": false}")
                    .header("Content-Type", " application/octet-stream")
                    .header("Authorization", "Bearer KDR7hPN4XmEAAAAAAAAAAWwi8ZL7LQ78kOzK_1KHmASGrDbqU10GhQjvlhk5WmTf")
                    .body("<file contents here>")
                    .asJson();

            JSONObject responseBody1 =  response1.getBody().getObject();
            return responseBody1;
        }
        catch (UnirestException e){
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject GetMetaData(){
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response2 = Unirest.post("https://api.dropboxapi.com/2/files/get_metadata")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer KDR7hPN4XmEAAAAAAAAAAWwi8ZL7LQ78kOzK_1KHmASGrDbqU10GhQjvlhk5WmTf")
                    .body("{\r\n    \"path\": \"/NewFile.txt\",\r\n    \"include_media_info\": false,\r\n    \"include_deleted\": false,\r\n    \"include_has_explicit_shared_members\": false\r\n}")
                    .asJson();

            JSONObject responseBody2 =  response2.getBody().getObject();
            return responseBody2;
        }catch (UnirestException e){
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject DeleteFile(){
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response3 = Unirest.post("https://api.dropboxapi.com/2/files/delete_v2")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer KDR7hPN4XmEAAAAAAAAAAWwi8ZL7LQ78kOzK_1KHmASGrDbqU10GhQjvlhk5WmTf")
                    .body("{\r\n    \"path\": \"/NewFile.txt\"\r\n}")
                    .asJson();

            JSONObject responseBody3 =  response3.getBody().getObject();
            return  responseBody3;
        }catch (UnirestException e){
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testUpload() {
        // Якщо в колонці name вказано ім'я ( якщо взагалі таке ім'я існує), то об'єкт створено.
        // У запитів, які видаються при помилці такого поля не існує. Тому перевіряємо на існування
        assertNotNull(UploadFile().has("name"));
    }

    @Test
    public void testGetMetaData(){
        // Те саме, що і для upload - інформація та сама.
        assertTrue(GetMetaData().has("name"));
    }

    @Test
    public void testDelete() {
        // при успішному видаленні має отримуватися файл JSON з ключем "metadata" - перевіримо на рівність даним у
        // запиті на отримання metadata, а також наявність такого поля
        assertTrue(DeleteFile().has("metadata"));
        if (DeleteFile().has("metadata")) {
            assertEquals(GetMetaData(), DeleteFile().getString("metadata"));
        }
    }

}
