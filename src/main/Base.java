/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Apl-Satellite
 */
public class Base {

    /**
     * @param args the command line arguments
     * @throws java.lang.Throwable
     */
    public static void main(String[] args) throws Throwable{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String urlRemote = "http://speller.yandex.net/services/spellservice/checkText";
        //String urlLocalStub = "http://apl-satellite1:8080/YaSpellerWSStub/SpellService";
        File file = new File("checkTextRequest.xml");
        Scanner sc = new Scanner(file);
        String str = null;
        while (sc.hasNextLine()) {
            if (str == null) {
                str = sc.nextLine();
            } else {
                str += sc.nextLine();
            }
        }

        HttpPost httpPost = new HttpPost(urlRemote);
        httpPost.addHeader("SOAPAction", "http://speller.yandex.net/services/spellservice");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(new StringEntity(str));
        CloseableHttpResponse resp2 = httpClient.execute(httpPost);
        String sResp = EntityUtils.toString(resp2.getEntity());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("checkTextResponse.xml"))) {
            writer.write(sResp);
        }
    }

}