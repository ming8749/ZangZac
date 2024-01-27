package com.kh.zangzac.common;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Component
public class ImageStorage {
    
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;

    // 생성자로 외부에서 Storage를 주입받도록 함
    @Autowired
    public ImageStorage(Storage storage) {
        this.storage = storage;
    }

    // 이미지 저장 로직 추가
    @SuppressWarnings("deprecation")
    public String[] saveImage(MultipartFile file, String name) {
        
        if(file.getOriginalFilename() != "") {
            // 랜덤 이름 생성 
            String uuid = UUID.randomUUID().toString();
            // 확장자 명 찾기
             String ext = file.getContentType();
    
             // 파일 경로를 설정합니다.
             String filePath = "image/"+name+"/"+uuid;
    
             BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath)
                     .setContentType(ext)
                     .build();
             
             //삭제 테스트
            // boolean deleted = storage.delete(blobInfo.getBlobId());
             //System.out.println(deleted);
             
             //실제 클라우드에 이미지 업로드
             try {
             storage.create(blobInfo, file.getInputStream());
          } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
          }
           
             
             String fileName =uuid+"."+ext.split("/")[1];
             String path = "https://storage.googleapis.com/zangzac/image/"+name+"/"+uuid;
             
             //이름과 경로를 반환
             String[] result =new String[] {fileName,path};
             return result;
        }
        return null;
          
     }

    // 이미지 삭제
    public boolean deleteImage(String imageName,String name) {
       
       String uuid = imageName.split("\\.")[0];
       
       //.으로 나누기
        String ext = imageName.split("\\.")[1];
        
        
        String filePath = "image/"+name+"/"+uuid;
        
        
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath)
                 .setContentType(ext)
                 .build();
         
         //삭제 
         boolean deleted = storage.delete(blobInfo.getBlobId());
         
         //실제 삭제를 성공 했는지 반환
        return deleted; 
    }
}