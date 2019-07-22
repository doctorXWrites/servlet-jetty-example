package com.codeacademy.www.integration.servlets;

import com.codeacademy.www.JettyServerB;
import org.junit.*;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*
 * Pitfall: 1. Before/After vs BeforeClass/AfterClass
 * */

public class UserFacingServletTest {
    JettyServerB server;

    @Before
    public void setUp() throws Exception {
        server = new JettyServerB();
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                server.start();
            }
        };

        new Thread(runnable).start();
        Thread.sleep(10000);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testServerUp() {
        String url = "http://localhost:8091/api/getAge";
        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream response = connection.getInputStream();
            Scanner sc = new Scanner(response);
            String result = sc.nextLine();
            assertEquals("Hello World!", result);
        } catch (Exception ex) {
            fail("Exception Happened, Server is not running!" + ex);
        }
    }

    @Test
    public void testGetAge() {
        String url = "http://localhost:8091/api/getAge";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            String params = "{\"name\":\"Aarav\",\"yob\":\"2003\"}";
            byte[] postData = params.getBytes( StandardCharsets.UTF_8 );
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(postData);

            InputStream response = connection.getInputStream();
            Scanner sc = new Scanner(response);
            String result = sc.nextLine();
            assertEquals("Hey Aarav your age is 16 years!", result);
        } catch (Exception ex) {
            fail("Exception Happened!" + ex);
        }
    }
}
