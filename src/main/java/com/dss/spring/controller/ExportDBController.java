package com.dss.spring.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RestController
public class ExportDBController {

    @GetMapping("/exportDB")
    public ResponseEntity<InputStreamResource> exportDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./data/testdb", "sa", "password");

            String outputFile = "exportedDB.sql";
            Statement stmt = conn.createStatement();
            stmt.execute("SCRIPT TO 'exportedDB.sql' TABLE PRODUCT");

            stmt.close();
            conn.close();
            
            File file = new File(outputFile);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);

        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

