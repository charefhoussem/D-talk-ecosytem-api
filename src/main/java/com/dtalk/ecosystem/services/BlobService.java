package com.dtalk.ecosystem.services;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service

public interface BlobService {


        public List<String> listFiles() ;

        public ByteArrayOutputStream downloadFile(String blobitem) ;

        public String storeFile(String filename, InputStream content, long length) ;


}
