package com.example.market.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import java.io.File;

@Component
@Getter
@Slf4j
public class CustomFileUtils {
    public final String uploadPath; // 객체화가 끝난다음에 final이 되기떄문에 이대로 사용 불가능
    // DI
    public CustomFileUtils(@Value("${file.directory}")String uploadPath) {
        this.uploadPath = uploadPath;
    }

    //폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadPath, path);
        // folder.mkdirs();
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                log.info("Successfully created directory: {}", folder.getAbsolutePath());
            } else {
                log.error("Failed to create directory: {}", folder.getAbsolutePath());
                throw new RuntimeException("Failed to create directory: " + folder.getAbsolutePath());
            }
        } else {
            log.info("Directory already exists: {}", folder.getAbsolutePath());
        }
        return folder.getAbsolutePath();
    }
    // UUID 랜덤 파일명
    public String makeRandomFileName(){
        return UUID.randomUUID().toString();
    }
    //파일명에서  확장자 얻어오기
    public String getExt(String fileName){
        //abcde.ddd.jpg 중에 jpg를 가져와야한다
//        int idx = fileName.indexOf("cd"); //  왼쪽에서 찾고자 함 찾고자하는 곳에 점이 있다면 양수값 없으면 -1 값
        //오른쪽에서 찾고자 함 찾고자하는 곳에 점이 있다면 양수값 없으면 -1 값
        int idx = fileName.lastIndexOf(".");
        String[] str = fileName.split("\\.");
        return fileName.substring(idx);
    }

    // 랜덤 파일명 with 확장자 만들기
    public String makeRandomFileName(String fileName){ // fileName 원본 에 확장자 랜덤한 파일명 리턴

        return makeRandomFileName() +getExt(fileName);
    }
    //랜덤파일명 with 확장자 만들기 using MultipartFile
    public String makeRandomFileName(MultipartFile mf){
        return mf == null ? null : mf.isEmpty() ? null: makeRandomFileName(mf.getOriginalFilename());


    }
    // 파일저장  target 는 경로랑 파일명까지 지정 된상태
    public  void transferTo(MultipartFile mf , String target ) throws Exception {
        log.info("uploadPath:{}",uploadPath);
        File saveFile = new File(uploadPath , target); // 최종경로
        File parentDir = saveFile.getParentFile();

        // 상위 디렉토리 확인 및 생성
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log.info("Successfully created parent directory: {}", parentDir.getAbsolutePath());
            } else {
                log.error("Failed to create parent directory: {}", parentDir.getAbsolutePath());
                throw new RuntimeException("Failed to create parent directory: " + parentDir.getAbsolutePath());
            }
        } else {
            log.info("Parent directory already exists: {}", parentDir.getAbsolutePath());
        }
    
        log.info("Attempting to save file to path: {}", saveFile.getAbsolutePath());
        mf.transferTo(saveFile);  // 이 부분에서 파일 저장 시도
    }
    
    // 폴더삭제
    public  void deleteFolder(String absoluteFolderPath){
        File folder = new File(absoluteFolderPath);
        if(folder.exists()&&folder.isDirectory()){
            File[] files = folder.listFiles();

            for (File file : files) {
                if(file.isDirectory()){
                    deleteFolder(file.getAbsolutePath());
                }else{
                    file.delete();
                }
                folder.delete();
            }
        }
    }
}
