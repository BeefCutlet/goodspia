package shop.goodspia.goods.global.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Profile("dev")
@Component
@RequiredArgsConstructor
public class ImageUploadDev implements ImageUpload {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImage(MultipartFile multipartFile, ImagePath imagePath) {
        String filename = UUID.randomUUID().toString().substring(0, 8) + "-" +
                URLEncoder.encode(Objects.requireNonNull(multipartFile.getOriginalFilename()), StandardCharsets.UTF_8);
        String filePath = imagePath + "/" + filename;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(bucket, filePath, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            log.warn("ImageUpload Failed, error: {}", e.getMessage());
        }

        return amazonS3Client.getUrl(bucket, filePath).toString();
    }
}
