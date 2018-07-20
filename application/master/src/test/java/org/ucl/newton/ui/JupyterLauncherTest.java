package org.ucl.newton.ui;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class JupyterLauncherTest
{
    @Test
    @Ignore
    public void authenticateTest() throws Exception {

        FileInputStream inputStream = new FileInputStream(new File("/Users/blair/Code/newton/deployment/jupyter/newton.key"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, outputStream);

        byte[] key = outputStream.toByteArray();
        //byte[] key = "htpYsmSJm5aSv4BVjvGFTR3hH2Ci1Pwho66P8LPMk0DL1U2E9kG7hg0AL4t6AoNYpXQSbvsfiVSGFavq1GFJPJ5edo4g00ZJv7VaBBsCSBejnMy59LYngoDzyy0YfP2hhtpYsmSJm5aSv4BVjvGFTR3hH2Ci1Pwho66P8LPMk0DL1U2E9kG7hg0AL4t6AoNYpXQSbvsfiVSGFavq1GFJPJ5edo4g00ZJv7VaBBsCSBejnMy59LYngoDzyy0YfP2h".getBytes(StandardCharsets.US_ASCII);

        String webToken = Jwts.builder()
                .setSubject("test")
                .setAudience("www.newton.com")
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:8000/login?access_token=" + webToken));
        }
    }
}
