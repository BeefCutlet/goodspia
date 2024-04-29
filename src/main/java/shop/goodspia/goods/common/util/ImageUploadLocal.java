package shop.goodspia.goods.common.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Profile("local")
@Component
public class ImageUploadLocal implements ImageUpload {

    private static final String OBJECT_PATH = "profile-image";

    public String uploadImage(MultipartFile multipartFile) {
        String filename = UUID.randomUUID().toString().substring(0, 8) + "-" + multipartFile.getOriginalFilename();

        //로컬에 파일 저장
        String localFilePath = System.getProperty("user.home") + "/" + OBJECT_PATH;
        File dir = new File(localFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = localFilePath + "/" + filename;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return filePath;
    }
}
